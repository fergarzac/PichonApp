package fernando.pichonapp.com.pichonapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String email,password;
    private String file="data";
    private String salir="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_toolbar);
        Intent intent = MainActivity.this.getIntent();
        try {
            salir = intent.getExtras().getString("salir");
        }catch (Exception e){

        }
        this.setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        if(salir.equals("false")){
            try{
                FileInputStream fin = openFileInput(file);
                int c;
                String temp="";
                while( (c = fin.read()) != -1){
                    temp = temp + Character.toString((char)c);
                }
                fin.close();

                String[] div=temp.split(",");

                String [] uid = div[0].split(":");
                String [] email= div[1].split(":");

                Intent intento = new Intent(MainActivity.this,ChatActivity.class);
                intento.putExtra("idu",uid[1]);
                intento.putExtra("email",email[1]);
                startActivity(intento);
            }catch(Exception e){

            }
        }
    }


    public void login(View view){
            email= ((EditText) findViewById(R.id.email)).getText().toString();
            password= ((EditText) findViewById(R.id.pass)).getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intento = new Intent(MainActivity.this,ChatActivity.class);
                                intento.putExtra("idu",user.getUid());
                                intento.putExtra("email",user.getEmail());
                                createFile(user.getUid(),user.getEmail());
                                startActivity(intento);
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
    }


    public void registrar(View view){
        email= ((EditText) findViewById(R.id.email)).getText().toString();
        password= ((EditText) findViewById(R.id.pass)).getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intento = new Intent(MainActivity.this,ChatActivity.class);
                            intento.putExtra("idu",user.getUid());
                            intento.putExtra("email",user.getEmail());
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Usuarios");
                            Usuario u = new Usuario(user.getUid(),user.getEmail(),user.getEmail());

                            createFile(user.getUid(),user.getEmail());
                            myRef.child(user.getUid()).setValue(u);
                            startActivity(intento);
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void createFile(String id, String email){
        String data="idu:"+id+",email:"+email;
        try {
            FileOutputStream fOut = openFileOutput(file,MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();
            System.out.println("Archivo hecho!");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
