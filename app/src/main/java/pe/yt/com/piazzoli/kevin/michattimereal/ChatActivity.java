package pe.yt.com.piazzoli.kevin.michattimereal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity {

    private EditText eTusuario;
    private EditText eTcontraseña;
    private Button bTingresar;

    private VolleyRP volley;
    private RequestQueue mRequest;

    private static String IP = "http://kevinandroidkap.pe.hu/ArchivosPHP/Login_GETID.php?id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        eTusuario = (EditText) findViewById(R.id.eTusuario);
        eTcontraseña = (EditText) findViewById(R.id.eTcontraseña);

        bTingresar = (Button) findViewById(R.id.bTingresar);

        bTingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerificarLogin(eTusuario.getText().toString().toLowerCase(),eTcontraseña.getText().toString().toLowerCase());
            }
        });
    }

    public void VerificarLogin(String user, String password){
        Toast.makeText(this,"El usuario es: "+user+" y la contraseña es: "+password,Toast.LENGTH_SHORT).show();
        SolicitudJSON(IP+user);
    }

    public void SolicitudJSON(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                VerficiarLogin(datos);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChatActivity.this,"Ocurrio un error, por favor contactese con el administrador",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,this,volley);
    }

    public void VerficiarLogin(JSONObject datos){
        //Controlar el json
    }

}
