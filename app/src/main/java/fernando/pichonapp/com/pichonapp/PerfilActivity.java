package fernando.pichonapp.com.pichonapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.Permission;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {
    String uid;
    private static final int PHOTO=1;
    private CircleImageView fotoPerfil;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Usuarios");
    private FirebaseStorage firebaseStorage;
    private Context c;
    private StorageReference storageReference;
    int MY_PERMISSIONS_ACCESS_NETWORK=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c=this;
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_toolbar);
        fotoPerfil= (CircleImageView) findViewById(R.id.profile_image);
        this.setSupportActionBar(toolbar);
        Intent intent = PerfilActivity.this.getIntent();
        uid = intent.getExtras().getString("idu");
        DatabaseReference ref = database.getReference("Usuarios");
        firebaseStorage = FirebaseStorage.getInstance();

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EditText nick=findViewById(R.id.nickname);
                String nn=dataSnapshot.child(uid).child("nickname").getValue().toString();
                nick.setText(nn);
                Glide.with(c).load(dataSnapshot.child(uid).child("perfil").getValue().toString()).into(fotoPerfil);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void clickFoto(View view){
        Intent i= new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/jpeg");
        i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(Intent.createChooser(i,"Selecciona una foto"),PHOTO);
    }

    public void saveData(View view){
        String nickname=((EditText) findViewById(R.id.nickname)).getText().toString();
        ref.child(uid).child("nickname").setValue(nickname);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PHOTO && resultCode== RESULT_OK){
            Uri u= data.getData();
            storageReference= firebaseStorage.getReference("perfil");
            final StorageReference sr = storageReference.child(u.getLastPathSegment());
            sr.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri u= taskSnapshot.getDownloadUrl();
                    ref.child(uid).child("perfil").setValue(u.toString());

                    Glide.with(c).load(u.toString()).into(fotoPerfil);
                }
            });
        }
    }
}
