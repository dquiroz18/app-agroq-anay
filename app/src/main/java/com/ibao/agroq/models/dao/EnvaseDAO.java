package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.EnvaseVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_ENVASE;
import static com.ibao.agroq.utilities.Utilities.TABLE_ENVASE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_ENVASE_COL_IDPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_ENVASE_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_ENVASE_COL_PESONETO;
import static com.ibao.agroq.utilities.Utilities.TABLE_ENVASE_COL_PESOTARA;


public class EnvaseDAO {

    String TAG = EnvaseDAO.class.getSimpleName();

    Context ctx;
    public EnvaseDAO(Context ctx) {


        this.ctx=ctx;
    }



    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_ENVASE,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
    public boolean insertar(int id, String name,String PN, String PT, int idProceso){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_ENVASE_COL_ID,id);
        values.put(TABLE_ENVASE_COL_NAME,name);
        values.put(TABLE_ENVASE_COL_PESONETO,PN);
        values.put(TABLE_ENVASE_COL_PESOTARA,PT);
        values.put(TABLE_ENVASE_COL_IDPROCESO,idProceso);
        Long temp = db.insert(TABLE_ENVASE,TABLE_ENVASE_COL_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }
    public EnvaseVO consultarById(int id) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        EnvaseVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "EN."+TABLE_ENVASE_COL_ID+", " +
                            "EN."+TABLE_ENVASE_COL_NAME+", " +
                            "EN."+TABLE_ENVASE_COL_PESONETO+", " +
                            "EN."+TABLE_ENVASE_COL_PESOTARA+", " +
                            "EN."+TABLE_ENVASE_COL_IDPROCESO+
                        " FROM "+
                            TABLE_ENVASE+" as EN"+
                        " WHERE "+
                            "EN."+TABLE_ENVASE_COL_ID+"="+String.valueOf(id)
                    ,null);
            Log.d(TAG,"cont Envase : "+cursor.getCount());
            if(cursor.getCount()>0){
                temp = new EnvaseVO();
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setPesoNeto(cursor.getString(2));
                temp.setPesoTara(cursor.getString(3));
                temp.setIdTipoProceso(cursor.getInt(4));
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"Envase cons id : "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return temp;
    }

    public List<EnvaseVO> listarByIdTipoProceso(int idTipoProceso){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<EnvaseVO> envaseVOList = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "EN."+TABLE_ENVASE_COL_ID+", " +
                            "EN."+TABLE_ENVASE_COL_NAME+", " +
                            "EN."+TABLE_ENVASE_COL_PESONETO+", "+
                            "EN."+TABLE_ENVASE_COL_PESOTARA+", "+
                            "EN."+TABLE_ENVASE_COL_IDPROCESO+" "+
                            " FROM "+
                            TABLE_ENVASE+" AS EN"+
                        " WHERE "+
                            "EN."+TABLE_ENVASE_COL_IDPROCESO+"="+  String.valueOf(idTipoProceso)+
                        " ORDER BY "+
                            "EN."+TABLE_ENVASE_COL_NAME+
                            " COLLATE UNICODE ASC"
                    ,null);
            while (cursor.moveToNext()){
                EnvaseVO temp = new EnvaseVO();
                    temp.setId(cursor.getInt(0));
                    temp.setName(cursor.getString(1));
                    temp.setPesoNeto(cursor.getString(2));
                    temp.setPesoTara(cursor.getString(3));
                    temp.setIdTipoProceso(cursor.getInt(4));
                envaseVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return envaseVOList;
    }


}
