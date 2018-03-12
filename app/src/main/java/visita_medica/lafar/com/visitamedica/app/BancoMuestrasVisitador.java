package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_banco_muestras_inicial;
import visita_medica.lafar.com.visitamedica.async.OperacionesGuardarBancoMuestras;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;

public class BancoMuestrasVisitador extends Activity {

    ArrayList<Tblbancomm> banco_muestra=  new ArrayList<Tblbancomm>();
    ListView lista;
    Visitas_controlador vc;
    EditText buscador;
    Adapter_banco_muestras_inicial adapter;
    public static Activity  activity_listado_banco_muestras;
    ImageView atras;
    Funciones fun= new Funciones();
    int contador_sin_sincronizar;
    Button realizadas, sincronizar;
    SharedPreferencesP fps=  new SharedPreferencesP();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_muestras_visitador);

        activity_listado_banco_muestras=BancoMuestrasVisitador.this;

        realizadas=(Button)this.findViewById(R.id.btn_realizadas);
        realizadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_realizadas = new Intent(BancoMuestrasVisitador.this, BancoMuestrasRealizado.class);
                intent_realizadas.putExtra("tipo","visitador");
                startActivity(intent_realizadas);
            }
        });



        atras= (ImageView)this.findViewById(R.id.btn_volver);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        vc=new Visitas_controlador(BancoMuestrasVisitador.this,"visita_medica.sqlite");
        llenar_lista();

        realizadas=(Button)this.findViewById(R.id.btn_realizadas);
        sincronizar=(Button)this.findViewById(R.id.btn_sincronizar_banco_muestras);

        contador_sin_sincronizar=vc.contar_banco_muestras_sin_sincronizar();
        sincronizar.setText(getString(R.string.sincronizar_banco_muestras)+"("+contador_sin_sincronizar+")");
        realizadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                AQUI HAY QUE DESCARGADAR TODAS LAS REALIZADAS DEL MES Y UN MES ANTERIOR PARA QHA
                 */
                Intent intent_realizadas = new Intent(BancoMuestrasVisitador.this, BancoMuestrasRealizado.class);
                startActivity(intent_realizadas);
            }
        });

        sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!fun.verificaConexion(BancoMuestrasVisitador.this))
                {
                    Toast toast =Toast.makeText(getApplicationContext(), R.string.necesita_conexion,Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                }
                else {

                    new OperacionesGuardarBancoMuestras(BancoMuestrasVisitador.this, fps.Consultar_si_hay_registro(BancoMuestrasVisitador.this)).execute();
                }

            }
        });
    }

    public void llenar_lista() {

        lista=(ListView)this.findViewById(R.id.lv_banco_muestras);
        banco_muestra=vc.select_banco_muestras_pendientes();
        buscador= (EditText) findViewById(R.id.txt_buscador);
        adapter=new Adapter_banco_muestras_inicial(this,R.layout.list_item_banco_muestras,banco_muestra);
        lista.setAdapter(adapter);


        buscador.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String text = buscador.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


       lista.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final LocationManager manager = (LocationManager) BancoMuestrasVisitador.this.getSystemService(Context.LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Toast.makeText(BancoMuestrasVisitador.this, getString(R.string.debe_tener_el_gps_prendido), Toast.LENGTH_SHORT).show();
                    Intent callGPSSettingIntent = new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(callGPSSettingIntent);
                } else {


                    try {

                        if (android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.AUTO_TIME) == 1) {


                            String id_formulario_de_banco_de_muestras = ((Tblbancomm) parent.getItemAtPosition(position)).getId_formulario_de_banco_de_muestras();
                            Intent i= new Intent();
                            i.setComponent(new ComponentName(BancoMuestrasVisitador.this, FormularioBancoMuestrasVisitador.class));
                            i.putExtra("id_formulario_de_banco_de_muestras",id_formulario_de_banco_de_muestras);
                            startActivity(i);


                        } else {
                            Toast.makeText(BancoMuestrasVisitador.this, getString(R.string.debe_poner_la_fecha_en_automatico), Toast.LENGTH_SHORT).show();
                            startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                        }
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

}
