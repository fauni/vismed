package visita_medica.lafar.com.visitamedica.web;

/**
 * Created by debbie on 22-07-15.
 */
public class VariablesUrl {

    //String general="http://200.87.143.226:80/visitadores_lafar_nueva_version/";
    //String general ="http://192.168.1.161/visitadores_lafar_nueva_version/"; //PC FRANZ
    //String general ="http://192.168.2.210/visitadores_lafar_nueva_version/";
    String general ="http://192.168.1.213/visitadores_lafar_nueva_version/"; // Servidor Centos
    //String general ="http://192.168.1.232/visitadores_lafar_desarrollo/";
    //String general ="http://192.168.1.161/visitadores_lafar_nueva_version/";
    //String general="http://200.87.97.83:80/visitadores_prueba/";

    String login="android/login.php";
    public String function_login()
    {
        return general+login;
    }

    String cargar_visita="android/descarga_visitas.php";
    public String function_cargar_visita()
    {
        return general+cargar_visita;
    }


    String guardar_visitas_positivas="android/guardar_visitas_positivas.php";
    public String function_guardar_visitas_positivas()
    {
        return  general+guardar_visitas_positivas;
    }

    String guardar_visitas_negativas="android/guardar_visitas_negativas.php";
    public String function_guardar_visitas_negativas()
    {
        return  general+guardar_visitas_negativas;
    }

    String guardar_visitas_recuperadas="android/guardar_visitas_recuperadas.php";
    public String function_guardar_visitas_recuperadas()
    {
        return  general+guardar_visitas_recuperadas;
    }

    String descarga_productos="android/descarga_mm.php";
    public String function_descarga_productos()
    {
        return  general+descarga_productos;
    }

    String recuperar_contrasenhia="android/he_olvidado_mi_contrasenhia.php";
    public String function_recuperar_contrasenhia()
    {
        return  general+recuperar_contrasenhia;
    }


    String descargar_banco_muestras="android/descarga_banco_muestras.php";
    public String function_descargar_banco_muestras(){return general+descargar_banco_muestras ; }


    String guardar_banco_muestras="android/guardar_banco_muestras.php";
    public String function_guardar_banco_muestras(){return general+guardar_banco_muestras ; }

    String descargar_medico_apm="android/descarga_medico_apm.php";
    public String function_descargar_medico_apm(){return general+descargar_medico_apm ; }

    String guardar_banco_muestras_supervisor="android/guardar_banco_muestras_supervisor.php";
    public String function_guardar_banco_muestras_supervisor(){return general+guardar_banco_muestras_supervisor; }

    String descargar_negativas_realizadas="android/descarga_realizadas_negativas.php";
    public String function_descargar_negativas_realizadas(){return general+descargar_negativas_realizadas; }

    String mi_cobertura="android/mi_cobertura.php";
    public String function_mi_cobertura(){return general+mi_cobertura;}


    String positivas_realizadas="android/descarga_positivas_realizadas.php";
    public String function_positivas_realizadas(){return general+positivas_realizadas;}



    String direccion_firmas="firmas/";
    public String function_direccion_firmas(){return general+direccion_firmas;}




    String seguimiento_banco_muestras="android/seguimiento_banco_muestras.php";
    public String function_seguimiento_banco_muestras(){return general+seguimiento_banco_muestras;}

    String descarga_justificadas_supervisor="android/descarga_justificadas_supervisor.php";
    public String function_descarga_justificadas_supervisor(){return general+descarga_justificadas_supervisor;}



    String guardar_historico_aztualizar_estado="android/guardar_historico_aztualizar_estado.php";
    public String function_guardar_historico_aztualizar_estado(){return general+guardar_historico_aztualizar_estado;}



    String sincronizar_justificadas="android/sincronizar_justificadas.php";
    public String function_sincronizar_justificadas(){return general+sincronizar_justificadas;}


}
