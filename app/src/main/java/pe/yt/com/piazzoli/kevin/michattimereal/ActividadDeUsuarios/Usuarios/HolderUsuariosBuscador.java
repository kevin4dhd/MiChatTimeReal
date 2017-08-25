package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 26/05/2017.
 */

public class HolderUsuariosBuscador extends RecyclerView.ViewHolder {

    private ImageView fotoPerfil;
    private TextView nameUsuario;
    private TextView estadoUsuario;
    private Button enviarSolicitud;
    private Button buttonEstado3;

    public HolderUsuariosBuscador(View itemView) {
        super(itemView);
        fotoPerfil = (ImageView) itemView.findViewById(R.id.fotoDePerfilSolicitud);
        nameUsuario = (TextView) itemView.findViewById(R.id.nombreUsuario);
        estadoUsuario = (TextView) itemView.findViewById(R.id.estadoUsuario);
        enviarSolicitud = (Button) itemView.findViewById(R.id.enviarSolicitud);
        buttonEstado3 = (Button) itemView.findViewById(R.id.buttonEstado3);
    }

    public ImageView getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(ImageView fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public TextView getNameUsuario() {
        return nameUsuario;
    }

    public void setNameUsuario(TextView nameUsuario) {
        this.nameUsuario = nameUsuario;
    }

    public TextView getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(TextView estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public Button getEnviarSolicitud() {
        return enviarSolicitud;
    }

    public void setEnviarSolicitud(Button enviarSolicitud) {
        this.enviarSolicitud = enviarSolicitud;
    }

    public Button getButtonEstado3() {
        return buttonEstado3;
    }

    public void setButtonEstado3(Button buttonEstado3) {
        this.buttonEstado3 = buttonEstado3;
    }
}
