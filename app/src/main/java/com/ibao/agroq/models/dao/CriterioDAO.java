package com.ibao.agroq.models.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.CriterioVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIODETALLE;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIODETALLE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIODETALLE_COL_IDCRITERIO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_CODIGO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_IDEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_IDTIPOPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_ISPHOTO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TIPODATO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TOLERANCIAMAXIMO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TOLERANCIAMINIMO;

public class CriterioDAO {

    Context ctx;

    String TAG= CriterioDAO.class.getSimpleName();

    public CriterioDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CRITERIO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CRITERIO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
/*
    public boolean insertar(int id, String name,Boolean isFotografiable, float tolMin, float tolMax, String tipoDato, int idTipoEval){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_CRITERIO_COL_ID,id);
        values.put(TABLE_CRITERIO_COL_NAME,name);
        values.put(TABLE_CRITERIO_COL_ISPHOTO,isFotografiable);
        values.put(TABLE_CRITERIO_COL_TOLERANCIAMINIMO,tolMin);
        values.put(TABLE_CRITERIO_COL_TOLERANCIAMAXIMO,tolMax);
        values.put(TABLE_CRITERIO_COL_TIPODATO,tipoDato);
        values.put(TABLE_CRITERIO_COL_IDEVALUACION,idTipoEval);

        Long temp = db.insert(TABLE_CRITERIO,TABLE_CRITERIO_COL_ID,values);
        db.close();
        return temp > 0;
    }
    */

    public CriterioVO consultarByid(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        CriterioVO criterioVO = null;
        try{
            criterioVO = new CriterioVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "C."+TABLE_CRITERIO_COL_ID+", " +
                            "C."+TABLE_CRITERIO_COL_CODIGO+", " +
                            "C."+TABLE_CRITERIO_COL_NAME+", "+
                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+
                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+
                            "C."+TABLE_CRITERIO_COL_IDEVALUACION+", "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOPROCESO+" " +
                            " FROM "+
                            TABLE_CRITERIO+" as C"+
                        " WHERE "+
                            "C."+TABLE_CRITERIO_COL_ID+" = "+String.valueOf(id)
                    ,null);
            cursor.moveToFirst();
            criterioVO.setId(cursor.getInt(0));
            criterioVO.setCodigo(cursor.getInt(1));
            criterioVO.setName(cursor.getString(2));
            criterioVO.setTipoDato(cursor.getString(3));
            criterioVO.setFotografiable(cursor.getInt(4)>0);
            criterioVO.setTolMin(cursor.getFloat(5));
            criterioVO.setTolMax(cursor.getFloat(6));
            criterioVO.setIdEvaluacion(cursor.getInt(7));
            criterioVO.setIdTipoProceso(cursor.getInt(8));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,"Criterio Eliminado al Actualizar\n(X_X)",Toast.LENGTH_SHORT).show();
            Log.d(TAG,"CONS 1 :"+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return criterioVO;
    }

    public CriterioVO consultarByIdCriterioDetalle(int idCriterioDetalle){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        CriterioVO criterioVO = null;
        try{
            criterioVO = new CriterioVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "C."+TABLE_CRITERIO_COL_ID+", " +
                            "C."+TABLE_CRITERIO_COL_CODIGO+", " +
                            "C."+TABLE_CRITERIO_COL_NAME+", "+
                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+
                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+
                            "C."+TABLE_CRITERIO_COL_IDEVALUACION+", "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOPROCESO+" " +
                        " FROM "+
                            TABLE_CRITERIO+" as C, "+
                            TABLE_CRITERIODETALLE+" as CD"+
                        " WHERE "+
                            "CD."+TABLE_CRITERIODETALLE_COL_ID+"="+  String.valueOf(idCriterioDetalle)+
                            " AND "+
                            "CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+" = "+"C."+TABLE_CRITERIO_COL_ID

            , null);
            cursor.moveToFirst();
            criterioVO.setId(cursor.getInt(0));
            criterioVO.setCodigo(cursor.getInt(1));
            criterioVO.setName(cursor.getString(2));
            criterioVO.setTipoDato(cursor.getString(3));
            criterioVO.setFotografiable(cursor.getInt(4)>0);
            criterioVO.setTolMin(cursor.getFloat(5));
            criterioVO.setTolMax(cursor.getFloat(6));
            criterioVO.setIdEvaluacion(cursor.getInt(7));
            criterioVO.setIdTipoProceso(cursor.getInt(8));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,"cons by idCriDe"+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,e.toString());
        }finally {
            db.close();
            c.close();
        }
        c.close();
        return criterioVO;
    }

    public List<CriterioVO> listarByIdEvaluacionIdTipoProceso(int idEvaluacion,int idTipoProceso){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<CriterioVO> criterioVOList = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "C."+TABLE_CRITERIO_COL_ID+", " +
                            "C."+TABLE_CRITERIO_COL_CODIGO+", " +
                            "C."+TABLE_CRITERIO_COL_NAME+", "+
                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+
                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+
                            "C."+TABLE_CRITERIO_COL_IDEVALUACION+", "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOPROCESO+" " +
                        " FROM "+
                            TABLE_CRITERIO+" as C "+
                        " WHERE "+
                            "C."+TABLE_CRITERIO_COL_IDEVALUACION+"="+  String.valueOf(idEvaluacion)+
                            " AND "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOPROCESO+"="+  String.valueOf(idTipoProceso)

                    ,null);
            Log.d(TAG,"LISTANDO CRITERIOS N="+cursor.getCount()+" idE;"+idEvaluacion+" idTP;"+idTipoProceso);

            while (cursor.moveToNext()){
                CriterioVO criterioVO = new CriterioVO();
                criterioVO.setId(cursor.getInt(0));
                criterioVO.setCodigo(cursor.getInt(1));
                criterioVO.setName(cursor.getString(2));
                criterioVO.setTipoDato(cursor.getString(3));
                criterioVO.setFotografiable(cursor.getInt(4)>0);
                criterioVO.setTolMin(cursor.getFloat(5));
                criterioVO.setTolMax(cursor.getFloat(6));
                criterioVO.setIdEvaluacion(cursor.getInt(7));
                criterioVO.setIdTipoProceso(cursor.getInt(8));
                criterioVOList.add(criterioVO);


            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"ExLISTAR : "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return criterioVOList;
    }

    public List<CriterioVO> listarAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<CriterioVO> criterioVOList = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "C."+TABLE_CRITERIO_COL_ID+", " +
                            "C."+TABLE_CRITERIO_COL_CODIGO+", "+
                            "C."+TABLE_CRITERIO_COL_NAME+", "+
                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+
                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+
                            "C."+TABLE_CRITERIO_COL_IDEVALUACION+", "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOPROCESO+" "+
                            " FROM "+
                            TABLE_CRITERIO+" as C "
                    ,null);
            while (cursor.moveToNext()){
                CriterioVO criterioVO = new CriterioVO();
                criterioVO.setId(cursor.getInt(0));
                criterioVO.setCodigo(cursor.getInt(1));
                criterioVO.setName(cursor.getString(2));
                criterioVO.setTipoDato(cursor.getString(3));
                criterioVO.setFotografiable(cursor.getInt(4)>0);
                criterioVO.setTolMin(cursor.getFloat(5));
                criterioVO.setTolMax(cursor.getFloat(6));
                criterioVO.setIdEvaluacion(cursor.getInt(7));
                criterioVO.setIdTipoProceso(cursor.getInt(8));
                criterioVOList.add(criterioVO);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,e.toString());
        }finally {
            db.close();
            c.close();
        }

        return criterioVOList;
    }

}
