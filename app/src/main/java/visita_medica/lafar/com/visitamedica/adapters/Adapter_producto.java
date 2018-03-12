package visita_medica.lafar.com.visitamedica.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.MM;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class Adapter_producto extends ArrayAdapter<MM> {
	private Context contexto;
	private ArrayList<MM> muestra_medica;
    private ArrayList<MM> muestra_auxiliar;




	private String url;
	Funciones fun=new Funciones();
	VariablesUrl var= new VariablesUrl();

	Visitas_controlador vc;

	public Adapter_producto(Context context, int textViewResourceId, ArrayList<MM> muestra_medica) {
		super(context, textViewResourceId, muestra_medica);
		this.contexto = context;
		this.muestra_medica =muestra_medica;


        this.muestra_auxiliar = new ArrayList<MM>();
        this.muestra_auxiliar.addAll(muestra_medica);

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holders.Holder_producto holder;
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_item_muestra_medica,parent, false);
			
			holder = new Holders.Holder_producto();
            holder.lbl_nombre_producto=(TextView)convertView.findViewById(R.id.lbl_mm);
			holder.lbl_codigo_producto = (TextView) convertView.findViewById(R.id.lbl_codigomm);

         	convertView.setTag(holder);
		} else {
			holder = (Holders.Holder_producto) convertView.getTag();
		}
		MM muestra =muestra_medica.get(position);
		final MM solo_un_item=muestra;
		holder.lbl_nombre_producto.setText(Html.fromHtml("<strong>"+contexto.getString(R.string.titulo_nombre)+": "+"</strong>"+muestra.getMM()));
        holder.lbl_codigo_producto.setText(Html.fromHtml(muestra.getCODIGOMM()));
		return convertView;	
	}


    // Filter Class
    public void filter(String charText) {

        muestra_medica.clear();
        if (charText.length() == 0) {
            muestra_medica.addAll(muestra_auxiliar);
        } else {
            for (MM Wd : muestra_auxiliar) {

                if(Wd.getCODIGOMM().toLowerCase(Locale.getDefault())
                        .contains(charText)
                        ||
                        Wd.getMM().toLowerCase(Locale.getDefault())
                                .contains(charText)
                     )

                {
                    muestra_medica.add(Wd);
                }
            }
        }
        notifyDataSetChanged();

    }

}
