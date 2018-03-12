package visita_medica.lafar.com.visitamedica.objetos;

/**
 * Created by debbie on 24-07-15.
 */
public class DetalleVisita {

    private String id_detalle_visita;
    private String Codbarra;
    private String CODMED;
    private String CODIGOMM;
    private String CANTIDAD;
    private String MM;
    private String mes;
    private String anhio;


    public DetalleVisita(String id_detalle_visita,String Codbarra, String CODMED,String CODIGOMM, String CANTIDAD,String MM,
String mes,  String anhio
    )
    {
        this.id_detalle_visita=id_detalle_visita;
        this.Codbarra=Codbarra;
        this.CODMED=CODMED;
        this.CODIGOMM=CODIGOMM;
        this.CANTIDAD=CANTIDAD;
        this.MM=MM;
        this.mes=mes;
        this.anhio=anhio;
    }

    public String getId_detalle_visita(){return id_detalle_visita ;}
    public String getCodbarra(){return Codbarra ;}
    public String getCODMED (){return CODMED;}
    public String getCODIGOMM(){return CODIGOMM;}
    public String getCANTIDAD (){return CANTIDAD;}
    public String getMM(){return  MM;}
    public  String getMes() {return  mes;}
    public String getAnhio() {return  anhio;}


    public void setCANTIDAD(String CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }
}
