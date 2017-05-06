package pe.yt.com.piazzoli.kevin.michattimereal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 6/05/2017.
 */

public class Registro extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private EditText nombre;
    private EditText apellidos;
    private EditText dia;
    private EditText mes;
    private EditText año;
    private EditText correo;
    private Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        user = (EditText) findViewById(R.id.userRegister);
        password = (EditText) findViewById(R.id.passwordRegistro);
        nombre = (EditText) findViewById(R.id.nombreRegistro);
        apellidos = (EditText) findViewById(R.id.apellidosRegistro);
        dia = (EditText) findViewById(R.id.diaRegistro);
        mes = (EditText) findViewById(R.id.mesRegistro);
        año = (EditText) findViewById(R.id.añoRegistro);
        correo = (EditText) findViewById(R.id.correoRegistro);

        registro = (Button) findViewById(R.id.buttonRegistro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Registro.this,
                                        "El usuario es: "+getStringET(user)+
                                        " La contraseña es: "+getStringET(password)+
                                        " El nombre es: "+getStringET(nombre)+
                                        " El apellido es: "+getStringET(apellidos)+
                                        " El dia es: "+getStringET(dia)+
                                        " El mes es: "+getStringET(mes)+
                                        " El año es: "+getStringET(año)+
                                        " El correo es: "+getStringET(correo), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getStringET(EditText e){
        return e.getText().toString();
    }

}
