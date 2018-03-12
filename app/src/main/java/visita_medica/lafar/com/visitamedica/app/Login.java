package visita_medica.lafar.com.visitamedica.app;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.async.OperacionesLogin;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;


public class Login extends Activity {

    EditText usuario,  contrasenhia;
    Button login;
    Funciones fun= new Funciones();
    TextView he_olvidado_mi_contrasenhia;
    public static Activity first;

    private static final int MY_PERMISSIONS_1 = 1 ;
    private static final int MY_PERMISSIONS_2 = 2 ;
    private static final int MY_PERMISSIONS_3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        first=Login.this;

        usuario=(EditText)this.findViewById(R.id.txt_usuario);
        contrasenhia=(EditText)this.findViewById(R.id.txt_contrasenhia);





        //Aca pregunta si esta activado el permiso
       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_1);
            }
        }
*/



        login=(Button)this.findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String imei=fun.getIMEI(Login.this);



                   if (!fun.verificaConexion(Login.this)) {
                       Toast toast = Toast.makeText(getApplicationContext(), R.string.necesita_conexion, Toast.LENGTH_LONG);
                       toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                       toast.show();
                       startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                   } else {

                       new OperacionesLogin(Login.this, usuario.getText().toString(), contrasenhia.getText().toString(),imei ).execute();
                   }


            }
        });

        he_olvidado_mi_contrasenhia=(TextView)this.findViewById(R.id.lbl_he_olvidado_mi_contrasenhia);
        he_olvidado_mi_contrasenhia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent();
                intent.setComponent(new ComponentName(Login.this, HeOlvidadoMiContrasenhia.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }


    /******DEBBIEEEEEEEE!!!!!!!!*******/
    //Aca esta la funciÃ³n que comprueba si tienes activado los permisos hace la verificaciÃ³n permiso por permiso si esta activado
    //@Override
   /* public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_2);
                        }
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_2);
                        }
                    }
                }
                return;
            }
            case MY_PERMISSIONS_2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_3);
                        }
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_3);
                        }
                    }
                }
                return;
            }
            case MY_PERMISSIONS_3: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }*/
}
