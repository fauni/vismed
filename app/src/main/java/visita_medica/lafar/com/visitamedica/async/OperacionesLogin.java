package visita_medica.lafar.com.visitamedica.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Toast;

import visita_medica.lafar.com.visitamedica.app.Login;
import visita_medica.lafar.com.visitamedica.app.MenuPrincipal;
import visita_medica.lafar.com.visitamedica.web.*;
import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.shared_preferences.*;

public class OperacionesLogin extends AsyncTask<String, String, String>{
	private ProgressDialog DialogLogin;
	protected Activity activity;
	protected String email;
	protected String password;
	protected String imei;

	SharedPreferencesP fps= new SharedPreferencesP();
	VariablesUrl var= new VariablesUrl();	
	String link =var.function_login();
	String servidor;	
	String resultado_exito;
	String error_message;
	String id_visitador;
    String tipo;
	Intent intent;
	String toast;
	String code;
	String error_nueva_version_error;

	
	
	public OperacionesLogin(Activity act, String email, String password,String imei)
	{
        super();
        this.activity=act;
        this.email=email;
        this.password=password;
		this.imei= imei;

	}

	protected void onPreExecute()
	{	super.onPreExecute();
		DialogLogin = new ProgressDialog(this.activity);
		DialogLogin.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		DialogLogin.setMessage(activity.getString(R.string.enviando_datos));
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
			error_nueva_version_error=json.toString();
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
                 /*
                 para guardar el id_visitador en el shared
                 */
			  id_visitador=jsonObject.getString("id_visi");
			  fps.Guardar_Shared(activity, id_visitador);

               /*
               para guardar el tipo de visitador en el shared
               */

               tipo=jsonObject.getString("tipo");
               fps.Guardar_tipo(activity, tipo);

               	servidor="si";
               }
			  else
			  {
				if(error_message.equals("id_incorrecto"))
				{
				servidor="imei_incorrecto";
				}
				  else {
					servidor = "incorrecto";
				}
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
	
		if(servidor.equals("si"))
		{		
			toast=activity.getString(R.string.datos_correctos);
			if(tipo.equals("supervisor")) {
				new OperacionesDescargarMuestraMedica(activity, id_visitador).execute();
			}
			else
			{
				intent = new Intent(activity, MenuPrincipal.class);
			/*	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.finish();*/
				activity.finish();
				activity.startActivity(intent);
			}


		
		}
		else if(servidor.equals("problemas_de_conexion"))
		{
			toast=activity.getString(R.string.problemas_de_conexion)+" "+error_nueva_version_error;
			intent = new Intent(activity, Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
			
		}
		else if(servidor.equals("no_se_pasan_parmetros"))
		{
			toast=activity.getString(R.string.error_del_sistema);
			intent = new Intent(activity,Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}
		else if(servidor.equals("imei_incorrecto"))
		{
			toast=activity.getString(R.string.imei_incorrecto);
			intent = new Intent(activity, Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}

		else if(servidor.equals("incorrecto"))
		{
			toast=activity.getString(R.string.datos_incorrectos);
			intent = new Intent(activity, Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}
		else
		{
			toast=activity.getString(R.string.error_del_sistema);
			intent = new Intent(activity, Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}
		
		Toast anuncio=Toast.makeText(activity,toast,Toast.LENGTH_LONG);
		anuncio.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		anuncio.show();
		DialogLogin.dismiss();
	}
	
	public String devuelve_cadena_json() throws Exception	
	{
		JSONObject jsonObject =  new JSONObject();					
		jsonObject.put("usuario",email);
		jsonObject.put("contrasenhia",password);
		jsonObject.put("imei",imei);
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

	
	