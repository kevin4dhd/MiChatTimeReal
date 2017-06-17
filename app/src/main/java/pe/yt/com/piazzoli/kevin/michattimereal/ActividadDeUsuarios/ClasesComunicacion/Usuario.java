package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.ClasesComunicacion;

/**
 * Created by user on 16/06/2017. 16
 */

/*id
nombreCompleto
apellido
estado
fecha_amigos
mensaje
hora_del_mensaje*/

public class Usuario {

    private String id;
    private String nombreCompleto;
    private int estado;
    private String fecha_amigos;
    private String mensaje;
    private String hora_del_mensaje;

    public Usuario(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha_amigos() {
        return fecha_amigos;
    }

    public void setFecha_amigos(String fecha_amigos) {
        this.fecha_amigos = fecha_amigos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHora_del_mensaje() {
        return hora_del_mensaje;
    }

    public void setHora_del_mensaje(String hora_del_mensaje) {
        this.hora_del_mensaje = hora_del_mensaje;
    }
}
