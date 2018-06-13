package fernando.pichonapp.com.pichonapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderMensaje extends RecyclerView.ViewHolder {
    private TextView nombre;
    private TextView mensaje;
    private CircleImageView imagen;
    private LinearLayout ll;
    public HolderMensaje(View itemView) {
        super(itemView);
        ll=(LinearLayout) itemView.findViewById(R.id.cardL);
        nombre= (TextView) itemView.findViewById(R.id.nombre);
        mensaje= (TextView) itemView.findViewById(R.id.mensaje);
        imagen= (CircleImageView) itemView.findViewById(R.id.imagen);
    }

    public TextView getNombre() {
        return nombre;
    }

    public LinearLayout getLl() {
        return ll;
    }

    public void setLl(LinearLayout ll) {
        this.ll = ll;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public CircleImageView getImagen() {
        return imagen;
    }

    public void setImagen(CircleImageView imagen) {
        this.imagen = imagen;
    }

}
