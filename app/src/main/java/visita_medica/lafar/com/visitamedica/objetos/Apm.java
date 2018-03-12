package visita_medica.lafar.com.visitamedica.objetos;

/**
 * Created by debbie on 14-09-15.
 */
public class Apm {

    private String apm;
    private String nombre_vis;


    public Apm( String apm, String nombre_vis)
    {
        this.apm=apm;
        this.nombre_vis=nombre_vis;
    }

    public String getApm(){return apm;}
    public String getNombre_vis(){return nombre_vis;}
}
