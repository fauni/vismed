package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import visita_medica.lafar.com.visitamedica.ImagesAndDrawing.ImageLoader;
import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.async.OperacionesSincronizarSeguimiento;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;

public class SeguimientoBancoMuestras extends Activity {

    ImageView btn_volver, firma_banco_muestras;

    TextView codigo,  nombre, fecha_entregado,  justificaciones_negativa_u_observaciones_seguimiento;
    String s_codigo, s_nombre, s_fecha_entregado, s_estado, s_ruta_imagen, s_observaciones;
    String s_bandera_seguimiento, s_estado_sincronizacion_bandera_seguimiento, s_observaciones_seguimiento;


    Bundle bundle;

    EditText observaciones_seguimiento;


    Button guardar,  sincronizar;

    SharedPreferencesP fps= new SharedPreferencesP();


    LinearLayout detalle, botones,existe_seguimiento;

    ArrayList<DetalleBancoMuestras> detalle_banco_muestras=new ArrayList<DetalleBancoMuestras>();
    Visitas_controlador vc;
    TableLayout tableLayout;

    //////////////Fecha y hora

    private Calendar fechaYhora = Calendar.getInstance();
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;

     BancoMuestrasRealizado banco_muestras_realizado= new BancoMuestrasRealizado();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_banco_muestras);

        bundle = getIntent().getExtras();


        detalle = (LinearLayout) this.findViewById(R.id.ly_detalle);
        botones = (LinearLayout) this.findViewById(R.id.ly_botones);
        existe_seguimiento = (LinearLayout) this.findViewById(R.id.ly_existe_seguimiento);

        justificaciones_negativa_u_observaciones_seguimiento=(TextView)this.findViewById(R.id.lbl_justificaciones_o_seguimiento);
        observaciones_seguimiento=(EditText)this.findViewById(R.id.txt_observaciones_seguimiento);

        s_codigo=bundle.getString("codigo_banco_muestras");
        s_nombre=bundle.getString("nombre_medico");
        s_fecha_entregado=bundle.getString("fecha_realizado");
        s_estado=bundle.getString("estado");
        s_ruta_imagen=bundle.getString("ruta_imagen");
        s_observaciones=bundle.getString("observaciones");
        s_bandera_seguimiento=bundle.getString("bandera_seguimiento");
        s_estado_sincronizacion_bandera_seguimiento=bundle.getString("estado_sincronizacion_bandera_seguimiento");
        s_observaciones_seguimiento=bundle.getString("observaciones_seguimiento");

       // Toast.makeText(SeguimientoBancoMuestras.this,  s_observaciones_seguimiento, Toast.LENGTH_SHORT).show();

        justificaciones_negativa_u_observaciones_seguimiento=(TextView)this.findViewById(R.id.lbl_justificaciones_o_seguimiento);


        guardar=(Button)this.findViewById(R.id.btn_guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast =Toast.makeText(getApplicationContext(), R.string.banco_de_muestras_guardado_localmente,Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

                fecha_actual = fecha.format(fechaYhora.getTime());
                vc.update_banco_muestras_seguimiento(s_codigo, observaciones_seguimiento.getText().toString(),  fecha_actual );

                guardar.setVisibility(View.GONE);
                observaciones_seguimiento.setVisibility(View.GONE);



            }
        });

        sincronizar=(Button)this.findViewById(R.id.btn_sincronizar);
        sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new OperacionesSincronizarSeguimiento(SeguimientoBancoMuestras.this, fps.Consultar_si_hay_registro(SeguimientoBancoMuestras.this), s_codigo).execute();

            }
        });


        vc=new Visitas_controlador(SeguimientoBancoMuestras.this, "visita_medica.sqlite");

        if(s_estado.equals("1"))
        {
            actualizar_tabla();

            /*
            HAY QUE VER SI EXISTE YA LA BANDERA DE SEGUIMIENTO
            NO
             */
            if(s_bandera_seguimiento.equals("0"))
            {

             System.out.println("la bandera de seguimiento esta en 0");

            }
            /*
            SI EXISTE LA BANDERA DE SEGUIMIENTO SOLO SE DEBE MOSTRAR EN OBSERVACIONES
             */
            else {

                System.out.println("la bandera de seguimiento esta en 1");

                if (s_estado_sincronizacion_bandera_seguimiento.equals("1"))

                {

                System.out.println("y e estado de sincronizacion esta en 1");


                    observaciones_seguimiento.setVisibility(View.GONE);
                    botones.setVisibility(View.GONE);
                    existe_seguimiento.setVisibility(View.VISIBLE);
                    justificaciones_negativa_u_observaciones_seguimiento.setText(s_observaciones_seguimiento);

                    justificaciones_negativa_u_observaciones_seguimiento.setBackgroundColor(Color.CYAN);

            }
            else
            {

                observaciones_seguimiento.setVisibility(View.GONE);
                guardar.setVisibility(View.GONE);
                System.out.println("y e estado de sincronizacion esta en 0");

            }
          }
        }
        else if(s_estado.equals("2"))
        {
            observaciones_seguimiento.setVisibility(View.GONE);
            detalle.setVisibility(View.VISIBLE);
            botones.setVisibility(View.GONE);
            existe_seguimiento.setVisibility(View.VISIBLE);
            justificaciones_negativa_u_observaciones_seguimiento.setText(s_observaciones);
            justificaciones_negativa_u_observaciones_seguimiento.setBackgroundColor(Color.RED);
           actualizar_tabla();
        }
        else if(s_estado.equals("0"))
        {

            observaciones_seguimiento.setVisibility(View.GONE);
            detalle.setVisibility(View.VISIBLE);
            botones.setVisibility(View.GONE);
            existe_seguimiento.setVisibility(View.GONE);
            justificaciones_negativa_u_observaciones_seguimiento.setVisibility(View.GONE);
            actualizar_tabla();
        }
        else
        {

        }


        firma_banco_muestras=(ImageView)this.findViewById(R.id.iv_firma_banco_muestras);
        mostrar_o_no_firma(s_estado);

        codigo=(TextView)this.findViewById(R.id.lbl_codigo_banco_muestra);
        codigo.setText(s_codigo);

        nombre=(TextView)this.findViewById(R.id.lbl_nombre_medico);
        nombre.setText(s_nombre);

        fecha_entregado=(TextView)this.findViewById(R.id.lbl_fecha_entregado);
        fecha_entregado.setText(s_fecha_entregado);

        btn_volver=(ImageView)this.findViewById(R.id.btn_volver);
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeguimientoBancoMuestras.this, BancoMuestrasRealizado.class);
                finish();
                banco_muestras_realizado.banco_muestras_realizado.finish();
                startActivity(intent);
            }
        });




    }
    public void actualizar_tabla()
    {


      /*  if(s_estado.equals("1"))
        {
detalle.setVisibility(View.VISIBLE);
        }

       else  if(s_estado.equals("2"))
        {
            detalle.setVisibility(View.GONE);
        }

        else
        {

        }
*/


        detalle_banco_muestras=vc.select_detalle_banco_muestras_id_formulario_banco_muestras(s_codigo);



        tableLayout=(TableLayout)this.findViewById(R.id.tl_detalle);
        detalle_banco_muestras.add(0, new DetalleBancoMuestras("", "", "", getString(R.string.producto), getString(R.string.cantidad)));
        final TextView cantidad[] = new TextView[detalle_banco_muestras.size()];

        if (detalle_banco_muestras.size() > 1) {


            for (int i = 0; i < detalle_banco_muestras.size(); i++) {
                final int index = i;
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
               // TextView tv_codigo = new TextView(this);
                TextView tv_nombre_producto = new TextView(this);
                cantidad[i] = new TextView(this);

                /*tv_codigo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                GradientDrawable roundRectangle = (GradientDrawable) this.getResources().getDrawable(R.drawable.figuras);
                tv_codigo.setGravity(Gravity.CENTER);
                tv_codigo.setTextSize(10);
                tv_codigo.setText(detalle_banco_muestras.get(i).getCodigo_mm());
*/

                tv_nombre_producto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                tv_nombre_producto.setGravity(Gravity.LEFT);
                tv_nombre_producto.setTextSize(10);
                tv_nombre_producto.setText(detalle_banco_muestras.get(i).getMm());


                cantidad[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                cantidad[i].setGravity(Gravity.LEFT);
                cantidad[i].setTextSize(10);
                cantidad[i].setText(detalle_banco_muestras.get(i).getCantidad());

                if (i == 0) {
                    cantidad[i].setTypeface(Typeface.DEFAULT_BOLD);
                    tv_nombre_producto.setTypeface(Typeface.DEFAULT_BOLD);
                //    tv_codigo.setTypeface(Typeface.DEFAULT_BOLD);
                }


              //  row.addView(tv_codigo);
                row.addView(tv_nombre_producto);
                row.addView(cantidad[i]);

                tableLayout.addView(row);

            }



        }
        else {
          /*
          aca no esta detectan detalles del banco de muestras cosa que no deberia pasar

           */
        }

    }


    public  void mostrar_o_no_firma(String s_estado)
    {

if(s_estado.equals("1"))
{
    if (s_ruta_imagen.equals("http:")) {
        //  Toast.makeText(FichaInformacionIndividual.this, ruta_imagen_a_comparar, Toast.LENGTH_LONG).show();

        ;
        ImageLoader imgLoader;
        imgLoader = new ImageLoader(this);
        imgLoader.DisplayImage(s_ruta_imagen,firma_banco_muestras);



    }
    else
    {
        File file = new File(s_ruta_imagen);
        if (file.exists()) {


            Bitmap bmp = BitmapFactory.decodeFile(s_ruta_imagen);
            firma_banco_muestras.setVisibility(View.VISIBLE);
            firma_banco_muestras.setImageBitmap(bmp);
        } else

        {
            firma_banco_muestras.setVisibility(View.VISIBLE);
            firma_banco_muestras.setBackgroundResource(R.drawable.no_image);
        }
    }

}
else if(s_estado.equals("2"))
{
    firma_banco_muestras.setVisibility(View.GONE);
}
else
{

}

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {

            Intent intent = new Intent(SeguimientoBancoMuestras.this, BancoMuestrasRealizado.class);
            finish();
            banco_muestras_realizado.banco_muestras_realizado.finish();
            startActivity(intent);

        }
        return true;
    }



}
