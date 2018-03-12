package visita_medica.lafar.com.visitamedica.objetos;



public class BoletajeAuxiliar {

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
    private String estado_subida;
    private String negativo_editado;
    private String ruta_imagen;
    private String acompanhiado;
    private String fecha_mas_diez_minutos;
    private String fecha_mas_quince_minutos;
    private String estado_alarma;
    private String estado_guardado;


    public BoletajeAuxiliar(String id_visita, String Codbarra, String FECHACELULAR, String MESV, String ANOV, String APM, String OBSER, String MOTIVO_OBSER, String ciudad,
                            String lat, String lon, String estado_subida, String negativo_editado, String ruta_imagen, String acompanhiado,
                            String fecha_mas_diez_minutos, String fecha_mas_quince_minutos,String estado_alarma, String estado_guardado
    )
    {
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
        this.estado_subida=estado_subida;
        this.negativo_editado=negativo_editado;
        this.ruta_imagen=ruta_imagen;
        this.acompanhiado=acompanhiado;
        this.fecha_mas_diez_minutos=fecha_mas_diez_minutos;
        this.fecha_mas_quince_minutos=fecha_mas_quince_minutos;
        this.estado_alarma=estado_alarma;
        this.estado_guardado=estado_guardado;

    }
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
    public String getEstado_subida(){return  estado_subida;}
    public String getNegativo_editado(){return negativo_editado;}
    public String getRuta_imagen(){return  ruta_imagen;}
    public String getAcompanhiado(){return  acompanhiado;}
    public String getFecha_mas_diez_minutos(){return  fecha_mas_diez_minutos;}
    public String getFecha_mas_quince_minutos(){return  fecha_mas_quince_minutos;}
    public String getEstado_alarma(){return  estado_alarma;}
    public String getEstado_guardado(){return  estado_guardado;}
}
