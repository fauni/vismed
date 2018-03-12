package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.async.OperacionesGuardarBancoMuestrasSupervisor;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Apm;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.objetos.MM;
import visita_medica.lafar.com.visitamedica.objetos.Medico;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;

public class BancoMuestras extends Activity {



    /*PARA PRODUCTOS
*/
    Visitas_controlador vc;
    ArrayList<DetalleBancoMuestras> detalle_banco_muestras = new ArrayList<DetalleBancoMuestras>();


    String codigo_valor;
    ArrayList<MM> array_producto= new ArrayList<MM>();
/*
medicos
 */
ArrayList<Medico> array_medicos= new ArrayList<Medico>();

    /*
    VISItadore
     */
    ArrayList<Apm> array_apm= new ArrayList<Apm>();

    /*
    Fecha
     */
    private Calendar fechaYhora = Calendar.getInstance();
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;


    /*
    PARA BANCO DE MUESTRAS
     */


    String id_formulario_de_banco_de_muestras;
    String apm_id;
    String codigo_producto;
    String codigo_doctor;
    ArrayList<Tblbancomm> banco_muestras = new ArrayList<Tblbancomm>();

/*DETALLE BANCO DE MUESTRAS DETALLE BANCO DE MUESTRAS DETALLE DE BANCO DE MUESTRAS
 */

    TableLayout table_detalle;

    /*
    BOTONES
     */
    Button agregar, quitar, nombre_visitador, nombre_doctor, realizadas, sincronizar;
    ImageView volver;
    /*
    ACTIVITIES
     */
    MenuPrincipal menu_principal = new MenuPrincipal();
    Intent intent;
    /*
     SHARED PREFERENCES
     */
     SharedPreferencesP fps= new SharedPreferencesP();

    /*
    CONTADOR DE BANCO DE MUESTRAS
     */
    Integer banco_muestras_a_sincronizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_muestras);

        table_detalle = (TableLayout) this.findViewById(R.id.tly_detalle_banco_de_muestras);
        vc= new Visitas_controlador(BancoMuestras.this,"visita_medica.sqlite");
        fecha_actual = fecha.format(fechaYhora.getTime());
        id_formulario_de_banco_de_muestras = (fecha_actual+fps.Consultar_si_hay_registro(BancoMuestras.this)).replace(" ", "_").replace("-","_").replace(":", "_");

        detalle_banco_muestras.add(new DetalleBancoMuestras(id_formulario_de_banco_de_muestras+"0",id_formulario_de_banco_de_muestras,getString(R.string.codigo_banco_de_muestra),getString(R.string.producto), getString(R.string.cantidad)));

        volver = (ImageView) this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        nombre_doctor = (Button) this.findViewById(R.id.btn_nombre_medico);
        nombre_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(BancoMuestras.this, PopupBancoMuestras.class);
                intent.putExtra("tipo", "medico");
                startActivityForResult(intent, 1);
            }
        });
        nombre_visitador = (Button) this.findViewById(R.id.btn_nombre_apm);
        nombre_visitador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent = new Intent(BancoMuestras.this, PopupBancoMuestras.class);
                intent.putExtra("tipo", "visitador");
                startActivityForResult(intent, 1);

            }
        });

        agregar = (Button) this.findViewById(R.id.btn_agregar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                intent = new Intent(BancoMuestras.this, PopupBancoMuestras.class);
                intent.putExtra("tipo", "producto");
                startActivityForResult(intent, 1);

            }
        });


        quitar = (Button) this.findViewById(R.id.btn_quitar);
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (detalle_banco_muestras.size()==1) {
                    Toast.makeText(BancoMuestras.this, getString(R.string.debe_elegir_producto), Toast.LENGTH_SHORT).show();
                } else {
                    detalle_banco_muestras.remove((detalle_banco_muestras.size() - 1));
                    table_detalle.removeAllViews();
                    table_detalle.setVisibility(View.GONE);
                    actualizar_tabla();
                }
            }

        });
realizadas=(Button)this.findViewById(R.id.btn_ver_realizadas);
        realizadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_realizadas = new Intent(BancoMuestras.this, BancoMuestrasRealizado.class);
                startActivity(intent_realizadas);
            }
        });



        banco_muestras_a_sincronizar=vc.contar_banco_muestras_sin_sincronizar_supervisor();
        sincronizar=(Button)this.findViewById(R.id.btn_sincronizar_banco_muestras);
        sincronizar.setText(getString(R.string.sincronizar_visitas) + " (" + banco_muestras_a_sincronizar + ") ");
        sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(banco_muestras_a_sincronizar>0)
                {
                    new OperacionesGuardarBancoMuestrasSupervisor(BancoMuestras.this, fps.Consultar_si_hay_registro(BancoMuestras.this)).execute();
                }
                else
                {


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
                    String cantidad = data.getStringExtra("cantidad");
                    System.out.println("TIPO: "+tipo + " VALOR: "+ valor+" Cantidad: "+cantidad);

                    if(tipo.equals("producto"))
                    {

                        codigo_producto=return_codigos(tipo,valor);
                        detalle_banco_muestras.add(new DetalleBancoMuestras((id_formulario_de_banco_de_muestras+codigo_producto),id_formulario_de_banco_de_muestras,codigo_producto,valor,cantidad));


                        table_detalle.removeAllViews();
                        table_detalle.setVisibility(View.GONE);
                        actualizar_tabla();
                    }

                    if(tipo.equals("medico"))
                    {
                    nombre_doctor.setText(valor);
                    }

                    else if(tipo.equals("visitador"))
                    {
                        nombre_visitador.setText(valor);
                    }

                   else
                    {

                    }



                }
                break;
            }

        }

    }



    public void actualizar_tabla() {
        table_detalle.setVisibility(View.VISIBLE);


        final TextView cantidad[] = new TextView[detalle_banco_muestras.size()];

        if (detalle_banco_muestras.size() > 1) {

            for (int i = 0; i < detalle_banco_muestras.size(); i++) {
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
                tv_codigo.setTextSize(12);
                tv_codigo.setText(detalle_banco_muestras.get(i).getCodigo_mm());


                tv_nombre_producto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                tv_nombre_producto.setGravity(Gravity.CENTER);
                tv_nombre_producto.setTextSize(12);
                tv_nombre_producto.setText(detalle_banco_muestras.get(i).getMm());


                cantidad[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                cantidad[i].setGravity(Gravity.CENTER);
                cantidad[i].setTextSize(12);
                cantidad[i].setText(detalle_banco_muestras.get(i).getCantidad());

                if (i == 0) {
                    cantidad[i].setTypeface(Typeface.DEFAULT_BOLD);
                    tv_nombre_producto.setTypeface(Typeface.DEFAULT_BOLD);
                    tv_codigo.setTypeface(Typeface.DEFAULT_BOLD);
                }


                row.addView(tv_codigo);
                row.addView(tv_nombre_producto);
                row.addView(cantidad[i]);

                table_detalle.addView(row);

            }


            Button guardar = new Button(this);
            guardar.setText(getString(R.string.guardar_banco_de_muestras));
            table_detalle.addView(guardar);
            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
         if ((nombre_doctor.getText().toString()).equals("") || (nombre_visitador.getText().toString()).equals("")) {
                        Toast.makeText(BancoMuestras.this, getString(R.string.debe_llenar_todos_los_campso), Toast.LENGTH_SHORT).show();


                    } else {
                        apm_id = return_codigos("visitador", nombre_visitador.getText().toString());
                        codigo_doctor = return_codigos("medico", nombre_doctor.getText().toString());

                        banco_muestras.add(new Tblbancomm(id_formulario_de_banco_de_muestras, apm_id, nombre_visitador.getText().toString(), "", "", "",
                                codigo_doctor, nombre_doctor.getText().toString(), "", fecha_actual, "0", "", "", "", "", "", "", "0", "0"));


                        vc.add_banco_muestra(banco_muestras);

                        detalle_banco_muestras.remove(0);
                        vc.add_detalle_banco_muestra(detalle_banco_muestras);

                        //menu_principal.primero.finish();
                        finish();
                        Toast.makeText(BancoMuestras.this, getString(R.string.banco_de_muestras_guardado_localmente), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(BancoMuestras.this, BancoMuestras.class);
                        startActivity(i);
                    }

                }
            });
        }
    }

    public String return_codigos(String tipo, String nombre)
    {


 if(tipo.equals("producto")) {

     array_producto = vc.select_muestra_medica_dado_nombre_muestra(nombre);
     for (int i = 0; i < array_producto.size(); i++) {
         codigo_valor = array_producto.get(i).getCODIGOMM();
     }

     array_producto.clear();

 }
        else if(tipo.equals("medico")) {


     array_medicos=vc.select_medico_dado_nombre_medico(nombre);
     for (int i = 0; i < array_medicos.size(); i++) {
         codigo_valor = array_medicos.get(i).getId_med();
     }

     array_medicos.clear();


 }

     else if(tipo.equals("visitador")) {


     array_apm=vc.select_apm_dado_nombre_apm(nombre);
     for (int i = 0; i < array_apm.size(); i++) {
         codigo_valor = array_apm.get(i).getApm();
     }

     array_apm.clear();
        }

        else
        {
          codigo_valor="";
        }
return codigo_valor;

    }

}
