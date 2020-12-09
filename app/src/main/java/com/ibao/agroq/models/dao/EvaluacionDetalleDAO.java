package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.EvaluacionVO;
import com.ibao.agroq.models.vo.entitiesDB.TolEvaVO;
import com.ibao.agroq.models.vo.entitiesInternal.CriterioDetalleVO;
import com.ibao.agroq.models.vo.entitiesInternal.EvaluacionDetalleVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CONFEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_CONFEVALUACION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_CONFEVALUACION_COL_IDEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_CONFEVALUACION_COL_IDTIPOPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_IDCULTIVO;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE_COL_IDEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE_COL_IDMUESTRA;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_ISMATSEC;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_LOGINDATA;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_CANTIDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_IDPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_TOLEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_TOLEVALUACION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_TOLEVALUACION_COL_IDCONFEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_TOLEVALUACION_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_TOLEVALUACION_COL_MAXIMO;
import static com.ibao.agroq.utilities.Utilities.TABLE_TOLEVALUACION_COL_MINIMO;

public class EvaluacionDetalleDAO {

    String TAG =EvaluacionDetalleDAO.class.getSimpleName();

    Context ctx;

    public EvaluacionDetalleDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getWritableDatabase();
        int res = db.delete(TABLE_EVALUACIONDETALLE,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        c.close();
        return flag;
    }

    public EvaluacionDetalleVO nuevoByIdMuestraIdEvaluacion(int idMuestra,int idEvaluacion){
        int temp=0;
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getWritableDatabase();
        try {
            Log.d(TAG,"intentando insert M:"+idMuestra+" E:"+idEvaluacion);

            ContentValues values = new ContentValues();
            values.put(TABLE_EVALUACIONDETALLE_COL_IDEVALUACION,idEvaluacion);
            values.put(TABLE_EVALUACIONDETALLE_COL_IDMUESTRA,idMuestra);
            temp = (int)db.insert(TABLE_EVALUACIONDETALLE,TABLE_EVALUACIONDETALLE_COL_ID,values);
            EvaluacionDetalleVO ev = consultarByid(temp);
            Log.d(TAG,"INSERTADO:"+temp+" id:"+ev.getId()+" idM"+ev.getIdMuestra()+" "+ev.getIdEvaluacion()+" "+ev.getNameEvaluacion()+" "+ev.getIdMuestra()+new MuestraDAO(ctx).consultarById(ev.getIdMuestra()).getIdTipoProceso());

            new CriterioDetalleDAO(ctx).createAllByIdEvaluacionDetalle(temp);
            Log.d(TAG,"INSERTADO OK:"+temp);

        }catch (Exception e){
            Log.d(TAG,"errorNuevo ->"+e.toString());
        }finally {
            db.close();
            c.close();
        }


        if(temp>0){
            EvaluacionDetalleVO ev = consultarByid(temp);
            Log.d(TAG,"INSERTADO OK- idbuscado:"+temp+" "+ev.getId()+" "+ev.getIdMuestra()+" "+ev.getIdEvaluacion()+" "+ev.getNameEvaluacion());
            return ev;

        }else {
            return null;
        }
    }

    public EvaluacionDetalleVO consultarByid(int id){


        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        EvaluacionDetalleVO temp = null;
        try{
            TolEvaVO tol;
            float cant;
            EvaluacionVO tempEva;
            int TP = new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso();
            int nFrutos;
            temp = new EvaluacionDetalleVO();
            Cursor cursor ;
            cursor= db.rawQuery(
                    "SELECT " +
                            " * "+
                        " FROM "+
                            TABLE_EVALUACIONDETALLE+" as ED"+
                            " WHERE " +
                            "ED."+TABLE_EVALUACIONDETALLE_COL_ID+" = "+String.valueOf(id)
                    ,null);

            switch (TP){

                case 1:
                    cursor= db.rawQuery(
                            "SELECT " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+", " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+", "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+", "+
                                    "M."+TABLE_MUESTRA_COL_CANTIDAD+", "+
                                    "R."+TABLE_RECEPCION_COL_IDVARIEDAD+
                                    " FROM "+
                                    TABLE_EVALUACIONDETALLE+" as ED,"+
                                    TABLE_MUESTRA+" as M, "+

                                    TABLE_RECEPCION+" as R"+

                                    " WHERE " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+" = "+String.valueOf(id)+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+" = "+"M."+TABLE_MUESTRA_COL_ID+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_IDPROCESO+" = "+"R."+TABLE_RECEPCION_COL_ID
                            ,null);

                    cursor.moveToFirst();
                    temp.setId(cursor.getInt(0));
                    temp.setIdEvaluacion(cursor.getInt(1));
                    temp.setIdMuestra(cursor.getInt(2));
                     nFrutos = cursor.getInt(3);
                    Log.d(TAG," nFrutos->"+nFrutos);
                    temp.setNameEvaluacion(new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion()).getName());
                    Log.d(TAG," ->"+temp.getNameEvaluacion());

                    //VERIFICANDO SI ES DEFECTO O MATERIA SECA

                     tempEva = new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion());
                    temp.setMatSec(tempEva.isMateriaSeca());
                    temp.setDefecto(tempEva.isDefecto());

                    if (temp.isMatSec()) {
                        Log.d(TAG, "IS MATERIA SECA cons by id");
                    }
                    if (temp.isDefecto()) {
                        Log.d(TAG, "IS defecto cons by id");
                    }

                    cant = 0.0f;
                    if(!temp.isMatSec()){
                        for(CriterioDetalleVO cri : new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(temp.getId())){
                            if(!cri.getTipoDatoCriterio().equals("TEXTO")) {
                                cant = cant + 1.0f * (cri.getValor() == null || cri.getValor().isEmpty() || cri.getValor() == "" ? 0.0f : Float.valueOf(cri.getValor()));
                            }
                        }
                    }

                 //   Log.d(TAG,"CANTIDAD: "+cant);

                //    Log.d(TAG,"POCENTAJET: "+(cant*100.00000f)/nFrutos);
                    temp.setCantidad(cant);

                    if(temp.isMatSec()){
                        temp.setPorcentaje(cant);
                    }else {
                        temp.setPorcentaje(cant*100.00000f/nFrutos);
                    }

                    //buscando tolerancia de  evaluacion
                     tol = consTolEvaByIdVariedadIdTipoPIdEva(
                            cursor.getInt(4),
                            temp.getIdEvaluacion(),
                            new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso(),temp.isMatSec());

                    if(tol != null && !temp.isMatSec()){
                        temp.setIdTol(tol.getId());
                        temp.setTolMax(tol.getTolMax());
                        temp.setTolMin(tol.getTolMin());

                        if(tol.getId()>0){
                            if(temp.getTolMin()>temp.getPorcentaje()){
                                if(temp.isMatSec()){
                                    temp.setCalificacion(3);
                                }else {
                                    temp.setCalificacion(1);
                                }
                            }else {
                                if(temp.getTolMax()>=temp.getPorcentaje()){
                                    if(temp.isMatSec()){
                                        temp.setCalificacion(1);
                                    }else {
                                        temp.setCalificacion(2);
                                    }
                                }else {
                                    temp.setCalificacion(3);
                                }
                            }
                        }else {
                            temp.setCalificacion(0);
                        }
                    }
                    Log.d(TAG,"consultarByid, CALIF:"+ temp.getCalificacion());
                    break;
                case 2:
                    cursor= db.rawQuery(
                            "SELECT " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+", " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+", "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+", "+
                                    "M."+TABLE_MUESTRA_COL_CANTIDAD+", "+
                                    "R."+TABLE_PRODUCCION_COL_IDVARIEDAD+
                                    " FROM "+
                                    TABLE_EVALUACIONDETALLE+" as ED,"+
                                    TABLE_MUESTRA+" as M, "+

                                    TABLE_PRODUCCION+" as R"+
                                    " WHERE " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+" = "+String.valueOf(id)+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+" = "+"M."+TABLE_MUESTRA_COL_ID+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_IDPROCESO+" = "+"R."+TABLE_RECEPCION_COL_ID
                            ,null);

                    cursor.moveToFirst();
                    temp.setId(cursor.getInt(0));
                    temp.setIdEvaluacion(cursor.getInt(1));
                    temp.setIdMuestra(cursor.getInt(2));
                    nFrutos = cursor.getInt(3);
                    //Log.d(TAG," nFrutos->"+nFrutos);
                    temp.setNameEvaluacion(new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion()).getName());
                    //Log.d(TAG," ->"+temp.getNameEvaluacion());

                    //VERIFICANDO SI ES DEFECTO O MATERIA SECA

                    tempEva = new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion());
                    temp.setMatSec(tempEva.isMateriaSeca());
                    temp.setDefecto(tempEva.isDefecto());

                    if (temp.isMatSec()) {
                        Log.d(TAG, "IS MATERIA SECA cons by id");
                    }
                    if (temp.isDefecto()) {
                        Log.d(TAG, "IS defecto cons by id");
                    }

                     cant = 0.0f;
                    if(!temp.isMatSec()){
                        for(CriterioDetalleVO cri : new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(temp.getId())){
                            if(!cri.getTipoDatoCriterio().equals("TEXTO")) {
                                cant = cant + 1.0f * (cri.getValor() == null || cri.getValor().isEmpty() || cri.getValor() == "" ? 0.0f : Float.valueOf(cri.getValor()));
                            }
                        }
                    }

                  //  Log.d(TAG,"CANTIDAD: "+cant);

                   // Log.d(TAG,"POCENTAJET: "+(cant*100.00000f)/nFrutos);
                    temp.setCantidad(cant);

                    if(temp.isMatSec()){
                        temp.setPorcentaje(cant);
                    }else {
                        temp.setPorcentaje(cant*100.00000f/nFrutos);
                    }

                    //buscando tolerancia de  evaluacion
                    tol = consTolEvaByIdVariedadIdTipoPIdEva(
                            cursor.getInt(4),
                            temp.getIdEvaluacion(),
                            new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso(),temp.isMatSec());

                    if(tol != null && !temp.isMatSec()){
                        temp.setIdTol(tol.getId());
                        temp.setTolMax(tol.getTolMax());
                        temp.setTolMin(tol.getTolMin());

                        if(tol.getId()>0){
                            if(temp.getTolMin()>temp.getPorcentaje()){
                                if(temp.isMatSec()){
                                    temp.setCalificacion(3);
                                }else {
                                    temp.setCalificacion(1);
                                }
                            }else {
                                if(temp.getTolMax()>=temp.getPorcentaje()){
                                    if(temp.isMatSec()){
                                        temp.setCalificacion(1);
                                    }else {
                                        temp.setCalificacion(2);
                                    }
                                }else {
                                    temp.setCalificacion(3);
                                }
                            }
                        }else {
                            temp.setCalificacion(0);
                        }
                    }
                    Log.d(TAG,"consultarByid, CALIF:"+ temp.getCalificacion());
                    break;
                case 3:
                    cursor= db.rawQuery(
                            "SELECT " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+", " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+", "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+" "+
                                    " FROM "+
                                    TABLE_EVALUACIONDETALLE+" as ED"+
                                    " WHERE " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+" = "+String.valueOf(id)
                            ,null);

                    cursor.moveToFirst();
                    nFrutos=0;
                    temp.setId(cursor.getInt(0));
                    temp.setIdEvaluacion(cursor.getInt(1));
                    temp.setIdMuestra(cursor.getInt(2));
                    Log.d(TAG," nFrutos->"+nFrutos);
                    temp.setNameEvaluacion(new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion()).getName());
                    Log.d(TAG," ->"+temp.getNameEvaluacion());

                    temp.setMatSec(false);
                    temp.setDefecto(false);



                    Log.d(TAG,"consultarByid, CALIF:"+ temp.getCalificacion());
                    break;
                case 4:
                    cursor= db.rawQuery(
                            "SELECT " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+", " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+", "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+", "+
                                    "M."+TABLE_MUESTRA_COL_CANTIDAD+", "+
                                    "R."+TABLE_DESCARTE_COL_IDVARIEDAD+
                                    " FROM "+
                                    TABLE_EVALUACIONDETALLE+" as ED,"+
                                    TABLE_MUESTRA+" as M, "+

                                    TABLE_DESCARTE+" as R"+

                                    " WHERE " +
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+" = "+String.valueOf(id)+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+" = "+"M."+TABLE_MUESTRA_COL_ID+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_IDPROCESO+" = "+"R."+TABLE_DESCARTE_COL_ID
                            ,null);

                    cursor.moveToFirst();
                    temp.setId(cursor.getInt(0));
                    temp.setIdEvaluacion(cursor.getInt(1));
                    temp.setIdMuestra(cursor.getInt(2));
                    nFrutos = cursor.getInt(3);
                    Log.d(TAG," nFrutos->"+nFrutos);
                    temp.setNameEvaluacion(new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion()).getName());
                    Log.d(TAG," ->"+temp.getNameEvaluacion());

                    //VERIFICANDO SI ES DEFECTO O MATERIA SECA

                    tempEva = new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion());
                    temp.setMatSec(tempEva.isMateriaSeca());
                    temp.setDefecto(tempEva.isDefecto());

                    if (temp.isMatSec()) {
                        Log.d(TAG, "IS MATERIA SECA cons by id");
                    }
                    if (temp.isDefecto()) {
                        Log.d(TAG, "IS defecto cons by id");
                    }

                    cant = 0.0f;
                    if(!temp.isMatSec()){
                        for(CriterioDetalleVO cri : new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(temp.getId())){
                            if(!cri.getTipoDatoCriterio().equals("TEXTO")) {
                                cant = cant + 1.0f * (cri.getValor() == null || cri.getValor().isEmpty() || cri.getValor() == "" ? 0.0f : Float.valueOf(cri.getValor()));
                            }
                        }
                    }

                    //   Log.d(TAG,"CANTIDAD: "+cant);

                    //    Log.d(TAG,"POCENTAJET: "+(cant*100.00000f)/nFrutos);
                    temp.setCantidad(cant);

                    if(temp.isMatSec()){
                        temp.setPorcentaje(cant);
                    }else {
                        temp.setPorcentaje(cant*100.00000f/nFrutos);
                    }

                    //buscando tolerancia de  evaluacion
                    tol = consTolEvaByIdVariedadIdTipoPIdEva(
                            cursor.getInt(4),
                            temp.getIdEvaluacion(),
                            new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso(),temp.isMatSec());

                    if(tol != null && !temp.isMatSec()){
                        temp.setIdTol(tol.getId());
                        temp.setTolMax(tol.getTolMax());
                        temp.setTolMin(tol.getTolMin());

                        if(tol.getId()>0){
                            if(temp.getTolMin()>temp.getPorcentaje()){
                                if(temp.isMatSec()){
                                    temp.setCalificacion(3);
                                }else {
                                    temp.setCalificacion(1);
                                }
                            }else {
                                if(temp.getTolMax()>=temp.getPorcentaje()){
                                    if(temp.isMatSec()){
                                        temp.setCalificacion(1);
                                    }else {
                                        temp.setCalificacion(2);
                                    }
                                }else {
                                    temp.setCalificacion(3);
                                }
                            }
                        }else {
                            temp.setCalificacion(0);
                        }
                    }
                    Log.d(TAG,"consultarByid, CALIF:"+ temp.getCalificacion());
                    break;
            }

            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"cons by id"+e.toString());
        }finally {
            db.close();
            c.close();
        }

        return temp;
    }


    public List<EvaluacionDetalleVO> listarAll() {
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<EvaluacionDetalleVO> evaluacionDetalleVOList = new ArrayList<>();
        try {
            TolEvaVO tol;
            float cant;
            EvaluacionVO tempEva;
            int TP = new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso();
            int nFrutos;
            Cursor cursor ;
            cursor= db.rawQuery(
                    "SELECT " +
                            " * "+
                            " FROM "+
                            TABLE_EVALUACIONDETALLE
                    ,null);

            switch (TP) {
                case 1:

                    cursor = db.rawQuery(
                            "SELECT " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + ", " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + ", " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + ", " +
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +
                                    "R." + TABLE_RECEPCION_COL_IDVARIEDAD +
                                    " FROM " +
                                    TABLE_EVALUACIONDETALLE + " as ED, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_RECEPCION + " as R" +
                                    " WHERE " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + " = " + "M." + TABLE_MUESTRA_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_RECEPCION_COL_ID
                            , null);
                    Log.d(TAG, "********************" + cursor.getCount());
                    while (cursor.moveToNext()) {
                        EvaluacionDetalleVO temp = new EvaluacionDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setIdEvaluacion(cursor.getInt(1));
                        temp.setIdMuestra(cursor.getInt(2));
                        // Log.d(TAG, "id Muestra:" + temp.getIdMuestra());
                        nFrutos = cursor.getInt(3);
                        temp.setNameEvaluacion(new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion()).getName());
                        // Log.d(TAG, " ->" + temp.getNameEvaluacion());
                        // Log.d(TAG, " nFrutos->" + nFrutos);

                        //VERIFICANDO SI ES DEFECTO O MATERIA SECA
                        tempEva = new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion());
                        temp.setMatSec(tempEva.isMateriaSeca());
                        temp.setDefecto(tempEva.isDefecto());
                        if (temp.isMatSec()) {
                            Log.d(TAG, "IS MATERIA SECA");
                        }
                        if (temp.isDefecto()) {
                            Log.d(TAG, "IS defecto");
                        }
                        evaluacionDetalleVOList.add(temp);
                        for (EvaluacionDetalleVO evaDe : evaluacionDetalleVOList) {
                            if (!evaDe.isMatSec()) {
                                cant = 0.0f;
                                for (CriterioDetalleVO cri : new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(evaDe.getId())) {
                                    if(!cri.getTipoDatoCriterio().equals("TEXTO")) {
                                        cant = cant + (cri.getValor() == null || cri.getValor().isEmpty() || cri.getValor() == "" ? 0.0f : Float.valueOf(cri.getValor()));
                                    }
                                }
                                evaDe.setCantidad(cant);
                                if (evaDe.isMatSec()) {
                                    evaDe.setPorcentaje(cant);
                                } else {
                                    evaDe.setPorcentaje(cant * 100.00000f / nFrutos);
                                }
                            }
                        }
                        //buscando tolerancia de  evaluacion
                        tol = consTolEvaByIdVariedadIdTipoPIdEva(
                                cursor.getInt(4),
                                temp.getIdEvaluacion(),
                                new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso(), temp.isMatSec());

                        if (tol != null && !temp.isMatSec()) {
                            temp.setIdTol(tol.getId());
                            temp.setTolMax(tol.getTolMax());
                            temp.setTolMin(tol.getTolMin());

                            if (tol.getId() > 0) {
                                if (temp.getTolMin() > temp.getPorcentaje()) {
                                    if (temp.isMatSec()) {
                                        temp.setCalificacion(3);
                                    } else {
                                        temp.setCalificacion(1);
                                    }
                                } else {
                                    if (temp.getTolMax() >= temp.getPorcentaje()) {
                                        if (temp.isMatSec()) {
                                            temp.setCalificacion(1);
                                        } else {
                                            temp.setCalificacion(2);
                                        }
                                    } else {
                                        temp.setCalificacion(3);
                                    }
                                }
                            } else {
                                temp.setCalificacion(0);
                            }
                        }

                        Log.d(TAG, "listarAll, CALIF:" + temp.getCalificacion());


                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
            /*
            Log.d(TAG, "********************");
            Log.d(TAG, "numero de evas:" + evaluacionDetalleVOList.size());
            Log.d(TAG, "********************");

            */
                break;

                case 2:
                    cursor = db.rawQuery(
                            "SELECT " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + ", " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + ", " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + ", " +
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +
                                    "R." + TABLE_PRODUCCION_COL_IDVARIEDAD +
                                    " FROM " +
                                    TABLE_EVALUACIONDETALLE + " as ED, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_PRODUCCION + " as R" +
                                    " WHERE " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + " = " + "M." + TABLE_MUESTRA_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_PRODUCCION_COL_ID
                            , null);
                    Log.d(TAG, "********************" + cursor.getCount());
                    while (cursor.moveToNext()) {
                        EvaluacionDetalleVO temp = new EvaluacionDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setIdEvaluacion(cursor.getInt(1));
                        temp.setIdMuestra(cursor.getInt(2));
                        // Log.d(TAG, "id Muestra:" + temp.getIdMuestra());
                        nFrutos = cursor.getInt(3);
                        temp.setNameEvaluacion(new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion()).getName());
                        // Log.d(TAG, " ->" + temp.getNameEvaluacion());
                        // Log.d(TAG, " nFrutos->" + nFrutos);

                        //VERIFICANDO SI ES DEFECTO O MATERIA SECA
                        tempEva = new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion());
                        temp.setMatSec(tempEva.isMateriaSeca());
                        temp.setDefecto(tempEva.isDefecto());
                        if (temp.isMatSec()) {
                            Log.d(TAG, "IS MATERIA SECA");
                        }
                        if (temp.isDefecto()) {
                            Log.d(TAG, "IS defecto");
                        }
                        evaluacionDetalleVOList.add(temp);
                        for (EvaluacionDetalleVO evaDe : evaluacionDetalleVOList) {
                            if (!evaDe.isMatSec()) {
                                cant = 0.0f;
                                for (CriterioDetalleVO cri : new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(evaDe.getId())) {
                                    if(!cri.getTipoDatoCriterio().equals("TEXTO")) {
                                        cant = cant + (cri.getValor() == null || cri.getValor().isEmpty() || cri.getValor() == "" ? 0.0f : Float.valueOf(cri.getValor()));
                                    }
                                }
                                evaDe.setCantidad(cant);
                                if (evaDe.isMatSec()) {
                                    evaDe.setPorcentaje(cant);
                                } else {
                                    evaDe.setPorcentaje(cant * 100.00000f / nFrutos);
                                }
                            }
                        }
                        //buscando tolerancia de  evaluacion
                        tol = consTolEvaByIdVariedadIdTipoPIdEva(
                                cursor.getInt(4),
                                temp.getIdEvaluacion(),
                                new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso(), temp.isMatSec());

                        if (tol != null && !temp.isMatSec()) {
                            temp.setIdTol(tol.getId());
                            temp.setTolMax(tol.getTolMax());
                            temp.setTolMin(tol.getTolMin());

                            if (tol.getId() > 0) {
                                if (temp.getTolMin() > temp.getPorcentaje()) {
                                    if (temp.isMatSec()) {
                                        temp.setCalificacion(3);
                                    } else {
                                        temp.setCalificacion(1);
                                    }
                                } else {
                                    if (temp.getTolMax() >= temp.getPorcentaje()) {
                                        if (temp.isMatSec()) {
                                            temp.setCalificacion(1);
                                        } else {
                                            temp.setCalificacion(2);
                                        }
                                    } else {
                                        temp.setCalificacion(3);
                                    }
                                }
                            } else {
                                temp.setCalificacion(0);
                            }
                        }

                        Log.d(TAG, "listarAll, CALIF:" + temp.getCalificacion());


                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }

                    break;

                case 3:

                    cursor = db.rawQuery(
                            "SELECT " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + ", " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + ", " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + " " +
                                    " FROM " +
                                    TABLE_EVALUACIONDETALLE + " as ED "

                            , null);
                    Log.d(TAG, "********************" + cursor.getCount());
                    while (cursor.moveToNext()) {
                        EvaluacionDetalleVO temp = new EvaluacionDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setIdEvaluacion(cursor.getInt(1));
                        temp.setIdMuestra(cursor.getInt(2));
                        // Log.d(TAG, "id Muestra:" + temp.getIdMuestra());
                        nFrutos=0;
                        temp.setNameEvaluacion(new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion()).getName());
                        // Log.d(TAG, " ->" + temp.getNameEvaluacion());
                        // Log.d(TAG, " nFrutos->" + nFrutos);

                        //VERIFICANDO SI ES DEFECTO O MATERIA SECA
                        tempEva = new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion());
                        temp.setMatSec(tempEva.isMateriaSeca());
                        temp.setDefecto(tempEva.isDefecto());
                        if (temp.isMatSec()) {
                            Log.d(TAG, "IS MATERIA SECA");
                        }
                        if (temp.isDefecto()) {
                            Log.d(TAG, "IS defecto");
                        }
                        evaluacionDetalleVOList.add(temp);
                        for (EvaluacionDetalleVO evaDe : evaluacionDetalleVOList) {
                            if (!evaDe.isMatSec()) {
                                cant = 0.0f;
                                for (CriterioDetalleVO cri : new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(evaDe.getId())) {
                                    if(!cri.getTipoDatoCriterio().equals("TEXTO")) {
                                        cant = cant + (cri.getValor() == null || cri.getValor().isEmpty() || cri.getValor() == "" ? 0.0f : Float.valueOf(cri.getValor()));
                                    }
                                }
                                evaDe.setCantidad(cant);
                                if (evaDe.isMatSec()) {
                                    evaDe.setPorcentaje(cant);
                                } else {
                                    evaDe.setPorcentaje(cant * 100.00000f / nFrutos);
                                }
                            }
                        }
                        //buscando tolerancia de  evaluacion
                        tol = consTolEvaByIdVariedadIdTipoPIdEva(
                                0,
                                temp.getIdEvaluacion(),
                                new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso(), temp.isMatSec());

                        if (tol != null && !temp.isMatSec()) {
                            temp.setIdTol(tol.getId());
                            temp.setTolMax(tol.getTolMax());
                            temp.setTolMin(tol.getTolMin());

                            if (tol.getId() > 0) {
                                if (temp.getTolMin() > temp.getPorcentaje()) {
                                    if (temp.isMatSec()) {
                                        temp.setCalificacion(3);
                                    } else {
                                        temp.setCalificacion(1);
                                    }
                                } else {
                                    if (temp.getTolMax() >= temp.getPorcentaje()) {
                                        if (temp.isMatSec()) {
                                            temp.setCalificacion(1);
                                        } else {
                                            temp.setCalificacion(2);
                                        }
                                    } else {
                                        temp.setCalificacion(3);
                                    }
                                }
                            } else {
                                temp.setCalificacion(0);
                            }
                        }

                        Log.d(TAG, "listarAll, CALIF:" + temp.getCalificacion());


                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
            /*
            Log.d(TAG, "********************");
            Log.d(TAG, "numero de evas:" + evaluacionDetalleVOList.size());
            Log.d(TAG, "********************");

            */
                    break;
                case 4:

                    cursor = db.rawQuery(
                            "SELECT " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + ", " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + ", " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + ", " +
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +
                                    "R." + TABLE_DESCARTE_COL_IDVARIEDAD +
                                    " FROM " +
                                    TABLE_EVALUACIONDETALLE + " as ED, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_DESCARTE + " as R" +
                                    " WHERE " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + " = " + "M." + TABLE_MUESTRA_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_DESCARTE_COL_ID
                            , null);
                    Log.d(TAG, "********************" + cursor.getCount());
                    while (cursor.moveToNext()) {
                        EvaluacionDetalleVO temp = new EvaluacionDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setIdEvaluacion(cursor.getInt(1));
                        temp.setIdMuestra(cursor.getInt(2));
                        // Log.d(TAG, "id Muestra:" + temp.getIdMuestra());
                        nFrutos = cursor.getInt(3);
                        temp.setNameEvaluacion(new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion()).getName());
                        // Log.d(TAG, " ->" + temp.getNameEvaluacion());
                        // Log.d(TAG, " nFrutos->" + nFrutos);

                        //VERIFICANDO SI ES DEFECTO O MATERIA SECA
                        tempEva = new EvaluacionDAO(ctx).consultarByid(temp.getIdEvaluacion());
                        temp.setMatSec(tempEva.isMateriaSeca());
                        temp.setDefecto(tempEva.isDefecto());
                        if (temp.isMatSec()) {
                            Log.d(TAG, "IS MATERIA SECA");
                        }
                        if (temp.isDefecto()) {
                            Log.d(TAG, "IS defecto");
                        }
                        evaluacionDetalleVOList.add(temp);
                        for (EvaluacionDetalleVO evaDe : evaluacionDetalleVOList) {
                            if (!evaDe.isMatSec()) {
                                cant = 0.0f;
                                for (CriterioDetalleVO cri : new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(evaDe.getId())) {
                                    if (!cri.getTipoDatoCriterio().equals("TEXTO")) {
                                        cant = cant + (cri.getValor() == null || cri.getValor().isEmpty() || cri.getValor() == "" ? 0.0f : Float.valueOf(cri.getValor()));
                                    }
                                }
                                evaDe.setCantidad(cant);
                                if (evaDe.isMatSec()) {
                                    evaDe.setPorcentaje(cant);
                                } else {
                                    evaDe.setPorcentaje(cant * 100.00000f / nFrutos);
                                }
                            }
                        }
                        //buscando tolerancia de  evaluacion
                        tol = consTolEvaByIdVariedadIdTipoPIdEva(
                                cursor.getInt(4),
                                temp.getIdEvaluacion(),
                                new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso(), temp.isMatSec());

                        if (tol != null && !temp.isMatSec()) {
                            temp.setIdTol(tol.getId());
                            temp.setTolMax(tol.getTolMax());
                            temp.setTolMin(tol.getTolMin());

                            if (tol.getId() > 0) {
                                if (temp.getTolMin() > temp.getPorcentaje()) {
                                    if (temp.isMatSec()) {
                                        temp.setCalificacion(3);
                                    } else {
                                        temp.setCalificacion(1);
                                    }
                                } else {
                                    if (temp.getTolMax() >= temp.getPorcentaje()) {
                                        if (temp.isMatSec()) {
                                            temp.setCalificacion(1);
                                        } else {
                                            temp.setCalificacion(2);
                                        }
                                    } else {
                                        temp.setCalificacion(3);
                                    }
                                }
                            } else {
                                temp.setCalificacion(0);
                            }
                        }

                        Log.d(TAG, "listarAll, CALIF:" + temp.getCalificacion());


                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
            /*
            Log.d(TAG, "********************");
            Log.d(TAG, "numero de evas:" + evaluacionDetalleVOList.size());
            Log.d(TAG, "********************");

            */
                    break;

            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "error EVA 2:" + e.toString());
        } finally {
            db.close();
            c.close();
        }
        return evaluacionDetalleVOList;
    }


    public TolEvaVO consTolEvaByIdVariedadIdTipoPIdEva(int idVariedad,int idEva,int idTipoProceso, boolean isMateriaseca){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        TolEvaVO temp = null;
        Log.d(TAG,"buscando tol eva : idVar:"+idVariedad+" idEva:"+idEva+" idTipoP:"+idTipoProceso);

        try{
            temp = new TolEvaVO();
            String query;
            if(isMateriaseca){
                query =  "SELECT " +
                        "T."+TABLE_TOLEVALUACION_COL_ID+", " +
                        "T."+TABLE_TOLEVALUACION_COL_IDCONFEVALUACION+", "+
                        "T."+TABLE_TOLEVALUACION_COL_IDVARIEDAD+", "+
                        "T."+TABLE_TOLEVALUACION_COL_MINIMO+", "+
                        "T."+TABLE_TOLEVALUACION_COL_MAXIMO+" "+
                        " FROM "+
                        TABLE_TOLEVALUACION+" as T, "+
                        TABLE_CONFEVALUACION+" as CE "+
                        " WHERE " +
                        "T."+TABLE_TOLEVALUACION_COL_IDVARIEDAD+" = "+String.valueOf(idVariedad)+
                        " AND "+
                        "CE."+TABLE_CONFEVALUACION_COL_IDEVALUACION+" = "+String.valueOf(idEva)+
                        " AND "+
                        "CE."+TABLE_CONFEVALUACION_COL_IDTIPOPROCESO+" = "+String.valueOf(idTipoProceso)+
                        " AND "+
                        "CE."+TABLE_CONFEVALUACION_COL_ID+" = "+"T."+TABLE_TOLEVALUACION_COL_IDCONFEVALUACION;
            }else {
                query =  "SELECT " +
                        "T."+TABLE_TOLEVALUACION_COL_ID+", " +
                        "T."+TABLE_TOLEVALUACION_COL_IDCONFEVALUACION+", "+
                        "T."+TABLE_TOLEVALUACION_COL_IDVARIEDAD+", "+
                        "T."+TABLE_TOLEVALUACION_COL_MINIMO+", "+
                        "T."+TABLE_TOLEVALUACION_COL_MAXIMO+" "+
                        " FROM "+
                        TABLE_TOLEVALUACION+" as T, "+
                        TABLE_CONFEVALUACION+" as CE "+
                        " WHERE " +
                        "CE."+TABLE_CONFEVALUACION_COL_IDEVALUACION+" = "+String.valueOf(idEva)+
                        " AND "+
                        "CE."+TABLE_CONFEVALUACION_COL_IDTIPOPROCESO+" = "+String.valueOf(idTipoProceso)+
                        " AND "+
                        "CE."+TABLE_CONFEVALUACION_COL_ID+" = "+"T."+TABLE_TOLEVALUACION_COL_IDCONFEVALUACION;
            }
            Cursor cursor = db.rawQuery(
                        query
                    ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setIdConfEva(cursor.getInt(1));
                temp.setIdVariedad(cursor.getInt(2));
                temp.setTolMin(cursor.getFloat(3));
                temp.setTolMax(cursor.getFloat(4));
                Log.d(TAG,"sen econtro id:"+temp.getId()+" idCE:"+temp.getIdConfEva()+" idV:"+temp.getIdVariedad()+" tolmin:"+temp.getTolMin()+" tolmax:"+temp.getTolMax());
            }else {
                temp = null;
                Log.d(TAG,"mo se encontro tol de eva");
            }

            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"cons by tol id"+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }


/*
    public EmpresaVO consultarEmpresaByIdFundo(int idFundo){
        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO empresaVO = null;
        try{
            empresaVO = new EmpresaVO();
            Cursor cursor = db.rawQuery(
                    " SELECT "+"E."+TABLE_EVALUACION_COL_ID+", "+"E."+TABLE_EVALUACION_COL_NAME+
                            " FROM "+TABLE_EVALUACION+" as E, "+TABLE_FUNDO+" as F"+
                            " WHERE "+"F."+TABLE_FUNDO_COL_ID+"="+  String.valueOf(idFundo)+
                            " AND "+"F."+TABLE_FUNDO_COL_IDEMPRESA+" = "+"E."+TABLE_EVALUACION_COL_ID, null);
            cursor.moveToFirst();
            empresaVO.setId(cursor.getInt(0));
            empresaVO.setName(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return empresaVO;
    }
  */

    public List<EvaluacionDetalleVO> listByIdMuestra(int idMuestra){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<EvaluacionDetalleVO> evaluacionDetalleVOList = new  ArrayList<>();
        try{
            TolEvaVO tol;
            float cant;
            EvaluacionVO tempEva;
            int TP = new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso();
            int nFrutos;
            Cursor cursor ;
            cursor= db.rawQuery(
                    "SELECT " +
                            " * "+
                            " FROM "+
                            TABLE_LOGINDATA
                    ,null);

            String consulta = "SELECT " +
                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + ", " +//0
                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + ", " +//1
                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + ", " +//2
                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//3
                    "R." + ((TP==1)?TABLE_RECEPCION_COL_IDVARIEDAD:(TP==2)?TABLE_PRODUCCION_COL_IDVARIEDAD:(TP==3)?TABLE_DESPACHO_COL_IDCULTIVO:(TP==4)?TABLE_DESCARTE_COL_IDVARIEDAD:TABLE_RECEPCION_COL_IDVARIEDAD )+ ", " +//4
                    "E." + TABLE_EVALUACION_COL_NAME + ", " +//5
                    "E." + TABLE_EVALUACION_COL_ISMATSEC +//6
                    " FROM " +
                    TABLE_EVALUACION + " as E, " +
                    TABLE_EVALUACIONDETALLE + " as ED, " +
                    TABLE_MUESTRA + " as M, " +
                    ((TP==1)?TABLE_RECEPCION:(TP==2)?TABLE_PRODUCCION:(TP==3)?TABLE_DESPACHO:(TP==4)?TABLE_DESCARTE:TABLE_RECEPCION ) + " as R" +
                    " WHERE " +
                    "E." + TABLE_EVALUACION_COL_ID + " = " + "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION +
                    " AND " +
                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + " = " + String.valueOf(idMuestra) +
                    " AND " +
                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA + " = " + "M." + TABLE_MUESTRA_COL_ID +
                    " AND " +
                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + ((TP==1)?TABLE_RECEPCION_COL_ID:(TP==2)?TABLE_PRODUCCION_COL_ID:(TP==3)?TABLE_DESPACHO_COL_ID:(TP==4)?TABLE_DESCARTE_COL_ID:TABLE_RECEPCION_COL_ID );

            cursor = db.rawQuery(consulta, null);
            Log.d(TAG, consulta);

            Log.d(TAG, "********************" + cursor.getCount());
                    while (cursor.moveToNext()) {
                        EvaluacionDetalleVO temp = new EvaluacionDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setIdEvaluacion(cursor.getInt(1));
                        temp.setIdMuestra(cursor.getInt(2));

                        nFrutos = cursor.getInt(3);
                        temp.setNameEvaluacion(cursor.getString(5));
                        Log.d(TAG, " ->" + temp.getNameEvaluacion());
                        Log.d(TAG, " nFrutos->" + nFrutos);

                        //VERIFICANDO SI ES DEFECTO O MATERIA SECA
                        temp.setMatSec(cursor.getInt(6) > 0);
                        temp.setDefecto(temp.getNameEvaluacion().toUpperCase().contains("DEFECTO"));

                        if (temp.isMatSec()) {
                            Log.d(TAG, "IS MATERIA SECA");
                        }
                        if (temp.isDefecto()) {
                            Log.d(TAG, "IS defecto");
                        }

                        evaluacionDetalleVOList.add(temp);

                        for (EvaluacionDetalleVO evaDe : evaluacionDetalleVOList) {
                            if (!evaDe.isMatSec()) {
                                cant = 0.0f;
                                for (CriterioDetalleVO cri : new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(evaDe.getId())) {
                                    if(!cri.getTipoDatoCriterio().equals("TEXTO")){
                                        cant = cant + (cri.getValor() == null || cri.getValor().isEmpty() || cri.getValor() == "" ? 0.0f : Float.valueOf(cri.getValor()));
                                    }
                                }
                                evaDe.setCantidad(cant);
                                if (evaDe.isMatSec()) {
                                    evaDe.setPorcentaje(cant);
                                } else {
                                    evaDe.setPorcentaje(cant * 100.00000f / nFrutos);
                                }
                            }
                        }
                        //buscando tolerancia de  evaluacion
                        tol = consTolEvaByIdVariedadIdTipoPIdEva(
                                cursor.getInt(4),
                                temp.getIdEvaluacion(),
                                new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso(), temp.isMatSec());

                        if (tol != null && !temp.isMatSec()) {
                            temp.setIdTol(tol.getId());
                            temp.setTolMax(tol.getTolMax());
                            temp.setTolMin(tol.getTolMin());

                            if (tol.getId() > 0) {
                                if (temp.getTolMin() > temp.getPorcentaje()) {
                                    if (temp.isMatSec()) {
                                        temp.setCalificacion(3);
                                    } else {
                                        temp.setCalificacion(1);
                                    }
                                } else {
                                    if (temp.getTolMax() >= temp.getPorcentaje()) {
                                        if (temp.isMatSec()) {
                                            temp.setCalificacion(1);
                                        } else {
                                            temp.setCalificacion(2);
                                        }
                                    } else {
                                        temp.setCalificacion(3);
                                    }
                                }
                            } else {
                                temp.setCalificacion(0);
                            }
                        }

                        Log.d(TAG, "listByIdMuestra, CALIF:" + temp.getCalificacion());
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "********************");
                    Log.d(TAG, "numero de evas:" + evaluacionDetalleVOList.size());
                    Log.d(TAG, "********************");

            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"error EVA 2:"+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return evaluacionDetalleVOList;
    }




    public boolean clearTableUpload(int id){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        List<CriterioDetalleVO> criterioDetalleVOList = new CriterioDetalleDAO(ctx).listByIdEvaluacionDetalle(id);

        for(CriterioDetalleVO cri : criterioDetalleVOList){
            new CriterioDetalleDAO(ctx).clearTableUpload(cri.getId());
        }

        String[] parametros = {
                String.valueOf(id)
        };

        int res = db.delete(TABLE_EVALUACIONDETALLE,TABLE_EVALUACIONDETALLE_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean deleteById(int id){

        new CriterioDetalleDAO(ctx).deleteByIdEvaluacionDetalle(id);

        boolean flag = false;
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        int res = db.delete(TABLE_EVALUACIONDETALLE,TABLE_EVALUACIONDETALLE_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
            Log.d(TAG,"evaluacion detalle borrada");
        }
        db.close();
        c.close();
        return flag;
    }

    public boolean deleteByIdMuestra(int idMuestra){

        List<EvaluacionDetalleVO> evaluacionDetalleVOList = new EvaluacionDetalleDAO(ctx).listByIdMuestra(idMuestra);
        for(EvaluacionDetalleVO e: evaluacionDetalleVOList){
            new CriterioDetalleDAO(ctx).deleteByIdEvaluacionDetalle(e.getId());
        }

        boolean flag = false;
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(idMuestra),
                };
        int res = db.delete(TABLE_EVALUACIONDETALLE,TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        c.close();
        return flag;
    }
}
