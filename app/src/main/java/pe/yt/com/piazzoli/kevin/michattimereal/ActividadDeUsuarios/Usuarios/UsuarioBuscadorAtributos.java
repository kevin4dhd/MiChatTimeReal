package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios;

/**
 * Created by user on 26/05/2017.
 */

public class UsuarioBuscadorAtributos {
    private String id;
    private int fotoPerfil;
    private String nombre;
    private String estadoUsuario;

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
