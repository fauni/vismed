package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.app.Login;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_helper;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeAuxiliar;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;


public class Splash extends Activity {

    Timer timer;
    String id_usuario;
    SharedPreferencesP fps= new SharedPreferencesP();

    /*
    para ver si llamar a la alarma o no
     */

    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
    ArrayList<BoletajeAuxiliar> boletaje_auxiliar= new ArrayList<BoletajeAuxiliar>();
    Visitas_controlador vc;
    Boolean existe_alarma=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        verificar_usuario_y_gps();
        vc= new Visitas_controlador(Splash.this,"visita_medica.sqlite");

    }





    public void verificar_usuario_y_gps()
    {
        final LocationManager manager = (LocationManager)Splash.this.getSystemService(Context.LOCATION_SERVICE );

        if( !manager.isProviderEnabled( LocationManager.NETWORK_PROVIDER )|| !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ))
        {
            Toast.makeText(Splash.this,getString(R.string.debe_tener_el_gps_prendido),Toast.LENGTH_SHORT).show();
            Intent callGPSSettingIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(callGPSSettingIntent, 1);
        }
        else {

            startService(new Intent(Splash.this, LocationService.class));


            TimerTask timerTask = new TimerTask() {
                public void run() {

                    id_usuario = fps.Consultar_si_hay_registro(Splash.this);

                    if (!id_usuario.equals("")) {
                        goToActivity(MenuPrincipal.class);
                        timer.cancel();
                    } else

                    {
                        goToActivity(Login.class);
                        timer.cancel();

                    }


                }
            };
            timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 1500, 1500);
        }

    }


    private void goToActivity(Class<? extends Activity> activityClass)
    {
        Intent newActivity = new Intent(this, activityClass);
        startActivity(newActivity);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_CANCELED) {

                verificar_usuario_y_gps();
            }
        }
    }


}
