package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.EmpresaVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA_COL_IDZONA;
import static com.ibao.agroq.utilities.Utilities.TABLE_EMPRESA_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_FUNDO;
import static com.ibao.agroq.utilities.Utilities.TABLE_FUNDO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_FUNDO_COL_IDEMPRESA;

public class EmpresaDAO {

    static String TAG =EmpresaDAO.class.getSimpleName();

    Context ctx;

    public EmpresaDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_EMPRESA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insertarEmpresa(int id, String name,int idZona){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_EMPRESA_COL_ID,id);
        values.put(TABLE_EMPRESA_COL_NAME,name);
        values.put(TABLE_EMPRESA_COL_IDZONA,idZona);
        Long temp = db.insert(TABLE_EMPRESA,TABLE_EMPRESA_COL_ID,values);
        db.close();
        conn.close();
        return (temp>0)?true:false;
    }

    public EmpresaVO consultarEmpresaByid(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            temp = new EmpresaVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                        "E."+TABLE_EMPRESA_COL_ID+", " +
                        "E."+TABLE_EMPRESA_COL_NAME+","+
                        "E."+TABLE_EMPRESA_COL_IDZONA+
                        " FROM "+TABLE_EMPRESA+" as E"+
                        " WHERE "+"E."+TABLE_EMPRESA_COL_ID+" = "+String.valueOf(id)
            ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setIdZona(cursor.getInt(2));
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarEmpresaByid "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }


    public EmpresaVO consultarEmpresaByidFundo(int idFundo){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            temp = new EmpresaVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E."+TABLE_EMPRESA_COL_ID+", " +
                            "E."+TABLE_EMPRESA_COL_NAME+","+
                            "E."+TABLE_EMPRESA_COL_IDZONA+
                        " FROM "+
                            TABLE_EMPRESA+" as E, "+
                            TABLE_FUNDO+" as F "+
                        " WHERE "+
                            "F."+TABLE_FUNDO_COL_ID+" = "+String.valueOf(idFundo)+
                            " AND "+
                            "F."+TABLE_FUNDO_COL_IDEMPRESA+" = "+"E."+TABLE_EMPRESA_COL_ID
                    ,null);
            cursor.moveToFirst();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
            temp.setIdZona(cursor.getInt(2));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarEmpresaByidFundo "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }


    public List<EmpresaVO> listEmpresasByIdZona(int idZona){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        List<EmpresaVO> empresas = new  ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E."+TABLE_EMPRESA_COL_ID+", " +
                            "E."+TABLE_EMPRESA_COL_NAME+", "+
                            "E."+TABLE_EMPRESA_COL_IDZONA+
                            " FROM "+TABLE_EMPRESA+" as E"+
                            " WHERE "+"E."+TABLE_EMPRESA_COL_IDZONA+" = "+String.valueOf(idZona)
                    ,null);
            while(cursor.moveToNext()){
                EmpresaVO temp = new EmpresaVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setIdZona(cursor.getInt(2));
                Log.d("EmpresaDAOxdxd",""+temp.getName());
                empresas.add(temp);
                // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listEmpresasByIdZona "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return empresas;
    }


    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_EMPRESA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
