package com.ibao.agroq.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibao.agroq.app.AppController;

import java.util.HashMap;
import java.util.Map;

import static com.ibao.agroq.utilities.Utilities.URL_UPLOAD_MAKE_PDF;

public class UploadSuccessPDF {

    Context ctx;
    ProgressDialog progress;

    public static int status;

    String TAG = UploadSuccessPDF.class.getSimpleName();

    public UploadSuccessPDF(Context ctx){
        status =0;
        this.ctx = ctx;
    }

    public void Upload(final String to_pdf){
        status =1;
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_UPLOAD_MAKE_PDF,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("to_pdf",to_pdf);
                Log.d(TAG,"to_pdf: "+to_pdf);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }
}