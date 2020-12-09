package com.ibao.agroq.views;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ibao.agroq.R;
import com.ibao.agroq.services.UpdateService;


public class ActivityUpdate extends AppCompatActivity {

    private static TextView porcentaje;
    private static TextView mensaje;
    static String TAG = "Act Update";
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(UpdateService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            final Bundle bundle = intent.getExtras();
            Log.d(TAG,""+bundle.getString(UpdateService.RESULT_MENSAJE)+" "+bundle.getInt(UpdateService.RESULT_PORCENT));

            if (bundle != null) {
                final int resultCode = bundle.getInt(UpdateService.RESULT);
                if (resultCode == RESULT_OK) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("cargando",""+bundle.getString(UpdateService.RESULT_MENSAJE)+" "+bundle.getInt(UpdateService.RESULT_PORCENT));
                            String menActual = bundle.getString(UpdateService.RESULT_MENSAJE);
                            String porActual = ""+bundle.getInt(UpdateService.RESULT_PORCENT)+"%";
                            if(!porcentaje.getText().toString().equals(porActual)){
                                porcentaje.setText(porActual);
                                if(porActual.equals("100%")){
                                    openLogin();
                                }
                            }
                            if(!mensaje.getText().toString().equals(menActual)){
                                mensaje.setText(menActual);
                            }
                        }
                    });
                } else {

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG,""+bundle.getString(UpdateService.RESULT_MENSAJE)+" "+bundle.getInt(UpdateService.RESULT_PORCENT));
                            String menActual = bundle.getString(UpdateService.RESULT_MENSAJE);
                            String porActual = ""+bundle.getInt(UpdateService.RESULT_PORCENT)+"%";
                            if(!porcentaje.getText().toString().equals(porActual)){
                                porcentaje.setText(porActual);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(porActual.equals("-2%")){
                                    openLogin();
                                }else {
                                    if(porActual.equals("-1%")){
                                        openLogin();
                                    }
                                }
                            }
                            if(!mensaje.getText().toString().equals(menActual)){
                                mensaje.setText(menActual);
                            }
                        }
                    });
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        porcentaje = (TextView) findViewById(R.id.update_tViewPorcentaje);
        mensaje = (TextView) findViewById(R.id.update_tViewMensaje);
        Intent intent = new Intent(this,UpdateService.class);
        startService(intent);
        mensaje.setText("Buscando Actulizacion");

    }

    void openLogin(){
        /**/
        Intent intent = new Intent(this,ActivityLogin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }





}


