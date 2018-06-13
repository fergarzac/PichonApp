package fernando.pichonapp.com.pichonapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversacionActivity extends AppCompatActivity {
    private String key;
    private TextView nick;
    private EditText texto;
    private AdaptadorMensajes am;
    private RecyclerView rv;
    private Button enviar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private CircleImageView perfil;
    private ImageButton img;
    Context c;
    private static final int PHOTO_SEND=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_toolbar);
        c=this;
        this.setSupportActionBar(toolbar);
        Intent intent = ConversacionActivity.this.getIntent();
        key = intent.getExtras().getString("key");
        perfil=findViewById(R.id.perfil);
        nick= findViewById(R.id.nickname);
        texto= findViewById(R.id.editText);
        rv= findViewById(R.id.mensajes);
        img= (ImageButton) findViewById(R.id.btnFoto);
        am= new AdaptadorMensajes(this);
        LinearLayoutManager l= new LinearLayoutManager(this);
        rv.setLayoutManager(l);
        rv.setAdapter(am);
        DatabaseReference ref = database.getReference("Usuarios/"+key);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nick.setText(dataSnapshot.child("nickname").getValue().toString());
                Glide.with(c).load(dataSnapshot.child("perfil").getValue().toString()).into(perfil);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference refMensajes = database.getReference("Mensajes");
        refMensajes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.child("destino").getValue().toString().equals(ChatActivity.idu) && data.child("remitente").getValue().toString().equals(key)
                            || data.child("destino").getValue().toString().equals(key) && data.child("remitente").getValue().toString().equals(ChatActivity.idu)){
                         am.addMensaje(new Mensaje(data.child("destino").getValue().toString(),data.child("remitente").getValue().toString(),data.child("mensaje").getValue().toString(),"",data.child("hora").getValue().toString(),1));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        am.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScroll();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una foto"),PHOTO_SEND);
            }
        });

        enviar=findViewById(R.id.Enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = database.getReference("/");
                DatabaseReference refmia = database.getReference("Usuarios/"+ChatActivity.idu+"/Contactos/"+key);
                DatabaseReference refel = database.getReference("Usuarios/"+key+"/Contactos/"+ChatActivity.idu);
                long msTime = System.currentTimeMillis();
                ref.child("Mensajes").push().setValue(new Mensaje(key,ChatActivity.idu,texto.getText().toString(),"",""+msTime,1));
                refmia.child("Mensajes").child("Ultimo").setValue(new Mensaje(texto.getText().toString(),""+msTime,1));
                refel.child("Mensajes").child("Ultimo").setValue(new Mensaje(texto.getText().toString(),""+msTime,1));
                texto.setText("");
            }
        });
    }


    private void setScroll(){
        rv.scrollToPosition(am.getItemCount()-1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PHOTO_SEND && resultCode== RESULT_OK){
            Uri u= data.getData();
        }
    }
}
