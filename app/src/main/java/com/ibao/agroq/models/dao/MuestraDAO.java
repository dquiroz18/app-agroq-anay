package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesInternal.EvaluacionDetalleVO;
import com.ibao.agroq.models.vo.entitiesInternal.MuestraVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_CANTIDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_COMENTARIO;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_FECHAHORA;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_IDPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_IDTIPOPROCESO;

public class MuestraDAO {

    String TAG = MuestraDAO.class.getSimpleName();

    private Context ctx;

    public MuestraDAO(Context ctx) {
        this.ctx = ctx;
    }

    /*
    public boolean editarIdTipoInspeccion(int id,int idTipoInspeccion){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_MUESTRA_COL_IDTIPOINSPECCION,String.valueOf(idTipoInspeccion));
        int res = db.update(TABLE_MUESTRA,values,TABLE_MUESTRA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return flag;
    }
    */

    public int clearTableUpload(int idProceso,int idTipoProceso){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();

        List<MuestraVO> muestraVOList = new MuestraDAO(ctx).listarByIdProcesoIdTipoProceso(idProceso,idTipoProceso);

        for(int i=0;i<muestraVOList.size();i++){

            //List<EvaluacionDetalleVO> evaluacionDetalleDAOList = new EvaluacionDetalleDAO(ctx).listByIdMuestra(muestraVOList.get(i).getId());
                new EvaluacionDetalleDAO(ctx).clearTableUpload(muestraVOList.get(i).getId());


        }

        String[] parametros = {
                String.valueOf(idProceso),
                String.valueOf(idTipoProceso)
        };

        int res = db.delete(
                TABLE_MUESTRA,
                TABLE_MUESTRA_COL_IDPROCESO+"=?" +
                        " AND " +
                        TABLE_MUESTRA_COL_IDTIPOPROCESO+"=?",
                parametros);

        Log.d("borrando",String.valueOf(res));
        db.close();
        conn.close();
        return res;
    }


    public MuestraVO consultarById(int  id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        MuestraVO temp = null;
        try{
            String consulta = "SELECT " +
                    "E."+TABLE_MUESTRA_COL_ID+         ", "+
                    "E."+TABLE_MUESTRA_COL_FECHAHORA+  ", "+
                    "E."+TABLE_MUESTRA_COL_CANTIDAD+   ", "+
                    "E."+TABLE_MUESTRA_COL_COMENTARIO+ ", "+
                    "E."+TABLE_MUESTRA_COL_IDPROCESO+  ", "+
                    "E."+TABLE_MUESTRA_COL_IDTIPOPROCESO+
                    " FROM "+
                    TABLE_MUESTRA+" as E "+
                    " WHERE "+
                    "E."+TABLE_MUESTRA_COL_ID+"="+String.valueOf(id);

            Cursor cursor = db.rawQuery(consulta,null);
            Log.d(TAG,consulta);
            Log.d(TAG,"contidad de consulta "+cursor.getCount());
            Log.d(TAG,"id q se busco "+id);
            if(cursor.getCount()>=0) {
                cursor.moveToFirst();
                temp = new MuestraVO();
                temp.setId(cursor.getInt(0));
                temp.setFechaHora(cursor.getString(1));
                temp.setCantidad(cursor.getInt(2));
                temp.setComentario(cursor.getString(3));
                temp.setIdProceso(cursor.getInt(4));
                temp.setIdTipoProceso(cursor.getInt(5));

                if (temp.getIdTipoProceso()>0){
                    temp.setNameTipoProceso(new TipoProcesoDAO(ctx).consultarByid(temp.getIdTipoProceso()).getName());
                    Log.d(TAG,"name tipo proceso"+new TipoProcesoDAO(ctx).consultarByid(temp.getIdTipoProceso()).getName());
                }

            }
            cursor.close();

        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public MuestraVO nuevoByIdProcesoIdtipoProceso(Integer idProceso, Integer idTipoProceso){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(TABLE_MUESTRA_COL_IDPROCESO,idProceso);
            values.put(TABLE_MUESTRA_COL_IDTIPOPROCESO,String.valueOf(idTipoProceso));
        int temp = (int) db.insert(TABLE_MUESTRA,TABLE_MUESTRA_COL_ID,values);
        Log.d("consultasuccess","idEva->"+temp);
        MuestraVO m  = consultarById(temp);
        Log.d(TAG,"NUEVA MUESTRA id-->"+m.getId());
        Log.d(TAG,"NUEVA MUESTRA idP-->"+m.getIdProceso());
        Log.d(TAG,"NUEVA MUESTRA idTP-->"+m.getIdTipoProceso());
        db.close();
        conn.close();
        return m;
    }

    public int borrarById(int id){

        new EvaluacionDetalleDAO(ctx).deleteByIdMuestra(id);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {String.valueOf(id)};
        int res =db.delete(TABLE_MUESTRA,TABLE_MUESTRA_COL_ID+"=?",parametros);
        Log.d(TAG,"ELIMINADO MUESTRA "+String.valueOf(res));

        db.close();
        conn.close();
        return res;
    }


    public int deleteByIdProcesoIdTipoProceso(int idProceso, int idTipoProceso){

        List<MuestraVO> muestraVOSList = new MuestraDAO(ctx).listarByIdProcesoIdTipoProceso(idProceso,idTipoProceso);
        for(MuestraVO m : muestraVOSList){
            new EvaluacionDetalleDAO(ctx).deleteByIdMuestra(m.getId());
        }

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros = {
                String.valueOf(idProceso),
                String.valueOf(idTipoProceso)
        };

        int res = db.delete(
                TABLE_MUESTRA,
                TABLE_MUESTRA_COL_IDPROCESO+"=?" +
                        " AND " +
                        TABLE_MUESTRA_COL_IDTIPOPROCESO+"=?",
                parametros);

        Log.d("borrando",String.valueOf(res));

        db.close();
        conn.close();
        return res;
    }



    public List<MuestraVO> listarByIdProcesoIdTipoProceso(int idProceso,int idTipoProceso){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<MuestraVO> evaluacionVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "M."+TABLE_MUESTRA_COL_ID+         ", "+
                            "M."+TABLE_MUESTRA_COL_FECHAHORA+  ", "+
                            "M."+TABLE_MUESTRA_COL_CANTIDAD+   ", "+
                            "M."+TABLE_MUESTRA_COL_COMENTARIO+ ", "+
                            "M."+TABLE_MUESTRA_COL_IDPROCESO+  ", "+
                            "M."+TABLE_MUESTRA_COL_IDTIPOPROCESO+
                        " FROM "+
                            TABLE_MUESTRA+" as M " +
                        " WHERE "+
                            "M."+TABLE_MUESTRA_COL_IDPROCESO+" = "+String.valueOf(idProceso)+
                            " AND "+
                            "M."+TABLE_MUESTRA_COL_IDTIPOPROCESO+" = "+String.valueOf(idTipoProceso)
                    ,null);
            while (cursor.moveToNext() && cursor.getCount()>0){
                MuestraVO temp = new MuestraVO();
                temp = new MuestraVO();
                temp.setId(cursor.getInt(0));
                temp.setFechaHora(cursor.getString(1));
                temp.setCantidad(cursor.getInt(2));
                temp.setComentario(cursor.getString(3));
                temp.setIdProceso(cursor.getInt(4));
                temp.setIdTipoProceso(cursor.getInt(5));



                if (temp.getIdTipoProceso()>0){
                    temp.setNameTipoProceso(new TipoProcesoDAO(ctx).consultarByid(temp.getIdTipoProceso()).getName());
                }


                List<EvaluacionDetalleVO> evaluacionDetalleVOList_Defectos = new ArrayList<>();
                evaluacionDetalleVOList_Defectos = new EvaluacionDetalleDAO(ctx).listByIdMuestra(temp.getId());

                List <EvaluacionDetalleVO> listDefetos = new ArrayList<>();
                List <EvaluacionDetalleVO> listNoDefetos = new ArrayList<>();


                for(EvaluacionDetalleVO eva : evaluacionDetalleVOList_Defectos){
                    if(eva.isDefecto()){
                        listDefetos.add(eva);
                    }else {
                        if(!eva.isMatSec()){
                            listNoDefetos.add(eva);
                        }
                    }
                }
                evaluacionDetalleVOList_Defectos.clear();
                // evaluacionDetalleVOList_Defectos.addAll(listNoDefetos);
                evaluacionDetalleVOList_Defectos.addAll(listDefetos);

                float tolMin=0f;
                float tolMax=0f;

                float totalDefectos=0f;
                float totalSanos=100.0f;
                for(EvaluacionDetalleVO eva : evaluacionDetalleVOList_Defectos){
                    if(eva.isDefecto()){
                        totalDefectos=totalDefectos+eva.getPorcentaje();
                        tolMin=tolMin+eva.getTolMin();
                        tolMax=tolMax+eva.getTolMax();
                    }
                }

                totalDefectos=((int)(totalDefectos*10))/10.0f;


                if(tolMin>0 && tolMax>0 && tolMin!=tolMax){
                    if(totalDefectos<tolMin){
                        //buena
                        temp.setCalificacion(1);
                    }else {
                        if(totalDefectos<=tolMax){
                            //regular
                            temp.setCalificacion(2);
                        }else{
                            //por mejorar
                            temp.setCalificacion(3);
                        }
                    }
                }else {
                    temp.setCalificacion(0);
                }



                evaluacionVOS.add(temp);

            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return evaluacionVOS;
    }




    public List<MuestraVO> listarAll(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<MuestraVO> muestraVOList = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "M."+TABLE_MUESTRA_COL_ID+         ", "+
                            "M."+TABLE_MUESTRA_COL_FECHAHORA+  ", "+
                            "M."+TABLE_MUESTRA_COL_CANTIDAD+   ", "+
                            "M."+TABLE_MUESTRA_COL_COMENTARIO+ ", "+
                            "M."+TABLE_MUESTRA_COL_IDPROCESO+  ", "+
                            "M."+TABLE_MUESTRA_COL_IDTIPOPROCESO+
                            " FROM "+
                            TABLE_MUESTRA+" as M " // +
                          //  " WHERE "+
                         //   "E."+TABLE_MUESTRA_COL_IDVISITA+" = "+String.valueOf(idVisita)
                    ,null);
            while (cursor.moveToNext() && cursor.getCount()>0){
                MuestraVO temp;
                temp = new MuestraVO();
                temp.setId(cursor.getInt(0));
                temp.setFechaHora(cursor.getString(1));
                temp.setCantidad(cursor.getInt(2));
                String com = cursor.getString(3);
                temp.setComentario(com==null || com.isEmpty() || com.equals("")?" ":com);
                temp.setIdProceso(cursor.getInt(4));
                temp.setIdTipoProceso(cursor.getInt(5));
                if (temp.getIdTipoProceso()>0){
                    temp.setNameTipoProceso(new TipoProcesoDAO(ctx).consultarByid(temp.getIdTipoProceso()).getName());
                }
                    muestraVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("TAG","listarAll() "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return muestraVOList;
    }

    public boolean editNFrutos(int id, int cantidad) {
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_MUESTRA_COL_CANTIDAD,cantidad);
        int res = db.update(TABLE_MUESTRA,values,TABLE_MUESTRA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        c.close();
        return  flag;

    }

    public boolean editComentarioById(int id, String comentario) {
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_MUESTRA_COL_COMENTARIO,comentario);
        int res = db.update(TABLE_MUESTRA,values,TABLE_MUESTRA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        c.close();
        return  flag;
    }


}
