package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_visita_medica;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;

public class TareasPendientes extends Activity {


    ArrayList<VisitaMedica> visita_medica=  new ArrayList<VisitaMedica>();
    ListView lista;
    Visitas_controlador vc;
    Intent i=null;
    EditText buscador;
    Adapter_visita_medica adapter;
    Integer dia_hoy;
    Integer dia_uno;
    Integer dia_dos;
    Integer dia_tres;
    Integer dia_cuatro;
    /*
    SOLO POR EL MES DE JULIO QUE DURo HASTA SEPTIEMBRE
     */
    //Integer dia_cinco,dia_seis, dia_siete, dia_ocho,  dia_nueve;

    /*
    FIN DE SOLO POR EL MES DE JULIO
     */
    public static Activity segundo;


    Button fecha, todas, categoria,  especialidad;

    ImageView volver, rutero_maestro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_pendientes);

        segundo=TareasPendientes.this;


        volver=(ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        rutero_maestro=  (ImageView)this.findViewById(R.id.btn_rutero_maestro);
        rutero_maestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i =  new Intent(TareasPendientes.this , RuteroMaestro.class);
                startActivity(i);


            }
        });

        vc= new Visitas_controlador(TareasPendientes.this, "visita_medica.sqlite");




        lista=(ListView)this.findViewById(R.id.lv_pendientes);
        llenar_lista();



        fecha=(Button)this.findViewById(R.id.btn_filtro_fecha);
        todas=(Button)this.findViewById(R.id.btn_filtro_todas);
        especialidad=(Button)this.findViewById(R.id.btn_filtro_especialidad);
        categoria=(Button)this.findViewById(R.id.btn_filtro_categoria);

        fecha.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i= new Intent(TareasPendientes.this, PopupFiltrosPendientes.class);
                i.putExtra("tipo_filtro","fecha");
                startActivityForResult(i, 1);
            }
        });

        todas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                i=new Intent(TareasPendientes.this, TareasPendientes.class);
                startActivity(i);
            }
        });

        especialidad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i= new Intent(TareasPendientes.this, PopupFiltrosPendientes.class);
                i.putExtra("tipo_filtro","especialidad");
                i.putExtra("dia_hoy",String.valueOf(dia_hoy));
                i.putExtra("dia_uno",String.valueOf(dia_uno));
                i.putExtra("dia_dos",String.valueOf(dia_dos));
                i.putExtra("dia_tres",String.valueOf(dia_tres));
                i.putExtra("dia_cuatro",String.valueOf(dia_cuatro));

                /*
                SOLO POR JULIO QUE DURO HASTA SEPTIEMBRE
                */
                /*i.putExtra("dia_cinco",String.valueOf(dia_cinco));
                i.putExtra("dia_seis",String.valueOf(dia_seis));
                i.putExtra("dia_siete",String.valueOf(dia_siete));
                i.putExtra("dia_ocho",String.valueOf(dia_ocho));
                i.putExtra("dia_nueve",String.valueOf(dia_nueve));*/

                /*
                SOLO POR JULIO
                 */

                startActivityForResult(i, 1);
            }
        });


        categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(TareasPendientes.this, PopupFiltrosPendientes.class);
                i.putExtra("tipo_filtro", "categoria");
                i.putExtra("dia_hoy",String.valueOf(dia_hoy));
                i.putExtra("dia_uno",String.valueOf(dia_uno));
                i.putExtra("dia_dos",String.valueOf(dia_dos));
                i.putExtra("dia_tres",String.valueOf(dia_tres));
                i.putExtra("dia_cuatro",String.valueOf(dia_cuatro));

                   /*
                SOLO POR JULIO QUE DURO HASTA SEPTIEMBRE
                */
               /* i.putExtra("dia_cinco",String.valueOf(dia_cinco));
                i.putExtra("dia_seis",String.valueOf(dia_seis));
                i.putExtra("dia_siete",String.valueOf(dia_siete));
                i.putExtra("dia_ocho",String.valueOf(dia_ocho));
                i.putExtra("dia_nueve",String.valueOf(dia_nueve));
                */
                /*
                SOLO POR JULIO
                 */
                startActivityForResult(i, 1);
            }
        });

    }

    public void llenar_lista() {

    
        visita_medica=vc.select_visita_medica_pendiente();

        if(visita_medica.size()==0)
        {

            dia_hoy=0;
            dia_uno =0;
            dia_dos = 0;
            dia_tres = 0;
            dia_cuatro = 0;
           /* dia_cinco=0;
            dia_seis=0;
            dia_siete=0;
            dia_ocho=0;
            dia_nueve=0;*/


        }
        else {
            for (int i = 0; i < visita_medica.size(); i++) {
                dia_hoy = Integer.parseInt(visita_medica.get(0).getDia_visita());
            }


            dia_uno = dia_hoy + 1;
            dia_dos = dia_hoy + 2;
            dia_tres = dia_hoy + 3;
            dia_cuatro = dia_hoy + 4;

/*
SOLO POR EL MES DE JUNLIO
 */

           /* dia_cinco = dia_hoy + 5;
            dia_seis = dia_hoy + 6;
            dia_siete = dia_hoy + 7;
            dia_ocho = dia_hoy + 8;
            dia_nueve = dia_hoy + 9;
    */

            /*
            FIN DE SOLO POR EL MES DE JULIO
             */

        }
        visita_medica.clear();
        //visita_medica=vc.select_visita_medica_pendiente_listado(String.valueOf(dia_hoy),String.valueOf(dia_uno),String.valueOf(dia_dos),String.valueOf(dia_tres),String.valueOf(dia_cuatro),String.valueOf(dia_cinco),String.valueOf(dia_seis),String.valueOf(dia_siete),String.valueOf(dia_ocho),String.valueOf(dia_nueve));
        visita_medica=vc.select_visita_medica_pendiente_listado(String.valueOf(dia_hoy),String.valueOf(dia_uno),String.valueOf(dia_dos),String.valueOf(dia_tres),String.valueOf(dia_cuatro));


        buscador= (EditText) findViewById(R.id.txt_buscador);
        adapter=new Adapter_visita_medica(this,R.layout.list_item_visita_medica,visita_medica);
        lista.setAdapter(adapter);

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



        lista.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final LocationManager manager = (LocationManager) TareasPendientes.this.getSystemService(Context.LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Toast.makeText(TareasPendientes.this, getString(R.string.debe_tener_el_gps_prendido), Toast.LENGTH_SHORT).show();
                    Intent callGPSSettingIntent = new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(callGPSSettingIntent);
                } else {


                    try {
                        // if (Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME) == 1) {

                        if (android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.AUTO_TIME) == 1) {

                            String Codbarra = ((VisitaMedica) parent.getItemAtPosition(position)).getCodbarra();
                            String nombre = ((VisitaMedica) parent.getItemAtPosition(position)).getNOMBREMED();
                            String codmed = ((VisitaMedica) parent.getItemAtPosition(position)).getCODMED();
                            String mes = ((VisitaMedica) parent.getItemAtPosition(position)).getMESV();

                            String anhio = ((VisitaMedica) parent.getItemAtPosition(position)).getANOV();
                            String ciudad = ((VisitaMedica) parent.getItemAtPosition(position)).getCIUDAD();
                            String id_visita = ((VisitaMedica) parent.getItemAtPosition(position)).getId_visita();
                            i = new Intent();
                            i.setComponent(new ComponentName(TareasPendientes.this, FormularioVisita.class));
                            i.putExtra("Codbarra", Codbarra);
                            i.putExtra("nombre", nombre);
                            i.putExtra("id_visita", id_visita);
                            i.putExtra("mes", mes);
                            i.putExtra("codmed", codmed);
                            i.putExtra("anhio", anhio);
                            i.putExtra("ciudad", ciudad);
                            i.putExtra("tipo_positivo", "inicial");

                            startActivity(i);
                        } else {
                            Toast.makeText(TareasPendientes.this, getString(R.string.debe_poner_la_fecha_en_automatico), Toast.LENGTH_SHORT).show();
                            startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                        }
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
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


        visita_medica.clear();
        lista.invalidateViews();

        if(tipo.equals("especialidad"))
        {
           // visita_medica=vc.select_visita_medica_where_espe(valor,String.valueOf(dia_hoy),String.valueOf(dia_uno), String.valueOf(dia_dos), String.valueOf(dia_tres), String.valueOf(dia_cuatro),String.valueOf(dia_cinco),String.valueOf(dia_seis),String.valueOf(dia_siete),String.valueOf(dia_ocho),String.valueOf(dia_nueve));
            visita_medica=vc.select_visita_medica_where_espe(valor,String.valueOf(dia_hoy),String.valueOf(dia_uno), String.valueOf(dia_dos), String.valueOf(dia_tres), String.valueOf(dia_cuatro));
        }
        else if(tipo.equals("categoria"))
        {
            visita_medica=vc.select_visita_medica_where_cate(valor,String.valueOf(dia_hoy), String.valueOf(dia_uno), String.valueOf(dia_dos), String.valueOf(dia_tres), String.valueOf(dia_cuatro));
        }
        else if(tipo.equals("fecha"))
        {
            visita_medica=vc.select_visita_medica_pendiente_listado(String.valueOf(dia_hoy), String.valueOf(dia_uno), String.valueOf(dia_dos), String.valueOf(dia_tres), String.valueOf(dia_cuatro));
        }
        else
        {
            visita_medica=vc.select_visita_medica_pendiente_listado(String.valueOf(dia_hoy),String.valueOf(dia_uno),String.valueOf(dia_dos),String.valueOf(dia_tres),String.valueOf(dia_cuatro));
        }



        buscador= (EditText) findViewById(R.id.txt_buscador);
        adapter=new Adapter_visita_medica(this,R.layout.list_item_visita_medica,visita_medica);
        lista.setAdapter(adapter);


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


        lista.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final LocationManager manager = (LocationManager)TareasPendientes.this.getSystemService(Context.LOCATION_SERVICE );

                if( !manager.isProviderEnabled( LocationManager.NETWORK_PROVIDER ) )
                {
                    Toast.makeText(TareasPendientes.this,getString(R.string.debe_tener_el_gps_prendido),Toast.LENGTH_SHORT).show();
                    Intent callGPSSettingIntent = new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(callGPSSettingIntent);
                }
                else {


                    try {
                       // if (Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME) == 1) {


                           if (android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.AUTO_TIME)== 1)
                           {
                            String Codbarra = ((VisitaMedica) parent.getItemAtPosition(position)).getCodbarra();
                            String nombre = ((VisitaMedica) parent.getItemAtPosition(position)).getNOMBREMED();
                            String codmed = ((VisitaMedica) parent.getItemAtPosition(position)).getCODMED();
                            String mes = ((VisitaMedica) parent.getItemAtPosition(position)).getMESV();
                            String anhio = ((VisitaMedica) parent.getItemAtPosition(position)).getANOV();
                            String id_visita=((VisitaMedica) parent.getItemAtPosition(position)).getId_visita();
                            String ciudad = ((VisitaMedica) parent.getItemAtPosition(position)).getCIUDAD();
                            i = new Intent();
                            i.setComponent(new ComponentName(TareasPendientes.this, FormularioVisita.class));
                            i.putExtra("Codbarra",Codbarra);
                            i.putExtra("nombre", nombre);
                            i.putExtra("id_visita", id_visita);
                            i.putExtra("mes", mes);
                            i.putExtra("codmed", codmed);
                            i.putExtra("anhio", anhio);
                            i.putExtra("ciudad", ciudad);
                            i.putExtra("tipo_positivo","inicial");
                            Toast.makeText(TareasPendientes.this, ciudad + " " + mes, Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(TareasPendientes.this,getString(R.string.debe_poner_la_fecha_en_automatico),Toast.LENGTH_SHORT).show();
                            startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                        }
                    }
                    catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }



}
