package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;

import visita_medica.lafar.com.visitamedica.ImagesAndDrawing.ImageLoader;
import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.DetalleVisita;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;

public class FichaInformacionIndividual extends android.support.v4.app.FragmentActivity {

    ImageView volver,firma;
    Bundle bundle;
    String cod_barra,id_visita, mes, nombre_doctor,otro_anhio, estado, observaciones,motivo_obser , codmed,anhio,ciudad, ruta_imagen;


    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
    ArrayList<VisitaMedica> visita= new ArrayList<VisitaMedica>();
    ArrayList<DetalleVisita> tblmm= new ArrayList<DetalleVisita>();


    String estado_subida_boletaje;



    Visitas_controlador vc;
    TextView Codbarra, mesv ,nombre;

    Intent i=null;
    TableLayout table_layout;
    // /////////////////////variables del mapa

    private GoogleMap mapa = null;
    SupportMapFragment mMapFragment;
    Double lat, lon;

    Funciones fun= new Funciones();

    public static Activity quinto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        quinto=FichaInformacionIndividual.this;


        setContentView(R.layout.activity_ficha_informacion_individual);
        volver=(ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bundle=getIntent().getExtras();
        id_visita=bundle.getString("id_visita");
        mes=bundle.getString("mes");
        estado=bundle.getString("estado");
        cod_barra=bundle.getString("Codbarra");
        otro_anhio=bundle.getString("anhio");
        System.out.println("****************************otro año :****************************"+otro_anhio);

        vc= new Visitas_controlador(FichaInformacionIndividual.this,  "visita_medica.sqlite");
        boletaje=vc.select_boletaje_dado_codbarra(id_visita);
        visita=vc.select_visita_medica_dado_id_visita(id_visita);

        tblmm=vc.select_detalle_visita_dado_el_id_visita(cod_barra, mes, otro_anhio);

        Codbarra=(TextView) this.findViewById(R.id.lbl_codigo_barra);
        mesv=(TextView)this.findViewById(R.id.lbl_mes);
        nombre=(TextView)this.findViewById(R.id.lbl_nombre_doctor);

        for(int i=0;i<visita.size();i++)
        {
            nombre_doctor=visita.get(i).getNOMBREMED();
            codmed=visita.get(i).getCODMED();
            anhio=visita.get(i).getANOV();
            ciudad=visita.get(i).getCIUDAD();
        }

        Codbarra.setText(Html.fromHtml("<strong>" + getString(R.string.codigo_de_barra) + ": " + "</strong>" + cod_barra));
        nombre.setText(Html.fromHtml("<strong>" + getString(R.string.nombre_doctor) + ": " + "</strong>" +nombre_doctor));
        mesv.setText(Html.fromHtml("<strong>" + getString(R.string.mes_de_visita) + ": " + "</strong>" + mes));



        mMapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        mapa = mMapFragment.getMap();


        for(int i=0;i<boletaje.size();i++) {
            lat =fun.convertir_de_string_a_double(boletaje.get(i).getLat());
            lon =  fun.convertir_de_string_a_double(boletaje.get(i).getLon());
            observaciones=boletaje.get(i).getOBSER();
            motivo_obser=boletaje.get(i).getMOTIVO_OBSER();
            estado_subida_boletaje=boletaje.get(i).getEstado_subida();
            ruta_imagen=boletaje.get(i).getRuta_imagen();
        }

        LatLng verificacion = new LatLng(lat,lon);
        CameraPosition camPos = new CameraPosition.Builder()
                .target(verificacion)
                .zoom(16)
                .build();


                CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                mapa.animateCamera(camUpd3);
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title("Visita Médica Código " + id_visita + " Mes " + mes));

        table_layout = (TableLayout) this.findViewById(R.id.tl_detalle);
        firma = (ImageView) this.findViewById(R.id.iv_firma);
        if(estado.equals("1")) {
            llenar_tabla_detalle();
        }
        else
        {
            llenar_negativas();
        }

    }

    public void llenar_tabla_detalle() {

        table_layout.setVisibility(View.VISIBLE);


        int filas = tblmm.size();
        if (filas > 0) {

        tblmm.add(0,new DetalleVisita("","","","",getString(R.string.cantidad_abreviado),getString(R.string.producto),"",""));
                                     //id codbarra  codmed codigomm  cantidad mm mes anhio

            int nuevas_filas=tblmm.size();

           // final TextView codigomm[] = new TextView[nuevas_filas];
            final TextView nombremm[] = new TextView[nuevas_filas];
            final TextView cantidadmm[] = new TextView[nuevas_filas];

            for (int i = 0; i < nuevas_filas; i++) {

                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));


              /*  codigomm[i] = new TextView(this);

               */
                nombremm[i] = new TextView(this);
                cantidadmm[i] = new TextView(this);

               /* codigomm[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                codigomm[i].setGravity(Gravity.CENTER);
                codigomm[i].setTextSize(10);
                codigomm[i].setText(tblmm.get(i).getCODIGOMM());
                */

                nombremm[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, (float) 1.0));
             //   nombremm[i].setGravity(Gravity.CENTER);
                nombremm[i].setTextSize(10);
                nombremm[i].setText(tblmm.get(i).getMM());


                cantidadmm[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, (float) 1.0));
               // cantidadmm[i].setGravity(Gravity.CENTER);
                cantidadmm[i].setTextSize(10);
                cantidadmm[i].setText(tblmm.get(i).getCANTIDAD()+"            ");

                if(i==0)
                {
                  //  codigomm[i].setTypeface(Typeface.DEFAULT_BOLD);
                    nombremm[i].setTypeface(Typeface.DEFAULT_BOLD);
                    cantidadmm[i].setTypeface(Typeface.DEFAULT_BOLD);

                }

              //  row.addView(codigomm[i]);
                row.addView(nombremm[i]);
                row.addView(cantidadmm[i]);


                table_layout.addView(row);
            }




if(ruta_imagen.equals(""))
 {
     firma.setBackgroundResource(R.drawable.no_image);
 }
      else {

    File file = new File(ruta_imagen);
    if(file.exists())
    {
        Bitmap bmp = BitmapFactory.decodeFile(ruta_imagen);
        ImageView firma = (ImageView) this.findViewById(R.id.iv_firma);
        firma.setVisibility(View.VISIBLE);
        firma.setImageBitmap(bmp);}

    else

    {

        String ruta_imagen_a_comparar;
        ruta_imagen_a_comparar = ruta_imagen;
        ruta_imagen_a_comparar = ruta_imagen_a_comparar.substring(0, 5);


        if (ruta_imagen_a_comparar.equals("http:")) {
          //  Toast.makeText(FichaInformacionIndividual.this, ruta_imagen_a_comparar, Toast.LENGTH_LONG).show();

            firma.setVisibility(View.VISIBLE);
             ImageLoader imgLoader;
            imgLoader = new ImageLoader(this);
            imgLoader.DisplayImage(ruta_imagen,firma);



        } else
        {
            firma.setVisibility(View.VISIBLE);
        firma.setBackgroundResource(R.drawable.no_image);
    }
    }

           }

        }
        else
        {

        }

    }

    public void llenar_negativas()
    {

            firma.setVisibility(View.GONE);
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv_observaciones = new TextView(this);

            TableRow.LayoutParams lp=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0);

            lp.setMargins(0,0,0,15);
            tv_observaciones.setLayoutParams(lp);

            tv_observaciones.setGravity(Gravity.CENTER);
            //tv_observaciones.setTextSize(10);
            tv_observaciones.setBackgroundColor(Color.RED);
            tv_observaciones.setPadding(5, 5, 5, 5);
            tv_observaciones.setTextColor(Color.BLACK);
            tv_observaciones.setText(Html.fromHtml("<strong>" + getString(R.string.observaciones) + ": " + "</strong>" + observaciones+"-"+ motivo_obser));
            row.addView(tv_observaciones);
            table_layout.addView(row);

            Button editar_visita= new Button(this);


            editar_visita.setText(getString(R.string.editar_visita));
            editar_visita.setBackgroundResource(R.drawable.boton_custom);
            editar_visita.setTextColor(Color.WHITE);
            table_layout.addView(editar_visita);
            editar_visita.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(estado_subida_boletaje.equals("0"))
                    {
                        Toast.makeText(FichaInformacionIndividual.this, getString(R.string.no_puede_editar_visita_medica),Toast.LENGTH_SHORT ).show();
                    }
                    else {
                        i = new Intent(FichaInformacionIndividual.this, FormularioVisita.class);
                        i = new Intent();
                        i.setComponent(new ComponentName(FichaInformacionIndividual.this, FormularioRecuperarVisita.class));
                        i.putExtra("Codbarra", cod_barra);
                        i.putExtra("nombre", nombre_doctor);
                        i.putExtra("id_visita", id_visita);
                        i.putExtra("mes", mes);
                        i.putExtra("codmed", codmed);
                        i.putExtra("anhio", anhio);
                        i.putExtra("ciudad", ciudad);
                        i.putExtra("tipo_positivo", "editar");
                        startActivity(i);
                    }

                }
            });



    }
}

