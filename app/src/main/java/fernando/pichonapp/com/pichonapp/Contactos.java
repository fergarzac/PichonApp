package fernando.pichonapp.com.pichonapp;

public class Contactos  {
    private String key;
    private String nombre;
    private String mensaje;
    private String foto;
    private int tipo;
    public Contactos(){

    }
    public Contactos(String key,String nombre, String mensaje, String foto, int tipo) {
        this.key = key;
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.foto = foto;
        this.tipo = tipo;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
