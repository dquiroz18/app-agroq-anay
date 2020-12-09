package com.ibao.agroq.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.agroq.R;
import com.ibao.agroq.helpers.adapters.RViewAdapterEvaluacionDetalle_new;
import com.ibao.agroq.models.dao.EvaluacionDAO;
import com.ibao.agroq.models.dao.EvaluacionDetalleDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.MuestraDAO;
import com.ibao.agroq.models.vo.entitiesInternal.EvaluacionDetalleVO;
import com.ibao.agroq.models.vo.entitiesDB.EvaluacionVO;
import com.ibao.agroq.models.vo.entitiesInternal.MuestraVO;

import java.util.ArrayList;
import java.util.List;

public class ActivityMuestra extends AppCompatActivity {


   // private Animator spruceAnimator;


    private static Dialog dialog;
    private static String TAG = ActivityMuestra.class.getSimpleName();
    private static TextView tViewFecha;
    public static EditText eTextNFrutos;
    private static boolean isEditable;
    private static MuestraVO MUESTRA;
    private static FloatingActionButton fABtnComent;

    static public int lastItemSelected=0;

    //private static ListView rViewEvaluacionesDetalle;
    private static RecyclerView rViewEvaluacionesDetalle;

    private static List<EvaluacionVO> evaluacionVOList;
    private static List<EvaluacionDetalleVO> evaluacionDetalleVOList;

    //private static AdapterListEvaluacionDetalle adapterListEvaluacionDetalle;
    private static RViewAdapterEvaluacionDetalle_new adapterListEvaluacionDetalle;

    public static final int REQUEST_CRITERIODETALLE=123;

    View activityRootView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle(R.string.muestra);


        fABtnComent = (FloatingActionButton) findViewById(R.id.fButtonComentario);

        dialog = new Dialog(this);
        rViewEvaluacionesDetalle = (RecyclerView) findViewById(R.id.rViewEvaluaciones);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
              //  initSpruce();
            }
        };

        rViewEvaluacionesDetalle.setLayoutManager(linearLayoutManager);

        activityRootView = findViewById(android.R.id.content);

        tViewFecha = (TextView) findViewById(R.id.tViewFechaRecepcion);
        eTextNFrutos = (EditText) findViewById(R.id.eTextNFrutosMuestra);
        Intent intent = getIntent();
        Bundle mybundle = intent.getExtras();
        isEditable = mybundle.getBoolean(getString(R.string.isEditable));
        MUESTRA = new MuestraDAO(getBaseContext()).consultarById(mybundle.getInt(getString(R.string.idMuestra)));
        tViewFecha.setText(MUESTRA.getFechaHora());
        if (MUESTRA.getCantidad() > 0) {
            eTextNFrutos.setText("" + MUESTRA.getCantidad());
        }

        if(MUESTRA.getComentario() == null || MUESTRA.getComentario().isEmpty()){
            Log.d(TAG,"COMENTARIO NULL");
            fABtnComent.setImageResource(R.drawable.ic_mode_comment_black_24dp);
        }else {
            Log.d(TAG,"CON COMENTARIO NO NULL");
            fABtnComent.setImageResource(R.drawable.ic_comment_icons_24dp);
        }

        eTextNFrutos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Toast.makeText(getBaseContext(),"guardando "+eTextNFrutos.getText().toString(),Toast.LENGTH_SHORT).show();
                    if ((eTextNFrutos.getText().toString().equals(""))) {
                        new MuestraDAO(getBaseContext()).editNFrutos(MUESTRA.getId(), 0);
                    } else {
                        new MuestraDAO(getBaseContext()).editNFrutos(MUESTRA.getId(), Integer.valueOf(eTextNFrutos.getText().toString()));
                    }
                    MUESTRA = new MuestraDAO(getBaseContext()).consultarById(MUESTRA.getId());
                }
            }
        });


        final View activityRootView = findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isTyping = false;
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    isTyping = true;
                    Log.d(TAG, "teclado Cerrado1");

                    //Toast.makeText(getBaseContext(),"guardando "+eTextNFrutos.getText().toString(),Toast.LENGTH_SHORT).show();
                    if ((eTextNFrutos.getText().toString().equals(""))) {
                        new MuestraDAO(getBaseContext()).editNFrutos(MUESTRA.getId(), 0);
                    } else {
                        new MuestraDAO(getBaseContext()).editNFrutos(MUESTRA.getId(), Integer.valueOf(eTextNFrutos.getText().toString()));
                    }
                    MUESTRA = new MuestraDAO(getBaseContext()).consultarById(MUESTRA.getId());
                } else {
                    //to make sure this will call only once when keyboard is hide.
                    if (isTyping) {
                        Log.d(TAG, "teclado Cerrado2");
                        isTyping = false;
                    }
                }
            }
        });

        evaluacionDetalleVOList = new ArrayList<>();
        evaluacionDetalleVOList = new EvaluacionDetalleDAO(getBaseContext()).listByIdMuestra(MUESTRA.getId());


            Handler handler = new Handler();
            handler.post(new Runnable() {
                public void run() {

                    if(evaluacionDetalleVOList.size()>0){
                        Log.d(TAG,"LISTA  DE EVALUACIONES NO VACIA "+evaluacionDetalleVOList.size());
                        eTextNFrutos.setFocusable(false);
                        eTextNFrutos.setClickable(false);
                    }else {
                        Log.d(TAG,"LISTA  DE EVALUACIONES VACIA "+evaluacionDetalleVOList.size());
                        eTextNFrutos.setFocusable(true);
                        eTextNFrutos.setClickable(true);
                    }
                    //adapterListEvaluacionDetalle = new AdapterListEvaluacionDetalle(ActivityMuestra.this,evaluacionDetalleVOList,rViewEvaluacionesDetalle,isEditable,eTextNFrutos);
                    adapterListEvaluacionDetalle = new RViewAdapterEvaluacionDetalle_new(ActivityMuestra.this,evaluacionDetalleVOList, rViewEvaluacionesDetalle,isEditable,eTextNFrutos);
                    rViewEvaluacionesDetalle.setAdapter(adapterListEvaluacionDetalle);
                    rViewEvaluacionesDetalle.setVisibility(View.VISIBLE);

                    //setListViewHeightBasedOnChildren(rViewEvaluacionesDetalle);

                    //r.run();
                }
            });


    }

 //   int i=0;
/*
    Runnable x = new Runnable() {
        @Override
        public void run() {
            if(AdapterListEvaluacionDetalle.finalizadoEva && AdapterListEvaluacionDetalle.finalizadoCri){
                AdapterListEvaluacionDetalle.finalizadoCri=false;
                AdapterListEvaluacionDetalle.finalizadoEva=false;

            }else {
                try {
                    Thread.sleep(500);
                    Log.d(TAG,"REPITIENDO ... ");
                    Log.d(TAG,"eva: "+AdapterListEvaluacionDetalle.finalizadoEva);
                    Log.d(TAG,"cri: "+AdapterListEvaluacionDetalle.finalizadoCri);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                x.run();
            }
        }
    };


    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(AdapterListEvaluacionDetalle.finalizadoEva && AdapterListEvaluacionDetalle.finalizadoCri){
                AdapterListEvaluacionDetalle.finalizadoCri=false;
                AdapterListEvaluacionDetalle.finalizadoEva=false;
                i++;
                Log.d(TAG,"finalizo eva XX"+i);

                if(i>=1){
                    Log.d(TAG,"finalizo eva XXX");

                }else {
                    try {
                        Thread.sleep(500);
                        Log.d(TAG,"finalizo eva repitiendo : "+i);
                        x.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d(TAG,e.toString());

                    }
                }
            }else {
                try {
                    Thread.sleep(500);
                    Log.d(TAG,"REPITIENDO ... ");
                    Log.d(TAG,"eva: "+AdapterListEvaluacionDetalle.finalizadoEva);
                    Log.d(TAG,"cri: "+AdapterListEvaluacionDetalle.finalizadoCri);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r.run();
            }
        }
    };

*/
/*
    private void initSpruce() {
        recyclerView = findViewById(R.id.rViewEvaluaciones);
        spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                .sortWith(new DefaultSort(100))
                .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                        ObjectAnimator.ofFloat(recyclerView, "translationX", -recyclerView.getWidth(), 0f).setDuration(800))
                .start();
    }
    */

    @Override
    public void onResume() {
        super.onResume();

    }


//hacer q  el edit  text pieda el foco cando se preciosne  en toro lado
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("sss", "dispatchTouchEvent: ");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)ev.getRawX();
                int rawY = (int)ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void showComent(View view) {
        //final String coment = new VisitaDAO(getBaseContext()).buscarById((long) visita.getId()).getComentario();
        dialog.setContentView(R.layout.dialog_coment);
        Button btnAccept = (Button) dialog.findViewById(R.id.btnAccept);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        final EditText eTextcoment = (EditText) dialog.findViewById(R.id.eTextComent);

        eTextcoment.setText(MUESTRA.getComentario());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (isEditable) {
                    new MuestraDAO(getBaseContext()).editComentarioById(MUESTRA.getId(), eTextcoment.getText().toString());
                }*/
                dialog.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditable) {
                    new MuestraDAO(getBaseContext()).editComentarioById(MUESTRA.getId(), eTextcoment.getText().toString());
                    MUESTRA.setComentario(eTextcoment.getText().toString());
                    if(MUESTRA.getComentario() == null || MUESTRA.getComentario().isEmpty()){
                        Log.d(TAG,"COMENTARIO NULL");
                        fABtnComent.setImageResource(R.drawable.ic_mode_comment_black_24dp);
                    }else {
                        Log.d(TAG,"CON COMENTARIO NO NULL");
                        fABtnComent.setImageResource(R.drawable.ic_comment_icons_24dp);
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        // super.onSupportNavigateUp();
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {

        Log.d(TAG,""+MUESTRA.getCantidad());
        if(/*saveMuestras.size()==0 ||*/ MUESTRA.getCantidad()==0){
            //new MuesDAO(getBaseContext()).deleteById(idEvaluacion);
            new MuestraDAO(getBaseContext()).borrarById(MUESTRA.getId());
        }

        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);

        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }




    public void openPhotoGallery(View view) {
        Handler handler = new Handler();
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getBaseContext(),ActivityPhotoGallery.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    }
                }
        );

    }


    public void setListViewHeightBasedOnChildren(final ListView listView) {

        /**********************************************
         *
         *
         */
        /*
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        int re=0;
                        ListAdapter listAdapter = listView.getAdapter();
                        if (listAdapter == null) {
                            return;
                        }
                        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
                        int desiredWidth = View.MeasureSpec.makeMeasureSpec (listView.getWidth (), View.MeasureSpec.EXACTLY);
                        for (int i = 0; i < listAdapter.getCount(); i++) {

                            View listItem = listAdapter.getView(i, null, listView);
                            if(listItem != null){
                                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                                totalHeight += listItem.getMeasuredHeight();

                            }
                        }

                        ViewGroup.LayoutParams params = listView.getLayoutParams();
                        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

                        listView.setLayoutParams(params);
                        listView.requestLayout();

                    }
                }

                ,200);
*/
    }

    static int tempSelect=0;
    public void showListEvaluaciones(View view){
        if(MUESTRA.getIdTipoProceso()>0){
            if(isEditable && MUESTRA.getCantidad()>0){
                List<EvaluacionVO> evaluaciones = new EvaluacionDAO(getBaseContext()).listByIdTipoProcesoIdMuestra(MUESTRA.getIdTipoProceso(),MUESTRA.getId());
                evaluacionVOList = new ArrayList<>();
                int idCultivo = new LoginDataDAO(getApplicationContext()).verficarLogueo().getIdCultivo();
                for (EvaluacionVO evaluacionVO: evaluaciones){
                    if (idCultivo == evaluacionVO.getCultivo()){
                        evaluacionVOList.add(evaluacionVO);
                    }
                }
                Log.d(TAG,"IDmUESTRA:"+MUESTRA.getId()+" TP:"+ MUESTRA.getIdTipoProceso());
                Log.d(TAG,"TAM EVALUACION LIST = "+evaluacionVOList.size());
                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                final CharSequence[] items = new CharSequence[ evaluacionVOList.size()];
                for(int i = 0; i< evaluacionVOList.size(); i++){
                    items[i]= evaluacionVOList.get(i).getName();
                }

                dialogo.setTitle("Evaluaciones")
                        .setSingleChoiceItems(items, lastItemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                lastItemSelected=which;
                                Toast.makeText(
                                        getBaseContext(),
                                        "Seleccionaste: " + evaluacionVOList.get(which).getName(),
                                        Toast.LENGTH_SHORT)
                                        .show();

                                EvaluacionVO evaSelect = evaluacionVOList.get(which);

                                Log.d(TAG,"eva select "+evaSelect.getId()+" "+evaSelect.getName());

                                int idEva = evaSelect.getId();
                                Log.d(TAG,"insertando ->M:"+MUESTRA.getId()+" E:"+idEva+"");

                                EvaluacionDetalleVO evaluacionDetalleVO = new EvaluacionDetalleDAO(getBaseContext()).nuevoByIdMuestraIdEvaluacion(MUESTRA.getId(),idEva);

                                Log.d(TAG,"AGREGADO : EVADE{ evaDeId:"+evaluacionDetalleVO.getId()+" idMu:"+evaluacionDetalleVO.getIdMuestra() +" nameEva:"+evaluacionDetalleVO.getNameEvaluacion()+"}");

                                evaluacionDetalleVOList.add(evaluacionDetalleVO);
                                Log.d(TAG,"NUMERO DE  EVALUACIONES AGREGADAS"+evaluacionDetalleVOList.size());
                                //adapterListEvaluacionDetalle = new AdapterListEvaluacionDetalle(ActivityMuestra.this, evaluacionDetalleVOList, rViewEvaluacionesDetalle,isEditable,eTextNFrutos);
                                adapterListEvaluacionDetalle = new RViewAdapterEvaluacionDetalle_new(ActivityMuestra.this, evaluacionDetalleVOList, rViewEvaluacionesDetalle,isEditable,eTextNFrutos);

                                rViewEvaluacionesDetalle.setAdapter(adapterListEvaluacionDetalle);

                                //setListViewHeightBasedOnChildren(rViewEvaluacionesDetalle);

                                if(evaluacionDetalleVOList.size()>0){
                                    Log.d(TAG,"LISTA  DE EVALUACIONES NO VACIA "+evaluacionDetalleVOList.size());
                                    eTextNFrutos.setFocusable(false);
                                    eTextNFrutos.setClickable(false);
                                }else {
                                    Log.d(TAG,"LISTA  DE EVALUACIONES VACIA "+evaluacionDetalleVOList.size());
                                    eTextNFrutos.setFocusable(true);
                                    eTextNFrutos.setClickable(true);
                                }

//                              rViewEvaluacionesDetalle.smoothScrollToPosition(evaluacionDetalleVOList.size()-1);
                                        rViewEvaluacionesDetalle.scrollToPosition(evaluacionDetalleVOList.size()-1);


                                /*
                                final ScrollView scrollView = findViewById(R.id.ScrollEva);

                                scrollView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                    }
                                });
*/
                            }

                        });
                dialogo.show();
            }else {
                if(isEditable){
                    Toast.makeText(getBaseContext(),"Ingrese Número de Frutos",Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            Toast.makeText(getBaseContext(),"Seleccione una evaluación para visualizar  los criterios",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Toast.makeText(getBaseContext(),String.valueOf(requestCode),Toast.LENGTH_SHORT).show();
        switch (requestCode) {

            case REQUEST_CRITERIODETALLE:
                //Toast.makeText(getBaseContext(),"entro 123",Toast.LENGTH_SHORT).show();
                evaluacionDetalleVOList = new EvaluacionDetalleDAO(getBaseContext()).listByIdMuestra(MUESTRA.getId());
                //Toast.makeText(getBaseContext(),"tam"+evaluacionDetalleVOList.size(),Toast.LENGTH_SHORT).show();
                adapterListEvaluacionDetalle = new RViewAdapterEvaluacionDetalle_new(ActivityMuestra.this,evaluacionDetalleVOList, rViewEvaluacionesDetalle,isEditable,eTextNFrutos);
                rViewEvaluacionesDetalle.setAdapter(adapterListEvaluacionDetalle);
                break;
        }

    }


}
