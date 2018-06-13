package fernando.pichonapp.com.pichonapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    public static String idu;
    private AdaptadorContactos am;
    private RecyclerView rv;
    private CircleImageView foto;
    private TextView nombre;
    private String ultimomensaje;
    ArrayList contactos;
    private String key;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar=(Toolbar) findViewById(R.id.app_toolbar);
        rv=(RecyclerView) findViewById(R.id.rvcontactos);
        Intent intent = ChatActivity.this.getIntent();
        idu = intent.getExtras().getString("idu");
        this.setSupportActionBar(toolbar);
        am= new AdaptadorContactos(this);
        LinearLayoutManager l= new LinearLayoutManager(this);
        rv.setLayoutManager(l);
        rv.setAdapter(am);

        DatabaseReference ref = database.getReference("Usuarios/"+idu+"/Contactos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot data:dataSnapshot.getChildren()){
                    key = data.child("idu").getValue().toString();
                    DatabaseReference mensajes = database.getReference("Usuarios/"+idu+"/Contactos/"+key+"/Mensajes");
                    mensajes.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                ultimomensaje = dataSnapshot.child("Ultimo").child("mensaje").getValue().toString();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mensajes.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            int item=0;
                            for (int i=0;i<am.getMensajes().size();i++){
                                if(am.getMensajes().get(i).getKey().equals(key)){
                                    item=i;
                                    am.getMensajes().get(i).setMensaje(dataSnapshot.child("mensaje").getValue().toString());
                                }
                            }
                            am.notifyItemChanged(item);
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    DatabaseReference ref2 = database.getReference("Usuarios/"+data.child("idu").getValue().toString());
                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            am.addMensaje(new Contactos(key,dataSnapshot.child("nickname").getValue().toString(),ultimomensaje,dataSnapshot.child("perfil").getValue().toString(),1));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference ref3 = database.getReference("Usuarios/");
        ref3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                List<Contactos> contactos = am.getMensajes();
                String key= dataSnapshot.getKey();
                int item=0;
                for (int i=0;i<am.getMensajes().size();i++){
                    System.out.println("Key "+contactos.get(i).getKey());
                    if(am.getMensajes().get(i).getKey().equals(key)){
                        item=i;
                        am.getMensajes().get(i).setNombre(dataSnapshot.child("nickname").getValue().toString());
                    }
                }
                am.notifyItemChanged(item);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){});

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.settings:
                Intent intento = new Intent(this,PerfilActivity.class);
                intento.putExtra("idu",idu);
                startActivity(intento);
                break;
            case R.id.about:
                break;
            case R.id.exit:
                Intent intento2 = new Intent(ChatActivity.this,MainActivity.class);
                intento2.putExtra("salir","true");
                startActivity(intento2);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void chat(View view){

    }


}
