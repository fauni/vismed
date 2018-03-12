package visita_medica.lafar.com.visitamedica.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeAuxiliar;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeHistorico;

public class AlarmReceiverCoordenadasCero extends BroadcastReceiver {
    Visitas_controlador vc;
    ArrayList<BoletajeAuxiliar> boletaje_auxiliar= new ArrayList<BoletajeAuxiliar>();
    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
    ArrayList<BoletajeHistorico> boletaje_historico= new ArrayList<BoletajeHistorico>();
    String id_visita;
    String fecha_mas_quince_minutos;
    String estado_guardado;


    /*
    Mas datos para guardar en la tabla boletaje
    */
    String Codbarra;
    String fecha_actual;
    String mes;
    String anhio;
    String apm;
    String observaciones;
    String motivo_obser;
    String ciudad;
    String latitud;
    String longitud;
    String estado_subida;
    String estado_negativa;
    String ruta_imagen;
    String acompanhiado;


    //////////////Fecha y hora

    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub


        vc=new Visitas_controlador(k1,"visita_medica.sqlite");
        boletaje_auxiliar=vc.select_all_boletaje_auxiliar_where_estado_guardado_pendientes();
        if(boletaje_auxiliar.size()>0)
        {

            for (int i=0 ;i<boletaje_auxiliar.size();i++)
            {
                // se toma en cuenta el id
                id_visita=boletaje_auxiliar.get(0).getId_visita();
                // *******************************
                Codbarra=boletaje_auxiliar.get(0).getCodbarra();
                // se toma en cuenta la fecha actual
                fecha_actual=boletaje_auxiliar.get(0).getFECHACELULAR();
                //***************************************
                mes=boletaje_auxiliar.get(0).getMESV();
                anhio=boletaje_auxiliar.get(0).getANOV();
                apm=boletaje_auxiliar.get(0).getAPM();
                observaciones=boletaje_auxiliar.get(0).getOBSER();
                motivo_obser=boletaje_auxiliar.get(0).getMOTIVO_OBSER();
                ciudad=boletaje_auxiliar.get(0).getCiudad();
                latitud=boletaje_auxiliar.get(0).getLat();
                longitud=boletaje_auxiliar.get(0).getLon();
                estado_subida=boletaje_auxiliar.get(0).getEstado_subida();
                estado_negativa=boletaje_auxiliar.get(0).getNegativo_editado();
                ruta_imagen=boletaje_auxiliar.get(0).getRuta_imagen();
                acompanhiado=boletaje_auxiliar.get(0).getAcompanhiado();

                // se toma en cuenta la fecha mas quince y  el estado guardado
                fecha_mas_quince_minutos=boletaje_auxiliar.get(0).getFecha_mas_quince_minutos();
                estado_guardado=boletaje_auxiliar.get(0).getEstado_guardado();
                //**************************************************************
            }

            if(estado_guardado.equals("1"))
            {

            }
            else
            {
                final MediaPlayer mpsound = MediaPlayer.create(k1, R.raw.recordatorio);
                mpsound.start();{
                //sleep(2000);
                //  mpsound.stop();
            }


                vc.update_estado_guardado_boletaje_auxiliar_dado_id_visita(id_visita);

                if(estado_negativa.equals("1"))
                {
                   guardar_historico(k1);
                }
                else
                {
                    guardar(k1);
                }
                if(boletaje_auxiliar.size()>1)
                {

                    for (int i=0 ;i<boletaje_auxiliar.size();i++) {
                        id_visita = boletaje_auxiliar.get(1).getId_visita();
                        fecha_actual = boletaje_auxiliar.get(1).getFECHACELULAR();
                        fecha_mas_quince_minutos = boletaje_auxiliar.get(1).getFecha_mas_quince_minutos();
                        estado_guardado = boletaje_auxiliar.get(1).getEstado_guardado();
                    }

                    sacar_mas_quince_minutos(k1);

                }
                else
                {



                }


            }

        }
        else
        {




        }


    }


    public  void sacar_mas_quince_minutos(Context k1)
    {

        Date dateObj = null;
        try {
            dateObj = fecha.parse(fecha_mas_quince_minutos);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObj);



        llamar_temporizador_mas_quince_minutos(k1, calendar);


        System.out.println(":) "+ fecha_mas_quince_minutos);
    }





    public void llamar_temporizador_mas_quince_minutos(Context k1, Calendar mas_quince_minutos)
    {

        final  int RQS_1 = 1;
        Intent intent = new Intent(k1, AlarmReceiverCoordenadasCero.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(k1, RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) k1.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, mas_quince_minutos.getTimeInMillis(), pendingIntent);


    }


    public void guardar(Context k1)
    {

        vc.update_estado_visita(id_visita, "1");
        boletaje.add(new Boletaje(
                id_visita,
                Codbarra,
                fecha_actual,
                mes,
                anhio,
                apm,
                observaciones,
                motivo_obser,
                ciudad,
                latitud,
                longitud,
                estado_subida,
                estado_negativa,
                ruta_imagen,
                acompanhiado));
        vc.add_boletaje(boletaje);

        //guardar_y_salir();

    }

    public void guardar_historico(Context K1)
    {


        boletaje=vc.select_boletaje_dado_codbarra(id_visita);

        String id_historico_auxiliar="";
        String id_visita_auxiliar="";
        String codbarra_auxiliar="";
        String fecha_celular_auxiliar="";
        String mesv_auxiliar="";
        String anhiov_auxiliar="";
        String apm_auxiliar="";
        String obser_auxiliar="";
        String motivo_obser_auxiliar="";
        String ciudad_auxiliar="";
        String lat_auxiliar="";
        String lon_auxiliar="";
        String acompanhiado_auxiliar="";


        for(int i=0;i<boletaje.size();i++)
        {
            id_historico_auxiliar=boletaje.get(i).getId_visita()+fecha_actual;
            id_visita_auxiliar=boletaje.get(i).getId_visita();
            codbarra_auxiliar=boletaje.get(i).getCodbarra();
            fecha_celular_auxiliar=boletaje.get(i).getFECHACELULAR();
            mesv_auxiliar=boletaje.get(i).getMESV();
            anhiov_auxiliar=boletaje.get(i).getANOV();
            apm_auxiliar=boletaje.get(i).getAPM();
            obser_auxiliar=boletaje.get(i).getOBSER();
            motivo_obser_auxiliar=boletaje.get(i).getMOTIVO_OBSER();
            ciudad_auxiliar=boletaje.get(i).getCiudad();
            lat_auxiliar=boletaje.get(i).getLat();
            lon_auxiliar=boletaje.get(i).getLon();
            acompanhiado_auxiliar=boletaje.get(i).getAcompanhiado();


        }

        boletaje_historico.add(new BoletajeHistorico(
                id_historico_auxiliar,
                id_visita_auxiliar,
                codbarra_auxiliar,
                fecha_celular_auxiliar,
                mesv_auxiliar,
                anhiov_auxiliar,
                apm_auxiliar,
                obser_auxiliar,
                motivo_obser_auxiliar,
                ciudad_auxiliar,
                lat_auxiliar,
                lon_auxiliar,
                acompanhiado_auxiliar,
                "0"
        ));


        vc.add_boletaje_historico(boletaje_historico);
        vc.delete_boletaje_dado_id(id_visita);
        boletaje.clear();
        vc.update_estado_visita(id_visita, "1");
        boletaje.add(new Boletaje(id_visita,
                Codbarra,
                fecha_actual,
                mes,
                anhio,
                apm,
                observaciones,
                motivo_obser,
                ciudad,
                latitud,
                longitud,
                estado_subida,
                estado_negativa,
                ruta_imagen,
                acompanhiado));
        vc.add_boletaje(boletaje);
    }

}