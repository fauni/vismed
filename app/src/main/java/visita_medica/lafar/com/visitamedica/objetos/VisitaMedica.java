package visita_medica.lafar.com.visitamedica.objetos;

import java.sql.Driver;

/**
 * Created by debbie on 24-07-15.
 */
public class VisitaMedica {

    private String id_visita;
    private String Codbarra;
    private String NUMVISI;
    private String CODMED;
    private String ESPE1;
    private String NOMBREMED;
    private String DIRECCION;
    private String HOSPITAL;
    private String REGIONAL;
    private String MESV;
    private String ANOV;
    private String cate;
    private String CIUDAD;
    private String estado;
    private String dia_visita;
    private String correlativo_visita;
    private String semana_visita;

    public VisitaMedica(String id_visita, String Codbarra,  String NUMVISI, String CODMED, String ESPE1,
                        String NOMBREMED,  String  DIRECCION, String HOSPITAL, String REGIONAL,
                        String MESV, String ANOV, String cate,  String CIUDAD,
                        String estado, String dia_visita, String correlativo_visita, String semana_visita
                        )
    {
        this.id_visita=id_visita;
        this.Codbarra=Codbarra;
        this. NUMVISI=NUMVISI;
        this.CODMED=CODMED;
        this.ESPE1=ESPE1;
        this.NOMBREMED=NOMBREMED;
        this.DIRECCION=DIRECCION;
        this.HOSPITAL=HOSPITAL;
        this.REGIONAL=REGIONAL;
        this.MESV=MESV;
        this.ANOV=ANOV;
        this.cate=cate;
        this.CIUDAD=CIUDAD;
        this.estado=estado;
        this.dia_visita=dia_visita;
        this.correlativo_visita=correlativo_visita;
        this.semana_visita=semana_visita;
    }
    public String getId_visita()
    {
        return id_visita;
    }
    public String getCodbarra(){
        return Codbarra;
    }
    public String getNUMVISI()
    {
        return NUMVISI ;
    }
    public String getCODMED()
    {
        return CODMED;
    }
    public String getESPE1()
    {
        return ESPE1 ;
    }
    public String  getNOMBREMED()
    {
        return NOMBREMED;
    }
    public String getDIRECCION ()
    {
        return  DIRECCION;
    }
    public String getHOSPITAL()
    {
        return HOSPITAL ;
    }
    public String getREGIONAL(){return REGIONAL;}
    public String getMESV()
    {
        return MESV;
    }
    public String getANOV()
    {
        return ANOV;
    }
    public String getCate()
    {
        return cate;
    }
    public String getCIUDAD()
    {
        return CIUDAD;
    }
    public String getEstado()
    {
        return estado;
    }
    public String getDia_visita()
    {
        return dia_visita;
    }
    public String getCorrelativo_visita(){return correlativo_visita;}
    public String getSemana_visita(){return semana_visita;}

}
