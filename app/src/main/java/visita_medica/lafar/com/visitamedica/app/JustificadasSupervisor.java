package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_justificadas;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_visita_medica;
import visita_medica.lafar.com.visitamedica.async.OperacionesSincronizarJustificadas;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.objetos.Justificadas;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;

public class JustificadasSupervisor extends Activity {


    Visitas_controlador vc;
    ArrayList<Justificadas> justificadas= new ArrayList<Justificadas>();
    Button guardar, sincronizar;
    ImageView volver;
    ArrayList<String> estado= new ArrayList<>();
    TableLayout tabla_justificadas;
    TableLayout.LayoutParams params;
    int contador_justificadas;

    //////////////Fecha y hora

    private Calendar fechaYhora = Calendar.getInstance();
    Calendar mas_diez_minutos,mas_quince_minutos;
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;

    SharedPreferencesP fps= new SharedPreferencesP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificadas_supervisor);

        vc=  new Visitas_controlador(JustificadasSupervisor.this,"visita_medica.sqlite");
        contador_justificadas=vc.select_count_justificadas_sin_sincronizar();
        justificadas=vc.select_justificadas_pendientes();


       tabla_justificadas=(TableLayout)this.findViewById(R.id.tl_justificadas);


       if(justificadas.size()>0)
       {
           actualizar_lista();
       }
        else
       {
           Toast.makeText(JustificadasSupervisor.this,R.string.no_tiene_justificadas_pendientes ,Toast.LENGTH_SHORT).show();
       }

        volver=(ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        guardar=(Button)this.findViewById(R.id.btn_guardar_justificadas);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha_actual = fecha.format(fechaYhora.getTime());


        for(int i=0 ; i<estado.size();i++)
        {

            String[] estado_id_visita = estado.get(i).split("_");
            String id_visi="";
            String estado_visi="";
            for (int j = 0; j < estado_id_visita.length; j++) {
                id_visi=estado_id_visita[0];
                estado_visi=estado_id_visita[1];

            }

            vc.update_justificadas_fecha_sinc_estado_justificada_dado_id_visita(id_visi,fecha_actual,estado_visi);
        }

                finish();
                Intent i=  new Intent(JustificadasSupervisor.this, JustificadasSupervisor.class);
                startActivity(i);

            }
        });
        sincronizar=(Button)this.findViewById(R.id.btn_sincronizar_justificadas);
        sincronizar.setText(getString(R.string.sincronizar_banco_muestras)+"("+contador_justificadas+")");
        sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OperacionesSincronizarJustificadas(JustificadasSupervisor.this, fps.Consultar_si_hay_registro(JustificadasSupervisor.this) ).execute();
            }
        });
    }

    public void actualizar_lista()
    {

       final Button rechazadas[]= new Button[justificadas.size()];
       final Button aceptadas[]= new Button[justificadas.size()];


        for (int j =0;j<justificadas.size();j++) {
            estado.add(justificadas.get(j).getId_visita() + "_2");

            params= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0, 0,5);


            rechazadas[j] = new Button(this);
            rechazadas[j].setBackgroundResource(R.drawable.boton_custom);
            rechazadas[j].setTextColor(Color.WHITE);

            aceptadas[j] = new Button(this);
            aceptadas[j].setBackgroundResource(R.color.plomo_oscuro);
            aceptadas[j].setTextColor(Color.BLACK);

            LinearLayout linear_horizontal= new LinearLayout(this);
            linear_horizontal.setLayoutParams(params);
            linear_horizontal.setOrientation(LinearLayout.HORIZONTAL);


            final int finalJ = j;


            TextView id_visita = new TextView(this);
            id_visita.setGravity(Gravity.LEFT);
            id_visita.setTextSize(16);
            id_visita.setLayoutParams(params);
            id_visita.setTypeface(null, Typeface.BOLD);
            id_visita.setText(justificadas.get(j).getId_visita());

            TextView nombre_doctor = new TextView(this);
            nombre_doctor.setGravity(Gravity.LEFT);
            nombre_doctor.setTextSize(10);
            nombre_doctor.setLayoutParams(params);
            nombre_doctor.setText(justificadas.get(j).getNombre_doctor());


            TextView visitador = new TextView(this);
            visitador.setGravity(Gravity.LEFT);
            visitador.setTextSize(10);
            visitador.setLayoutParams(params);
            visitador.setText(justificadas.get(j).getApm());


            TextView motivo_obser = new TextView(this);
            motivo_obser.setGravity(Gravity.LEFT);
            motivo_obser.setTextSize(10);
            motivo_obser.setLayoutParams(params);
            motivo_obser.setText(justificadas.get(j).getMOTIVO_OBSER());



/*
RECHAZADAS
 */
            rechazadas[j].setText(getString(R.string.rechazar));
            rechazadas[j].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
            rechazadas[j].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    estado.set(finalJ, justificadas.get(finalJ).getId_visita() + "_2");

                    rechazadas[finalJ].setBackgroundResource(R.drawable.boton_custom);
                    rechazadas[finalJ].setTextColor(Color.WHITE);

                    aceptadas[finalJ].setBackgroundResource(R.color.plomo_oscuro);
                    aceptadas[finalJ].setTextColor(Color.BLACK);


                }
            });


           /*
       ACEPTADAS
        */
            aceptadas[j].setText(getString(R.string.autorizar));
            aceptadas[j].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
            aceptadas[j].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    estado.set(finalJ, justificadas.get(finalJ).getId_visita() + "_1");


                    rechazadas[finalJ].setBackgroundResource(R.color.plomo_oscuro);
                    rechazadas[finalJ].setTextColor(Color.BLACK);


                    aceptadas[finalJ].setBackgroundResource(R.drawable.boton_custom);
                    aceptadas[finalJ].setTextColor(Color.WHITE);


                }
            });


            linear_horizontal.addView(rechazadas[j]);
            linear_horizontal.addView(aceptadas[j]);


            tabla_justificadas.addView(id_visita);
            tabla_justificadas.addView(nombre_doctor);
            tabla_justificadas.addView(visitador);
            tabla_justificadas.addView(motivo_obser);
            tabla_justificadas.addView(linear_horizontal);

        }

    }
}



