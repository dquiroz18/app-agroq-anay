package com.ibao.agroq.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.agroq.R;
import com.ibao.agroq.models.dao.DescarteDAO;
import com.ibao.agroq.models.dao.DespachoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.MuestraDAO;
import com.ibao.agroq.models.dao.ProduccionDAO;
import com.ibao.agroq.models.dao.RecepcionDAO;
import com.ibao.agroq.models.vo.entitiesInternal.DescarteVO;
import com.ibao.agroq.models.vo.entitiesInternal.DespachoVO;
import com.ibao.agroq.models.vo.entitiesInternal.MuestraVO;
import com.ibao.agroq.models.vo.entitiesInternal.ProcesoVO;
import com.ibao.agroq.models.vo.entitiesInternal.ProduccionVO;
import com.ibao.agroq.models.vo.entitiesInternal.RecepcionVO;

import java.util.ArrayList;
import java.util.List;

public class ActivityProceso extends AppCompatActivity {

    String TAG = ActivityProceso.class.getSimpleName();

    //REQUEST
    static final public int REQUEST_BASICS_RECEPCION_DATA = 51;  // The request code
    static final public int REQUEST_BASICS_PRODUCCION_DATA = 52;  // The request code
    static final public int REQUEST_BASICS_DESPACHO_DATA = 53;
    static final public int REQUEST_BASICS_DESCARTE_DATA = 54;
    static final public int REQUEST_EDIT_MUESTRA=2;
    static final public int REQUEST_EDIT_MUESTRADESPACHO=3;


    private Dialog dialogClose;
    static final String REQUEST_ID = "id";

    private static BaseAdapter baseAdapter;

    //labels

    //despacho
    static TextView tViewFechaCarga;
    static TextView tViewFechaLlegada;
    static TextView tViewFechaSalida;

    static TextView tViewOrigen;
    static TextView tViewCliente;
    static TextView tViewControlador;
    static TextView tViewNPallets;
    static TextView tViewNCajasPallet;
    static TextView tViewTotalCajas;
    static TextView tViewPesoTotal;
    //fin despacho

    static TextView tViewFecha;
    static TextView tViewPlanta;
    static TextView tViewNOrdenProceso;
    static TextView tViewTipoEnvase;
    static TextView tViewNGuia;
    static TextView tViewProductor;
    static TextView tViewVariedad;
    static TextView tViewNUnidades;
    static TextView tViewKilos;
    static TextView tViewDestino;
    static TextView tViewCalibre;
    static TextView tViewCategoria;


    static ListView lViewMuestras;

    static Boolean ISEDITABLE=true;
    static Object PROCESO;
    //fin labels


    //argunemntos de actividad anterior

    private  List<MuestraVO> muestraVOList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepcion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String tittle = getResources().getString(R.string.registro);
        tittle += " "+new LoginDataDAO(getBaseContext()).verficarLogueo().getNameTypeProcess();
        setTitle(tittle);
        dialogClose = new Dialog(this);

        ConstraintLayout root = (ConstraintLayout) findViewById(R.id.clMuestraData);
        View child;
        switch (new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()){

            case 1:
                child= getLayoutInflater().inflate(R.layout.showdatos_recepcion, null);
                root.addView(child);
                break;
            case 2:
                child = getLayoutInflater().inflate(R.layout.showdatos_produccion, null);
                root.addView(child);
                break;
            case 3:
                child = getLayoutInflater().inflate(R.layout.showdatos_despacho, null);
                root.addView(child);
                break;
            case 4:
                child = getLayoutInflater().inflate(R.layout.showdatos_descarte, null);
                root.addView(child);
                break;
        }



        lViewMuestras = (ListView)findViewById(R.id.lViewEvaluaciones);
        dialogClose = new Dialog(this);

        

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String def = getString(R.string.tres_puntos);

        ISEDITABLE= b.getBoolean(getString(R.string.isEditable),false);

        
        switch (new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()){
            case 1:
                Log.d(TAG,"MOSTRANDO DATOS RECEPCION");
                tViewFecha = (TextView) findViewById(R.id.tViewFechaRecepcion);
                tViewPlanta = (TextView) findViewById(R.id.tViewNFrutos);
                tViewNOrdenProceso = (TextView) findViewById(R.id.tViewNOrdenProceso);
                tViewNGuia = (TextView) findViewById(R.id.tViewNGuia);
                tViewProductor = (TextView) findViewById(R.id.tViewProductor);
                tViewVariedad = (TextView) findViewById(R.id.tViewCultivo);
                tViewNUnidades = (TextView) findViewById(R.id.tViewUnidades);
                tViewTipoEnvase = (TextView) findViewById(R.id.tViewEnvase);
                tViewKilos = (TextView) findViewById(R.id.tViewPesoTotal);

                Log.d(TAG,"bucando id R:"+b.getInt(getString(R.string.idProceso)));
                PROCESO = new RecepcionDAO(getBaseContext()).buscarById((long)b.getInt(getString(R.string.idProceso)));

                tViewTipoEnvase.setText((((RecepcionVO) PROCESO).getNameEnvase()==null?def:((RecepcionVO) PROCESO).getNameEnvase()));
                tViewFecha.setText( ((RecepcionVO) PROCESO).getFechaHora()==null?def: ((RecepcionVO) PROCESO).getFechaHora());
                tViewPlanta.setText(((RecepcionVO) PROCESO).getNamePlanta()==null?def: ((RecepcionVO) PROCESO).getNamePlanta());
                tViewNOrdenProceso.setText(((RecepcionVO) PROCESO).getnOrdenProceso()==null?def: ((RecepcionVO) PROCESO).getnOrdenProceso());
                tViewNGuia.setText(((RecepcionVO) PROCESO).getnOrdenGuia()==null?def: ((RecepcionVO) PROCESO).getnOrdenGuia());
                tViewProductor.setText(((RecepcionVO) PROCESO).getNameEmpresa()==null?def: ((RecepcionVO) PROCESO).getNameEmpresa());
                tViewVariedad.setText((((RecepcionVO) PROCESO).getNameCultivo()==null?def: ((RecepcionVO) PROCESO).getNameCultivo())+(((RecepcionVO) PROCESO).getNameVariedad()==null?def:" - "+ ((RecepcionVO) PROCESO).getNameVariedad()));
                tViewNUnidades.setText((((RecepcionVO) PROCESO).getUnidadesRecepcion()==null?def:((RecepcionVO) PROCESO).getUnidadesRecepcion()));
                tViewKilos.setText(((RecepcionVO) PROCESO).getKilosRecepcion()==null?def: ((RecepcionVO) PROCESO).getKilosRecepcion());
            break;

            case 2:
                Log.d(TAG,"MOSTRANDO DATOS PRODUCCCION");
                tViewFecha = (TextView) findViewById(R.id.tViewFechaRecepcion);
                tViewPlanta = (TextView) findViewById(R.id.tViewNFrutos);
                tViewNOrdenProceso = (TextView) findViewById(R.id.tViewNOrdenProceso);
                tViewProductor = (TextView) findViewById(R.id.tViewProductor);
                tViewVariedad = (TextView) findViewById(R.id.tViewCultivo);
                tViewNUnidades = (TextView) findViewById(R.id.tViewUnidades);
                tViewKilos = (TextView) findViewById(R.id.tViewPesoTotal);
                tViewTipoEnvase = (TextView)findViewById(R.id.tViewControlador);
                tViewDestino = (TextView) findViewById(R.id.tViewOrigen);
                tViewCalibre = (TextView) findViewById(R.id.tViewNCajasPallet);
                tViewCategoria = (TextView) findViewById(R.id.tViewNPallets);

                PROCESO = new ProduccionDAO(getBaseContext()).buscarById((long)b.getInt(getString(R.string.idProceso)));
                tViewFecha.setText( ((ProduccionVO) PROCESO).getFechaHora()==null?def: ((ProduccionVO) PROCESO).getFechaHora());
                tViewPlanta.setText(((ProduccionVO) PROCESO).getNamePlanta()==null?def: ((ProduccionVO) PROCESO).getNamePlanta());
                tViewNOrdenProceso.setText(((ProduccionVO) PROCESO).getnOrdenProceso()==null?def: ((ProduccionVO) PROCESO).getnOrdenProceso());
                tViewProductor.setText(((ProduccionVO) PROCESO).getNameEmpresa()==null?def: ((ProduccionVO) PROCESO).getNameEmpresa());
                tViewVariedad.setText((((ProduccionVO) PROCESO).getNameCultivo()==null?def: ((ProduccionVO) PROCESO).getNameCultivo())+" - "+(((ProduccionVO) PROCESO).getNameVariedad()==null?def: ((ProduccionVO) PROCESO).getNameVariedad()));
                tViewNUnidades.setText(((ProduccionVO) PROCESO).getUnidades()==null?def: ((ProduccionVO) PROCESO).getUnidades());
                tViewKilos.setText(((ProduccionVO) PROCESO).getKilos()==null?def: ((ProduccionVO) PROCESO).getKilos());

                Log.d(TAG,"calibre = "+((ProduccionVO) PROCESO).getIdCalibre()+" "+((ProduccionVO) PROCESO).getNameCalibre());
                tViewDestino.setText(((ProduccionVO) PROCESO).getNameDestino()==null?def: ((ProduccionVO) PROCESO).getNameDestino());
                tViewCalibre.setText(((ProduccionVO) PROCESO).getNameCalibre()==null?def: ((ProduccionVO) PROCESO).getNameCalibre());
                tViewCategoria.setText(((ProduccionVO) PROCESO).getNameCategoria()==null?def: ((ProduccionVO) PROCESO).getNameCategoria());
                tViewTipoEnvase.setText(((ProduccionVO) PROCESO).getNameEnvase()==null?def: ((ProduccionVO) PROCESO).getNameEnvase());

                break;

            case 3:
                Log.d(TAG,"MOSTRANDO DATOS RECEPCION");

                tViewFecha = (TextView) findViewById(R.id.tViewFechaRecepcion);
                tViewPlanta = (TextView) findViewById(R.id.tViewNFrutos);
                tViewVariedad = (TextView) findViewById(R.id.tViewCultivo);

                tViewFechaCarga = (TextView) findViewById(R.id.tViewFechaCarga);
                tViewFechaLlegada = (TextView) findViewById(R.id.tViewFechaLlegada);
                tViewFechaSalida = (TextView) findViewById(R.id.tViewFechaSalida);

                tViewOrigen = (TextView) findViewById(R.id.tViewOrigen);
                tViewCliente = (TextView) findViewById(R.id.tViewCliente);
                tViewControlador = (TextView) findViewById(R.id.tViewControlador);
                tViewNPallets = (TextView) findViewById(R.id.tViewNPallets);
                tViewNCajasPallet = (TextView) findViewById(R.id.tViewNCajasPallet);
                tViewTotalCajas = (TextView) findViewById(R.id.tViewTotalCajas);
                tViewPesoTotal = (TextView) findViewById(R.id.tViewPesoTotal);


                PROCESO = new DespachoDAO(getBaseContext()).buscarById((long)b.getInt(getString(R.string.idProceso)));

                tViewFecha.setText((((DespachoVO) PROCESO).getFechaInspeccion()==null?def: ((DespachoVO) PROCESO).getFechaInspeccion()));
                tViewPlanta.setText(((DespachoVO) PROCESO).getNamePlanta()==null?def: ((DespachoVO) PROCESO).getNamePlanta());
                tViewVariedad.setText((((DespachoVO) PROCESO).getNameCultivo()==null?def: ((DespachoVO) PROCESO).getNameCultivo()));

                tViewFechaCarga.setText( ((DespachoVO) PROCESO).getFechaCarga()==null?def: ((DespachoVO) PROCESO).getFechaCarga());
                tViewFechaLlegada.setText( ((DespachoVO) PROCESO).getFechaLlegada()==null?def: ((DespachoVO) PROCESO).getFechaLlegada());
                tViewFechaSalida.setText( ((DespachoVO) PROCESO).getFechaSalida()==null?def: ((DespachoVO) PROCESO).getFechaSalida());

                tViewOrigen.setText( ((DespachoVO) PROCESO).getOrigen()==null?def: ((DespachoVO) PROCESO).getOrigen());
                tViewCliente.setText( ((DespachoVO) PROCESO).getCliente()==null?def: ((DespachoVO) PROCESO).getCliente());

                tViewControlador.setText( ((DespachoVO) PROCESO).getNameControlador()==null?def: ((DespachoVO) PROCESO).getNameControlador());
                tViewNPallets.setText(""+ ((DespachoVO) PROCESO).getnPallets());
                tViewNCajasPallet.setText(""+ ((DespachoVO) PROCESO).getnCajasPallet());
                tViewTotalCajas.setText(""+ ((DespachoVO) PROCESO).getnPallets()*((DespachoVO) PROCESO).getnCajasPallet() );
                tViewPesoTotal.setText( ""+((DespachoVO) PROCESO).getnPallets()*((DespachoVO) PROCESO).getnCajasPallet() * ((DespachoVO) PROCESO).getPesoCaja() );

                break;

            case 4:
                Log.d(TAG,"MOSTRANDO DATOS PRODUCCCION");
                tViewFecha = (TextView) findViewById(R.id.tViewFechaRecepcion);
                tViewPlanta = (TextView) findViewById(R.id.tViewNFrutos);
                tViewNOrdenProceso = (TextView) findViewById(R.id.tViewNOrdenProceso);
                tViewProductor = (TextView) findViewById(R.id.tViewProductor);
                tViewVariedad = (TextView) findViewById(R.id.tViewCultivo);
                tViewNUnidades = (TextView) findViewById(R.id.tViewUnidades);
                tViewKilos = (TextView) findViewById(R.id.tViewPesoTotal);
                tViewTipoEnvase = (TextView) findViewById(R.id.tViewControlador);

                PROCESO = new DescarteDAO(getBaseContext()).buscarById((long)b.getInt(getString(R.string.idProceso)));
                tViewFecha.setText( ((DescarteVO) PROCESO).getFechaHora()==null?def: ((DescarteVO) PROCESO).getFechaHora());
                tViewPlanta.setText(((DescarteVO) PROCESO).getNamePlanta()==null?def: ((DescarteVO) PROCESO).getNamePlanta());
                tViewNOrdenProceso.setText(((DescarteVO) PROCESO).getnOrdenProceso()==null?def: ((DescarteVO) PROCESO).getnOrdenProceso());
                tViewProductor.setText(((DescarteVO) PROCESO).getNameEmpresa()==null?def: ((DescarteVO) PROCESO).getNameEmpresa());
                tViewVariedad.setText((((DescarteVO) PROCESO).getNameCultivo()==null?def: ((DescarteVO) PROCESO).getNameCultivo())+" - "+(((DescarteVO) PROCESO).getNameVariedad()==null?def: ((DescarteVO) PROCESO).getNameVariedad()));
                tViewNUnidades.setText(((DescarteVO) PROCESO).getUnidades()==null?def: ((DescarteVO) PROCESO).getUnidades());
                tViewKilos.setText(((DescarteVO) PROCESO).getKilos()==null?def: ((DescarteVO) PROCESO).getKilos());

                tViewTipoEnvase.setText(((DescarteVO) PROCESO).getNameEnvase()==null?def: ((DescarteVO) PROCESO).getNameEnvase());
                break;
        }

        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return muestraVOList.size();
            }

            @Override
            public Object getItem(int position) {
                return muestraVOList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return muestraVOList.get(position).getId();
            }


            @SuppressLint("RestrictedApi")
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = convertView;
                final LayoutInflater inflater;
                inflater = LayoutInflater.from(getBaseContext());
                v = inflater.inflate(R.layout.item_view_muestras,null);

                TextView tViewFecha = v.findViewById(R.id.tViewFechaRecepcion);
                TextView tViewNFrutos = v.findViewById(R.id.tViewNFrutos);
                TextView tViewCalificacion = v.findViewById(R.id.tViewNOrdenProceso);
                final FloatingActionButton fAButtonDelte = v.findViewById(R.id.fAButtonDelete);
                FloatingActionButton faButtonResult = v.findViewById(R.id.fAButtonResult);
                final ConstraintLayout cLayoutTotal = v.findViewById(R.id.cLayoutComplete);

                if(new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()==3){
                    TextView tView_FechaHora =v.findViewById(R.id.tView_FechaHora);
                    tView_FechaHora.setText("Fecha Inspección");

                    TextView tView_NFrutos =v.findViewById(R.id.tView_NFrutos);
                    tView_NFrutos.setVisibility(View.INVISIBLE);

                    TextView tViewTP2 =v.findViewById(R.id.tViewTP2);
                    tViewTP2.setVisibility(View.INVISIBLE);

                    tViewNFrutos.setVisibility(View.INVISIBLE);

                    faButtonResult.setVisibility(View.INVISIBLE);
                }



                if(!ISEDITABLE){
                    fAButtonDelte.setVisibility(View.INVISIBLE);
                }

                tViewNFrutos.setText(""+muestraVOList.get(position).getCantidad());

                switch (muestraVOList.get(position).getCalificacion()){
                    case 1:
                        tViewCalificacion.setText("¡ Buena !");
                        break;
                    case 2:
                        tViewCalificacion.setText("¡ Regular !");
                        break;
                    case 3:
                        tViewCalificacion.setText("¡ Por Mejorar !");
                        break;
                    case 0:
                        tViewCalificacion.setText("¡ Buena (sin Defectos) !");
                        if(new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()==3){
                            tViewCalificacion.setText("¡ Sin Calificación !");
                        }
                        break;
                }

                faButtonResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        MuestraVO muestraVO =muestraVOList.get(position);
                        Intent intent = new Intent(getBaseContext(), ActivityResultRecepcion.class);
                        Bundle mybundle = new Bundle();
                        //visita = new VisitaDAO(ctx).getEditing();
                        mybundle.putInt(getString(R.string.idMuestra),muestraVO.getId());
                        mybundle.putBoolean(getString(R.string.isEditable),ISEDITABLE);
                        intent.putExtras(mybundle);
                        //ActivityEvaluacion.setLastItemSelected(0);
                        startActivity(intent);

                    }
                });



                v.setClickable(true);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MuestraVO muestraVO =muestraVOList.get(position);

                        Intent intent = new Intent(getBaseContext(),ActivityMuestra.class);
                        if(new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()==3){
                            intent = new Intent(getBaseContext(),ActivityMuestraDespacho.class);
                        }
                        Bundle mybundle = new Bundle();
                        //visita = new VisitaDAO(ctx).getEditing();
                        mybundle.putInt(getString(R.string.idMuestra),muestraVO.getId());
                        mybundle.putBoolean(getString(R.string.isEditable),ISEDITABLE);
                        intent.putExtras(mybundle);



                        //ActivityEvaluacion.setLastItemSelected(0);
                        startActivityForResult(intent,REQUEST_EDIT_MUESTRA);

                    }
                });



                fAButtonDelte.setClickable(true);

                fAButtonDelte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialogClose.setContentView(R.layout.dialog_danger);
                        Button btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close);
                        Button btnDialogAcept = (Button) dialogClose.findViewById(R.id.buton_acept);
                        ImageView iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);
                        TextView mensaje = (TextView) dialogClose.findViewById(R.id.tViewRecomendacion);

                        try{

                        mensaje.setText("Esta a punto de eliminar una Muestra.\n¿DESEA CONTINUAR?");
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

                        btnDialogAcept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final Animation animClose =
                                        android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.zoom_back_out_item);

                                MuestraDAO evaluacionDAO = new MuestraDAO(getBaseContext());
                                boolean x=evaluacionDAO.borrarById(muestraVOList.get(position).getId())>0;

                                if(!x){
                                    Toast.makeText(getBaseContext(),getString(R.string.error_al_eliminar),Toast.LENGTH_SHORT).show();
                                }else{

                                    muestraVOList.remove(position);
                                    cLayoutTotal.startAnimation(animClose);
                                    fAButtonDelte.setClickable(false);
                                    Handler handler = new Handler();
                                    handler.postDelayed(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    baseAdapter.notifyDataSetChanged();

                                                }
                                            },200
                                    );
                                }
                                dialogClose.dismiss();
                            }
                        });

                        dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogClose.show();

                    }catch (Exception e){
                        Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
                        Log.d(TAG,e.toString());
                    }
                    }
                });

                tViewFecha.setText(muestraVOList.get(position).getFechaHora());



                return v;
            }

        };


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                MuestraDAO muestraDAO = new MuestraDAO(getBaseContext());
                muestraVOList.clear();
                switch (new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()){
                    case 1:
                        muestraVOList.addAll( muestraDAO.listarByIdProcesoIdTipoProceso(
                                ((RecepcionVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()
                        ));

                        Log.d(TAG,"numero de muestras: "+muestraVOList.size());
                        Log.d(TAG,"numero de muestras: "+muestraDAO.listarByIdProcesoIdTipoProceso(
                                ((RecepcionVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()).size());
                        Log.d(TAG, "MUESTRAS IDP:"+ ((RecepcionVO) PROCESO).getId()+" idTP"+
                                (new LoginDataDAO(getBaseContext()).verficarLogueo()).getIdTipoProceso()
                        );
                        break;
                    case 2:
                        muestraVOList.addAll( muestraDAO.listarByIdProcesoIdTipoProceso(
                                ((ProduccionVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()
                        ));

                        Log.d(TAG,"numero de muestras: "+muestraVOList.size());
                        Log.d(TAG,"numero de muestras: "+muestraDAO.listarByIdProcesoIdTipoProceso(
                                ((ProduccionVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()).size());
                        Log.d(TAG, "MUESTRAS IDP:"+ ((ProduccionVO) PROCESO).getId()+" idTP"+
                                (new LoginDataDAO(getBaseContext()).verficarLogueo()).getIdTipoProceso()
                        );
                        break;
                    case 3:
                        muestraVOList.addAll( muestraDAO.listarByIdProcesoIdTipoProceso(
                                ((DespachoVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()
                        ));

                        Log.d(TAG,"numero de muestras: "+muestraVOList.size());
                        Log.d(TAG,"numero de muestras: "+muestraDAO.listarByIdProcesoIdTipoProceso(
                                ((DespachoVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()).size());
                        Log.d(TAG, "MUESTRAS IDP:"+ ((DespachoVO) PROCESO).getId()+" idTP"+
                                (new LoginDataDAO(getBaseContext()).verficarLogueo()).getIdTipoProceso()
                        );
                        break;
                    case 4:

                        Log.d(TAG," "+((DescarteVO) PROCESO).getId());

                        muestraVOList.addAll( muestraDAO.listarByIdProcesoIdTipoProceso(
                                ((DescarteVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()
                        ));

                        Log.d(TAG,"numero de muestras: "+muestraVOList.size());
                        Log.d(TAG,"numero de muestras: "+muestraDAO.listarByIdProcesoIdTipoProceso(
                                ((DescarteVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()).size());
                        Log.d(TAG, "MUESTRAS IDP:"+ ((DescarteVO) PROCESO).getId()+" idTP"+
                                (new LoginDataDAO(getBaseContext()).verficarLogueo()).getIdTipoProceso()
                        );
                        break;
                }

                lViewMuestras.setAdapter(baseAdapter);
                setListViewHeightBasedOnChildren(lViewMuestras);
            }
        }, 100);

    }


    public void openMuestra(View view){
        Intent intent = new Intent(this,ActivityMuestra.class);

        /**falta insertar primero en ta tabla  evaluaciones
         con el id de la visita
         **/
switch (new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()){
    case 1:
        if(((RecepcionVO) PROCESO).getIdFundo()>0 && ((RecepcionVO) PROCESO).getIdVariedad()>0){

            Log.d(TAG,"INSERTANDO M: idP:"+ ((RecepcionVO) PROCESO).getId()+" idTP:"+new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());
            MuestraVO muestraVO = new MuestraDAO(getBaseContext()).nuevoByIdProcesoIdtipoProceso(((RecepcionVO) PROCESO).getId(), new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());

            Bundle mybundle = new Bundle();
            mybundle.putInt(getString(R.string.idMuestra),muestraVO.getId());
            mybundle.putBoolean(getString(R.string.isEditable),ISEDITABLE);

            intent.putExtras(mybundle);
            //ActivityEvaluacion.setLastItemSelected(0);
            startActivityForResult(intent,REQUEST_EDIT_MUESTRA);

            FloatingActionButton fButtonAddMuestra =
                    (FloatingActionButton) findViewById(R.id.fButtonAddEvalaucion);
            fButtonAddMuestra.setClickable(false);

        }else{
            Toast.makeText(getBaseContext(),"Primero configura tus Datos Basicos",Toast.LENGTH_LONG).show();
        }
        break;
    case 2:
        if(((ProduccionVO) PROCESO).getIdFundo()>0 && ((ProduccionVO) PROCESO).getIdVariedad()>0){

            Log.d(TAG,"INSERTANDO M: idP:"+ ((ProduccionVO) PROCESO).getId()+" idTP:"+new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());
            MuestraVO muestraVO = new MuestraDAO(getBaseContext()).nuevoByIdProcesoIdtipoProceso(((ProduccionVO) PROCESO).getId(), new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());

            Bundle mybundle = new Bundle();
            mybundle.putInt(getString(R.string.idMuestra),muestraVO.getId());
            mybundle.putBoolean(getString(R.string.isEditable),ISEDITABLE);

            intent.putExtras(mybundle);
            //ActivityEvaluacion.setLastItemSelected(0);
            startActivityForResult(intent,REQUEST_EDIT_MUESTRA);

            FloatingActionButton fButtonAddMuestra =
                    (FloatingActionButton) findViewById(R.id.fButtonAddEvalaucion);
            fButtonAddMuestra.setClickable(false);

        }else{
            Toast.makeText(getBaseContext(),"Primero configura tus Datos Basicos",Toast.LENGTH_LONG).show();
        }
        break;
    case 3:
        if(true){
            intent = new Intent(this,ActivityMuestraDespacho.class);
            Log.d(TAG,"INSERTANDO M: idP:"+ ((DespachoVO) PROCESO).getId()+" idTP:"+new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());
            MuestraVO muestraVO = new MuestraDAO(getBaseContext()).nuevoByIdProcesoIdtipoProceso(((DespachoVO) PROCESO).getId(), new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());

            Bundle mybundle = new Bundle();
            mybundle.putInt(getString(R.string.idMuestra),muestraVO.getId());
            mybundle.putBoolean(getString(R.string.isEditable),ISEDITABLE);

            intent.putExtras(mybundle);
            //ActivityEvaluacion.setLastItemSelected(0);
            startActivityForResult(intent,REQUEST_EDIT_MUESTRADESPACHO);

            FloatingActionButton fButtonAddMuestra =
                    (FloatingActionButton) findViewById(R.id.fButtonAddEvalaucion);
            fButtonAddMuestra.setClickable(false);
        }

        break;
    case 4:
        if(((DescarteVO) PROCESO).getIdFundo()>0 && ((DescarteVO) PROCESO).getIdVariedad()>0){

            Log.d(TAG,"INSERTANDO M: idP:"+ ((DescarteVO) PROCESO).getId()+" idTP:"+new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());
            MuestraVO muestraVO = new MuestraDAO(getBaseContext()).nuevoByIdProcesoIdtipoProceso(((DescarteVO) PROCESO).getId(), new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());

            Bundle mybundle = new Bundle();
            mybundle.putInt(getString(R.string.idMuestra),muestraVO.getId());
            mybundle.putBoolean(getString(R.string.isEditable),ISEDITABLE);

            intent.putExtras(mybundle);
            //ActivityEvaluacion.setLastItemSelected(0);
            startActivityForResult(intent,REQUEST_EDIT_MUESTRA);

            FloatingActionButton fButtonAddMuestra =
                    (FloatingActionButton) findViewById(R.id.fButtonAddEvalaucion);
            fButtonAddMuestra.setClickable(false);

        }else{
            Toast.makeText(getBaseContext(),"Primero configura tus Datos Basicos",Toast.LENGTH_LONG).show();
        }
        break;
}

    }


    public void editBasics(View view){


        Intent intent;
        Bundle mybundle;
        FloatingActionButton editBasics;

        int TP =new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso();

        if(ISEDITABLE){
            /*if(!muestraVOList.isEmpty() && (TP==1 ||TP==2 || TP==4)) {
                Toast.makeText(getBaseContext(), "No puede editar, ya tiene Muestras agregadas",Toast.LENGTH_LONG).show();
            }else {
            */
                switch (TP){
                    case 1:
                        intent = new Intent(this, ActivityBasicsRecepcion.class);
                        mybundle = new Bundle();
                        if (!(((RecepcionVO) PROCESO).getnOrdenProceso() == null || ((RecepcionVO) PROCESO).getnOrdenProceso().equals(""))) {
                            mybundle.putInt("isFirst", 0);
                        } else {
                            mybundle.putInt("isFirst", 1);
                        }
                        mybundle.putInt(REQUEST_ID, ((RecepcionVO) PROCESO).getId());

                        intent.putExtras(mybundle);
                        startActivityForResult(intent, REQUEST_BASICS_RECEPCION_DATA);

                        editBasics = (FloatingActionButton) findViewById(R.id.fButtonEditBasicos);
                        editBasics.setClickable(false);
                        overridePendingTransition(R.anim.bot_in, R.anim.bot_out);
                        break;
                    case 2:
                        intent = new Intent(this, ActivityBasicsProduccion.class);
                        mybundle = new Bundle();
                        if (!(((ProduccionVO) PROCESO).getnOrdenProceso() == null || ((ProduccionVO) PROCESO).getnOrdenProceso().equals(""))) {
                            mybundle.putInt("isFirst", 0);
                        } else {
                            mybundle.putInt("isFirst", 1);
                        }
                        mybundle.putInt(REQUEST_ID, ((ProduccionVO) PROCESO).getId());

                        intent.putExtras(mybundle);
                        startActivityForResult(intent, REQUEST_BASICS_PRODUCCION_DATA);

                        editBasics = (FloatingActionButton) findViewById(R.id.fButtonEditBasicos);
                        editBasics.setClickable(false);
                        overridePendingTransition(R.anim.bot_in, R.anim.bot_out);
                        break;

                    case 3:
                        intent = new Intent(this, ActivityBasicsDespacho.class);
                        mybundle = new Bundle();
                        if (!(((DespachoVO) PROCESO).getNamePlanta() == null || ((DespachoVO) PROCESO).getNamePlanta().equals(""))) {
                            mybundle.putInt("isFirst", 0);
                        } else {
                            mybundle.putInt("isFirst", 1);
                        }
                        mybundle.putInt(REQUEST_ID, ((DespachoVO) PROCESO).getId());

                        intent.putExtras(mybundle);
                        startActivityForResult(intent, REQUEST_BASICS_DESPACHO_DATA);

                        editBasics = (FloatingActionButton) findViewById(R.id.fButtonEditBasicos);
                        editBasics.setClickable(false);
                        overridePendingTransition(R.anim.bot_in, R.anim.bot_out);
                        break;

                    case 4:
                            intent = new Intent(this, ActivityBasicsDescarte.class);
                            mybundle = new Bundle();
                            if (!(((DescarteVO) PROCESO).getnOrdenProceso() == null || ((DescarteVO) PROCESO).getnOrdenProceso().equals(""))) {
                                mybundle.putInt("isFirst", 0);
                            } else {
                                mybundle.putInt("isFirst", 1);
                            }
                            mybundle.putInt(REQUEST_ID, ((DescarteVO) PROCESO).getId());

                            intent.putExtras(mybundle);
                            startActivityForResult(intent, REQUEST_BASICS_DESCARTE_DATA);

                            editBasics = (FloatingActionButton) findViewById(R.id.fButtonEditBasicos);
                            editBasics.setClickable(false);
                            overridePendingTransition(R.anim.bot_in, R.anim.bot_out);
                            break;
                }

            }
       // }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        //Toast.makeText(this,"onactivity resytk",Toast.LENGTH_LONG).show();
        // visita = new VisitaDAO(ctx).getEditing();
        switch (requestCode){
            case REQUEST_BASICS_RECEPCION_DATA:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    String def = getString(R.string.tres_puntos);

                    PROCESO = new RecepcionDAO(getBaseContext()).buscarById((long) ((RecepcionVO) PROCESO).getId());
                    tViewFecha.setText(((RecepcionVO) PROCESO).getFechaHora()==null?def: ((RecepcionVO) PROCESO).getFechaHora());
                    tViewPlanta.setText(((RecepcionVO) PROCESO).getNamePlanta()==null?def: ((RecepcionVO) PROCESO).getNamePlanta());
                    tViewNOrdenProceso.setText(((RecepcionVO) PROCESO).getnOrdenProceso()==null?def: ((RecepcionVO) PROCESO).getnOrdenProceso());
                    tViewNGuia.setText(((RecepcionVO) PROCESO).getnOrdenGuia()==null?def: ((RecepcionVO) PROCESO).getnOrdenGuia());
                    tViewProductor.setText(((RecepcionVO) PROCESO).getNameEmpresa()==null?def: ((RecepcionVO) PROCESO).getNameEmpresa());
                    tViewVariedad.setText((((RecepcionVO) PROCESO).getNameCultivo()==null?def: ((RecepcionVO) PROCESO).getNameCultivo())+(((RecepcionVO) PROCESO).getNameVariedad()==null?def:" - "+ ((RecepcionVO) PROCESO).getNameVariedad()));
                    tViewNUnidades.setText((((RecepcionVO) PROCESO).getUnidadesRecepcion()==null?def:((RecepcionVO) PROCESO).getUnidadesRecepcion()));
                    tViewKilos.setText(((RecepcionVO) PROCESO).getKilosRecepcion()==null?def: ((RecepcionVO) PROCESO).getKilosRecepcion());
                    tViewTipoEnvase.setText((((RecepcionVO) PROCESO).getNameEnvase()==null?def:((RecepcionVO) PROCESO).getNameEnvase()));
                }else{
                    Toast.makeText(getBaseContext(),"Edición no Guardada",Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_BASICS_PRODUCCION_DATA:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    String def = getString(R.string.tres_puntos);

                    PROCESO = new ProduccionDAO(getBaseContext()).buscarById((long) ((ProduccionVO) PROCESO).getId());
                    tViewFecha.setText(((ProduccionVO) PROCESO).getFechaHora()==null?def: ((ProduccionVO) PROCESO).getFechaHora());
                    tViewPlanta.setText(((ProduccionVO) PROCESO).getNamePlanta()==null?def: ((ProduccionVO) PROCESO).getNamePlanta());
                    tViewNOrdenProceso.setText(((ProduccionVO) PROCESO).getnOrdenProceso()==null?def: ((ProduccionVO) PROCESO).getnOrdenProceso());
                    tViewProductor.setText(((ProduccionVO) PROCESO).getNameEmpresa()==null?def: ((ProduccionVO) PROCESO).getNameEmpresa());
                    tViewVariedad.setText((((ProduccionVO) PROCESO).getNameCultivo()==null?def: ((ProduccionVO) PROCESO).getNameCultivo())+" - "+(((ProduccionVO) PROCESO).getNameVariedad()==null?def: ((ProduccionVO) PROCESO).getNameVariedad()));
                    tViewNUnidades.setText(((ProduccionVO) PROCESO).getUnidades()==null?def: ((ProduccionVO) PROCESO).getUnidades());
                    tViewKilos.setText(((ProduccionVO) PROCESO).getKilos()==null?def: ((ProduccionVO) PROCESO).getKilos());
                    tViewDestino.setText(((ProduccionVO) PROCESO).getNameDestino()==null?def: ((ProduccionVO) PROCESO).getNameDestino());
                    tViewCalibre.setText(((ProduccionVO) PROCESO).getNameCalibre()==null?def: ((ProduccionVO) PROCESO).getNameCalibre());
                    tViewCategoria.setText(((ProduccionVO) PROCESO).getNameCategoria()==null?def: ((ProduccionVO) PROCESO).getNameCategoria());
                    tViewTipoEnvase.setText(((ProduccionVO) PROCESO).getNameEnvase()==null?def: ((ProduccionVO) PROCESO).getNameEnvase());

                }else{
                    Toast.makeText(getBaseContext(),"Edición no Guardada",Toast.LENGTH_LONG).show();
                }

                break;

            case REQUEST_BASICS_DESPACHO_DATA:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    String def = getString(R.string.tres_puntos);

                    PROCESO = new DespachoDAO(getBaseContext()).buscarById((long) ((DespachoVO) PROCESO).getId());

                    tViewFecha.setText((((DespachoVO) PROCESO).getFechaInspeccion()==null?def: ((DespachoVO) PROCESO).getFechaInspeccion()));
                    tViewPlanta.setText(((DespachoVO) PROCESO).getNamePlanta()==null?def: ((DespachoVO) PROCESO).getNamePlanta());
                    tViewVariedad.setText((((DespachoVO) PROCESO).getNameCultivo()==null?def: ((DespachoVO) PROCESO).getNameCultivo()));

                    tViewFechaCarga.setText( ((DespachoVO) PROCESO).getFechaCarga()==null?def: ((DespachoVO) PROCESO).getFechaCarga());
                    tViewFechaLlegada.setText( ((DespachoVO) PROCESO).getFechaLlegada()==null?def: ((DespachoVO) PROCESO).getFechaLlegada());
                    tViewFechaSalida.setText( ((DespachoVO) PROCESO).getFechaSalida()==null?def: ((DespachoVO) PROCESO).getFechaSalida());

                    tViewOrigen.setText( ((DespachoVO) PROCESO).getOrigen()==null?def: ((DespachoVO) PROCESO).getOrigen());
                    tViewCliente.setText( ((DespachoVO) PROCESO).getCliente()==null?def: ((DespachoVO) PROCESO).getCliente());

                    tViewControlador.setText( ((DespachoVO) PROCESO).getNameControlador()==null?def: ((DespachoVO) PROCESO).getNameControlador());
                    tViewNPallets.setText(""+ ((DespachoVO) PROCESO).getnPallets());
                    tViewNCajasPallet.setText(""+ ((DespachoVO) PROCESO).getnCajasPallet());
                    tViewTotalCajas.setText(""+ ((DespachoVO) PROCESO).getnPallets()*((DespachoVO) PROCESO).getnCajasPallet() );
                    tViewPesoTotal.setText( ""+((DespachoVO) PROCESO).getnPallets()*((DespachoVO) PROCESO).getnCajasPallet() * ((DespachoVO) PROCESO).getPesoCaja() );

                }else{
                    Toast.makeText(getBaseContext(),"Edición no Guardada",Toast.LENGTH_LONG).show();
                }

                break;

            case REQUEST_BASICS_DESCARTE_DATA:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    String def = getString(R.string.tres_puntos);

                    PROCESO = new DescarteDAO(getBaseContext()).buscarById((long) ((DescarteVO) PROCESO).getId());
                    tViewFecha.setText(((DescarteVO) PROCESO).getFechaHora()==null?def: ((DescarteVO) PROCESO).getFechaHora());
                    tViewPlanta.setText(((DescarteVO) PROCESO).getNamePlanta()==null?def: ((DescarteVO) PROCESO).getNamePlanta());
                    tViewNOrdenProceso.setText(((DescarteVO) PROCESO).getnOrdenProceso()==null?def: ((DescarteVO) PROCESO).getnOrdenProceso());
                    tViewProductor.setText(((DescarteVO) PROCESO).getNameEmpresa()==null?def: ((DescarteVO) PROCESO).getNameEmpresa());
                    tViewVariedad.setText((((DescarteVO) PROCESO).getNameCultivo()==null?def: ((DescarteVO) PROCESO).getNameCultivo())+" - "+(((DescarteVO) PROCESO).getNameVariedad()==null?def: ((DescarteVO) PROCESO).getNameVariedad()));
                    tViewNUnidades.setText(((DescarteVO) PROCESO).getUnidades()==null?def: ((DescarteVO) PROCESO).getUnidades());
                    tViewKilos.setText(((DescarteVO) PROCESO).getKilos()==null?def: ((DescarteVO) PROCESO).getKilos());
                    tViewTipoEnvase.setText(((DescarteVO) PROCESO).getNameEnvase()==null?def: ((DescarteVO) PROCESO).getNameEnvase());

                }else{
                    Toast.makeText(getBaseContext(),"Edición no Guardada",Toast.LENGTH_LONG).show();
                }

                break;



            case REQUEST_EDIT_MUESTRA :


                muestraVOList = new MuestraDAO(getBaseContext())
                        .listarByIdProcesoIdTipoProceso(
                                ((ProcesoVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()
                        );

                baseAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lViewMuestras);
                break;
            case REQUEST_EDIT_MUESTRADESPACHO :


                muestraVOList = new MuestraDAO(getBaseContext())
                        .listarByIdProcesoIdTipoProceso(
                                ((ProcesoVO) PROCESO).getId(),
                                new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()
                        );

                baseAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lViewMuestras);
                break;
        }

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec (listView.getWidth (), View.MeasureSpec.EXACTLY);
        // int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
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

    @Override
    public void onResume() {
            super.onResume();
            FloatingActionButton editBasics = (FloatingActionButton) findViewById(R.id.fButtonEditBasicos);
            editBasics.setClickable(true);
            FloatingActionButton fButtonAddMuestra =
                    (FloatingActionButton) findViewById(R.id.fButtonAddEvalaucion);
            fButtonAddMuestra.setClickable(true);
    }

    @Override
    public void onBackPressed() {
        if(ISEDITABLE){
                    if(((ProcesoVO) PROCESO).isEditing()){
                        showClosePopup();
                    }else{
                        finish();
                    }

        }else {
            finish();
        }

    }

    private Button btnDialogClose;
    private ImageView iViewDialogClose;
    private void showClosePopup(){
        dialogClose.setContentView(R.layout.dialog_guardar_progreso);
        btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close_dialog);
        iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);

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
                finish();
            }
        });

        dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogClose.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void end(View view){
        //verificando si todas las listas  estan en 100% de completadas
        boolean flag=true;
        if(ISEDITABLE){
            switch (new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()){
                case 1:
                    if(((RecepcionVO) PROCESO).getIdVariedad()>0){
                        flag=true;
                    }else {
                        flag=false;
                    }
                    if(flag){
                        //guardar y finalizar
                        if(new RecepcionDAO(getBaseContext()).save(((RecepcionVO) PROCESO).getId())){
                    /*
                    Toast.makeText(
                            getBaseContext()
                            ,"Visita Guardada"
                            ,Toast.LENGTH_SHORT).show();
                    */
                            Intent i = new Intent(this,ActivityMain.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }else{
                            Toast.makeText(
                                    getBaseContext()
                                    ,"Recepción no se pudo guardar"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(
                                getBaseContext()
                                ,"Recepción no guardada"
                                ,Toast.LENGTH_LONG).show();
                        new RecepcionDAO(getBaseContext()).deleteById(((RecepcionVO) PROCESO).getId());
                        Intent i = new Intent(this,ActivityMain.class);
                        startActivity(i);

                    }
                    break;

                case 2:
                    if(((ProduccionVO) PROCESO).getIdVariedad()>0){
                        flag=true;
                    }else {
                        flag=false;
                    }
                    if(flag){
                        //guardar y finalizar
                        if(new ProduccionDAO(getBaseContext()).save(((ProduccionVO) PROCESO).getId())){
                    /*
                    Toast.makeText(
                            getBaseContext()
                            ,"Visita Guardada"
                            ,Toast.LENGTH_SHORT).show();
                    */
                            Intent i = new Intent(this,ActivityMain.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }else{
                            Toast.makeText(
                                    getBaseContext()
                                    ,"Proceso no se pudo guardar"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(
                                getBaseContext()
                                ,"Proceso no guardado"
                                ,Toast.LENGTH_LONG).show();
                        new ProduccionDAO(getBaseContext()).deleteById(((ProduccionVO) PROCESO).getId());
                        Intent i = new Intent(this,ActivityMain.class);
                        startActivity(i);

                    }
                    break;

                case 3:
                    if(((DespachoVO) PROCESO).getIdCultivo()>0){
                        flag=true;
                    }else {
                        flag=false;
                    }
                    if(flag){
                        //guardar y finalizar
                        if(new DespachoDAO(getBaseContext()).save(((DespachoVO) PROCESO).getId())){
                            Log.d(TAG,"guardado Despacho");
                            Intent i = new Intent(this,ActivityMain.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }else{
                            Toast.makeText(
                                    getBaseContext()
                                    ,"Despacho no se pudo guardar"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(
                                getBaseContext()
                                ,"Despacho no guardado"
                                ,Toast.LENGTH_LONG).show();
                        new DespachoDAO(getBaseContext()).deleteById(((DespachoVO) PROCESO).getId());
                        Intent i = new Intent(this,ActivityMain.class);
                        startActivity(i);

                    }
                    break;

                case 4:
                    if(((DescarteVO) PROCESO).getIdVariedad()>0){
                        flag=true;
                    }else {
                        flag=false;
                    }
                    if(flag){
                        //guardar y finalizar
                        if(new DescarteDAO(getBaseContext()).save(((DescarteVO) PROCESO).getId())){
                    /*
                    Toast.makeText(
                            getBaseContext()
                            ,"Visita Guardada"
                            ,Toast.LENGTH_SHORT).show();
                    */
                            Intent i = new Intent(this,ActivityMain.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }else{
                            Toast.makeText(
                                    getBaseContext()
                                    ,"Descarte no se pudo guardar"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(
                                getBaseContext()
                                ,"Descarte no guardado"
                                ,Toast.LENGTH_LONG).show();
                        new DescarteDAO(getBaseContext()).deleteById(((DescarteVO) PROCESO).getId());
                        Intent i = new Intent(this,ActivityMain.class);
                        startActivity(i);

                    }
                    break;

            }

        }else{//no es editable

            finish();

        }


    }

}
