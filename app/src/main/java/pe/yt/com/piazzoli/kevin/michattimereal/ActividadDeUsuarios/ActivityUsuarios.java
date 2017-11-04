package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Amigos.AmigosAtributos;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Amigos.LimpiarListaAmigos;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Solicitudes.LimpiarListaSolicitudes;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Solicitudes.Solicitudes;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios.LimpiarListaUsuarios;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios.UsuarioBuscadorAtributos;
import pe.yt.com.piazzoli.kevin.michattimereal.Imagen.ActivitySubirImagen;
import pe.yt.com.piazzoli.kevin.michattimereal.Internet.SolicitudesJson;
import pe.yt.com.piazzoli.kevin.michattimereal.Login;
import pe.yt.com.piazzoli.kevin.michattimereal.Preferences;
import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 14/05/2017.
 */

public class ActivityUsuarios extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EventBus bus;
    private String URL_USER_FOTO_PERFIL = "http://kevinandroidkap.pe.hu/Imagenes/error.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Mensajeria");
        setContentView(R.layout.activity_usuarios);
        bus = EventBus.getDefault();
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutUsuarios);
        viewPager = (ViewPager) findViewById(R.id.viewPagerUsuarios);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new AdapterUsuarios(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    //setTitle("Chat");
                }else if(position==1){
                    //setTitle("Registro");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarSolicitudes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_amigos,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.NoCerrarSesionMenu){
            Preferences.savePreferenceBoolean(ActivityUsuarios.this,false,Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
            Intent i = new Intent(ActivityUsuarios.this,Login.class);
            startActivity(i);
            finish();
        }else if(id==R.id.subirFotoPerfil){
            Intent i = new Intent(ActivityUsuarios.this, ActivitySubirImagen.class);
            i.putExtra("imagen",URL_USER_FOTO_PERFIL);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void actualizarSolicitudes(){
        SolicitudesJson sj = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                bus.post(new LimpiarListaAmigos());
                bus.post(new LimpiarListaSolicitudes());
                bus.post(new LimpiarListaUsuarios());
                try {
                    JSONArray jA = j.getJSONArray("resultado");
                    for(int i=0;i<jA.length();i++){
                        JSONObject json = jA.getJSONObject(i);
                        String id = json.getString("id");
                        if(!id.equals(Preferences.obtenerPreferenceString(ActivityUsuarios.this,Preferences.PREFERENCE_USUARIO_LOGIN))) {
                            String nombreCompleto = json.getString("nombre") + " " + json.getString("apellidos");
                            String estado = json.getString("estado");
                            UsuarioBuscadorAtributos usuario = new UsuarioBuscadorAtributos();
                            usuario.setFotoPerfil(json.getString("imagen"));
                            usuario.setId(id);
                            usuario.setNombreCompleto(nombreCompleto);
                            usuario.setEstado(1);
                            Solicitudes s;
                            switch (estado) {
                                case "2"://solicitudes
                                    usuario.setEstado(2);
                                    s = new Solicitudes();
                                    s.setId(id);
                                    s.setNombreCompleto(nombreCompleto);
                                    s.setFotoPerfil(json.getString("imagen"));
                                    s.setHora(json.getString("fecha_amigos"));
                                    s.setEstado(2);
                                    bus.post(s);
                                    break;
                                case "3"://solicitudes
                                    usuario.setEstado(3);
                                    s = new Solicitudes();
                                    s.setId(id);
                                    s.setNombreCompleto(nombreCompleto);
                                    s.setFotoPerfil(json.getString("imagen"));
                                    s.setHora(json.getString("fecha_amigos"));
                                    s.setEstado(3);
                                    bus.post(s);
                                    break;
                                case "4"://amigos
                                    usuario.setEstado(4);
                                    AmigosAtributos a = new AmigosAtributos();
                                    a.setId(id);
                                    a.setNombreCompleto(nombreCompleto);
                                    a.setFotoPerfil(json.getString("imagen"));
                                    a.setMensaje(json.getString("mensaje"));
                                    a.setType_mensaje(json.getString("tipo_mensaje"));
                                    String hora_mensaje = json.getString("hora_del_mensaje");
                                    String hora_vector[] = hora_mensaje.split("\\,");
                                    a.setHora(hora_vector[0]);
                                    bus.post(a);
                                    break;
                            }
                            bus.post(usuario);
                        }else{
                            URL_USER_FOTO_PERFIL = json.getString("imagen");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void solicitudErronea() {
                Toast.makeText(ActivityUsuarios.this, "ocurrio un error al momento de pedir datos en activity usuarios", Toast.LENGTH_SHORT).show();
            }
        };

        String usuario = Preferences.obtenerPreferenceString(this,Preferences.PREFERENCE_USUARIO_LOGIN);
        sj.solicitudJsonGET(this,SolicitudesJson.URL_GET_ALL_DATOS+usuario);
    }

}
