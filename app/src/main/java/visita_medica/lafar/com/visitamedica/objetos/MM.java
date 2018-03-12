package visita_medica.lafar.com.visitamedica.objetos;

/**
 * Created by debbie on 14-09-15.
 */
public class MM {

    private String CODIGOMM;
    private String MM;


    public MM(String CODIGOMM, String MM)
    {
        this.CODIGOMM=CODIGOMM;
        this.MM=MM;
    }
    public String getCODIGOMM(){return CODIGOMM ;}
    public String getMM (){return MM;}
}
