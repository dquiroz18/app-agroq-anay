package com.ibao.agroq.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ibao.agroq.R;
import com.ibao.agroq.helpers.UploadMaster;
import com.ibao.agroq.models.dao.CriterioDetalleDAO;
import com.ibao.agroq.models.dao.DescarteDAO;
import com.ibao.agroq.models.dao.DespachoDAO;
import com.ibao.agroq.models.dao.EvaluacionDetalleDAO;
import com.ibao.agroq.models.dao.FotoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.MuestraDAO;
import com.ibao.agroq.models.dao.ProduccionDAO;
import com.ibao.agroq.models.dao.RecepcionDAO;
import com.ibao.agroq.models.vo.entitiesInternal.CriterioDetalleVO;
import com.ibao.agroq.models.vo.entitiesInternal.DescarteVO;
import com.ibao.agroq.models.vo.entitiesInternal.DespachoVO;
import com.ibao.agroq.models.vo.entitiesInternal.EvaluacionDetalleVO;
import com.ibao.agroq.models.vo.entitiesInternal.FotoVO;
import com.ibao.agroq.models.vo.entitiesInternal.MuestraVO;
import com.ibao.agroq.models.vo.entitiesInternal.ProduccionVO;
import com.ibao.agroq.models.vo.entitiesInternal.RecepcionVO;

import java.util.ArrayList;
import java.util.List;

public class ActivityUpload extends AppCompatActivity {

    private static TextView porcentaje;
    private static TextView mensaje;

    private static String TAG = ActivityUpload.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        porcentaje = (TextView) findViewById(R.id.update_tViewPorcentaje);
        mensaje = (TextView) findViewById(R.id.update_tViewMensaje);

        Toast.makeText(getBaseContext(),"Enviando Data...",
                Toast.LENGTH_SHORT).show();

        UploadMaster.status=0;

        Bundle bundle = getIntent().getExtras();
        int idProceso =0;
        try {
            idProceso = bundle.getInt(getString(R.string.idProceso),0);
            Log.d(TAG," idP:"+idProceso);
        }catch (Exception e){
            Log.d(TAG,"error->"+e.toString()+" idP:"+idProceso);
        }



        List<RecepcionVO> recepcionVOList;
        List<ProduccionVO> produccionVOList;
        List<DespachoVO> despachoVOList;
        List<DescarteVO> descarteVOList;

        List<MuestraVO> muestraVOList;
        List<EvaluacionDetalleVO> evaluacionDetalleVOList;
        List<CriterioDetalleVO> criterioDetalleVOList;

        List<FotoVO> fotoVOList;

        try {
            int idTipoProceso=new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso();
            switch (idTipoProceso){
                case 1:
                    if(idProceso==0){
                        new UploadMaster(getBaseContext()).UploadRecepcion(
                                new RecepcionDAO(getBaseContext()).listarNoEditable()
                                ,new MuestraDAO(getBaseContext()).listarAll()
                                ,new EvaluacionDetalleDAO(getBaseContext()).listarAll()
                                ,new CriterioDetalleDAO(getBaseContext()).listarAll()
                                ,new FotoDAO(getBaseContext()).listarAll()
                        );
                    }else {

                        RecepcionVO rTemp =new RecepcionDAO(getBaseContext()).buscarByIdToUpdate((long)idProceso);
                        recepcionVOList = new ArrayList<>();
                        recepcionVOList.add(rTemp);

                        muestraVOList = new MuestraDAO(getBaseContext()).listarByIdProcesoIdTipoProceso(recepcionVOList.get(0).getId(),idTipoProceso);
                        evaluacionDetalleVOList = new ArrayList<>();
                        for(MuestraVO m:muestraVOList){
                            evaluacionDetalleVOList.addAll(new EvaluacionDetalleDAO(getBaseContext()).listByIdMuestra(m.getId()));
                        }
                        criterioDetalleVOList =new ArrayList<>();
                        for(EvaluacionDetalleVO ed:evaluacionDetalleVOList){
                            criterioDetalleVOList.addAll(new CriterioDetalleDAO(getBaseContext()).listByIdEvaluacionDetalle(ed.getId()));
                        }
                        fotoVOList =new ArrayList<>();
                        for(CriterioDetalleVO ed:criterioDetalleVOList){
                            fotoVOList.addAll(new FotoDAO(getBaseContext()).listarByIdCriterioDetalle(ed.getId()));
                        }
                        new UploadMaster(getBaseContext()).UploadRecepcion(
                                recepcionVOList
                                ,muestraVOList
                                ,evaluacionDetalleVOList
                                ,criterioDetalleVOList
                                ,fotoVOList
                        );
                    }

                    break;
                case 2:
                    if(idProceso==0) {
                        new UploadMaster(getBaseContext()).UploadProduccion(
                                new ProduccionDAO(getBaseContext()).listarNoEditable()
                                , new MuestraDAO(getBaseContext()).listarAll()
                                , new EvaluacionDetalleDAO(getBaseContext()).listarAll()
                                , new CriterioDetalleDAO(getBaseContext()).listarAll()
                                , new FotoDAO(getBaseContext()).listarAll()
                        );
                    }else {
                        ProduccionVO pTemp =new ProduccionDAO(getBaseContext()).buscarByIdToUpload((long)idProceso);
                        produccionVOList = new ArrayList<>();
                        produccionVOList.add(pTemp);

                        muestraVOList = new MuestraDAO(getBaseContext()).listarByIdProcesoIdTipoProceso(produccionVOList.get(0).getId(),idTipoProceso);
                        evaluacionDetalleVOList = new ArrayList<>();
                        for(MuestraVO m:muestraVOList){
                            evaluacionDetalleVOList.addAll(new EvaluacionDetalleDAO(getBaseContext()).listByIdMuestra(m.getId()));
                        }
                        criterioDetalleVOList =new ArrayList<>();
                        for(EvaluacionDetalleVO ed:evaluacionDetalleVOList){
                            criterioDetalleVOList.addAll(new CriterioDetalleDAO(getBaseContext()).listByIdEvaluacionDetalle(ed.getId()));
                        }
                        fotoVOList =new ArrayList<>();
                        for(CriterioDetalleVO ed:criterioDetalleVOList){
                            fotoVOList.addAll(new FotoDAO(getBaseContext()).listarByIdCriterioDetalle(ed.getId()));
                        }
                        new UploadMaster(getBaseContext()).UploadProduccion(
                                produccionVOList
                                ,muestraVOList
                                ,evaluacionDetalleVOList
                                ,criterioDetalleVOList
                                ,fotoVOList
                        );
                    }
                    break;
                case 3:
                    if(idProceso==0) {
                        new UploadMaster(getBaseContext()).UploadDespacho(
                                new DespachoDAO(getBaseContext()).listarNoEditable()
                                , new MuestraDAO(getBaseContext()).listarAll()
                                , new EvaluacionDetalleDAO(getBaseContext()).listarAll()
                                , new CriterioDetalleDAO(getBaseContext()).listarAll()
                                , new FotoDAO(getBaseContext()).listarAll()
                        );
                    }else {
                        DespachoVO despTemp =new DespachoDAO(getBaseContext()).buscarById((long)idProceso);
                        despachoVOList = new ArrayList<>();
                        despachoVOList.add(despTemp);

                        muestraVOList = new MuestraDAO(getBaseContext()).listarByIdProcesoIdTipoProceso(despachoVOList.get(0).getId(),idTipoProceso);
                        Log.d(TAG,"muestraList Size :"+muestraVOList.size());
                        evaluacionDetalleVOList = new ArrayList<>();
                        for(MuestraVO m:muestraVOList){
                            evaluacionDetalleVOList.addAll(new EvaluacionDetalleDAO(getBaseContext()).listByIdMuestra(m.getId()));
                        }
                        Log.d(TAG,"evaDe Size :"+evaluacionDetalleVOList.size());
                        criterioDetalleVOList =new ArrayList<>();
                        for(EvaluacionDetalleVO ed:evaluacionDetalleVOList){
                            criterioDetalleVOList.addAll(new CriterioDetalleDAO(getBaseContext()).listByIdEvaluacionDetalle(ed.getId()));
                        }
                        Log.d(TAG,"criDe Size :"+criterioDetalleVOList.size());

                        fotoVOList =new ArrayList<>();
                        for(CriterioDetalleVO ed:criterioDetalleVOList){
                            fotoVOList.addAll(new FotoDAO(getBaseContext()).listarByIdCriterioDetalle(ed.getId()));
                        }
                        Log.d(TAG,"foto Size :"+fotoVOList.size());
                        new UploadMaster(getBaseContext()).UploadDespacho(
                                despachoVOList
                                ,muestraVOList
                                ,evaluacionDetalleVOList
                                ,criterioDetalleVOList
                                ,fotoVOList
                        );

                    }
                    break;
                case 4:
                    if(idProceso==0) {
                        new UploadMaster(getBaseContext()).UploadDescarte(
                                new DescarteDAO(getBaseContext()).listarNoEditable()
                                , new MuestraDAO(getBaseContext()).listarAll()
                                , new EvaluacionDetalleDAO(getBaseContext()).listarAll()
                                , new CriterioDetalleDAO(getBaseContext()).listarAll()
                                , new FotoDAO(getBaseContext()).listarAll()
                        );
                    }else {
                        DescarteVO descTemp =new DescarteDAO(getBaseContext()).buscarByIdToUpload((long)idProceso);
                        descarteVOList = new ArrayList<>();
                        descarteVOList.add(descTemp);

                        muestraVOList = new MuestraDAO(getBaseContext()).listarByIdProcesoIdTipoProceso(descarteVOList.get(0).getId(),idTipoProceso);
                        evaluacionDetalleVOList = new ArrayList<>();
                        for(MuestraVO m:muestraVOList){
                            evaluacionDetalleVOList.addAll(new EvaluacionDetalleDAO(getBaseContext()).listByIdMuestra(m.getId()));
                        }
                        criterioDetalleVOList =new ArrayList<>();
                        for(EvaluacionDetalleVO ed:evaluacionDetalleVOList){
                            criterioDetalleVOList.addAll(new CriterioDetalleDAO(getBaseContext()).listByIdEvaluacionDetalle(ed.getId()));
                        }
                        fotoVOList =new ArrayList<>();
                        for(CriterioDetalleVO ed:criterioDetalleVOList){
                            fotoVOList.addAll(new FotoDAO(getBaseContext()).listarByIdCriterioDetalle(ed.getId()));
                        }
                        new UploadMaster(getBaseContext()).UploadDescarte(
                                descarteVOList
                                ,muestraVOList
                                ,evaluacionDetalleVOList
                                ,criterioDetalleVOList
                                ,fotoVOList
                        );

                    }
                    break;
            }
        }catch (Exception e){
            Log.d(TAG,"Error->"+e.toString());
        }

        r.run();
    }


    int i=0;

    Runnable r = new Runnable() {
        @Override
        public void run() {
        i++;
        Log.d("statusUpload",""+UploadMaster.status+" "+i);
                if(UploadMaster.status==1){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            mensaje.setText("Encontrando servidor");
                            porcentaje.setText("0%");
                            Log.d("porcentaje ",porcentaje.getText().toString());
                            r.run();
                        }
                    },1);

                    Log.d("qmierda",""+UploadMaster.status+" "+i);
                }else {

                    Log.d("qmierda",""+i);
                    if (UploadMaster.status == 3) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                mensaje.setText("Subiendo Archivos");
                                porcentaje.setText("100%");
                                Log.d("porcentaje ",porcentaje.getText().toString());

                            }
                        },500);
                        openMain();
                    }else{

                        if(UploadMaster.status == 2){
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    mensaje.setText("Subiendo Archivos");
                                    porcentaje.setText("50%");
                                    Log.d("porcentaje ",porcentaje.getText().toString());
                                    r.run();
                                }
                            },500);
                        }else{
                            if(UploadMaster.status == -1){
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        mensaje.setText("Error de Conversion");
                                        porcentaje.setText("-1%");
                                        Log.d("porcentaje ",porcentaje.getText().toString());

                                    }
                                },500);
                            }else{
                                if(UploadMaster.status == -2) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            mensaje.setText("Error de Servidor");
                                            porcentaje.setText("-2%");
                                            Log.d("porcentaje ", porcentaje.getText().toString());

                                        }
                                    }, 500);
                                }else {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            mensaje.setText("Error de Fatal");
                                            porcentaje.setText("-3%");
                                            Log.d("porcentaje ", porcentaje.getText().toString());

                                        }
                                    }, 500);
                                }
                            }
                            openMain();
                            finish();
                        }
                        Log.d("tamano",""+i);
                    }

                }
            }

    };

    void openMain(){
        /*try {
            Thread.sleep(323);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Intent intent = new Intent(this,ActivityMain.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        onBackPressed();
    }



}

