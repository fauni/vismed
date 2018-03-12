package visita_medica.lafar.com.visitamedica.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeAuxiliar;

public class PruebaComprobarFecha extends ActionBarActivity {

    ArrayList<BoletajeAuxiliar> boletaje_auxiliar= new ArrayList<BoletajeAuxiliar>();
    Visitas_controlador vc;

    Button guardar, mostrar, guardar_todo;
    EditText fecha;
    TextView mostrar_fecha;

    Integer contador=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_comprobar_fecha);
   vc=  new Visitas_controlador(PruebaComprobarFecha.this,  "visita_medica.sqlite");
        mostrar_fecha=(TextView)this.findViewById(R.id.lbl_mostrar);
        fecha=(EditText)this.findViewById(R.id.txt_fecha);

        guardar=(Button)this.findViewById(R.id.btn_guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contador = contador + 1;


                boletaje_auxiliar.add(new BoletajeAuxiliar(
                        Integer.toString(contador),//1  id_visita
                        Integer.toString(contador),//2  codbarra
                        fecha.getText().toString(),//3  fecha_celular
                        "",//4  mes
                        "",//5  anhio
                        "",//6  apm
                        "",//7  obser
                        "",//7/2 motivo_obser
                        "",//8  ciudad
                        "",//9  lat
                        "",//10 lon
                        "",//11 estado_subida
                        "",//12 negativo_editado
                        "",//13 ruta_imagen
                        "",//14 acompanhiado
                        "",//15 mas diez
                        "",//16 mas quince
                        "0",//17 estado_alarma
                        "0"//18 estado_guardado
                ));
        fecha.setText("");
            }
        });

        guardar_todo=(Button)this.findViewById(R.id.btn_guardar_todo);
        guardar_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vc.add_boletaje_auxiliar(boletaje_auxiliar);
            }
        });



        mostrar=(Button)this.findViewById(R.id.btn_mostrar);
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String todo="";
            boletaje_auxiliar.clear();
            boletaje_auxiliar=vc.select_all_boletaje_auxiliar_where_estado_alarma_and_estado_guardado_equals_pendientes();
             for (int i=0; i<boletaje_auxiliar.size();i++)
             {
                 todo=todo+boletaje_auxiliar.get(i).getFECHACELULAR()+"\n";
             }
           mostrar_fecha.setText(todo);

            }
        });


    }


}
