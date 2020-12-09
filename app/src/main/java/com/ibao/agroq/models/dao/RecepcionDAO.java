package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.CultivoVO;
import com.ibao.agroq.models.vo.entitiesDB.EmpresaVO;
import com.ibao.agroq.models.vo.entitiesDB.EnvaseVO;
import com.ibao.agroq.models.vo.entitiesDB.FundoVO;
import com.ibao.agroq.models.vo.entitiesDB.ZonaVO;
import com.ibao.agroq.models.vo.entitiesInternal.RecepcionVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_EDITING;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_FECHAHORA;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_IDFUNDO;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_IDPLANTA;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_IDENVASE;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_KILOS;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_NGUIA;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_NORDENPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_UNIDADES;

public class RecepcionDAO {

    String TAG = RecepcionDAO.class.getSimpleName();
    Context ctx;

    public RecepcionDAO(Context ctx){
        this.ctx = ctx;
    }

    public List<RecepcionVO> listarNoEditable(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<RecepcionVO> recepcionVOList = new ArrayList<>();
        try{
            
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "R."+TABLE_RECEPCION_COL_ID        +", " +//0
                            "R."+TABLE_RECEPCION_COL_IDPLANTA +", " +//1
                            "R."+TABLE_RECEPCION_COL_IDFUNDO   +", " +//2
                            "R."+TABLE_RECEPCION_COL_IDVARIEDAD+", " +//3
                            "R."+TABLE_RECEPCION_COL_IDENVASE +", "+//4
                            "R."+TABLE_RECEPCION_COL_FECHAHORA+", "+//5
                            "R."+TABLE_RECEPCION_COL_NORDENPROCESO+", "+//6
                            "R."+TABLE_RECEPCION_COL_NGUIA+", "+//7
                            "R."+TABLE_RECEPCION_COL_UNIDADES+", "+//8
                            "R."+TABLE_RECEPCION_COL_KILOS+", "+//9
                            "R."+TABLE_RECEPCION_COL_EDITING+//10
                        " FROM "+
                            TABLE_RECEPCION+" as R "+
                        " WHERE "+
                            "R."+TABLE_RECEPCION_COL_EDITING+"="+"0"
                    ,null);
           // Toast.makeText(ctx,"contando visitas"+cursor.getCount(),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext() && cursor.getCount()>0){
                RecepcionVO temp;
                //Log.d(TAG,"hay primero");
                //Log.d(TAG,"0");
                temp = new RecepcionVO();
                temp.setId(cursor.getInt(0));
                //Log.d(TAG,"1");
                temp.setIdPlanta(cursor.getInt(1));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                //Log.d(TAG,"2");
                temp.setIdFundo(cursor.getInt(2));
                //Log.d(TAG,"3");
                temp.setIdVariedad(cursor.getInt(3));
                //Log.d(TAG,"4");
                temp.setIdEnvase(cursor.getInt(4));
                //Log.d(TAG,"5");
                temp.setFechaHora(cursor.getString(5));
                //Log.d(TAG,"6");
                temp.setnOrdenProceso(cursor.getString(6));
                if(temp.getnOrdenProceso()==null || temp.getnOrdenProceso().isEmpty()){
                    temp.setnOrdenProceso("S/N");
                }
                //7
                temp.setnOrdenGuia(cursor.getString(7));
                if(temp.getnOrdenGuia()==null || temp.getnOrdenGuia().isEmpty()){
                    temp.setnOrdenGuia("S/N");
                }
                //Log.d(TAG,"8");
                temp.setUnidadesRecepcion(cursor.getString(8));
                if(temp.getUnidadesRecepcion()==null || temp.getUnidadesRecepcion().isEmpty()){
                    temp.setUnidadesRecepcion("0");
                }
                //Log.d(TAG,"9");
                temp.setKilosRecepcion(cursor.getString(9));
                if(temp.getKilosRecepcion()==null || temp.getKilosRecepcion().isEmpty()){
                    temp.setKilosRecepcion("0");
                }
                //Log.d(TAG,"10");
                temp.setEditing(cursor.getInt(10)>0);

                if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                    //obteniedo datos d e fundo
                    FundoDAO fundoDAO = new FundoDAO(ctx);
                    FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                    String nameFundo = f.getName();
                    temp.setNameFundo(nameFundo);
                    //obteniedno datos d e empresa
                    EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByidFundo(temp.getIdFundo());
                    temp.setIdEmpresa(empresaVO.getId());
                    temp.setNameEmpresa(empresaVO.getName());

                    ZonaVO zonaVO = new ZonaDAO(ctx).consultarByid(empresaVO.getIdZona());
                    temp.setIdZona(zonaVO.getId());
                    temp.setNameZona(zonaVO.getName());

                }

                if(temp.getIdVariedad()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByIdVariedad(temp.getIdVariedad());
                    temp.setNameCultivo(cultivoVO.getName());
                    temp.setIdCultivo(cultivoVO.getId());
                    //obteiedno datos de  variedad
                    VariedadDAO variedadDAO = new VariedadDAO(ctx);
                    temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                }
                Log.d(TAG,"id envase:"+temp.getIdEnvase());
                if(temp.getIdEnvase()>0){
                    //obteniendo datos  de cultivo
                    EnvaseDAO envaseDAO = new EnvaseDAO(ctx);
                    EnvaseVO env = envaseDAO.consultarById(temp.getIdEnvase());
                    temp.setNameEnvase(env.getName());
                }


                recepcionVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"RECEP NO EDIT "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }

        return recepcionVOList;
    }


    public List<RecepcionVO> listAll() {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<RecepcionVO> recepcionVOList =  new ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "R."+TABLE_RECEPCION_COL_ID        +", " +//0
                            "R."+TABLE_RECEPCION_COL_IDPLANTA +", " +//1
                            "R."+TABLE_RECEPCION_COL_IDFUNDO   +", " +//2
                            "R."+TABLE_RECEPCION_COL_IDVARIEDAD+", " +//3
                            "R."+TABLE_RECEPCION_COL_IDENVASE +", "+//4
                            "R."+TABLE_RECEPCION_COL_FECHAHORA+", "+//5
                            "R."+TABLE_RECEPCION_COL_NORDENPROCESO+", "+//6
                            "R."+TABLE_RECEPCION_COL_NGUIA+", "+//7
                            "R."+TABLE_RECEPCION_COL_UNIDADES+", "+//8
                            "R."+TABLE_RECEPCION_COL_KILOS+", "+//9
                            "R."+TABLE_RECEPCION_COL_EDITING+//10
                            " FROM "+
                            TABLE_RECEPCION+" as R "+
                            " ORDER BY "+
                            "R."+TABLE_RECEPCION_COL_ID+
                            " DESC "
                    ,null);
            // Toast.makeText(ctx,"contando visitas"+cursor.getCount(),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext() && cursor.getCount()>0){
                RecepcionVO temp;
                Log.d(TAG,"hay primero");
                //Log.d(TAG,"0");
                temp = new RecepcionVO();
                temp.setId(cursor.getInt(0));
                //Log.d(TAG,"1");
                temp.setIdPlanta(cursor.getInt(1));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                //Log.d(TAG,"2");
                temp.setIdFundo(cursor.getInt(2));
                //Log.d(TAG,"3");
                temp.setIdVariedad(cursor.getInt(3));
                //Log.d(TAG,"4");
                temp.setIdEnvase(cursor.getInt(4));
                //Log.d(TAG,"5");
                temp.setFechaHora(cursor.getString(5));
                //Log.d(TAG,"6");
                temp.setnOrdenProceso(cursor.getString(6));

                //Log.d(TAG,"7");
                temp.setnOrdenGuia(cursor.getString(7));

                //Log.d(TAG,"8");
                temp.setUnidadesRecepcion(cursor.getString(8));

                //Log.d(TAG,"9");
                temp.setKilosRecepcion(cursor.getString(9));

                //Log.d(TAG,"10");
                temp.setEditing(cursor.getInt(10)>0);

                if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                    //obteniedo datos d e fundo
                    FundoDAO fundoDAO = new FundoDAO(ctx);
                    FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                    String nameFundo = f.getName();
                    temp.setNameFundo(nameFundo);
                    //obteniedno datos d e empresa
                    EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByidFundo(temp.getIdFundo());
                    temp.setIdEmpresa(empresaVO.getId());
                    temp.setNameEmpresa(empresaVO.getName());

                    ZonaVO zonaVO = new ZonaDAO(ctx).consultarByid(empresaVO.getIdZona());
                    temp.setIdZona(zonaVO.getId());
                    temp.setNameZona(zonaVO.getName());
                }

                if(temp.getIdVariedad()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByIdVariedad(temp.getIdVariedad());
                    temp.setNameCultivo(cultivoVO.getName());
                    temp.setIdCultivo(cultivoVO.getId());
                    //obteiedno datos de  variedad
                    VariedadDAO variedadDAO = new VariedadDAO(ctx);
                    temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                }
                if(temp.getIdEnvase()>0){
                    //obteniendo datos  de cultivo
                    EnvaseDAO envaseDAO = new EnvaseDAO(ctx);
                    EnvaseVO env = envaseDAO.consultarById(temp.getIdEnvase());
                    temp.setNameEnvase(env.getName());
                }
                recepcionVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"RECEP NO EDIT "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();


        }finally {
            db.close();
            c.close();
        }

        return recepcionVOList;
    }


    public RecepcionVO getEditing(){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();

        RecepcionVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "R."+TABLE_RECEPCION_COL_ID        +", " +//0
                            "R."+TABLE_RECEPCION_COL_IDPLANTA +", " +//1
                            "R."+TABLE_RECEPCION_COL_IDFUNDO   +", " +//2
                            "R."+TABLE_RECEPCION_COL_IDVARIEDAD+", " +//3
                            "R."+TABLE_RECEPCION_COL_IDENVASE +", "+//4
                            "R."+TABLE_RECEPCION_COL_FECHAHORA+", "+//5
                            "R."+TABLE_RECEPCION_COL_NORDENPROCESO+", "+//6
                            "R."+TABLE_RECEPCION_COL_NGUIA+", "+//7
                            "R."+TABLE_RECEPCION_COL_UNIDADES+", "+//8
                            "R."+TABLE_RECEPCION_COL_KILOS+", "+//9
                            "R."+TABLE_RECEPCION_COL_EDITING+//10
                            " FROM "+
                            TABLE_RECEPCION+" as R "+
                            " WHERE "+
                            "R."+TABLE_RECEPCION_COL_EDITING+"="+"1"
                    ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                Log.d(TAG,"hay primero");
                temp = new RecepcionVO();
                temp.setId(cursor.getInt(0));
                //Log.d(TAG,"1");
                temp.setIdPlanta(cursor.getInt(1));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                //Log.d(TAG,"2");
                temp.setIdFundo(cursor.getInt(2));
                //Log.d(TAG,"3");
                temp.setIdVariedad(cursor.getInt(3));
                //Log.d(TAG,"4");
                temp.setIdEnvase(cursor.getInt(4));
                //Log.d(TAG,"5");
                temp.setFechaHora(cursor.getString(5));
                //Log.d(TAG,"6");
                temp.setnOrdenProceso(cursor.getString(6));
                //Log.d(TAG,"7");
                temp.setnOrdenGuia(cursor.getString(7));
                //Log.d(TAG,"8");
                temp.setUnidadesRecepcion(cursor.getString(8));
                //Log.d(TAG,"9");
                temp.setKilosRecepcion(cursor.getString(9));
                //Log.d(TAG,"10");
                temp.setEditing(cursor.getInt(10)>0);

                if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                    //obteniedo datos d e fundo
                    FundoDAO fundoDAO = new FundoDAO(ctx);
                    FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                    String nameFundo = f.getName();
                    temp.setNameFundo(nameFundo);
                    //obteniedno datos d e empresa
                    EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByidFundo(temp.getIdFundo());
                    temp.setIdEmpresa(empresaVO.getId());
                    temp.setNameEmpresa(empresaVO.getName());

                    ZonaVO zonaVO = new ZonaDAO(ctx).consultarByid(empresaVO.getIdZona());
                    temp.setIdZona(zonaVO.getId());
                    temp.setNameZona(zonaVO.getName());

                }

                if(temp.getIdVariedad()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByIdVariedad(temp.getIdVariedad());
                    temp.setNameCultivo(cultivoVO.getName());
                    temp.setIdCultivo(cultivoVO.getId());
                    //obteiedno datos de  variedad
                    VariedadDAO variedadDAO = new VariedadDAO(ctx);
                    temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                }
                if(temp.getIdEnvase()>0){
                    //obteniendo datos  de cultivo
                    EnvaseDAO envaseDAO = new EnvaseDAO(ctx);
                    EnvaseVO env = envaseDAO.consultarById(temp.getIdEnvase());
                    temp.setNameEnvase(env.getName());
                }
            }
            cursor.close();

        }catch (Exception e){
            Log.d(TAG,"1getEditing "+e.toString());
            Toast.makeText(ctx,"1getEditing "+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

/*
    public boolean cambiarIdFundoIdVariedadIdContactoIsPersonalizadoContacto(int id,int idFundo, int idVariedad,int idContacto, boolean isPersonalizado, String Contacto){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_RECEPCION_COL_IDFUNDO,String.valueOf(idFundo));
        values.put(TABLE_RECEPCION_COL_IDVARIEDAD,String.valueOf(idVariedad));
        values.put(TABLE_RECEPCION_COL_IDCONTACTO,idContacto);
        values.put(TABLE_RECEPCION_COL_ISCONTACTOPERSONALIZADO,isPersonalizado);
        values.put(TABLE_RECEPCION_COL_CONTACTOPERSONALIZADO,Contacto);
        int res = db.update(TABLE_RECEPCION,values,TABLE_RECEPCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return  flag;
    }
    */

    public boolean setFechaHoraById(int id){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();

        /*String[] parametros =
                {
                        String.valueOf(id),
                };

        ContentValues values = new ContentValues();
        values.put(TABLE_RECEPCION_COL_LATITUDINI,lat);
        values.put(TABLE_RECEPCION_COL_LONGITUDINI,lon);
        */
        String sql = "UPDATE "+
                        TABLE_RECEPCION+
                     " SET "+
                        TABLE_RECEPCION_COL_FECHAHORA+" = datetime('now','localtime')  "+
                     " WHERE " +
                        TABLE_RECEPCION_COL_ID+"="+String.valueOf(id);
        db.execSQL(sql);
        /*int res = db.update(TABLE_RECEPCION,values,TABLE_RECEPCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        */
        c.close();
        return  flag;
    }

    


    public RecepcionVO intentarNuevo(){
        //obtener el q  se esta editando
        RecepcionVO resVisita = getEditing();
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        Long id = null;
        if(resVisita==null){//si retorno vacio osea q no hay editando creamos uno nuevo
            try {
                ContentValues values = new ContentValues();
                values.put(TABLE_RECEPCION_COL_EDITING, true);
                id = db.insert(TABLE_RECEPCION,TABLE_RECEPCION_COL_ID, values);
                resVisita = getEditing();
                Log.d(TAG,"--->"+resVisita.getFechaHora());
               // Toast.makeText(ctx, "Nueva Visita Registrada:" + id, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Log.d(TAG,"Intentar nuevo Error "+e.toString());
            }finally {
                db.close();
                c.close();
            }
        } else{
           // Toast.makeText(ctx,"Abriendo Visita "/*+resVisita.getId()*/,Toast.LENGTH_SHORT).show();
        }
        return resVisita;
    }


    public boolean deleteById(int id){
        boolean flag = false;
        new MuestraDAO(ctx).deleteByIdProcesoIdTipoProceso(id,new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros =
                {
                        String.valueOf(id),
                };

        int res = db.delete(TABLE_RECEPCION,TABLE_RECEPCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
            //new EvaluacionDAO(ctx).borrarByIdVisita(id);
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        List<RecepcionVO> recepcionVOList = new RecepcionDAO(ctx).listarNoEditable();
        for(int i=0;i<recepcionVOList.size();i++){
            new MuestraDAO(ctx).clearTableUpload(recepcionVOList.get(i).getId(),new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());
            deleteById(recepcionVOList.get(i).getId());
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean clearTableUpload(int id){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        RecepcionVO recepcionVO = new RecepcionDAO(ctx).buscarById((long)id);

            new MuestraDAO(ctx).clearTableUpload(recepcionVO.getId(),new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());
            deleteById(recepcionVO.getId());

        flag=true;

        db.close();
        conn.close();
        return flag;
    }

    public  RecepcionVO buscarById(Long id){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        RecepcionVO temp = null;
        try{
/*
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "R."+TABLE_RECEPCION_COL_ID+", " +
                            "R."+TABLE_RECEPCION_COL_EDITING+", "+
                            "R."+TABLE_RECEPCION_COL_FECHAHORA+", "+
                            "R."+TABLE_RECEPCION_COL_LATITUD+", "+
                            "R."+TABLE_RECEPCION_COL_LONGITUD+", "+
                            "R."+TABLE_RECEPCION_COL_IDFUNDO+", "+
                            "R."+TABLE_RECEPCION_COL_IDVARIEDAD+
                        " FROM "+
                            TABLE_RECEPCION+" as V"+
                        " WHERE "+
                            "R."+TABLE_RECEPCION_COL_ID+"="+String.valueOf(id)
                    ,null);
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                temp = new RecepcionVO();
                temp.setId(cursor.getInt(0));
                temp.setEditing(cursor.getInt(1) > 0);
                temp.setFechaHora(cursor.getString(2));
                temp.setLat(cursor.getDouble(3));
                temp.setLon(cursor.getDouble(4));
                temp.setIdFundo(cursor.getInt(5));
                temp.setIdVariedad(cursor.getInt(6));
                //Toast.makeText(ctx, "**"+temp.getFechaHora().toString(), Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            c.close();*/
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "R."+TABLE_RECEPCION_COL_ID        +", " +//0
                            "R."+TABLE_RECEPCION_COL_IDPLANTA +", " +//1
                            "R."+TABLE_RECEPCION_COL_IDFUNDO   +", " +//2
                            "R."+TABLE_RECEPCION_COL_IDVARIEDAD+", " +//3
                            "R."+TABLE_RECEPCION_COL_IDENVASE +", "+//4
                            "R."+TABLE_RECEPCION_COL_FECHAHORA+", "+//5
                            "R."+TABLE_RECEPCION_COL_NORDENPROCESO+", "+//6
                            "R."+TABLE_RECEPCION_COL_NGUIA+", "+//7
                            "R."+TABLE_RECEPCION_COL_UNIDADES+", "+//8
                            "R."+TABLE_RECEPCION_COL_KILOS+", "+//9
                            "R."+TABLE_RECEPCION_COL_EDITING+//10
                            " FROM "+
                            TABLE_RECEPCION+" as R "+
                            " WHERE "+
                            "R."+TABLE_RECEPCION_COL_ID+"="+String.valueOf(id)
                    ,null);
            Log.d(TAG,"contando buscar id : "+cursor.getCount());
            Log.d("your_tag", DatabaseUtils.dumpCursorToString(cursor));
            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                temp = new RecepcionVO();
                temp.setId(cursor.getInt(0));
                //Log.d(TAG,"1");
                temp.setIdPlanta(cursor.getInt(1));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                //Log.d(TAG,"2");
                temp.setIdFundo(cursor.getInt(2));
                //Log.d(TAG,"3");
                temp.setIdVariedad(cursor.getInt(3));
                //Log.d(TAG,"4");
                temp.setIdEnvase(cursor.getInt(4));
                //Log.d(TAG,"5");
                temp.setFechaHora(cursor.getString(5));
                //Log.d(TAG,"6");
                temp.setnOrdenProceso(cursor.getString(6));
                //Log.d(TAG,"7");
                temp.setnOrdenGuia(cursor.getString(7));
                //Log.d(TAG,"8");
                temp.setUnidadesRecepcion(cursor.getString(8));
                //Log.d(TAG,"9");
                temp.setKilosRecepcion(cursor.getString(9));
                //Log.d(TAG,"10");
                temp.setEditing(cursor.getInt(10)>0);

                if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                    //obteniedo datos d e fundo
                    FundoDAO fundoDAO = new FundoDAO(ctx);
                    FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                    String nameFundo = f.getName();
                    temp.setNameFundo(nameFundo);

                    //obteniedno datos d e empresa
                    EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByidFundo(temp.getIdFundo());
                    temp.setIdEmpresa(empresaVO.getId());
                    temp.setNameEmpresa(empresaVO.getName());

                    ZonaVO zonaVO = new ZonaDAO(ctx).consultarByid(empresaVO.getIdZona());
                    temp.setIdZona(zonaVO.getId());
                    temp.setNameZona(zonaVO.getName());

                    Log.d(TAG,"Z:"+temp.getIdZona()+" e:"+temp.getIdEmpresa()+" f:"+temp.getIdFundo());

                }

                if(temp.getIdVariedad()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByIdVariedad(temp.getIdVariedad());
                    temp.setNameCultivo(cultivoVO.getName());
                    temp.setIdCultivo(cultivoVO.getId());
                    //obteiedno datos de  variedad
                    VariedadDAO variedadDAO = new VariedadDAO(ctx);
                    temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                }
                if(temp.getIdEnvase()>0){
                    //obteniendo datos  de cultivo
                    EnvaseDAO envaseDAO = new EnvaseDAO(ctx);
                    EnvaseVO env = envaseDAO.consultarById(temp.getIdEnvase());
                    temp.setNameEnvase(env.getName());
                }
            }
            cursor.close();
        }catch (Exception e){
            Log.e(TAG,"buscarbyid"+e.toString());
            Toast.makeText(ctx,"buscarbyId"+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public  RecepcionVO buscarByIdToUpdate(Long id){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        RecepcionVO temp = null;
        try{
/*
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "R."+TABLE_RECEPCION_COL_ID+", " +
                            "R."+TABLE_RECEPCION_COL_EDITING+", "+
                            "R."+TABLE_RECEPCION_COL_FECHAHORA+", "+
                            "R."+TABLE_RECEPCION_COL_LATITUD+", "+
                            "R."+TABLE_RECEPCION_COL_LONGITUD+", "+
                            "R."+TABLE_RECEPCION_COL_IDFUNDO+", "+
                            "R."+TABLE_RECEPCION_COL_IDVARIEDAD+
                        " FROM "+
                            TABLE_RECEPCION+" as V"+
                        " WHERE "+
                            "R."+TABLE_RECEPCION_COL_ID+"="+String.valueOf(id)
                    ,null);
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                temp = new RecepcionVO();
                temp.setId(cursor.getInt(0));
                temp.setEditing(cursor.getInt(1) > 0);
                temp.setFechaHora(cursor.getString(2));
                temp.setLat(cursor.getDouble(3));
                temp.setLon(cursor.getDouble(4));
                temp.setIdFundo(cursor.getInt(5));
                temp.setIdVariedad(cursor.getInt(6));
                //Toast.makeText(ctx, "**"+temp.getFechaHora().toString(), Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            c.close();*/
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "R."+TABLE_RECEPCION_COL_ID        +", " +//0
                            "R."+TABLE_RECEPCION_COL_IDPLANTA +", " +//1
                            "R."+TABLE_RECEPCION_COL_IDFUNDO   +", " +//2
                            "R."+TABLE_RECEPCION_COL_IDVARIEDAD+", " +//3
                            "R."+TABLE_RECEPCION_COL_IDENVASE +", "+//4
                            "R."+TABLE_RECEPCION_COL_FECHAHORA+", "+//5
                            "R."+TABLE_RECEPCION_COL_NORDENPROCESO+", "+//6
                            "R."+TABLE_RECEPCION_COL_NGUIA+", "+//7
                            "R."+TABLE_RECEPCION_COL_UNIDADES+", "+//8
                            "R."+TABLE_RECEPCION_COL_KILOS+", "+//9
                            "R."+TABLE_RECEPCION_COL_EDITING+//10
                            " FROM "+
                            TABLE_RECEPCION+" as R "+
                            " WHERE "+
                            "R."+TABLE_RECEPCION_COL_ID+"="+String.valueOf(id)
                    ,null);
            Log.d(TAG,"contando buscar id : "+cursor.getCount());
            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                temp = new RecepcionVO();
                temp.setId(cursor.getInt(0));
                //Log.d(TAG,"1");
                temp.setIdPlanta(cursor.getInt(1));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                //Log.d(TAG,"2");
                temp.setIdFundo(cursor.getInt(2));
                //Log.d(TAG,"3");
                temp.setIdVariedad(cursor.getInt(3));
                //Log.d(TAG,"4");
                temp.setIdEnvase(cursor.getInt(4));
                //Log.d(TAG,"5");
                temp.setFechaHora(cursor.getString(5));
                //Log.d(TAG,"6");
                temp.setnOrdenProceso(cursor.getString(6));
                if(temp.getnOrdenProceso()==null || temp.getnOrdenProceso().isEmpty()){
                    temp.setnOrdenProceso("S/N");
                }
                //7
                temp.setnOrdenGuia(cursor.getString(7));
                if(temp.getnOrdenGuia()==null || temp.getnOrdenGuia().isEmpty()){
                    temp.setnOrdenGuia("S/N");
                }
                //Log.d(TAG,"8");
                temp.setUnidadesRecepcion(cursor.getString(8));
                if(temp.getUnidadesRecepcion()==null || temp.getUnidadesRecepcion().isEmpty()){
                    temp.setUnidadesRecepcion("0");
                }
                //Log.d(TAG,"9");
                temp.setKilosRecepcion(cursor.getString(9));
                if(temp.getKilosRecepcion()==null || temp.getKilosRecepcion().isEmpty()){
                    temp.setKilosRecepcion("0");
                }
                //Log.d(TAG,"10");
                temp.setEditing(cursor.getInt(10)>0);

                if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                    //obteniedo datos d e fundo
                    FundoDAO fundoDAO = new FundoDAO(ctx);
                    FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                    String nameFundo = f.getName();
                    temp.setNameFundo(nameFundo);
                    //obteniedno datos d e empresa
                    EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByidFundo(temp.getIdFundo());
                    temp.setIdEmpresa(empresaVO.getId());
                    temp.setNameEmpresa(empresaVO.getName());

                    ZonaVO zonaVO = new ZonaDAO(ctx).consultarByid(empresaVO.getIdZona());
                    temp.setIdZona(zonaVO.getId());
                    temp.setNameZona(zonaVO.getName());

                }

                if(temp.getIdVariedad()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByIdVariedad(temp.getIdVariedad());
                    temp.setNameCultivo(cultivoVO.getName());
                    temp.setIdCultivo(cultivoVO.getId());
                    //obteiedno datos de  variedad
                    VariedadDAO variedadDAO = new VariedadDAO(ctx);
                    temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                }
                Log.d(TAG,"id envase:"+temp.getIdEnvase());
                if(temp.getIdEnvase()>0){
                    //obteniendo datos  de cultivo
                    EnvaseDAO envaseDAO = new EnvaseDAO(ctx);
                    EnvaseVO env = envaseDAO.consultarById(temp.getIdEnvase());
                    temp.setNameEnvase(env.getName());
                }

            }
            cursor.close();
        }catch (Exception e){
            Log.e(TAG,"buscarbyid"+e.toString());
            Toast.makeText(ctx,"buscarbyId"+e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public boolean save(int id) {
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_RECEPCION_COL_EDITING,false);
        int res = db.update(TABLE_RECEPCION,values,TABLE_RECEPCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        c.close();
        return flag;

    }

    public boolean setBasicos(int id, int idFundo, int idVariedad, int idEnvase, String nProceso, String nGuia, String kilos, String unidades,int idPlanta) {
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_RECEPCION_COL_IDFUNDO,String.valueOf(idFundo));
        values.put(TABLE_RECEPCION_COL_IDVARIEDAD,String.valueOf(idVariedad));
        values.put(TABLE_RECEPCION_COL_IDENVASE,String.valueOf(idEnvase));
        values.put(TABLE_RECEPCION_COL_NORDENPROCESO,String.valueOf(nProceso));
        values.put(TABLE_RECEPCION_COL_NGUIA,nGuia);
        values.put(TABLE_RECEPCION_COL_KILOS,kilos);
        values.put(TABLE_RECEPCION_COL_UNIDADES,unidades);
        values.put(TABLE_RECEPCION_COL_IDPLANTA,String.valueOf(idPlanta));
        int res = db.update(TABLE_RECEPCION,values,TABLE_RECEPCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;

        }
        db.close();
        c.close();
        return  flag;
    }
}
