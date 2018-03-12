package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;

public class VisitasNegativas extends Activity {


    String Codbarra;
    String id_visita;
    String nombremed;
    String mes;
    String anhio;
    String ciudad;
    TextView tv_codbarra,  tv_nombremed;

/* AÑADIDO EL 5 DE ENERO DEL 2016*/
    EditText motivo_visita_negativa;


    Button guardar_negativa;
    Visitas_controlador vc;

    TareasPendientes tareas_pendientes= new TareasPendientes();//segundo tareas_pendientes.segundo.finish();
    MenuPrincipal menu_principal= new MenuPrincipal();//primero
    FormularioVisita formulario_visita= new FormularioVisita();//tercero

    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();


    //////////////////////////Intent
    Intent intent ;
    Bundle bundle;
    SharedPreferencesP fps=new SharedPreferencesP();

    //////////////Fecha y hora

    private Calendar fechaYhora = Calendar.getInstance();
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;


    ImageView volver;

    ArrayList<String> motivos_justificacion= new ArrayList<String>();
    Spinner sp_motivos_justificacion;
    String justificacion;
    Double latitudes=0.0,  longitudes=0.0;
    String string_observaciones_coordenadas;

    Funciones fun = new Funciones();

    /////////////////////////////////////////Vista acompañada
    /*RadioButton acompanhiada, no_acompanhiada;*/
    String valor_visita_acompanhiada;
    ArrayList<String> array_acompanhiada= new ArrayList<String>();
    Spinner sp_acompanhiada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitas_negativas);

       /* acompanhiada= (RadioButton)findViewById(R.id.radio_acompanhiada);
        acompanhiada.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                valor_visita_acompanhiada="acompanhiada";
            }
        });

        no_acompanhiada= (RadioButton)findViewById(R.id.radio_no_acompanhiada);
        no_acompanhiada.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                valor_visita_acompanhiada="no_acompanhiada";
            }
        });*/

        array_acompanhiada.add(new String(getString(R.string.visita_no_acompanhiada)));
        array_acompanhiada.add(new String(getString(R.string.supervisor)));
        array_acompanhiada.add(new String(getString(R.string.jefatura_de_regional)));
        array_acompanhiada.add(new String(getString(R.string.gerente_de_producto)));
        array_acompanhiada.add(new String(getString(R.string.acesor_medico)));

        sp_acompanhiada=(Spinner)this.findViewById(R.id.sp_acompanhiado);

        ArrayAdapter spinnerArrayAdapter_acompamhiado = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,array_acompanhiada);
        sp_acompanhiada.setAdapter(spinnerArrayAdapter_acompamhiado);
        sp_acompanhiada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position, long id) {

                valor_visita_acompanhiada = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        motivo_visita_negativa=(EditText)this.findViewById(R.id.txt_observaciones_visita_negativa);

        motivos_justificacion.add(new String(getString(R.string.no_se_pudo_realizar_la_visita)));
        motivos_justificacion.add(new String(getString(R.string.baja_medica)));
        motivos_justificacion.add(new String(getString(R.string.permiso_temporal_o_prolongado)));
        motivos_justificacion.add(new String(getString(R.string.retiro)));
        motivos_justificacion.add(new String(getString(R.string.jubilacion)));
        motivos_justificacion.add(new String(getString(R.string.vacaciones)));
        motivos_justificacion.add(new String(getString(R.string.fallecimiento)));
        motivos_justificacion.add(new String(getString(R.string.visita_justificada)));


        sp_motivos_justificacion=(Spinner)this.findViewById(R.id.spinner_negativas);


        ArrayAdapter spinnerArrayAdapter_motivos_justificacion = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,motivos_justificacion);
        sp_motivos_justificacion.setAdapter(spinnerArrayAdapter_motivos_justificacion);
        sp_motivos_justificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position,  long id){

                justificacion=parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        } );

        volver=(ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        bundle=getIntent().getExtras();


        vc= new Visitas_controlador(VisitasNegativas.this, "visita_medica.sqlite");
        id_visita=bundle.getString("id_visita");
        Codbarra=bundle.getString("codigo_barra");
        nombremed=bundle.getString("nombre_medico");
        mes=bundle.getString("mes");
        anhio=bundle.getString("anhio");
        ciudad=bundle.getString("ciudad");



        tv_codbarra=(TextView)this.findViewById(R.id.lbl_id_boleta);
        tv_nombremed=(TextView)this.findViewById(R.id.lbl_nombre_doctor);

        tv_codbarra.setText(id_visita);
        tv_nombremed.setText(nombremed);

        guardar_negativa=(Button)this.findViewById(R.id.btn_guardar_negativa);
        guardar_negativa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(motivo_visita_negativa.getText().toString().equals("")) {

                 Toast.makeText(VisitasNegativas.this,getString(R.string.observacion_obligatoria),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i = new Intent(VisitasNegativas.this, EstoyAqui2.class);
                    startActivityForResult(i, 1);
                }


            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {


                case 1:
                    if (resultCode == Activity.RESULT_OK && resultCode != VisitasNegativas.this.RESULT_CANCELED) {
                        String string_latitude = data.getStringExtra("Latitude");
                        String string_longitude = data.getStringExtra("Longitude");
                        latitudes = fun.convertir_de_string_a_double(string_latitude);
                        longitudes = fun.convertir_de_string_a_double(string_longitude);
                        string_observaciones_coordenadas = data.getStringExtra("observaciones_coordenadas");

                        if (latitudes == 0.0 && longitudes == 0.0) {
                            coordenadas_no_validas();
                        } else {

/*
comentar y descomentar para el alto
 */

                           if(fun.existe_error_con_id_boleta(VisitasNegativas.this,id_visita)) {
                                datos_guardados();
                                fecha_actual = fecha.format(fechaYhora.getTime());
                                vc.update_estado_visita(id_visita, "2");
                                boletaje.add(new Boletaje(
                                        id_visita,
                                        Codbarra,
                                        fecha_actual,
                                        mes,
                                        anhio,
                                        fps.Consultar_si_hay_registro(VisitasNegativas.this),
                                        justificacion,
                                        motivo_visita_negativa.getText().toString()+ "-"+ string_observaciones_coordenadas ,
                                        ciudad,
                                        Double.toString(latitudes),
                                        Double.toString(longitudes),
                                        "0",
                                        "0",
                                        "",
                                        valor_visita_acompanhiada));
                                vc.add_boletaje(boletaje);
                                menu_principal.primero.finish();
                                tareas_pendientes.segundo.finish();
                                formulario_visita.tercero.finish();
                                finish();
                                intent = new Intent(VisitasNegativas.this, MenuPrincipal.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(VisitasNegativas.this,getString(R.string.cierre_la_boleta_intente_nuevamente_llame_al_departamento_de_sistemas),Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        coordenadas_no_validas();
                    }


                    break;
            }
        }
        catch (NullPointerException e)
        {
            coordenadas_no_validas();
        }
    }
public void datos_guardados()
{
    Toast.makeText(VisitasNegativas.this, getString(R.string.datos_guardados_en_la_memoria_local), Toast.LENGTH_SHORT).show();
}

    public void coordenadas_no_validas()
    {
        Toast.makeText(VisitasNegativas.this,getString(R.string.vuelva_a_intentar_obtener_coordenadas),Toast.LENGTH_SHORT).show();
    }


}
