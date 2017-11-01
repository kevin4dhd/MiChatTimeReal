package pe.yt.com.piazzoli.kevin.michattimereal.Imagen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 31/10/2017. 31
 */

public class ActivitySubirImagen extends AppCompatActivity {

    private ImageView fotoPerfil;
    private String URL_USER_FOTO_PERFIL = "http://kevinandroidkap.pe.hu/Imagenes/error.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);
        fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle!=null){
            URL_USER_FOTO_PERFIL = bundle.getString("imagen");
        }
        Picasso.with(this).load(URL_USER_FOTO_PERFIL).error(R.drawable.ic_account_circle).into(fotoPerfil);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
