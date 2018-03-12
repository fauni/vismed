package visita_medica.lafar.com.visitamedica.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.Justificadas;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;

public class Adapter_justificadas extends ArrayAdapter<Justificadas> {
	private Context contexto;
	private ArrayList<Justificadas> justificadas;
    private ArrayList<Justificadas> justificadas_auxiliar;

    Visitas_controlador vc;
    Funciones fun= new Funciones();

	public Adapter_justificadas(Context context, int textViewResourceId, ArrayList<Justificadas> justificadas) {
        super(context, textViewResourceId, justificadas);
		this.contexto = context;
		this.justificadas =justificadas;


        this.justificadas_auxiliar = new ArrayList<Justificadas>();
        this.justificadas_auxiliar.addAll(justificadas);

	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holders.Holder_justificadas holder;
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_item_justificadas,parent, false);
			
			holder = new Holders.Holder_justificadas();

            holder.lbl_id_visita=(TextView)convertView.findViewById(R.id.lbl_id_visita);
			holder.lbl_nombre_doctor= (TextView) convertView.findViewById(R.id.lbl_nombre_doctor);
            holder.lbl_fecha_hora_realizada=(TextView)convertView.findViewById(R.id.lbl_fecha_realizada);

            holder.autorizar=(RadioButton)convertView.findViewById(R.id.rb_autorizar);
            holder.rechazar=(RadioButton)convertView.findViewById(R.id.rb_rechazar);

            holder.radioGroup=(RadioGroup)convertView.findViewById(R.id.rg_botones);

         	convertView.setTag(holder);
		} else {
			holder = (Holders.Holder_justificadas) convertView.getTag();
		}
		final Justificadas justificada_1 =justificadas.get(position);
        justificadas.get(position).setEstado_justificada("1");

        holder.lbl_id_visita.setText(justificada_1.getId_visita());
        holder.lbl_nombre_doctor.setText(justificada_1.getNombre_doctor());
        holder.lbl_fecha_hora_realizada.setText(justificada_1.getFecha_tab() + " " + justificada_1.getHora_tab());

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected

                if (checkedId == R.id.rb_autorizar) {

                    Toast.makeText(contexto, "AUTORIZANDO", Toast.LENGTH_SHORT).show();
                    justificadas.get(position).setEstado_justificada("1");

                    holder.autorizar.setChecked(true);
                    holder.rechazar.setChecked(false);



                } else if (checkedId == R.id.rb_rechazar) {
                    Toast.makeText(contexto, "RECHANZANDO", Toast.LENGTH_SHORT).show();
                    justificadas.get(position).setEstado_justificada("2");
                    holder.autorizar.setChecked(false);
                    holder.rechazar.setChecked(true);

                } else {
                    Toast.makeText(contexto, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        });





        return convertView;
	}


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_autorizar:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.rb_rechazar:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }





    // Filter Class
 /*   public void filter(String charText) {

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

    }*/

}
