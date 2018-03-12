package visita_medica.lafar.com.visitamedica.objetos;



public class BoletajeHistorico {

    private String id_historico;
    private String id_visita;
    private String Codbarra;
    private String FECHACELULAR;
    private String MESV;
    private String ANOV;
    private String APM;
    private String OBSER;
    private String MOTIVO_OBSER;
    private String ciudad;
    private String lat;
    private String lon;
    private String acompanhiado;
    private String estado_subida;


    public BoletajeHistorico(String id_historico,String id_visita, String Codbarra, String FECHACELULAR, String MESV, String ANOV, String APM, String OBSER,
            String MOTIVO_OBSER, String ciudad, String lat, String lon,String acompanhiado, String estado_subida
    )
    {
        this.id_historico=id_historico;
        this.id_visita=id_visita;
        this.Codbarra=Codbarra;
        this.FECHACELULAR=FECHACELULAR;
        this.MESV=MESV;
        this.ANOV=ANOV;
        this.APM=APM;
        this.OBSER=OBSER;
        this.MOTIVO_OBSER=MOTIVO_OBSER;
        this.ciudad=ciudad;
        this.lat=lat;
        this.lon=lon;
        this.acompanhiado=acompanhiado;
        this.estado_subida=estado_subida;

    }
    public String getId_historico() {return id_historico;}
    public String getId_visita() {return id_visita;}
    public String getCodbarra()
    {
        return Codbarra;
    }
    public String getFECHACELULAR()
    {
        return FECHACELULAR;
    }
    public String getMESV()
    {
        return MESV;
    }
    public String getANOV(){return ANOV;}
    public String getAPM()
    {
        return APM;
    }
    public String getOBSER()
    {
        return OBSER;
    }
    public String getMOTIVO_OBSER(){return  MOTIVO_OBSER;}
    public String getCiudad(){return   ciudad;}
    public String getLat() {return  lat;}
    public String getLon() {return lon;}
    public String getAcompanhiado(){return acompanhiado;}
    public String getEstado_subida(){return  estado_subida;}
}
