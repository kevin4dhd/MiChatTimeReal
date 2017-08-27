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

import java.util.ArrayList;
import java.util.List;

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

    public void enviarSolicitud(String id){
        cambiarEstado(id,2);
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

        if(posUsuario!=1){
            adapter.notifyItemChanged(posUsuario);
        }else{
            Toast.makeText(getContext(), "No se pudo cambiar el estado", Toast.LENGTH_SHORT).show();
        }
    }

}
