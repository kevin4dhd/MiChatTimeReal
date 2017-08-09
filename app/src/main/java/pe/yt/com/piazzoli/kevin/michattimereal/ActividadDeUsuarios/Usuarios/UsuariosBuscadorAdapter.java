package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.Internet.SolicitudesJson;
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 26/05/2017.
 */

public class UsuariosBuscadorAdapter extends RecyclerView.Adapter<HolderUsuariosBuscador> {

    private List<UsuarioBuscadorAtributos> atributosList;
    private Context context;

    public UsuariosBuscadorAdapter(List<UsuarioBuscadorAtributos> atributosList, Context context) {
        this.atributosList = atributosList;
        this.context = context;
    }

    @Override
    public HolderUsuariosBuscador onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_buscar_usuarios,parent,false);
        return new HolderUsuariosBuscador(v);
    }

    @Override
    public void onBindViewHolder(HolderUsuariosBuscador holder, final int position) {
        holder.getFotoPerfil().setImageResource(atributosList.get(position).getFotoPerfil());
        holder.getNameUsuario().setText(atributosList.get(position).getNombreCompleto());
        holder.getEstadoUsuario().setText(""+atributosList.get(position).getEstado());

        /*holder.getEnviarSolicitud().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuarioEmisor = Preferences.obtenerPreferenceString(context,Preferences.PREFERENCE_USUARIO_LOGIN);
                String receptor = atributosList.get(position).getId();

                SolicitudesJson s = new SolicitudesJson() {
                    @Override
                    public void solicitudCompletada(JSONObject j) {
                        Toast.makeText(context, "La solicitud se envio correctamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void solicitudErronea() {
                        Toast.makeText(context, "Ocurrio un error al enviar la solicitud de amistad", Toast.LENGTH_SHORT).show();
                    }
                };
                HashMap<String,String> hs = new HashMap<>();
                hs.put("emisor",usuarioEmisor);
                hs.put("receptor",receptor);
                s.solicitudJsonPOST(context,SolicitudesJson.URL_ENVIAR_SOLICITUD,hs);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }
}
