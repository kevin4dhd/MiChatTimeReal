package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios.Solicitudes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 16/05/2017.
 */

public class FragmentSolicitudes extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitud_amistad,container,false);
        return v;
    }
}
