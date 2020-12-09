package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesInternal.FotoVO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_FOTO;
import static com.ibao.agroq.utilities.Utilities.TABLE_FOTO_COL_HORAFECHA;
import static com.ibao.agroq.utilities.Utilities.TABLE_FOTO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_FOTO_COL_IDCRITERIODETALLE;
import static com.ibao.agroq.utilities.Utilities.TABLE_FOTO_COL_PATH;

public class FotoDAO {


    String TAG = FotoDAO.class.getSimpleName();

    Context ctx;

    public FotoDAO(Context ctx) {
        this.ctx=ctx;
    }

    public FotoVO nuevoByIdCriterioDetalle(int idCriterioDetalle, String path) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        Log.d("locomata","datos recibidos e funcion : "+idCriterioDetalle);

        SQLiteDatabase db = conn.getWritableDatabase();
        FotoVO res = null;
        ContentValues values = new ContentValues();
        values.put(TABLE_FOTO_COL_PATH,path);
        values.put(TABLE_FOTO_COL_IDCRITERIODETALLE,String.valueOf(idCriterioDetalle));
        Long id = db.insert(TABLE_FOTO,TABLE_FOTO_COL_ID,values);

        Log.d("locomata","id FOTO insertado : "+id);

        //obteniendo datos extra de tablas alternas
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "P."+TABLE_FOTO_COL_ID+", " +
                        "P."+TABLE_FOTO_COL_HORAFECHA+", " +
                        "P."+TABLE_FOTO_COL_PATH+", "+
                        "P."+TABLE_FOTO_COL_IDCRITERIODETALLE+
                        " FROM "+
                        TABLE_FOTO+" as P"+
                        " WHERE "+
                        "P."+TABLE_FOTO_COL_ID+"="+String.valueOf(id)
                , null);
        Log.d("locomata","cantidad isnercio"+cursor.getCount());
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            res = new FotoVO();
            res.setId(cursor.getInt(0));
            res.setFechaHora(cursor.getString(1));
            res.setPath(cursor.getString(2));
            res.setIdCriterioDetalle(cursor.getInt(3));

        }
        cursor.close();
        db.close();
        conn.close();
        return res;
    }

    public FotoVO consultarById(int id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        FotoVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_FOTO_COL_ID+", " +
                            "P."+TABLE_FOTO_COL_HORAFECHA+", " +
                            "P."+TABLE_FOTO_COL_PATH+", "+
                            "P."+TABLE_FOTO_COL_IDCRITERIODETALLE+
                            " FROM "+TABLE_FOTO+" as P"+
                            " WHERE "+
                            "P."+TABLE_FOTO_COL_ID+"="+String.valueOf(id)
                    ,null);
            if(cursor.getCount()>0){
                Log.d("locomata","photo encontrado");
                temp = new FotoVO();
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setFechaHora(cursor.getString(1));
                temp.setPath(cursor.getString(2));
                temp.setIdCriterioDetalle(cursor.getInt(3));

            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarById "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }
    public FotoVO consultarById_Upload(int id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        FotoVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_FOTO_COL_ID+", " +
                            "P."+TABLE_FOTO_COL_HORAFECHA+", " +
                            "P."+TABLE_FOTO_COL_PATH+", "+
                            "P."+TABLE_FOTO_COL_IDCRITERIODETALLE+
                            " FROM "+TABLE_FOTO+" as P"+
                            " WHERE "+
                            "P."+TABLE_FOTO_COL_ID+"="+String.valueOf(id)
                    ,null);
            if(cursor.getCount()>0){
                Log.d("locomata","photo encontrado");
                temp = new FotoVO();
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setFechaHora(cursor.getString(1));
                temp.setPath(cursor.getString(2));
                temp.setIdCriterioDetalle(cursor.getInt(3));
                try{

                    Uri uri = Uri.fromFile(new File(temp.getPath()));

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);

                    int rotate = 0;
                    try {
                        File imageFile = new File(temp.getPath());
                        ExifInterface exif = new ExifInterface(
                                imageFile.getAbsolutePath());
                        int orientation = exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_NORMAL);

                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotate = 270;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotate = 180;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotate = 90;
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //redimensionando bitmap

                    final int tam=500;

                    int ancho = bitmap.getWidth();
                    int alto = bitmap.getHeight();
                    float scalaAlto;
                    float scalaAncho;
                    if(alto>=ancho){
                        float nuevoAlto = tam;
                        float nuevoAncho = (1.0f*ancho*nuevoAlto)/alto;
                        scalaAlto = nuevoAlto/alto;
                        scalaAncho = nuevoAncho/ancho;
                        //si la imagen esta parada
                    }else{
                        //si la imagen esta echada
                        float nuevoAncho = tam;
                        float nuevoAlto = (1.0f*alto*nuevoAncho)/ancho;
                        scalaAlto = nuevoAlto/alto;
                        scalaAncho = nuevoAncho/ancho;
                    }


                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotate);
                    matrix.postScale(scalaAncho,scalaAlto);


                    bitmap = Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,true);

                    //terminado redimensionado de bitmap
                    ByteArrayOutputStream array = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
                    byte[] imageByte = array.toByteArray();
                    String imageString = Base64.encodeToString(imageByte,Base64.DEFAULT);
                  //  String imageString = StrinimageByte;
                    temp.setStringBitmap(imageString);

                }catch (Exception e){
                    Log.d("fotosJson","Error"+e.toString());
                }

            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" consultarById_Upload "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public boolean borrarById_UPLOAD(int id){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        int res = db.delete(TABLE_FOTO,TABLE_FOTO_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
            //new EvaluacionDAO(ctx).borrarByIdVisita(id);
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean deleteByIdCriterioDetalle(int idCriterioDetalle){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(idCriterioDetalle),
                };

        int res = db.delete(TABLE_FOTO,TABLE_FOTO_COL_IDCRITERIODETALLE+"=?",parametros);
        if(res>0){
            flag=true;
            //new EvaluacionDAO(ctx).borrarByIdVisita(id);
        }
        db.close();
        conn.close();
        return flag;
    }


    public List<FotoVO> listarByIdCriterioDetalle(int idMuestra){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<FotoVO> fotoVOS = new ArrayList<FotoVO>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "P."+TABLE_FOTO_COL_ID+", " +
                            "P."+TABLE_FOTO_COL_HORAFECHA+", " +
                            "P."+TABLE_FOTO_COL_PATH+", "+
                            "P."+TABLE_FOTO_COL_IDCRITERIODETALLE+
                            " FROM "+
                            TABLE_FOTO+" AS P"+
                            " WHERE "+
                            "P."+TABLE_FOTO_COL_IDCRITERIODETALLE+"="+String.valueOf(idMuestra)
                    ,null);
            while (cursor.moveToNext()){
                FotoVO temp = new FotoVO();
                temp.setId(cursor.getInt(0));
                temp.setFechaHora(cursor.getString(1));
                temp.setPath(cursor.getString(2));
                temp.setIdCriterioDetalle(cursor.getInt(3));
                fotoVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listarByIdCriterioDetalle "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return fotoVOS;
    }
    public int contarFotos(int idMuestra){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int i=0;
        try {
            Cursor mCount= db.rawQuery(
                    "select " +
                            "count(*)" +
                            " from " +
                            TABLE_FOTO +
                            " where " +
                            TABLE_FOTO_COL_IDCRITERIODETALLE+"="+String.valueOf(idMuestra)
                    , null);
            if(mCount.getCount()>0){
                mCount.moveToFirst();
                i= mCount.getInt(0);
            }
            mCount.close();
        }catch (Exception e){
            Log.d(TAG,"erorr conteo fotos :"+e.toString());
            Toast.makeText(ctx,TAG+" contarFotos "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            conn.close();
        }

        return i;
    }



    public boolean borrarById(int id){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        int res = db.delete(TABLE_FOTO,TABLE_FOTO_COL_ID+"=?",parametros);

        if(res>0){
            flag=true;
        }

        db.close();
        conn.close();
        return flag;
    }

    public boolean borrarByIdMuestra(int idMuestra){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        List<FotoVO> list = new FotoDAO(ctx).listarByIdCriterioDetalle(idMuestra);

        int tam = 0;
        for(FotoVO f: list){
            try{
                File file = new File(f.getPath());
                boolean delete = file.delete();
                if(!delete){
                    tam++;
                    Log.d("fotoxdxd"," Error eliminando");
                }else{
                    Log.d("fotoxdxd","eliminado");
                }
            }catch (Exception e){
                tam++;
            }
        }
        if(tam>0){
            Toast.makeText(ctx,"No se pudo Eliminar "+tam+" Foto"+(tam>1?"s":" !"),Toast.LENGTH_LONG).show();
        }

        String[] parametros =
                {
                        String.valueOf(idMuestra),
                };
        int res = db.delete(TABLE_FOTO,TABLE_FOTO_COL_IDCRITERIODETALLE+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public List<FotoVO> listarAll(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<FotoVO> fotoVOS = new ArrayList<FotoVO>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "P."+TABLE_FOTO_COL_ID+", " +
                            "P."+TABLE_FOTO_COL_HORAFECHA+", " +
                            "P."+TABLE_FOTO_COL_PATH+", "+
                            "P."+TABLE_FOTO_COL_IDCRITERIODETALLE+
                            " FROM "+
                            TABLE_FOTO+" AS P"//+
                    //        " WHERE "+
                    //        "P."+TABLE_FOTO_COL_IDMUESTRA+"="+String.valueOf(idMuestra)
                    ,null);
            while (cursor.moveToNext()){
                FotoVO temp = new FotoVO();
                temp.setId(cursor.getInt(0));
                temp.setFechaHora(cursor.getString(1));
                temp.setPath(cursor.getString(2));
                temp.setIdCriterioDetalle(cursor.getInt(3));

                fotoVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listarAll "+e.toString(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return fotoVOS;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        int res = db.delete(TABLE_FOTO,null,null);
        if(res>0){
            flag=true;
            //new EvaluacionDAO(ctx).borrarByIdVisita(id);
        }
        db.close();
        conn.close();
        return flag;
    }
}
