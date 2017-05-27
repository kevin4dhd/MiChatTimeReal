package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 26/05/2017.
 */

public class FragmentUsuarios extends Fragment {

    private List<UsuarioBuscadorAtributos> atributosList;
    private RecyclerView rv;
    private UsuariosBuscadorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buscar_usuarios,container,false);

        atributosList = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.rvUsuarios);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter = new UsuariosBuscadorAdapter(atributosList,getContext());
        rv.setAdapter(adapter);

        for(int i =0;i<10;i++){
            insertarUsuario(R.drawable.ic_account_circle,"Nombre "+i,"Estado "+i);
        }

        return v;
    }

    public void insertarUsuario(int fotoPerfil,String nombre,String estadoUsuario){
        UsuarioBuscadorAtributos buscadorAtributos = new UsuarioBuscadorAtributos();
        buscadorAtributos.setFotoPerfil(fotoPerfil);
        buscadorAtributos.setNombre(nombre);
        buscadorAtributos.setEstadoUsuario(estadoUsuario);
        atributosList.add(buscadorAtributos);
        adapter.notifyDataSetChanged();
    }

}
