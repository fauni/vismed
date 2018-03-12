package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;

public class FormularioBancoMuestrasVisitador extends Activity {

    String id_formulario_de_banco_de_muestras;
    String string_nombre_doctor, string_bandera_seguimiento;
    Bundle bundle;
    ArrayList<Tblbancomm> banco_muestras=new ArrayList<Tblbancomm>();
    ArrayList<DetalleBancoMuestras> detalle_banco_muestras=new ArrayList<DetalleBancoMuestras>();
    Visitas_controlador vc;

    TextView codigo_banco_de_muestras;
    TextView nombre_doctor;

    Button negativo, cargar_firma;
    ImageView volver;

    String ruta_imagen;

    TableLayout tableLayout;

    TextView negativo_leyenda;
    EditText negativo_observaciones;
    Button negativo_guardar;

    BancoMuestrasVisitador banco_muestras_visitador = new BancoMuestrasVisitador();


    //////////////Fecha y hora

    private Calendar fechaYhora = Calendar.getInstance();
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_banco_muestras_visitador);




        volver=(ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        bundle= getIntent().getExtras();
        id_formulario_de_banco_de_muestras=bundle.getString("id_formulario_de_banco_de_muestras");

        vc= new Visitas_controlador(FormularioBancoMuestrasVisitador.this,"visita_medica.sqlite" );
        detalle_banco_muestras= vc.select_detalle_banco_muestras_id_formulario_banco_muestras(id_formulario_de_banco_de_muestras);
        banco_muestras=vc.select_banco_muestras_dado_id(id_formulario_de_banco_de_muestras);

        for(int i=0 ; i<banco_muestras.size();i++)
        {
          string_nombre_doctor=banco_muestras.get(i).getNombre_med();


        }

        System.out.println("<3 <3 <3 <3 <3 bandera seguimiento "+ string_bandera_seguimiento);

        codigo_banco_de_muestras=(TextView)this.findViewById(R.id.lbl_codigo_banco_muestra);
        codigo_banco_de_muestras.setText(id_formulario_de_banco_de_muestras);

        nombre_doctor=(TextView)this.findViewById(R.id.lbl_nombre_doctor);
        nombre_doctor.setText(string_nombre_doctor);



        cargar_firma=(Button)this.findViewById(R.id.btn_captura_firma);
        cargar_firma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(FormularioBancoMuestrasVisitador.this, CapturaFirma.class);
                i.putExtra("nombre_foto", id_formulario_de_banco_de_muestras);
                startActivityForResult(i, 2);

            }
        });

       actualizar_tabla();

   negativo=(Button)this.findViewById(R.id.btn_negativo);
        negativo_leyenda=(TextView)this.findViewById(R.id.lbl_negativo_leyenda);
        negativo_observaciones=(EditText)this.findViewById(R.id.txt_negativo_justificaciones);
        negativo_guardar=(Button)this.findViewById(R.id.btn_negativo_guardar);

           negativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargar_firma.setVisibility(View.GONE);
                tableLayout.setVisibility(View.GONE);
                negativo.setVisibility(View.GONE);

                negativo_leyenda.setVisibility(View.VISIBLE);
                negativo_observaciones.setVisibility(View.VISIBLE);
                negativo_guardar.setVisibility(View.VISIBLE);
            }
        });



negativo_guardar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i= new Intent(FormularioBancoMuestrasVisitador.this, BancoMuestrasVisitador.class);


        Toast toast =Toast.makeText(getApplicationContext(), R.string.banco_de_muestras_guardado_localmente,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        fecha_actual = fecha.format(fechaYhora.getTime());
        vc.update_banco_muestras_guardar(id_formulario_de_banco_de_muestras, "2", fecha_actual, negativo_observaciones.getText().toString(), "");
        finish();
        banco_muestras_visitador.activity_listado_banco_muestras.finish();
        startActivity(i);
    }
});

    }

    public void actualizar_tabla()
    {
        tableLayout=(TableLayout)this.findViewById(R.id.tly_banco_de_muestras);
        detalle_banco_muestras.add(0, new DetalleBancoMuestras("","",getString(R.string.codigo_banco_de_muestra),getString(R.string.producto),getString(R.string.cantidad)));
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
                tv_codigo.setTextSize(10);
                tv_codigo.setText(detalle_banco_muestras.get(i).getCodigo_mm());


                tv_nombre_producto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                tv_nombre_producto.setGravity(Gravity.CENTER);
                tv_nombre_producto.setTextSize(10);
                tv_nombre_producto.setText(detalle_banco_muestras.get(i).getMm());


                cantidad[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                cantidad[i].setGravity(Gravity.CENTER);
                cantidad[i].setTextSize(10);
                cantidad[i].setText(detalle_banco_muestras.get(i).getCantidad());

                if (i == 0) {
                    cantidad[i].setTypeface(Typeface.DEFAULT_BOLD);
                    tv_nombre_producto.setTypeface(Typeface.DEFAULT_BOLD);
                    tv_codigo.setTypeface(Typeface.DEFAULT_BOLD);
                }


                row.addView(tv_codigo);
                row.addView(tv_nombre_producto);
                row.addView(cantidad[i]);

                tableLayout.addView(row);

            }



        }
        else {

        }

    }

    public void actualizar_tabla_despues_de_captura()
    {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout=(TableLayout)this.findViewById(R.id.tly_banco_de_muestras);
        detalle_banco_muestras=vc.select_detalle_banco_muestras_id_formulario_banco_muestras(id_formulario_de_banco_de_muestras);
        detalle_banco_muestras.add(0, new DetalleBancoMuestras("","",getString(R.string.codigo_banco_de_muestra),getString(R.string.producto),getString(R.string.cantidad)));

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
                tv_codigo.setTextSize(10);
                tv_codigo.setText(detalle_banco_muestras.get(i).getCodigo_mm());


                tv_nombre_producto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                tv_nombre_producto.setGravity(Gravity.CENTER);
                tv_nombre_producto.setTextSize(10);
                tv_nombre_producto.setText(detalle_banco_muestras.get(i).getMm());


                cantidad[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, (float) 1.0));
                cantidad[i].setGravity(Gravity.CENTER);
                cantidad[i].setTextSize(10);
                cantidad[i].setText(detalle_banco_muestras.get(i).getCantidad());

                if (i == 0) {
                    cantidad[i].setTypeface(Typeface.DEFAULT_BOLD);
                    tv_nombre_producto.setTypeface(Typeface.DEFAULT_BOLD);
                    tv_codigo.setTypeface(Typeface.DEFAULT_BOLD);
                }


                row.addView(tv_codigo);
                row.addView(tv_nombre_producto);
                row.addView(cantidad[i]);

                tableLayout.addView(row);

            }


            Button guardar=new Button(this);
            guardar.setTextSize(10);
            guardar.setText(getString(R.string.guardar_banco_de_muestras));
            tableLayout.addView(guardar);
            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i;

                    Toast toast =Toast.makeText(getApplicationContext(), R.string.banco_de_muestras_guardado_localmente,Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                    fecha_actual = fecha.format(fechaYhora.getTime());
                    vc.update_banco_muestras_guardar(id_formulario_de_banco_de_muestras,"1",fecha_actual,"" ,ruta_imagen);
                    banco_muestras_visitador.activity_listado_banco_muestras.finish();
                    i= new Intent(FormularioBancoMuestrasVisitador.this, BancoMuestrasVisitador.class);
                    startActivity(i);
                    finish();

                }
            });
        }
        else {

        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 2:

                if (resultCode == Activity.RESULT_OK && resultCode != FormularioBancoMuestrasVisitador.this.RESULT_CANCELED)
                {
                    ruta_imagen= data.getStringExtra("ruta_imagen");
                    detalle_banco_muestras.clear();
                    tableLayout.removeAllViews();
                    tableLayout.setVisibility(View.GONE);
                    actualizar_tabla_despues_de_captura();


                }
                else
                {
                }
                break;
        }
    }

    public void toast_ruta_imagen(String ruta_imagen)
    {

     Toast.makeText(FormularioBancoMuestrasVisitador.this,ruta_imagen, Toast.LENGTH_SHORT).show();
    }
}
