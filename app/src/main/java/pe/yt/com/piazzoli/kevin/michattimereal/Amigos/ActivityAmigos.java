package pe.yt.com.piazzoli.kevin.michattimereal.Amigos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 8/05/2017.
 */

public class ActivityAmigos extends AppCompatActivity {

    private RecyclerView rv;
    private List<AmigosAtributos> atributosList;
    private AmigosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);
        setTitle("Amigos");

        atributosList = new ArrayList<>();

        rv = (RecyclerView) findViewById(R.id.amigosRecyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

        adapter = new AmigosAdapter(atributosList,this);
        rv.setAdapter(adapter);

        for(int i=0;i<10;i++){
            agregarAmigo(R.drawable.prueba,"usuario "+i,"mensaje "+i,"00:00");
        }

    }

    public void agregarAmigo(int fotoDePerfil,String nombre,String ultimoMensaje, String hora){
        AmigosAtributos amigosAtributos = new AmigosAtributos();
        amigosAtributos.setFotoDePerfil(fotoDePerfil);
        amigosAtributos.setNombre(nombre);
        amigosAtributos.setUltimoMensaje(ultimoMensaje);
        amigosAtributos.setHora(hora);
        atributosList.add(amigosAtributos);
        adapter.notifyDataSetChanged();
    }
}
