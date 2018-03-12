package visita_medica.lafar.com.visitamedica.app;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_visita_medica;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_visita_realizada;
import visita_medica.lafar.com.visitamedica.async.OperacionesLogin;
import visita_medica.lafar.com.visitamedica.async.OperacionesMiCobertura;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;

public class TareasRealizadas extends Activity {

    // /////////////////////variables del mapa

    private GoogleMap mapa = null;
    SupportMapFragment mMapFragment;


    Visitas_controlador vc;
    ListView visitas_realizadas;
    Adapter_visita_realizada adapter;
    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
    ArrayList<VisitaMedica> visita_medica= new ArrayList<VisitaMedica>();
    Funciones fun= new Funciones();


    ImageView volver, reporte_cobertura;

    Intent i;

    Button fecha, estado,sinc, todos;

    EditText buscador;

    public static Activity cuarto;

    SharedPreferencesP fps=new SharedPreferencesP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_realizadas);

        cuarto=TareasRealizadas.this;

        buscador= (EditText) findViewById(R.id.txt_buscador);
      /*  buscador.setFocusableInTouchMode(true);
        buscador.setFocusable(false);*/

        fecha=(Button)this.findViewById(R.id.btn_filtro_fecha);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i=new Intent(TareasRealizadas.this, PopupFiltros.class);
                i.putExtra("tipo_filtro","fecha");
                startActivityForResult(i, 1);
            }
        });

        sinc=(Button)this.findViewById(R.id.btn_filtro_sincronizadas);
        sinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i= new Intent(TareasRealizadas.this, PopupFiltros.class);
                i.putExtra("tipo_filtro","sinc");
                startActivityForResult(i, 1);

            }
        });
        estado=(Button)this.findViewById(R.id.btn_filtro_estado);
        estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i= new Intent(TareasRealizadas.this, PopupFiltros.class);
                i.putExtra("tipo_filtro","estado");
                startActivityForResult(i, 1);

            }
        });


        todos=(Button)this.findViewById(R.id.btn_filtro_todas);
        todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i= new Intent(TareasRealizadas.this, TareasRealizadas.class);
                startActivity(i);
                finish();
            }
        });




        volver=(ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        reporte_cobertura=(ImageView)this.findViewById(R.id.btn_cobertura);
        reporte_cobertura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  i= new Intent(TareasRealizadas.this, MiCobertura.class);
                i.putExtra("positivas","200");
                i.putExtra("negativas","42");
                i.putExtra("faltantes","48");
                i.putExtra("total","290");
                startActivity(i);*/

                if (!fun.verificaConexion(TareasRealizadas.this)) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.necesita_conexion, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                } else {

                    new OperacionesMiCobertura(TareasRealizadas.this,fps.Consultar_si_hay_registro(TareasRealizadas.this)).execute();
                }


            }
        });



        vc= new Visitas_controlador(TareasRealizadas.this, "visita_medica.sqlite");
        boletaje=vc.select_all_boletaje_for_listview();
        visitas_realizadas=(ListView)this.findViewById(R.id.lv_realizadas);
        llenar_lista();



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    String tipo = data.getStringExtra("tipo");
                    String valor = data.getStringExtra("valor");
                    System.out.println("TIPO "+tipo + " VALOR "+ valor);
                    llenar_lista_con_filtro(tipo, valor);
                }
                break;
            }

        }

    }



    public void llenar_lista_con_filtro(String tipo, String valor) {

        boletaje.clear();
        visitas_realizadas.invalidateViews();


        if(tipo.equals("fecha"))
        {
         boletaje=vc.select_boletaje_where_fecha_like_for_listview(valor);
        }
        else if(tipo.equals("estado"))
        {

            if(valor.equals("positivas"))
            {
                boletaje=vc.select_boletaje_with_visita_estado_positiva_for_listview();
            }
            else
            {
                boletaje=vc.select_boletaje_with_visita_estado_negativa_for_listview();
            }
        }

        else if(tipo.equals("sinc"))
        {
           if(valor.equals("sincronizadas"))
           {
               boletaje=vc.select_boletaje_where_estado_not_pendiete_for_listview();
           }
            else
           {
               boletaje=vc.select_boletaje_where_estado_pendiete_for_listview();
           }

        }
        else
        {

        }

        adapter=new Adapter_visita_realizada(this,R.layout.list_item_visita_realizada,boletaje);
        visitas_realizadas.setAdapter(adapter);


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



        visitas_realizadas.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id_visita =((Boletaje)parent.getItemAtPosition(position)).getId_visita();
                String Codbarra =((Boletaje)parent.getItemAtPosition(position)).getCodbarra();
                String mes =((Boletaje)parent.getItemAtPosition(position)).getMESV();
                String otro_anhio=((Boletaje)parent.getItemAtPosition(position)).getANOV();


                Toast.makeText(TareasRealizadas.this, Codbarra, Toast.LENGTH_SHORT).show();

                visita_medica=vc.select_visita_medica_dado_id_visita(id_visita);
                String estado=null;
                for(int i=0;i<visita_medica.size();i++)
                {
                    estado=visita_medica.get(i).getEstado();
                }


                 i= new Intent();
                i.setComponent(new ComponentName(TareasRealizadas.this, FichaInformacionIndividual.class));
                i.putExtra("id_visita", id_visita);
                i.putExtra("Codbarra",Codbarra);
                i.putExtra("mes",mes);
                i.putExtra("estado",estado);
                i.putExtra("anhio",otro_anhio);
                startActivity(i);

            }
        });

    }



    public void llenar_lista() {


        adapter=new Adapter_visita_realizada(this,R.layout.list_item_visita_realizada,boletaje);
        visitas_realizadas.setAdapter(adapter);


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


        visitas_realizadas.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String id_visita =((Boletaje)parent.getItemAtPosition(position)).getId_visita();
        String mes =((Boletaje)parent.getItemAtPosition(position)).getMESV();
        String Codbarra =((Boletaje)parent.getItemAtPosition(position)).getCodbarra();
                String otro_anhio=((Boletaje)parent.getItemAtPosition(position)).getANOV();
          visita_medica=vc.select_visita_medica_dado_id_visita(id_visita);
                String estado=null;
          for(int i=0;i<visita_medica.size();i++)
          {
          estado=visita_medica.get(i).getEstado();
          }


        i= new Intent();
        i.setComponent(new ComponentName(TareasRealizadas.this, FichaInformacionIndividual.class));
        i.putExtra("id_visita",id_visita);
        i.putExtra("mes",mes);
        i.putExtra("Codbarra",Codbarra);
        i.putExtra("estado",estado);
         i.putExtra("anhio",otro_anhio);
        startActivity(i);

            }
        });

    }



}


