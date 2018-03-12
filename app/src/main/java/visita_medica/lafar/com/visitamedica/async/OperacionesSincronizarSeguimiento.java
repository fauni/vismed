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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.app.BancoMuestrasRealizado;
import visita_medica.lafar.com.visitamedica.app.Login;
import visita_medica.lafar.com.visitamedica.app.MenuPrincipal;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class OperacionesSincronizarSeguimiento extends AsyncTask<String, String, String>{
	private ProgressDialog DialogLogin;
	protected Activity activity;
	protected String codigo_banco_muestras;
	protected String usuario;


	String bandera_seguimiento;
	String observaciones_seguimiento;
	String fecha_seguimiento;


	SharedPreferencesP fps= new SharedPreferencesP();
	VariablesUrl var= new VariablesUrl();
	String link =var.function_seguimiento_banco_muestras();
	String servidor;
	String resultado_exito;
	String error_message;
	String id_visitador;
    String tipo;
	Intent intent;
	String toast;
	String code;


    Visitas_controlador vc;
	ArrayList<Tblbancomm> banco_muestras=  new ArrayList<Tblbancomm>();


	public OperacionesSincronizarSeguimiento(Activity activity, String usuario ,String codigo_banco_muestras )
	{
        super();
        this.activity=activity;
        this.codigo_banco_muestras=codigo_banco_muestras;
        this.usuario=usuario;

	}

	protected void onPreExecute()
	{	super.onPreExecute();
		DialogLogin = new ProgressDialog(this.activity);
		DialogLogin.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		DialogLogin.setMessage(activity.getString(R.string.sincronizando_seguimiento));
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
			System.out.println("$$$$$$$$$$$$$$$$ "+resultado.toString());
			JSONObject jsonObject=new JSONObject(resultado);

			

			resultado_exito=jsonObject.getString("status");
			error_message=jsonObject.getString("message");
			code=jsonObject.getString("code");
			
			  if(resultado_exito.equals("ok"))
                {
             

					vc.update_estado_sincronizacion_seguimiento(codigo_banco_muestras );


               	servidor="si";
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
	
		if(servidor.equals("si"))
		{		
			toast=activity.getString(R.string.seguimiento_sincronizado);
			intent = new Intent(activity, BancoMuestrasRealizado.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);



		
		}
		else if(servidor.equals("problemas_de_conexion"))
		{
			toast=activity.getString(R.string.problemas_de_conexion);
			activity.finish();
			
		}
		else if(servidor.equals("no_se_pasan_parmetros"))
		{
			toast=activity.getString(R.string.error_del_sistema);
			activity.finish();
		}
		else if(servidor.equals("incorrecto"))
		{
			toast=activity.getString(R.string.datos_incorrectos);
			activity.finish();
		}
		else
		{
			toast=activity.getString(R.string.error_del_sistema);
			activity.finish();

		}
		
		Toast anuncio=Toast.makeText(activity,toast,Toast.LENGTH_LONG);
		anuncio.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		anuncio.show();
		DialogLogin.dismiss();
	}
	
	public String devuelve_cadena_json() throws Exception	
	{

		vc= new Visitas_controlador(activity, "visita_medica.sqlite");
		banco_muestras=vc.select_banco_muestras_dado_id(codigo_banco_muestras);


		for(int i=0 ; i < banco_muestras.size();i++)
		{
			bandera_seguimiento=banco_muestras.get(i).getBandera_seguimiento();
			observaciones_seguimiento=banco_muestras.get(i).getObservaciones_seguimiento();
			fecha_seguimiento=banco_muestras.get(i).getFecha_seguimiento();
		}


		System.out.println(bandera_seguimiento+ observaciones_seguimiento+ fecha_seguimiento);

		JSONObject jsonObject =  new JSONObject();					
		jsonObject.put("id_usuario",usuario);
		jsonObject.put("id_formulario_banco_muestras",codigo_banco_muestras);
		jsonObject.put("bandera_seguimiento",bandera_seguimiento);
		jsonObject.put("observaciones_seguimiento",observaciones_seguimiento);
		jsonObject.put("fecha_seguimiento",fecha_seguimiento);

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

	
	