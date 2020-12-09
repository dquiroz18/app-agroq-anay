package com.ibao.agroq.views;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ibao.agroq.R;
import com.ibao.agroq.models.dao.DescarteDAO;
import com.ibao.agroq.models.dao.DespachoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.ProduccionDAO;
import com.ibao.agroq.models.dao.RecepcionDAO;

public class ActivityPostloader extends AppCompatActivity {

    private static String TAG = ActivityPostloader.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postloader);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Animation animBtn =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(),R.anim.press_btn);
        final   Button btnRecepcion = (Button)  findViewById(R.id.btnProcessRecepcion);
        final   Button btnProduccion = (Button) findViewById(R.id.btnProcessProduccion);
        final   Button btnDespacho = (Button) findViewById(R.id.btnProcessDespacho);
        final   Button btnDescarte = (Button) findViewById(R.id.btnProcessDescarte);
        final ConstraintLayout clCerrarSession = (ConstraintLayout) findViewById(R.id.clCerrarSession);
        final   Button btnCerrarSession = (Button) findViewById(R.id.btnCerrarSession);
        blockBtn(btnRecepcion);
        blockBtn(btnDescarte);
        blockBtn(btnProduccion);
        blockBtn(btnDespacho);


        btnCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animBtn);

                Handler handler = new Handler();
                handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                int i=0;
                                String reco = "No olvide sincronizar sus procesos de:";

                                if (new RecepcionDAO(getBaseContext()).listAll().size() > 0) {
                                    reco=reco+"\n*Recepcion";
                                    i++;
                                }
                                if (new ProduccionDAO(getBaseContext()).listAll().size() > 0) {
                                    reco=reco+"\n*Proceso";
                                    i++;
                                }
                                if (new DespachoDAO(getBaseContext()).listAll().size() > 0) {
                                    reco=reco+"\n*Despacho";
                                    i++;
                                }
                                if (new DescarteDAO(getBaseContext()).listAll().size() > 0) {
                                    reco=reco+"\n*Descarte";
                                    i++;
                                }

                                if(i>0){
                                    showPopupRecomendacion(reco);
                                }else {
                                    Toast.makeText(getBaseContext(), "Cerrando Sesión...", Toast.LENGTH_LONG).show();
                                    new LoginDataDAO(getBaseContext()).borrarTable();
                                    Intent intent = new Intent(getBaseContext(), ActivityPreloader.class);
                                    startActivity(intent);
                                    //finish();
                                }
                            }
                        },200
                );
            }
        });

        clCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animBtn);

                Handler handler = new Handler();
                handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                int i=0;
                                String reco = "No olvide sincronizar sus procesos de:";

                                if (new RecepcionDAO(getBaseContext()).listAll().size() > 0) {
                                    reco=reco+"\n*Recepcion";
                                    i++;
                                }
                                if (new ProduccionDAO(getBaseContext()).listAll().size() > 0) {
                                    reco=reco+"\n*Proceso";
                                    i++;
                                }
                                if (new DespachoDAO(getBaseContext()).listAll().size() > 0) {
                                    reco=reco+"\n*Despacho";
                                    i++;
                                }
                                if (new DescarteDAO(getBaseContext()).listAll().size() > 0) {
                                    reco=reco+"\n*Descarte";
                                    i++;
                                }

                                if(i>0){
                                    showPopupRecomendacion(reco);
                                }else {
                                    Toast.makeText(getBaseContext(), "Cerrando Sesión...", Toast.LENGTH_LONG).show();
                                    new LoginDataDAO(getBaseContext()).borrarTable();
                                    Intent intent = new Intent(getBaseContext(), ActivityPreloader.class);
                                    startActivity(intent);
                                    //finish();
                                }
                            }
                        },200
                );
            }
        });

        String strTipoProcesos = new LoginDataDAO(getBaseContext()).verficarLogueo().getListIdTipoProcesos();
        Log.d(TAG,"hola->"+strTipoProcesos);
        String[] listTipoProcesos = strTipoProcesos.split(",");

        for(int i=0;i<listTipoProcesos.length;i++){
            switch (listTipoProcesos[i]){
                case "1":
                    unBlockBtn(btnRecepcion);
                    /**
                     * btn recepcion
                     */

                    btnRecepcion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animBtn);
                            clCerrarSession.setClickable(false);
                            btnDescarte.setClickable(false);
                            btnRecepcion.setClickable(false);
                            btnProduccion.setClickable(false);
                            btnDespacho.setClickable(false);
                            Handler handler = new Handler();
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            boolean i = new LoginDataDAO(getBaseContext()).editIdTipoProceso(1);
                                            Log.d(TAG,"intento de logueo resp:"+i);
                                            Intent intent = new Intent(getBaseContext(),ActivityMain.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        }
                                    },200
                            );
                        }
                    });
                    break;
                case "2":
                    unBlockBtn(btnProduccion);

                    /***
                     * btnProduccion
                     */
                    /*
                    btnProduccion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animBtn);
                            Handler handler = new Handler();
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getBaseContext(), R.string.noevent_btn,Toast.LENGTH_SHORT).show();
                                        }
                                    },100
                            );


                        }
                    });


                    */
                    btnProduccion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animBtn);
                            clCerrarSession.setClickable(false);
                            btnDescarte.setClickable(false);
                            btnRecepcion.setClickable(false);
                            btnProduccion.setClickable(false);
                            btnDespacho.setClickable(false);
                            Handler handler = new Handler();
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            boolean i = new LoginDataDAO(getBaseContext()).editIdTipoProceso(2);
                                            Log.d(TAG,"intento de logueo resp:"+i);
                                            Intent intent = new Intent(getBaseContext(),ActivityMain.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                          //  Toast.makeText(getBaseContext(), R.string.noevent_btn,Toast.LENGTH_SHORT).show();
                                        }
                                    },100
                            );

                        }
                    });

                    break;
                case "3":
                    unBlockBtn(btnDespacho);
                    /***
                     * btnPTerminado
                     */
                    /*
                    btnPTerminado.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animBtn);
                            Handler handler = new Handler();
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getBaseContext(), R.string.noevent_btn,Toast.LENGTH_SHORT).show();
                                        }
                                    },100
                            );


                        }
                    });
                    */
                    btnDespacho.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animBtn);
                            clCerrarSession.setClickable(false);
                            btnDescarte.setClickable(false);
                            btnRecepcion.setClickable(false);
                            btnProduccion.setClickable(false);
                            btnDespacho.setClickable(false);
                            Handler handler = new Handler();
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            boolean i = new LoginDataDAO(getBaseContext()).editIdTipoProceso(3);
                                            Log.d(TAG,"intento de logueo resp:"+i);
                                            Intent intent = new Intent(getBaseContext(),ActivityMain.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            //  Toast.makeText(getBaseContext(), R.string.noevent_btn,Toast.LENGTH_SHORT).show();
                                        }
                                    },100
                            );
                        }
                    });
                    break;

                case "4":
                    unBlockBtn(btnDescarte);
                    /***
                     * btnDescarte
                     */
/*
                    btnDescarte.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animBtn);
                            Handler handler = new Handler();
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getBaseContext(), R.string.noevent_btn,Toast.LENGTH_SHORT).show();
                                        }
                                    },100
                            );

                        }
                    });
                    */

                    btnDescarte.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animBtn);
                            clCerrarSession.setClickable(false);
                            btnDescarte.setClickable(false);
                            btnRecepcion.setClickable(false);
                            btnProduccion.setClickable(false);
                            btnDespacho.setClickable(false);
                            Handler handler = new Handler();
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            boolean i = new LoginDataDAO(getBaseContext()).editIdTipoProceso(4);
                                            Log.d(TAG,"intento de logueo resp:"+i);
                                            Intent intent = new Intent(getBaseContext(),ActivityMain.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            //  Toast.makeText(getBaseContext(), R.string.noevent_btn,Toast.LENGTH_SHORT).show();
                                        }
                                    },100
                            );
                        }
                    });
                    break;
            }
        }




    }

    private void blockBtn(Button btn){
        float alphaBlock = 0.4f;
        //btnProduccion.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_redaccent700_br30_b0));
        //btnPTerminado.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_redaccent700_br30_b0));
        //btnDescarte.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_redaccent700_br30_b0));
        //btnProduccion.setAlpha(alphaBlock);
        //btnDescarte.setAlpha(alphaBlock);
        btn.setAlpha(alphaBlock);
        btn.setClickable(false);
    }

    private void showPopupRecomendacion(String mensaje){
        final Dialog dialogClose= new Dialog(this);
        dialogClose.setContentView(R.layout.dialog_recomendaciones);
        Button btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close_dialog);
        ImageView iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);
        TextView tViewRecomendacion = dialogClose.findViewById(R.id.tViewRecomendacion);
        tViewRecomendacion.setText(mensaje);
        iViewDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose.dismiss();
            }
        });
        btnDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose.dismiss();
            }
        });

        dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogClose.show();
    }
    private void unBlockBtn(Button btn){
        float alphaBlock = 1.0f;
        //btnProduccion.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_redaccent700_br30_b0));
        //btnPTerminado.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_redaccent700_br30_b0));
        //btnDescarte.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_redaccent700_br30_b0));
        //btnProduccion.setAlpha(alphaBlock);
        //btnDescarte.setAlpha(alphaBlock);
        btn.setAlpha(alphaBlock);
        btn.setClickable(true);
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        moveTaskToBack(true);

    }


}
