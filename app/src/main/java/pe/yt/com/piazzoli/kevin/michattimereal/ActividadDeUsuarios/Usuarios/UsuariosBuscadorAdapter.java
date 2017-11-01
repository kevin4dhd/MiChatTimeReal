package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 26/05/2017.
 */

public class UsuariosBuscadorAdapter extends RecyclerView.Adapter<HolderUsuariosBuscador> {

    private List<UsuarioBuscadorAtributos> atributosList;
    private Context context;
    private FragmentUsuarios f;

    public UsuariosBuscadorAdapter(List<UsuarioBuscadorAtributos> atributosList, Context context,FragmentUsuarios f) {
        this.atributosList = atributosList;
        this.context = context;
        this.f = f;
    }

    @Override
    public HolderUsuariosBuscador onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_buscar_usuarios,parent,false);
        return new HolderUsuariosBuscador(v);
    }

    @Override
    public void onBindViewHolder(HolderUsuariosBuscador holder, final int position) {
        Picasso.with(context).load(atributosList.get(position).getFotoPerfil()).error(R.drawable.ic_account_circle).into(holder.getFotoPerfil());
        holder.getNameUsuario().setText(atributosList.get(position).getNombreCompleto());
        switch (atributosList.get(position).getEstado()){
            case 1://no son amigos ni tienen solicitudes de amistad
                holder.getButtonDerecho().setHint("Enviar Solicitud");
                holder.getButtonIzquierdo().setVisibility(View.GONE);//invisible

                holder.getButtonDerecho().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //evento de click al enviar solicitud
                        f.enviarSolicitud(atributosList.get(position).getId());
                        Toast.makeText(context, "Enviando solicitud...", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.getCardViewBuscador().setOnLongClickListener(null);

                holder.getButtonDerecho().setHintTextColor(ContextCompat.getColor(context, R.color.colorBlue));

                break;
            case 2://solicitud pendiente a que el usuario receptor acepte
                holder.getButtonIzquierdo().setVisibility(View.GONE);//invisible
                holder.getButtonDerecho().setHint("Cancelar");

                holder.getButtonDerecho().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        f.cancelarSolicitud(atributosList.get(position).getId());
                        Toast.makeText(context, "Cancelando solicitud...", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.getCardViewBuscador().setOnLongClickListener(null);

                holder.getButtonDerecho().setHintTextColor(ContextCompat.getColor(context, R.color.colorRed));

                break;
            case 3://solicitud pendiente a aceptar
                holder.getButtonIzquierdo().setVisibility(View.VISIBLE);//visible
                holder.getButtonDerecho().setHint("Cancelar");
                holder.getButtonIzquierdo().setHint("Aceptar");

                holder.getButtonIzquierdo().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        f.aceptarSolicitud(atributosList.get(position).getId());
                        Toast.makeText(context, "Aceptando solicitud", Toast.LENGTH_SHORT).show();
                    }
                });

                holder.getButtonDerecho().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        f.cancelarSolicitud(atributosList.get(position).getId());
                        Toast.makeText(context, "Cancelando solicitud", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.getCardViewBuscador().setOnLongClickListener(null);

                holder.getButtonDerecho().setHintTextColor(ContextCompat.getColor(context, R.color.colorRed));

                break;
            case 4://somos amigos
                holder.getButtonIzquierdo().setVisibility(View.GONE);//invisible
                holder.getButtonDerecho().setHint("Ver Perfil");

                holder.getButtonDerecho().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Ver Perfil", Toast.LENGTH_SHORT).show();
                    }
                });

                holder.getCardViewBuscador().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(context).
                                setMessage("¿Estas seguro que quieres eliminar a este amigo?").
                                setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        f.eliminarUsuario(atributosList.get(position).getId());
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

                holder.getButtonDerecho().setHintTextColor(ContextCompat.getColor(context, R.color.colorBlue));

                break;
        }
        holder.getEstadoUsuario().setText(""+atributosList.get(position).getEstado());

    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }
}
