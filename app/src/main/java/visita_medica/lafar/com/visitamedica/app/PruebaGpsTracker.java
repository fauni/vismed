package visita_medica.lafar.com.visitamedica.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import visita_medica.lafar.com.visitamedica.R;

public class PruebaGpsTracker extends ActionBarActivity {
    Bundle bundle;
    Double latitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_gps_tracker);

       bundle= new Intent().getExtras();




        Button btnInicio = (Button) findViewById(R.id.btnInicio);
        Button btnFin = (Button) findViewById(R.id.btnFin);

        btnInicio.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(PruebaGpsTracker.this, LocationService.class));

               /* latitud=bundle.getDouble("Latitude");
                Toast.makeText(PruebaGpsTracker.this , latitud+""   ,Toast.LENGTH_LONG ).show();*/
            }
        });

        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(PruebaGpsTracker.this, LocationService.class));

            }
        });
    }


}
