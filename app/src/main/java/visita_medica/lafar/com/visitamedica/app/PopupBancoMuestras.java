package visita_medica.lafar.com.visitamedica.app;



        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.view.Window;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

        import visita_medica.lafar.com.visitamedica.R;
        import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
        import visita_medica.lafar.com.visitamedica.objetos.Apm;
        import visita_medica.lafar.com.visitamedica.objetos.MM;
        import visita_medica.lafar.com.visitamedica.objetos.Medico;

public class PopupBancoMuestras extends Activity {


    Visitas_controlador vc;
    /*
    CONTROLADORES
     */
    ArrayList<MM> producto = new ArrayList<MM>();
    ArrayList<String> string_producto=new ArrayList<String>();
    Button  boton_helper;
    ArrayAdapter<String> adapter;
    ListView lv;
    EditText inputSearch;
    Bundle bundle;
    String tipo;
    String nombre_producto;
    Integer contador_presion=0;

    /*
    productos
     */
    Spinner sp_cantidad;
    Button guardar;
    TextView lbl_cantidad;
    ArrayList<String> array_cantidad= new ArrayList<String>();
    String cantidad;
    ArrayAdapter adapter_cantidad;
    /*
    DOCTORES
     */
    String nombre_doctor;
    ArrayList<Medico> medico= new ArrayList<Medico>();
    ArrayList<String> string_doctores=new ArrayList<String>();

    /*
    VISITADORES
     */

    ArrayList<Apm> apm= new ArrayList<Apm>();
    ArrayList<String> string_apm=new ArrayList<String>();
    String nombre_apm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup_banco_muestras);
        inputSearch=(EditText)this.findViewById(R.id.txt_input_search);


        /*
        solo para productos
         */

       sp_cantidad=(Spinner)this.findViewById(R.id.sp_cantidad);
       guardar=(Button)this.findViewById(R.id.btn_guardar);
       lbl_cantidad=(TextView)this.findViewById(R.id.lbl_cantidad);

        array_cantidad.add("1");
        array_cantidad.add("2");
        array_cantidad.add("3");
        array_cantidad.add("4");
        array_cantidad.add("5");
        array_cantidad.add("6");
        array_cantidad.add("7");
        array_cantidad.add("8");
        array_cantidad.add("9");
        array_cantidad.add("10");
        array_cantidad.add("11");
        array_cantidad.add("12");
        array_cantidad.add("13");
        array_cantidad.add("14");
        array_cantidad.add("15");
        array_cantidad.add("16");
        array_cantidad.add("17");
        array_cantidad.add("18");
        array_cantidad.add("19");
        array_cantidad.add("20");

        /*
        FIN PRODUCTOS FIN PRODUCTOS
         */
        vc=new Visitas_controlador(PopupBancoMuestras.this, "visita_medica.sqlite");
        bundle=getIntent().getExtras();
        tipo=bundle.getString("tipo");
        if(tipo.equals("producto"))
        {
            mostrar_productos();

        }
        else if(tipo.equals("medico"))
        {
            mostrar_medicos();
            sp_cantidad.setVisibility(View.GONE);
            lbl_cantidad.setVisibility(View.GONE);
            guardar.setVisibility(View.GONE);
        }
        else if(tipo.equals("visitador"))
        {
        mostrar_visitadores();
        sp_cantidad.setVisibility(View.GONE);
        lbl_cantidad.setVisibility(View.GONE);
        guardar.setVisibility(View.GONE);
        }
        else
        {


        }


        guardar=(Button)this.findViewById(R.id.btn_guardar);
        guardar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contador_presion==0)
                {
                    Toast.makeText(PopupBancoMuestras.this,getString(R.string.debe_elegir_producto),Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("tipo", tipo);
                    resultIntent.putExtra("valor", nombre_producto);
                    resultIntent.putExtra("cantidad", cantidad);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

    }

public void mostrar_productos()
    {

        adapter_cantidad  = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,array_cantidad);
        sp_cantidad.setAdapter(adapter_cantidad);
        sp_cantidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position, long id) {

                cantidad = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });



        inputSearch.setHint(R.string.escribir_muestra_medica);
        producto= vc.select_muestra_medica();
        for(int i=0; i<producto.size();i++)
        {
            string_producto.add(producto.get(i).getMM());
        }

        lv=(ListView)this.findViewById(R.id.lv_lista_productos);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_filtro,R.id.item_elegido, string_producto);
        lv.setAdapter(adapter);
        filtrar();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> parent, View view,int position, long id)
                                      {

                                          nombre_producto = parent.getItemAtPosition(position).toString();
                                          Toast.makeText(PopupBancoMuestras.this,nombre_producto ,Toast.LENGTH_SHORT).show();
                                          contador_presion=contador_presion+1;
                                          inputSearch.setText(nombre_producto);
                                          inputSearch.setBackgroundColor(Color.RED);
                                          inputSearch.setFocusable(false);
                                          inputSearch.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                                          inputSearch.setClickable(false);

                                      }
                                  }
        );




    }


    public void filtrar()
    {
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                PopupBancoMuestras.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {


            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });


        inputSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                contador_presion=contador_presion-1;
                inputSearch.setFocusable(true);
                inputSearch.setFocusableInTouchMode(true);
                inputSearch.setClickable(true);
                inputSearch.setBackgroundResource(0);
                return false;
            }
        });

    }


    public void mostrar_medicos()
    {
        inputSearch.setHint(R.string.escribir_nombre_doctor);
        medico=vc.select_all_medico();
        for(int i=0;i<medico.size();i++)
        {
            if(!string_doctores.contains(medico.get(i).getNombre_med()))
            {
                string_doctores.add(medico.get(i).getNombre_med());
            }
            else
            {

            }
        }

        lv=(ListView)this.findViewById(R.id.lv_lista_productos);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_filtro,R.id.item_elegido, string_doctores);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                   nombre_doctor = parent.getItemAtPosition(position).toString();
                                                   inputSearch.setText(nombre_doctor);
                                                   inputSearch.setBackgroundColor(Color.RED);
                                                   inputSearch.setFocusable(false);
                                                   inputSearch.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                                                   inputSearch.setClickable(false); // user navigates with wheel and selects widget
                                                   contador_presion=contador_presion+1;

                                                   Intent resultIntent = new Intent();
                                                   resultIntent.putExtra("tipo",tipo);
                                                   resultIntent.putExtra("valor",nombre_doctor);
                                                   resultIntent.putExtra("cantidad","");
                                                   setResult(Activity.RESULT_OK, resultIntent);
                                                   finish();

                                               }
                                           }
        );
filtrar();
    }
public void mostrar_visitadores()
    {
        inputSearch.setHint(R.string.escribir_apm);
        apm=vc.select_all_apm();
        for(int i=0;i<apm.size();i++)
        {
            if(!string_apm.contains(apm.get(i).getNombre_vis())) {
                string_apm.add(apm.get(i).getNombre_vis());
            }
            else
            {

            }
        }

        lv=(ListView)this.findViewById(R.id.lv_lista_productos);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_filtro,R.id.item_elegido, string_apm);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              nombre_apm = parent.getItemAtPosition(position).toString();
                                              inputSearch.setText(nombre_apm);
                                              inputSearch.setBackgroundColor(Color.RED);
                                              inputSearch.setFocusable(false);
                                              inputSearch.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                                              inputSearch.setClickable(false); // user navigates with wheel and selects widget
                                              contador_presion=contador_presion+1;


                                              Intent resultIntent = new Intent();
                                              resultIntent.putExtra("tipo",tipo);
                                              resultIntent.putExtra("valor",nombre_apm);
                                              resultIntent.putExtra("cantidad","");
                                              setResult(Activity.RESULT_OK, resultIntent);
                                              finish();

                                          }
                                      }
        );
 filtrar();
    }


}