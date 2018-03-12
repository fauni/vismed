package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_producto;
import visita_medica.lafar.com.visitamedica.adapters.Adapter_visita_realizada;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.MM;

public class ListadoMuestraMedica extends Activity {

    ListView lista_muestra_medica;
    Adapter_producto adapter;
    Visitas_controlador vc;
    ArrayList<MM> mm= new ArrayList<MM>();
    EditText buscador;

    ImageView volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_muestra_medica);
        vc= new Visitas_controlador(ListadoMuestraMedica.this,  "visita_medica.sqlite");
        lista_muestra_medica=(ListView)this.findViewById(R.id.lv_muestra_medica);
        mm=vc.select_muestra_medica();
        volver=(ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListadoMuestraMedica.this,getString(R.string.debe_elegir_producto),Toast.LENGTH_SHORT).show();
            }
        });

        llenar_listado();
    }
    public void llenar_listado()
    {

        buscador= (EditText) findViewById(R.id.txt_buscador_muestra_medica);
        adapter=new Adapter_producto(this,R.layout.list_item_muestra_medica,mm);
        lista_muestra_medica.setAdapter(adapter);

        lista_muestra_medica.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigoMM = ((MM) parent.getItemAtPosition(position)).getCODIGOMM();
                String nombreMM = ((MM) parent.getItemAtPosition(position)).getMM();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("codigoMM",codigoMM);
                resultIntent.putExtra("nombreMM",nombreMM);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });


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


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Toast.makeText(ListadoMuestraMedica.this,getString(R.string.debe_elegir_producto),Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
