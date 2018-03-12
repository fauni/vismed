package visita_medica.lafar.com.visitamedica.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class Adapter_visita_realizada extends ArrayAdapter<Boletaje> {
	private Context contexto;
	private ArrayList<Boletaje> visita_medica;
    private ArrayList<Boletaje> visita_auxiliar;
	private ArrayList<VisitaMedica> visita_visita;
    Visitas_controlador vc;
    Funciones fun= new Funciones();

	public Adapter_visita_realizada(Context context, int textViewResourceId, ArrayList<Boletaje> visita_medica) {
		super(context, textViewResourceId, visita_medica);
		this.contexto = context;
		this.visita_medica =visita_medica;


        this.visita_auxiliar = new ArrayList<Boletaje>();
        this.visita_auxiliar.addAll(visita_medica);

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holders.Holder_visita_realizada holder;
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_item_visita_realizada,parent, false);
			
			holder = new Holders.Holder_visita_realizada();
            holder.lbl_nombre=(TextView)convertView.findViewById(R.id.lbl_nombre_realizada);
			holder.img_estado = (ImageView) convertView.findViewById(R.id.img_estado);
            holder.lbl_codbarra=(TextView)convertView.findViewById(R.id.lbl_codbarra);
			holder.lbl_fecha_visita=(TextView)convertView.findViewById(R.id.lbl_fecha_visita);
			holder.lbl_ciudad=(TextView)convertView.findViewById(R.id.lbl_ciudad_visita);
			holder.lbl_observaciones=(TextView)convertView.findViewById(R.id.lbl_observaciones_visita);
            holder.ly_estado_sic=(LinearLayout)convertView.findViewById(R.id.ly_estado_sinc);
            holder.lbl_numero_de_dia=(TextView)convertView.findViewById(R.id.lbl_numero_de_dia);
            holder.layout_dia=(FrameLayout)convertView.findViewById(R.id.layout_dia);
         	convertView.setTag(holder);
		} else {
			holder = (Holders.Holder_visita_realizada) convertView.getTag();
		}
		Boletaje visita =visita_medica.get(position);
        final String estado_visita;
        final String direccion;
        final String numero_de_dia;
        final String correlativo;



     /*   vc= new Visitas_controlador(contexto,"visita_medica.sqlite");
        visita_visita=vc.select_visita_medica_dado_id_visita(visita.getId_visita());

        if(visita_visita.size()==0)
        {
            holder.lbl_nombre.setText("Error");
            estado_visita = "Error";
            direccion="Error";
            numero_de_dia="Error";
            if(fun.existe_error_con_id_boleta(contexto ,visita.getId_visita()))
            {
             System.out.println("");
            }
            else
            {

            vc.delete_boletaje_dado_id(visita.getId_visita());
            }
        }
        else {


        }
        */

        holder.lbl_nombre.setText(visita.getCiudad());
        estado_visita = visita.getAPM();
        direccion=visita.getOBSER();
        numero_de_dia=visita.getAcompanhiado();
        correlativo=visita.getMOTIVO_OBSER();


       // FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(holder.layout_dia.getWidth(), holder.layout_dia.getHeight());
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(holder.layout_dia.getWidth(), holder.layout_dia.getHeight());

        if(estado_visita.equals("2"))
        {
            holder.img_estado.setBackgroundResource(R.drawable.negativo);
           /* //margin left top,  right,  bottom
            params2.setMargins(15, 0, 0, 10);
            holder.lbl_numero_de_dia.setLayoutParams(params2);
*/
        }
        else  if(estado_visita.equals("1"))
        {
            holder.img_estado.setBackgroundResource(R.drawable.positivo);

           /* params2.setMargins(0, 5, 0, 0);
            holder.lbl_numero_de_dia.setLayoutParams(params2);*/
        }
        else
        {
            holder.img_estado.setBackgroundResource(R.drawable.logo);
        }

        String estado_sinc=visita.getEstado_subida();
        if(estado_sinc.equals("0"))
        {
            holder.ly_estado_sic.setBackgroundResource(R.color.rojo_claro);
        }
        else if (estado_sinc.equals("1"))
        {
            holder.ly_estado_sic.setBackgroundResource(R.color.celeste_claro);
        }

        else
        {
            holder.ly_estado_sic.setBackgroundResource(R.color.blanco);
        }

        holder.lbl_codbarra.setText(Html.fromHtml("<strong>"+contexto.getString(R.string.id_boleta)+"</strong>"+": " + visita.getId_visita()));
        holder.lbl_fecha_visita.setText(Html.fromHtml("<strong>"+contexto.getString(R.string.fecha_generacion)+"</strong>"+": "  + visita.getFECHACELULAR()));
        holder.lbl_ciudad.setText(Html.fromHtml("<strong>" + contexto.getString(R.string.direccion_doctor) + "</strong>" + ": " + direccion));
        holder.lbl_numero_de_dia.setText(numero_de_dia+"-"+correlativo);
        holder.lbl_observaciones.setText(visita.getOBSER());




        return convertView;
	}


    // Filter Class
    public void filter(String charText) {

        visita_medica.clear();
        if (charText.length() == 0) {
            visita_medica.addAll(visita_auxiliar);
        }
        else
        {
            for (Boletaje Wd : visita_auxiliar) {

                if(Wd.getId_visita().toLowerCase(Locale.getDefault())
                        .contains(charText)
                        ||
                        Wd.getFECHACELULAR().toLowerCase(Locale.getDefault())
                                .contains(charText)

                        ||
                        Wd.getCiudad().toLowerCase(Locale.getDefault())
                                .contains(charText)
                     )

                {
                    visita_medica.add(Wd);
                }
            }
        }
        notifyDataSetChanged();

    }

}
