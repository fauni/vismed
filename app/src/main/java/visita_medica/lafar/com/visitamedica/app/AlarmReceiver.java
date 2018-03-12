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

public class AlarmReceiver extends BroadcastReceiver {


    Visitas_controlador vc;
    ArrayList<BoletajeAuxiliar> boletaje_auxiliar= new ArrayList<BoletajeAuxiliar>();
    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
    String id_visita;
    String fecha_mas_diez_minutos;
    String estado_alarma;


    //////////////Fecha y hora


    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;

    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub


        vc=new Visitas_controlador(k1,"visita_medica.sqlite");
        boletaje_auxiliar=vc.select_all_boletaje_auxiliar_where_estado_alarma_pendientes();
        if(boletaje_auxiliar.size()>0)
        {

            for (int i=0 ;i<boletaje_auxiliar.size();i++)
            {
                id_visita=boletaje_auxiliar.get(0).getId_visita();
                fecha_actual=boletaje_auxiliar.get(0).getFECHACELULAR();
                fecha_mas_diez_minutos=boletaje_auxiliar.get(0).getFecha_mas_diez_minutos();
                estado_alarma=boletaje_auxiliar.get(0).getEstado_alarma();

            }

            if(estado_alarma.equals("1"))
            {

            }
            else
            {
                final MediaPlayer mpsound = MediaPlayer.create(k1, R.raw.alarma);
                mpsound.start();{
                //sleep(2000);
                //  mpsound.stop();
            }

                Toast.makeText(k1, "No se olvide guardar la visita; "+ id_visita ,Toast.LENGTH_LONG).show();

                vc.update_estado_alarma_boletaje_auxiliar_dado_id_visita(id_visita);
                if(boletaje_auxiliar.size()>1)
                {

                    for (int i=0 ;i<boletaje_auxiliar.size();i++) {
                        id_visita = boletaje_auxiliar.get(1).getId_visita();
                        fecha_actual = boletaje_auxiliar.get(1).getFECHACELULAR();
                        fecha_mas_diez_minutos=boletaje_auxiliar.get(1).getFecha_mas_diez_minutos();
                        estado_alarma = boletaje_auxiliar.get(1).getEstado_alarma();
                    }

                    sacar_mas_diez_minutos(k1);

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


    public  void sacar_mas_diez_minutos(Context k1)
    {

        Date dateObj = null;
        try {
            dateObj = fecha.parse(fecha_mas_diez_minutos);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObj);



        llamar_temporizador_mas_diez_minutos(k1, calendar);


        System.out.println(":) "+ fecha_mas_diez_minutos);
    }





    public void llamar_temporizador_mas_diez_minutos(Context k1, Calendar mas_diez_minutos)
    {

        final  int RQS_1 = 1;
        Intent intent = new Intent(k1, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(k1, RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) k1.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, mas_diez_minutos.getTimeInMillis(), pendingIntent);


    }
}