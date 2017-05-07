package pe.yt.com.piazzoli.kevin.michattimereal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pe.yt.com.piazzoli.kevin.michattimereal.Mensajes.Mensajeria;

/**
 * Created by user on 6/05/2017.
 */

public class Registro extends AppCompatActivity {

    private static final String IP_REGISTRAR = "http://kevinandroidkap.pe.hu/ArchivosPHP/Registro_INSERT.php";

    private EditText user;
    private EditText password;
    private EditText nombre;
    private EditText apellidos;
    private EditText dia;
    private EditText mes;
    private EditText año;
    private EditText correo;
    private EditText telefono;
    private Button registro;

    private RadioButton rdHombre;
    private RadioButton rdMujer;

    private VolleyRP volley;
    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        user = (EditText) findViewById(R.id.userRegister);
        password = (EditText) findViewById(R.id.passwordRegistro);
        nombre = (EditText) findViewById(R.id.nombreRegistro);
        apellidos = (EditText) findViewById(R.id.apellidosRegistro);
        dia = (EditText) findViewById(R.id.diaRegistro);
        mes = (EditText) findViewById(R.id.mesRegistro);
        año = (EditText) findViewById(R.id.añoRegistro);
        correo = (EditText) findViewById(R.id.correoRegistro);
        telefono = (EditText) findViewById(R.id.telefonoRegistro);

        rdHombre = (RadioButton) findViewById(R.id.RDhombre);
        rdMujer = (RadioButton) findViewById(R.id.RDmujer);

        rdHombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdMujer.setChecked(false);
            }
        });

        rdMujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdHombre.setChecked(false);
            }
        });

        registro = (Button) findViewById(R.id.buttonRegistro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String genero = "";

                if (rdHombre.isChecked()) genero = "hombre";
                else if (rdMujer.isChecked()) genero = "mujer";

                registrarWebService(
                        getStringET(user),
                        getStringET(password),
                        getStringET(nombre),
                        getStringET(apellidos),
                        getStringET(dia) + "/" + getStringET(mes) + "/" + getStringET(año),
                        getStringET(correo),
                        getStringET(telefono),
                        genero);
            }
        });
    }

    private void registrarWebService(String usuario,String contraseña,String nombre,String apellido,String fechaNacimiento,String correo,String numero, String genero){
        HashMap<String,String> hashMapToken = new HashMap<>();
        hashMapToken.put("id",usuario);
        hashMapToken.put("nombre",nombre);
        hashMapToken.put("apellidos",apellido);
        hashMapToken.put("fecha_nacimiento",fechaNacimiento);
        hashMapToken.put("correo",correo);
        hashMapToken.put("genero",genero);
        hashMapToken.put("telefono",numero);
        hashMapToken.put("password",contraseña);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_REGISTRAR,new JSONObject(hashMapToken), new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String estado = datos.getString("resultado");
                    if(estado.equalsIgnoreCase("El usuario se registro correctamente")){
                        Toast.makeText(Registro.this,estado, Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(Registro.this,estado, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Registro.this,"No se pudo registrar",Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registro.this,"No se pudo registrar",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,this,volley);
    }


    private String getStringET(EditText e){
        return e.getText().toString();
    }

}
