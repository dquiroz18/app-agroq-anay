package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.EvaluacionVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CONFEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_CONFEVALUACION_COL_IDEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_CONFEVALUACION_COL_IDTIPOPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE_COL_IDEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE_COL_IDMUESTRA;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_CULTIVO;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_ISMATSEC;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_ID;

public class EvaluacionDAO {

    String TAG = EvaluacionDAO.class.getSimpleName();

    Context ctx;

    public EvaluacionDAO(Context ctx) {
       this.ctx = ctx;
    }

    public boolean borrarTable() {
        boolean flag = false;
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_EVALUACION, null, null);
        if (res > 0) {
            flag = true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insertar(int id, String name, int idCultivo) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_EVALUACION_COL_ID, id);
        values.put(TABLE_EVALUACION_COL_NAME, name);
        values.put(TABLE_EVALUACION_COL_CULTIVO, idCultivo);
        Long temp = db.insert(TABLE_EVALUACION, TABLE_EVALUACION_COL_ID, values);
        db.close();
        conn.close();
        return (temp > 0) ? true : false;
    }

    public EvaluacionVO consultarByid(int id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        EvaluacionVO temp = null;
        try {
            temp = new EvaluacionVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E." + TABLE_EVALUACION_COL_ID + ", " +
                            "E." + TABLE_EVALUACION_COL_NAME + ", " +
                            "E." + TABLE_EVALUACION_COL_ISMATSEC + ", " +
                            "E." + TABLE_EVALUACION_COL_CULTIVO +
                            " FROM " +
                            TABLE_EVALUACION + " as E" +
                            " WHERE " +
                            "E." + TABLE_EVALUACION_COL_ID + " = " + String.valueOf(id)
                    , null);
            cursor.moveToFirst();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
            temp.setMateriaSeca(cursor.getInt(2) > 0);
            temp.setCultivo(cursor.getInt(3));
            temp.setDefecto(temp.getName().toUpperCase().contains("DEFECTO"));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show();
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

    public List<EvaluacionVO> listByIdTipoProcesoIdMuestra(int idTipoProceso, int idMuestra) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<EvaluacionVO> evaluacionVOList = new ArrayList<>();
        try {

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E." + TABLE_EVALUACION_COL_ID + ", " +
                            "E." + TABLE_EVALUACION_COL_NAME + ", " +
                            "E." + TABLE_EVALUACION_COL_ISMATSEC + ", " +
                            "E." + TABLE_EVALUACION_COL_CULTIVO +
                            " FROM " +
                            TABLE_EVALUACION + " as E , " +
                            TABLE_CONFEVALUACION + " as CE " +
                            " WHERE " +
                            "CE." + TABLE_CONFEVALUACION_COL_IDTIPOPROCESO + " = " + String.valueOf(idTipoProceso) +
                            " AND " +
                            "CE." + TABLE_CONFEVALUACION_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                            " AND " +
                            "E." + TABLE_EVALUACION_COL_ID + " NOT IN " + " ( " +
                            "SELECT " +
                            "E." + TABLE_EVALUACION_COL_ID + " " +
                            " FROM " +
                            TABLE_EVALUACION + " as E , " +
                            TABLE_MUESTRA + " as M , " +
                            TABLE_EVALUACIONDETALLE + " as ED  " +
                            " WHERE " +
                            "M." + TABLE_MUESTRA_COL_ID + " = " + String.valueOf(idMuestra) +
                            " AND " +
                            "M." + TABLE_MUESTRA_COL_ID + " = " + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                            " AND " +
                            "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                            " ) " +
                            " ORDER BY " +
                            "E." + TABLE_EVALUACION_COL_NAME +
                            " COLLATE UNICODE ASC "
                    , null);

            while (cursor.moveToNext()) {
                EvaluacionVO temp = new EvaluacionVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setMateriaSeca(cursor.getInt(2) > 0);
                temp.setCultivo(cursor.getInt(3));
                temp.setDefecto(temp.getName().toUpperCase().contains("DEFECTO"));
                Log.d(TAG, "" + temp.getName());
                evaluacionVOList.add(temp);
                // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "error EVA " + e.toString());
        }finally {
            db.close();
            c.close();
        }
        return evaluacionVOList;
    }


    public List<EvaluacionVO> listByIdTipoProcesoIdMuestraDespacho(int idTipoProceso,int idMuestra){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<EvaluacionVO> evaluacionVOList = new  ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E."+TABLE_EVALUACION_COL_ID+", " +
                            "E."+TABLE_EVALUACION_COL_NAME+", "+
                            "E." + TABLE_EVALUACION_COL_ISMATSEC + ", " +
                            "E." + TABLE_EVALUACION_COL_CULTIVO +
                        " FROM "+
                            TABLE_EVALUACION+" as E , "+
                            TABLE_CONFEVALUACION+" as CE "+
                        " WHERE "+
                            "CE."+TABLE_CONFEVALUACION_COL_IDTIPOPROCESO+" = "+String.valueOf(idTipoProceso)+
                            " AND "+
                            "CE."+TABLE_CONFEVALUACION_COL_IDEVALUACION+" = "+"E."+TABLE_EVALUACION_COL_ID+
                            " AND "+
                            "E."+TABLE_EVALUACION_COL_ID+" NOT IN "+" ( "+
                                "SELECT " +
                                    "E."+TABLE_EVALUACION_COL_ID+" " +
                                " FROM "+
                                    TABLE_EVALUACION+" as E , "+
                                    TABLE_MUESTRA+" as M , "+
                                    TABLE_EVALUACIONDETALLE+" as ED  "+
                                " WHERE "+
                                    "M."+TABLE_MUESTRA_COL_ID+" = "+String.valueOf(idMuestra)+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_ID+" = "+"ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+" = "+"E."+TABLE_EVALUACION_COL_ID+
                                    " AND "+
                                    "E."+TABLE_EVALUACION_COL_ID+" != "+"7"+
                            " ) "+
                        " ORDER BY "+
                            "E."+TABLE_EVALUACION_COL_NAME+
                            " COLLATE UNICODE ASC "
                    ,null);

            while(cursor.moveToNext()){
                EvaluacionVO temp = new EvaluacionVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setMateriaSeca(cursor.getInt(2)>0);
                temp.setCultivo(cursor.getInt(3));
                temp.setDefecto(temp.getName().toUpperCase().contains("DEFECTO"));
                Log.d(TAG,""+temp.getName());
                evaluacionVOList.add(temp);
                // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"error EVA "+e.toString());
        }finally{
            db.close();
            c.close();

        }
        return evaluacionVOList;
    }

    public List<EvaluacionVO> listByIdTipoProceso(int idTipoProceso){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<EvaluacionVO> evaluacionVOList = new  ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E."+TABLE_EVALUACION_COL_ID+", " +
                            "E."+TABLE_EVALUACION_COL_NAME+", "+
                            "E."+TABLE_EVALUACION_COL_ISMATSEC+
                        " FROM "+
                            TABLE_EVALUACION+" as E ,"+
                            TABLE_CONFEVALUACION+" as CE"+
                        " WHERE "+
                            "CE."+TABLE_CONFEVALUACION_COL_IDTIPOPROCESO+" = "+String.valueOf(idTipoProceso)+
                            " AND "+
                            "CE."+TABLE_CONFEVALUACION_COL_IDEVALUACION+" = "+"E."+TABLE_EVALUACION_COL_ID+
                        " ORDER BY "+
                            "E."+TABLE_EVALUACION_COL_NAME+
                            " COLLATE UNICODE ASC "
                    ,null);

            while(cursor.moveToNext()){
                EvaluacionVO temp = new EvaluacionVO();
                    temp.setId(cursor.getInt(0));
                    temp.setName(cursor.getString(1));
                    temp.setMateriaSeca(cursor.getInt(2)>0);
                    temp.setDefecto(temp.getName().toUpperCase().contains("DEFECTO"));
                    Log.d(TAG,""+temp.getName());
                evaluacionVOList.add(temp);
               // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"error EVA "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return evaluacionVOList;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_EVALUACION,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
