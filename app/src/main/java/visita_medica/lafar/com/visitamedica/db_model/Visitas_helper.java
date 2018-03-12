package visita_medica.lafar.com.visitamedica.db_model;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class Visitas_helper extends SQLiteOpenHelper {

	private Context context;
	private File dbFile;
	private String dbName;
	private boolean dbExist;
	private DatabaseCallback listener;

	public Visitas_helper(Context context, String dbName) {
		super(context, dbName, null, 1);
		this.context = context;
		this.dbName = dbName;
	}

	public void CreateDatabase() {
		dbFile = context.getDatabasePath(dbName);
		dbExist = checkDbExists(dbFile.getAbsolutePath());
		if (!dbExist) {
			try {
				File f = new File(dbFile.getAbsolutePath().replace(dbName, ""));
				f.mkdir();
				copyDataBase(dbFile);
				if (listener != null) {
					listener.OnCopied();
				}
			} catch (IOException e) {
				if (listener != null) {
					listener.OnErrorDb();
				}
				e.printStackTrace();
			}
		} else {
			if (listener != null) {
				listener.OnExists();
			}
		}
	}

	private boolean checkDbExists(String path) {
		SQLiteDatabase checkDB = null;
		try {
			checkDB = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (Exception e) {
			return checkDB != null;
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null;
	}

	private void copyDataBase(File pathFile) throws IOException {
		InputStream myInput = context.getAssets().open(dbName);
		OutputStream myOutput = new FileOutputStream(pathFile);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	protected void setDatabaseListener(DatabaseCallback dbListener) {
		this.listener = dbListener;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	protected static class Table {

        public static class Visitas {

            private Visitas() {
            }

            public final static String TABLE_NAME = "visita_medica";
            public final static String ID_VISITA="id_visita";
            public final static String CODBARRA = "Codbarra";
            public final static String NUMVISI = "NUMVISI";
            public final static String CODMED = "CODMED";
            public final static String ESPE1 = "ESPE1";
            public final static String NOMBREMED= "NOMBREMED";
            public final static String DIRECCION= "DIRECCION";
            public final static String HOSPITAL= "HOSPITAL";
            public final static String REGIONAL= "REGIONAL";
            public final static String MESV= "MESV";
            public final static String ANOV= "ANOV";
			public final static String CATE= "cate";
			public final static String CIUDAD= "CIUDAD";
            public final static String ESTADO= "estado";
            public final static String DIA_VISITA= "dia_visita";
            public final static String CORRELATIVO_VISITA= "correlativo_visita";
            public final static String SEMANA_VISITA= "semana_visita";

        }



        public static class DetalleVisitas {

            private DetalleVisitas()
            {
            }
            public final static String TABLE_NAME = "detalle_visita";
            public final static String ID_DETALLE_VISITA = "id_detalle_visita";
            public final static String CODBARRA = "Codbarra";
			public final static String CODMED = "CODMED";
			public final static String CODIGOMM = "CODIGOMM";
			public final static String CANTIDAD = "CANTIDAD";
			public final static String MM = "MM";
			public final static String MES = "mes";
			public final static String ANHIO= "anhio";
        }



        public static class Boletajes {

            private Boletajes()
            {
            }
            public final static String TABLE_NAME = "boletaje";
            public final static String ID_VISITA = "id_visita";
            public final static String CODBARRA = "Codbarra";
            public final static String FECHACELULAR = "FECHACELULAR";
            public final static String MESV = "MESV";
			public final static String ANOV = "ANOV";
            public final static String APM = "APM";
            public final static String OBSER = "OBSER";
            public final static String MOTIVO_OBSER = "MOTIVO_OBSER";
			public final static String CIUDAD = "ciudad";
			public final static String LAT = "lat";
			public final static String LON = "lon";
			public final static String ESTADO_SUBIDA = "estado_subida";
            public final static String NEGATIVO_EDITADO = "negativo_editado";
            public final static String RUTA_IMAGEN="ruta_imagen";
            public final static String ACOMPANHIADO="acompanhiado";

        }


        public static class BoletajeAuxiliars {

            private BoletajeAuxiliars()
            {
            }
            public final static String TABLE_NAME = "boletaje_auxiliar";
            public final static String ID_VISITA = "id_visita";
            public final static String CODBARRA = "Codbarra";
            public final static String FECHACELULAR = "FECHACELULAR";
            public final static String MESV = "MESV";
            public final static String ANOV = "ANOV";
            public final static String APM = "APM";
            public final static String OBSER = "OBSER";
            public final static String MOTIVO_OBSER = "MOTIVO_OBSER";
            public final static String CIUDAD = "ciudad";
            public final static String LAT = "lat";
            public final static String LON = "lon";
            public final static String ESTADO_SUBIDA = "estado_subida";
            public final static String NEGATIVO_EDITADO = "negativo_editado";
            public final static String RUTA_IMAGEN="ruta_imagen";
            public final static String ACOMPANHIADO="acompanhiado";
            public final static String FECHA_MAS_DIEZ_MINUTOS = "fecha_mas_diez_minutos";
            public final static String FECHA_MAS_QUINCE_MINUTOS="fecha_mas_quince_minutos";
            public final static String ESTADO_ALARMA="estado_alarma";
            public final static String ESTADO_GUARDADO="estado_guardado";
        }



        public static class BoletajeHistoricos {

            private BoletajeHistoricos()
            {
            }
            public final static String TABLE_NAME = "boletaje_historico";
            public final static String ID_HISTORICO = "id_historico";
            public final static String ID_VISITA = "id_visita";
            public final static String CODBARRA = "Codbarra";
            public final static String FECHACELULAR = "FECHACELULAR";
            public final static String MESV = "MESV";
            public final static String ANOV = "ANOV";
            public final static String APM = "APM";
            public final static String OBSER = "OBSER";
            public final static String MOTIVO_OBSER = "MOTIVO_OBSER";
            public final static String CIUDAD = "ciudad";
            public final static String LAT = "lat";
            public final static String LON = "lon";
            public final static String ACOMPANHIADO="acompanhiado";
            public final static String ESTADO_SUBIDA = "estado_subida";

        }



        public static class MMs {

            private MMs()
            {
            }
            public final static String TABLE_NAME = "mm";
            public final static String CODIGOMM = "CODIGOMM";
            public final static String MM = "MM";

        }


        public static class Tblbancomms {
            private Tblbancomms(){}
            public final static String TABLE_NAME = "banco_muestras";
            public final static String  ID_FORMULARIO_DE_BANCO_DE_MUESTRAS= "id_formulario_de_banco_de_muestras";
            public final static String  APM= "apm";
            public final static String  NOMBRE_VIS= "nombre_vis";
            public final static String  LINEA_VIS= "linea_vis";
            public final static String  ESPECIALIDAD_MED= "especialidad_med";
            public final static String  CATEGORIA_MED= "categoria_med";
            public final static String  CODMED= "codmed";
            public final static String  NOMBRE_MED= "nombre_med";
            public final static String  REGIONAL= "regional";
            public final static String  FECHA_SOLICITUD= "fecha_solicitud";
            public final static String  ESTADO= "estado";
            public final static String  FECHA_ENTREGADO= "fecha_entregado";
            public final static String  JUSTIFICACION= "justificacion";
            public final static String  BANDERA_SEGUIMIENTO= "bandera_seguimiento";
            public final static String  FECHA_SEGUIMIENTO= "fecha_seguimiento";
            public final static String  OBSERVACIONES_SEGUIMIENTO= "observaciones_seguimiento";
            public final static String  RUTA_IMAGEN_BANCO_MUESTRAS= "ruta_imagen_banco_muestras";
            public final static String  ESTADO_SINC= "estado_sinc";
            public final static String  ESTADO_SINC_SEGUIMIENTO= "estado_sinc_seguimiento";
        }



        public static class DetalleBancoMuestrass {
            private DetalleBancoMuestrass(){}
            public final static String TABLE_NAME = "detalle_banco_muestras";
            public final static String  ID_DETALLE_BANCO_MUESTRAS= "id_detalle_banco_muestras";
            public final static String  ID_FORMULARIO_DE_BANCO_DE_MUESTRAS= "id_formulario_de_banco_de_muestras";
            public final static String  CODIGOMM= "codigomm";
            public final static String  MM= "mm";
            public final static String  CANTIDAD= "cantidad";
        }

        public static class Medicos{

            private Medicos()
            {
            }
            public final static String TABLE_NAME = "medico";
            public final static String ID_MED = "id_med";
            public final static String NOMBRE_MED = "nombre_med";
        }



        public static class Justificadass{

            private Justificadass()
            {
            }
            public final static String TABLE_NAME = "justificadas";

            //CREATE TABLE [justificadas] (id_visita VARCHAR PRIMARY KEY,apm VARCHAR,nombre_doctor
            // VARCHAR,MESV VARCHAR,ANOV VARCHAR,fecha_tab VARCHAR,hora_tab VARCHAR,fecha_sinc VARCHAR,
            // estado_justificada VARCHAR,estado_sinc VARCHAR)

            public final static String  ID_VISITA= "id_visita";
            public final static String  APM= "apm";
            public final static String  NOMBRE_DOCTOR= "nombre_doctor";
            public final static String  MESV= "MESV";
            public final static String  ANOV= "ANOV";
            public final static String  FECHA_TAB= "fecha_tab";
            public final static String  HORA_TAB= "hora_tab";
            public final static String  FECHA_SINC= "fecha_sinc";
            public final static String  ESTADO_JUSTIFICADA= "estado_justificada";
            public final static String  ESTADO_SINC="estado_sinc";
            public final static String  MOTIVO_OBSER="MOTIVO_OBSER";
        }

        public static class Apms{

            private Apms()
            {
            }
            public final static String TABLE_NAME = "apm";
            public final static String APM = "apm";
            public final static String NOMBRE_VIS = "nombre_vis";
        }

    }
	

	
}
