package com.ibao.agroq.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibao.agroq.R;
import com.ibao.agroq.models.dao.LoginDataDAO;
/*
import pe.ibao.agromovil.helpers.LoginHelper;
import pe.ibao.agromovil.models.vo.entitiesInternal.UsuarioVO;
*/

public class ActivityPreloader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Resources res = getResources();
        final int orange = res.getColor(R.color.customOrange);

        ((ProgressBar)findViewById(R.id.progressBar))
                .getIndeterminateDrawable()

                .setColorFilter(orange, PorterDuff.Mode.SRC_IN);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                //verificamos si tenemos internet
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {

                    if (new LoginDataDAO(getBaseContext()).verficarLogueo()!=null) {

                        if(new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()>0){
                            startActivity(
                                    new Intent(getBaseContext(), ActivityMain.class)//si tiens intenrnet u estas ogueado
                            );
                            finish();
                        }else {
                            startActivity(
                                    new Intent(getBaseContext(), ActivityPostloader.class)//si tiens intenrnet u estas ogueado
                            );
                            finish();
                        }


                    } else {


                        startActivity(
                                new Intent(getBaseContext(), ActivityUpdate.class)//si tienes internt y no esta ogueado te  manda  loguearte
                        );
                        finish();
                    }

                } else {
                    if (new LoginDataDAO(getBaseContext()).verficarLogueo()!=null) {

                        if(new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()>0){
                            startActivity(
                                    new Intent(getBaseContext(), ActivityMain.class)//si tiens intenrnet u estas ogueado
                            );
                            finish();
                        }else {
                            startActivity(
                                    new Intent(getBaseContext(), ActivityPostloader.class)//si tiens intenrnet u estas ogueado
                            );
                            finish();
                        }


                    } else {
                        Toast.makeText(getBaseContext(),"Conectese a internet porfavor",Toast.LENGTH_LONG).show();
                        finish();
                    }

                }

            }
        }, 500);




    }

    void  openMain(){
        Intent intent = new Intent(this,ActivityMain.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    void  openLogin(){
        Intent intent = new Intent(this,ActivityLogin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

  /*  void  ingresar(){
        Intent intent = new Intent(this,ActivityMain.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
*/
    /*
    void verifyUpdate(){
        Intent intent = new Intent(this,ActivityUpdate.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }*/
}

/***
 datos basucis  no aparece  fecha

 en recepcion traer debajo de productor el fundo
 y en cultivo - variedad

 muestras

 cudano eliminas no actualiza el contador  de la foto



 ***/
