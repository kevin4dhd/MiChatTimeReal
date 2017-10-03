package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Amigos.AmigosAtributos;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Amigos.EliminarFragmentAmigos;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.ClasesComunicacion.Usuario;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Solicitudes.SolicitudFragmentSolicitudes;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Solicitudes.Solicitudes;
import pe.yt.com.piazzoli.kevin.michattimereal.Internet.SolicitudesJson;
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 26/05/2017.
 */

public class FragmentUsuarios extends Fragment {

    private List<UsuarioBuscadorAtributos> atributosList;//conectada con nuestro adaptor
    private List<UsuarioBuscadorAtributos> listAuxiliar; //lista con todo los elementos estaticas
    private RecyclerView rv;
    private UsuariosBuscadorAdapter adapter;
    private EditText search;

    private LinearLayout layoutVacio;

    //private static final String URL_GET_ALL_USUARIOS = "http://kevinandroidkap.pe.hu/ArchivosPHP/Usuarios_GETALL.php";

    private EventBus bus = EventBus.getDefault();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buscar_usuarios,container,false);

        atributosList = new ArrayList<>();
        listAuxiliar = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.rvUsuarios);
        search = (EditText) v.findViewById(R.id.searchUsuarios);
        layoutVacio = (LinearLayout) v.findViewById(R.id.layoutVacio);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter = new UsuariosBuscadorAdapter(atributosList,getContext(),this);
        rv.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscador(""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verificarSiExisteEsaPersona();

        return v;
    }

    public void verificarSiExisteEsaPersona(){
        if(atributosList.isEmpty()){
            layoutVacio.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else{
            layoutVacio.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    //nombre
    //estadoUsuario
    //id
    public void insertarUsuario(int fotoPerfil,String nombre,int estadoUsuario,String id){
        UsuarioBuscadorAtributos buscadorAtributos = new UsuarioBuscadorAtributos();
        buscadorAtributos.setFotoPerfil(fotoPerfil);
        buscadorAtributos.setNombreCompleto(nombre);
        buscadorAtributos.setEstado(estadoUsuario);
        buscadorAtributos.setId(id);
        atributosList.add(buscadorAtributos);
        listAuxiliar.add(buscadorAtributos);
        adapter.notifyDataSetChanged();
        verificarSiExisteEsaPersona();
    }

    public void insertarUsuario(UsuarioBuscadorAtributos b){
        atributosList.add(b);
        listAuxiliar.add(b);
        adapter.notifyDataSetChanged();
        verificarSiExisteEsaPersona();
    }

    public void buscador(String texto){
        atributosList.clear();
        for(int i = 0;i<listAuxiliar.size();i++){
            if(listAuxiliar.get(i).getNombreCompleto().toLowerCase().contains(texto.toLowerCase())){
                atributosList.add(listAuxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
        verificarSiExisteEsaPersona();
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
                            insertarUsuario(R.drawable.ic_account_circle,js.getString("nombre"),1,js.getString("id"));
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

    @Subscribe
    public void ejecutarLLamada(UsuarioBuscadorAtributos b){
        insertarUsuario(b);
    }

    @Subscribe
    public void cancelarSolicitud(SolicitudFragmentUsuarios b){
        cambiarEstado(b.getId(),1);
    }

    @Subscribe
    public void aceptarSolicitud(AceptarSolicitudFragmentUsuarios a){
        cambiarEstado(a.getId(),4);
    }

    @Subscribe
    public void eliminarUsuario(EliminarAmigoFragmentUsuarios e){
        cambiarEstado(e.getId(),1);
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

    public void enviarSolicitud(final String id){
        String usuarioEmisor = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);

        SolicitudesJson s = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("respuesta");
                    if(respuesta.equals("200")){
                        //solicitud realizada correctamente
                        Toast.makeText(getContext(), "La solicitud se envio correctamente", Toast.LENGTH_SHORT).show();
                        int estado = j.getInt("estado");
                        String nombreCompleto = j.getString("nombreCompleto");
                        String hora = j.getString("hora").split(",")[0];

                        Solicitudes s = new Solicitudes();
                        s.setId(id);
                        s.setEstado(estado);
                        s.setNombreCompleto(nombreCompleto);
                        s.setHora(hora);
                        s.setFotoPerfil(R.drawable.ic_account_circle);
                        bus.post(s);
                        cambiarEstado(id,2);
                    }else if(respuesta.equals("-1")){
                        //solicitud fallida
                        Toast.makeText(getContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {
                Toast.makeText(getContext(), "Ocurrio un error al enviar la solicitud de amistad", Toast.LENGTH_SHORT).show();
            }
        };
        HashMap<String,String> hs = new HashMap<>();
        hs.put("emisor",usuarioEmisor);
        hs.put("receptor",id);
        s.solicitudJsonPOST(getContext(),SolicitudesJson.URL_ENVIAR_SOLICITUD,hs);
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
                        cambiarEstado(id,1);
                        bus.post(new SolicitudFragmentSolicitudes(id));
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

    public void aceptarSolicitud(final String id){

        String usuarioEmisor = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);

        SolicitudesJson s = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("respuesta");
                    if(respuesta.equals("200")){
                        //solicitud realizada correctamente
                        cambiarEstado(id,4);//cambia el estado en buscador
                        bus.post(new SolicitudFragmentSolicitudes(id));//elimina la tarjeta
                        AmigosAtributos a = new AmigosAtributos();//crear nueva tarjeta para el fragment de amigos
                        a.setId(id);
                        a.setNombreCompleto(j.getString("nombreCompleto"));
                        a.setFotoPerfil(R.drawable.ic_account_circle);
                        a.setMensaje(j.getString("UltimoMensaje"));
                        a.setHora(j.getString("hora"));
                        a.setType_mensaje(j.getString("type_mensaje"));
                        bus.post(a);
                    }else if(respuesta.equals("-1")){
                        //solicitud fallida
                        Toast.makeText(getContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {
                Toast.makeText(getContext(), "Ocurrio un error al enviar la solicitud de amistad", Toast.LENGTH_SHORT).show();
            }
        };
        HashMap<String,String> hs = new HashMap<>();
        hs.put("emisor",usuarioEmisor);
        hs.put("receptor",id);
        s.solicitudJsonPOST(getContext(),SolicitudesJson.URL_ACEPTAR_SOLICITUD,hs);
    }

    public void eliminarUsuario(final String id){
        String usuarioEmisor = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);

        SolicitudesJson s = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("respuesta");
                    if(respuesta.equals("200")){
                        cambiarEstado(id,1);
                        bus.post(new EliminarFragmentAmigos(id));
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

    private void cambiarEstado(String id,int estado){
        for(int i=0;i<listAuxiliar.size();i++){
            if(listAuxiliar.get(i).getId().equals(id)){
                listAuxiliar.get(i).setEstado(estado);
                break;
            }
        }
        int posUsuario = -1;
        for(int i=0;i<atributosList.size();i++){
            if(atributosList.get(i).getId().equals(id)){
                atributosList.get(i).setEstado(estado);
                posUsuario = i;
                break;
            }
        }

        if(posUsuario!=-1){
            adapter.notifyItemChanged(posUsuario);
        }else{
            Toast.makeText(getContext(), "No se pudo cambiar el estado", Toast.LENGTH_SHORT).show();
        }
    }

    private Usuario obtenerUsuarioPorId(String id){
        for(int i=0;i<atributosList.size();i++){
            if(atributosList.get(i).getId().equals(id)){
                return atributosList.get(i);
            }
        }
        return null;
    }

}
