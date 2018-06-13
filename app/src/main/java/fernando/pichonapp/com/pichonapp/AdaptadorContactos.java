package fernando.pichonapp.com.pichonapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorContactos extends RecyclerView.Adapter<HolderMensaje> {
    List<Contactos> contactos = new ArrayList<>();
    private int posicion=0;
    private Context c;

    public List<Contactos> getMensajes() {
        return contactos;
    }

    public AdaptadorContactos(Context c) {
        this.c = c;
    }

    public void addMensaje(Contactos m){
        contactos.add(m);
        notifyItemInserted(contactos.size());
    }

    public void setMensajes(List<Contactos> contactos) {
        this.contactos = contactos;
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    @Override
    public HolderMensaje onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_contactos,parent,false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(HolderMensaje holder, int position) {
        posicion=position;
        holder.getNombre().setText(contactos.get(position).getNombre());
        holder.getMensaje().setText(contactos.get(position).getMensaje());
        Glide.with(c).load(contactos.get(position).getFoto()).into(holder.getImagen());
        holder.getLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(view.getContext(),ConversacionActivity.class);
                i.putExtra("key",contactos.get(posicion).getKey());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

}
