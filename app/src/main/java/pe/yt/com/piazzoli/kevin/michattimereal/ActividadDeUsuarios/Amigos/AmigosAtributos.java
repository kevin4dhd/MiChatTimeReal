package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Amigos;

import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.ClasesComunicacion.Usuario;

/**
 * Created by user on 8/05/2017.
 */

public class AmigosAtributos extends Usuario{

    private String type_mensaje;

    public AmigosAtributos() {
    }

    public AmigosAtributos(String id, String nombreCompleto, String mensaje, String hora, int fotoPerfil, String type_mensaje) {
        super(id, nombreCompleto, mensaje, hora, fotoPerfil);
        this.type_mensaje = type_mensaje;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }
}
