package fernando.pichonapp.com.pichonapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mensaje {
    private String destino;
    private String Remitente;
    private String mensaje;
    private String foto;
    private String hora;
    private String key;
    private int tipo;
    private String nickname;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Mensaje(){

    }

    public Mensaje(String destino, String remitente, String mensaje, String foto, String hora, int tipo) {
        this.destino = destino;
        Remitente = remitente;
        this.mensaje = mensaje;
        this.foto = foto;
        this.hora = hora;
        this.tipo = tipo;
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Usuarios/"+remitente);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nickname=dataSnapshot.child("nickname").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getRemitente() {
        return Remitente;
    }

    public void setRemitente(String remitente) {
        Remitente = remitente;
    }

    public Mensaje(String mensaje, String hora, int tipo) {
        this.hora = hora;
        this.mensaje = mensaje;
        this.tipo = tipo;

    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
