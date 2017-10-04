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
    private String mensaje;
    private String hora;
    private int FotoPerfil;

    public Usuario(){

    }

    public Usuario(String id, String nombreCompleto, int estado, String hora, int fotoPerfil) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.estado = estado;
        this.hora = hora;
        FotoPerfil = fotoPerfil;
    }

    public Usuario(String id, String nombreCompleto, String mensaje, String hora, int fotoPerfil) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.mensaje = mensaje;
        this.hora = hora;
        FotoPerfil = fotoPerfil;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getFotoPerfil() {
        return FotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        FotoPerfil = fotoPerfil;
    }
}
