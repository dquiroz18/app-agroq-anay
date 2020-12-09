package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.ZonaVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA_COL_IDZONA;
import static com.ibao.agroq.utilities.Utilities.TABLE_ZONA;
import static com.ibao.agroq.utilities.Utilities.TABLE_ZONA_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_ZONA_COL_NAME;


public class ZonaDAO {

    Context ctx;

    public ZonaDAO(Context ctx) {
        this.ctx=ctx;
    }

    static public String TAG = ZonaDAO.class.getSimpleName();

    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_ZONA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insertar(int id, String name){

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(TABLE_ZONA_COL_ID,id);
            values.put(TABLE_ZONA_COL_NAME,name);
            Long temp = db.insert(TABLE_ZONA,TABLE_ZONA_COL_ID,values);

            return (temp>0);
        }catch (Exception e){
            Log.d(TAG,e.toString());
            Toast.makeText(ctx,TAG+" insertar "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            conn.close();
        }

        return false;
    }


    public ZonaVO consultarByidEmpresa(int idEmpresa){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ZonaVO temp = null;
        try{
            temp = new ZonaVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "Z."+TABLE_ZONA_COL_ID+", " +
                            "Z."+TABLE_ZONA_COL_NAME+
                        " FROM "+
                            TABLE_ZONA+" as Z, "+
                            TABLE_EMPRESA+" as E "+
                        " WHERE "+
                            "E."+TABLE_EMPRESA_COL_ID+" = "+String.valueOf(idEmpresa)+
                            " AND "+
                            "E."+TABLE_EMPRESA_COL_IDZONA+" = "+"Z."+TABLE_ZONA_COL_ID
                    ,null);
            cursor.moveToFirst();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarByidEmpresa "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public ZonaVO consultarByid(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ZonaVO temp = null;
        try{
            temp = new ZonaVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "Z."+TABLE_ZONA_COL_ID+", " +
                            "Z."+TABLE_ZONA_COL_NAME+
                        " FROM "+
                            TABLE_ZONA+" as Z"+
                        " WHERE "+
                            "Z."+TABLE_ZONA_COL_ID+" = "+String.valueOf(id)
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

    //guardando f:1 v:2 env:1

    public List<ZonaVO> listZonas(){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<ZonaVO> zonas = new ArrayList<>();
        try{
            String[] campos = {TABLE_ZONA_COL_ID,TABLE_ZONA_COL_NAME};
            Cursor cursor= db.query(TABLE_ZONA,campos,null,null,null,null,TABLE_ZONA_COL_NAME+" COLLATE UNICODE ASC");
            while(cursor.moveToNext()){
                ZonaVO temp = new ZonaVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                zonas.add(temp);

            }
            cursor.close();
        }catch (Exception e){
            Log.d("zonaDAOtag",e.toString());
            Toast.makeText(ctx,TAG+" listZonas "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return zonas;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_ZONA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
