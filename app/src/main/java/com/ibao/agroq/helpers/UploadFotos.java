package com.ibao.agroq.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibao.agroq.app.AppController;
import com.ibao.agroq.models.dao.FotoDAO;
import com.ibao.agroq.models.vo.entitiesInternal.FotoVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import static com.ibao.agroq.utilities.Utilities.URL_UPLOAD_FOTOS;

public class UploadFotos {

    Context ctx;
    ProgressDialog progress;

    public static int status;
    public static int count;

    String TAG = UploadFotos.class.getSimpleName();

    public UploadFotos(Context ctx){
        count=0;
        this.ctx = ctx;
    }

    public void Upload(final int idInterno, final int idEvidencia, final String bitmap,int i,int cantidad){

        status =1;
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_UPLOAD_FOTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        count=count+1;
                        //progress.dismiss();
                        status=2;
                        Log.d(TAG,"resp server: "+response);
                        try {
                            JSONObject main = new JSONObject(response);
                            if(main.getInt("success")==1){
                               // Toast.makeText(ctx,""+idEvidencia+" success="+1,Toast.LENGTH_SHORT).show();
                                Log.d(TAG,"[succes 1] idEvidencia"+idEvidencia+"<<<<<<<<<<<<<<<<<<<<<<<<<<");
                                new FotoDAO(ctx).borrarById_UPLOAD(idInterno);
                                status =3;
                            }else{
                                if(main.getInt("success")==0)
                                {
                                  //  Toast.makeText(ctx,""+idEvidencia+" success="+0,Toast.LENGTH_SHORT).show();
                                    Log.d(TAG,"[succes 0] idEvidencia"+idEvidencia+"**********************");
                                    status =-3;
                                    FotoVO temp = new FotoDAO(ctx).consultarById(idInterno);
                                    //new ColaUploadFotosDAO(ctx).nuevo(idEvidencia,temp.getPath());
                                    new FotoDAO(ctx).borrarById_UPLOAD(idInterno);
                                    if (main.has("message"))
                                        Toast.makeText(ctx,main.getString("message"),Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            Log.d(TAG,e.toString());
                            status=-1;
                            Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
                            FotoVO temp = new FotoDAO(ctx).consultarById(idInterno);
                            //new ColaUploadFotosDAO(ctx).nuevo(idEvidencia,temp.getPath());
                            new FotoDAO(ctx).borrarById_UPLOAD(idInterno);
                        }
                        status =3;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progress.dismiss();
                        Log.d(TAG,error.toString());
                        Toast.makeText(ctx,"Error conectando con el servidor",Toast.LENGTH_LONG).show();
                        status =-2;
                        FotoVO temp = new FotoDAO(ctx).consultarById(idInterno);
                        //new ColaUploadFotosDAO(ctx).nuevo(idEvidencia,temp.getPath());
                        new FotoDAO(ctx).borrarById_UPLOAD(idInterno);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("idMuestraEvidencia", String.valueOf(idEvidencia));
                params.put("imagen",bitmap);
                Log.d(TAG,"idMuestraEvidencia: "+(idEvidencia));
                Log.d(TAG,"bitmapmandado: "+bitmap);
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