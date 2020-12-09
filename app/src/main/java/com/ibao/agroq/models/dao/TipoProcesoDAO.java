package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.TipoProcesoVO;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_TIPOPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_TIPOPROCESO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_TIPOPROCESO_COL_NAME;


public class TipoProcesoDAO {

    String TAG =TipoProcesoDAO.class.getSimpleName();

    Context ctx;

    public TipoProcesoDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_TIPOPROCESO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insertar(int id, String name){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_TIPOPROCESO_COL_ID,id);
        values.put(TABLE_TIPOPROCESO_COL_NAME,name);
        Long temp = db.insert(TABLE_TIPOPROCESO,TABLE_TIPOPROCESO_COL_ID,values);
        db.close();
        conn.close();
        return (temp>0)?true:false;
    }

    public TipoProcesoVO consultarByid(int id){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        TipoProcesoVO temp = null;
        try{
            temp = new  TipoProcesoVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                        "TP."+TABLE_TIPOPROCESO_COL_ID+", " +
                        "TP."+TABLE_TIPOPROCESO_COL_NAME+" "+
                        " FROM "+TABLE_TIPOPROCESO+" as TP"+
                        " WHERE "+"TP."+TABLE_TIPOPROCESO_COL_ID+" = "+String.valueOf(id)
            ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarByid "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"error -> "+e.toString());
        }finally {
            db.close();
            c.close();
        }

        return temp;
    }



    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_TIPOPROCESO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
