package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Amigos.FragmentAmigos;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Solicitudes.FragmentSolicitudes;
import pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Usuarios.FragmentUsuarios;

/**
 * Created by user on 14/05/2017.
 */

public class AdapterUsuarios extends FragmentPagerAdapter {


    public AdapterUsuarios(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) return new FragmentAmigos();
        else if(position==1) return new FragmentSolicitudes();
        else if(position==2) return new FragmentUsuarios();
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return "Chat";
        else if(position==1)return "Solicitudes";
        else if(position==2)return "Buscar";
        return null;
    }
}
