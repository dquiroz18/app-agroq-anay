package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.TipoCalibreVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CALIBRE;
import static com.ibao.agroq.utilities.Utilities.TABLE_CALIBRE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_CALIBRE_COL_IDTIPOCALIBRE;
import static com.ibao.agroq.utilities.Utilities.TABLE_TIPOCALIBRE;
import static com.ibao.agroq.utilities.Utilities.TABLE_TIPOCALIBRE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_TIPOCALIBRE_COL_NAME;


public class TipoCalibreDAO {

    String TAG  = TipoCalibreDAO.class.getSimpleName();

    Context ctx;
    public TipoCalibreDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_TIPOCALIBRE,null,null);
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
            values.put(TABLE_TIPOCALIBRE_COL_ID,id);
            values.put(TABLE_TIPOCALIBRE_COL_NAME,name);
            Long temp = db.insert(TABLE_TIPOCALIBRE,TABLE_TIPOCALIBRE_COL_ID,values);

            return (temp>0);
        }catch (Exception e){
            Log.d("ZonaDAO",e.toString());
        }finally {
            db.close();
            conn.close();
        }
        return false;
    }

    public TipoCalibreVO consultarByid(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        TipoCalibreVO temp = null;
        try{
            temp = new TipoCalibreVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "Z."+TABLE_TIPOCALIBRE_COL_ID+", " +
                            "Z."+TABLE_TIPOCALIBRE_COL_NAME+
                        " FROM "+
                            TABLE_TIPOCALIBRE+" as Z"+
                        " WHERE "+
                            "Z."+TABLE_TIPOCALIBRE_COL_ID+" = "+String.valueOf(id)
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

    public List<TipoCalibreVO> listTipoCalibre(){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<TipoCalibreVO> TipoCalibreVOList = new ArrayList<>();
        try{
            String[] campos = {TABLE_TIPOCALIBRE_COL_ID,TABLE_TIPOCALIBRE_COL_NAME};
            Cursor cursor= db.query(TABLE_TIPOCALIBRE,campos,null,null,null,null,TABLE_TIPOCALIBRE_COL_NAME+" COLLATE UNICODE ASC");
            while(cursor.moveToNext()){
                TipoCalibreVO temp = new TipoCalibreVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                TipoCalibreVOList.add(temp);
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
        return TipoCalibreVOList;
    }

    public TipoCalibreVO consultarByidCalibre(int idCalibre){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        TipoCalibreVO tipoCalibreVO = null;
        try{
            tipoCalibreVO = new TipoCalibreVO();
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "TC."+TABLE_TIPOCALIBRE_COL_ID+", "+
                            "TC."+TABLE_TIPOCALIBRE_COL_NAME+
                            " FROM "+
                            TABLE_TIPOCALIBRE+" as TC, "+
                            TABLE_CALIBRE+" as C"+
                            " WHERE "+
                            "C."+TABLE_CALIBRE_COL_ID+"="+  String.valueOf(idCalibre)+
                            " AND "+
                            "C."+TABLE_CALIBRE_COL_IDTIPOCALIBRE+" = "+"TC."+TABLE_CALIBRE_COL_ID
                    , null);
            cursor.moveToFirst();
            tipoCalibreVO.setId(cursor.getInt(0));
            tipoCalibreVO.setName(cursor.getString(1));
            Log.d(TAG,"ID CALIBRE : "+tipoCalibreVO.getName());
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"ID CALIBRE : "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return tipoCalibreVO;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_TIPOCALIBRE,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
