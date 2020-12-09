package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.DestinoVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESTINO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESTINO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESTINO_COL_NAME;


public class DestinoDAO {

    Context ctx;
    public DestinoDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_DESTINO,null,null);
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
            values.put(TABLE_DESTINO_COL_ID,id);
            values.put(TABLE_DESTINO_COL_NAME,name);
            Long temp = db.insert(TABLE_DESTINO,TABLE_DESTINO_COL_ID,values);
            db.close();
            conn.close();
            return (temp>0);
        }catch (Exception e){
            Log.d("ZonaDAO",e.toString());
        }

        return false;
    }

    public DestinoVO consultarByid(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        DestinoVO temp = null;
        try{
            temp = new DestinoVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "Z."+TABLE_DESTINO_COL_ID+", " +
                            "Z."+TABLE_DESTINO_COL_NAME+
                        " FROM "+
                            TABLE_DESTINO+" as Z"+
                        " WHERE "+
                            "Z."+TABLE_DESTINO_COL_ID+" = "+String.valueOf(id)
                    ,null);
            cursor.moveToFirst();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(), Toast.LENGTH_SHORT);
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    //guardando f:1 v:2 env:1

    public List<DestinoVO> listDestinos(){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        List<DestinoVO> destinoVOList = new ArrayList<>();
        try{
            String[] campos = {TABLE_DESTINO_COL_ID,TABLE_DESTINO_COL_NAME};
            Cursor cursor= db.query(TABLE_DESTINO,campos,null,null,null,null,TABLE_DESTINO_COL_NAME+" COLLATE UNICODE ASC");
            while(cursor.moveToNext()){
                DestinoVO temp = new DestinoVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                destinoVOList.add(temp);
                //Log.d("zonaDAOtag",temp.getName());
               // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }catch (Exception e){
            Log.d("zonaDAOtag",e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return destinoVOList;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_DESTINO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
