package visita_medica.lafar.com.visitamedica.db_model;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeAuxiliar;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeHistorico;
import visita_medica.lafar.com.visitamedica.objetos.DetalleVisita;
import visita_medica.lafar.com.visitamedica.objetos.Justificadas;
import visita_medica.lafar.com.visitamedica.objetos.MM;
import visita_medica.lafar.com.visitamedica.objetos.Medico;
import visita_medica.lafar.com.visitamedica.objetos.Apm;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;

public class Visitas_client extends Visitas_helper {
private SQLiteDatabase db;	
	protected Visitas_client(Context context, String dbName) {
		super(context, dbName);
		this.CreateDatabase();
	}	
	protected void OpenDb(){
		db = this.getWritableDatabase();
	}	
	
	/*
	Visitas
	 */


	/*protected Cursor mostrar_visitas_medica_pendientes_listado(String dia_hoy, String dia_uno, String dia_dos, String dia_tres,  String dia_cuatro,String dia_cinco, String dia_seis,String dia_siete,String dia_ocho, String dia_nueve){
		OpenDb();
		String[] selectionArgs = new String[]{"0"};
		return db.query(Table.Visitas.TABLE_NAME, null, Table.Visitas.ESTADO + "=? AND " + Table.Visitas.DIA_VISITA+ " IN(" +dia_hoy+","+dia_uno+","+dia_dos+","+dia_tres+","+dia_cuatro+","+dia_cinco+","+dia_seis+","+dia_siete+","+dia_ocho+","+dia_nueve+")", selectionArgs, null, null,    " CAST("+ Table.Visitas.DIA_VISITA+" AS INTEGER)"+" ASC ,  CAST("  +Table.Visitas.CORRELATIVO_VISITA+" AS INTEGER)"+ " ASC");
        //                                                                                                                                                           "+dia_tres+","+dia_cuatro+")"
	}*/

    protected Cursor mostrar_visitas_medica_pendientes_listado(String dia_hoy, String dia_uno, String dia_dos, String dia_tres,  String dia_cuatro){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Visitas.TABLE_NAME, null, Table.Visitas.ESTADO + "=? AND " + Table.Visitas.DIA_VISITA+ " IN(" +dia_hoy+","+dia_uno+","+dia_dos+","+dia_tres+","+dia_cuatro+")", selectionArgs, null, null,    " CAST("+ Table.Visitas.DIA_VISITA+" AS INTEGER)"+" ASC ,  CAST("  +Table.Visitas.CORRELATIVO_VISITA+" AS INTEGER)"+ " ASC");
        //                                                                                                                                                           "+dia_tres+","+dia_cuatro+")"
    }


    protected Cursor mostrar_visitas_medica_pendientes(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Visitas.TABLE_NAME, null, Table.Visitas.ESTADO + " =? ", selectionArgs, null, null,   " CAST("+ Table.Visitas.DIA_VISITA+" AS INTEGER)"+" ASC ,  CAST("  +Table.Visitas.CORRELATIVO_VISITA+" AS INTEGER)"+ " ASC" );
    }



    protected Cursor mostrar_visitas_dado_semana_visita(String semana_visita){
        OpenDb();
        String[] selectionArgs = new String[]{semana_visita};
        return db.query(Table.Visitas.TABLE_NAME, null, Table.Visitas.SEMANA_VISITA + " =? ", selectionArgs, null, null,   " CAST("+ Table.Visitas.DIA_VISITA+" AS INTEGER)"+" ASC ,  CAST("  +Table.Visitas.CORRELATIVO_VISITA+" AS INTEGER)"+ " ASC" );
    }


   /* protected Cursor mostrar_visitas_medicas_where_cate(String cate, String dia_hoy, String dia_uno, String dia_dos,  String dia_tres,  String dia_cuatro,String dia_cinco, String dia_seis,  String dia_siete,String dia_ocho, String dia_nueve){
        OpenDb();
        String[] selectionArgs = new String[]{"0",cate};
        return db.query(Table.Visitas.TABLE_NAME, null, Table.Visitas.ESTADO + " =? AND "+Table.Visitas.CATE + " =? AND "+ Table.Visitas.DIA_VISITA+ " IN(" +dia_hoy+","+dia_uno+","+dia_dos+","+dia_tres+","+dia_cuatro+","+dia_cinco+","+dia_seis+","+dia_siete+","+dia_ocho+","+dia_nueve+")", selectionArgs, null, null,  " CAST("+ Table.Visitas.DIA_VISITA+" AS INTEGER)"+" ASC ,  CAST("  +Table.Visitas.CORRELATIVO_VISITA+" AS INTEGER)"+ " ASC");
    }*/
   protected Cursor mostrar_visitas_medicas_where_cate(String cate, String dia_hoy, String dia_uno, String dia_dos,  String dia_tres,  String dia_cuatro){
       OpenDb();
       String[] selectionArgs = new String[]{"0",cate};
       return db.query(Table.Visitas.TABLE_NAME, null, Table.Visitas.ESTADO + " =? AND "+Table.Visitas.CATE + " =? AND "+ Table.Visitas.DIA_VISITA+ " IN(" +dia_hoy+","+dia_uno+","+dia_dos+","+dia_tres+","+dia_cuatro+")", selectionArgs, null, null,  " CAST("+ Table.Visitas.DIA_VISITA+" AS INTEGER)"+" ASC ,  CAST("  +Table.Visitas.CORRELATIVO_VISITA+" AS INTEGER)"+ " ASC");
   }


    /*protected Cursor mostrar_visitas_medicas_where_espe(String espe, String dia_hoy, String dia_uno, String dia_dos, String dia_tres, String dia_cuatro, String dia_cinco,String dia_seis, String dia_siete,String dia_ocho, String dia_nueve){
        OpenDb();
        String[] selectionArgs = new String[]{"0",espe};
        return db.query(Table.Visitas.TABLE_NAME, null,Table.Visitas.ESTADO +" =? AND "+ Table.Visitas.ESPE1 + " =? AND "+ Table.Visitas.DIA_VISITA+ " IN(" +dia_hoy+","+dia_uno+","+dia_dos+","+dia_tres+","+dia_cuatro+","+dia_cinco+","+dia_seis+","+dia_siete+","+dia_ocho+","+dia_nueve+")" , selectionArgs, null, null,  " CAST("+ Table.Visitas.DIA_VISITA+" AS INTEGER)"+" ASC ,  CAST("  +Table.Visitas.CORRELATIVO_VISITA+" AS INTEGER)"+ " ASC");
    }*/

    protected Cursor mostrar_visitas_medicas_where_espe(String espe, String dia_hoy, String dia_uno, String dia_dos, String dia_tres, String dia_cuatro){
        OpenDb();
        String[] selectionArgs = new String[]{"0",espe};
        return db.query(Table.Visitas.TABLE_NAME, null,Table.Visitas.ESTADO +" =? AND "+ Table.Visitas.ESPE1 + " =? AND "+ Table.Visitas.DIA_VISITA+ " IN(" +dia_hoy+","+dia_uno+","+dia_dos+","+dia_tres+","+dia_cuatro+")" , selectionArgs, null, null,  " CAST("+ Table.Visitas.DIA_VISITA+" AS INTEGER)"+" ASC ,  CAST("  +Table.Visitas.CORRELATIVO_VISITA+" AS INTEGER)"+ " ASC");
    }



    protected Cursor mostrar_all_visitas_medicas(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Visitas.TABLE_NAME, null, Table.Visitas.ID_VISITA + " !=? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_visitas_medica_dado_id_visita(String id_visita){
        OpenDb();
        String[] selectionArgs = new String[]{id_visita};
        return db.query(Table.Visitas.TABLE_NAME, null, Table.Visitas.ID_VISITA + "  =? ", selectionArgs, null, null, null);
    }

	   protected void insertar_visitas(ArrayList<VisitaMedica> visita) {
			OpenDb();
			ContentValues cv = new ContentValues();
           for(int i=0; i<visita.size();i++) {
               cv.put(Table.Visitas.ID_VISITA, visita.get(i).getId_visita());
               cv.put(Table.Visitas.CODBARRA, visita.get(i).getCodbarra());
               cv.put(Table.Visitas.NUMVISI, visita.get(i).getNUMVISI());
               cv.put(Table.Visitas.CODMED, visita.get(i).getCODMED());
               cv.put(Table.Visitas.ESPE1, visita.get(i).getESPE1());
               cv.put(Table.Visitas.NOMBREMED, visita.get(i).getNOMBREMED());
               cv.put(Table.Visitas.DIRECCION, visita.get(i).getDIRECCION());
               cv.put(Table.Visitas.HOSPITAL, visita.get(i).getHOSPITAL());
               cv.put(Table.Visitas.REGIONAL, visita.get(i).getREGIONAL());
               cv.put(Table.Visitas.MESV, visita.get(i).getMESV());
               cv.put(Table.Visitas.ANOV, visita.get(i).getANOV());
               cv.put(Table.Visitas.CATE, visita.get(i).getCate());
               cv.put(Table.Visitas.CIUDAD, visita.get(i).getCIUDAD());
               cv.put(Table.Visitas.ESTADO, visita.get(i).getEstado());
               cv.put(Table.Visitas.DIA_VISITA, visita.get(i).getDia_visita());
               cv.put(Table.Visitas.CORRELATIVO_VISITA, visita.get(i).getCorrelativo_visita());
               cv.put(Table.Visitas.SEMANA_VISITA, visita.get(i).getSemana_visita());
               db.insert(Table.Visitas.TABLE_NAME, null, cv);
           }
		}

    protected boolean borrar_visitas(){
        OpenDb();
        return db.delete(Table.Visitas.TABLE_NAME,Table.Visitas.ID_VISITA+" !=?" , new String[]{"0"})>0 ;
    }

    protected void modificar_estado_visita(String id_visita,String estado) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Visitas.ESTADO,estado);
        db.update(Table.Visitas.TABLE_NAME,cv, Table.Visitas.ID_VISITA+" = ?",new String[] {id_visita});
    }

/*
DETALLE VISITAS DETALLE VISITAS DETALLE VISITAS DETALLE VISITAS DETALLE VISITAS
 */

    protected void insertar_detalle_visita(ArrayList<DetalleVisita> producto) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<producto.size();i++) {
            cv.put(Table.DetalleVisitas.ID_DETALLE_VISITA, producto.get(i).getId_detalle_visita());
            cv.put(Table.DetalleVisitas.CODBARRA, producto.get(i).getCodbarra());
            cv.put(Table.DetalleVisitas.CODMED, producto.get(i).getCODMED());
            cv.put(Table.DetalleVisitas.CODIGOMM, producto.get(i).getCODIGOMM());
            cv.put(Table.DetalleVisitas.CANTIDAD, producto.get(i).getCANTIDAD());
            cv.put(Table.DetalleVisitas.MM, producto.get(i).getMM());
            cv.put(Table.DetalleVisitas.MES, producto.get(i).getMes());
            cv.put(Table.DetalleVisitas.ANHIO, producto.get(i).getAnhio());
            db.insert(Table.DetalleVisitas.TABLE_NAME, null, cv);
        }
    }




    protected Cursor mostrar_todo_detalle_visita(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.DetalleVisitas.TABLE_NAME, null, Table.DetalleVisitas.CODBARRA + "!=?", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_detalle_visita_dado_un_id_visita(String Codbarra,String mes, String anhio){
        OpenDb();
        String[] selectionArgs = new String[]{Codbarra, mes, anhio};
        return db.query(Table.DetalleVisitas.TABLE_NAME, null, Table.DetalleVisitas.CODBARRA+"=?"+" AND "+Table.DetalleVisitas.MES+"=?"+ " AND "+Table.DetalleVisitas.ANHIO+"=? " , selectionArgs, null, null, null);
    }


    protected boolean borrar_detalle_visitas(){
        OpenDb();
        return db.delete(Table.DetalleVisitas.TABLE_NAME,Table.DetalleVisitas.CODBARRA+" !=?" , new String[]{"0"})>0 ;
    }

/*
Boletaje
 */

    protected void insertar_boletaje(ArrayList<Boletaje> boletaje) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<boletaje.size();i++) {
            cv.put(Table.Boletajes.ID_VISITA, boletaje.get(i).getId_visita());
            cv.put(Table.Boletajes.CODBARRA, boletaje.get(i).getCodbarra());
            cv.put(Table.Boletajes.FECHACELULAR, boletaje.get(i).getFECHACELULAR());
            cv.put(Table.Boletajes.MESV,boletaje.get(i).getMESV());
            cv.put(Table.Boletajes.ANOV,boletaje.get(i).getANOV());
            cv.put(Table.Boletajes.APM, boletaje.get(i).getAPM());
            cv.put(Table.Boletajes.OBSER, boletaje.get(i).getOBSER());
            cv.put(Table.Boletajes.MOTIVO_OBSER, boletaje.get(i).getMOTIVO_OBSER());
            cv.put(Table.Boletajes.CIUDAD,boletaje.get(i).getCiudad());
            cv.put(Table.Boletajes.LAT,boletaje.get(i).getLat());
            cv.put(Table.Boletajes.LON,boletaje.get(i).getLon());
            cv.put(Table.Boletajes.ESTADO_SUBIDA,boletaje.get(i).getEstado_subida());
            cv.put(Table.Boletajes.NEGATIVO_EDITADO,boletaje.get(i).getNegativo_editado());
            cv.put(Table.Boletajes.RUTA_IMAGEN,boletaje.get(i).getRuta_imagen());
            cv.put(Table.Boletajes.ACOMPANHIADO,boletaje.get(i).getAcompanhiado());
            db.insert(Table.Boletajes.TABLE_NAME, null, cv);
        }
    }

    protected Cursor mostrar_todo_boletaje(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Boletajes.TABLE_NAME, null, Table.Boletajes.ID_VISITA + "!=?", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_boletaje_subida_pendiente(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Boletajes.TABLE_NAME, null, Table.Boletajes.ESTADO_SUBIDA + "=?", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_boletaje_subida_pendiente_y_estado_igual_positivo(){
        OpenDb();
        String MY_QUERY = "SELECT * FROM "+ Table.Boletajes.TABLE_NAME+ " a JOIN " +Table.Visitas.TABLE_NAME +" b ON "+" a."+Table.Visitas.ID_VISITA+"=b."+Table.Boletajes.ID_VISITA+" "+" WHERE b."+Table.Visitas.ESTADO +"=?"+" AND a."+Table.Boletajes.ESTADO_SUBIDA+"=?"+" AND a."+Table.Boletajes.NEGATIVO_EDITADO+"=?";
        Cursor cursor=db.rawQuery(MY_QUERY, new String[]{"1","0","0"});
        return cursor;
    }


    protected Cursor mostrar_boletaje_subida_pendiente_y_estado_igual_negativo(){
        OpenDb();
        String MY_QUERY = "SELECT * FROM "+ Table.Boletajes.TABLE_NAME+ " a JOIN " +Table.Visitas.TABLE_NAME +" b ON "+" a."+Table.Visitas.ID_VISITA+"=b."+Table.Boletajes.ID_VISITA+" "+" WHERE b."+Table.Visitas.ESTADO +"=?"+" AND a."+Table.Boletajes.ESTADO_SUBIDA+"=?"+" AND a."+Table.Boletajes.NEGATIVO_EDITADO+"=?";
        Cursor cursor=db.rawQuery(MY_QUERY, new String[]{"2","0","0"});
        return cursor;
    }




    protected Cursor mostrar_boletaje_subida_pendiente_y_recuperada(){
        OpenDb();
        String MY_QUERY = "SELECT * FROM "+ Table.Boletajes.TABLE_NAME+ " a JOIN " +Table.Visitas.TABLE_NAME +" b ON "+" a."+Table.Visitas.ID_VISITA+"=b."+Table.Boletajes.ID_VISITA+" "+" WHERE b."+Table.Visitas.ESTADO +"=?"+" AND a."+Table.Boletajes.ESTADO_SUBIDA+"=?"+" AND a."+Table.Boletajes.NEGATIVO_EDITADO+"=?";
        Cursor cursor=db.rawQuery(MY_QUERY, new String[]{"1","0","1"});
        return cursor;
    }


    protected Cursor mostrar_boletaje_subida_not_pendiente(){
        OpenDb();
        String[] selectionArgs = new String[]{"1"};
        return db.query(Table.Boletajes.TABLE_NAME, null, Table.Boletajes.ESTADO_SUBIDA + "=?", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_boletaje_where_fecha_like(String fecha){
        OpenDb();
        String[] selectionArgs = new String[]{"%"+ fecha+ "%" };
        return db.query(Table.Boletajes.TABLE_NAME, null, Table.Boletajes.FECHACELULAR + " like  ? ", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_boletaje_dado_cod_barra(String id_visita){
        OpenDb();
        String[] selectionArgs = new String[]{id_visita};
        return db.query(Table.Boletajes.TABLE_NAME, null, Table.Boletajes.ID_VISITA + " =? ", selectionArgs, null, null, null);
    }

    /*
    Este lo estamos haciendo con rawquwry porque no hay de otra
     */
    protected Cursor mostrar_boletaje_join_visita_estado_positivo(){
        OpenDb();
        String MY_QUERY = "SELECT * FROM "+ Table.Boletajes.TABLE_NAME+ " a JOIN " +Table.Visitas.TABLE_NAME +" b ON "+" a."+Table.Visitas.ID_VISITA+"=b."+Table.Boletajes.ID_VISITA+" "+" WHERE b."+Table.Visitas.ESTADO +"=?";
        Cursor cursor=db.rawQuery(MY_QUERY, new String[]{"1"});
        return cursor;
    }
    /*
    Este lo estamos haciendo con rawquwry porque no hay de otra es por el join
    */

    protected Cursor mostrar_boletaje_join_visita_estado_negativo(){
        OpenDb();
        String MY_QUERY = "SELECT * FROM "+ Table.Boletajes.TABLE_NAME+ " a JOIN " +Table.Visitas.TABLE_NAME +" b ON "+" a."+Table.Visitas.ID_VISITA+"=b."+Table.Boletajes.ID_VISITA+" "+" WHERE b."+Table.Visitas.ESTADO +"=?";
        Cursor cursor=db.rawQuery(MY_QUERY, new String[]{"2"});
        return cursor;
    }


    protected boolean borrar_boletaje_dado_id(String id){
        OpenDb();
        return db.delete(Table.Boletajes.TABLE_NAME,Table.Boletajes.ID_VISITA+" =? " , new String[]{id})>0 ;
    }


    protected boolean borrar_boletaje(){
        OpenDb();
        return db.delete(Table.Boletajes.TABLE_NAME,Table.Boletajes.ID_VISITA+" !=?" , new String[]{"0"})>0 ;
    }



    protected boolean borrar_boletaje_dado_id_visita(String id_visita){
        OpenDb();
        return db.delete(Table.Boletajes.TABLE_NAME,Table.Boletajes.ID_VISITA+" =? " , new String[]{id_visita})>0 ;
    }

    protected void modificar_estado_boletaje(String estado) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Boletajes.ESTADO_SUBIDA,estado);
        db.update(Table.Boletajes.TABLE_NAME, cv, Table.Boletajes.ID_VISITA + " != ?", new String[]{"0"});
    }


    protected void modificar_estado_boletaje_dado_id_visita(String id_visita, String estado) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Boletajes.ESTADO_SUBIDA,estado);
        db.update(Table.Boletajes.TABLE_NAME, cv, Table.Boletajes.ID_VISITA + " =? ", new String[]{id_visita});
    }

    protected void modificar_estado_boletaje_dato_id_visita(String id_visita,String estado) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Boletajes.ESTADO_SUBIDA,estado);
        db.update(Table.Boletajes.TABLE_NAME, cv, Table.Boletajes.ID_VISITA + " = ?", new String[]{id_visita});
    }


    protected void update_boletaje_dado(String id_visita,String estado) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Boletajes.ESTADO_SUBIDA,estado);
        db.update(Table.Boletajes.TABLE_NAME, cv, Table.Boletajes.ID_VISITA + " = ?", new String[]{id_visita});
    }


    protected void modificar_negativo_editado(String id_visita,String estado) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Boletajes.NEGATIVO_EDITADO,estado);
        db.update(Table.Boletajes.TABLE_NAME, cv, Table.Boletajes.ID_VISITA + " = ?", new String[]{id_visita});
    }


    /*
    MUESTRAS MÉDICAS
     */


    protected void insertar_muestra_medica(ArrayList<MM> muestra_medica) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<muestra_medica.size();i++) {
            cv.put(Table.MMs.CODIGOMM, muestra_medica.get(i).getCODIGOMM());
            cv.put(Table.MMs.MM, muestra_medica.get(i).getMM());
            db.insert(Table.MMs.TABLE_NAME, null, cv);
        }
    }


    protected Cursor mostrar_muetras_medicas(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.MMs.TABLE_NAME, null, Table.MMs.CODIGOMM + " !=? ", selectionArgs, null, null, null);
    }



    protected Cursor mostrar_muetras_medicas_dado_nombre_muestra(String nombre_muestra){
        OpenDb();
        String[] selectionArgs = new String[]{nombre_muestra};
        System.out.println("client **********************" + nombre_muestra);
        return db.query(Table.MMs.TABLE_NAME, null, Table.MMs.MM + " =? ", selectionArgs, null, null, null);
    }

    protected boolean borrar_muestra_medica(){
        OpenDb();
        return db.delete(Table.MMs.TABLE_NAME,Table.MMs.CODIGOMM+" !=?" , new String[]{"0"})>0 ;
    }

    /*
    BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR BOLETAJE AUXILIAR
     */

    protected void insertar_boletaje_auxiliar(ArrayList<BoletajeAuxiliar> boletaje_auxiliar) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<boletaje_auxiliar.size();i++) {
            cv.put(Table.BoletajeAuxiliars.ID_VISITA, boletaje_auxiliar.get(i).getId_visita());
            cv.put(Table.BoletajeAuxiliars.CODBARRA, boletaje_auxiliar.get(i).getCodbarra());
            cv.put(Table.BoletajeAuxiliars.FECHACELULAR, boletaje_auxiliar.get(i).getFECHACELULAR());
            cv.put(Table.BoletajeAuxiliars.MESV,boletaje_auxiliar.get(i).getMESV());
            cv.put(Table.BoletajeAuxiliars.ANOV,boletaje_auxiliar.get(i).getANOV());
            cv.put(Table.BoletajeAuxiliars.APM, boletaje_auxiliar.get(i).getAPM());
            cv.put(Table.BoletajeAuxiliars.OBSER, boletaje_auxiliar.get(i).getOBSER());
            cv.put(Table.Boletajes.MOTIVO_OBSER, boletaje_auxiliar.get(i).getMOTIVO_OBSER());
            cv.put(Table.BoletajeAuxiliars.CIUDAD,boletaje_auxiliar.get(i).getCiudad());
            cv.put(Table.BoletajeAuxiliars.LAT,boletaje_auxiliar.get(i).getLat());
            cv.put(Table.BoletajeAuxiliars.LON,boletaje_auxiliar.get(i).getLon());
            cv.put(Table.BoletajeAuxiliars.ESTADO_SUBIDA,boletaje_auxiliar.get(i).getEstado_subida());
            cv.put(Table.BoletajeAuxiliars.NEGATIVO_EDITADO,boletaje_auxiliar.get(i).getNegativo_editado());
            cv.put(Table.BoletajeAuxiliars.RUTA_IMAGEN,boletaje_auxiliar.get(i).getRuta_imagen());
            cv.put(Table.BoletajeAuxiliars.ACOMPANHIADO,boletaje_auxiliar.get(i).getAcompanhiado());
            cv.put(Table.BoletajeAuxiliars.FECHA_MAS_DIEZ_MINUTOS,boletaje_auxiliar.get(i).getFecha_mas_diez_minutos());
            cv.put(Table.BoletajeAuxiliars.FECHA_MAS_QUINCE_MINUTOS,boletaje_auxiliar.get(i).getFecha_mas_quince_minutos());
            cv.put(Table.BoletajeAuxiliars.ESTADO_ALARMA,boletaje_auxiliar.get(i).getEstado_alarma());
            cv.put(Table.BoletajeAuxiliars.ESTADO_GUARDADO,boletaje_auxiliar.get(i).getEstado_guardado());
            db.insert(Table.BoletajeAuxiliars.TABLE_NAME, null, cv);
        }
    }


    protected Cursor mostrar_todo_boletaje_auxiliar(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.BoletajeAuxiliars.TABLE_NAME, null, Table.BoletajeAuxiliars.ID_VISITA + "!=?", selectionArgs, null, null, null);
    }



    protected Cursor mostrar_boletaje_auxiliar_donde_estado_guardado_pendiente_order_by_fecha(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.BoletajeAuxiliars.TABLE_NAME, null, Table.BoletajeAuxiliars.ESTADO_GUARDADO + " =? ", selectionArgs, null, null,  " CAST("+ Table.BoletajeAuxiliars.FECHACELULAR+" AS DATE)"+" ASC ");
    }

    protected Cursor mostrar_boletaje_auxiliar_donde_estado_alarma_pendiente_order_by_fecha(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.BoletajeAuxiliars.TABLE_NAME, null, Table.BoletajeAuxiliars.ESTADO_ALARMA + " =? ", selectionArgs, null, null,  " CAST("+ Table.BoletajeAuxiliars.FECHACELULAR+" AS DATE)"+" ASC ");
    }


    protected Cursor mostrar_boletaje_auxiliar_donde_estado_alarma_and_estado_guardado_igual_pendiente_order_by_fecha(){
        OpenDb();
        String[] selectionArgs = new String[]{"0","0"};
        return db.query(Table.BoletajeAuxiliars.TABLE_NAME, null, Table.BoletajeAuxiliars.ESTADO_ALARMA + " =? AND "+ Table.BoletajeAuxiliars.ESTADO_GUARDADO +" =? "  , selectionArgs, null, null,  " CAST("+ Table.BoletajeAuxiliars.FECHACELULAR+" AS DATE)"+" ASC ");
    }


    protected Cursor mostrar_boletaje_auxiliar_dado_id_visita(String id_visita){
        OpenDb();
        String[] selectionArgs = new String[]{id_visita};
        return db.query(Table.BoletajeAuxiliars.TABLE_NAME, null, Table.BoletajeAuxiliars.ID_VISITA + "=?", selectionArgs, null, null, null);
    }






    protected void modificar_estado_guardado_boletaje_auxiliar_dado_id_visita(String id_visita) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.BoletajeAuxiliars.ESTADO_GUARDADO,"1");
        db.update(Table.BoletajeAuxiliars.TABLE_NAME, cv, Table.BoletajeAuxiliars.ID_VISITA + " = ?", new String[]{id_visita});
    }

    protected void modificar_estado_alarma_boletaje_auxiliar_dado_id_visita(String id_visita) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.BoletajeAuxiliars.ESTADO_ALARMA,"1");
        db.update(Table.BoletajeAuxiliars.TABLE_NAME, cv, Table.BoletajeAuxiliars.ID_VISITA + " = ?", new String[]{id_visita});
    }

    protected boolean borrar_boletaje_auxiliar(){
        OpenDb();
        return db.delete(Table.BoletajeAuxiliars.TABLE_NAME,Table.BoletajeAuxiliars.ID_VISITA+" !=?" , new String[]{"0"})>0 ;
    }



    /*
    ***************************************
    * BOLETAJE HISTORICO BOLETAJE HISTORICO BOLETAJE HISTORICO BOLETAJE HISTORICO BOLETAJE HISTORICO BOLETAJE HISTORICO BOLETAJE HISTORICO BOLETAJE HISTORICO
     */

    protected void insertar_boletaje_historico(ArrayList<BoletajeHistorico> boletaje_historico) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<boletaje_historico.size();i++) {
            cv.put(Table.BoletajeHistoricos.ID_HISTORICO, boletaje_historico.get(i).getId_historico());
            cv.put(Table.BoletajeHistoricos.ID_VISITA, boletaje_historico.get(i).getId_visita());
            cv.put(Table.BoletajeHistoricos.CODBARRA, boletaje_historico.get(i).getCodbarra());
            cv.put(Table.BoletajeHistoricos.FECHACELULAR, boletaje_historico.get(i).getFECHACELULAR());
            cv.put(Table.BoletajeHistoricos.MESV,boletaje_historico.get(i).getMESV());
            cv.put(Table.BoletajeHistoricos.ANOV,boletaje_historico.get(i).getANOV());
            cv.put(Table.BoletajeHistoricos.APM, boletaje_historico.get(i).getAPM());
            cv.put(Table.BoletajeHistoricos.OBSER, boletaje_historico.get(i).getOBSER());
            cv.put(Table.Boletajes.MOTIVO_OBSER, boletaje_historico.get(i).getMOTIVO_OBSER());
            cv.put(Table.BoletajeHistoricos.CIUDAD,boletaje_historico.get(i).getCiudad());
            cv.put(Table.BoletajeHistoricos.LAT,boletaje_historico.get(i).getLat());
            cv.put(Table.BoletajeHistoricos.LON,boletaje_historico.get(i).getLon());
            cv.put(Table.BoletajeHistoricos.ACOMPANHIADO,boletaje_historico.get(i).getAcompanhiado());
            cv.put(Table.BoletajeHistoricos.ESTADO_SUBIDA,boletaje_historico.get(i).getEstado_subida());
            db.insert(Table.BoletajeHistoricos.TABLE_NAME, null, cv);
        }
    }

    protected Cursor mostrar_todo_boletaje_historico(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.BoletajeHistoricos.TABLE_NAME, null, Table.BoletajeHistoricos.ID_HISTORICO + "!=?", selectionArgs, null, null, null);
    }



    protected Cursor mostrar_boletaje_historico_subida_pendiente(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.BoletajeHistoricos.TABLE_NAME, null, Table.BoletajeHistoricos.ESTADO_SUBIDA + "=?", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_boletaje_historico_subida_not_pendiente(){
        OpenDb();
        String[] selectionArgs = new String[]{"1"};
        return db.query(Table.BoletajeHistoricos.TABLE_NAME, null, Table.BoletajeHistoricos.ESTADO_SUBIDA + "=?", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_boletaje_historico_where_fecha_like(String fecha){
        OpenDb();
        String[] selectionArgs = new String[]{"%"+ fecha+ "%" };
        return db.query(Table.BoletajeHistoricos.TABLE_NAME, null, Table.BoletajeHistoricos.FECHACELULAR + " like  ? ", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_boletaje_historico_dado_cod_barra(String id_visita){
        OpenDb();
        String[] selectionArgs = new String[]{id_visita};
        return db.query(Table.BoletajeHistoricos.TABLE_NAME, null, Table.BoletajeHistoricos.ID_VISITA + " =? ", selectionArgs, null, null, null);
    }



    protected boolean borrar_boletaje_historico(){
        OpenDb();
        return db.delete(Table.BoletajeHistoricos.TABLE_NAME,Table.BoletajeHistoricos.ID_HISTORICO+" !=?" , new String[]{"0"})>0 ;
    }

    protected void modificar_estado_boletaje_historico(String estado) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.BoletajeHistoricos.ESTADO_SUBIDA,estado);
        db.update(Table.BoletajeHistoricos.TABLE_NAME, cv, Table.Boletajes.ID_VISITA + " != ?", new String[]{"0"});
    }
/*
BANCO DE MUESTRAS BANCO DE MUESTRAS BANCO DE MUESTRAS BANCO DE MUESTRAS BANCO DE MUESTRAS BENCO DE MUESTRAS BANCO DE MUESTRAS BANCO DE MUESTRAS BANCO DE MUESTRAS
 */


    protected Cursor mostrar_banco_muestras() {
        OpenDb();
        String[] selectionArgs = new String[] {""};
        return db.query(Table.Tblbancomms.TABLE_NAME, null, Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS + " !=? ", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_banco_muestras_pendiente() {
        OpenDb();
        String[] selectionArgs = new String[] {"0"};
        return db.query(Table.Tblbancomms.TABLE_NAME, null, Table.Tblbancomms.ESTADO + " =? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_banco_muestras_realizadas() {
        OpenDb();
        String[] selectionArgs = new String[] {"1"};
        return db.query(Table.Tblbancomms.TABLE_NAME, null, Table.Tblbancomms.ESTADO_SINC + " =? ", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_banco_dado_id_banco(String id_formulario_banco_muestras) {
        OpenDb();
        String[] selectionArgs = new String[] { id_formulario_banco_muestras };
        return db.query(Table.Tblbancomms.TABLE_NAME, null, Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS + " =? ",
                selectionArgs, null, null, null, null);
    }



    protected Cursor mostrar_banco_de_muestras_sin_sincronizar() {
        OpenDb();
        String[] selectionArgs = new String[] { "0", "0" };
        return db.query(Table.Tblbancomms.TABLE_NAME, null, Table.Tblbancomms.ESTADO_SINC + " =? AND "+ Table.Tblbancomms.ESTADO +" !=? ",selectionArgs, null, null, null, null);
    }




    protected Cursor mostrar_banco_de_muestras_sin_sincronizar_supervisor() {
        OpenDb();
        String[] selectionArgs = new String[] { "0" };
        return db.query(Table.Tblbancomms.TABLE_NAME, null, Table.Tblbancomms.ESTADO_SINC + " =? ",selectionArgs, null, null, null, null);
    }


    protected void insertar_banco_muestras(ArrayList<Tblbancomm> banco_muestras) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<banco_muestras.size();i++) {
            cv.put(Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS, banco_muestras.get(i).getId_formulario_de_banco_de_muestras());
            cv.put(Table.Tblbancomms.APM, banco_muestras.get(i).getApm());
            cv.put(Table.Tblbancomms.NOMBRE_VIS, banco_muestras.get(i).getNombre_vis());
            cv.put(Table.Tblbancomms.LINEA_VIS, banco_muestras.get(i).getLinea_vis());
            cv.put(Table.Tblbancomms.ESPECIALIDAD_MED, banco_muestras.get(i).getEspecialidad_med());
            cv.put(Table.Tblbancomms.CATEGORIA_MED, banco_muestras.get(i).getCategoria_med());
            cv.put(Table.Tblbancomms.CODMED, banco_muestras.get(i).getCodmed());
            cv.put(Table.Tblbancomms.NOMBRE_MED, banco_muestras.get(i).getNombre_med());
            cv.put(Table.Tblbancomms.REGIONAL, banco_muestras.get(i).getRegional());
            cv.put(Table.Tblbancomms.FECHA_SOLICITUD, banco_muestras.get(i).getFecha_solicitud());
            cv.put(Table.Tblbancomms.ESTADO, banco_muestras.get(i).getEstado());
            cv.put(Table.Tblbancomms.FECHA_ENTREGADO, banco_muestras.get(i).getFecha_entregado());
            cv.put(Table.Tblbancomms.JUSTIFICACION, banco_muestras.get(i).getJustificacion());

            cv.put(Table.Tblbancomms.BANDERA_SEGUIMIENTO, banco_muestras.get(i).getBandera_seguimiento());
            cv.put(Table.Tblbancomms.FECHA_SEGUIMIENTO, banco_muestras.get(i).getFecha_seguimiento());
            cv.put(Table.Tblbancomms.OBSERVACIONES_SEGUIMIENTO, banco_muestras.get(i).getObservaciones_seguimiento());

            cv.put(Table.Tblbancomms.RUTA_IMAGEN_BANCO_MUESTRAS, banco_muestras.get(i).getRuta_imagen_banco_muestras());
            cv.put(Table.Tblbancomms.ESTADO_SINC, banco_muestras.get(i).getEstado_sinc());

            cv.put(Table.Tblbancomms.ESTADO_SINC_SEGUIMIENTO, banco_muestras.get(i).getEstado_sinc_seguimiento());

            db.insert(Table.Tblbancomms.TABLE_NAME, null, cv);
        }
    }



    protected void modificar_banco_muestras_estado_sinc() {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Tblbancomms.ESTADO_SINC, "1");
        db.update(Table.Tblbancomms.TABLE_NAME, cv, Table.Tblbancomms.ESTADO_SINC + " =? AND "+Table.Tblbancomms.ESTADO+" !=? ", new String[]{"0","0"});
    }



    protected void modificar_banco_muestras_estado_sinc_supervisor() {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Tblbancomms.ESTADO_SINC, "1");
        db.update(Table.Tblbancomms.TABLE_NAME, cv, Table.Tblbancomms.ESTADO_SINC + " =? ", new String[]{"0"});
    }


    protected void modificar_banco_muestras_guardar(String id_formulario_banco_muestras,String estado,String fecha,String justificacion,String ruta_fotografia) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Tblbancomms.ESTADO, estado);
        cv.put(Table.Tblbancomms.FECHA_ENTREGADO,fecha);
        cv.put(Table.Tblbancomms.JUSTIFICACION, justificacion);
        cv.put(Table.Tblbancomms.RUTA_IMAGEN_BANCO_MUESTRAS,ruta_fotografia);
        db.update(Table.Tblbancomms.TABLE_NAME, cv, Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS + " = ?", new String[]{id_formulario_banco_muestras});
    }



    protected void modificar_banco_muestras_seguimiento(String id_formulario_banco_muestras, String observaciones_seguimiento, String fecha_seguimiento) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Tblbancomms.BANDERA_SEGUIMIENTO,"1");
        cv.put(Table.Tblbancomms.OBSERVACIONES_SEGUIMIENTO, observaciones_seguimiento);
        cv.put(Table.Tblbancomms.FECHA_SEGUIMIENTO, fecha_seguimiento);
        db.update(Table.Tblbancomms.TABLE_NAME, cv, Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS + " =? ", new String[]{id_formulario_banco_muestras});
    }


    public void modificar_estado_sincronizacion_seguimiento(String codigo_banco_muestras)
    {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Tblbancomms.ESTADO_SINC_SEGUIMIENTO,"1");
        db.update(Table.Tblbancomms.TABLE_NAME, cv, Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS + " =? ", new String[]{codigo_banco_muestras});
    }


    protected boolean borrar_banco_muestras() {
        OpenDb();
        return db.delete(Table.Tblbancomms.TABLE_NAME, Table.Tblbancomms.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS + " !=?", new String[] { "" }) > 0;
    }

    /*
    DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS DETALLE BANCO MUESTRAS
     */




    protected Cursor mostrar_all_detalle_banco_muestras() {
        OpenDb();
        String[] selectionArgs = new String[] {""};
        return db.query(Table.DetalleBancoMuestrass.TABLE_NAME, null, Table.DetalleBancoMuestrass.ID_DETALLE_BANCO_MUESTRAS + " !=? ", selectionArgs, null, null, null);
    }

    protected Cursor mostrar_detalle_banco_muestras_dado_id_detalle_banco_muestras(String id_detalle_banco_muestras) {
        OpenDb();
        String[] selectionArgs = new String[] {id_detalle_banco_muestras};
        return db.query(Table.DetalleBancoMuestrass.TABLE_NAME, null, Table.DetalleBancoMuestrass.ID_DETALLE_BANCO_MUESTRAS + " =? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_detalle_banco_muestras_dado_id_formulario_banco_muestras(String id_formulario_banco_muestras) {
        OpenDb();
        String[] selectionArgs = new String[] { id_formulario_banco_muestras };
        return db.query(Table.DetalleBancoMuestrass.TABLE_NAME, null, Table.DetalleBancoMuestrass.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS + " =? ",
                selectionArgs, null, null, null, null);
    }


    protected void insertar_detalle_banco_muestras(ArrayList<DetalleBancoMuestras> detalle_banco_muestras) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<detalle_banco_muestras.size();i++) {
            cv.put(Table.DetalleBancoMuestrass.ID_DETALLE_BANCO_MUESTRAS, detalle_banco_muestras.get(i).getId_detalle_banco_muestras());
            cv.put(Table.DetalleBancoMuestrass.ID_FORMULARIO_DE_BANCO_DE_MUESTRAS, detalle_banco_muestras.get(i).getId_formulario_de_banco_de_muestras());
            cv.put(Table.DetalleBancoMuestrass.CODIGOMM, detalle_banco_muestras.get(i).getCodigo_mm());
            cv.put(Table.DetalleBancoMuestrass.MM, detalle_banco_muestras.get(i).getMm());
            cv.put(Table.DetalleBancoMuestrass.CANTIDAD, detalle_banco_muestras.get(i).getCantidad());
            db.insert(Table.DetalleBancoMuestrass.TABLE_NAME, null, cv);
        }
    }



    protected void modificar_detalle_banco_muestras_dado_id(String id_detalle_banco_muestras, String cantidad) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.DetalleBancoMuestrass.CANTIDAD, cantidad);
        db.update(Table.DetalleBancoMuestrass.TABLE_NAME, cv, Table.DetalleBancoMuestrass.ID_DETALLE_BANCO_MUESTRAS + " !=? ",new String[] {id_detalle_banco_muestras});
    }





    protected boolean borrar_detalle_banco_muestras() {
        OpenDb();
        return db.delete(Table.DetalleBancoMuestrass.TABLE_NAME, Table.DetalleBancoMuestrass.ID_DETALLE_BANCO_MUESTRAS + " !=?", new String[] { "" }) > 0;
    }


/*
TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO TABLA MEDICO
 */

    protected void insertar_medico(ArrayList<Medico> medico) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<medico.size();i++) {
            cv.put(Table.Medicos.ID_MED, medico.get(i).getId_med());
            cv.put(Table.Medicos.NOMBRE_MED, medico.get(i).getNombre_med());
            db.insert(Table.Medicos.TABLE_NAME, null, cv);
        }
    }


    protected Cursor mostrar_all_medico(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Medicos.TABLE_NAME, null, Table.Medicos.ID_MED + " !=? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_medico_dado_id_medico(String id_med){
        OpenDb();
        String[] selectionArgs = new String[]{id_med};
        return db.query(Table.Medicos.TABLE_NAME, null, Table.Medicos.ID_MED + " =? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_medico_dado_nombre_medico(String nombre_medico){
        OpenDb();
        String[] selectionArgs = new String[]{nombre_medico};
        return db.query(Table.Medicos.TABLE_NAME, null, Table.Medicos.NOMBRE_MED + " =? ", selectionArgs, null, null, null);
    }


    protected void modificar_medico(String id_med, String nombre_medico) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Medicos.NOMBRE_MED, nombre_medico);
        db.update(Table.Medicos.TABLE_NAME, cv, Table.Medicos.ID_MED + " !=? ",new String[] {id_med});
    }

    protected boolean borrar_medico(){
        OpenDb();
        return db.delete(Table.Medicos.TABLE_NAME,Table.Medicos.ID_MED+" !=?" , new String[]{"0"})>0 ;
    }


  /*
    APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM APM
     */


    protected void insertar_apm(ArrayList<Apm> apm) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<apm.size();i++) {
            cv.put(Table.Apms.APM, apm.get(i).getApm());
            cv.put(Table.Apms.NOMBRE_VIS, apm.get(i).getNombre_vis());
            db.insert(Table.Apms.TABLE_NAME, null, cv);
        }
    }


    protected Cursor mostrar_all_apm(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Apms.TABLE_NAME, null, Table.Apms.APM + " !=? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_apm_dado_id_apm(String id_med){
        OpenDb();
        String[] selectionArgs = new String[]{id_med};
        return db.query(Table.Apms.TABLE_NAME, null, Table.Apms.APM + " =? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_apm_dado_nombre_vis(String nombre_vis){
        OpenDb();
        String[] selectionArgs = new String[]{nombre_vis};
        return db.query(Table.Apms.TABLE_NAME, null, Table.Apms.NOMBRE_VIS + " =? ", selectionArgs, null, null, null);
    }


    protected void modificar_apm(String id_med, String nombre_vis) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Apms.NOMBRE_VIS, nombre_vis);
        db.update(Table.Apms.TABLE_NAME, cv, Table.Apms.APM + " !=? ",new String[] {id_med});
    }

    protected boolean borrar_apm(){
        OpenDb();
        return db.delete(Table.Apms.TABLE_NAME,Table.Apms.APM+" !=?" , new String[]{"0"})>0 ;
    }



    /*
TABLA JUSTIFICADAS TABLA JUSTIFICADAS TABLA JUSTIFICADAS TABLA JUSTIFICADAS TABLA JUSTIFICADAS TABLA JUSTIFICADAS TABLA JUSTIFICADAS TABLA JUSTIFICADAS
 */


    //CREATE TABLE [justificadas] (id_visita VARCHAR PRIMARY KEY,apm VARCHAR,nombre_doctor
    // VARCHAR,MESV VARCHAR,ANOV VARCHAR,fecha_tab VARCHAR,hora_tab VARCHAR,fecha_sinc VARCHAR,
    // estado_justificada VARCHAR,estado_sinc VARCHAR)

    protected void insertar_justificadas(ArrayList<Justificadas> justificadas) {
        OpenDb();
        ContentValues cv = new ContentValues();
        for(int i=0; i<justificadas.size();i++) {
            cv.put(Table.Justificadass.ID_VISITA, justificadas.get(i).getId_visita());
            cv.put(Table.Justificadass.APM, justificadas.get(i).getApm());
            cv.put(Table.Justificadass.NOMBRE_DOCTOR, justificadas.get(i).getNombre_doctor());
            cv.put(Table.Justificadass.MESV, justificadas.get(i).getMESV());
            cv.put(Table.Justificadass.ANOV, justificadas.get(i).getANOV());
            cv.put(Table.Justificadass.FECHA_TAB, justificadas.get(i).getFecha_tab());
            cv.put(Table.Justificadass.HORA_TAB, justificadas.get(i).getHora_tab());
            cv.put(Table.Justificadass.FECHA_SINC, justificadas.get(i).getFecha_sinc());
            cv.put(Table.Justificadass.ESTADO_JUSTIFICADA, justificadas.get(i).getEstado_justificada());
            cv.put(Table.Justificadass.ESTADO_SINC, justificadas.get(i).getEstado_sinc());
            cv.put(Table.Justificadass.MOTIVO_OBSER, justificadas.get(i).getMOTIVO_OBSER());
            db.insert(Table.Justificadass.TABLE_NAME, null, cv);
        }
    }


    protected Cursor mostrar_all_justificadas(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Justificadass.TABLE_NAME, null, Table.Justificadass.ID_VISITA + " !=? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_justificadas_dado_id_visita(String id_visita){
        OpenDb();
        String[] selectionArgs = new String[]{id_visita};
        return db.query(Table.Justificadass.TABLE_NAME, null, Table.Justificadass.ID_VISITA + " =? ", selectionArgs, null, null, null);
    }


    protected Cursor mostrar_justificadas_pendientes(){
        OpenDb();
        String[] selectionArgs = new String[]{"0"};
        return db.query(Table.Justificadass.TABLE_NAME, null, Table.Justificadass.ESTADO_JUSTIFICADA + " =? ", selectionArgs, null, null, null);
    }



    protected Cursor mostrar_justificadas_sin_sincronizar(){
        OpenDb();
        String[] selectionArgs = new String[]{"0","0"};
        return db.query(Table.Justificadass.TABLE_NAME, null, Table.Justificadass.ESTADO_JUSTIFICADA + " !=? AND "+ Table.Justificadass.ESTADO_SINC+" =? ", selectionArgs, null, null, null);
    }



    protected Cursor mostrar_justificadas_dado_estado_justificadas_lleno_y_sinc_cero(){
        OpenDb();
        String[] selectionArgs = new String[]{"0","0"};
        return db.query(Table.Justificadass.TABLE_NAME, null, Table.Justificadass.ESTADO_JUSTIFICADA + " !=? AND "+ Table.Justificadass.ESTADO_SINC+ " =? ", selectionArgs, null, null, null);
    }



    protected void modificar_justificadas_fecha_sinc_estado_justificada_dado_id_visita(String id_visita , String fecha_sinc, String estado_justificada) {

        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Justificadass.FECHA_SINC, fecha_sinc);
        cv.put(Table.Justificadass.ESTADO_JUSTIFICADA, estado_justificada);
        db.update(Table.Justificadass.TABLE_NAME, cv, Table.Justificadass.ID_VISITA + " =? ",new String[] {id_visita});
    }


    protected void modificar_justificadas_estado_sinc_dado_id_visita(String id_visita) {
        OpenDb();
        ContentValues cv = new ContentValues();
        cv.put(Table.Justificadass.ESTADO_SINC, "1");
        db.update(Table.Justificadass.TABLE_NAME, cv, Table.Justificadass.ID_VISITA + " =? ",new String[] {id_visita});
    }





    protected boolean borrar_justificadas(){
        OpenDb();
        return db.delete(Table.Justificadass.TABLE_NAME,Table.Justificadass.ID_VISITA+" !=?" , new String[]{"0"})>0 ;
    }




}