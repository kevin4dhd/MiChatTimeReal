package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios;

import android.support.v7.widget.CardView;
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

    private CardView cardViewBuscador;
    private ImageView fotoPerfil;
    private TextView nameUsuario;
    private TextView estadoUsuario;
    private Button buttonDerecho;
    private Button buttonIzquierdo;

    public HolderUsuariosBuscador(View itemView) {
        super(itemView);
        fotoPerfil = (ImageView) itemView.findViewById(R.id.fotoDePerfilSolicitud);
        nameUsuario = (TextView) itemView.findViewById(R.id.nombreUsuario);
        estadoUsuario = (TextView) itemView.findViewById(R.id.estadoUsuario);
        buttonDerecho = (Button) itemView.findViewById(R.id.buttonDerecho);
        buttonIzquierdo = (Button) itemView.findViewById(R.id.buttonIzquierdo);
        cardViewBuscador = (CardView) itemView.findViewById(R.id.cardViewBuscador);
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

    public Button getButtonDerecho() {
        return buttonDerecho;
    }

    public void setButtonDerecho(Button buttonDerecho) {
        this.buttonDerecho = buttonDerecho;
    }

    public Button getButtonIzquierdo() {
        return buttonIzquierdo;
    }

    public void setButtonIzquierdo(Button buttonIzquierdo) {
        this.buttonIzquierdo = buttonIzquierdo;
    }

    public CardView getCardViewBuscador() {
        return cardViewBuscador;
    }

    public void setCardViewBuscador(CardView cardViewBuscador) {
        this.cardViewBuscador = cardViewBuscador;
    }
}
