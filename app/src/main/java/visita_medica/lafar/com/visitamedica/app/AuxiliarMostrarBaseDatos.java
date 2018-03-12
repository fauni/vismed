package visita_medica.lafar.com.visitamedica.app;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;


public class AuxiliarMostrarBaseDatos extends Activity{
	
	 private int rows=0;
	 TableLayout table_layout,table_layout2;
	 Button mostrar_datos;
	 EditText que_mostrar;



	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_bd);  
        
        
        
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);       
    	mostrar_datos= (Button)findViewById(R.id.btn_mostrar_datos);
    	mostrar_datos.setOnClickListener(new View.OnClickListener() {
    	    public void onClick(View arg0)
    	    {        
    	    	que_mostrar=(EditText)findViewById(R.id.txt_recibo_bd);
    	    	String muestra=que_mostrar.getText().toString();

				if(muestra.equals("visita")) {
					construir_visita();
				}

				else if(muestra.equals("detalle"))
				{
                construir_detalle_visita();
				}
                else if(muestra.equals("boletaje"))
                {
 construir_boletaje();;
                }
				else if(muestra.equals("tblmm"))
				{
construir_tblmm();
				}

                else if(muestra.equals("producto"))
                {
                    construir_muestra_medica();
                }

                else if(muestra.equals("historico"))
                {
                    construir_boletaje_historico();
                }
				else if(muestra.equals("banco"))
				{
					construir_banco_muestras();
				}

				else if(muestra.equals("auxiliar"))
				{
					construir_boletaje_auxiliar();
				}

				else if(muestra.equals("justificadas"))
				{
					construir_justificadas();
				}

				else
				{

				}

    	    }});        
}
	
	 private void  construir_visita() {

		 String[] user=new String[]{"0"};
		 
		   Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
	        SQLiteDatabase bd = admin.getWritableDatabase();	       
	  	Cursor fila = bd.rawQuery("SELECT id_visita, estado, semana_visita from visita_medica where Codbarra !=?", user);
	        if (fila.moveToFirst()) 
	        {
	        	do
	        	{	        		     	
	          	        	
	           TableRow row = new TableRow(this);
	           row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	           LayoutParams.WRAP_CONTENT));	           	          
	           for (int j = 0; j < 3; j++) {
	           TextView tv = new TextView(this);
			    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
			    LayoutParams.WRAP_CONTENT));

			    GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
		        tv.setBackgroundDrawable(roundRectangle);
			    
			    
			    tv.setGravity(Gravity.CENTER);
			    tv.setTextSize(5);

			    tv.setPadding(0, 5, 0, 5);
			    tv.setText(""+fila.getString(j));
			    row.addView(tv);
	           }
			    table_layout.addView(row);			    	          
	           }while(fila.moveToNext());
	        }
		 }







    private void  construir_detalle_visita() {

        String[] user=new String[]{"0"};

        Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * from detalle_visita where  Codbarra !=?", user);
        if (fila.moveToFirst())
        {
            do
            {

                TableRow row = new TableRow(this);
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 8; j++) {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));

                    GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
                    tv.setBackgroundDrawable(roundRectangle);


                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(5);

                    tv.setPadding(0, 5, 0, 5);
                    tv.setText(""+fila.getString(j));
                    row.addView(tv);
                }
                table_layout.addView(row);
            }while(fila.moveToNext());
        }
    }


	private void  construir_tblmm() {

		String[] user=new String[]{"0"};

		Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila = bd.rawQuery("SELECT * from tblasignacionmm where  Codbarra !=?", user);
		if (fila.moveToFirst())
		{
			do
			{

				TableRow row = new TableRow(this);
				row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				for (int j = 0; j < 8; j++) {
					TextView tv = new TextView(this);
					tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
					tv.setBackgroundDrawable(roundRectangle);


					tv.setGravity(Gravity.CENTER);
					tv.setTextSize(5);

					tv.setPadding(0, 5, 0, 5);
					tv.setText(""+fila.getString(j));
					row.addView(tv);
				}
				table_layout.addView(row);
			}while(fila.moveToNext());
		}
	}



	private void  construir_boletaje() {

		String[] user=new String[]{"0"};

		Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila = bd.rawQuery("SELECT * from boletaje where  Codbarra !=?", user);
		if (fila.moveToFirst())
		{
			do
			{

				TableRow row = new TableRow(this);
				row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				for (int j = 0; j < 12; j++) {
					TextView tv = new TextView(this);
					tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
					tv.setBackgroundDrawable(roundRectangle);


					tv.setGravity(Gravity.CENTER);
					tv.setTextSize(5);

					tv.setPadding(0, 5, 0, 5);
					tv.setText(""+fila.getString(j));
					row.addView(tv);
				}
				table_layout.addView(row);
			}while(fila.moveToNext());
		}
	}
    private void  construir_boletaje_historico() {

        String[] user=new String[]{"0"};

        Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * from boletaje_historico where  Codbarra !=?", user);
        if (fila.moveToFirst())
        {
            do
            {

                TableRow row = new TableRow(this);
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 12; j++) {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));

                    GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
                    tv.setBackgroundDrawable(roundRectangle);


                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(5);

                    tv.setPadding(0, 5, 0, 5);
                    tv.setText(""+fila.getString(j));
                    row.addView(tv);
                }
                table_layout.addView(row);
            }while(fila.moveToNext());
        }
        else
        {
            Toast.makeText(AuxiliarMostrarBaseDatos.this, "caray", Toast.LENGTH_SHORT).show();
        }
    }

    private void  construir_muestra_medica() {

        String[] user=new String[]{"0"};

        Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * from mm where  CODIGOMM !=?", user);
        if (fila.moveToFirst())
        {
            do
            {

                TableRow row = new TableRow(this);
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 2; j++) {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));

                    GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
                    tv.setBackgroundDrawable(roundRectangle);


                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(5);

                    tv.setPadding(0, 5, 0, 5);
                    tv.setText(""+fila.getString(j));
                    row.addView(tv);
                }
                table_layout.addView(row);
            }while(fila.moveToNext());
        }
    }


	private void  construir_banco_muestras() {

		String[] user=new String[]{"0"};

		Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila = bd.rawQuery("SELECT id_formulario_de_banco_de_muestras, fecha_solicitud, estado, fecha_entregado, justificacion, bandera_seguimiento, fecha_seguimiento, observaciones_seguimiento from banco_muestras  where  id_formulario_de_banco_de_muestras !=?", user);
		if (fila.moveToFirst())
		{
			do
			{

				TableRow row = new TableRow(this);
				row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				for (int j = 0; j < 8; j++) {
					TextView tv = new TextView(this);
					tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
					tv.setBackgroundDrawable(roundRectangle);


					tv.setGravity(Gravity.CENTER);
					tv.setTextSize(5);

					tv.setPadding(0, 5, 0, 5);
					tv.setText(""+fila.getString(j));
					row.addView(tv);
				}
				table_layout.addView(row);
			}while(fila.moveToNext());
		}
	}



	private void  construir_boletaje_auxiliar() {

		String[] user=new String[]{"0"};

		Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila = bd.rawQuery("SELECT * from boletaje_auxiliar where id_visita  !=?", user);
		if (fila.moveToFirst())
		{
			do
			{

				TableRow row = new TableRow(this);
				row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				for (int j = 0; j < 18; j++) {
					TextView tv = new TextView(this);
					tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
					tv.setBackgroundDrawable(roundRectangle);


					tv.setGravity(Gravity.CENTER);
					tv.setTextSize(5);

					tv.setPadding(0, 5, 0, 5);
					tv.setText(""+fila.getString(j));
					row.addView(tv);
				}
				table_layout.addView(row);
			}while(fila.moveToNext());
		}
	}



	public void construir_justificadas()
	{
		String[] user=new String[]{"0"};

		Visitas_controlador admin= new Visitas_controlador(this, "visita_medica.sqlite");
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila = bd.rawQuery("SELECT * from justificadas  where id_visita  !=?", user);
		if (fila.moveToFirst())
		{
			do
			{

				TableRow row = new TableRow(this);
				row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				for (int j = 0; j < 10; j++) {
					TextView tv = new TextView(this);
					tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					GradientDrawable roundRectangle=(GradientDrawable)this.getResources().getDrawable(R.drawable.figuras);
					tv.setBackgroundDrawable(roundRectangle);


					tv.setGravity(Gravity.CENTER);
					tv.setTextSize(5);

					tv.setPadding(0, 5, 0, 5);
					tv.setText(""+fila.getString(j));
					row.addView(tv);
				}
				table_layout.addView(row);
			}while(fila.moveToNext());
		}
	}
}
