package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_banco_muestras;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;

public class BancoMuestrasRealizado extends Activity {
ImageView atras;
    ArrayList<Tblbancomm> banco_muestra=  new ArrayList<Tblbancomm>();
    ListView lista;
    Visitas_controlador vc;
    EditText buscador;
    Adapter_banco_muestras adapter;
    TextView banco_muestras_titulo;
    Button sincronizar,realizadas_visitador;


    public static Activity banco_muestras_realizado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_muestras_visitador);


        banco_muestras_realizado=BancoMuestrasRealizado.this;

        banco_muestras_titulo=(TextView)this.findViewById(R.id.lb_ltitulo_banco_muestras);
        banco_muestras_titulo.setText(getString(R.string.banco_de_muestras_realizadas_titulo));

        realizadas_visitador=(Button)this.findViewById(R.id.btn_realizadas);
        realizadas_visitador.setVisibility(View.INVISIBLE);

        sincronizar=(Button)this.findViewById(R.id.btn_sincronizar_banco_muestras);
        sincronizar.setVisibility(View.INVISIBLE);


        vc= new Visitas_controlador(BancoMuestrasRealizado.this,"visita_medica.sqlite" );
        atras= (ImageView)this.findViewById(R.id.btn_volver);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llenar_lista();
    }
    public void llenar_lista() {

        lista=(ListView)this.findViewById(R.id.lv_banco_muestras);
        banco_muestra=vc.select_banco_muestras_realizadas();
        buscador= (EditText) findViewById(R.id.txt_buscador);
        adapter=new Adapter_banco_muestras(this,R.layout.list_item_banco_muestras,banco_muestra);
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


                String codigo_banco_muestras = ((Tblbancomm) parent.getItemAtPosition(position)).getId_formulario_de_banco_de_muestras();
                String nombre_medico = ((Tblbancomm) parent.getItemAtPosition(position)).getNombre_med();
                String fecha_realizado = ((Tblbancomm) parent.getItemAtPosition(position)).getFecha_entregado();
                String estado=((Tblbancomm) parent.getItemAtPosition(position)).getEstado();
                String ruta_imagen=((Tblbancomm) parent.getItemAtPosition(position)).getRuta_imagen_banco_muestras();
                String observaciones=((Tblbancomm) parent.getItemAtPosition(position)).getJustificacion();

                String bandera_seguimiento=((Tblbancomm) parent.getItemAtPosition(position)).getBandera_seguimiento();
                String estado_bandera_seguimimento=((Tblbancomm) parent.getItemAtPosition(position)).getEstado_sinc_seguimiento();
                String observaciones_seguimiento=((Tblbancomm) parent.getItemAtPosition(position)).getObservaciones_seguimiento();
                String fecha_seguimiento=((Tblbancomm) parent.getItemAtPosition(position)).getFecha_seguimiento();

                Intent i;
                i = new Intent();
                i.setComponent(new ComponentName(BancoMuestrasRealizado.this, SeguimientoBancoMuestras.class));
                i.putExtra("codigo_banco_muestras", codigo_banco_muestras);
                i.putExtra("nombre_medico", nombre_medico);
                i.putExtra("fecha_realizado", fecha_realizado);
                i.putExtra("estado", estado);
                i.putExtra("ruta_imagen",ruta_imagen);
                i.putExtra("observaciones",observaciones);

                i.putExtra("bandera_seguimiento",bandera_seguimiento);
                i.putExtra("estado_sincronizacion_bandera_seguimiento",estado_bandera_seguimimento);
                i.putExtra("observaciones_seguimiento",observaciones_seguimiento);
                i.putExtra("fecha_seguimiento",fecha_seguimiento);

                startActivity(i);

            }
        });

    }




}
