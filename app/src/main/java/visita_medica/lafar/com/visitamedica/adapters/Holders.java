package visita_medica.lafar.com.visitamedica.adapters;

import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Holders {

	public static class Holder_visita_medica{
		
		ImageView img_logo;
		TextView lbl_nombre;
		TextView lbl_direccion_useful;
		TextView lbl_celular_useful;
        TextView lbl_observaciones_useful;
        TextView lbl_fecha_visita_y_turno;
        LinearLayout ly_fondo_tarea_pendiente;

	}


	public static class Holder_visita_realizada{

		ImageView img_estado;
		TextView lbl_nombre;
		TextView lbl_codbarra;
		TextView lbl_fecha_visita;
		TextView lbl_ciudad;
		TextView lbl_observaciones;
        LinearLayout ly_estado_sic;
		TextView lbl_numero_de_dia;
		FrameLayout layout_dia;
	}


    public static class Holder_producto{

        TextView lbl_nombre_producto;
        TextView lbl_codigo_producto;

    }


	public static class Holder_banco_muestras{

		LinearLayout ly_fondo_estado_banco_muestra;
		ImageView img_logo;
		TextView lbl_codigo_banco_muestra;
		TextView lbl_nombre_medico;
		TextView lbl_fecha_generacion;
		TextView lbl_linea_vis;
		TextView lbl_producto;

	}


	public static class Holder_justificadas{

        TextView lbl_id_visita;
		TextView lbl_nombre_doctor;
		TextView lbl_fecha_hora_realizada;
		RadioButton autorizar;
		RadioButton rechazar;
		RadioGroup radioGroup;

	}

}
