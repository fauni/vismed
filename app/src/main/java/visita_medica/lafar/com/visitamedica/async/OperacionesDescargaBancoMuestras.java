package visita_medica.lafar.com.visitamedica.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
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
import java.util.ArrayList;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.app.BancoMuestras;
import visita_medica.lafar.com.visitamedica.app.BancoMuestrasVisitador;
import visita_medica.lafar.com.visitamedica.app.MenuPrincipal;
import visita_medica.lafar.com.visitamedica.app.TareasPendientes;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.objetos.DetalleVisita;
import visita_medica.lafar.com.visitamedica.objetos.MM;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class OperacionesDescargaBancoMuestras extends AsyncTask<String, String, String>{
	private ProgressDialog DialogLogin;
	protected Activity activity;
	protected String apm;

	VariablesUrl var= new VariablesUrl();
	String link =var.function_descargar_banco_muestras();
	String servidor;
	String resultado_exito;
	String error_message;
	Intent intent;
	String toast;
	String code;
    SharedPreferencesP fps= new SharedPreferencesP();
    ArrayList<Tblbancomm> banco_muestras= new ArrayList<Tblbancomm>();
	ArrayList<DetalleBancoMuestras> detalle_banco_muestras= new ArrayList<DetalleBancoMuestras>();
    Visitas_controlador vc;


	public OperacionesDescargaBancoMuestras(Activity act, String apm)
	{
        super();
        this.activity=act;
        this.apm=apm;

	}

	protected void onPreExecute()
	{	super.onPreExecute();
		DialogLogin = new ProgressDialog(this.activity);
		DialogLogin.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		DialogLogin.setMessage(activity.getString(R.string.descargando_banco_de_muestras));
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
			System.out.println("$$$$$$$$$$$$$$$$ "+resultado.toString());

            vc= new Visitas_controlador(activity,"visita_medica.sqlite");

			resultado_exito=jsonObject.getString("status");
			error_message=jsonObject.getString("message");
			code=jsonObject.getString("code");


			  if(resultado_exito.equals("ok"))
                {




					JSONArray  jArray = new JSONArray(jsonObject.getJSONArray("banco_muestras").toString());
					for(int i=0;i<jArray.length();i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						banco_muestras.add(new Tblbancomm(
								json_data.getString("id_formulario_de_banco_de_muestras"),
								json_data.getString("apm"),
								json_data.getString("nombre_vis"),
								json_data.getString("linea_vis"),
								json_data.getString("especialidad_med"),
								json_data.getString("categoria_med"),
								json_data.getString("codmed"),
								json_data.getString("nombre_med"),
								json_data.getString("regional"),
								json_data.getString("fecha_solicitud"),
								"0",
								"",
								"",
								"0",
								"",
								"",
								"",
								"0",
								"0"
						));


						JSONArray  jArray1 = new JSONArray(json_data.getJSONArray("detalle_banco_muestras").toString());
						for(int j=0;j<jArray1.length();j++) {
							JSONObject json_data1 = jArray1.getJSONObject(j);
							detalle_banco_muestras.add
									(new DetalleBancoMuestras(
													json_data1.getString("id_detalle_banco_muestras"),
													json_data1.getString("id_formulario_de_banco_de_muestras"),
													json_data1.getString("codigomm"),
													json_data1.getString("mm"),
													json_data1.getString("cantidad")
											)
									);

						}
					}




                    vc.add_banco_muestra(banco_muestras);
					vc.add_detalle_banco_muestra(detalle_banco_muestras);
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
			servidor="no_se_tienen_datos";
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
			toast=activity.getString(R.string.banco_de_muestras_descargado);
			intent= new Intent();
			intent.setComponent(new ComponentName(activity, BancoMuestrasVisitador.class));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.startActivity(intent);

		}
		else if(servidor.equals("problemas_de_conexion"))
		{
			toast=activity.getString(R.string.problemas_de_conexion);			
			intent = new Intent(activity, BancoMuestrasVisitador.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.startActivity(intent);
			
		}
		else if(servidor.equals("no_se_tienen_datos"))
		{
			toast=activity.getString(R.string.banco_de_muestras_descargado);
			intent = new Intent(activity, BancoMuestrasVisitador.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
		DialogLogin.dismiss();
	}
	
	public String devuelve_cadena_json() throws Exception	
	{
		JSONObject jsonObject =  new JSONObject();					
		jsonObject.put("apm",apm);
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

	
	