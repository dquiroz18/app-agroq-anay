package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.PlantaVO;


import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_PLANTA;
import static com.ibao.agroq.utilities.Utilities.TABLE_PLANTA_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_PLANTA_COL_NAME;


public class PlantaDAO {

    String TAG ="PlantaDAO";

    Context ctx;
    public PlantaDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_PLANTA,null,null);
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
        values.put(TABLE_PLANTA,id);
        values.put(TABLE_PLANTA_COL_NAME,name);
        Long temp = db.insert(TABLE_PLANTA,TABLE_PLANTA_COL_ID,values);
        db.close();
        conn.close();
        return (temp>0)?true:false;
    }

    public  PlantaVO consultarByid(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        PlantaVO temp = null;

        try{
            temp = new  PlantaVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                        "P."+TABLE_PLANTA_COL_ID+", " +
                        "P."+TABLE_PLANTA_COL_NAME+" "+
                        " FROM "+TABLE_PLANTA+" as P"+
                        " WHERE "+"P."+TABLE_PLANTA_COL_ID+" = "+String.valueOf(id)
            ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarByid "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }


    public List<PlantaVO> listPlantas(){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        List<PlantaVO> plantaVOList = new ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_PLANTA_COL_ID+", " +
                            "P."+TABLE_PLANTA_COL_NAME+
                            " FROM "+TABLE_PLANTA+" as P"
                    ,null);
            while(cursor.moveToNext()){
                PlantaVO temp = new PlantaVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                Log.d(TAG,""+temp.getName());
                plantaVOList.add(temp);
                // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listPlantas "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return plantaVOList;
    }


    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_PLANTA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
