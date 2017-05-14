package pe.yt.com.piazzoli.kevin.michattimereal.ActividadDeUsuarios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import pe.yt.com.piazzoli.kevin.michattimereal.R;

/**
 * Created by user on 14/05/2017.
 */

public class ActivityUsuarios extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
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
                    setTitle("Login");
                }else if(position==1){
                    setTitle("Registro");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
