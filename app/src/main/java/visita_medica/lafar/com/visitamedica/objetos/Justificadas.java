package visita_medica.lafar.com.visitamedica.objetos;

/**
 * Created by ASISTENTE DESARROLLO on 27/06/2016.
 */
public class Justificadas {

    private String id_visita;
    private String apm;
    private String nombre_doctor;
    private String MESV;
    private String ANOV;
    private String fecha_tab;
    private String hora_tab;
    private String fecha_sinc;
    private String estado_justificada;
    private String estado_sinc;
    private String MOTIVO_OBSER;

    public Justificadas(
            String id_visita,
            String apm,
            String nombre_doctor,
            String MESV,
            String ANOV,
            String fecha_tab,
            String hora_tab,
            String fecha_sinc,
            String estado_justificada,
            String estado_sinc, String MOTIVO_OBSER)
    {
        this.id_visita=id_visita;
        this.apm=apm;
        this.nombre_doctor=nombre_doctor;
        this.MESV=MESV;
        this.ANOV=ANOV;
        this.fecha_tab=fecha_tab;
        this.hora_tab=hora_tab;
        this.fecha_sinc=fecha_sinc;
        this.estado_justificada=estado_justificada;
        this.estado_sinc=estado_sinc;
        this.MOTIVO_OBSER=MOTIVO_OBSER;
    }
    public String getId_visita(){return id_visita;}
    public String getApm(){return apm;}
    public String getNombre_doctor(){return nombre_doctor;}
    public String getMESV(){return MESV;}
    public String getANOV(){return ANOV;}
    public String getFecha_tab(){return fecha_tab ;}
    public String getHora_tab(){return hora_tab;}
    public String getFecha_sinc(){return fecha_sinc;}
    public String getEstado_justificada(){return estado_justificada;}

    public void setEstado_justificada(String estado_justificada){
        this.estado_justificada = estado_justificada;
    }

    public String getEstado_sinc(){return estado_sinc;}


    public String getMOTIVO_OBSER(){return MOTIVO_OBSER;}
}
