package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeAuxiliar;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeHistorico;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.objetos.DetalleVisita;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;

public class FormularioVisita extends Activity {


    Bundle bundle;
    String Codbarra;
    String id_visita;
    String mes;
    String anhio;
    String ciudad;
    String codmed;
    String nombre;
    String tipo_positivo;
    TextView nombre_doctor, id_boleta;
    Button negativa,  cargar_firma, mas;
    EditText observaciones;
    ArrayList<DetalleVisita> detalle_visita = new ArrayList<DetalleVisita>();
    ArrayList<Boletaje> boletaje = new ArrayList<Boletaje>();
    ArrayList<BoletajeHistorico> boletaje_historico = new ArrayList<BoletajeHistorico>();
    ArrayList<BoletajeAuxiliar> boletaje_auxiliar = new ArrayList<BoletajeAuxiliar>();
    TableLayout table_layout;
    ////////////////////////Consultas en la base de datos////////////////////////////
    Visitas_controlador vc;

    //////////////////////////Intent
    Intent intent;
    /////Shared Preferences
    SharedPreferencesP fps = new SharedPreferencesP();

    //////////////Fecha y hora

    private Calendar fechaYhora = Calendar.getInstance();
    Calendar mas_diez_minutos,mas_quince_minutos;
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;

    /////////////////por componentes para hacer finish de golde una pila de activityes
    TareasPendientes tareas_pendientes = new TareasPendientes();
    MenuPrincipal menu_principal = new MenuPrincipal();
    TareasRealizadas tareas_realizadas = new TareasRealizadas();
    FichaInformacionIndividual ficha_informacion_individual = new FichaInformacionIndividual();
    public static Activity tercero;
    ImageView volver;
    Funciones fun= new Funciones();
    ImageView estado_gps;
///////////////////////////////////////datos para localización que se me tiene que devolver
    public Double latitudes=0.0, longitudes=0.0;
    String string_observaciones_coordenadas;
    String ruta_imagen="";
/////////////////////////////////////////Vista acompañada

    String valor_visita_acompanhiada;
    ArrayList<String> array_acompanhiada= new ArrayList<String>();
    Spinner sp_acompanhiada;

/*
Para ver si file exites
 */

    String foto;
    Integer bandera_existe_foto=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_visita);




        /*
        Esto es para indicar si la visita fue acompañada o no
         */
        array_acompanhiada.add(new String(getString(R.string.visita_no_acompanhiada)));
        array_acompanhiada.add(new String(getString(R.string.supervisor)));
        array_acompanhiada.add(new String(getString(R.string.jefatura_de_regional)));
        array_acompanhiada.add(new String(getString(R.string.gerente_de_producto)));
        array_acompanhiada.add(new String(getString(R.string.acesor_medico)));

        tercero = FormularioVisita.this;



        sp_acompanhiada=(Spinner)this.findViewById(R.id.sp_acompanhiado);

     //   ArrayAdapter spinnerArrayAdapter_acompamhiado = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,array_acompanhiada);
        ArrayAdapter spinnerArrayAdapter_acompamhiado = new ArrayAdapter(this, R.layout.spinner_layout,array_acompanhiada);
        sp_acompanhiada.setAdapter(spinnerArrayAdapter_acompamhiado);
        sp_acompanhiada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position,  long id){

                valor_visita_acompanhiada=parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        } );



        estado_gps=(ImageView)this.findViewById(R.id.img_estado_gps);
        estado_gps.setVisibility(View.GONE);

        volver = (ImageView) this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        observaciones = (EditText) this.findViewById(R.id.txt_observaciones_visita);

        vc = new Visitas_controlador(FormularioVisita.this, "visita_medica.sqlite");

        bundle = getIntent().getExtras();
        Codbarra = bundle.getString("Codbarra");
        id_visita = bundle.getString("id_visita");
        mes = bundle.getString("mes");
        anhio = bundle.getString("anhio");
        ciudad = bundle.getString("ciudad");
        nombre = bundle.getString("nombre");
        codmed = bundle.getString("codmed");
        nombre_doctor = (TextView) this.findViewById(R.id.lbl_nombre_doctor);
        nombre_doctor.setText(nombre);
        tipo_positivo = bundle.getString("tipo_positivo");

        id_boleta = (TextView) this.findViewById(R.id.lbl_id_boleta);
        id_boleta.setText(id_visita);


             /*
        Esto nos servirá pare ver si la
         */
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/VISMED");
        if (!directory.isDirectory())
            directory.mkdirs();
        foto = Environment.getExternalStorageDirectory()+ "/VISMED/" +id_visita+ ".debbie_zuleta";

       File file = new File(foto);
        if(file.exists())
        {
            Toast.makeText(FormularioVisita.this,getString(R.string.ya_existe_firma_guardada),Toast.LENGTH_SHORT).show();
            bandera_existe_foto=1;
            ruta_imagen=foto;
        }
        else
        {
            Toast.makeText(FormularioVisita.this,getString(R.string.no_existe_firma_guardada),Toast.LENGTH_SHORT).show();
            bandera_existe_foto=0;
        }


/*
Para declarar el table layout
 */
        table_layout = (TableLayout) findViewById(R.id.tl_productos);


        /*
para hacer un select del detalle de la visita
 */
        vc=new Visitas_controlador(FormularioVisita.this,"visita_medica.sqlite" );
        detalle_visita=vc.select_detalle_visita_dado_el_id_visita(Codbarra,mes,anhio);
        actualizar_tabla();
    /*
    para poner como negativa
     */
        negativa = (Button) this.findViewById(R.id.btn_ir_a_negativa);
        if (tipo_positivo.equals("editar")) {
            negativa.setVisibility(View.GONE);
        } else {
            negativa.setVisibility(View.VISIBLE);
        }


        /*
        para mandar a visita negativa
         */
        negativa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Settings.System.getInt(getContentResolver(), Settings.System.AUTO_TIME)== 1)
                    {
                        intent = new Intent();
                        intent.setComponent(new ComponentName(FormularioVisita.this, VisitasNegativas.class));
                        intent.putExtra("id_visita", id_visita);
                        intent.putExtra("codigo_barra", Codbarra);
                        intent.putExtra("nombre_medico", nombre);
                        intent.putExtra("mes", mes);
                        intent.putExtra("anhio", anhio);
                        intent.putExtra("ciudad", ciudad);
                        intent.putExtra("latitude", Double.toString(latitudes));
                        intent.putExtra("longitude", Double.toString(longitudes));
                        startActivity(intent);
                    }
                    else {

                        Toast.makeText(FormularioVisita.this,getString(R.string.debe_poner_la_fecha_en_automatico),Toast.LENGTH_SHORT).show();
                        startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);

                    }
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            }


        });

/*
para cargar la firma
 */



        cargar_firma=(Button)this.findViewById(R.id.btn_captura_firma);
        cargar_firma.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(bandera_existe_foto.equals(1))
                {

                    new AlertDialog.Builder(FormularioVisita.this)
                            .setTitle(getString(R.string.cargar_firma))
                            .setMessage(getString(R.string.desea_sobreescribir_la_firma_guardada))
                            .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(FormularioVisita.this, CapturaFirma.class);
                                    i.putExtra("nombre_foto", id_visita);
                                    startActivityForResult(i, 2);


                                }
                            })
                            .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }
                else
                {
                    Intent i = new Intent(FormularioVisita.this, CapturaFirma.class);
                    i.putExtra("nombre_foto", id_visita);
                    startActivityForResult(i, 2);
                }


            }
        });

    }



    public void actualizar_tabla()
    {

        detalle_visita.add(0, new DetalleVisita("","","",getString(R.string.titulo_codigo),getString(R.string.cantidad),getString(R.string.producto),"",""));
        final TextView cantidad[] = new TextView[detalle_visita.size()];

        if (detalle_visita.size() > 1) {
            Toast.makeText(FormularioVisita.this,Codbarra+" "+ mes+" "+anhio, Toast.LENGTH_LONG).show();
            for (int i = 0; i < detalle_visita.size(); i++) {
                final int index = i;
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                TextView tv_codigo = new TextView(this);
                TextView tv_nombre_producto = new TextView(this);
                cantidad[i] = new TextView(this);

                tv_codigo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                GradientDrawable roundRectangle = (GradientDrawable) this.getResources().getDrawable(R.drawable.figuras);
                tv_codigo.setGravity(Gravity.CENTER);
                tv_codigo.setTextSize(10);
                tv_codigo.setText(detalle_visita.get(i).getCODIGOMM());


                tv_nombre_producto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                tv_nombre_producto.setGravity(Gravity.CENTER);
                tv_nombre_producto.setTextSize(10);
                tv_nombre_producto.setText(detalle_visita.get(i).getMM());


                cantidad[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                cantidad[i].setGravity(Gravity.CENTER);
                cantidad[i].setTextSize(10);
                cantidad[i].setText(detalle_visita.get(i).getCANTIDAD());

                if (i == 0) {
                    cantidad[i].setTypeface(Typeface.DEFAULT_BOLD);
                    tv_nombre_producto.setTypeface(Typeface.DEFAULT_BOLD);
                    tv_codigo.setTypeface(Typeface.DEFAULT_BOLD);
                }


                row.addView(tv_codigo);
                row.addView(tv_nombre_producto);
                row.addView(cantidad[i]);

                table_layout.addView(row);

            }

            Button guardar=new Button(this);
            guardar.setTextSize(10);
            guardar.setText(getString(R.string.guardar_visita_medica));
            table_layout.addView(guardar);
            guardar.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if (bandera_existe_foto == 0) {
                                                   Toast.makeText(FormularioVisita.this, getString(R.string.no_existe_firma_guardada), Toast.LENGTH_SHORT).show();
                                               } else{
                                                   new AlertDialog.Builder(FormularioVisita.this)
                                                           .setTitle(getString(R.string.guardar_visita_medica))
                                                           .setMessage(getString(R.string.leyenda_guardar_ahora_guardar_despues))
                                                           .setPositiveButton(getString(R.string.guardar_coordenadas_ahora), new DialogInterface.OnClickListener() {
                                                               public void onClick(DialogInterface dialog, int which) {

                                                                   Intent location_find = new Intent(FormularioVisita.this, EstoyAqui2.class);
                                                                   startActivityForResult(location_find, 1);

                                                               }
                                                           })
                                                        /*   .setNegativeButton(getString(R.string.guardar_coordenadas_despues), new DialogInterface.OnClickListener() {
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                if (fun.existe_error_con_id_boleta(FormularioVisita.this, id_visita)) {
                                                                    boletaje_auxiliar = vc.select_all_boletaje_auxiliar_where_estado_guardado_pendientes();
                                                                    if (boletaje_auxiliar.size() == 0) {
                                                                        System.out.println("Noy hay niinguna tarea auxiliar pendiente");
                                                                        boletaje_auxiliar.clear();
                                                                        boletaje_auxiliar = vc.select_all_boletaje_auxiliar_dado_id_visita(id_visita);
                                                                        if (boletaje_auxiliar.size() > 0) {

                                                                            System.out.println("Esta tarea auxiliar pendiente ya fue actualizada");
                                                                            Toast.makeText(FormularioVisita.this, getString(R.string.la_tarea_ya_fue_guardada), Toast.LENGTH_SHORT).show();
                                                                        } else {

                                                                            System.out.println("Esta tarea auxiliar pendiente NO fue actualizada y sera guardada");
                                                                            sacar_mas_diez_y_quince_minutos();
                                                                            llamar_a_funciones_alarma();
                                                                            guardar_boletaje_auxiliar();
                                                                            Toast.makeText(FormularioVisita.this, getString(R.string.tiene_quince_minutos_para_capturar_las_coordenadas), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } else {

                                                                        System.out.println("Hay tareas pendientes");


                                                                        boletaje_auxiliar.clear();
                                                                        boletaje_auxiliar = vc.select_all_boletaje_auxiliar_dado_id_visita(id_visita);
                                                                        if (boletaje_auxiliar.size() > 0) {

                                                                            System.out.println("Esta tarea auxiliar pendiente ya fue actualizada");
                                                                            Toast.makeText(FormularioVisita.this, getString(R.string.la_tarea_ya_fue_guardada), Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            sacar_mas_diez_y_quince_minutos();
                                                                            guardar_boletaje_auxiliar();
                                                                            Toast.makeText(FormularioVisita.this, getString(R.string.tiene_quince_minutos_para_capturar_las_coordenadas), Toast.LENGTH_SHORT).show();

                                                                        }

                                                                    }
                                                                    //Toast.makeText(FormularioVisita.this,getString(R.string.tiene_quince_minutos_para_capturar_las_coordenadas)  ,Toast.LENGTH_SHORT).show();
                                                                    guardar_y_salir();
                                                                }
                                                                 else
                                                                {
                                                                    Toast.makeText(FormularioVisita.this, getString(R.string.visita_guardadas), Toast.LENGTH_SHORT).show();
                                                                }


                                                               }
                                                           })*/
                                                           .setIcon(android.R.drawable.ic_dialog_alert)
                                                           .show();

                                           }
                                         }
                                       }
            );


        }
        else {

        }

    }

    public  void sacar_mas_diez_y_quince_minutos()
    {
        fecha_actual = fecha.format(fechaYhora.getTime());
        Date dateObj = null;
        try {
            dateObj = fecha.parse(fecha_actual);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObj);


        calendar.add(Calendar.MINUTE, 10);
        mas_diez_minutos=calendar;
        mas_diez_minutos.getTime();


        calendar = Calendar.getInstance();
        calendar.setTime(dateObj);

        calendar.add(Calendar.MINUTE, 15);
        mas_quince_minutos=calendar;
        mas_quince_minutos.getTime();

        System.out.println(fecha.format(mas_diez_minutos.getTime()) + "<3 3<" + fecha.format(mas_quince_minutos.getTime()));
    }


    public void llamar_a_funciones_alarma()
    {

        llamar_temporizador(mas_diez_minutos);
        llamar_temporizador_coordenadas_cero(mas_quince_minutos);
    }

    public void guardar_boletaje_auxiliar()
    {

        boletaje_auxiliar.clear();
        boletaje_auxiliar.add(new BoletajeAuxiliar(id_visita,
                Codbarra,
                fecha_actual,
                mes,
                anhio,
                fps.Consultar_si_hay_registro(FormularioVisita.this),
                "", //vamos a poner observaciones en vacío porque este campo solo será para observaciones
                observaciones.getText().toString(),
                ciudad,
                fun.convertir_de_double_a_string(latitudes),
                fun.convertir_de_double_a_string(longitudes),
                "0",
                "0",
                ruta_imagen,
                valor_visita_acompanhiada,
                fecha.format(mas_diez_minutos.getTime()),
                fecha.format(mas_quince_minutos.getTime()),
                "0",
                "0"
        ));
        vc.add_boletaje_auxiliar(boletaje_auxiliar);
    }


    public void guardar()
    {



            boletaje_auxiliar = vc.select_all_boletaje_auxiliar_dado_id_visita(id_visita);
            if (boletaje_auxiliar.size() > 0) {

                vc.update_estado_guardado_boletaje_auxiliar_dado_id_visita(id_visita);
                vc.update_estado_alarma_boletaje_auxiliar_dado_id_visita(id_visita);
            }
            else
            {

            }

/*
Comentar y descomentar esta opción para el alto
 */
       if(fun.existe_error_con_id_boleta(FormularioVisita.this,id_visita)) {
            fecha_actual = fecha.format(fechaYhora.getTime());
            Toast.makeText(FormularioVisita.this, getString(R.string.visita_guardadas), Toast.LENGTH_SHORT).show();
            fecha_actual = fecha.format(fechaYhora.getTime());
            vc.update_estado_visita(id_visita, "1");
            boletaje.add(new Boletaje(id_visita,
                    Codbarra,
                    fecha_actual,
                    mes,
                    anhio,
                    fps.Consultar_si_hay_registro(FormularioVisita.this),
                    "",////observaciones solo será negativa
                    observaciones.getText().toString()+"-"+string_observaciones_coordenadas,
                    ciudad,
                    fun.convertir_de_double_a_string(latitudes),
                    fun.convertir_de_double_a_string(longitudes),
                    "0",
                    "0",
                    ruta_imagen,
                    valor_visita_acompanhiada));
            vc.add_boletaje(boletaje);

            guardar_y_salir();
        }
        else
        {
          Toast.makeText(FormularioVisita.this,getString(R.string.cierre_la_boleta_intente_nuevamente_llame_al_departamento_de_sistemas),Toast.LENGTH_SHORT).show();
        }
    }



    public void guardar_y_salir()
    {

        menu_principal.primero.finish();
        tareas_pendientes.segundo.finish();
        finish();
        intent = new Intent(FormularioVisita.this, MenuPrincipal.class);
        startActivity(intent);
    }


    public void oops()
    {

        Toast.makeText(FormularioVisita.this,getString(R.string.oops_esto_es_vergonzoso),Toast.LENGTH_LONG).show();

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        try

        {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {


            case 1:
                if (resultCode == Activity.RESULT_OK && resultCode != FormularioVisita.this.RESULT_CANCELED) {
                    String string_latitude = data.getStringExtra("Latitude");
                    String string_longitude = data.getStringExtra("Longitude");
                    string_observaciones_coordenadas = data.getStringExtra("observaciones_coordenadas");

                    latitudes=fun.convertir_de_string_a_double(string_latitude);
                    longitudes=fun.convertir_de_string_a_double(string_longitude);



                    if(latitudes==0.0&&longitudes==0.0)
                    {
                     coordenadas_no_validas();
                    }
                    else
                    {
                        guardar();
                    }

                    }
             else{
                 coordenadas_no_validas();
                 }


                break;
            case 2:

                if (resultCode == Activity.RESULT_OK && resultCode != FormularioVisita.this.RESULT_CANCELED)
                {

                  ruta_imagen= data.getStringExtra("ruta_imagen");
                  bandera_existe_foto=1;



                }
                else
                {


                }


            break;

        }

        }
       catch (NullPointerException e)
        {
            coordenadas_no_validas();
        }
    }

    public void firma_no_guardada()
    {
    Toast.makeText(FormularioVisita.this, getString(R.string.la_firma_no_fue_guardada),Toast.LENGTH_SHORT).show();
    }


    public void coordenadas_no_validas()
    {
        Toast.makeText(FormularioVisita.this,getString(R.string.vuelva_a_intentar_obtener_coordenadas),Toast.LENGTH_SHORT).show();
    }

    public void llamar_temporizador(Calendar mas_diez_minutos)
    {
    final  int RQS_1 = 1;
    Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP, mas_diez_minutos.getTimeInMillis(),pendingIntent);


}


    public void llamar_temporizador_coordenadas_cero(Calendar mas_quince_minutos)
    {


        final  int RQS_1 = 1;
        Intent intent = new Intent(getBaseContext(), AlarmReceiverCoordenadasCero.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, mas_quince_minutos.getTimeInMillis(), pendingIntent);


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}