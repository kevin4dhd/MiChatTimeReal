package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Amigos;

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
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios.EliminarAmigoFragmentUsuarios;
import pe.yt.com.piazzoli.kevin.michattimereal.Internet.SolicitudesJson;
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 8/05/2017.
 */

public class FragmentAmigos extends Fragment {

    private RecyclerView rv;
    private List<AmigosAtributos> atributosList;
    private AmigosAdapter adapter;
    private LinearLayout layoutVacio;

    //private static final String URL_GET_ALL_USUARIOS = "http://kevinandroidkap.pe.hu/ArchivosPHP/Usuarios_GETALL.php";

    private EventBus bus = EventBus.getDefault();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_amigos,container,false);

        atributosList = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.amigosRecyclerView);
        layoutVacio = (LinearLayout) v.findViewById(R.id.layoutVacio);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new AmigosAdapter(atributosList,getContext(),this);
        rv.setAdapter(adapter);
        //SolicitudJSON();

        verificarSiTenemosAmigos();

        return v;
    }

    public void verificarSiTenemosAmigos(){
        if(atributosList.isEmpty()){
            layoutVacio.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else{
            layoutVacio.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    private void actualizarTarjetas(){
        adapter.notifyDataSetChanged();
        verificarSiTenemosAmigos();
    }

    //id
    //nombre
    //ultimo_mensaje
    //hora
    public void agregarAmigo(int fotoDePerfil, String nombre, String ultimoMensaje, String hora,String id){
        AmigosAtributos amigosAtributos = new AmigosAtributos();
        amigosAtributos.setFotoPerfil(fotoDePerfil);
        amigosAtributos.setNombreCompleto(nombre);
        amigosAtributos.setMensaje(ultimoMensaje);
        amigosAtributos.setHora(hora);
        amigosAtributos.setId(id);
        atributosList.add(amigosAtributos);
        adapter.notifyDataSetChanged();
        verificarSiTenemosAmigos();
    }

    public void agregarAmigo(AmigosAtributos a){
        atributosList.add(0,a);
        adapter.notifyDataSetChanged();
        verificarSiTenemosAmigos();
    }

    public void SolicitudJSON(){
        /*JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String TodosLosDatos = datos.getString("resultado");
                    JSONArray jsonArray = new JSONArray(TodosLosDatos);
                    String NuestroUsuario = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        if(!js.getString("id").equals(NuestroUsuario)){
                            agregarAmigo(R.drawable.prueba,js.getString("nombre"),"mensaje "+i,"00:00",js.getString("id"));
                        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ejecutarLLamada(AmigosAtributos a){
        agregarAmigo(a);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eliminarAmigo(EliminarFragmentAmigos a){
        for(int i=0;i<atributosList.size();i++){
            if(atributosList.get(i).getId().equals(a.getId())){
                atributosList.remove(i);
                actualizarTarjetas();
            }
        }
    }

    public void eliminarAmigo(final String id){

        String usuarioEmisor = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);

        SolicitudesJson s = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("respuesta");
                    if(respuesta.equals("200")){
                        bus.post(new EliminarAmigoFragmentUsuarios(id));
                        for(int i=0;i<atributosList.size();i++){
                            if(atributosList.get(i).getId().equals(id)){
                                atributosList.remove(i);
                                actualizarTarjetas();
                            }
                        }
                    }else if(respuesta.equals("-1")){
                        //solicitud fallida
                        Toast.makeText(getContext(), "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {
                Toast.makeText(getContext(), "Ocurrio un error al eliminar el usuario", Toast.LENGTH_SHORT).show();
            }
        };
        HashMap<String,String> hs = new HashMap<>();
        hs.put("emisor",usuarioEmisor);
        hs.put("receptor",id);
        s.solicitudJsonPOST(getContext(),SolicitudesJson.URL_ELIMINAR_USUARIO,hs);
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
}
