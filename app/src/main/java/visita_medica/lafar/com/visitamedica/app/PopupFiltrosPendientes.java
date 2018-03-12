package visita_medica.lafar.com.visitamedica.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;

public class PopupFiltrosPendientes extends ActionBarActivity {

    Bundle bundle;
    String tipo_filtro;
    LinearLayout layout_fecha,  layout_lista;

    ArrayList<String> filtro= new ArrayList<String>();
    ArrayList<VisitaMedica> visita_medica=new ArrayList<VisitaMedica>();

    Visitas_controlador vc;

    ListView lv_filtro;

    ArrayAdapter adapter;

    String dia_hoy, dia_uno,dia_dos,  dia_tres,  dia_cuatro;
    /*
    SOLO POR EL MES DE JULIO
     */
    //String dia_cinco, dia_seis, dia_siete,dia_ocho,dia_nueve;
    /*
    FIN SOLO POR EL MES DE JULIO
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_filtros_pendientes);

        android.support.v7.app.ActionBar AB=getSupportActionBar();
        AB.hide();

        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        layout_fecha=(LinearLayout)this.findViewById(R.id.ly_filtro_fecha);
        layout_lista=(LinearLayout)this.findViewById(R.id.ly_filtro_cate_o_espes);



        bundle= getIntent().getExtras();
        tipo_filtro=bundle.getString("tipo_filtro");

        dia_hoy=bundle.getString("dia_hoy");
        dia_uno=bundle.getString("dia_uno");
        dia_dos=bundle.getString("dia_dos");
        dia_tres=bundle.getString("dia_tres");
        dia_cuatro=bundle.getString("dia_cuatro");

        /*
        SOLO POR EL MES DE JULIO
         */
        /*dia_cinco=bundle.getString("dia_cinco");
        dia_seis=bundle.getString("dia_seis");
        dia_siete=bundle.getString("dia_siete");
        dia_ocho=bundle.getString("dia_ocho");
        dia_nueve=bundle.getString("dia_nueve");
        */
        /*
        SOLO POR EL MES DE JULIO
         */


        vc= new Visitas_controlador(PopupFiltrosPendientes.this,"visita_medica.sqlite");
        //visita_medica=vc.select_visita_medica_pendiente_listado(dia_hoy,  dia_uno, dia_dos,dia_tres, dia_cuatro,dia_cinco,dia_seis,dia_siete,dia_ocho,dia_nueve );

        visita_medica=vc.select_visita_medica_pendiente_listado(dia_hoy,  dia_uno, dia_dos,dia_tres, dia_cuatro);

        lv_filtro=(ListView)this.findViewById(R.id.listView_filtro_pendientes);

        if (tipo_filtro.equals("especialidad"))
        {

            layout_fecha.setVisibility(View.GONE);
            layout_lista.setVisibility(View.VISIBLE);
            for (int i =0;i<visita_medica.size();i++)
            {
            filtro.add(visita_medica.get(i).getESPE1());
            }
            filtro = new ArrayList<String>(new HashSet<String>(filtro));
           llenar_lista();
        }
        else if (tipo_filtro.equals("categoria"))
        {
            layout_lista.setVisibility(View.VISIBLE);
            layout_fecha.setVisibility(View.GONE);

            for (int i =0;i<visita_medica.size();i++)
            {
                filtro.add(visita_medica.get(i).getCate());
            }
            filtro = new ArrayList<String>(new HashSet<String>(filtro));
            llenar_lista();

        }
        else if (tipo_filtro.equals("fecha"))
        {
            layout_fecha.setVisibility(View.VISIBLE);
            layout_lista.setVisibility(View.GONE);
        }
        else
        {

        }
    }


    public void llenar_lista()
    {
        adapter = new ArrayAdapter<String>(PopupFiltrosPendientes.this,  android.R.layout.simple_list_item_1, filtro);
        lv_filtro.setAdapter(adapter);

        lv_filtro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String valor_filtro =parent.getItemAtPosition(position).toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("tipo",tipo_filtro);
                resultIntent.putExtra("valor",valor_filtro);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();


            }
        });

    }

}
