package com.ibao.agroq.helpers.downloaders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.app.AppController;
import com.ibao.agroq.models.dao.CriterioDAO;
import com.ibao.agroq.models.dao.CultivoDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_CODIGO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_IDEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_IDTIPOPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_ISPHOTO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TIPODATO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TOLERANCIAMAXIMO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TOLERANCIAMINIMO;
import static com.ibao.agroq.utilities.Utilities.URL_DOWNLOAD_TABLE_CRITERIO;
import static com.ibao.agroq.utilities.Utilities.URL_DOWNLOAD_TABLE_CULTIVO;

public class DownloaderCriterio {

    String TAG = "Down Criterio";
    Context ctx;
 //   ProgressDialog progress;
    public static int status;
    public DownloaderCriterio(Context ctx){
        this.ctx = ctx;
        status = 0;
    }

    public void download(){
        status = 1;
   //     progress = new ProgressDialog(ctx);
   //     progress.setCancelable(false);
   //     progress.setMessage("Intentando descargar Cultivo");
   //     progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_DOWNLOAD_TABLE_CRITERIO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
       //                 progress.dismiss();
                        try {
                            JSONArray main = new JSONArray(response);
                            Log.d(getClass().getSimpleName(), "res: "+response);
                            if (main.length() == 0)
                            {
                                status = 3;
                            }
                            if(main.length()>0){
                                new CriterioDAO(ctx).clearTableUpload();
                                status=2;
                            }

                            String insert = "INSERT INTO " +
                                    TABLE_CRITERIO+
                                    "("+
                                    TABLE_CRITERIO_COL_CODIGO+","+
                                    TABLE_CRITERIO_COL_NAME+", "+
                                    TABLE_CRITERIO_COL_TIPODATO+", "+
                                    TABLE_CRITERIO_COL_ISPHOTO+", "+
                                    TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+
                                    TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+
                                    TABLE_CRITERIO_COL_IDEVALUACION+", "+
                                    TABLE_CRITERIO_COL_IDTIPOPROCESO+
                                    ")"+
                                    "VALUES ";

                            for(int i=0;i<main.length();i++){
                                JSONObject data = new JSONObject(main.get(i).toString());
                                int codigo = data.getInt("id");
                                String nombre = data.getString("nombre");
                                String tipoDato = data.getString("tipoDato");
                                int isPhoto = data.getInt("fotoCriterio");
                                float tolMinima = data.getString("tolMinima")=="null"?0.0f:(float) data.getDouble("tolMinima");
                                float tolMaxima = data.getString("tolMaxima")=="null"?0.0f:(float) data.getDouble("tolMaxima");
                                int idTipoEvaluacion = data.getInt("idTipoEvaluacion");
                                int idTipoProceso = data.getInt("idTipoProceso");

                                Log.d(TAG,"fila "+i+" : "+codigo+" "+nombre+" "+tipoDato+" "+isPhoto+" "+tolMinima+" "+tolMaxima+" "+idTipoEvaluacion+" "+idTipoProceso);
                                /*
                                if(new CultivoDAO(ctx).insertarCultivo(id,nombre)){
                                    Log.d("CULTIVODOWN","logro insertar");
                                }
                                */
                                insert=insert+"("
                                        +codigo+", "
                                        +"\""+nombre+"\", "
                                        +"\""+tipoDato+"\", "
                                        +isPhoto+", "+
                                        +tolMinima+", "+
                                        +tolMaxima+", "+
                                        +idTipoEvaluacion+", "
                                        +idTipoProceso+
                                        ")";
                                if(i%1000==0 && i>0){
                                    try{
                                        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                                        SQLiteDatabase db = conn.getWritableDatabase();
                                        db.execSQL(insert);
                                        db.close();
                                        conn.close();
                                        insert = "INSERT INTO " +
                                                TABLE_CRITERIO+
                                                "("+
                                                TABLE_CRITERIO_COL_CODIGO+", "+
                                                TABLE_CRITERIO_COL_NAME+", "+
                                                TABLE_CRITERIO_COL_TIPODATO+", "+
                                                TABLE_CRITERIO_COL_ISPHOTO+", "+
                                                TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+
                                                TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+
                                                TABLE_CRITERIO_COL_IDEVALUACION+", "+
                                                TABLE_CRITERIO_COL_IDTIPOPROCESO+
                                                ")"+
                                                "VALUES ";
                                    }catch (Exception e){
                                        Log.d("errorCR",e.toString());
                                    }
                                }else {
                                    if(main.length()-1!=i ){
                                        insert=insert+",";
                                    }
                                }
                            }
                            try{
                                ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                                SQLiteDatabase db = conn.getWritableDatabase();
                                db.execSQL(insert);
                                db.close();
                                conn.close();
                            }catch (Exception e){
                                Log.d(TAG," error insertando "+e.toString());
                            }
                            status = 3;
                        } catch (JSONException e) {
                            Log.d(TAG,"error json"+e.toString());
                            status = -1;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
     //                   progress.dismiss();
                        Toast.makeText(ctx,"Error conectando con el servidor",Toast.LENGTH_LONG).show();
                        status=-2;
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
               /* params.put(POST_USER, user);
                params.put(POST_PASSWORD, pass);
                */
/*
                UsuarioVO temp = new LoginHelper(ctx).verificarLogueo();
                params.put("id",String.valueOf(temp.getId()));
                params.put("idInspector",String.valueOf(temp.getCodigo()));
*/
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        AppController.getInstance().addToRequestQueue(sr);
    }

    public void download(final TextView porcentaje, final TextView mensaje, final int ini, final int tam) {
   /*     progress = new ProgressDialog(ctx);
        progress.setCancelable(false);
        progress.setMessage("Intentando descargar Cultivo");
        progress.show();
    */
        status=1;
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_DOWNLOAD_TABLE_CULTIVO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mensaje.setText("Descargando Configuraciones de Cultivo");
                   //     progress.dismiss();
                        try {
                            JSONArray main = new JSONArray(response);
                            final int length = main.length();
                            if(main.length()>0){
                                new CultivoDAO(ctx).clearTableUpload();
                                status=2;
                            }
                            for(int i=0;i<main.length();i++){
                                JSONObject data = new JSONObject(main.get(i).toString());
                                int id = data.getInt("id");
                                String nombre = data.getString("nombre");
                                Log.d("CULTIVODOWN","fila "+i+" : "+id+" "+nombre);
                                if(new CultivoDAO(ctx).insertar(id,nombre)){
                                    android.os.Handler handler = new android.os.Handler();
                                    final int finalI = i;
                                    handler.post(new Runnable() {
                                        public void run() {
                                            try {
                                                Log.d("CULTIVODOWN","logro insertar");
                                                porcentaje.setText("" + (ini + ((finalI * tam) / length)) + "%");
                                            }catch (Exception e){
                                                Log.d("CULTIVODOWN excepcion",porcentaje.getText().toString()+ e);
                                            }

                                        }
                                    });
                                }
                            }
                   //         porcentaje.setText(String.valueOf(tam));
                            status=3;
                        } catch (JSONException e) {
                            Log.d("CULTIVODOWN ",e.toString());
                            status=-1;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
              //          progress.dismiss();
                        Toast.makeText(ctx,"Error conectando con el servidor",Toast.LENGTH_LONG).show();
                        status=-2;
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
               /* params.put(POST_USER, user);
                params.put(POST_PASSWORD, pass);
                */

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        AppController.getInstance().addToRequestQueue(sr);
    }
}
