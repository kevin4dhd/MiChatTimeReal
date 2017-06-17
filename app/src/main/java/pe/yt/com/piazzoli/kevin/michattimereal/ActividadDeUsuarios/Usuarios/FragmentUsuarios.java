package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.greenrobot.eventbus.EventBus;
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
 * Created by user on 26/05/2017.
 */

public class FragmentUsuarios extends Fragment {

    private List<UsuarioBuscadorAtributos> atributosList;//conectada con nuestro adaptor
    private List<UsuarioBuscadorAtributos> listAuxiliar; //lista con todo los elementos estaticas
    private RecyclerView rv;
    private UsuariosBuscadorAdapter adapter;
    private EditText search;

    private VolleyRP volley;
    private RequestQueue mRequest;

    private static final String URL_GET_ALL_USUARIOS = "http://kevinandroidkap.pe.hu/ArchivosPHP/Usuarios_GETALL.php";

    private EventBus bus = EventBus.getDefault();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buscar_usuarios,container,false);

        volley = VolleyRP.getInstance(getContext());
        mRequest = volley.getRequestQueue();

        atributosList = new ArrayList<>();
        listAuxiliar = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.rvUsuarios);
        search = (EditText) v.findViewById(R.id.searchUsuarios);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter = new UsuariosBuscadorAdapter(atributosList,getContext());
        rv.setAdapter(adapter);

        SolicitudJSON();

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

        return v;
    }

    //nombre
    //estadoUsuario
    //id
    public void insertarUsuario(int fotoPerfil,String nombre,String estadoUsuario,String id){
        UsuarioBuscadorAtributos buscadorAtributos = new UsuarioBuscadorAtributos();
        buscadorAtributos.setFotoPerfil(fotoPerfil);
        buscadorAtributos.setNombre(nombre);
        buscadorAtributos.setEstadoUsuario(estadoUsuario);
        buscadorAtributos.setId(id);
        atributosList.add(buscadorAtributos);
        listAuxiliar.add(buscadorAtributos);
        adapter.notifyDataSetChanged();
    }

    public void buscador(String texto){
        atributosList.clear();
        for(int i = 0;i<listAuxiliar.size();i++){
            if(listAuxiliar.get(i).getNombre().toLowerCase().contains(texto.toLowerCase())){
                atributosList.add(listAuxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void SolicitudJSON(){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String TodosLosDatos = datos.getString("resultado");
                    JSONArray jsonArray = new JSONArray(TodosLosDatos);
                    String NuestroUsuario = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        if(!js.getString("id").equals(NuestroUsuario)){
                            insertarUsuario(R.drawable.ic_account_circle,js.getString("nombre"),"no amigos",js.getString("id"));
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
        VolleyRP.addToQueue(solicitud,mRequest,getContext(),volley);
    }

}
