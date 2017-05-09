package pe.yt.com.piazzoli.kevin.michattimereal.Mensajes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.Login;
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;
import pe.yt.com.piazzoli.kevin.michattimereal.Registro;
import pe.yt.com.piazzoli.kevin.michattimereal.Services.FireBaseId;
import pe.yt.com.piazzoli.kevin.michattimereal.VolleyRP;

/**
 * Created by Yomaira on 03/01/2017.
 */

public class Mensajeria extends AppCompatActivity {

    public static final String MENSAJE = "MENSAJE";

    private BroadcastReceiver bR;

    private RecyclerView rv;
    private Button bTEnviarMensaje;
    private EditText eTEscribirMensaje;
    private EditText eTRECEPTOR;
    private List<MensajeDeTexto> mensajeDeTextos;
    private MensajeriaAdapter adapter;

    private Button cerrarSesion;

    private String MENSAJE_ENVIAR = "";
    private String EMISOR = "";
    private String RECEPTOR;

    private static final String IP_MENSAJE = "http://kevinandroidkap.pe.hu/ArchivosPHP/Enviar_Mensajes.php";

    private VolleyRP volley;
    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        mensajeDeTextos = new ArrayList<>();

        EMISOR = Preferences.obtenerPreferenceString(this,Preferences.PREFERENCE_USUARIO_LOGIN);

        Toast.makeText(this, EMISOR, Toast.LENGTH_SHORT).show();

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bTEnviarMensaje = (Button) findViewById(R.id.bTenviarMensaje);
        eTEscribirMensaje = (EditText) findViewById(R.id.eTEsribirMensaje);
        eTRECEPTOR = (EditText) findViewById(R.id.receptorET);

        cerrarSesion = (Button) findViewById(R.id.cerrarSesion);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.savePreferenceBoolean(Mensajeria.this,false,Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                Intent i = new Intent(Mensajeria.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        rv = (RecyclerView) findViewById(R.id.rvMensajes);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);//Mensajeria
        rv.setLayoutManager(lm);

        adapter = new MensajeriaAdapter(mensajeDeTextos,this);
        rv.setAdapter(adapter);

        bTEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = validarCadena(eTEscribirMensaje.getText().toString());
                RECEPTOR = eTRECEPTOR.getText().toString();
                if(!mensaje.isEmpty() && !RECEPTOR.isEmpty()){
                    MENSAJE_ENVIAR = mensaje;
                    MandarMensaje();
                    CreateMensaje(mensaje,"00:00",1);
                    eTEscribirMensaje.setText("");
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setScrollbarChat();

        bR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String mensaje = intent.getStringExtra("key_mensaje");
                String hora = intent.getStringExtra("key_hora");
                CreateMensaje(mensaje,hora,2);
            }
        };

    }

    //"   " NO ENVIAR
    //"  hola"=>"hola"
    //"      hola como estas" =>"hola como estas"
    private String validarCadena(String cadena){
        for(int i=0;i<cadena.length();i++) if(!(""+cadena.charAt(i)).equalsIgnoreCase(" ")) return cadena.substring(i,cadena.length());
        return "";
    }

    private void MandarMensaje(){
        HashMap<String,String> hashMapToken = new HashMap<>();
        hashMapToken.put("emisor",EMISOR);
        hashMapToken.put("receptor",RECEPTOR);
        hashMapToken.put("mensaje",MENSAJE_ENVIAR);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_MENSAJE,new JSONObject(hashMapToken), new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    Toast.makeText(Mensajeria.this,datos.getString("resultado"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e){}
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mensajeria.this,"Ocurrio un error",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,this,volley);
    }

    public void CreateMensaje(String mensaje,String hora,int tipoDeMensaje){
        MensajeDeTexto mensajeDeTextoAuxiliar = new MensajeDeTexto();
        mensajeDeTextoAuxiliar.setId("0");
        mensajeDeTextoAuxiliar.setMensaje(mensaje);
        mensajeDeTextoAuxiliar.setTipoMensaje(tipoDeMensaje);
        mensajeDeTextoAuxiliar.setHoraDelMensaje(hora);
        mensajeDeTextos.add(mensajeDeTextoAuxiliar);
        adapter.notifyDataSetChanged();
        setScrollbarChat();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(bR,new IntentFilter(MENSAJE));
    }

    public void setScrollbarChat(){
        rv.scrollToPosition(adapter.getItemCount()-1);
    }

}
