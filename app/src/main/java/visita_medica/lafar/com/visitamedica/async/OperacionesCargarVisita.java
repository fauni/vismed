package visita_medica.lafar.com.visitamedica.async;

/**
 * Created by debbie on 24-07-15.
 */

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
import org.apache.http.NameValuePair;
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
import java.util.List;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.app.TareasPendientes;
import visita_medica.lafar.com.visitamedica.db_model.Visitas_controlador;
import visita_medica.lafar.com.visitamedica.objetos.DetalleVisita;
import visita_medica.lafar.com.visitamedica.objetos.VisitaMedica;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;
import visita_medica.lafar.com.visitamedica.web.VariablesUrl;


public class OperacionesCargarVisita extends AsyncTask<String, String, String> {
    private ProgressDialog Dialog;

    protected Activity activity;
    protected String id_visitador;

    SharedPreferencesP fps= new SharedPreferencesP();

    VariablesUrl var= new VariablesUrl();
    String link =var.function_cargar_visita();
    String servidor;
    Intent intent;
    String toast;
    String resultado_exito;
    String message;
    String code;
    ArrayList<VisitaMedica> visita_medicas= new ArrayList<VisitaMedica>();
    ArrayList<DetalleVisita> detalle_visita= new ArrayList<DetalleVisita>();
    Visitas_controlador vc;
    Integer contador_visitas;

    public OperacionesCargarVisita(Activity act,String id_visitador)
    {
        super();
        this.activity=act;
        this.id_visitador=id_visitador;

    }

    protected void onPreExecute()
    {
        super.onPreExecute();
        Dialog = new ProgressDialog(this.activity);
        Dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        Dialog.setMessage(activity.getString(R.string.enviando_datos));
        Dialog.setIndeterminate(false);
        Dialog.setCancelable(false);
        Dialog.show();
    }

    protected String doInBackground(String... args)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        try
        {
            DefaultHttpClient httpClient =new DefaultHttpClient();
            HttpPost httpPost= new HttpPost(link);
            String json=devuelve_cadena_json();
            StringEntity stringEntity=  new StringEntity(json, "UTF-8");
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-type","application/json;");
            HttpResponse httpResponse= httpClient.execute(httpPost);
            HttpEntity httpEntity=httpResponse.getEntity();
            String resultado=convertStreamToString(httpEntity.getContent());
            JSONObject jsonObject=new JSONObject(resultado);
            Log.i("resultado json: ", resultado.toString());
            System.out.println(resultado.toString());

            resultado_exito=jsonObject.getString("status");
            message=jsonObject.getString("message");
            code=jsonObject.getString("code");


            if(resultado_exito.equals("ok"))
            {
                JSONArray  jArray = new JSONArray(jsonObject.getJSONArray("visita").toString());
                for(int i=0;i<jArray.length();i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    visita_medicas.add(new VisitaMedica(
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
                            json_data.getString("CIUDAD"),
                            json_data.getString("estado"),
                            json_data.getString("dia_visita"),
                            json_data.getString("correlativo_visita"),
                            json_data.getString("semana_visita")

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




            /*    for (int k=0; k<visita_medicas.size();k++)
                {
                    System.out.println("codbarra  "+visita_medicas.get(k).getCodbarra());
                    System.out.println("numvisi  "+visita_medicas.get(k).getNUMVISI());
                    System.out.println("codmed  "+visita_medicas.get(k).getCODMED());
                    System.out.println("espe1  "+visita_medicas.get(k).getESPE1());
                    System.out.println("nombremed  "+visita_medicas.get(k).getNOMBREMED());
                    System.out.println("direccion  "+visita_medicas.get(k).getDIRECCION());
                    System.out.println("hospital "+visita_medicas.get(k).getHOSPITAL());
                    System.out.println("regional  "+visita_medicas.get(k).getREGIONAL());
                    System.out.println("mesv  "+visita_medicas.get(k).getMESV());
                    System.out.println("añov  "+visita_medicas.get(k).getANOV());
                    System.out.println("cate "+visita_medicas.get(k).getCate());
                    System.out.println("ciudad "+visita_medicas.get(k).getCIUDAD());
                    System.out.println("estado  "+visita_medicas.get(k).getEstado());
                }


                for(int l=0;l<detalle_visita.size();l++) {

                    System.out.println("Id_detalle_visita "+          detalle_visita.get(l).getId_detalle_visita());
                    System.out.println("Codbarra "+          detalle_visita.get(l).getCodbarra());
                    System.out.println("CODMED "+          detalle_visita.get(l).getCODMED());
                    System.out.println("CODIGOMM "+          detalle_visita.get(l).getCODIGOMM());
                    System.out.println("CANTIDAD "+         detalle_visita.get(l).getCANTIDAD());
                    System.out.println("MM "+          detalle_visita.get(l).getMM());
                    System.out.println("MES "+          detalle_visita.get(l).getMes());
                    System.out.println("AÑO "+          detalle_visita.get(l).getAnhio());

                }

*/
                vc= new Visitas_controlador(activity, "visita_medica.sqlite");


                    vc.add_visitas(visita_medicas);
                    vc.add_detalle_visita(detalle_visita);


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
            servidor="error_json";
            Log.e("log_tag", "Error parsing data "+e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            servidor="error_excepcion";
        }

        return null;
    }
    protected void onPostExecute(String file_url)
    {

        if(servidor.equals("si"))
        {
            toast=activity.getString(R.string.visitas_descargadas);


            intent= new Intent();
            intent.setComponent(new ComponentName(activity, TareasPendientes.class));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);

        }
        else if(servidor.equals("problemas_de_conexion"))
        {
            toast=activity.getString(R.string.problemas_de_conexion);
            intent = new Intent(activity, TareasPendientes.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }
        else if(servidor.equals("error_exepcion"))
        {
            toast=activity.getString(R.string.error_exception);
            intent = new Intent(activity, TareasPendientes.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }
        else if(servidor.equals("error_json"))
        {
            //toast=activity.getString(R.string.error_del_sistema);
            toast=activity.getString(R.string.error_json);
            intent = new Intent(activity,TareasPendientes.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }
        else if(servidor.equals("incorrecto"))
        {
            toast=activity.getString(R.string.no_cuenta_con_visitas);
            intent = new Intent(activity, TareasPendientes.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }
        else
        {
            toast=activity.getString(R.string.error_del_sistema);
            intent = new Intent(activity, TareasPendientes.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }
        Toast aviso=Toast.makeText(activity,toast,Toast.LENGTH_SHORT);
        aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        aviso.show();

        Dialog.dismiss();
    }

    public String devuelve_cadena_json() throws Exception
    {
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("id_visitador",id_visitador);

        System.out.println("(****************************** "+jsonObject.toString());

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


