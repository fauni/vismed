package visita_medica.lafar.com.visitamedica.adapters;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.apache.http.util.ByteArrayBuffer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import visita_medica.lafar.com.visitamedica.R;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class Adapter_visita_medica extends ArrayAdapter<VisitaMedica> {
	private Context contexto;
	private ArrayList<VisitaMedica> visita_medica;
    private ArrayList<VisitaMedica> visita_auxiliar;




	private String url;

	VariablesUrl var= new VariablesUrl();
	
	Visitas_controlador vc;


    Funciones fun=  new Funciones();


    /////////////////////////////FECHA
    private Calendar fechaYhora = Calendar.getInstance();
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
    String fecha_actual;
	
	public Adapter_visita_medica(Context context, int textViewResourceId, ArrayList<VisitaMedica> visita_medica) {
		super(context, textViewResourceId, visita_medica);
		this.contexto = context;
		this.visita_medica =visita_medica;


        this.visita_auxiliar = new ArrayList<VisitaMedica>();
        this.visita_auxiliar.addAll(visita_medica);

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holders.Holder_visita_medica holder;
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_item_visita_medica,parent, false);
			
			holder = new Holders.Holder_visita_medica();
            holder.lbl_nombre=(TextView)convertView.findViewById(R.id.lbl_nombre);
			holder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
            holder.lbl_direccion_useful=(TextView)convertView.findViewById(R.id.lbl_direccion_useful);
			holder.lbl_celular_useful=(TextView)convertView.findViewById(R.id.lbl_celular_useful);
			holder.lbl_observaciones_useful=(TextView)convertView.findViewById(R.id.lbl_observaciones_useful);
            holder.lbl_fecha_visita_y_turno=(TextView)convertView.findViewById(R.id.lbl_fecha_visita_y_turno);
            holder.ly_fondo_tarea_pendiente=(LinearLayout)convertView.findViewById(R.id.ly_fondo_tarea_pendiente);
         	convertView.setTag(holder);
		} else {
			holder = (Holders.Holder_visita_medica) convertView.getTag();
		}
		VisitaMedica visita =visita_medica.get(position);
		final VisitaMedica solo_un_item=visita;




        holder.lbl_nombre.setText(visita.getNOMBREMED());
		holder.lbl_direccion_useful.setText(Html.fromHtml("<strong>" + contexto.getString(R.string.direccion_doctor) + ": " + "</strong>" + visita.getDIRECCION()));
        holder.lbl_celular_useful.setText(Html.fromHtml("<strong>"+contexto.getString(R.string.id_boleta)+": "+"</strong>"+visita.getId_visita()));
        holder.lbl_observaciones_useful.setText(Html.fromHtml("<strong>"+contexto.getString(R.string.especialidad)+": "+"</strong>"+visita.getESPE1()));
        holder.lbl_fecha_visita_y_turno.setText(Html.fromHtml("<strong>"+contexto.getString(R.string.dia_visita)+": "+"</strong>"+ visita.getDia_visita()+"<strong>"+" "+contexto.getString(R.string.visita_no) + ": " +"</strong>"+visita.getCorrelativo_visita()));

        vc= new Visitas_controlador(contexto, "visita_medica.sqlite");
       if(vc.mostrar_dia_de_hoy().equals(visita.getDia_visita()))
       {
           holder.ly_fondo_tarea_pendiente.setBackgroundResource(R.color.lila_claro);

       }
        else
       {
           holder.ly_fondo_tarea_pendiente.setBackgroundResource(R.color.verde_claro);

       }



		return convertView;	
	}


    // Filter Class
    public void filter(String charText) {

        visita_medica.clear();
        if (charText.length() == 0) {
            visita_medica.addAll(visita_auxiliar);
        } else {
            for (VisitaMedica Wd : visita_auxiliar) {

                if(Wd.getNOMBREMED().toLowerCase(Locale.getDefault())
                        .contains(charText)
                        ||
                        Wd.getCodbarra().toLowerCase(Locale.getDefault())
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
