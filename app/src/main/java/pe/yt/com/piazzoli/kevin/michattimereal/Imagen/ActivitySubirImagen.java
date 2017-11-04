package pe.yt.com.piazzoli.kevin.michattimereal.Imagen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;

import pe.yt.com.piazzoli.kevin.michattimereal.Internet.SolicitudesJson;
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 31/10/2017. 31
 */

public class ActivitySubirImagen extends AppCompatActivity {

    private ImageView fotoPerfil;
    private String URL_USER_FOTO_PERFIL = "http://kevinandroidkap.pe.hu/Imagenes/error.png";
    private FotoManagerGotev fotoManagerGotev;
    private Button btnGaleria;
    private Button btnCamara;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);
        fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);
        btnGaleria = (Button) findViewById(R.id.btnGaleria);
        btnCamara = (Button) findViewById(R.id.btnCamara);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) URL_USER_FOTO_PERFIL = bundle.getString("imagen");
        Picasso.with(this).load(URL_USER_FOTO_PERFIL).error(R.drawable.ic_account_circle).into(fotoPerfil);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fotoManagerGotev = new FotoManagerGotev(this, SolicitudesJson.URL_SUBIR_FOTO) {
            @Override
            public void onProgress(UploadInfo uploadInfo) {

            }

            @Override
            public void onError(UploadInfo uploadInfo, Exception exception) {

            }

            @Override
            public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {

            }

            @Override
            public void onCancelled(UploadInfo uploadInfo) {

            }
        };
        fotoManagerGotev.setParameterNamePhoto("file");
        fotoManagerGotev.setEliminarFoto(false);
        fotoManagerGotev.setIdUser(Preferences.obtenerPreferenceString(this,Preferences.PREFERENCE_USUARIO_LOGIN));

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoManagerGotev.subirFotoGaleria();
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoManagerGotev.subirFotoCamara();
            }
        });
        FotoManagerGotev.verifyStoragePermissions(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri u = fotoManagerGotev.onActivityResult(requestCode,resultCode,data);
        if(u!=null)fotoPerfil.setImageURI(u);
    }
}
