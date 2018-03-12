package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.async.*;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.*;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeAuxiliar;
import visita_medica.lafar.com.visitamedica.objetos.MM;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;


public class MenuPrincipal extends Activity {

    Button pendientes, realizadas,  sincronizar,  desloguearme, salir,banco_muestras, backup, justificadas;
    private ClickButtonsCall buttonsCall;
    Visitas_controlador vc;
    SharedPreferencesP fps=  new SharedPreferencesP();
    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
   ArrayList<VisitaMedica> visita= new ArrayList<VisitaMedica>();
    ArrayList<MM> mm= new ArrayList<MM>();

    Integer tareas_a_sincronizar;
   // Integer banco_muestras_a_sincronizar;
    public static Activity primero;

    //////////////Fecha y hora

   /* private Calendar fechaYhora = Calendar.getInstance();
    Calendar mas_diez_minutos,mas_quince_minutos;
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;
    ArrayList<BoletajeAuxiliar> boletaje_auxiliar= new ArrayList<BoletajeAuxiliar>();*/

      Funciones fun= new Funciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        primero=MenuPrincipal.this;

        vc= new Visitas_controlador(MenuPrincipal.this, "visita_medica.sqlite");


        tareas_a_sincronizar=vc.select_count_boletaje_pendiente();
        /*esto solo se hacia porque supervision no podia realizar boletas
        banco_muestras_a_sincronizar=vc.contar_banco_muestras_sin_sincronizar_supervisor();
         */
        ConfigButtons();


     /*
      el supervisor igual podra hacer banco de muestras
      if(fun.existen_tareas_auxiliares(MenuPrincipal.this))
        {
        llamar_alarmas();
        }
        else
        {

        }*/

    }



    /*public void llamar_alarmas()
    {

        fecha_actual = fecha.format(fechaYhora.getTime());

     String id_visita_alarma;String fecha_alarma="";
     String id_visita_guardado;String fecha_guardado="";

        Date date_alarma = null,date_guardado=null, date_fecha_actual=null;

        boletaje_auxiliar=vc.select_all_boletaje_auxiliar_where_estado_alarma_pendientes();
        for(int i=0;i<boletaje_auxiliar.size();i++)
        {
            fecha_alarma=boletaje_auxiliar.get(0).getFecha_mas_diez_minutos();
            id_visita_alarma=boletaje_auxiliar.get(0).getId_visita();

        }
        boletaje_auxiliar.clear();

        boletaje_auxiliar=vc.select_all_boletaje_auxiliar_where_estado_guardado_pendientes();
        for(int i=0;i<boletaje_auxiliar.size();i++)
        {
            fecha_guardado=boletaje_auxiliar.get(0).getFecha_mas_quince_minutos();
            id_visita_guardado=boletaje_auxiliar.get(0).getId_visita();

        }


        try {
           date_alarma= fecha.parse(fecha_alarma);
           date_guardado = fecha.parse(fecha_guardado);
            date_fecha_actual = fecha.parse(fecha_actual);

            System.out.println(fecha.format(date_alarma));
            System.out.println(fecha.format(date_guardado));
            System.out.println(fecha.format(date_fecha_actual));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date_alarma.compareTo(date_fecha_actual)<0)
        {
            System.out.println("fecha_2 is Greater than my fecha_alarma");
        }
        else
        {

        }

        if (date_guardado.compareTo(date_fecha_actual)<0)
        {
            System.out.println("fecha_2 is Greater than my fecha_guardado");
        }
        else
        {

        }

    }*/



    private void ConfigButtons(){

        pendientes=(Button)this.findViewById(R.id.btn_pendientes);
        realizadas=(Button)this.findViewById(R.id.btn_realizadas);
        sincronizar=(Button)this.findViewById(R.id.btn_sincronizar_visitas);
        sincronizar.setText(getString(R.string.sincronizar_visitas)+ " ("+tareas_a_sincronizar+") ");

        //SINCRONIZAR SOLO TENDRA TAREAS A SINCRONIZAR
       /* if(fps.Consultar_tipo(MenuPrincipal.this).equals("visitador"))
        {

        }
        else if(fps.Consultar_tipo(MenuPrincipal.this).equals("supervisor"))
        {


            sincronizar.setText(getString(R.string.sincronizar_visitas) + " (" + banco_muestras_a_sincronizar + ") ");
        }
        else
        {

        }*/


        desloguearme=(Button)this.findViewById(R.id.btn_desloguearme);
        salir=(Button)this.findViewById(R.id.btn_salir);
        banco_muestras=(Button)this.findViewById(R.id.btn_banco_de_muestras);
        backup=(Button)this.findViewById(R.id.btn_backup);
        justificadas=(Button)this.findViewById(R.id.btn_justificadas);

        buttonsCall = new ClickButtonsCall();
        pendientes.setOnClickListener(buttonsCall);
        realizadas.setOnClickListener(buttonsCall);
        sincronizar.setOnClickListener(buttonsCall);
        desloguearme.setOnClickListener(buttonsCall);
        salir.setOnClickListener(buttonsCall);
        justificadas.setOnClickListener(buttonsCall);
        banco_muestras.setOnClickListener(buttonsCall);
        backup.setOnClickListener(buttonsCall);





      if(fps.Consultar_tipo(MenuPrincipal.this).equals("visitador"))
        {
            justificadas.setVisibility(View.GONE);
        }
        else if(fps.Consultar_tipo(MenuPrincipal.this).equals("supervisor"))
        {

        }
        else
        {

        }


    }
    private class ClickButtonsCall implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;

            switch (v.getId()) {
                case R.id.btn_pendientes:


                    int contador_pendientes = 0;
                    int contador_tareas = 0;


                    visita = vc.select_visita_medica_pendiente();
                    for (int i = 0; i < visita.size(); i++) {
                        contador_pendientes = contador_pendientes + 1;
                    }

                    visita = vc.select_all_visita_medica();

                    for (int i = 0; i < visita.size(); i++) {
                        contador_tareas = contador_tareas + 1;
                    }


                    if (contador_pendientes > 0 && contador_tareas > 0) {
                        pasar_directo();
                    } else if (contador_pendientes == 0 && contador_tareas > 0) {
                        descarga_tareas();
                    } else if (contador_pendientes > 0 && contador_tareas == 0) {
                        pasar_directo();
                    } else if (contador_pendientes == 0 && contador_tareas == 0) {
                        descarga_tareas();
                    } else {

                    }

                    break;
                case R.id.btn_realizadas:

             Integer contador_tareas_realizadas=0;
                    contador_tareas_realizadas=vc.select_count_all_tareas_realizadas();

if(contador_tareas_realizadas==0) {

    if (!fun.verificaConexion(MenuPrincipal.this)) {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.necesita_conexion, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    } else {
        new OperacionesDescargaRealizadasNegativas(MenuPrincipal.this, fps.Consultar_si_hay_registro(MenuPrincipal.this)).execute();
    }

}
                    else
{

    intent = new Intent(MenuPrincipal.this, TareasRealizadas.class);
    startActivity(intent);
}

                    break;
                case R.id.btn_sincronizar_visitas:

                    if(!fun.verificaConexion(MenuPrincipal.this))
                    {
                        Toast toast =Toast.makeText(getApplicationContext(), R.string.necesita_conexion,Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                    }
                    else {

                       /* if(fps.Consultar_tipo(MenuPrincipal.this).equals("visitador"))
                        {*/
                            new OperacionesGuardarVisitaPositiva(MenuPrincipal.this, fps.Consultar_si_hay_registro(MenuPrincipal.this)).execute();
                        /*}
                        else if(fps.Consultar_tipo(MenuPrincipal.this).equals("supervisor"))
                        {
                           if(banco_muestras_a_sincronizar>0)
                           {
                               new OperacionesGuardarBancoMuestrasSupervisor(MenuPrincipal.this, fps.Consultar_si_hay_registro(MenuPrincipal.this)).execute();
                           }
                            else
                           {

                           }
                        }
                        else
                        {

                        }*/


                    }

                    break;
                case R.id.btn_desloguearme:

                    //Toast.makeText(MenuPrincipal.this,getString(R.string.opcion_no_permitida_en_esta_version) ,Toast.LENGTH_SHORT).show();
                    fps.Borrar(MenuPrincipal.this);
                    fps.Borrar_tipo(MenuPrincipal.this);
                    vc.delete_boletaje();
                    vc.delete_apm();
                    vc.delete_boletaje_auxiliar();
                    vc.delete_detalle_banco_muestras();
                    vc.delete_detalle_visitas();
                    vc.delete_medico();
                    vc.delete_muestra_medica();
                    vc.delete_banco_muestras();
                    vc.delete_visitas();
                    vc.delete_justificadas();
                    vc.delete_boletaje_historico();
                    finish();

                    break;

                case R.id.btn_salir:

                    stopService(new Intent(MenuPrincipal.this, LocationService.class));
                    finish();
                    break;

                case R.id.btn_backup:



                    Intent a;
                    a = new Intent(MenuPrincipal.this, FiltroBackup.class);
                    startActivityForResult(a, 1);


                break;


                case R.id.btn_justificadas:

                    if(!fun.verificaConexion(MenuPrincipal.this))
                    {
                        Toast toast =Toast.makeText(getApplicationContext(), R.string.necesita_conexion,Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                    }
                    else {
                        new OperacionesDescargarNegativasJustificadasSupervisor(MenuPrincipal.this, fps.Consultar_si_hay_registro(MenuPrincipal.this)).execute();

                    }
                    break;
                case R.id.btn_banco_de_muestras:

                    Intent i;

                    if(fps.Consultar_tipo(MenuPrincipal.this).equals("visitador"))
                    {
                        if(!fun.verificaConexion(MenuPrincipal.this))
                        {
                            Toast toast =Toast.makeText(getApplicationContext(), R.string.necesita_conexion,Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                        }
                        else
                        {
                            new OperacionesDescargaBancoMuestras(MenuPrincipal.this, fps.Consultar_si_hay_registro(MenuPrincipal.this)).execute();
                        }
                    }
                    else if(fps.Consultar_tipo(MenuPrincipal.this).equals("supervisor"))
                    {
                     i= new Intent(MenuPrincipal.this,BancoMuestras.class);
                        startActivity(i);
                    }
                    else
                    {

                    }

                    break;

            }

        }

    }

    public void descarga_tareas()
    {
        if (!fun.verificaConexion(MenuPrincipal.this)) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.necesita_conexion, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
        } else {

            new OperacionesCargarVisita(MenuPrincipal.this, fps.Consultar_si_hay_registro(MenuPrincipal.this)).execute();
        }
    }
    public void pasar_directo()
    {
        Intent i= new Intent(MenuPrincipal.this,TareasPendientes.class);
        startActivity(i);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:

                if (resultCode == Activity.RESULT_OK && resultCode != MenuPrincipal.this.RESULT_CANCELED)
                {
                    String fecha= data.getStringExtra("fecha");
                    String apm=fps.Consultar_si_hay_registro(MenuPrincipal.this);
                    String nombre_documento=apm+"_"+fecha;
                    String ruta_documento;

                    File sdCard = Environment.getExternalStorageDirectory();
                    File directory = new File(sdCard.getAbsolutePath() + "/VISMED/backup");
                    if (!directory.isDirectory())
                        directory.mkdirs();
                    ruta_documento= Environment.getExternalStorageDirectory()+ "/VISMED/backup/" +nombre_documento+ ".txt";
                    File myFile = new File(ruta_documento);


                    try {




                        myFile.createNewFile();
                        FileOutputStream fOut = new FileOutputStream(myFile);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        myOutWriter.append(devuelve_cadena_json(fecha));
                        myOutWriter.flush();
                        myOutWriter.close();
                        fOut.close();

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                            new String[]{"dzuleta@lafar.net"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "BACKUP DE DATOS DEL DIA:" +fecha);
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "En el adjunto se le manda el backup");
                    Uri myUri = Uri.parse("file://" + ruta_documento);
                    emailIntent.putExtra(Intent.EXTRA_STREAM ,myUri);
                    emailIntent.setType("plain/text");
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.no_se_eligio_fecha_para_backup, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }


            break;


        }

    }




    public String devuelve_cadena_json(String fecha) throws Exception
    {


        boletaje=vc.select_boletaje_where_fecha_like(fecha);
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("id_visitador",fps.Consultar_si_hay_registro(MenuPrincipal.this));
        JSONArray jsonArray= new JSONArray();
        if (boletaje != null) {
            for (int i = 0; i < boletaje.size(); i++) {

                String ba1;
                String ruta_imagen;

                if(boletaje.get(i).getRuta_imagen().equals(""))
                {

                    ba1="";
                    ruta_imagen="";
                }

                else {
                Bitmap bitmap = BitmapFactory.decodeFile(boletaje.get(i).getRuta_imagen());
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba = bao.toByteArray();
                ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
                ruta_imagen=boletaje.get(i).getRuta_imagen()+".jpg";
                }
                String id_historico=boletaje.get(i).getId_visita()+boletaje.get(i).getFECHACELULAR();
                String estado;
                if(boletaje.get(i).getNegativo_editado().equals("1"))
                {
                    estado="3";
                }
                else
                {
                    estado=vc.select_estado_dado_id_visita(boletaje.get(i).getId_visita());
                }

                System.out.println("<3 <3 <3 <3 <3 <3 "+boletaje.get(i).getId_visita());

                JSONObject  object =  new JSONObject();
                object.put("id_historico",id_historico);
                object.put("id_visita",boletaje.get(i).getId_visita());
                object.put("Codbarra",boletaje.get(i).getCodbarra());
                object.put("FECHACELULAR",boletaje.get(i).getFECHACELULAR());
                object.put("MESV",boletaje.get(i).getMESV());
                object.put("ANOV",boletaje.get(i).getANOV());
                object.put("APM",boletaje.get(i).getAPM());
                object.put("OBSER",boletaje.get(i).getOBSER());
                object.put("ciudad",boletaje.get(i).getCiudad());
                object.put("lat",boletaje.get(i).getLat());
                object.put("lon", boletaje.get(i).getLon());
                object.put("estado_visita",vc.select_estado_dado_id_visita(boletaje.get(i).getId_visita()));
                object.put("negativa_editado",boletaje.get(i).getNegativo_editado());
                object.put("ruta_imagen",ruta_imagen);
                object.put("acompanhiado", boletaje.get(i).getAcompanhiado());
                object.put("imagen",ba1);
                object.put("estado",estado);


                jsonArray.put(object);




            }

            jsonObject.put("boletaje", jsonArray);
            return jsonObject.toString();
        }
        else
        {
            return null;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            stopService(new Intent(MenuPrincipal.this, LocationService.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
