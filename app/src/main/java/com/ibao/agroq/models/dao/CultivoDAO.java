package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.CultivoVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CULTIVO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CULTIVO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_CULTIVO_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_VARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_VARIEDAD_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_VARIEDAD_COL_IDCULTIVO;

public class CultivoDAO {

    Context ctx;

   static String TAG = CultivoDAO.class.getSimpleName();
    public CultivoDAO(Context ctx) {

        this.ctx=ctx;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CULTIVO,null,null);
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
        values.put(TABLE_CULTIVO_COL_ID,id);
        values.put(TABLE_CULTIVO_COL_NAME,name);
        Long temp = db.insert(TABLE_CULTIVO,TABLE_CULTIVO_COL_ID,values);
        db.close();
        return temp > 0;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CULTIVO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public CultivoVO consultarByid(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        CultivoVO temp = null;
        try{
            temp = new CultivoVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "C."+TABLE_CULTIVO_COL_ID+", " +
                            "C."+TABLE_CULTIVO_COL_NAME+
                        " FROM "+
                            TABLE_CULTIVO+" as C"+
                        " WHERE "+
                            "C."+TABLE_CULTIVO_COL_ID+" = "+String.valueOf(id)+
                        " ORDER BY "+
                            "C."+TABLE_CULTIVO_COL_NAME+
                            " COLLATE UNICODE ASC "
                    ,null);
            cursor.moveToFirst();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarByid "+e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return temp;
    }

    public CultivoVO consultarByIdVariedad(int idVariedad){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        CultivoVO cultivoVO = null;
        try{
            cultivoVO = new CultivoVO();
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "C."+TABLE_CULTIVO_COL_ID+", "+
                            "C."+TABLE_CULTIVO_COL_NAME+
                        " FROM "+
                            TABLE_CULTIVO+" as C, "+
                            TABLE_VARIEDAD+" as V"+
                        " WHERE "+
                            "V."+TABLE_VARIEDAD_COL_ID+"= ?" +
                            " AND "+
                            "V."+TABLE_VARIEDAD_COL_IDCULTIVO+" = "+"C."+TABLE_CULTIVO_COL_ID
            , new String[]{
                    String.valueOf(idVariedad)
                    });
            cursor.moveToFirst();
            cultivoVO.setId(cursor.getInt(0));
            cultivoVO.setName(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarByIdVariedad "+e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return cultivoVO;
    }

    public List<CultivoVO> listCultivos(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<CultivoVO> cultivos = new ArrayList<>();
        try{
            String[] campos = {TABLE_CULTIVO_COL_ID,TABLE_CULTIVO_COL_NAME};
            Cursor cursor= db.query(TABLE_CULTIVO,campos,null,null,null,null,null);
            while(cursor.moveToNext()){
                CultivoVO temp = new CultivoVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                cultivos.add(temp);
               // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listCultivos "+e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return cultivos;
    }

}
