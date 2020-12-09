package com.ibao.agroq.services;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ibao.agroq.R;
import com.ibao.agroq.helpers.downloaders.DownloaderCalibre;
import com.ibao.agroq.helpers.downloaders.DownloaderCategoria;
import com.ibao.agroq.helpers.downloaders.DownloaderConfToleranciaEvaluacion;
import com.ibao.agroq.helpers.downloaders.DownloaderConfiguracionEvaluacion;
import com.ibao.agroq.helpers.downloaders.DownloaderCriterio;
import com.ibao.agroq.helpers.downloaders.DownloaderCultivo;
import com.ibao.agroq.helpers.downloaders.DownloaderDestino;
import com.ibao.agroq.helpers.downloaders.DownloaderEmpresa;
import com.ibao.agroq.helpers.downloaders.DownloaderEnvase;
import com.ibao.agroq.helpers.downloaders.DownloaderEvaluacion;
import com.ibao.agroq.helpers.downloaders.DownloaderFundo;
import com.ibao.agroq.helpers.downloaders.DownloaderPlanta;
import com.ibao.agroq.helpers.downloaders.DownloaderTipoCalibre;
import com.ibao.agroq.helpers.downloaders.DownloaderTipoProceso;
import com.ibao.agroq.helpers.downloaders.DownloaderVariedad;
import com.ibao.agroq.helpers.downloaders.DownloaderZona;

public class UpdateService extends IntentService {

    public static final String NOTIFICATION = "notification";
    public static final String RESULT = "RESULT";
    public static final String RESULT_PORCENT = "PORCENT";
    public static final String RESULT_MENSAJE = "MENSAJE";

    static String TAG = "ints update";

    public UpdateService() {
        super("Download Service");
    }

    void reiniciarDownloaders(){

        new DownloaderCalibre(getBaseContext()).download();
        new DownloaderCategoria(getBaseContext()).download();
        new DownloaderConfiguracionEvaluacion(getBaseContext()).download();
        new DownloaderConfToleranciaEvaluacion(getBaseContext()).download();
        new DownloaderCriterio(getBaseContext()).download();
        new DownloaderCultivo(getBaseContext()).download();
        new DownloaderDestino(getBaseContext()).download();
        new DownloaderEmpresa(getBaseContext()).download();
        new DownloaderEnvase(getBaseContext()).download();
        new DownloaderEvaluacion(getBaseContext()).download();
        new DownloaderFundo(getBaseContext()).download();
        new DownloaderPlanta(getBaseContext()).download();
        new DownloaderTipoCalibre(getBaseContext()).download();
        new DownloaderTipoProceso(getBaseContext()).download();
        new DownloaderVariedad(getBaseContext()).download();
        new DownloaderZona(getBaseContext()).download();
    }

    @SuppressLint("WrongThread")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        actualizar ac = new actualizar(getBaseContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
        reiniciarDownloaders();
        r.run();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = getPackageName();
        String channelName = "Servicio de actualización";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_add_icons_24dp)
                .setContentTitle("Actualizando Data")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    private void publishResults(int result, String mensaje, int porcent) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        intent.putExtra(RESULT_PORCENT, porcent);
        intent.putExtra(RESULT_MENSAJE, mensaje);
        sendBroadcast(intent);
    }


    boolean flag = true;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            while (flag){
                final int resultStatus = Activity.RESULT_OK;
                Log.d("holaasd", "empesando");
                int[] i = new int[16];
                i[0] = DownloaderCalibre.status;
                i[1] = DownloaderCategoria.status;
                i[2] = DownloaderConfiguracionEvaluacion.status;
                i[3] = DownloaderConfToleranciaEvaluacion.status;
                i[4] = DownloaderCriterio.status;
                i[5] = DownloaderCultivo.status;
                i[6] = DownloaderDestino.status;
                i[7] = DownloaderEmpresa.status;
                i[8] = DownloaderEnvase.status;
                i[9] = DownloaderEvaluacion.status;
                i[10] = DownloaderFundo.status;
                i[11] = DownloaderPlanta.status;
                i[12] = DownloaderTipoCalibre.status;
                i[13] = DownloaderTipoProceso.status;
                i[14] = DownloaderVariedad.status;
                i[15] = DownloaderZona.status;


                for(int k=0;k<i.length;k++){
                    Log.d(TAG, "status d"+k+": "+i[k]);
                }

                String m0 = "Buscando Actulizacion";
                final String m1 = "Actualizando";
                final String m2 = "Buscando Actulizacion";
                final String m3 = "Error de Conversion";
                final String m4 = "Error de Conexion";
                //   mensaje.setText(m0);
                int cont2 = 0;
                int cont3 = 0;
                boolean act = false;
                Log.d(TAG, "while");
                //verificando si todos las  descargas terminaron
                for (int n = 0; n < i.length; n++) {
                    if (i[n] == 3) {
                        cont3++;
                        Log.d(TAG, "cont3:" + cont3);
                        act = true;
                    } else {
                        if (i[n] == 2) {
                            cont2++;
                            act = true;
                        } else {
                            if (i[n] == -1) {
                                Log.d(TAG, "i["+n+"]=-1");
                                String mensaje = m3;
                                int por = -1;
                                publishResults(Activity.RESULT_CANCELED, mensaje, por);
                                return;
                            } else {
                                if (i[n] == -2) {
                                    Log.d(TAG, "i["+n+"]=-2");
                                    String mensaje = m4;
                                    int por = -2;
                                    flag=false;
                                    publishResults(Activity.RESULT_CANCELED, mensaje, por);
                                    Log.d(TAG,"cancle-2");
                                    return;
                                }
                            }
                        }
                    }
                }

                Log.d("holahola3",""+cont3);
                Log.d("holahola2",""+cont2);

                if (cont3 == i.length) {

                    publishResults(Activity.RESULT_OK, "Actulizacion Terminada", 100);
                    flag=false;
                    return;

                } else {
                    Log.d("holahola", "" + i.length);
                    Log.d("holahola", "" + cont3);
                    final boolean a = act;
                    final int porce = (int) ((float) cont3 / (float) i.length * 100);

                    publishResults(Activity.RESULT_OK, m1, porce);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.d(TAG,"error"+ e.toString());
                    }
                }
            }
        }

    };
}

class actualizar extends AsyncTask<Void, Integer, Boolean> {
    Context ctx;
    String TAG = "ACTU ASYNC";

    actualizar(Context ctx){
        this.ctx=ctx;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d(TAG,"doInBackground");

        DownloaderConfiguracionEvaluacion core= new DownloaderConfiguracionEvaluacion(ctx);
        core.download();
        while(DownloaderConfiguracionEvaluacion.status!=3){
            if(DownloaderConfiguracionEvaluacion.status==-2 || DownloaderConfiguracionEvaluacion.status==-1){
                Log.d(TAG,"ERROR CONF EVA DOWN"+DownloaderConfiguracionEvaluacion.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderCriterio cont = new DownloaderCriterio(ctx);
        cont.download();
        while(DownloaderCriterio.status!=3){
            if(DownloaderCriterio.status==-2 || DownloaderCriterio.status==-1){
                Log.d("segundo","seperando contacto"+DownloaderCriterio.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderEmpresa zona = new DownloaderEmpresa(ctx);
        zona.download();
        while(DownloaderEmpresa.status!=3){
            if(DownloaderEmpresa.status==-2 || DownloaderEmpresa.status==-1){
                Log.d("segundo","seperando zona"+DownloaderEmpresa.status);
                return null;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderEnvase emp = new DownloaderEnvase(ctx);
        emp.download();
        while(DownloaderEnvase.status!=3){
            if(DownloaderEnvase.status==-2 || DownloaderEnvase.status==-1){
                Log.d("segundo","seperando empresa"+DownloaderEnvase.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderEvaluacion fun = new DownloaderEvaluacion(ctx);
        fun.download();
        while(DownloaderEvaluacion.status!=3){
            if(DownloaderEvaluacion.status==-2 || DownloaderEvaluacion.status==-1){
                Log.d("segundo","seperando fundo"+DownloaderEvaluacion.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderFundo cul = new DownloaderFundo(ctx);
        cul.download();
        while(DownloaderFundo.status!=3){
            if(DownloaderFundo.status==-2 || DownloaderFundo.status==-1){
                Log.d("segundo","seperando cultivo"+DownloaderFundo.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderTipoProceso var = new DownloaderTipoProceso(ctx);
        var.download();
        while(DownloaderTipoProceso.status!=3){
            if(DownloaderTipoProceso.status==-2 || DownloaderTipoProceso.status==-1){
                Log.d("segundo","seperando vareidad"+DownloaderTipoProceso.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        DownloaderVariedad fva = new DownloaderVariedad(ctx);
        fva.download();
        while(DownloaderVariedad.status!=3){
            if(DownloaderVariedad.status==-2 || DownloaderVariedad.status==-1){
                Log.d("segundo","seperando fundovariedad"+DownloaderVariedad.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderZona ti = new DownloaderZona(ctx);
        ti.download();
        while(DownloaderZona.status!=3){
            if(DownloaderZona.status==-2 || DownloaderZona.status==-1){
                Log.d("segundo","seperando tipoinspeccion"+DownloaderZona.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderCultivo dCul = new DownloaderCultivo(ctx);
        dCul.download();
        while(DownloaderCultivo.status!=3){
            if(DownloaderCultivo.status==-2 || DownloaderCultivo.status==-1){
                Log.d("segundo","seperando tipoinspeccion"+DownloaderCultivo.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderPlanta tPl = new DownloaderPlanta(ctx);
        tPl.download();
        while(DownloaderPlanta.status!=3){
            if(DownloaderPlanta.status==-2 || DownloaderPlanta.status==-1){
                Log.d("segundo","seperando tipoinspeccion"+DownloaderPlanta.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderConfToleranciaEvaluacion confTolEva = new DownloaderConfToleranciaEvaluacion(ctx);
        confTolEva.download();
        while(DownloaderConfToleranciaEvaluacion.status!=3){
            if(DownloaderConfToleranciaEvaluacion.status==-2 || DownloaderConfToleranciaEvaluacion.status==-1){
                Log.d("segundo","seperando tipoinspeccion"+DownloaderConfToleranciaEvaluacion.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderCalibre cal = new DownloaderCalibre(ctx);
        cal.download();
        while(DownloaderCalibre.status!=3){
            if(DownloaderCalibre.status==-2 || DownloaderCalibre.status==-1){
                Log.d("segundo","seperando vareidad"+DownloaderCalibre.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderCategoria cat = new DownloaderCategoria(ctx);
        cat.download();
        while(DownloaderCategoria.status!=3){
            if(DownloaderCategoria.status==-2 || DownloaderCategoria.status==-1){
                Log.d("segundo","seperando vareidad"+DownloaderCategoria.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderDestino des = new DownloaderDestino(ctx);
        des.download();
        while(DownloaderDestino.status!=3){
            if(DownloaderDestino.status==-2 || DownloaderDestino.status==-1){
                Log.d("segundo","seperando vareidad"+DownloaderDestino.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderTipoCalibre tcal = new DownloaderTipoCalibre(ctx);
        tcal.download();
        while(DownloaderTipoCalibre.status!=3){
            if(DownloaderTipoCalibre.status==-2 || DownloaderTipoCalibre.status==-1){
                Log.d("segundo","seperando vareidad"+DownloaderTipoCalibre.status);
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //          int progreso = values[0].intValue();

        //         pbarProgreso.setProgress(progreso);
        Log.d("segundo","doProgressUpdate");
    }

    @Override
    protected void onPreExecute() {
        Log.d("segundo","onPreExecute");
        try {
            //  mensaje.setText("Buscando Actualizacion");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //         pbarProgreso.setMax(100);
        //         pbarProgreso.setProgress(0);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("segundo","onPostEXecute");
        super.onPostExecute(false);
        //         if(result)
        //             Toast.makeText(MainHilos.this, "Tarea finalizada!",
        //                     Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {
        Log.d("segundo","onCancelled");
        //        Toast.makeText(MainHilos.this, "Tarea cancelada!",
        //                Toast.LENGTH_SHORT).show();
    }
}



