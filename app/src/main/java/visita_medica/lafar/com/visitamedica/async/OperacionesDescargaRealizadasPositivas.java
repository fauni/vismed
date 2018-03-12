package visita_medica.lafar.com.visitamedica.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.app.TareasRealizadas;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.DetalleVisita;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class OperacionesDescargaRealizadasPositivas extends AsyncTask<String, String, String>{
	private ProgressDialog DialogLogin;
	protected Activity activity;
	protected String apm;

	VariablesUrl var= new VariablesUrl();
	String link =var.function_positivas_realizadas();
	String servidor;
	String resultado_exito;
	String error_message;
	Intent intent;
	String toast;
	String code;
	String error;

	/*
	GET DAY DEL CALENDARIO
	 */
	private Calendar fechaYhora = Calendar.getInstance();
	SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
	String fecha_actual;

    SharedPreferencesP fps= new SharedPreferencesP();
    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
	ArrayList<VisitaMedica> visita_medica= new ArrayList<VisitaMedica>();
	ArrayList<DetalleVisita> detalle_visita= new ArrayList<DetalleVisita>();

    Visitas_controlador vc;


	public OperacionesDescargaRealizadasPositivas(Activity act, String apm)
	{
        super();
        this.activity=act;
        this.apm=apm;

	}

	protected void onPreExecute()
	{	super.onPreExecute();
		DialogLogin = new ProgressDialog(this.activity);
		DialogLogin.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		DialogLogin.setMessage(activity.getString(R.string.descargando_tareas_realizadas));
		DialogLogin.setIndeterminate(false);
		DialogLogin.setCancelable(false);
		DialogLogin.show();	
	}
	
	protected String doInBackground(String... args) 
	{		
		

		try
		{
			DefaultHttpClient httpClient =new DefaultHttpClient();
			HttpPost httpPost= new HttpPost(link);		
			String json=devuelve_cadena_json();
			 System.out.println("************ "+json.toString());

			StringEntity stringEntity=  new StringEntity(json, "UTF-8");
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("Content-type","application/json;");
			HttpResponse httpResponse= httpClient.execute(httpPost);		
			HttpEntity httpEntity=httpResponse.getEntity();
			String resultado=convertStreamToString(httpEntity.getContent());		
		    JSONObject jsonObject=new JSONObject(resultado);
			error=resultado.toString();
			System.out.println("$$$$$$$$$$$$$$$$ "+resultado.toString());

            vc= new Visitas_controlador(activity,"visita_medica.sqlite");

			resultado_exito=jsonObject.getString("status");
			error_message=jsonObject.getString("message");
			code=jsonObject.getString("code");


			  if(resultado_exito.equals("ok"))
                {


                JSONArray  jArray = new JSONArray(jsonObject.getJSONArray("visita").toString());
					for(int i=0;i<jArray.length();i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						visita_medica.add(new VisitaMedica(

								json_data.getString("id_visita"),
								json_data.getString("Codbarra"),
								json_data.getString("NUMVISI"),
								json_data.getString("CODMED"),
								json_data.getString("ESPE1"),
								json_data.getString("NOMBREMED"),
								json_data.getString("DIRECCION"),
								json_data.getString("HOSPITAL"),
								json_data.getString("REGIONAL"),
								json_data.getString("MESV"),
								json_data.getString("ANOV"),
								json_data.getString("cate"),
								json_data.getString("CIUDAD_visita"),
								json_data.getString("estado"),
								json_data.getString("dia_visita"),
								json_data.getString("correlativo_visita"),
								json_data.getString("semana_visita")


						));






						boletaje.add(new Boletaje(
								json_data.getString("id_visita"),
								json_data.getString("Codbarra"),
								json_data.getString("FECHACELULAR"),
								json_data.getString("MESV"),
								json_data.getString("ANOV"),
								json_data.getString("APM"),
								json_data.getString("OBSER"),
								json_data.getString("MOTIVO_OBSER"),
								json_data.getString("ciudad_boletaje"),
								json_data.getString("lat"),
								json_data.getString("lon"),
								json_data.getString("estado_subida"),
								json_data.getString("negativo_editado"),
							     var.function_direccion_firmas()+json_data.getString("ruta_imagen"),
								json_data.getString("acompanhiado")
						));

						JSONArray  jArray1 = new JSONArray(json_data.getJSONArray("detalle_visita").toString());
						for(int j=0;j<jArray1.length();j++) {
							JSONObject json_data1 = jArray1.getJSONObject(j);
							detalle_visita.add
									(new DetalleVisita(
													json_data1.getString("id_detalle_visita"),
													json_data1.getString("Codbarra"),
													json_data1.getString("CODMED"),
													json_data1.getString("CODIGOMM"),
													json_data1.getString("CANTIDAD"),
													json_data1.getString("MM"),
													json_data1.getString("mes"),
													json_data1.getString("anhio")
											)
									);

						}
					}
					vc.add_boletaje(boletaje);
					vc.add_visitas(visita_medica);
					vc.add_detalle_visita(detalle_visita);
                    servidor="si";
               }
			  else
			  {
				  servidor="incorrecto";
			  }

			servidor="si";
		}
		catch (NullPointerException e) 
		{
			servidor="problemas_de_conexion";
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data "+e.toString());
			servidor="problemas_de_conexion";
			e.printStackTrace();
		} catch (Exception e) {
			servidor="problemas_de_conexion";
			e.printStackTrace();
		}
		
		return null;
	}
	protected void onPostExecute(String file_url)
	{
	
		if(servidor.equals("si"))
		{
			toast=activity.getString(R.string.visitas_realizadas_descargadas);
			intent = new Intent(activity, TareasRealizadas.class);
			activity.startActivity(intent);

		}
		else if(servidor.equals("problemas_de_conexion"))
		{
			toast=activity.getString(R.string.problemas_de_conexion)+" "+error;
			intent = new Intent(activity, TareasRealizadas.class);
			activity.startActivity(intent);
			
		}
		else if(servidor.equals("no_se_pasan_parmetros"))
		{
			toast=activity.getString(R.string.error_del_sistema);
			intent = new Intent(activity, TareasRealizadas.class);
			activity.startActivity(intent);
		}
		else if(servidor.equals("incorrecto"))
		{
			toast=activity.getString(R.string.datos_incorrectos);
			intent = new Intent(activity, TareasRealizadas.class);
			activity.startActivity(intent);
		}
		else
		{
			toast=activity.getString(R.string.error_del_sistema);
			intent = new Intent(activity, TareasRealizadas.class);
			activity.startActivity(intent);
		}
		
		Toast anuncio=Toast.makeText(activity,toast,Toast.LENGTH_LONG);
		anuncio.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		anuncio.show();
		DialogLogin.dismiss();
	}
	
	public String devuelve_cadena_json() throws Exception	
	{

		fecha_actual = fecha.format(fechaYhora.getTime());
		String array_fecha_inicio[]= fecha_actual.split("-");
		String fecha_inicio="";
		for(int i = 0 ; i<array_fecha_inicio.length; i++)
		{
			fecha_inicio=array_fecha_inicio[0]+"-"+array_fecha_inicio[1]+"-01";
		}

		JSONObject jsonObject =  new JSONObject();					
		jsonObject.put("apm",apm);
		jsonObject.put("fecha_inicio",fecha_inicio);
		jsonObject.put("fecha_fin",fecha_actual);
		return jsonObject.toString();
	}

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
		
	
  }	

	
	