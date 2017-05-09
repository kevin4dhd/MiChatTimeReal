package pe.yt.com.piazzoli.kevin.michattimereal.Amigos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.Mensajes.MensajeriaAdapter;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 8/05/2017.
 */

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.HolderAmigos> {

    private List<AmigosAtributos> atributosList;
    private Context context;

    public AmigosAdapter(List<AmigosAtributos> atributosList,Context context){
        this.atributosList = atributosList;
        this.context = context;
    }

    @Override
    public HolderAmigos onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_amigos,parent,false);
        return new AmigosAdapter.HolderAmigos(v);
    }

    @Override
    public void onBindViewHolder(HolderAmigos holder, int position) {
        holder.imageView.setImageResource(atributosList.get(position).getFotoDePerfil());
        holder.nombre.setText(atributosList.get(position).getNombre());
        holder.mensaje.setText(atributosList.get(position).getUltimoMensaje());
        holder.hora.setText(atributosList.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }

    static class HolderAmigos extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;
        TextView nombre;
        TextView mensaje;
        TextView hora;

        public HolderAmigos(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.fotoDePerfilAmigos);
            nombre = (TextView) itemView.findViewById(R.id.nombreUsuarioAmigo);
            mensaje = (TextView) itemView.findViewById(R.id.mensajeAmigos);
            hora = (TextView) itemView.findViewById(R.id.horaAmigos);
        }
    }

}
