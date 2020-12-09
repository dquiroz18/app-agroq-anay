package com.ibao.agroq.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibao.agroq.R;
import com.ibao.agroq.helpers.adapters.RViewAdapterListEvaluacionDetalleISDefecto;
import com.ibao.agroq.models.dao.EvaluacionDetalleDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.MuestraDAO;
import com.ibao.agroq.models.vo.entitiesInternal.EvaluacionDetalleVO;
import com.ibao.agroq.models.vo.entitiesInternal.MuestraVO;

import java.util.ArrayList;
import java.util.List;

public class ActivityResultRecepcion extends Activity {


    private static ImageView iViewCarita;
    private static TextView tViewResultado;
    private static TextView tViewPorDefecto;
    private static TextView tViewPorSanos;
    private static TextView tViewTipoProceso;
    private static TextView tViewNFrutos;
    private static RecyclerView rViewEvaluaciones;
    private static List<EvaluacionDetalleVO> evaluacionDetalleVOList_Defectos;
    private static MuestraVO MUESTRA;
    private static boolean isEditable;

    private static RViewAdapterListEvaluacionDetalleISDefecto adapterRView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_recepcion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tViewTipoProceso = findViewById(R.id.tViewTipoProceso);
        tViewNFrutos = findViewById(R.id.tViewNFrutos);
        rViewEvaluaciones = findViewById(R.id.rViewEvaluaciones);
        iViewCarita = findViewById(R.id.iViewCarita);
        tViewPorSanos = findViewById(R.id.tViewPorSanos);
        tViewPorDefecto = findViewById(R.id.tViewPorDefecto);
        tViewResultado = findViewById(R.id.tViewResultado);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                //  initSpruce();
            }
        };

        rViewEvaluaciones.setLayoutManager(linearLayoutManager);

        tViewTipoProceso.setText(
                new LoginDataDAO(getBaseContext()).verficarLogueo().getNameTypeProcess()
        );

        Intent intent = getIntent();
        Bundle mybundle = intent.getExtras();
        isEditable = mybundle.getBoolean(getString(R.string.isEditable));
        MUESTRA = new MuestraDAO(getBaseContext()).consultarById(mybundle.getInt(getString(R.string.idMuestra)));

        tViewNFrutos.setText(""+MUESTRA.getCantidad());

        evaluacionDetalleVOList_Defectos = new ArrayList<>();
        evaluacionDetalleVOList_Defectos = new EvaluacionDetalleDAO(getBaseContext()).listByIdMuestra(MUESTRA.getId());

        List <EvaluacionDetalleVO> listDefetos = new ArrayList<>();
        List <EvaluacionDetalleVO> listNoDefetos = new ArrayList<>();


        for(EvaluacionDetalleVO eva : evaluacionDetalleVOList_Defectos){
            if(eva.isDefecto()){
                listDefetos.add(eva);
            }else {
                if(!eva.isMatSec()){
                    listNoDefetos.add(eva);
                }
            }
        }
        evaluacionDetalleVOList_Defectos.clear();
       // evaluacionDetalleVOList_Defectos.addAll(listNoDefetos);
        evaluacionDetalleVOList_Defectos.addAll(listDefetos);

        adapterRView = new RViewAdapterListEvaluacionDetalleISDefecto(ActivityResultRecepcion.this, evaluacionDetalleVOList_Defectos,rViewEvaluaciones,isEditable);
        rViewEvaluaciones.setAdapter(adapterRView);

        float tolMin=0f;
        float tolMax=0f;

        float totalDefectos=0f;
        float totalSanos=100.0f;
        for(EvaluacionDetalleVO eva : evaluacionDetalleVOList_Defectos){
            if(eva.isDefecto()){
                totalDefectos=totalDefectos+eva.getPorcentaje();
                tolMin=tolMin+eva.getTolMin();
                tolMax=tolMax+eva.getTolMax();
            }
        }
            totalSanos=totalSanos-totalDefectos;



        totalDefectos=((int)(totalDefectos*10))/10.0f;
        totalSanos=((int)(totalSanos*10))/10.0f;

        tViewPorDefecto.setText(""+totalDefectos+"%");
        tViewPorSanos.setText(""+totalSanos+"%");


        if(tolMin >0 && tolMax>0 && tolMin!= tolMax){
            if(totalDefectos<tolMin){
                //buena
                tViewResultado.setText("¡ Buena !");
                tViewResultado.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.customGreen));
                iViewCarita.setImageDrawable(getResources().getDrawable(R.drawable.ic_sentiment_very_satisfied_green_150dp));
            }else {
                if(totalDefectos<=tolMax){
                    //regular
                    tViewResultado.setText("¡ Regular !");
                    tViewResultado.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.customOrange));
                    iViewCarita.setImageDrawable(getResources().getDrawable(R.drawable.ic_sentiment_neutral_orange_150dp));
                }else{
                    //por mejorar
                    tViewResultado.setText("¡ Por Mejorar !");
                    tViewResultado.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.redAccent700));
                    iViewCarita.setImageDrawable(getResources().getDrawable(R.drawable.ic_sentiment_very_dissatisfied_red_150dp));
                }
            }
        }else {
            tViewResultado.setText("¡ Buena  \n(Sin Defectos) !");
            tViewResultado.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.customGreen));
            iViewCarita.setImageDrawable(getResources().getDrawable(R.drawable.ic_sentiment_very_satisfied_green_150dp));
        }




    }

}
