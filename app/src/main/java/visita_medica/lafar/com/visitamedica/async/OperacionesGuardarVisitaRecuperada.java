package visita_medica.lafar.com.visitamedica.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
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
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.app.MenuPrincipal;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Boletaje;
import visita_medica.lafar.com.visitamedica.objetos.BoletajeHistorico;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class OperacionesGuardarVisitaRecuperada extends AsyncTask<String, String, String>{
	private ProgressDialog DialogLogin;
	protected Activity activity;
	protected String id_visitador;

	SharedPreferencesP fps= new SharedPreferencesP();
	VariablesUrl var= new VariablesUrl();
	String link =var.function_guardar_visitas_recuperadas();
	String servidor;
	String resultado_exito;
	String error_message;
	Intent intent;
	String toast;
	String code;
    Visitas_controlador vc;
	String resultado;

    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
    ArrayList<BoletajeHistorico> boletaje_historico= new ArrayList<BoletajeHistorico>();
    ArrayList<String> ids_visitas=new ArrayList<String>();

	public OperacionesGuardarVisitaRecuperada(Activity act, String id_visitador)
	{
        super();
        this.activity=act;
        this.id_visitador=id_visitador;

	}

	protected void onPreExecute()
	{	super.onPreExecute();
		DialogLogin = new ProgressDialog(this.activity);
		DialogLogin.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		DialogLogin.setMessage(activity.getString(R.string.guardando_visitas_recuperadas));
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

            System.out.println("************ estoy entrando al try");
			String json=devuelve_cadena_json();
			System.out.println("************:D :D :D "+json.toString());

			StringEntity stringEntity=  new StringEntity(json, "UTF-8");
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("Content-type","application/json;");
			HttpResponse httpResponse= httpClient.execute(httpPost);
			HttpEntity httpEntity=httpResponse.getEntity();
			resultado=convertStreamToString(httpEntity.getContent());

			Log.i("resultado json: ", resultado.toString());
			System.out.println("$$$$$$$$$$$****************************"+resultado.toString());
			JSONObject jsonObject=new JSONObject(resultado);

			resultado_exito=jsonObject.getString("status");
			error_message=jsonObject.getString("message");
			code=jsonObject.getString("code");

			if(resultado_exito.equals("ok"))
                {

vc=new Visitas_controlador(activity,"visita_medica.sqlite");
for(int i=0;i<ids_visitas.size();i++)
{
vc.update_estado_bole_dado_id_visita(ids_visitas.get(i), "1");
}

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
			toast=activity.getString(R.string.visita_guardadas);

			intent = new Intent(activity, MenuPrincipal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);

		
		}
		else if(servidor.equals("problemas_de_conexion"))
		{
			toast=activity.getString(R.string.problemas_de_conexion)+" "+resultado;
			intent = new Intent(activity, MenuPrincipal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
			
		}
		else if(servidor.equals("no_se_pasan_parmetros"))
		{
			toast=activity.getString(R.string.error_del_sistema)+" "+resultado;;
			intent = new Intent(activity,MenuPrincipal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}
		else if(servidor.equals("incorrecto"))
		{
			toast=activity.getString(R.string.datos_incorrectos)+" "+resultado;;
			intent = new Intent(activity, MenuPrincipal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}
		else
		{
			toast=activity.getString(R.string.error_del_sistema)+" "+resultado;;
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

        vc = new Visitas_controlador(activity,"visita_medica.sqlite");
        boletaje=vc.select_boletaje_where_estado_pendiete_and_recuperada();



        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("id_visitador",id_visitador);
        JSONArray jsonArray= new JSONArray();
        if (boletaje != null) {
            for (int i = 0; i < boletaje.size(); i++) {
				System.out.println("entre al for porque hay algo");
				String ba1;

				try
				{
					Bitmap bitmap = BitmapFactory.decodeFile(boletaje.get(i).getRuta_imagen());
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
					byte[] ba = bao.toByteArray();
					ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
				}
				catch(Exception e)
				{
					ba1="";
				}


				JSONObject  object =  new JSONObject();
				object.put("id_visita",boletaje.get(i).getId_visita());
				object.put("Codbarra", boletaje.get(i).getCodbarra());
				object.put("FECHACELULAR",boletaje.get(i).getFECHACELULAR());
				object.put("MESV",boletaje.get(i).getMESV());
				object.put("ANOV",boletaje.get(i).getANOV());
				object.put("APM",boletaje.get(i).getAPM());
				object.put("OBSER",boletaje.get(i).getOBSER());
				object.put("MOTIVO_OBSER",boletaje.get(i).getMOTIVO_OBSER());
				object.put("ciudad",boletaje.get(i).getCiudad());
				object.put("lat",boletaje.get(i).getLat());
				object.put("lon",boletaje.get(i).getLon());
				object.put("estado_visita",vc.select_estado_dado_id_visita(boletaje.get(i).getId_visita()));
				object.put("negativa_editado","0");
				object.put("id_historico","");
				object.put("ruta_imagen",boletaje.get(i).getId_visita()+".jpg");
				object.put("acompanhiado",boletaje.get(i).getAcompanhiado());
				object.put("imagen",ba1);


				jsonArray.put(object);


				ids_visitas.add(boletaje.get(i).getId_visita());

			}

            jsonObject.put("boletaje", jsonArray);
            return jsonObject.toString();
        }
        else
        {
            return null;
        }

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

	
	