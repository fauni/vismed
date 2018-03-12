package visita_medica.lafar.com.visitamedica.objetos;

/**
 * Created by debbie on 28-01-16.
 */
public class Tblbancomm {


    private String id_formulario_de_banco_de_muestras;
    private String apm;
    private String nombre_vis;
    private String linea_vis;
    private String especialidad_med;
    private String categoria_med;
    private String codmed;
    private String nombre_med;
    private String regional;
    private String fecha_solicitud;
    private String estado;   //(entregado,  no entregado,  pendiente_aprobación)
    private String fecha_entregado;
    private String justificacion;
    private String bandera_seguimiento;
    private String fecha_seguimiento;
    private String observaciones_seguimiento;
    private String ruta_imagen_banco_muestras;
    private String estado_sinc;
    private String estado_sinc_seguimiento;


    public Tblbancomm(
            String id_formulario_de_banco_de_muestras,
            String apm,
            String nombre_vis,
            String linea_vis,
            String especialidad_med,
            String categoria_med,
            String codmed,
            String nombre_med,
            String regional,
            String fecha_solicitud,
            String estado,
            String fecha_entregado,
            String justificacion,
            String bandera_seguimiento,
            String fecha_seguimiento,
            String observaciones_seguimiento,
            String ruta_imagen_banco_muestras,
            String estado_sinc,
            String estado_sinc_seguimiento
    )
    {
        this.id_formulario_de_banco_de_muestras=id_formulario_de_banco_de_muestras;
        this.apm=apm;
        this.nombre_vis=nombre_vis;
        this.linea_vis=linea_vis;
        this.especialidad_med=especialidad_med;
        this.categoria_med=categoria_med;
        this.codmed=codmed;
        this.nombre_med=nombre_med;
        this.regional=regional;
        this.fecha_solicitud=fecha_solicitud;
        this.estado=estado;
        this.fecha_entregado=fecha_entregado;
        this.justificacion=justificacion;
        this.bandera_seguimiento=bandera_seguimiento;
        this.observaciones_seguimiento=observaciones_seguimiento;
        this.fecha_seguimiento=fecha_seguimiento;
        this.ruta_imagen_banco_muestras=ruta_imagen_banco_muestras;
        this.estado_sinc=estado_sinc;
        this.estado_sinc_seguimiento=estado_sinc_seguimiento;
    }
    public String getId_formulario_de_banco_de_muestras(){return id_formulario_de_banco_de_muestras;}
    public String getApm(){return apm;}
    public String getNombre_vis(){return nombre_vis;}
    public String getLinea_vis(){return linea_vis;}
    public String getEspecialidad_med(){return especialidad_med;}
    public String getCategoria_med(){return categoria_med;}
    public String getCodmed(){return codmed;}
    public String getNombre_med(){return nombre_med;}
    public String getRegional(){return regional;}
    public String getFecha_solicitud(){return fecha_solicitud;}
    public String getEstado(){return estado;}
    public String getFecha_entregado(){return fecha_entregado;}
    public String getJustificacion(){return justificacion;}

    public String getBandera_seguimiento(){return bandera_seguimiento;}
    public String getFecha_seguimiento() { return fecha_seguimiento;}
    public String getObservaciones_seguimiento(){return observaciones_seguimiento;}

    public String getRuta_imagen_banco_muestras(){return ruta_imagen_banco_muestras;}
    public String getEstado_sinc(){return estado_sinc;}
    public String getEstado_sinc_seguimiento(){return estado_sinc_seguimiento;}
}
