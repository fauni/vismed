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

public class OperacionesGuardarHistoricoActualizarEstado extends AsyncTask<String, String, String>{
	private ProgressDialog DialogLogin;
	protected Activity activity;
	protected String id_visitador;

	SharedPreferencesP fps= new SharedPreferencesP();
	VariablesUrl var= new VariablesUrl();
	String link =var.function_guardar_historico_aztualizar_estado();
	String servidor;
	String resultado_exito;
	String error_message;
	Intent intent;
	String toast;
	String code;
	String resultado;

    Visitas_controlador vc;


    ArrayList<Boletaje> boletaje= new ArrayList<Boletaje>();
    ArrayList<BoletajeHistorico> boletaje_historico= new ArrayList<BoletajeHistorico>();
    ArrayList<String> ids_visitas=new ArrayList<String>();

	public OperacionesGuardarHistoricoActualizarEstado(Activity act, String id_visitador)
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
			System.out.println("************ "+json.toString());

			StringEntity stringEntity=  new StringEntity(json, "UTF-8");
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("Content-type","application/json;");
			HttpResponse httpResponse= httpClient.execute(httpPost);
			HttpEntity httpEntity=httpResponse.getEntity();
			resultado=convertStreamToString(httpEntity.getContent());

			Log.i("resultado json: ", resultado.toString());
			System.out.println("$$$$$$$$$$$"+resultado.toString());
			JSONObject jsonObject=new JSONObject(resultado);

			resultado_exito=jsonObject.getString("status");
			error_message=jsonObject.getString("message");
			code=jsonObject.getString("code");

			if(resultado_exito.equals("ok"))
                {

/*vc=new Visitas_controlador(activity,"visita_medica.sqlite");
for(int i=0;i<ids_visitas.size();i++)
{
vc.update_estado_bole_dado_id_visita(ids_visitas.get(i), "1");
}*/

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

			new OperacionesGuardarVisitaRecuperada(activity,id_visitador).execute();

		
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
			toast=activity.getString(R.string.datos_incorrectos)+" "+resultado;
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

                      String id_visita_boletaje=boletaje.get(i).getId_visita();
                      boletaje_historico=vc.select_boletaje_historico_dado_codbarra(id_visita_boletaje);


							JSONObject object = new JSONObject();
							object.put("id_historico",  boletaje_historico.get(0).getId_historico());
							object.put("id_visita", id_visita_boletaje);
							object.put("Codbarra", boletaje_historico.get(0).getCodbarra());
							object.put("FECHACELULAR", boletaje_historico.get(0).getFECHACELULAR());
							object.put("MESV", boletaje_historico.get(0).getMESV());
							object.put("ANOV", boletaje_historico.get(0).getANOV());
							object.put("APM", boletaje_historico.get(0).getAPM());
							object.put("OBSER", boletaje_historico.get(0).getOBSER());
							object.put("MOTIVO_OBSER", boletaje_historico.get(0).getMOTIVO_OBSER());
							object.put("ciudad", boletaje_historico.get(0).getCiudad());
							object.put("lat", boletaje_historico.get(0).getLat());
							object.put("lon", boletaje_historico.get(0).getLon());
							object.put("ruta_imagen", boletaje_historico.get(0).getId_visita() + ".jpg");
							object.put("acompanhiado", boletaje_historico.get(0).getAcompanhiado());


							jsonArray.put(object);
							boletaje_historico.clear();


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

	
	