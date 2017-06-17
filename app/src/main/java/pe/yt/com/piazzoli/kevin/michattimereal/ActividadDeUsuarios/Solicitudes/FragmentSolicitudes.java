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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.ClasesComunicacion.Prueba;
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;
import pe.yt.com.piazzoli.kevin.michattimereal.VolleyRP;

/**
 * Created by user on 16/05/2017.
 */

public class FragmentSolicitudes extends Fragment {

    private RecyclerView rv;
    private SolicitudesAdapter adapter;
    private List<Solicitudes> listSolicitudes;
    private LinearLayout layoutSinSolicitudes;

    private EventBus bus = EventBus.getDefault();

    private VolleyRP volley;
    private RequestQueue mRequest;

    private static final String URL_GET_ALL_USUARIOS = "http://kevinandroidkap.pe.hu/ArchivosPHP/Amigos_GETALL.php?id=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitud_amistad,container,false);

        listSolicitudes = new ArrayList<>();
        volley = VolleyRP.getInstance(getContext());
        mRequest = volley.getRequestQueue();

        rv = (RecyclerView) v.findViewById(R.id.cardviewSolicitudes);
        layoutSinSolicitudes = (LinearLayout) v.findViewById(R.id.layoutVacioSolicitudes);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new SolicitudesAdapter(listSolicitudes,getContext());
        rv.setAdapter(adapter);

        /*for(int i=0;i<10;i++){
            agregarTarjetasDeSolicitud(R.drawable.ic_account_circle,"usuario "+i,"00:00");
        }*/

        String usuario = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);
        SolicitudJSON("http://kevinandroidkap.pe.hu/ArchivosPHP/Amigos_GETALL.php?id="+usuario);
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


    //nombre
    //hora
    //id
    public void agregarTarjetasDeSolicitud(int fotoPerfil,String id,String nombre, String hora){
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setFotoPerfil(fotoPerfil);
        solicitudes.setNombre(nombre);
        solicitudes.setHora(hora);
        solicitudes.setId(id);
        listSolicitudes.add(solicitudes);
        actualizarTarjetas();
    }

    public void actualizarTarjetas(){
        adapter.notifyDataSetChanged();
        verificarSiTenemosSolicitudes();
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Subscribe
    public void ejecutarLLamada(Prueba b){
        agregarTarjetasDeSolicitud(R.drawable.ic_account_circle,"nulo",b.getNombre(),"00:00");
    }

    public void SolicitudJSON(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String TodosLosDatos = datos.getString("resultado");
                    JSONArray jsonArray = new JSONArray(TodosLosDatos);
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        agregarTarjetasDeSolicitud(R.drawable.ic_account_circle,"",jsonObject.getString("nombre")+" "+jsonObject.getString("apellidos"),
                                jsonObject.getString("fecha_amigos"));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Ocurrio un error al descomponer el JSON",Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Ocurrio un error, por favor contactese con el administrador",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,getContext(),volley);
    }

}
