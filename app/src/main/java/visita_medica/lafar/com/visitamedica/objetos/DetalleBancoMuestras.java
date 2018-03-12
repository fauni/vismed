package visita_medica.lafar.com.visitamedica.objetos;

/**
 * Created by debbie on 28-01-16.
 */
public class DetalleBancoMuestras {

    private String id_detalle_banco_muestras;
    private String id_formulario_de_banco_de_muestras;
    private String codigo_mm;
    private String mm;
    private String cantidad;



    public DetalleBancoMuestras(String id_detalle_banco_muestras,
            String id_formulario_de_banco_de_muestras,String codigomm, String mm  ,String cantidad

    )
    {
        this.id_detalle_banco_muestras=id_detalle_banco_muestras;
        this.id_formulario_de_banco_de_muestras=id_formulario_de_banco_de_muestras;
        this.codigo_mm=codigomm;
        this.mm=mm;
        this.cantidad=cantidad;

    }
    public String getId_detalle_banco_muestras(){return id_detalle_banco_muestras;}
    public String getId_formulario_de_banco_de_muestras(){return id_formulario_de_banco_de_muestras;}
    public String getCodigo_mm(){return codigo_mm;}
    public String getMm(){return mm;}
    public String getCantidad(){return cantidad;}
}
