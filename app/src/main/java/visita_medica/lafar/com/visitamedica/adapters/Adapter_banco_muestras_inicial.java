package visita_medica.lafar.com.visitamedica.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class Adapter_banco_muestras_inicial extends ArrayAdapter<Tblbancomm> {
	private Context contexto;
	private ArrayList<Tblbancomm> banco_muestra;
    private ArrayList<Tblbancomm> banco_muestra_auxiliar;




	private String url;
	Funciones fun=new Funciones();
	VariablesUrl var= new VariablesUrl();

	Visitas_controlador vc;




    /////////////////////////////FECHA
    private Calendar fechaYhora = Calendar.getInstance();
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
    String fecha_actual;

	public Adapter_banco_muestras_inicial(Context context, int textViewResourceId, ArrayList<Tblbancomm> banco_muestra) {
		super(context, textViewResourceId, banco_muestra);
		this.contexto = context;
		this.banco_muestra =banco_muestra;


        this.banco_muestra_auxiliar = new ArrayList<Tblbancomm>();
        this.banco_muestra_auxiliar.addAll(banco_muestra);

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holders.Holder_banco_muestras holder;
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_item_banco_muestras,parent, false);
			
			holder = new Holders.Holder_banco_muestras();
			holder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
            holder.lbl_codigo_banco_muestra=(TextView)convertView.findViewById(R.id.lbl_codigo_banco_muestra);
			holder.lbl_nombre_medico=(TextView)convertView.findViewById(R.id.lbl_nombre_medico);
			holder.lbl_fecha_generacion=(TextView)convertView.findViewById(R.id.lbl_fecha_generacion);
            holder.lbl_linea_vis=(TextView)convertView.findViewById(R.id.lbl_linea_vis);
            holder.lbl_producto=(TextView)convertView.findViewById(R.id.lbl_producto);
            holder.ly_fondo_estado_banco_muestra=(LinearLayout)convertView.findViewById(R.id.ly_fondo_estado_banco_muestra);
         	convertView.setTag(holder);
		} else {
			holder = (Holders.Holder_banco_muestras) convertView.getTag();
		}
		Tblbancomm muestra =banco_muestra.get(position);

        if(muestra.getEstado().equals("1"))
        {


        }
        else
        {

        }

     /*  if(muestra.getBandera_seguimiento().equals("1"))
        {
            holder.ly_fondo_estado_banco_muestra.setBackgroundResource(R.color.celeste_claro);
        }
        else
        {
            holder.ly_fondo_estado_banco_muestra.setBackgroundResource(R.color.rojo_claro);
        }*/



        holder.lbl_codigo_banco_muestra.setText(Html.fromHtml(muestra.getId_formulario_de_banco_de_muestras()));
        holder.lbl_nombre_medico.setText(Html.fromHtml("<strong>"+contexto.getString(R.string.nombre_de_medico)+": "+"</strong>" +muestra.getNombre_med()));
        holder.lbl_fecha_generacion.setText(Html.fromHtml("<strong>"+contexto.getString(R.string.fecha_generacion)+": "+"</strong>"+muestra.getFecha_solicitud()));
        holder.lbl_linea_vis.setVisibility(View.GONE);
        holder.lbl_producto.setVisibility(View.GONE);



		return convertView;	
	}


    // Filter Class
    public void filter(String charText) {

        banco_muestra.clear();
        if (charText.length() == 0) {
            banco_muestra.addAll(banco_muestra_auxiliar);
        } else {
            for (Tblbancomm Wd :banco_muestra_auxiliar) {

                if(Wd.getNombre_med().toLowerCase(Locale.getDefault())
                        .contains(charText)
                     )

                {
                    banco_muestra.add(Wd);
                }
            }
        }
        notifyDataSetChanged();

    }

}
