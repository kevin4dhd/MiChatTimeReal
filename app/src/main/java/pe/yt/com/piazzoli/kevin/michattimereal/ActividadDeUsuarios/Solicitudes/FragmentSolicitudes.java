package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Solicitudes;

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
 * Created by user on 16/05/2017.
 */

public class FragmentSolicitudes extends Fragment {

    private RecyclerView rv;
    private SolicitudesAdapter adapter;
    private List<Solicitudes> listSolicitudes;
    private LinearLayout layoutSinSolicitudes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitud_amistad,container,false);

        listSolicitudes = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.cardviewSolicitudes);
        layoutSinSolicitudes = (LinearLayout) v.findViewById(R.id.layoutVacioSolicitudes);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new SolicitudesAdapter(listSolicitudes,getContext());
        rv.setAdapter(adapter);

        /*for(int i=0;i<10;i++){
            agregarTarjetasDeSolicitud(R.drawable.ic_account_circle,"usuario "+i,"00:00");
        }*/

        verificarSiTenemosSolicitudes();

        return v;
    }

    public void verificarSiTenemosSolicitudes(){
        if(listSolicitudes.isEmpty()){
            layoutSinSolicitudes.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else{
            layoutSinSolicitudes.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }


    public void agregarTarjetasDeSolicitud(int fotoPerfil,String nombre, String hora){
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setFotoPerfil(fotoPerfil);
        solicitudes.setNombre(nombre);
        solicitudes.setHora(hora);
        listSolicitudes.add(solicitudes);
        actualizarTarjetas();
    }

    public void actualizarTarjetas(){
        adapter.notifyDataSetChanged();
        verificarSiTenemosSolicitudes();
    }

}
