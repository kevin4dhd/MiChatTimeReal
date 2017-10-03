package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Amigos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.Mensajes.Mensajeria;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 8/05/2017.
 */

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.HolderAmigos> {

    private List<AmigosAtributos> atributosList;
    private Context context;
    private FragmentAmigos f;

    public AmigosAdapter(List<AmigosAtributos> atributosList,Context context,FragmentAmigos f){
        this.atributosList = atributosList;
        this.context = context;
        this.f=f;
    }

    @Override
    public HolderAmigos onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_amigos,parent,false);
        return new AmigosAdapter.HolderAmigos(v);
    }

    @Override
    public void onBindViewHolder(HolderAmigos holder, final int position) {
        holder.imageView.setImageResource(atributosList.get(position).getFotoPerfil());
        holder.nombre.setText(atributosList.get(position).getNombreCompleto());
        holder.mensaje.setText(atributosList.get(position).getMensaje());
        holder.hora.setText(atributosList.get(position).getHora());

        if(atributosList.get(position).getMensaje().equals("null")){
            holder.hora.setVisibility(View.GONE);
            holder.mensaje.setText("Enviale un mensaje!");
        }else{
            holder.hora.setVisibility(View.VISIBLE);

            if(atributosList.get(position).getType_mensaje().equals("1")){
                holder.mensaje.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            }else{
                holder.mensaje.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
            }

        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Mensajeria.class);
                i.putExtra("key_receptor",atributosList.get(position).getId());
                context.startActivity(i);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context).
                        setMessage("¿Estas seguro que quieres eliminar a este amigo?").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                f.eliminarAmigo(atributosList.get(position).getId());
                                Toast.makeText(context, "Se elimino el amigo correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(context, "Cancelando solicitud de eliminacion", Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return true;
            }
        });

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
            cardView = (CardView) itemView.findViewById(R.id.cardViewAmigos);
            imageView = (ImageView) itemView.findViewById(R.id.fotoDePerfilAmigos);
            nombre = (TextView) itemView.findViewById(R.id.nombreUsuarioAmigo);
            mensaje = (TextView) itemView.findViewById(R.id.mensajeAmigos);
            hora = (TextView) itemView.findViewById(R.id.horaAmigos);
        }
    }

}
