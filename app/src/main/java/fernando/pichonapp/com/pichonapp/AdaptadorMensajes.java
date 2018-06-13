package fernando.pichonapp.com.pichonapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorMensajes extends RecyclerView.Adapter<HolderMensaje> {
    List<Mensaje> mensajes = new ArrayList<>();
    private Context c;
    HolderMensaje h;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String remitente;
    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public AdaptadorMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(Mensaje m){
        mensajes.add(m);
        notifyItemInserted(mensajes.size());
    }



    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }
    int FIRST_TYPE=1,SECOND_TYPE=2;
    @Override
    public HolderMensaje onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println(viewType);
        View v;
        if(viewType == FIRST_TYPE ){
            v = LayoutInflater.from(c).inflate(R.layout.card_view_mios,parent,false);
        }else{
            v = LayoutInflater.from(c).inflate(R.layout.card_viw_mensajes,parent,false);
        }

        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(HolderMensaje holder, int position) {
        h=holder;
        h.getNombre().setText(mensajes.get(position).getNickname());
        h.getMensaje().setText(mensajes.get(position).getMensaje());
        DatabaseReference fotoP= database.getReference("Usuarios/"+mensajes.get(position).getRemitente()+"/perfil");
        fotoP.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Glide.with(c).load(dataSnapshot.getValue().toString()).into(h.getImagen());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public int getItemViewType (int position) {
        if(mensajes.get(position).getRemitente().equals(ChatActivity.idu)){
            return FIRST_TYPE;
        }else {
            return SECOND_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }
}
