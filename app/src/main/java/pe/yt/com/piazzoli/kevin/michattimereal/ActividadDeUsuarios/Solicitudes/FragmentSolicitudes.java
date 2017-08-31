package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Solicitudes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios.EliminarSolicitudFragmentUsuarios;
import pe.yt.com.piazzoli.kevin.michattimereal.Internet.SolicitudesJson;
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 16/05/2017.
 */

public class FragmentSolicitudes extends Fragment {

    private RecyclerView rv;
    private SolicitudesAdapter adapter;
    private List<Solicitudes> listSolicitudes;
    private LinearLayout layoutSinSolicitudes;

    private EventBus bus = EventBus.getDefault();

    //private static final String URL_GET_ALL_USUARIOS = "http://kevinandroidkap.pe.hu/ArchivosPHP/Amigos_GETALL.php?id=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitud_amistad,container,false);

        listSolicitudes = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.cardviewSolicitudes);
        layoutSinSolicitudes = (LinearLayout) v.findViewById(R.id.layoutVacio);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new SolicitudesAdapter(listSolicitudes,getContext(),this);
        rv.setAdapter(adapter);

        /*for(int i=0;i<10;i++){
            agregarTarjetasDeSolicitud(R.drawable.ic_account_circle,"usuario "+i,"00:00");
        }*/

        //String usuario = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);
        //SolicitudJSON("http://kevinandroidkap.pe.hu/ArchivosPHP/Amigos_GETALL.php?id="+usuario);
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
    public void agregarTarjetasDeSolicitud(int fotoPerfil,String id,String nombre, String hora,int estado){
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setFotoPerfil(fotoPerfil);
        solicitudes.setNombreCompleto(nombre);
        solicitudes.setHora(hora);
        solicitudes.setId(id);
        solicitudes.setEstado(estado);
        listSolicitudes.add(0,solicitudes);
        actualizarTarjetas();
    }

    public void agregarTarjetasDeSolicitud(Solicitudes solicitudes){
        listSolicitudes.add(0,solicitudes);
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
    public void ejecutarLLamada(Solicitudes b){
        agregarTarjetasDeSolicitud(b);
    }

    @Subscribe
    public void cancelarSolicitud(EliminarSolicitudFragmentSolicitudes e){
        eliminarTarjeta(e.getId());
    }

    public void cancelarSolicitud(final String id){
        String usuarioEmisor = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);
        SolicitudesJson s = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respueta = j.getString("respuesta");
                    if(respueta.equals("200")){
                        //se cancelo correctamente
                        eliminarTarjeta(id);
                        bus.post(new EliminarSolicitudFragmentUsuarios(id));
                        Toast.makeText(getContext(),j.getString("resultado"), Toast.LENGTH_SHORT).show();
                    }else if(respueta.equals("-1")){
                        //error al cancelar
                        Toast.makeText(getContext(),j.getString("resultado"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "No se pudo cancelar la solicitud", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {

            }
        };
        HashMap<String,String> h = new HashMap<>();
        h.put("emisor",usuarioEmisor);
        h.put("receptor",id);
        s.solicitudJsonPOST(getContext(),SolicitudesJson.URL_CANCELAR_SOLICITUD,h);
    }

    public void eliminarTarjeta(String id){
        for(int i=0;i<listSolicitudes.size();i++){
            if(listSolicitudes.get(i).getId().equals(id)){
                listSolicitudes.remove(i);
                actualizarTarjetas();
            }
        }
    }

    public void SolicitudJSON(String URL){
        /*JsonObjectRequest solicitud = new JsonObjectRequest(URL,null, new Response.Listener<JSONObject>(){
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
        VolleyRP.addToQueue(solicitud,mRequest,getContext(),volley);*/
    }

}
