package com.ibao.agroq.helpers.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.agroq.R;
import com.ibao.agroq.models.dao.CalibreDAO;
import com.ibao.agroq.models.dao.CriterioDetalleDAO;
import com.ibao.agroq.models.dao.EvaluacionDetalleDAO;
import com.ibao.agroq.models.dao.FotoDAO;
import com.ibao.agroq.models.vo.entitiesInternal.CriterioDetalleVO;
import com.ibao.agroq.models.vo.entitiesInternal.EvaluacionDetalleVO;
import com.ibao.agroq.views.ActivityPhotoGallery;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.helpers.adapters.AdapterListRecepcion.setListViewHeightBasedOnChildren;

public class RViewAdapterEvaluacionDetalle extends RecyclerView.Adapter<RViewAdapterEvaluacionDetalle.ViewHolder> {

    private Context ctx;
    private List<EvaluacionDetalleVO> evaluacionDetalleVOList;
    private RecyclerView lView;
    public static Boolean finalizadoEva;
    public static Boolean finalizadoCri;
    private boolean isEditable;
    private static BaseAdapter baseAdapter;
    private static EditText eTextNFrutos ;

    String TAG = RViewAdapterEvaluacionDetalle.class.getSimpleName();

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //elementos

        //fin labels
        ConstraintLayout cLayoutTotal ;
        //bottons
        ListView lViewCriteriosDetalle;
        FloatingActionButton fAButtonDelete;
        TextView tViewNameEvaluacion ;
        TextView tViewCantidadTotal ;
        TextView tViewPorcentajeTotal;
        TextView tView_total;
        TextView tView_twoPoints;


        public ViewHolder(View itemView){
            super(itemView);
            cLayoutTotal = itemView.findViewById(R.id.cLayoutComplete);
            lViewCriteriosDetalle = itemView.findViewById(R.id.lViewEvaluaciones);
            fAButtonDelete = itemView.findViewById(R.id.fAButtonDelete);
            tViewNameEvaluacion=itemView.findViewById(R.id.tViewNameCri);
            tViewCantidadTotal= itemView.findViewById(R.id.tViewCantidadTotal);
            tViewPorcentajeTotal = itemView.findViewById(R.id.tViewPorcentaje);
            tView_total = itemView.findViewById(R.id.tView_total);
            tView_twoPoints = itemView.findViewById(R.id.tView_twoPoints);
        }

    }

    public RViewAdapterEvaluacionDetalle(Context ctx, List<EvaluacionDetalleVO> evaluacionDetalleVOList, RecyclerView lView, boolean isEditable, EditText eTextNFrutos) {
        this.ctx = ctx;
        this.evaluacionDetalleVOList = evaluacionDetalleVOList;
        this.lView = lView;
        this.isEditable = isEditable;
        this.eTextNFrutos = eTextNFrutos;
        this.finalizadoEva = false;
        this.finalizadoCri = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        progress.show();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_evaluaciondetalle,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public EvaluacionDetalleVO getItem(int position) {
        return evaluacionDetalleVOList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int posEva) {

        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {

                holder.tViewNameEvaluacion.setText(getItem(posEva).getNameEvaluacion());
                holder.fAButtonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialogClose = new Dialog(ctx);
                        dialogClose.setContentView(R.layout.dialog_danger);
                        Button btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close);
                        Button btnDialogAcept = (Button) dialogClose.findViewById(R.id.buton_acept);
                        ImageView iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);
                        TextView mensaje = (TextView) dialogClose.findViewById(R.id.tViewRecomendacion);


                        if(evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==7 || evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==12){
                            holder.tView_total.setVisibility(View.INVISIBLE);
                            holder.tView_twoPoints.setVisibility(View.INVISIBLE);
                            holder.tViewCantidadTotal.setVisibility(View.INVISIBLE);
                            holder.tViewPorcentajeTotal.setVisibility(View.INVISIBLE);
                        }
                        mensaje.setText("Esta a punto de eliminar una Evaluación. \n ¿DESEA CONTINUAR? ");
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
                                        android.view.animation.AnimationUtils.loadAnimation(ctx, R.anim.zoom_back_out_item);

                                boolean x=new EvaluacionDetalleDAO(ctx).deleteById(evaluacionDetalleVOList.get(posEva).getId());

                                if(!x){
                                    Toast.makeText(ctx,"Error al eliminar",Toast.LENGTH_SHORT).show();
                                }else{
                                    evaluacionDetalleVOList.remove(posEva);
                                    if(evaluacionDetalleVOList.size()>0){
                                        Log.d(TAG,"LISTA  DE EVALUACIONES NO VACIA "+evaluacionDetalleVOList.size());
                                        eTextNFrutos.setFocusable(false);
                                        eTextNFrutos.setClickable(false);
                                        eTextNFrutos.setFocusableInTouchMode(false);
                                    }else {
                                        Log.d(TAG,"LISTA  DE EVALUACIONES VACIA "+evaluacionDetalleVOList.size());
                                        eTextNFrutos.setFocusable(true);
                                        eTextNFrutos.setClickable(true);
                                        eTextNFrutos.setFocusableInTouchMode(true);
                                    }
                                    //holder.cLayoutTotal.startAnimation(animClose);
                                    Handler handler = new Handler();
                                    handler.postDelayed(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    //progress.show();
                                                    RViewAdapterEvaluacionDetalle.super.notifyDataSetChanged();
                                                }
                                            },10
                                    );
                                }
                                dialogClose.dismiss();
                            }
                        });

                        dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogClose.show();
                    }
                });

            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {

                final List<CriterioDetalleVO> criterioDetalleVOList = new ArrayList<>();

                baseAdapter = new BaseAdapter() {


                    @Override
                    public int getCount() {
                        return criterioDetalleVOList.size();
                    }

                    @Override
                    public CriterioDetalleVO getItem(int position) {
                        return criterioDetalleVOList.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return criterioDetalleVOList.get(position).getId();
                    }


                    @Override
                    public View getView(final int posCri, View convertView, ViewGroup parent) {
                        View v = convertView;
                        final LayoutInflater inflater;
                        inflater = LayoutInflater.from(ctx);
                        v = inflater.inflate(R.layout.item_view_criteriodetalle,null);
                        Log.d(TAG,"Dibujando CRi: "+posCri);

                        EditText _1 = v.findViewById(R.id.eTextEntero);
                        EditText _2 = v.findViewById(R.id.eTextDecimal);
                        EditText _3 = v.findViewById(R.id.eTextTexto);

                        if(evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==7 || evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==12){
                            _1.setVisibility(View.INVISIBLE);
                            _2.setVisibility(View.INVISIBLE);
                            _1.setFocusable(false);
                            _2.setFocusable(false);
                           _1 = v.findViewById(R.id.eTextEntero2);
                             _2 = v.findViewById(R.id.eTextDecimal2);
                        }

                        TextView tViewCriterioName = v.findViewById(R.id.tViewNameCri);
                        final EditText eTextEntero = _1;
                        final EditText eTextDecimal = _2;
                        final EditText eTextTexto = v.findViewById(R.id.eTextTexto);





                        ImageView iViewFoto = v.findViewById(R.id.iViewFoto);
                        final TextView tViewPorcentajeCri = v.findViewById(R.id.tViewPorcentajeCri);
                        final ImageView iViewCarita = v.findViewById(R.id.iViewCarita);
                        TextView tViewSPorcentajeCri = v.findViewById(R.id.tViewSPorcentajeCri);

                        tViewPorcentajeCri.setText(""+getItem(posCri).getPorcentaje()+"%");

                        tViewCriterioName.setText(getItem(posCri).getNameCriterio());

                        TextView tviewNumFotos = v.findViewById(R.id.tViewNfotos);

                        try{
                            int i = new FotoDAO(ctx).contarFotos(getItem(posCri).getId());

                            if(i>0 && i<=9){
                                tviewNumFotos.setVisibility(View.VISIBLE);
                                tviewNumFotos.setText(String.valueOf(i));
                            }
                            if(i>9){
                                tviewNumFotos.setVisibility(View.VISIBLE);
                                tviewNumFotos.setText(String.valueOf(9)+"+");
                            }
                            if(i==0){
                                tviewNumFotos.setVisibility(View.INVISIBLE);
                                tviewNumFotos.setText(String.valueOf(0));
                            }
                        }catch (Exception e){
                            Log.d(TAG,"error conteo nfotos:"+e.toString());
                        }

                        switch (getItem(posCri).getCalificacion()){
                            case 0:
                                iViewCarita.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_good_24dp));
                                break;
                            case 2:
                                iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_regular));
                                break;
                            case 3:
                                iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_mal));
                                break;
                        }

                        if(getItem(posCri).isFotografiableCriterio())
                        {
                            iViewFoto.setVisibility(View.VISIBLE);
                            iViewFoto.setClickable(true);

                            iViewFoto.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //tomarFoto();
                                    //Toast.makeText(ctx,"Tomar Fotografia",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ctx, ActivityPhotoGallery.class);
                                    intent.putExtra("idCriterioDetalle",getItem(posCri).getId());
                                    intent.putExtra("value",getItem(posCri).getValor());
                                    intent.putExtra("name",getItem(posCri).getNameCriterio());
                                    intent.putExtra("isEditable",isEditable);
                                    intent.putExtra("fechaHora",getItem(posCri).getPorcentaje());

                                    ((Activity) ctx).startActivityForResult(intent, 123);

                                }
                            });



                        }else {
                            iViewFoto.setVisibility(View.INVISIBLE);
                            iViewFoto.setClickable(false);
                        }



                        if(new CalibreDAO(ctx).consultarTolCalibreByidCriterioDetalle(getItem(posCri).getId())!=null){
                            tViewPorcentajeCri.setVisibility(View.INVISIBLE);
                            holder.tView_total.setVisibility(View.INVISIBLE);
                            holder.tView_twoPoints.setVisibility(View.INVISIBLE);
                            holder.tViewCantidadTotal.setVisibility(View.INVISIBLE);
                            holder.tViewPorcentajeTotal.setVisibility(View.INVISIBLE);
                        }

                        if(getItem(posCri).getTipoDatoCriterio()!=null){

                            switch (criterioDetalleVOList.get(posCri).getTipoDatoCriterio()){
                                case "ENTERO":
                                    eTextEntero.setVisibility(View.VISIBLE);
                                    eTextDecimal.setVisibility(View.INVISIBLE);
                                    eTextTexto.setVisibility(View.INVISIBLE);
                                    eTextEntero.setText(getItem(posCri).getValor());

                                    eTextEntero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            if (!hasFocus) {
                                                // Toast.makeText(getBaseContext(),"guardando "+eTextEntero.getText().toString(),Toast.LENGTH_SHORT).show();
                                                if((eTextEntero.getText().toString().equals(""))){
                                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(posCri),"")
                                                    ){
                                                        //   Toast.makeText(ctx,"se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                                    }else {
                                                        Toast.makeText(ctx,"no se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                                    }

                                                }else {
                                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(posCri),String.valueOf(Integer.valueOf(eTextEntero.getText().toString())))
                                                    ){
                                                        // Toast.makeText(ctx,"se puedo cambiar el valor "+String.valueOf(Integer.valueOf(eTextEntero.getText().toString())), Toast.LENGTH_LONG).show();
                                                    }else {
                                                        Toast.makeText(ctx,"no se puedo cambiar el valor "+String.valueOf(Integer.valueOf(eTextEntero.getText().toString())), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                                criterioDetalleVOList.set(posCri, new CriterioDetalleDAO(ctx).consultarByid(getItem(posCri).getId()));


                                                float porCri= getItem(posCri).getPorcentaje();

                                                tViewPorcentajeCri.setText(""+porCri+"%");


                                                switch (getItem(posCri).getCalificacion()){
                                                    case 0:
                                                        iViewCarita.setVisibility(View.INVISIBLE);
                                                        break;
                                                    case 1:
                                                        iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_good_24dp));
                                                        break;
                                                    case 2:
                                                        iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_regular));
                                                        break;
                                                    case 3:
                                                        iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_mal));
                                                        break;
                                                }
                                                //

                                                Log.d(TAG,"cons1");
                                                evaluacionDetalleVOList.set(posEva,
                                                        new EvaluacionDetalleDAO(ctx).consultarByid(evaluacionDetalleVOList.get(posEva).getId())
                                                );


                                                if(evaluacionDetalleVOList.get(posEva).isMatSec()){
                                                    holder.tView_total.setVisibility(View.INVISIBLE);
                                                    holder.tView_twoPoints.setVisibility(View.INVISIBLE);
                                                    holder.tViewCantidadTotal.setVisibility(View.INVISIBLE);
                                                    holder.tViewPorcentajeTotal.setVisibility(View.INVISIBLE);
                                                 //   holder.tViewCantidadTotal.setText(""+evaluacionDetalleVOList.get(posEva).getCantidad()+"%");
                                                }else {
                                                    holder.tViewPorcentajeTotal.setText(""+evaluacionDetalleVOList.get(posEva).getPorcentaje()+"%");
                                                    holder.tViewCantidadTotal.setText(""+evaluacionDetalleVOList.get(posEva).getCantidad());
                                                }
                                            }
                                        }
                                    });

                                    break;
                                case "TEXTO":
                                    eTextTexto.setVisibility(View.VISIBLE);
                                    eTextDecimal.setVisibility(View.INVISIBLE);
                                    eTextEntero.setVisibility(View.INVISIBLE);
                                    Log.d(TAG,"val texto-->"+getItem(posCri).getValor());
                                    eTextTexto.setText(getItem(posCri).getValor());
                                    eTextTexto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            if (!hasFocus) {
                                                // Toast.makeText(getBaseContext(),"guardando "+eTextEntero.getText().toString(),Toast.LENGTH_SHORT).show();
                                                if((eTextTexto.getText().toString().equals(""))){
                                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(posCri),"")
                                                    ){
                                                        //   Toast.makeText(ctx,"se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                                    }else {
                                                        Toast.makeText(ctx,"no se puede cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                                    }

                                                }else {
                                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(posCri),(eTextTexto.getText().toString()))
                                                    ){
                                                       //  Toast.makeText(ctx,"se puedo cambiar el valor "+eTextTexto.getText().toString(), Toast.LENGTH_LONG).show();
                                                    }else {
                                                        Toast.makeText(ctx,"no se puede cambiar el valor "+String.valueOf(eTextTexto.getText().toString()), Toast.LENGTH_LONG).show();
                                                    }
                                                }


                                                Log.d(TAG,"cons1");
                                                evaluacionDetalleVOList.set(posEva,
                                                        new EvaluacionDetalleDAO(ctx).consultarByid(evaluacionDetalleVOList.get(posEva).getId())
                                                );


                                                if(evaluacionDetalleVOList.get(posEva).isMatSec()|| evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==7 || evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==12){
                                                    holder.tView_total.setVisibility(View.INVISIBLE);
                                                    holder.tView_twoPoints.setVisibility(View.INVISIBLE);
                                                    holder.tViewCantidadTotal.setVisibility(View.INVISIBLE);
                                                    holder.tViewPorcentajeTotal.setVisibility(View.INVISIBLE);
                                                    //   holder.tViewCantidadTotal.setText(""+evaluacionDetalleVOList.get(posEva).getCantidad()+"%");
                                                }else {
                                                    holder.tViewPorcentajeTotal.setText(""+evaluacionDetalleVOList.get(posEva).getPorcentaje()+"%");
                                                    holder.tViewCantidadTotal.setText(""+evaluacionDetalleVOList.get(posEva).getCantidad());
                                                }
                                            }
                                        }
                                    });

                                    break;
                                case "PORCENTAJE":
                                    tViewPorcentajeCri.setVisibility(View.INVISIBLE);
                                    tViewSPorcentajeCri.setVisibility(View.VISIBLE);

                                case "DECIMAL":
                                default:
                                    eTextDecimal.setVisibility(View.VISIBLE);
                                    eTextEntero.setVisibility(View.INVISIBLE);
                                    eTextTexto.setVisibility(View.INVISIBLE);

                                    eTextDecimal.setText(getItem(posCri).getValor());

                                    eTextDecimal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            if (!hasFocus) {
                                                // Toast.makeText(getBaseContext(),"guardando "+eTextEntero.getText().toString(),Toast.LENGTH_SHORT).show();
                                                if((eTextDecimal.getText().toString().equals(""))){
                                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(posCri),"")
                                                    ){
                                                        //  Toast.makeText(ctx,"se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                                    }else {
                                                        Toast.makeText(ctx,"no se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                                    }
                                                }else {
                                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(posCri),String.valueOf(Float.valueOf(eTextDecimal.getText().toString())))
                                                    ){
                                                        //Toast.makeText(ctx,"se puedo cambiar el valor "+String.valueOf(Float.valueOf(eTextDecimal.getText().toString())), Toast.LENGTH_LONG).show();
                                                    }else {
                                                        Toast.makeText(ctx,"no se puedo cambiar el valor "+String.valueOf(Float.valueOf(eTextDecimal.getText().toString())), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                                criterioDetalleVOList.set(posCri, new CriterioDetalleDAO(ctx).consultarByid(getItem(posCri).getId()));


                                                tViewPorcentajeCri.setText(""+getItem(posCri).getPorcentaje()+"%");

                                                switch (getItem(posCri).getCalificacion()){
                                                    case 0:
                                                        iViewCarita.setVisibility(View.INVISIBLE);
                                                        break;
                                                    case 1:
                                                        iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_good_24dp));
                                                        break;
                                                    case 2:
                                                        iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_regular));
                                                        break;
                                                    case 3:
                                                        iViewCarita.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_mal));
                                                        break;
                                                }
                                                Log.d(TAG,"cons2");
                                                evaluacionDetalleVOList.set(posEva,
                                                        new EvaluacionDetalleDAO(ctx).consultarByid(evaluacionDetalleVOList.get(posEva).getId())
                                                );

                                                if(evaluacionDetalleVOList.get(posEva).isMatSec()|| evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==7|| evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==12){

                                                    holder.tView_total.setVisibility(View.INVISIBLE);
                                                    holder.tView_twoPoints.setVisibility(View.INVISIBLE);
                                                    holder.tViewCantidadTotal.setVisibility(View.INVISIBLE);
                                                    holder.tViewPorcentajeTotal.setVisibility(View.INVISIBLE);
                                                    //holder.tViewCantidadTotal.setText(""+evaluacionDetalleVOList.get(posEva).getCantidad()+"%");
                                                }else {
                                                    holder.tViewPorcentajeTotal.setText(""+evaluacionDetalleVOList.get(posEva).getPorcentaje()+"%");
                                                    holder.tViewCantidadTotal.setText(""+evaluacionDetalleVOList.get(posEva).getCantidad());
                                                }
                                            }
                                        }
                                    });



                            }
                        }else {
                            eTextDecimal.setVisibility(View.INVISIBLE);
                            eTextEntero.setVisibility(View.INVISIBLE);
                        }

                /*

                final View activityRootView = v.findViewById(android.R.id.content);
                activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        boolean isTyping=false;
                        Rect r = new Rect();
                        //r will be populated with the coordinates of your view that area still visible.
                        activityRootView.getWindowVisibleDisplayFrame(r);

                        int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                        if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                            isTyping = true;
                            Log.d(TAG,"teclado Cerrado1");
                            //Toast.makeText(getBaseContext(),"guardando "+eTextEntero.getText().toString(),Toast.LENGTH_SHORT).show();

                            if(getItem(position).getTipoDatoCriterio().equals("ENTERO")){
                                if((eTextEntero.getText().toString().equals(""))){
                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(position),"")
                                    ){
                                        Toast.makeText(ctx,"se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(ctx,"no se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                    }

                                }else {
                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(position),String.valueOf(Integer.valueOf(eTextEntero.getText().toString())))
                                    ){
                                        Toast.makeText(ctx,"se puedo cambiar el valor "+String.valueOf(Integer.valueOf(eTextEntero.getText().toString())), Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(ctx,"no se puedo cambiar el valor "+String.valueOf(Integer.valueOf(eTextEntero.getText().toString())), Toast.LENGTH_LONG).show();
                                    }


                                }
                            }else {
                                if((eTextDecimal.getText().toString().equals(""))){
                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(position),"")
                                    ){
                                        Toast.makeText(ctx,"se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(ctx,"no se puedo cambiar el valor a 0", Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    if(new CriterioDetalleDAO(ctx).editValor((int)getItemId(position),String.valueOf(Integer.valueOf(eTextDecimal.getText().toString())))
                                    ){
                                        Toast.makeText(ctx,"se puedo cambiar el valor "+String.valueOf(Integer.valueOf(eTextDecimal.getText().toString())), Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(ctx,"no se puedo cambiar el valor "+String.valueOf(Integer.valueOf(eTextDecimal.getText().toString())), Toast.LENGTH_LONG).show();
                                    }

                                }
                            }


                            criterioDetalleVOList.set(position, new CriterioDetalleDAO(ctx).consultarByid(getItem(position).getId()));
                        } else {
                            //to make sure this will call only once when keyboard is hide.
                            if(isTyping){
                                Log.d(TAG,"teclado Cerrado2");
                                isTyping = false;
                            }
                        }
                    }
                });
*/

                        if(posCri == criterioDetalleVOList.size()-1){
                            Log.d(TAG,"FINALIZO CRI "+ posCri);
                            finalizadoCri = true;
                        }else {
                            Log.d(TAG,"no finalizo cri "+ posCri+" "+(criterioDetalleVOList.size()-1));
                            finalizadoEva =false;
                        }

                        return v;
                    }

                };

                criterioDetalleVOList.addAll( new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(
                        getItem(posEva).getId()
                ));

                Log.d(TAG,"AGREGANDO AL LIST CRITERIOS "+posEva);
                holder.lViewCriteriosDetalle.setAdapter(baseAdapter);
                setListViewHeightBasedOnChildren(holder.lViewCriteriosDetalle);

//canculando porcentaje
/*
        float cant = 0;
        Log.d(TAG,"cons 4");
        int idMuestra = evaluacionDetalleVOList.get(posEva).getIdMuestra();
        int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());

        for(CriterioDetalleVO cr : criterioDetalleVOList){
            cant= cant+ Float.valueOf(cr.getValor()==null ||cr.getValor().equals("") ?"0":(cr.getValor()==null?"0":cr.getValor()));
        }

        float porTotal =0.0f;
            porTotal =
                    (1.0f*Float.valueOf(cant)/(1.0f*nFrutos))*100.0f;
*/
                //      Log.d(TAG,"CONS 3");
/*
        evaluacionDetalleVOList.set(posEva,
                new EvaluacionDetalleDAO(ctx).consultarByid(evaluacionDetalleVOList.get(posEva).getId())
        );
*/
                if(evaluacionDetalleVOList.get(posEva).isMatSec()|| evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==7|| evaluacionDetalleVOList.get(posEva).getIdEvaluacion()==12){
                    holder.tView_total.setVisibility(View.INVISIBLE);
                    holder.tView_twoPoints.setVisibility(View.INVISIBLE);
                    holder.tViewCantidadTotal.setVisibility(View.INVISIBLE);
                    holder.tViewPorcentajeTotal.setVisibility(View.INVISIBLE);
                   // holder.tViewCantidadTotal.setText(""+evaluacionDetalleVOList.get(posEva).getCantidad()+"%");
                }else {
                    holder.tViewPorcentajeTotal.setText(""+evaluacionDetalleVOList.get(posEva).getPorcentaje()+"%");
                    holder.tViewCantidadTotal.setText(""+evaluacionDetalleVOList.get(posEva).getCantidad());
                }

                //fin canculo porcentaje

                //if(posEva==evaluacionDetalleVOList.size()-1){
                //   Log.d(TAG,"finalizo EVA"+ posEva);
                finalizadoEva =true;
//                progress.dismiss();
            /*}else {

                Log.d(TAG,"no finalizo EVA "+ posEva+"/"+(evaluacionDetalleVOList.size()-1));
                finalizadoEva =false;
            }*/

            }
        });


    }

    @Override
    public int getItemCount() {
        return evaluacionDetalleVOList.size();
    }

}
