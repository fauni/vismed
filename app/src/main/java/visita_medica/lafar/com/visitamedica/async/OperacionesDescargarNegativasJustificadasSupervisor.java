package visita_medica.lafar.com.visitamedica.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import java.util.ArrayList;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.app.JustificadasSupervisor;
import visita_medica.lafar.com.visitamedica.app.Login;
import visita_medica.lafar.com.visitamedica.app.MenuPrincipal;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Apm;
import visita_medica.lafar.com.visitamedica.objetos.Justificadas;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class OperacionesDescargarNegativasJustificadasSupervisor extends AsyncTask<String, String, String>{
	private ProgressDialog DialogDescarga;
	protected Activity activity;
	protected String email;
	protected String password;

	SharedPreferencesP fps= new SharedPreferencesP();
	VariablesUrl var= new VariablesUrl();
	String link =var.function_descarga_justificadas_supervisor();
	String servidor;
	String resultado_exito;
	String error_message;
	String id_visitador;
    String tipo;
	Intent intent;
	String toast;
	String code;

	Visitas_controlador vc;

	ArrayList<Justificadas> justificadas= new ArrayList<Justificadas>();



	public OperacionesDescargarNegativasJustificadasSupervisor(Activity activity, String id_visitador)
	{
        super();
        this.activity=activity;
        this.id_visitador=id_visitador;

	}

	protected void onPreExecute()
	{	super.onPreExecute();
		DialogDescarga = new ProgressDialog(this.activity);
		DialogDescarga.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		DialogDescarga.setMessage(activity.getString(R.string.enviando_datos));
		DialogDescarga.setIndeterminate(false);
		DialogDescarga.setCancelable(false);
		DialogDescarga.show();
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
			System.out.println("$$$$$$$$$$$$$$$$ "+resultado.toString());
			

			resultado_exito=jsonObject.getString("status");
			error_message=jsonObject.getString("message");
			code=jsonObject.getString("code");
			
			  if(resultado_exito.equals("ok"))
                {

						try {
							JSONArray jArray = new JSONArray(jsonObject.getJSONArray("visitas_justificadas").toString());
							for (int i = 0; i < jArray.length(); i++) {
								JSONObject json_data = jArray.getJSONObject(i);


								justificadas.add(new Justificadas(
												json_data.getString("id_visita"),
												json_data.getString("apm"),
												json_data.getString("nombre_doctor"),
												json_data.getString("MESV"),
												json_data.getString("ANOV"),
												json_data.getString("fecha_tab"),
												json_data.getString("hora_tab"),
												json_data.getString("fecha_sinc"),
												json_data.getString("estado_justificada"),
												json_data.getString("estado_sinc"),
												json_data.getString("MOTIVO_OBSER")
										)

								);

							}



							vc = new Visitas_controlador(activity, "visita_medica.sqlite");
							vc.add_justificadas(justificadas);

						}

						catch (JSONException e)
						{

							System.out.println("NO HAY JUSTIFICADAS");
						}


					servidor = "si";

               }
			  else
			  {
				  servidor="incorrecto";
			  }

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

		System.out.println("SERVIDOR <3 <3 <3 <3 DEBBIE"+ servidor);
	
		if(servidor.equals("si"))
		{

			if(error_message.equals("no_hay_justificadas"))
			{
				toast=activity.getString(R.string.no_hay_justificadas);
				intent = new Intent(activity, MenuPrincipal.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.finish();
				activity.startActivity(intent);
			}
			else
			{



				toast=activity.getString(R.string.negativas_justificadas_descargadas);
				intent = new Intent(activity, JustificadasSupervisor.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.finish();
				activity.startActivity(intent);
			}
		}



		else if(servidor.equals("problemas_de_conexion"))
		{
			toast=activity.getString(R.string.problemas_de_conexion);
			intent = new Intent(activity, MenuPrincipal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
			
		}
		else if(servidor.equals("no_se_pasan_parmetros"))
		{
			toast=activity.getString(R.string.error_del_sistema);
			intent = new Intent(activity,MenuPrincipal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}
		else if(servidor.equals("incorrecto"))
		{
			toast=activity.getString(R.string.datos_incorrectos);
			intent = new Intent(activity, MenuPrincipal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}
		else
		{
			toast=activity.getString(R.string.error_del_sistema);
			intent = new Intent(activity, MenuPrincipal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}
		
		Toast anuncio=Toast.makeText(activity,toast,Toast.LENGTH_LONG);
		anuncio.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		anuncio.show();
		DialogDescarga.dismiss();
	}
	
	public String devuelve_cadena_json() throws Exception	
	{
		JSONObject jsonObject =  new JSONObject();					
		jsonObject.put("apm",id_visitador);
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

	
	