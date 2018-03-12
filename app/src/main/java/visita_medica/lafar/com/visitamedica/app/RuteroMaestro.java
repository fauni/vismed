package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;

public class RuteroMaestro extends Activity {

    Visitas_controlador vc;
    ArrayList<VisitaMedica> visita_medica=new ArrayList<VisitaMedica>();
    TableLayout tableLayout;

    ImageView btn_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutero_maestro);

        btn_volver=(ImageView)this.findViewById(R.id.btn_volver);
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        vc= new Visitas_controlador(RuteroMaestro.this,"visita_medica.sqlite");
        tableLayout=(TableLayout)this.findViewById(R.id.tly_rutero_maestro);
        llenar_tabla();

    }
public void llenar_tabla()
{



    visita_medica=vc.select_visitas_dado_semana_visita("1");
    if(visita_medica.size()>0)
    {
        llenar_por_semanas(visita_medica, "1");

    }
    visita_medica.clear();


    visita_medica=vc.select_visitas_dado_semana_visita("2");
    if(visita_medica.size()>0)
    {
        llenar_por_semanas(visita_medica, "2");

    }
    visita_medica.clear();


    visita_medica=vc.select_visitas_dado_semana_visita("3");
    if(visita_medica.size()>0)
    {
        llenar_por_semanas(visita_medica, "3");

    }
    visita_medica.clear();

    visita_medica=vc.select_visitas_dado_semana_visita("4");
    if(visita_medica.size()>0)
    {
        llenar_por_semanas(visita_medica, "4");

    }
    visita_medica.clear();

    visita_medica=vc.select_visitas_dado_semana_visita("5");
    if(visita_medica.size()>0)
    {
        llenar_por_semanas(visita_medica, "5");

    }
    visita_medica.clear();


}



    public void llenar_por_semanas(ArrayList<VisitaMedica> visita_medica ,String s_semana)
    {
        TableRow row_semana = new TableRow(this);
        row_semana.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row_semana.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView semana= new TextView(this);

        if(s_semana.equals("1"))
        {
            semana.setText(getString(R.string.semana1));
        }
        else if(s_semana.equals("2"))
        {
            semana.setText(getString(R.string.semana2));
        }
        else  if(s_semana.equals("3"))
        {
            semana.setText(getString(R.string.semana3));
        }
        else if(s_semana.equals("4"))
        {
            semana.setText(getString(R.string.semana4));
        }
        else if(s_semana.equals("5"))
        {
            semana.setText(getString(R.string.semana5));
        }
        else
        {

        }
        semana.setGravity(Gravity.CENTER_HORIZONTAL);
        semana.setTextSize(14);
        semana.setTypeface(Typeface.DEFAULT_BOLD);
        row_semana.addView(semana);
        tableLayout.addView(row_semana);



        visita_medica.add(0,new VisitaMedica((getString(R.string.codigo_de_barra)),"","","","",getString(R.string.nombre_de_medico),"","","","","","","","",(getString(R.string.dia_visita)),"",""));
        final TextView dia_visita[] = new TextView[visita_medica.size()];
        final TextView id_visita[] = new TextView[visita_medica.size()];
        final TextView nombre_med[] = new TextView[visita_medica.size()];
        for (int i = 0; i < visita_medica.size(); i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            dia_visita[i] = new TextView(this);
            dia_visita[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
            dia_visita[i].setGravity(Gravity.CENTER);
            dia_visita[i].setTextSize(10);
            dia_visita[i].setText(visita_medica.get(i).getDia_visita());



            id_visita[i] = new TextView(this);
            id_visita[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
            id_visita[i].setGravity(Gravity.CENTER);
            id_visita[i].setTextSize(10);
            id_visita[i].setText(visita_medica.get(i).getId_visita());



            nombre_med[i] = new TextView(this);
            nombre_med[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
            nombre_med[i].setGravity(Gravity.CENTER);
            nombre_med[i].setTextSize(10);
            nombre_med[i].setText(visita_medica.get(i).getNOMBREMED());



            if (i == 0) {
                dia_visita[i].setTypeface(Typeface.DEFAULT_BOLD);
                id_visita[i].setTypeface(Typeface.DEFAULT_BOLD);
                nombre_med[i].setTypeface(Typeface.DEFAULT_BOLD);
            }

            // row.addView(semana1);
            row.addView(dia_visita[i]);
            row.addView(id_visita[i]);
            row.addView(nombre_med[i]);

            tableLayout.addView(row);

        }

    }
}
