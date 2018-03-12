package visita_medica.lafar.com.visitamedica.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by debbie on 23-07-15.
 */
public class SharedPreferencesP {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String id_visitador;

    /*
    ID_VISITADOR ID_VISITADOR ID_VISITADOR ID_VISITADOR ID_VISITADOR ID_VISITADOR
     */
    public void Guardar_Shared(Context context,String id_visitador)
    {

        prefs=context.getSharedPreferences("visitador", Context.MODE_PRIVATE);
        editor= prefs.edit();
        editor.putString("id_visitador", id_visitador);
        editor.commit();

    }




    public String   Consultar_si_hay_registro(Context context)
    {
        prefs =context.getSharedPreferences("visitador", Context.MODE_PRIVATE);
        editor = prefs.edit();
        id_visitador = prefs.getString("id_visitador",null);
        if(id_visitador != null) {
            return id_visitador;
        }
        else
        {
            return "";
        }

    }


    public void  Borrar(Context context)
    {
        prefs =context.getSharedPreferences("visitador", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.clear();
        editor.commit();

    }


/*
TIPO_VISITADOR TIPO_VISITADOR TIPO_VISITADOR TIPO_VISITADOR TIPO_VISITADOR TIPO_VISITADOR
 */


    public void Guardar_tipo(Context context,String tipo)
    {

        prefs=context.getSharedPreferences("tipo", Context.MODE_PRIVATE);
        editor= prefs.edit();
        editor.putString("tipo_visitador", tipo);
        editor.commit();

    }
    public String   Consultar_tipo(Context context)
    {
        prefs =context.getSharedPreferences("tipo", Context.MODE_PRIVATE);
        editor = prefs.edit();
        id_visitador = prefs.getString("tipo_visitador",null);
        if(id_visitador != null) {
            return id_visitador;
        }
        else
        {
            return "";
        }
    }
    public void  Borrar_tipo(Context context)
    {
        prefs =context.getSharedPreferences("tipo", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.clear();
        editor.commit();

    }

}
