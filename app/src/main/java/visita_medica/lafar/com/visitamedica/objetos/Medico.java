package visita_medica.lafar.com.visitamedica.objetos;

/**
 * Created by debbie on 14-09-15.
 */
public class Medico {

    private String id_med;
    private String nombre_med;


    public Medico(String id_med, String nombre_med)
    {
        this.id_med=id_med;
        this.nombre_med=nombre_med;
    }
    public String getId_med(){return id_med;}
    public String getNombre_med (){return nombre_med;}
}
