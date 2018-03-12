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
import java.util.ArrayList;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.app.MenuPrincipal;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.Apm;
import visita_medica.lafar.com.visitamedica.objetos.DetalleBancoMuestras;
import visita_medica.lafar.com.visitamedica.objetos.Medico;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;

public class OperacionesMedicoApm extends AsyncTask<String, String, String> {
    private ProgressDialog DialogLogin;
    protected Activity activity;
    protected String id_visitador;

    VariablesUrl var= new VariablesUrl();
    String link =var.function_descargar_medico_apm();
    String servidor;
    String resultado_exito;
    String error_message;
    Intent intent;
    String toast;
    String code;
    SharedPreferencesP fps= new SharedPreferencesP();
    ArrayList<Medico> medico= new ArrayList<Medico>();
    ArrayList<Apm> visitadores =  new ArrayList<Apm>();
    Visitas_controlador vc;


    public OperacionesMedicoApm(Activity act,String id_visitador)
    {
        super();
        this.activity=act;
        this.id_visitador=id_visitador;

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
            System.out.println("************ "+json.toString());

            StringEntity stringEntity=  new StringEntity(json, "UTF-8");
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-type","application/json;");
            HttpResponse httpResponse= httpClient.execute(httpPost);
            HttpEntity httpEntity=httpResponse.getEntity();
            String resultado=convertStreamToString(httpEntity.getContent());
            System.out.println("$$$$$$$$$$$$$$$$ "+resultado.toString());
            JSONObject jsonObject=new JSONObject(resultado);
            //JSONObject jsonObject=new JSONObject();



           vc= new Visitas_controlador(activity,"visita_medica.sqlite");

            resultado_exito=jsonObject.getString("status");
            error_message=jsonObject.getString("message");
            code=jsonObject.getString("code");


            if(resultado_exito.equals("ok"))
            {

                JSONArray jArray = new JSONArray(jsonObject.getJSONArray("apm").toString());
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);

                    visitadores.add(new Apm(json_data.getString("id_visi"), json_data.getString("nombre_visi")));

                try {

                    JSONArray jArray1 = new JSONArray(json_data.getJSONArray("medicos").toString());
                    for (int j = 0; j < jArray1.length(); j++) {
                        JSONObject json_data1 = jArray1.getJSONObject(j);
                        medico.add
                                (new Medico(
                                                json_data1.getString("id_med"),
                                                json_data1.getString("nombre_med")
                                        )
                                );

                    }

                }
                catch(JSONException e)
                {
                    System.out.println("ya esta controlado");
                }

           }


                vc.add_apm(visitadores);
                vc.add_medico(medico);
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
            Log.e("log_tag", "Error parsing data " + e.toString());
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
            intent = new Intent(activity, MenuPrincipal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.finish();
            activity.startActivity(intent);




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
        DialogLogin.dismiss();
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


