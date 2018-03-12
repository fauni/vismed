package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.async.OperacionesLogin;
import visita_medica.lafar.com.visitamedica.async.OperacionesRecuperarContrasenhia;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;

public class HeOlvidadoMiContrasenhia extends Activity {

    EditText email;
    Button btn_enviar_datos;
    ImageView volver;
    Funciones fun= new Funciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_he_olvidado_mi_contrasenhia);

        volver=(ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        email=(EditText)this.findViewById(R.id.txt_email);
        btn_enviar_datos=(Button)this.findViewById(R.id.btn_recuperar_contrasenhia);
        btn_enviar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fun.isEmailValid(email.getText().toString())==true) {

                    if (!fun.verificaConexion(HeOlvidadoMiContrasenhia.this)) {
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.necesita_conexion, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                    } else {
                        new OperacionesRecuperarContrasenhia(HeOlvidadoMiContrasenhia.this, email.getText().toString()).execute();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.debe_ingresar_un_mail_valido, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }



            }
        });


    }

}
