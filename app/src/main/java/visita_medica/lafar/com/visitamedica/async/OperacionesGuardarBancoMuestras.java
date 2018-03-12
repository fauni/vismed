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
import visita_medica.lafar.com.visitamedica.app.BancoMuestrasVisitador;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Tblbancomm;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class OperacionesGuardarBancoMuestras extends AsyncTask<String, String, String> {
	private ProgressDialog DialogLogin;
	protected Activity activity;
	protected String id_visitador;

	SharedPreferencesP fps = new SharedPreferencesP();
	VariablesUrl var = new VariablesUrl();
	String link = var.function_guardar_banco_muestras();
	String servidor;
	String resultado_exito;
	String error_message;
	Intent intent;
	String toast;
	String code;
	Visitas_controlador vc;

	ArrayList<Tblbancomm> banco_muestras = new ArrayList<Tblbancomm>();

	public OperacionesGuardarBancoMuestras(Activity act, String id_visitador) {
		super();
		this.activity = act;
		this.id_visitador = id_visitador;

	}

	protected void onPreExecute() {
		super.onPreExecute();
		DialogLogin = new ProgressDialog(this.activity);
		DialogLogin.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		DialogLogin.setMessage(activity.getString(R.string.guardando_banco_de_muestras));
		DialogLogin.setIndeterminate(false);
		DialogLogin.setCancelable(false);
		DialogLogin.show();
	}

	protected String doInBackground(String... args) {


		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(link);

			System.out.println("************ estoy entrando al try");
			String json = devuelve_cadena_json();
			System.out.println("************ " + json.toString());


			servidor="si";
		    StringEntity stringEntity = new StringEntity(json, "UTF-8");
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("Content-type", "application/json;");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String resultado = convertStreamToString(httpEntity.getContent());

			Log.i("resultado json: ", resultado.toString());
			System.out.println("$$$$$$$$$$$" + resultado.toString());
			JSONObject jsonObject = new JSONObject(resultado);

			resultado_exito = jsonObject.getString("status");
			error_message = jsonObject.getString("message");
			code = jsonObject.getString("code");

			if (resultado_exito.equals("ok")) {

				vc = new Visitas_controlador(activity, "visita_medica.sqlite");
				vc.update_banco_muestras_estado_sinc();


				servidor = "si";
			} else {
				servidor = "incorrecto";
			}


		} catch (NullPointerException e) {
			servidor = "problemas_de_conexion";
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
			servidor = "problemas_de_conexion";
			e.printStackTrace();
		} catch (Exception e) {
			servidor = "problemas_de_conexion";
			e.printStackTrace();
		}

		return null;
	}

	protected void onPostExecute(String file_url) {

		if (servidor.equals("si")) {
			toast = activity.getString(R.string.banco_de_muestras_guardado);

			intent = new Intent(activity, BancoMuestrasVisitador.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);


		} else if (servidor.equals("problemas_de_conexion")) {
			toast = activity.getString(R.string.problemas_de_conexion);
			intent = new Intent(activity, BancoMuestrasVisitador.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);

		} else if (servidor.equals("no_se_pasan_parmetros")) {
			toast = activity.getString(R.string.error_del_sistema);
			intent = new Intent(activity, BancoMuestrasVisitador.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		} else if (servidor.equals("incorrecto")) {
			toast = activity.getString(R.string.datos_incorrectos);
			intent = new Intent(activity, BancoMuestrasVisitador.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		} else {
			toast = activity.getString(R.string.error_del_sistema);
			intent = new Intent(activity, BancoMuestrasVisitador.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.finish();
			activity.startActivity(intent);
		}

		Toast anuncio = Toast.makeText(activity, toast, Toast.LENGTH_LONG);
		anuncio.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		anuncio.show();
		DialogLogin.dismiss();
	}


	public String devuelve_cadena_json()  throws Exception{
		vc = new Visitas_controlador(activity, "visita_medica.sqlite");
		banco_muestras = vc.select_banco_muestras_sin_sincronizar();
		JSONObject jsonObject = new JSONObject();

			jsonObject.put("apm", id_visitador);

		JSONArray jsonArray = new JSONArray();
		if (banco_muestras != null)

		{

			for (int i = 0; i < banco_muestras.size(); i++) {


				String ba1;
				if (banco_muestras.get(i).getRuta_imagen_banco_muestras().equals(""))
				{
					ba1 = "";
				}
				else
				{
					Bitmap bitmap = BitmapFactory.decodeFile(banco_muestras.get(i).getRuta_imagen_banco_muestras());
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
					byte[] ba = bao.toByteArray();
					ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
				}





				JSONObject  object =  new JSONObject();
				object.put("codigo", banco_muestras.get(i).getId_formulario_de_banco_de_muestras());
				object.put("estado",banco_muestras.get(i).getEstado());
				object.put("fecha",banco_muestras.get(i).getFecha_entregado());
				object.put("justificacion",banco_muestras.get(i).getJustificacion());
				if(ba1.equals(""))
				{
					object.put("ruta_imagen", "");
				}
				else
				{
					object.put("ruta_imagen", banco_muestras.get(i).getId_formulario_de_banco_de_muestras()+".jpg");
				}
				object.put("imagen",ba1);

				jsonArray.put(object);





			}
			jsonObject.put("banco_muestras", jsonArray);
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

	
	