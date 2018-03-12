/*package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class EstoyAqui2 extends android.support.v4.app.FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    /////////////////////GEOLOCALIZACIÓN POR GPS
    public LocationManager locationManager;
    public android.location.LocationListener locationListener;
    public Location location;
    Double lat_gps=0.0;
    Double lon_gps=0.0;

    /////////////////////GEOLOCALIZACIÓN POR REDES

    Double lat_redes=0.0;
    Double lon_redes=0.0;


    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    ////////////////////////MAPAS
    private GoogleMap mapa_gps = null;
    SupportMapFragment mMapFragment_gps;
    Marker marker_gps;

    private GoogleMap mapa_redes = null;
    SupportMapFragment mMapFragment_redes;
    Marker marker_redes;

    ////////////GENERAL
    Funciones fun = new Funciones();
    Button btn_redes, btn_gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_estoy_aqui);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);

        /////////////////////////////////////////GEOLOCALIZACIÓN POR GPS

        //localización
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locationListener = new android.location.LocationListener() {
            public void onLocationChanged(Location location) {


                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null)
                {

                    lat_gps=location.getLatitude();
                    lon_gps=location.getLongitude();
                }
                else
                {
                    lat_gps=0.0;
                    lon_gps=0.0;
                }


                if(marker_gps!=null) {
                    marker_gps.remove();



                }
                else
                {

                }

                mMapFragment_gps = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_gps));
                mapa_gps = mMapFragment_gps.getMap();
                LatLng visita_gps = new LatLng(lat_gps, lon_gps);
                CameraPosition camPos_gps = new CameraPosition.Builder()
                        .target(visita_gps)
                        .zoom(16)
                        .build();
                CameraUpdate camUpd3_gps = CameraUpdateFactory.newCameraPosition(camPos_gps);
                mapa_gps.animateCamera(camUpd3_gps);
                marker_gps = mapa_gps.addMarker(new MarkerOptions()
                        .position(new LatLng(lat_gps, lon_gps))
                        .title("Localización por GPS"));


            }

            public void onProviderDisabled(String provider) {
                Log.i("LocAndroid",
                        "Proveedor GPS deshabilitado, habilitelo por favor");
                Intent in = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(in);
            }

            public void onProviderEnabled(String provider) {
                Log.i("LocAndroid", "Proveedor habilitado");
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.i("LocAndroid", "Estado proveedor: " + status);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 0, locationListener);

        btn_redes=(Button)this.findViewById(R.id.btn_elegir_redes);
        btn_redes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Latitude",Double.toString(lat_redes));
                resultIntent.putExtra("Longitude",Double.toString(lon_redes));
                setResult(Activity.RESULT_OK, resultIntent);

                locationManager.removeUpdates(locationListener);
                stopService(new Intent(EstoyAqui2.this, LocationService.class));
                finish();
            }
        });

        btn_gps=(Button)this.findViewById(R.id.btn_elegir_gps);
        btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Latitude",Double.toString(lat_gps));
                resultIntent.putExtra("Longitude",Double.toString(lon_gps));
                setResult(Activity.RESULT_OK, resultIntent);


                locationManager.removeUpdates(locationListener);
                stopService(new Intent(EstoyAqui2.this, LocationService.class));
                finish();
            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }


    MyReceiver myReceiver;
    String service_latitude, service_longitude;
    @Override
    protected void onStart() {

        super.onStart();
        mGoogleApiClient.connect();

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service
        Intent intent = new Intent(EstoyAqui2.this,LocationService.class);
        startService(intent);
        super.onStart();
        System.out.println("reciever registrado");
    }

    ///////////ESTE MÉTODO ACTUALMENTE ESTÁ BORRADO*/

   /* @Override
    protected void onStop() {
        //Toast.makeText(EstoyAqui.this, "reciever ya no registrado", Toast.LENGTH_SHORT).show();
        unregisterReceiver(myReceiver);
        super.onStop();
    }*/


   /* @Override
    protected void onDestroy()
    {
        System.out.println("reciever ya no registrado");
        unregisterReceiver(myReceiver);
        myReceiver=null;
        super.onDestroy();
    }*/

  /*  @Override
    public void onConnected(Bundle bundle) {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {


            if(marker_redes!=null) {
                marker_redes.remove();


            }
            else
            {

            }


            mMapFragment_redes = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_network));
            mapa_redes = mMapFragment_redes.getMap();

            lat_redes=mLocation.getLatitude();
            lon_redes=mLocation.getLongitude();

            LatLng visita_redes = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());//lat_redes,lon_redes);
            CameraPosition camPos_redes = new CameraPosition.Builder()
                    .target(visita_redes)
                    .zoom(16)
                    .build();
            CameraUpdate camUpd3_redes = CameraUpdateFactory.newCameraPosition(camPos_redes);
            mapa_redes.animateCamera(camUpd3_redes);
            marker_redes= mapa_redes.addMarker(new MarkerOptions()
                    .position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
                    .title("Localización por redes"));



        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {



            service_latitude = arg1.getStringExtra("Latitude");
            service_longitude= arg1.getStringExtra("Longitude");
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%"+service_latitude+","+service_longitude);

            lat_redes=fun.convertir_de_string_a_double(service_latitude);
            lon_redes=fun.convertir_de_string_a_double(service_longitude);



            if(marker_redes!=null) {
                marker_redes.remove();



            }
            else
            {

            }




            mMapFragment_redes = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_network));
            mapa_redes = mMapFragment_redes.getMap();
            LatLng visita_redes = new LatLng(lat_redes,lon_redes);
            CameraPosition camPos_redes = new CameraPosition.Builder()
                    .target(visita_redes)
                    .zoom(16)
                    .build();
            CameraUpdate camUpd3_redes = CameraUpdateFactory.newCameraPosition(camPos_redes);
            mapa_redes.animateCamera(camUpd3_redes);
            marker_redes= mapa_redes.addMarker(new MarkerOptions()
                    .position(new LatLng(lat_redes, lon_redes))
                    .title("Localización por redes"));

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent resultIntent = new Intent();
            resultIntent.putExtra("Latitude",Double.toString(0.0));
            resultIntent.putExtra("Longitude",Double.toString(0.0));
            setResult(Activity.RESULT_OK, resultIntent);


            locationManager.removeUpdates(locationListener);
            stopService(new Intent(EstoyAqui2.this, LocationService.class));
            finish();


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}*/

package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;

import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class EstoyAqui2 extends android.support.v4.app.FragmentActivity {
    /////////////////////GEOLOCALIZACIÓN POR GPS
    public LocationManager locationManager;
    public android.location.LocationListener locationListener;
    public Location location;
    Double lat_gps=0.0;
    Double lon_gps=0.0;

    /////////////////////GEOLOCALIZACIÓN POR REDES

    Double lat_redes=0.0;
    Double lon_redes=0.0;

    ////////////////////////MAPAS
    private GoogleMap mapa_gps = null;
    SupportMapFragment mMapFragment_gps;
    Marker marker_gps;

    private GoogleMap mapa_redes = null;
    SupportMapFragment mMapFragment_redes;
    Marker marker_redes;

    ////////////GENERAL
    Funciones fun = new Funciones();
    Button btn_redes, btn_gps;
    String bandera_gps_correcto="1";

    /*
    OBSERVACIONES COORDENADAS
     */
    ArrayList<String> observaciones_coordenadas= new ArrayList<String>();
    Spinner sp_observaciones_coordenadas;
    ArrayAdapter spinnerArrayAdapter_observaciones_coordenadas;
    String s_observaciones_coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_estoy_aqui);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        observaciones_coordenadas.add(getString(R.string.ubicacion_precisa));
        observaciones_coordenadas.add(getString(R.string.ubicacion_imprecisa));

        sp_observaciones_coordenadas=(Spinner)this.findViewById(R.id.sp_observaciones_coordenadas);


        spinnerArrayAdapter_observaciones_coordenadas= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,observaciones_coordenadas);
        sp_observaciones_coordenadas.setAdapter(spinnerArrayAdapter_observaciones_coordenadas);
        sp_observaciones_coordenadas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position,  long id){

                s_observaciones_coordenadas=parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        } );


        /*
        vemos si permite ubicaciones falsas
         */

        if(is_mock())
        {
            bandera_gps_correcto="0";
        }

if(bandera_gps_correcto.equals("0"))
{

    finish();

}
        /////////////////////////////////////////GEOLOCALIZACIÓN POR GPS

        //localización
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locationListener = new android.location.LocationListener() {
            public void onLocationChanged(Location location) {


                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null)
                {

                    lat_gps=location.getLatitude();
                    lon_gps=location.getLongitude();
                }
                else
                {
                    lat_gps=0.0;
                    lon_gps=0.0;
                }


                if(marker_gps!=null) {
                    marker_gps.remove();

                }
                else
                {

                }

                try {
                    mMapFragment_gps = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_gps));
                    mapa_gps = mMapFragment_gps.getMap();
                    LatLng visita_gps = new LatLng(lat_gps, lon_gps);
                    CameraPosition camPos_gps = new CameraPosition.Builder()
                            .target(visita_gps)
                            .zoom(16)
                            .build();
                    CameraUpdate camUpd3_gps = CameraUpdateFactory.newCameraPosition(camPos_gps);
                    mapa_gps.animateCamera(camUpd3_gps);
                    marker_gps = mapa_gps.addMarker(new MarkerOptions()
                            .position(new LatLng(lat_gps, lon_gps))
                            .title("Localización por GPS"));
                }
                catch(NullPointerException e)
                {
                   // Toast.makeText(EstoyAqui2.this,"El GPS está mandando coordenadas nulas",Toast.LENGTH_SHORT).show();
                }

            }

            public void onProviderDisabled(String provider) {
                Log.i("LocAndroid",
                        "Proveedor GPS deshabilitado, habilitelo por favor");
                Intent in = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(in);
            }

            public void onProviderEnabled(String provider) {
                Log.i("LocAndroid", "Proveedor habilitado");
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.i("LocAndroid", "Estado proveedor: " + status);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 0, locationListener);

        btn_redes=(Button)this.findViewById(R.id.btn_elegir_redes);
        btn_redes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Latitude",Double.toString(lat_redes));
                resultIntent.putExtra("Longitude",Double.toString(lon_redes));
                resultIntent.putExtra("observaciones_coordenadas",s_observaciones_coordenadas);
                setResult(Activity.RESULT_OK, resultIntent);

                locationManager.removeUpdates(locationListener);
                stopService(new Intent(EstoyAqui2.this, LocationService.class));
                finish();
            }
        });

        btn_gps=(Button)this.findViewById(R.id.btn_elegir_gps);
        btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Latitude",Double.toString(lat_gps));
                resultIntent.putExtra("Longitude",Double.toString(lon_gps));
                resultIntent.putExtra("observaciones_coordenadas",s_observaciones_coordenadas);
                setResult(Activity.RESULT_OK, resultIntent);


                locationManager.removeUpdates(locationListener);
                stopService(new Intent(EstoyAqui2.this, LocationService.class));
                finish();
            }
        });


    }


    MyReceiver myReceiver;
    String service_latitude, service_longitude;
    @Override
    protected void onStart() {
        //Register BroadcastReceiver
        //to receive event from our service
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service
        Intent intent = new Intent(EstoyAqui2.this,LocationService.class);
        startService(intent);
        super.onStart();
        System.out.println("reciever registrado");
    }

/*
FAKE GPS PRUEBA 1
 */
    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }


   /*
    FAKE GPS PRUEBA 2
     */

    public static boolean areThereMockPermissionApps(Context context) {

        int count = 0;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages =
                pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,PackageManager.GET_PERMISSIONS);

                // Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;

                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        if (requestedPermissions[i]
                                .equals("android.permission.ACCESS_MOCK_LOCATION")
                                && !applicationInfo.packageName.equals(context.getPackageName())) {
                            count++;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
               // Log.i("Got exception " + e.getMessage());
                Log.e("Exception",""+e.getMessage());
            }
        }

        if (count > 0)
            return true;
        return false;
    }


public boolean is_mock() {
    if (Settings.Secure.getString(getContentResolver(),
            Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
        return false;
    else return true;
}
    ///////////ESTE MÉTODO ACTUALMENTE ESTÁ BORRADO

  /*  @Override
    protected void onStop() {
        //Toast.makeText(EstoyAqui.this, "reciever ya no registrado", Toast.LENGTH_SHORT).show();
        unregisterReceiver(myReceiver);
        super.onStop();
    }
*/



   /* @Override
    protected void onDestroy()
    {
        System.out.println("reciever ya no registrado");
        unregisterReceiver(myReceiver);
        myReceiver=null;
        super.onDestroy();
    }*/

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {



            service_latitude = arg1.getStringExtra("Latitude");
            service_longitude= arg1.getStringExtra("Longitude");
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%"+service_latitude+","+service_longitude);

            lat_redes=fun.convertir_de_string_a_double(service_latitude);
            lon_redes=fun.convertir_de_string_a_double(service_longitude);



            if(marker_redes!=null) {
                marker_redes.remove();



            }
            else
            {

            }


try {

    mMapFragment_redes = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_network));
    mapa_redes = mMapFragment_redes.getMap();
    LatLng visita_redes = new LatLng(lat_redes, lon_redes);
    CameraPosition camPos_redes = new CameraPosition.Builder()
            .target(visita_redes)
            .zoom(16)
            .build();
    CameraUpdate camUpd3_redes = CameraUpdateFactory.newCameraPosition(camPos_redes);
    mapa_redes.animateCamera(camUpd3_redes);
    marker_redes = mapa_redes.addMarker(new MarkerOptions()
            .position(new LatLng(lat_redes, lon_redes))
            .title("Localización por redes"));

}

catch (NullPointerException e)
{

}

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent resultIntent = new Intent();
            resultIntent.putExtra("Latitude",Double.toString(0.0));
            resultIntent.putExtra("Longitude",Double.toString(0.0));
            setResult(Activity.RESULT_OK, resultIntent);


            locationManager.removeUpdates(locationListener);
            stopService(new Intent(EstoyAqui2.this, LocationService.class));
            finish();


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}

