package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.CultivoVO;
import com.ibao.agroq.models.vo.entitiesDB.EmpresaVO;
import com.ibao.agroq.models.vo.entitiesDB.FundoVO;
import com.ibao.agroq.models.vo.entitiesDB.ZonaVO;
import com.ibao.agroq.models.vo.entitiesInternal.DescarteVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_EDITING;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_FECHAHORA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_IDENVASE;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_IDFUNDO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_IDPLANTA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_IDRECEPCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_KILOS;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_NORDENPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_UNIDADES;

public class DescarteDAO {

    String TAG = DescarteDAO.class.getSimpleName();
    Context ctx;

    public DescarteDAO(Context ctx){
        this.ctx = ctx;
    }

    public List<DescarteVO> listarNoEditable(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<DescarteVO> descarteVOList = new ArrayList<>();
        try{
                    Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESCARTE_COL_ID        +", " +//0
                            "P."+TABLE_DESCARTE_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_DESCARTE_COL_IDPLANTA +", " +//2
                            "P."+TABLE_DESCARTE_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_DESCARTE_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_DESCARTE_COL_IDENVASE +", "+//5
                            "P."+TABLE_DESCARTE_COL_FECHAHORA+", "+//6
                            "P."+TABLE_DESCARTE_COL_EDITING+", "+//7
                            "P."+TABLE_DESCARTE_COL_NORDENPROCESO+", "+//8
                            "P."+TABLE_DESCARTE_COL_UNIDADES+", "+//9
                            "P."+TABLE_DESCARTE_COL_KILOS+//10
                        " FROM "+
                            TABLE_DESCARTE+" as P "+
                        " WHERE "+
                            "P."+TABLE_DESCARTE_COL_EDITING+"="+"0"
                    ,null);
           // Toast.makeText(ctx,"contando visitas"+cursor.getCount(),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext() && cursor.getCount()>0){
                DescarteVO temp;
                //Log.d(TAG,"hay primero");
                //Log.d(TAG,"0");
                temp = new DescarteVO();

                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setFechaHora(cursor.getString(6));
                temp.setEditing(cursor.getInt(7)>0);
                temp.setnOrdenProceso(cursor.getString(8));
                if(temp.getnOrdenProceso()==null || temp.getnOrdenProceso().isEmpty()){
                    temp.setnOrdenProceso("S/N");
                }

                temp.setUnidades(cursor.getString(9));
                if(temp.getUnidades()==null || temp.getUnidades().isEmpty()){
                    temp.setUnidades("0");
                }

                temp.setKilos(cursor.getString(10));
                if(temp.getKilos()==null || temp.getKilos().isEmpty()){
                    temp.setKilos("0");
                }
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
                    //obteniendo datos  de categoria
                    temp.setNameEnvase(new EnvaseDAO(ctx).consultarById(temp.getIdEnvase()).getName());
                }
                descarteVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"produc NO EDIT "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }

        return descarteVOList;
    }


    public List<DescarteVO> listAll() {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<DescarteVO> descarteVOList =  new ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESCARTE_COL_ID        +", " +//0
                            "P."+TABLE_DESCARTE_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_DESCARTE_COL_IDPLANTA +", " +//2
                            "P."+TABLE_DESCARTE_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_DESCARTE_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_DESCARTE_COL_IDENVASE +", "+//5
                            "P."+TABLE_DESCARTE_COL_FECHAHORA+", "+//6
                            "P."+TABLE_DESCARTE_COL_EDITING+", "+//7
                            "P."+TABLE_DESCARTE_COL_NORDENPROCESO+", "+//8
                            "P."+TABLE_DESCARTE_COL_UNIDADES+", "+//9
                            "P."+TABLE_DESCARTE_COL_KILOS+//10
                            " FROM "+
                            TABLE_DESCARTE+" as P "+
                            " ORDER BY "+
                            "P."+TABLE_DESCARTE_COL_ID+
                            " DESC "
                    ,null);
            // Toast.makeText(ctx,"contando visitas"+cursor.getCount(),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext() && cursor.getCount()>0){
                DescarteVO temp;
                Log.d(TAG,"hay primero");
                //Log.d(TAG,"0");
                temp = new DescarteVO();
                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setFechaHora(cursor.getString(6));
                temp.setEditing(cursor.getInt(7)>0);


                temp.setnOrdenProceso(cursor.getString(8));


                temp.setUnidades(cursor.getString(9));


                temp.setKilos(cursor.getString(10));



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
                    //obteniendo datos  de categoria
                    temp.setNameEnvase(new EnvaseDAO(ctx).consultarById(temp.getIdEnvase()).getName());
                }
                descarteVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"produc ListAll error: "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }

        return descarteVOList;
    }


    public DescarteVO getEditing(){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();

        DescarteVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESCARTE_COL_ID        +", " +//0
                            "P."+TABLE_DESCARTE_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_DESCARTE_COL_IDPLANTA +", " +//2
                            "P."+TABLE_DESCARTE_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_DESCARTE_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_DESCARTE_COL_IDENVASE +", "+//5
                            "P."+TABLE_DESCARTE_COL_FECHAHORA+", "+//6
                            "P."+TABLE_DESCARTE_COL_EDITING+", "+//7
                            "P."+TABLE_DESCARTE_COL_NORDENPROCESO+", "+//8
                            "P."+TABLE_DESCARTE_COL_UNIDADES+", "+//9
                            "P."+TABLE_DESCARTE_COL_KILOS+//10
                            " FROM "+
                            TABLE_DESCARTE+" as P "+
                            " WHERE "+
                            "P."+TABLE_DESCARTE_COL_EDITING+"="+"1"
                    ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                Log.d(TAG,"hay primero");

                temp = new DescarteVO();
                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setFechaHora(cursor.getString(6));
                temp.setEditing(cursor.getInt(7)>0);
                temp.setnOrdenProceso(cursor.getString(8));
                temp.setUnidades(cursor.getString(9));
                temp.setKilos(cursor.getString(10));

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
                    //obteniendo datos  de categoria
                    temp.setNameEnvase(new EnvaseDAO(ctx).consultarById(temp.getIdEnvase()).getName());
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
                        TABLE_DESCARTE+
                     " SET "+
                        TABLE_DESCARTE_COL_FECHAHORA+" = datetime('now','localtime')  "+
                     " WHERE " +
                        TABLE_DESCARTE_COL_ID+"="+String.valueOf(id);
        db.execSQL(sql);
        /*int res = db.update(TABLE_RECEPCION,values,TABLE_RECEPCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        */
        return  flag;
    }

    


    public DescarteVO intentarNuevo(){
        //obtener el q  se esta editando
        DescarteVO descarteVO = getEditing();
        Long id = null;
        if(descarteVO==null){//si retorno vacio osea q no hay editando creamos uno nuevo
            try {
                ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
                SQLiteDatabase db = c.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(TABLE_DESCARTE_COL_EDITING, true);
                id = db.insert(TABLE_DESCARTE,TABLE_DESCARTE_COL_ID, values);
                c.close();
                descarteVO = getEditing();
                Log.d(TAG,"--->"+descarteVO.getFechaHora());
               // Toast.makeText(ctx, "Nueva DESCARTE :" + id, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(ctx, "Error nuevo DESCARTE :"+ e.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Intentar nuevo ErroP "+e.toString());
            }
        } else{
           // Toast.makeText(ctx,"Abriendo Visita "/*+resVisita.getId()*/,Toast.LENGTH_SHORT).show();
        }
        return descarteVO;
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

        int res = db.delete(TABLE_DESCARTE,TABLE_DESCARTE_COL_ID+"=?",parametros);
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
        List<DescarteVO> descarteVOList = new DescarteDAO(ctx).listarNoEditable();
        for(int i=0;i<descarteVOList.size();i++){
            new MuestraDAO(ctx).clearTableUpload(descarteVOList.get(i).getId(),new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());
            deleteById(descarteVOList.get(i).getId());
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
        DescarteVO descarteVO = new DescarteDAO(ctx).buscarById((long) id);

            new MuestraDAO(ctx).clearTableUpload(descarteVO.getId(),new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());
            deleteById(descarteVO.getId());
            flag=true;

        db.close();
        conn.close();
        return flag;
    }


    public  DescarteVO buscarById(Long id){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        DescarteVO temp = null;
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESCARTE_COL_ID        +", " +//0
                            "P."+TABLE_DESCARTE_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_DESCARTE_COL_IDPLANTA +", " +//2
                            "P."+TABLE_DESCARTE_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_DESCARTE_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_DESCARTE_COL_IDENVASE +", "+//5
                            "P."+TABLE_DESCARTE_COL_FECHAHORA+", "+//6
                            "P."+TABLE_DESCARTE_COL_EDITING+", "+//7
                            "P."+TABLE_DESCARTE_COL_NORDENPROCESO+", "+//8
                            "P."+TABLE_DESCARTE_COL_UNIDADES+", "+//9
                            "P."+TABLE_DESCARTE_COL_KILOS+//10
                            " FROM "+
                            TABLE_DESCARTE+" as P "+
                            " WHERE "+
                            "P."+TABLE_DESCARTE_COL_ID+"="+String.valueOf(id)                   ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                temp = new DescarteVO();
                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setFechaHora(cursor.getString(6));
                temp.setEditing(cursor.getInt(7)>0);
                temp.setnOrdenProceso(cursor.getString(8));
                temp.setUnidades(cursor.getString(9));
                temp.setKilos(cursor.getString(10));

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
                    //obteniendo datos  de categoria
                    temp.setNameEnvase(new EnvaseDAO(ctx).consultarById(temp.getIdEnvase()).getName());
                }

            }
            cursor.close();
        }catch (Exception e){
            Log.e(TAG,"buscarbyid"+e.toString());
            Toast.makeText(ctx,"buscarbyId"+e.toString(),Toast.LENGTH_SHORT).show();
        }
        return temp;
    }

    public  DescarteVO buscarByIdToUpload(Long id){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        DescarteVO temp = null;
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESCARTE_COL_ID        +", " +//0
                            "P."+TABLE_DESCARTE_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_DESCARTE_COL_IDPLANTA +", " +//2
                            "P."+TABLE_DESCARTE_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_DESCARTE_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_DESCARTE_COL_IDENVASE +", "+//5
                            "P."+TABLE_DESCARTE_COL_FECHAHORA+", "+//6
                            "P."+TABLE_DESCARTE_COL_EDITING+", "+//7
                            "P."+TABLE_DESCARTE_COL_NORDENPROCESO+", "+//8
                            "P."+TABLE_DESCARTE_COL_UNIDADES+", "+//9
                            "P."+TABLE_DESCARTE_COL_KILOS+//10
                            " FROM "+
                            TABLE_DESCARTE+" as P "+
                            " WHERE "+
                            "P."+TABLE_DESCARTE_COL_ID+"="+String.valueOf(id)                   ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                temp = new DescarteVO();
                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setFechaHora(cursor.getString(6));
                temp.setEditing(cursor.getInt(7)>0);
                temp.setnOrdenProceso(cursor.getString(8));
                if(temp.getnOrdenProceso()==null || temp.getnOrdenProceso().isEmpty()){
                    temp.setnOrdenProceso("S/N");
                }

                temp.setUnidades(cursor.getString(9));
                if(temp.getUnidades()==null || temp.getUnidades().isEmpty()){
                    temp.setUnidades("0");
                }

                temp.setKilos(cursor.getString(10));
                if(temp.getKilos()==null || temp.getKilos().isEmpty()){
                    temp.setKilos("0");
                }
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
                    //obteniendo datos  de categoria
                    temp.setNameEnvase(new EnvaseDAO(ctx).consultarById(temp.getIdEnvase()).getName());
                }

            }
            cursor.close();
        }catch (Exception e){
            Log.e(TAG,"buscarbyid"+e.toString());
            Toast.makeText(ctx,"buscarbyId"+e.toString(),Toast.LENGTH_SHORT).show();
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
        values.put(TABLE_DESCARTE_COL_EDITING,false);
        int res = db.update(TABLE_DESCARTE,values,TABLE_DESCARTE_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return flag;

    }



    public boolean setBasicos(int id, int idFundo, int idVariedad, int idEnvase, String nProceso, String kilos, String unidades, int idPlanta, int IDRECEPCION) {
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        ContentValues values = new ContentValues();
        values.put(TABLE_DESCARTE_COL_IDFUNDO,idFundo);
        values.put(TABLE_DESCARTE_COL_IDVARIEDAD,idVariedad);
        values.put(TABLE_DESCARTE_COL_IDENVASE,idEnvase);
        values.put(TABLE_DESCARTE_COL_NORDENPROCESO,nProceso);
        values.put(TABLE_DESCARTE_COL_KILOS,kilos);
        values.put(TABLE_DESCARTE_COL_UNIDADES,unidades);
        values.put(TABLE_DESCARTE_COL_IDPLANTA,idPlanta);
        values.put(TABLE_DESCARTE_COL_IDRECEPCION,IDRECEPCION);
        int res = db.update(TABLE_DESCARTE,values,TABLE_DESCARTE_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;

        }
        c.close();
        return  flag;
    }

}
