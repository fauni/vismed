package visita_medica.lafar.com.visitamedica.db_model;

        import java.util.ArrayList;

        import android.content.Context;
        import android.database.Cursor;

        import visita_medica.lafar.com.visitamedica.objetos.Apm;
        import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
        import visita_medica.lafar.com.visitamedica.objetos.BoletajeAuxiliar;
        import visita_medica.lafar.com.visitamedica.objetos.BoletajeHistorico;
        import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
        import visita_medica.lafar.com.visitamedica.objetos.DetalleVisita;
        import visita_medica.lafar.com.visitamedica.objetos.Justificadas;
        import visita_medica.lafar.com.visitamedica.objetos.MM;
        import visita_medica.lafar.com.visitamedica.objetos.Medico;
        import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;
        import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;


public class Visitas_controlador extends Visitas_client {

    public Visitas_controlador(Context context, String dbName) {
        super(context, dbName);
    }

    public void setDatabaseListener(DatabaseCallback dbListener){
        this.setDatabaseListener(dbListener);
    }
    //////Objetos********************************************************

    private ArrayList<VisitaMedica> visita=new ArrayList<VisitaMedica>();
    private ArrayList<DetalleVisita> detalle_visitas=new ArrayList<DetalleVisita>();
    private ArrayList<Boletaje> boletaje=new ArrayList<Boletaje>();
    private ArrayList<BoletajeAuxiliar> boletaje_auxiliar=new ArrayList<BoletajeAuxiliar>();
    private ArrayList<BoletajeHistorico> boletaje_historico=new ArrayList<BoletajeHistorico>();
    private ArrayList<MM> mm=new ArrayList<MM>();
    private ArrayList<Tblbancomm> banco_muestras= new ArrayList<Tblbancomm>();
    private ArrayList<DetalleBancoMuestras> detalle_banco_muestras= new ArrayList<DetalleBancoMuestras>();
    private ArrayList<Medico> medico= new ArrayList<Medico>();
    private ArrayList<Apm> apm= new ArrayList<Apm>();
    private ArrayList<Justificadas> justificadas= new ArrayList<Justificadas>();

    /////Funciones*****************************************************



  /*  public ArrayList<VisitaMedica> select_visita_medica_pendiente_listado(String dia_hoy, String dia_uno,String dia_dos, String dia_tres, String dia_cuatro,String dia_cinco,  String dia_seis,String dia_siete, String dia_ocho,String dia_nueve){
        Cursor cursor = this.mostrar_visitas_medica_pendientes_listado(dia_hoy, dia_uno,  dia_dos, dia_tres,  dia_cuatro, dia_cinco, dia_seis,dia_siete, dia_ocho, dia_nueve);*/

    public ArrayList<VisitaMedica> select_visita_medica_pendiente_listado(String dia_hoy, String dia_uno,String dia_dos, String dia_tres, String dia_cuatro){
        Cursor cursor = this.mostrar_visitas_medica_pendientes_listado(dia_hoy, dia_uno,  dia_dos, dia_tres,  dia_cuatro);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            visita.add(new VisitaMedica(
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NUMVISI)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESPE1)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NOMBREMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.HOSPITAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.REGIONAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CATE)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIA_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CORRELATIVO_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.SEMANA_VISITA))
                    ));
        }
        return visita;
    }


    public String mostrar_dia_de_hoy(){
        Cursor cursor = this.mostrar_visitas_medica_pendientes();
        String dia_visita="";
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            visita.add(new VisitaMedica(
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NUMVISI)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESPE1)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NOMBREMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.HOSPITAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.REGIONAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CATE)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIA_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CORRELATIVO_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.SEMANA_VISITA))
            ));
        }

        for(int i= 0; i<visita.size();i++)
        {
            dia_visita=visita.get(0).getDia_visita();
        }

        return dia_visita;
    }



    public ArrayList<VisitaMedica> select_visita_medica_pendiente(){
        Cursor cursor = this.mostrar_visitas_medica_pendientes();

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            visita.add(new VisitaMedica(
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NUMVISI)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESPE1)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NOMBREMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.HOSPITAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.REGIONAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CATE)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIA_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CORRELATIVO_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.SEMANA_VISITA))
            ));
        }
        return visita;
    }



    public ArrayList<VisitaMedica> select_all_visita_medica(){

        Cursor cursor = this.mostrar_all_visitas_medicas();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            visita.add(new VisitaMedica(
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NUMVISI)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESPE1)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NOMBREMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.HOSPITAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.REGIONAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CATE)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIA_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CORRELATIVO_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.SEMANA_VISITA))

            ));
        }
        return visita;
    }


   /* public ArrayList<VisitaMedica> select_visita_medica_where_cate(String cate, String dia_hoy, String dia_uno, String dia_dos, String dia_tres, String dia_cuatro, String dia_cinco,  String dia_seis,  String dia_siete,String dia_ocho, String dia_nueve){
        Cursor cursor = this.mostrar_visitas_medicas_where_cate(cate, dia_hoy, dia_uno, dia_dos, dia_tres, dia_cuatro,dia_cinco, dia_seis,dia_siete,dia_ocho,dia_nueve);
*/

    public ArrayList<VisitaMedica> select_visita_medica_where_cate(String cate, String dia_hoy, String dia_uno, String dia_dos, String dia_tres, String dia_cuatro){
        Cursor cursor = this.mostrar_visitas_medicas_where_cate(cate, dia_hoy, dia_uno, dia_dos, dia_tres, dia_cuatro);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            visita.add(new VisitaMedica(
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NUMVISI)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESPE1)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NOMBREMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.HOSPITAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.REGIONAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CATE)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIA_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CORRELATIVO_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.SEMANA_VISITA))

            ));
        }
        return visita;
    }



    public ArrayList<VisitaMedica> select_visitas_dado_semana_visita(String semana_visita){
        Cursor cursor = this.mostrar_visitas_dado_semana_visita(semana_visita);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            visita.add(new VisitaMedica(
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NUMVISI)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESPE1)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NOMBREMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.HOSPITAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.REGIONAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CATE)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIA_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CORRELATIVO_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.SEMANA_VISITA))

            ));
        }
        return visita;
    }

   /* public ArrayList<VisitaMedica> select_visita_medica_where_espe(String espe, String dia_hoy, String dia_uno, String dia_dos, String dia_tres, String dia_cuatro,String dia_cinco,  String dia_seis,String dia_siete,String dia_ocho, String dia_nueve){
        Cursor cursor = this.mostrar_visitas_medicas_where_espe(espe, dia_hoy, dia_uno, dia_dos, dia_tres, dia_cuatro,dia_cinco,dia_seis,dia_siete,dia_ocho,dia_nueve);*/
   public ArrayList<VisitaMedica> select_visita_medica_where_espe(String espe, String dia_hoy, String dia_uno, String dia_dos, String dia_tres, String dia_cuatro){
       Cursor cursor = this.mostrar_visitas_medicas_where_espe(espe, dia_hoy, dia_uno, dia_dos, dia_tres, dia_cuatro);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            visita.add(new VisitaMedica(
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NUMVISI)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESPE1)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NOMBREMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.HOSPITAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.REGIONAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CATE)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIA_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CORRELATIVO_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.SEMANA_VISITA))

            ));
        }
        return visita;
    }



    public ArrayList<VisitaMedica> select_visita_medica_dado_id_visita(String id_visita){
        Cursor cursor = this.mostrar_visitas_medica_dado_id_visita(id_visita);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            visita.add(new VisitaMedica(
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NUMVISI)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESPE1)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.NOMBREMED)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.HOSPITAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.REGIONAL)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CATE)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.DIA_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.CORRELATIVO_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Visitas.SEMANA_VISITA))

            ));
        }
        return visita;
    }


    public String select_estado_visita_dado_id_visita(String id_visita){
        Cursor cursor = this.mostrar_visitas_medica_dado_id_visita(id_visita);
        String estado="";
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){

               estado=     cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO));

        }
        return estado;
    }


    public String select_estado_dado_id_visita(String id_visita){
        Cursor cursor = this.mostrar_visitas_medica_dado_id_visita(id_visita);
        String estado_visita=null;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            estado_visita=cursor.getString(cursor.getColumnIndex(Table.Visitas.ESTADO));

        }
        return estado_visita;
    }


    public  void add_visitas(ArrayList<VisitaMedica>  visita){
        this.insertar_visitas(visita);
    }

    public void delete_visitas(){
        this.borrar_visitas();
    }


    public  void update_estado_visita(String id_visita, String estado){
        this.modificar_estado_visita(id_visita, estado);
    }


    /******************* DETALLE VISITAS**************************/
    public  void add_detalle_visita(ArrayList<DetalleVisita>  detalle_visita){
        this.insertar_detalle_visita(detalle_visita);
    }


    public ArrayList<DetalleVisita> select_all_detalle_visita(){
        Cursor cursor = this.mostrar_todo_detalle_visita();

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            detalle_visitas.add(new DetalleVisita(
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.ID_DETALLE_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.CODIGOMM)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.CANTIDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.MM)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.MES)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.ANHIO))
            ));
        }
        return detalle_visitas;
    }



    public ArrayList<DetalleVisita> select_detalle_visita_dado_el_id_visita(String Codbarra,String mes, String anhio){
        Cursor cursor = this.mostrar_detalle_visita_dado_un_id_visita(Codbarra, mes, anhio);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            detalle_visitas.add(new DetalleVisita(
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.ID_DETALLE_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.CODMED)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.CODIGOMM)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.CANTIDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.MM)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.MES)),
                    cursor.getString(cursor.getColumnIndex(Table.DetalleVisitas.ANHIO))
            ));

        }
        return detalle_visitas;
    }






    public void delete_detalle_visitas(){
        this.borrar_detalle_visitas();
    }



    //////////////////////Todo el boletaje///////////////////////////////////////////////////
    public  void add_boletaje(ArrayList<Boletaje>  boletaje){
        this.insertar_boletaje(boletaje);
    }

    public ArrayList<Boletaje> select_all_boletaje() {
        Cursor cursor = this.mostrar_todo_boletaje();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
          boletaje.add(new Boletaje(
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                  cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))

          ));
        }
        return boletaje;


    }




    public ArrayList<Boletaje> select_all_boletaje_for_listview() {
        Cursor cursor = this.mostrar_todo_boletaje();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

      /* boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))*/
            String  id_visita= cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA));
            String estado="";
            String direccion="";
            String nombre_doctor="";
            String dia_visita="";
            String correlativo_visita="";
            ArrayList<VisitaMedica> visita_medica=new ArrayList<VisitaMedica>();
            visita_medica=select_visita_medica_dado_id_visita(id_visita);
            for(int i=0; i<visita_medica.size();i++)
            {
            estado=visita_medica.get(i).getEstado();
            direccion= visita_medica.get(i).getDIRECCION();
            nombre_doctor= visita_medica.get(i).getNOMBREMED();
            dia_visita=visita_medica.get(i).getDia_visita();
            correlativo_visita=visita_medica.get(i).getCorrelativo_visita();
            }


            boletaje.add(new Boletaje(
                    id_visita,
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),

/*
  vamos a poner valores de la tabla visita  para el listview
  apm no servira para el campo estado
  obser para el campo direccion
  motivo_obser para el correlativo_visita
  ciudad para el campo nombre
  acompanhiado para el campo dia_visita
 */
                    /*cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),*/

                    estado,
                    direccion,
                    correlativo_visita,
                    nombre_doctor,

                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),

                   // cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
                    dia_visita


            ));
        }
        return boletaje;


    }




    public Integer select_count_all_tareas_realizadas() {
        Cursor cursor = this.mostrar_todo_boletaje();
        Integer contador_tareas_realizadas=0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))

            ));
            contador_tareas_realizadas=contador_tareas_realizadas+1;
        }
        return contador_tareas_realizadas;


    }








    public ArrayList<Boletaje> select_boletaje_where_estado_pendiete() {
        Cursor cursor = this.mostrar_boletaje_subida_pendiente();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
            ));
        }
        return boletaje;
    }



    public ArrayList<Boletaje> select_boletaje_where_estado_pendiete_for_listview() {
        Cursor cursor = this.mostrar_boletaje_subida_pendiente();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
           /* boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))*/

            String  id_visita= cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA));
            String estado="";
            String direccion="";
            String nombre_doctor="";
            String dia_visita="";
            String correlativo_visita="";
            ArrayList<VisitaMedica> visita_medica=new ArrayList<VisitaMedica>();
            visita_medica=select_visita_medica_dado_id_visita(id_visita);
            for(int i=0; i<visita_medica.size();i++)
            {
                estado=visita_medica.get(i).getEstado();
                direccion= visita_medica.get(i).getDIRECCION();
                nombre_doctor= visita_medica.get(i).getNOMBREMED();
                dia_visita=visita_medica.get(i).getDia_visita();
                correlativo_visita=visita_medica.get(i).getCorrelativo_visita();
            }


            boletaje.add(new Boletaje(
                    id_visita,
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),

/*
 vamos a poner valores de la tabla visita  para el listview
  apm no servira para el campo estado
  obser para el campo direccion
  motivo_obser para el campo correlativo_visita
  ciudad para el campo nombre
  acompanhiado para el campo dia_visita

 */
                    /*cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),*/

                    estado,
                    direccion,
                    correlativo_visita,
                    nombre_doctor,

                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),

                    // cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
                    dia_visita
            ));
        }
        return boletaje;
    }




    public ArrayList<Boletaje> select_boletaje_where_estado_pendiete_and_estado_equals_positivo() {
        Cursor cursor = this.mostrar_boletaje_subida_pendiente_y_estado_igual_positivo();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
            ));
        }
        return boletaje;
    }



    public ArrayList<Boletaje> select_boletaje_where_estado_pendiete_and_estado_equals_negativo() {
        Cursor cursor = this.mostrar_boletaje_subida_pendiente_y_estado_igual_negativo();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
            ));
        }
        return boletaje;
    }


    public ArrayList<Boletaje> select_boletaje_where_estado_pendiete_and_recuperada() {
        Cursor cursor = this.mostrar_boletaje_subida_pendiente_y_recuperada();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
            ));
        }
        return boletaje;
    }



    public ArrayList<Boletaje> select_boletaje_where_estado_not_pendiete() {
        Cursor cursor = this.mostrar_boletaje_subida_not_pendiente();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
            ));
        }
        return boletaje;
    }




    public ArrayList<Boletaje> select_boletaje_where_estado_not_pendiete_for_listview() {
        Cursor cursor = this.mostrar_boletaje_subida_not_pendiente();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
           /* boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))*/

            String  id_visita= cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA));
            String estado="";
            String direccion="";
            String nombre_doctor="";
            String dia_visita="";
            String correlativo_visita="";
            ArrayList<VisitaMedica> visita_medica=new ArrayList<VisitaMedica>();
            visita_medica=select_visita_medica_dado_id_visita(id_visita);
            for(int i=0; i<visita_medica.size();i++)
            {
                estado=visita_medica.get(i).getEstado();
                direccion= visita_medica.get(i).getDIRECCION();
                nombre_doctor= visita_medica.get(i).getNOMBREMED();
                dia_visita=visita_medica.get(i).getDia_visita();
                correlativo_visita=visita_medica.get(i).getCorrelativo_visita();
            }


            boletaje.add(new Boletaje(
                    id_visita,
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),

/*
 vamos a poner valores de la tabla visita  para el listview
  apm no servira para el campo estado
  obser para el campo direccion
  motivo_obser para el campo correlativo_visita
  ciudad para el campo nombre
  acompanhiado para el campo dia_visita
 */
                    /*cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),*/

                    estado,
                    direccion,
                    correlativo_visita,
                    nombre_doctor,

                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),

                    // cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
                    dia_visita

            ));
        }
        return boletaje;
    }


    public ArrayList<Boletaje> select_boletaje_where_fecha_like(String fecha){
        Cursor cursor = this.mostrar_boletaje_where_fecha_like(fecha);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))


            ));

            System.out.println("##############<3 <3 <3 " + cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)));
        }
        return boletaje;
    }




/*
POR MORTIVOS DE BUSCADOR DE NOMBRES
 */


    public ArrayList<Boletaje> select_boletaje_where_fecha_like_for_listview(String fecha){
        Cursor cursor = this.mostrar_boletaje_where_fecha_like(fecha);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
           /* boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))*/

            String  id_visita= cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA));
            String estado="";
            String direccion="";
            String nombre_doctor="";
            String dia_visita="";
            String correlativo_visita="";
            ArrayList<VisitaMedica> visita_medica=new ArrayList<VisitaMedica>();
            visita_medica=select_visita_medica_dado_id_visita(id_visita);
            for(int i=0; i<visita_medica.size();i++)
            {
                estado=visita_medica.get(i).getEstado();
                direccion= visita_medica.get(i).getDIRECCION();
                nombre_doctor= visita_medica.get(i).getNOMBREMED();
                dia_visita=visita_medica.get(i).getDia_visita();
                correlativo_visita=visita_medica.get(i).getCorrelativo_visita();
            }


            boletaje.add(new Boletaje(
                    id_visita,
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),

/*
 vamos a poner valores de la tabla visita  para el listview
  apm no servira para el campo estado
  obser para el campo direccion
  motivo_obser para el campo correlativo_visita
  ciudad para el campo nombre
  acompanhiado para el campo dia_visita
 */
                    /*cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),*/

                    estado,
                    direccion,
                    correlativo_visita,
                    nombre_doctor,

                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),

                    // cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
                    dia_visita



            ));


        }
        return boletaje;
    }



    public ArrayList<Boletaje> select_boletaje_with_visita_estado_positiva() {
        Cursor cursor = this.mostrar_boletaje_join_visita_estado_positivo();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))

            ));
        }
        return boletaje;

    }



    public ArrayList<Boletaje> select_boletaje_with_visita_estado_positiva_for_listview() {
        Cursor cursor = this.mostrar_boletaje_join_visita_estado_positivo();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            /*boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))*/

            String  id_visita= cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA));
            String estado="";
            String direccion="";
            String nombre_doctor="";
            String dia_visita="";
            String correlativo_visita="";
            ArrayList<VisitaMedica> visita_medica=new ArrayList<VisitaMedica>();
            visita_medica=select_visita_medica_dado_id_visita(id_visita);
            for(int i=0; i<visita_medica.size();i++)
            {
                estado=visita_medica.get(i).getEstado();
                direccion= visita_medica.get(i).getDIRECCION();
                nombre_doctor= visita_medica.get(i).getNOMBREMED();
                dia_visita=visita_medica.get(i).getDia_visita();
                correlativo_visita=visita_medica.get(i).getCorrelativo_visita();
            }


            boletaje.add(new Boletaje(
                    id_visita,
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),

/*
 vamos a poner valores de la tabla visita  para el listview
  apm no servira para el campo estado
  obser para el campo direccion
  motivo_obser para el campo correlativo_visita
  ciudad para el campo nombre
  acompanhiado para el campo dia_visita
 */
                    /*cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),*/

                    estado,
                    direccion,
                    correlativo_visita,

                    nombre_doctor,

                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),

                    // cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
                    dia_visita

            ));
        }
        return boletaje;

    }


    public ArrayList<Boletaje> select_boletaje_with_visita_estado_negativa() {
        Cursor cursor = this.mostrar_boletaje_join_visita_estado_negativo();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
            ));
        }
        return boletaje;

    }



    public ArrayList<Boletaje> select_boletaje_with_visita_estado_negativa_for_listview() {
        Cursor cursor = this.mostrar_boletaje_join_visita_estado_negativo();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            /*boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))*/

            String  id_visita= cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA));
            String estado="";
            String direccion="";
            String nombre_doctor="";
            String dia_visita="";
            String correlativo_visita="";
            ArrayList<VisitaMedica> visita_medica=new ArrayList<VisitaMedica>();
            visita_medica=select_visita_medica_dado_id_visita(id_visita);
            for(int i=0; i<visita_medica.size();i++)
            {
                estado=visita_medica.get(i).getEstado();
                direccion= visita_medica.get(i).getDIRECCION();
                nombre_doctor= visita_medica.get(i).getNOMBREMED();
                dia_visita=visita_medica.get(i).getDia_visita();
                correlativo_visita=visita_medica.get(i).getDia_visita();
            }


            boletaje.add(new Boletaje(
                    id_visita,
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),

/*
 vamos a poner valores de la tabla visita  para el listview
  apm no servira para el campo estado
  obser para el campo direccion
  motivo_obser para el campo correlativo_visita
  ciudad para el campo nombre
  acompanhiado para el campo dia_visita
 */
                    /*cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),*/

                    estado,
                    direccion,
                    correlativo_visita,
                    nombre_doctor,

                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),

                    // cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
                    dia_visita

            ));
        }
        return boletaje;

    }


    public Integer select_count_boletaje_pendiente() {
        Integer i=0;
        Cursor cursor = this.mostrar_boletaje_subida_pendiente();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))
            ));

            i=i+1;

        }
        return i;
    }


    public ArrayList<Boletaje> select_boletaje_dado_codbarra(String id_visita) {
        Cursor cursor = this.mostrar_boletaje_dado_cod_barra(id_visita);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje.add(new Boletaje(
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.Boletajes.ACOMPANHIADO))

            ));
        }
        return boletaje;

    }

    public void delete_boletaje_dado_id(String id){
        this.borrar_boletaje_dado_id(id);
    }

    public void delete_boletaje(){
        this.borrar_boletaje();
    }


    public void delete_boletaje_dado_id_visita(String id_visita){
        this.borrar_boletaje_dado_id_visita(id_visita);
    }


    public  void update_estado_boletaje(String estado){
        this.modificar_estado_boletaje(estado);
    }

    public  void update_estado_bole_dado_id_visita(String id_visita,String estado){
        this.modificar_estado_boletaje_dado_id_visita(id_visita, estado);
    }

    public  void update_estado_boletaje_dado_id_visita(String id_visita,String estado){
        this.modificar_estado_boletaje_dato_id_visita(id_visita, estado);
    }


    public  void update_negativa_estado(String id_visita,String estado){
        this.modificar_negativo_editado(id_visita, estado);
    }





    /*
    MUESTRA MEDICA MUESTRA MEDICA MUESTRA MEDICA MUESTRA MEDICA MUESTRA MEDICA MUESTRA MEDICA MUESTRA MEDICA MUESTRA MEDICA
     */
    public  void add_muestra_medica(ArrayList<MM> mm){
        this.insertar_muestra_medica(mm);
    }


    public ArrayList<MM> select_muestra_medica(){
        Cursor cursor = this.mostrar_muetras_medicas();

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            mm.add(new MM(
                    cursor.getString(cursor.getColumnIndex(Table.MMs.CODIGOMM)),
                    cursor.getString(cursor.getColumnIndex(Table.MMs.MM))
            ));
        }
        return mm;
    }

    public ArrayList<MM> select_muestra_medica_dado_nombre_muestra(String nombre_muestra){
        Cursor cursor = this.mostrar_muetras_medicas_dado_nombre_muestra(nombre_muestra);
        System.out.println("controller **********************"+nombre_muestra);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            mm.add(new MM(
                    cursor.getString(cursor.getColumnIndex(Table.MMs.CODIGOMM)),
                    cursor.getString(cursor.getColumnIndex(Table.MMs.MM))
            ));
        }
        return mm;
    }

    public void delete_muestra_medica(){
        this.borrar_muestra_medica();
    }
/*
BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR
 */

    public  void add_boletaje_auxiliar(ArrayList<BoletajeAuxiliar>  boletaje_auxiliar){
        this.insertar_boletaje_auxiliar(boletaje_auxiliar);
    }

    public ArrayList<BoletajeAuxiliar> select_all_boletaje_auxiliar() {
        Cursor cursor = this.mostrar_todo_boletaje_auxiliar();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_auxiliar.add(new BoletajeAuxiliar(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_DIEZ_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_QUINCE_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_ALARMA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_GUARDADO))

            ));
        }
        return boletaje_auxiliar;


    }
    public ArrayList<BoletajeAuxiliar> select_all_boletaje_auxiliar_where_estado_alarma_pendientes() {
        Cursor cursor = this.mostrar_boletaje_auxiliar_donde_estado_alarma_pendiente_order_by_fecha();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_auxiliar.add(new BoletajeAuxiliar(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_DIEZ_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_QUINCE_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_ALARMA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_GUARDADO))

            ));
        }
        return boletaje_auxiliar;


    }



    public ArrayList<BoletajeAuxiliar> select_all_boletaje_auxiliar_where_estado_alarma_and_estado_guardado_equals_pendientes() {
        Cursor cursor = this.mostrar_boletaje_auxiliar_donde_estado_alarma_and_estado_guardado_igual_pendiente_order_by_fecha();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_auxiliar.add(new BoletajeAuxiliar(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_DIEZ_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_QUINCE_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_ALARMA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_GUARDADO))

            ));
        }
        return boletaje_auxiliar;


    }




    public ArrayList<BoletajeAuxiliar> select_all_boletaje_auxiliar_where_estado_guardado_pendientes() {
        Cursor cursor = this.mostrar_boletaje_auxiliar_donde_estado_guardado_pendiente_order_by_fecha();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_auxiliar.add(new BoletajeAuxiliar(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_DIEZ_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_QUINCE_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_ALARMA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_GUARDADO))

            ));
        }
        return boletaje_auxiliar;


    }



    public ArrayList<BoletajeAuxiliar> select_all_boletaje_auxiliar_dado_id_visita(String id_visita) {
        Cursor cursor = this.mostrar_boletaje_auxiliar_dado_id_visita(id_visita);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_auxiliar.add(new BoletajeAuxiliar(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_SUBIDA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.NEGATIVO_EDITADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.RUTA_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_DIEZ_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.FECHA_MAS_QUINCE_MINUTOS)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_ALARMA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeAuxiliars.ESTADO_GUARDADO))

            ));
        }
        return boletaje_auxiliar;


    }


    public  void update_estado_guardado_boletaje_auxiliar_dado_id_visita(String id_visita){
        this.modificar_estado_guardado_boletaje_auxiliar_dado_id_visita(id_visita);
    }

    public  void update_estado_alarma_boletaje_auxiliar_dado_id_visita(String id_visita){
        this.modificar_estado_alarma_boletaje_auxiliar_dado_id_visita(id_visita);
    }

    public void delete_boletaje_auxiliar(){
        this.borrar_boletaje_auxiliar();
    }

/*
**********************************************Boletaje historico
 */

    public  void add_boletaje_historico(ArrayList<BoletajeHistorico>  boletaje){
        this.insertar_boletaje_historico(boletaje);
    }

    public ArrayList<BoletajeHistorico> select_all_boletaje_historico() {
        Cursor cursor = this.mostrar_todo_boletaje_historico();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_historico.add(new BoletajeHistorico(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_HISTORICO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ESTADO_SUBIDA))
            ));
        }
        return boletaje_historico;


    }



    public ArrayList<BoletajeHistorico> select_boletaje_historico_where_estado_pendiete() {
        Cursor cursor=this.mostrar_boletaje_historico_subida_pendiente();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_historico.add(new BoletajeHistorico(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_HISTORICO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ESTADO_SUBIDA))
            ));
        }
        return boletaje_historico;
    }



    public ArrayList<BoletajeHistorico> select_boletaje_historico_where_estado_not_pendiete() {
        Cursor cursor = this.mostrar_boletaje_historico_subida_not_pendiente();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_historico.add(new BoletajeHistorico(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_HISTORICO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ESTADO_SUBIDA))
            ));
        }
        return boletaje_historico;
    }


    public ArrayList<BoletajeHistorico> select_boletaje_historico_where_fecha_like(String fecha){
        Cursor cursor = this.mostrar_boletaje_historico_where_fecha_like(fecha);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_historico.add(new BoletajeHistorico(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_HISTORICO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ESTADO_SUBIDA))
            ));
        }
        return boletaje_historico;
    }

    public ArrayList<BoletajeHistorico> select_boletaje_historico_dado_codbarra(String id_visita) {
        Cursor cursor = this.mostrar_boletaje_historico_dado_cod_barra(id_visita);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            boletaje_historico.add(new BoletajeHistorico(
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_HISTORICO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ID_VISITA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CODBARRA)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.FECHACELULAR)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MESV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ANOV)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.APM)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.MOTIVO_OBSER)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.CIUDAD)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LAT)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.LON)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ACOMPANHIADO)),
                    cursor.getString(cursor.getColumnIndex(Table.BoletajeHistoricos.ESTADO_SUBIDA))
            ));
        }
        return boletaje_historico;

    }

    public void delete_boletaje_historico(){
        this.borrar_boletaje_historico();
    }


    public  void update_estado_boletaje_historico(String estado){
        this.modificar_estado_boletaje_historico(estado);
    }

/*
BANCO MUESTRAS BANCO MUESTRAS BANCO MUESTRAS BANCO MUESTRAS BANCO MUESTRAS BANCO MUESTRAS
 */

public ArrayList<Tblbancomm> select_all_banco_muestras() {
    Cursor cursor = this.mostrar_banco_muestras();

    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
        banco_muestras.add(new Tblbancomm(
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.APM)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_VIS)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.LINEA_VIS)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESPECIALIDAD_MED)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CATEGORIA_MED)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CODMED)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_MED)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.REGIONAL)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SOLICITUD)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_ENTREGADO)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.JUSTIFICACION)),

                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.BANDERA_SEGUIMIENTO)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SEGUIMIENTO)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.OBSERVACIONES_SEGUIMIENTO)),

                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.RUTA_IMAGEN_BANCO_MUESTRAS)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC)),
                        cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC_SEGUIMIENTO))

                )
        );
    }
    return banco_muestras;
}


    public ArrayList<Tblbancomm> select_banco_muestras_realizadas() {
        Cursor cursor = this.mostrar_banco_muestras_realizadas();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            banco_muestras.add(new Tblbancomm(
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.LINEA_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESPECIALIDAD_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CATEGORIA_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CODMED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.REGIONAL)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SOLICITUD)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_ENTREGADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.JUSTIFICACION)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.BANDERA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.OBSERVACIONES_SEGUIMIENTO)),


                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.RUTA_IMAGEN_BANCO_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC)),

                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC_SEGUIMIENTO))
                    )
            );
        }
        return banco_muestras;
    }

    public ArrayList<Tblbancomm> select_banco_muestras_pendientes() {
        Cursor cursor = this.mostrar_banco_muestras_pendiente();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            banco_muestras.add(new Tblbancomm(
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.LINEA_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESPECIALIDAD_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CATEGORIA_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CODMED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.REGIONAL)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SOLICITUD)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_ENTREGADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.JUSTIFICACION)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.BANDERA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.OBSERVACIONES_SEGUIMIENTO)),


                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.RUTA_IMAGEN_BANCO_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC)),

                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC_SEGUIMIENTO))
                    )
            );
        }
        return banco_muestras;
    }


    public ArrayList<Tblbancomm> select_banco_muestras_dado_id(String id_formulario_banco_muestras) {
        Cursor cursor = this.mostrar_banco_dado_id_banco(id_formulario_banco_muestras);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            banco_muestras.add(new Tblbancomm(
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.LINEA_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESPECIALIDAD_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CATEGORIA_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CODMED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.REGIONAL)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SOLICITUD)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_ENTREGADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.JUSTIFICACION)),

                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.BANDERA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.OBSERVACIONES_SEGUIMIENTO)),


                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.RUTA_IMAGEN_BANCO_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC)),

                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC_SEGUIMIENTO))
                    )
            );
        }
        return banco_muestras;
    }




    public ArrayList<Tblbancomm> select_banco_muestras_sin_sincronizar() {
        Cursor cursor = this.mostrar_banco_de_muestras_sin_sincronizar();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            banco_muestras.add(new Tblbancomm(
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.LINEA_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESPECIALIDAD_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CATEGORIA_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CODMED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.REGIONAL)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SOLICITUD)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_ENTREGADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.JUSTIFICACION)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.BANDERA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.OBSERVACIONES_SEGUIMIENTO)),


                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.RUTA_IMAGEN_BANCO_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC)),

                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC_SEGUIMIENTO))
                    )
            );
        }
        return banco_muestras;
    }


    public ArrayList<Tblbancomm> select_banco_muestras_sin_sincronizar_supervisor() {
        Cursor cursor = this.mostrar_banco_de_muestras_sin_sincronizar_supervisor();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            banco_muestras.add(new Tblbancomm(
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.LINEA_VIS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESPECIALIDAD_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CATEGORIA_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.CODMED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.NOMBRE_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.REGIONAL)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SOLICITUD)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_ENTREGADO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.JUSTIFICACION)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.BANDERA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.FECHA_SEGUIMIENTO)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.OBSERVACIONES_SEGUIMIENTO)),


                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.RUTA_IMAGEN_BANCO_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC)),

                            cursor.getString(cursor.getColumnIndex(Table.Tblbancomms.ESTADO_SINC_SEGUIMIENTO))
                    )
            );
        }
        return banco_muestras;
    }

    public Integer contar_banco_muestras_sin_sincronizar_supervisor() {
        Integer i=0;
        Cursor cursor = this.mostrar_banco_de_muestras_sin_sincronizar_supervisor();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {


            i=i+1;
        }
        return i;
    }


    public Integer contar_banco_muestras_sin_sincronizar() {
        Integer i=0;
        Cursor cursor = this.mostrar_banco_de_muestras_sin_sincronizar();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {


            i=i+1;
        }
        return i;
    }

    public void add_banco_muestra(ArrayList<Tblbancomm> banco_muestras) {
        this.insertar_banco_muestras(banco_muestras);
    }


    public void update_banco_muestras_estado_sinc() {
        this.modificar_banco_muestras_estado_sinc();
    }


    public void update_banco_muestras_estado_sinc_supervisor() {
        this.modificar_banco_muestras_estado_sinc_supervisor();
    }


    public void update_banco_muestras_seguimiento(String id_formulario_banco_muestras, String  observaciones_seguimiento, String fecha_seguimiento) {
        this.modificar_banco_muestras_seguimiento(id_formulario_banco_muestras, observaciones_seguimiento, fecha_seguimiento);
    }


    public void update_estado_sincronizacion_seguimiento(String codigo_banco_muestras )
    {
        this.modificar_estado_sincronizacion_seguimiento(codigo_banco_muestras);
    }

    public void update_banco_muestras_guardar(String id_formulario_banco_muestras,String estado,String fecha, String justificacion,String ruta_fotografia) {
        this.modificar_banco_muestras_guardar(id_formulario_banco_muestras, estado, fecha, justificacion, ruta_fotografia);
    }
    public void delete_banco_muestras() {
        this.borrar_banco_muestras();
    }

    /*
    DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRA DETALLE BANCO MUESTRAS
     */

    public ArrayList<DetalleBancoMuestras> select_all_detalle_banco_muestras() {
        Cursor cursor = this.mostrar_all_detalle_banco_muestras();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            detalle_banco_muestras.add(new DetalleBancoMuestras(
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.ID_DETALLE_BANCO_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.CODIGOMM)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.MM)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.CANTIDAD))
                    )
                    );

        }
        return detalle_banco_muestras;
    }

    public ArrayList<DetalleBancoMuestras> select_detalle_banco_muestras_id_detalle_banco_muestras(String id_detalle_banco_muestras) {
        Cursor cursor = this.mostrar_detalle_banco_muestras_dado_id_detalle_banco_muestras(id_detalle_banco_muestras);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            detalle_banco_muestras.add(new DetalleBancoMuestras(
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.ID_DETALLE_BANCO_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.CODIGOMM)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.MM)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.CANTIDAD))
                    )
            );

        }
        return detalle_banco_muestras;
    }


    public ArrayList<DetalleBancoMuestras> select_detalle_banco_muestras_id_formulario_banco_muestras(String id_formulario_banco_muestras) {
        Cursor cursor = this.mostrar_detalle_banco_muestras_dado_id_formulario_banco_muestras(id_formulario_banco_muestras);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            detalle_banco_muestras.add(new DetalleBancoMuestras(
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.ID_DETALLE_BANCO_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.CODIGOMM)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.MM)),
                            cursor.getString(cursor.getColumnIndex(Table.DetalleBancoMuestrass.CANTIDAD))
                    )
            );

        }
        return detalle_banco_muestras;
    }


    public void add_detalle_banco_muestra(ArrayList<DetalleBancoMuestras> detalle_banco_muestras) {
        this.insertar_detalle_banco_muestras(detalle_banco_muestras);
    }



    public void update_detalle_banco_muestras_dado_id(String id_detalle_banco_muestras, String cantidad) {
        this.modificar_detalle_banco_muestras_dado_id(id_detalle_banco_muestras, cantidad);
    }


    public void delete_detalle_banco_muestras() {
        this.borrar_detalle_banco_muestras();
    }

    /*
   MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO MEDICO
     */


    public void add_medico(ArrayList<Medico> medico) {
        this.insertar_medico(medico);
    }

    public ArrayList<Medico> select_all_medico(){
        Cursor cursor = this.mostrar_all_medico();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            medico.add(new Medico(
                            cursor.getString(cursor.getColumnIndex(Table.Medicos.ID_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Medicos.NOMBRE_MED))
                    )
            );
        }
        return medico;
    }


    public ArrayList<Medico> select_medico_id_medico(String id_medico){
        Cursor cursor = this.mostrar_medico_dado_id_medico(id_medico);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            medico.add(new Medico(
                            cursor.getString(cursor.getColumnIndex(Table.Medicos.ID_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Medicos.NOMBRE_MED))
                    )
            );
        }
        return medico;
    }

    public ArrayList<Medico> select_medico_dado_nombre_medico(String nombre_medico){
        Cursor cursor = this.mostrar_medico_dado_nombre_medico(nombre_medico);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            medico.add(new Medico(
                            cursor.getString(cursor.getColumnIndex(Table.Medicos.ID_MED)),
                            cursor.getString(cursor.getColumnIndex(Table.Medicos.NOMBRE_MED))
                    )
            );
        }
        return medico;
    }


    public void update_medico(String id_med, String nombre_med) {
        this.modificar_medico(id_med, nombre_med);
    }

    public void delete_medico() {
        this.borrar_medico();
    }




     /*
    APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM
     */

    public void add_apm(ArrayList<Apm> apm) {
        this.insertar_apm(apm);
    }

    public ArrayList<Apm> select_all_apm(){
        Cursor cursor = this.mostrar_all_apm();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            apm.add(new Apm(
                            cursor.getString(cursor.getColumnIndex(Table.Apms.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Apms.NOMBRE_VIS))
                    )
            );
        }
        return apm;
    }


    public ArrayList<Apm> select_apm_dado_id_apm(String id_apm){
        Cursor cursor = this.mostrar_apm_dado_id_apm(id_apm);


        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            apm.add(new Apm(
                            cursor.getString(cursor.getColumnIndex(Table.Apms.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Apms.NOMBRE_VIS))
                    )
            );
        }
        return apm;
    }
    public ArrayList<Apm> select_apm_dado_nombre_apm(String nombre_vis){
        Cursor cursor = this.mostrar_apm_dado_nombre_vis(nombre_vis);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            apm.add(new Apm(
                            cursor.getString(cursor.getColumnIndex(Table.Apms.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Apms.NOMBRE_VIS))
                    )
            );
        }
        return apm;
    }


    public void update_apm(String id_apm, String nombre_vis) {
        this.modificar_apm(id_apm, nombre_vis);
    }

    public void delete_apm() {
        this.borrar_apm();
    }



    /*
    JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS JUSTIFICADAS
     */

    public void add_justificadas(ArrayList<Justificadas> justificadas) {
        this.insertar_justificadas(justificadas);
    }


    //CREATE TABLE [justificadas] (id_visita VARCHAR PRIMARY KEY,apm VARCHAR,nombre_doctor
    // VARCHAR,MESV VARCHAR,ANOV VARCHAR,fecha_tab VARCHAR,hora_tab VARCHAR,fecha_sinc VARCHAR,
    // estado_justificada VARCHAR,estado_sinc VARCHAR)

    public ArrayList<Justificadas> select_all_justificadas(){
        Cursor cursor = this.mostrar_all_justificadas();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            justificadas.add(new Justificadas(
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ID_VISITA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.NOMBRE_DOCTOR)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MESV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ANOV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.HORA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_JUSTIFICADA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MOTIVO_OBSER))
                    )
            );
        }
        return justificadas;
    }


    public ArrayList<Justificadas> select_justificadas_dado_id_visita(String id_visita){
        Cursor cursor = this.mostrar_justificadas_dado_id_visita(id_visita);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            justificadas.add(new Justificadas(
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ID_VISITA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.NOMBRE_DOCTOR)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MESV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ANOV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.HORA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_JUSTIFICADA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MOTIVO_OBSER))
                    )
            );
        }
        return justificadas;
    }



    public ArrayList<Justificadas> select_justificadas_pendientes(){
        Cursor cursor = this.mostrar_justificadas_pendientes();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            justificadas.add(new Justificadas(
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ID_VISITA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.NOMBRE_DOCTOR)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MESV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ANOV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.HORA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_JUSTIFICADA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MOTIVO_OBSER))
                    )
            );
        }
        return justificadas;
    }



    public ArrayList<Justificadas> select_justificadas_sin_sincronizar(){
        Cursor cursor = this.mostrar_justificadas_sin_sincronizar();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            justificadas.add(new Justificadas(
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ID_VISITA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.NOMBRE_DOCTOR)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MESV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ANOV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.HORA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_JUSTIFICADA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MOTIVO_OBSER))
                    )
            );
        }
        return justificadas;
    }



    public int select_count_justificadas_sin_sincronizar(){
        int contador=0;
        Cursor cursor = this.mostrar_justificadas_sin_sincronizar();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            contador=contador+1;
        }
        return contador;
    }


    public ArrayList<Justificadas> select_justificadas_estado_sincronizacion_pendiente(){
        Cursor cursor = this.mostrar_justificadas_dado_estado_justificadas_lleno_y_sinc_cero();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            justificadas.add(new Justificadas(
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ID_VISITA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.APM)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.NOMBRE_DOCTOR)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MESV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ANOV)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.HORA_TAB)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.FECHA_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_JUSTIFICADA)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.ESTADO_SINC)),
                            cursor.getString(cursor.getColumnIndex(Table.Justificadass.MOTIVO_OBSER))
                    )
            );
        }
        return justificadas;
    }


    public void update_justificadas_fecha_sinc_estado_justificada_dado_id_visita(String id_visita , String fecha_sinc, String estado_justificada) {
        this.modificar_justificadas_fecha_sinc_estado_justificada_dado_id_visita(id_visita , fecha_sinc, estado_justificada);
    }



    public void update_justificadas_estado_sinc_dado_id_visita(String id_visita) {
        this.modificar_justificadas_estado_sinc_dado_id_visita(id_visita);
    }

    public void delete_justificadas() {
        this.borrar_justificadas();
    }




}