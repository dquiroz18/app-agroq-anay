package com.ibao.agroq.helpers.downloaders;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibao.agroq.app.AppController;
import com.ibao.agroq.models.dao.EmpresaDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA_COL_IDZONA;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.URL_DOWNLOAD_TABLE_EMPRESA;

public class DownloaderEmpresa {

    Context ctx;
    //   ProgressDialog progress;
    public static int status;
    public DownloaderEmpresa(Context ctx){
        status=0;
        this.ctx = ctx;
    }

    public void download(){
        status=1;
        //     progress = new ProgressDialog(ctx);
        //     progress.setCancelable(false);
        //     progress.setMessage("Intentando descargar Empresas");
        //     progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_DOWNLOAD_TABLE_EMPRESA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //                   progress.dismiss();
                        try {
                            JSONArray main = new JSONArray(response);
                            Log.d(getClass().getSimpleName(), "res: "+response);
                            if (main.length() == 0)
                            {
                                status = 3;
                            }
                            if(main.length()>0){
                                new EmpresaDAO(ctx).clearTableUpload();
                                status=2;
                            }

                            String insert = "INSERT INTO " +
                                    TABLE_EMPRESA+
                                    "("+
                                    TABLE_EMPRESA_COL_ID+","+
                                    TABLE_EMPRESA_COL_NAME+","+
                                    TABLE_EMPRESA_COL_IDZONA+
                                    ")"+
                                    "VALUES ";
                            for(int i=0;i<main.length();i++){
                                JSONObject data = new JSONObject(main.get(i).toString());
                                int id = data.getInt("id");
                                String nombre = /*String.valueOf(id)+"-"+*/data.getString("nombre");
                                int zona = /*String.valueOf(id)+"-"+*/data.getInt("zona");
                                Log.d("EMPRESADOWN","fila "+i+" : "+id+" "+nombre+" "+zona);


                                if(new EmpresaDAO(ctx).insertarEmpresa(id,nombre,zona)){
                                    Log.d("EMPRESADOWN","logro insertar");
                                }

                                /*
                                insert=insert+"("+id+",\""+nombre+"\","+zona+")";
                                if(i%1000==0&& i>0){
                                    try{
                                        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                                        SQLiteDatabase db = conn.getWritableDatabase();
                                        db.execSQL(insert);
                                        db.close();
                                        conn.close();
                                        insert = "INSERT INTO " +
                                                TABLE_EMPRESA+
                                                "("+
                                                TABLE_EMPRESA_COL_ID+","+
                                                TABLE_EMPRESA_COL_NAME+","+
                                                TABLE_EMPRESA_COL_IDZONA+
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
                                */
                            }
                            /*
                            try{
                                ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                                SQLiteDatabase db = conn.getWritableDatabase();
                                db.execSQL(insert);
                                db.close();
                                conn.close();
                            }catch (Exception e){
                                Log.d("errorCR",e.toString());
                            }*/
                            status=3;
                        } catch (JSONException e) {
                            Log.d("EMPRESADOWN ",e.toString());
                            status=-1;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //                 progress.dismiss();
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

    public void download(final TextView porcentaje, final TextView mensaje, final int  ini, final int tam) {
       /* progress = new ProgressDialog(ctx);
        progress.setCancelable(false);
        progress.setMessage("Intentando descargar Empresas");
        progress.show();
        */
        status=1;
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_DOWNLOAD_TABLE_EMPRESA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mensaje.setText("Descargando Empresas");
                        // progress.dismiss();
                        try {
                            JSONArray main = new JSONArray(response);
                            final int length = main.length();
                            if(main.length()>0){
                                new EmpresaDAO(ctx).clearTableUpload();
                                status=2;
                            }
                            for(int i=0;i<main.length();i++){
                                JSONObject data = new JSONObject(main.get(i).toString());
                                int id = data.getInt("id");
                                String nombre = String.valueOf(id)+"-"+data.getString("nombre");
                                Log.d("EMPRESADOWN","fila "+i+" : "+id+" "+nombre);
                                int zona = data.getInt("zona");
                                if(new EmpresaDAO(ctx).insertarEmpresa(id,nombre,zona)){
                                    Log.d("EMPRESADOWN","logro insertar");
                                    android.os.Handler handler = new android.os.Handler();
                                    final int finalI = i;
                                    handler.post(new Runnable() {
                                        public void run() {
                                            porcentaje.setText("" + (ini + ((finalI * tam) / length)) + "%");
                                        }
                                    });

                                }
                            }
                            status=3;
                            //    porcentaje.setText(String.valueOf(tam));

                        } catch (JSONException e) {
                            Log.d("EMPRESADOWN ",e.toString());
                            status=-1;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progress.dismiss();
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
