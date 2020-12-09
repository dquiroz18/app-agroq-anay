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
import com.ibao.agroq.models.vo.entitiesDB.TipoCalibreVO;
import com.ibao.agroq.models.vo.entitiesDB.ZonaVO;
import com.ibao.agroq.models.vo.entitiesInternal.ProduccionVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_EDITING;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_FECHAHORA;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDCALIBRE;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDCATEGORIA;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDDESTINO;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDENVASE;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDFUNDO;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDPLANTA;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDRECEPCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_KILOS;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_NORDENPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_UNIDADES;

public class ProduccionDAO {

    String TAG = ProduccionDAO.class.getSimpleName();
    Context ctx;

    public ProduccionDAO(Context ctx){
        this.ctx = ctx;
    }

    public List<ProduccionVO> listarNoEditable(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<ProduccionVO> produccionVOList = new ArrayList<>();
        try{
                    Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_PRODUCCION_COL_ID        +", " +//0
                            "P."+TABLE_PRODUCCION_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_PRODUCCION_COL_IDPLANTA +", " +//2
                            "P."+TABLE_PRODUCCION_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_PRODUCCION_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_PRODUCCION_COL_IDENVASE +", "+//5
                            "P."+TABLE_PRODUCCION_COL_IDDESTINO+", "+//6
                            "P."+TABLE_PRODUCCION_COL_IDCATEGORIA+", "+//7
                            "P."+TABLE_PRODUCCION_COL_IDCALIBRE+", "+//8
                            "P."+TABLE_PRODUCCION_COL_FECHAHORA+", "+//9
                            "P."+TABLE_PRODUCCION_COL_EDITING+", "+//10
                            "P."+TABLE_PRODUCCION_COL_NORDENPROCESO+", "+//11
                            "P."+TABLE_PRODUCCION_COL_UNIDADES+", "+//12
                            "P."+TABLE_PRODUCCION_COL_KILOS+//13
                        " FROM "+
                            TABLE_PRODUCCION+" as P "+
                        " WHERE "+
                            "P."+TABLE_PRODUCCION_COL_EDITING+"="+"0"
                    ,null);
           // Toast.makeText(ctx,"contando visitas"+cursor.getCount(),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext() && cursor.getCount()>0){
                ProduccionVO temp;
                //Log.d(TAG,"hay primero");
                //Log.d(TAG,"0");
                temp = new ProduccionVO();

                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setIdDestino(cursor.getInt(6));
                temp.setIdCategoria(cursor.getInt(7));
                temp.setIdCalibre(cursor.getInt(8));
                temp.setFechaHora(cursor.getString(9));
                temp.setEditing(cursor.getInt(10)>0);

                temp.setnOrdenProceso(cursor.getString(11));
                if(temp.getnOrdenProceso().equals("") || temp.getnOrdenProceso()==null || temp.getnOrdenProceso().isEmpty()){
                    temp.setnOrdenProceso("S/N");
                }


                temp.setUnidades(cursor.getString(12));
                if(temp.getUnidades().equals("") || temp.getUnidades()==null || temp.getUnidades().isEmpty()){
                    temp.setUnidades("0");
                }


                temp.setKilos(cursor.getString(13));
                if(temp.getKilos().equals("") || temp.getKilos()==null || temp.getKilos().isEmpty()){
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
                if(temp.getIdCalibre()>0){
                    TipoCalibreVO tca = new TipoCalibreDAO(ctx).consultarByidCalibre(temp.getIdCalibre());
                    temp.setNameTipoCalibre(tca.getName());
                    temp.setIdTipoCalibre(tca.getId());
                    Log.d(TAG,"name tc:"+temp.getNameTipoCalibre());
                    //obteiedno datos de  Calibre
                    CalibreDAO calibreDAO = new CalibreDAO(ctx);
                    temp.setNameCalibre(calibreDAO.consultarByid(temp.getIdCalibre()).getName());
                    Log.d(TAG,"name c:"+temp.getNameCalibre());
                }
                if(temp.getIdDestino()>0){
                    //obteniendo datos  de TipoCalibre
                    temp.setNameDestino(new DestinoDAO(ctx).consultarByid(temp.getIdDestino()).getName());
                }

                if(temp.getIdCategoria()>0){
                    //obteniendo datos  de categoria
                    temp.setNameCategoria(new CategoriaDAO(ctx).consultarByid(temp.getIdCategoria()).getName());
                }
                if(temp.getIdEnvase()>0){
                    //obteniendo datos  de categoria
                    temp.setNameEnvase(new EnvaseDAO(ctx).consultarById(temp.getIdEnvase()).getName());
                }
                produccionVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"produc NO EDIT "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
            c.close();
        }

        return produccionVOList;
    }


    public List<ProduccionVO> listAll() {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<ProduccionVO> produccionVOList =  new ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_PRODUCCION_COL_ID        +", " +//0
                            "P."+TABLE_PRODUCCION_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_PRODUCCION_COL_IDPLANTA +", " +//2
                            "P."+TABLE_PRODUCCION_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_PRODUCCION_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_PRODUCCION_COL_IDENVASE +", "+//5
                            "P."+TABLE_PRODUCCION_COL_IDDESTINO+", "+//6
                            "P."+TABLE_PRODUCCION_COL_IDCATEGORIA+", "+//7
                            "P."+TABLE_PRODUCCION_COL_IDCALIBRE+", "+//8
                            "P."+TABLE_PRODUCCION_COL_FECHAHORA+", "+//9
                            "P."+TABLE_PRODUCCION_COL_EDITING+", "+//10
                            "P."+TABLE_PRODUCCION_COL_NORDENPROCESO+", "+//11
                            "P."+TABLE_PRODUCCION_COL_UNIDADES+", "+//12
                            "P."+TABLE_PRODUCCION_COL_KILOS+//13
                            " FROM "+
                            TABLE_PRODUCCION+" as P "+
                            " ORDER BY "+
                            "P."+TABLE_PRODUCCION_COL_ID+
                            " DESC "
                    ,null);
            // Toast.makeText(ctx,"contando visitas"+cursor.getCount(),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext() && cursor.getCount()>0){
                ProduccionVO temp;
                Log.d(TAG,"hay primero");
                //Log.d(TAG,"0");
                temp = new ProduccionVO();
                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setIdDestino(cursor.getInt(6));
                temp.setIdCategoria(cursor.getInt(7));
                temp.setIdCalibre(cursor.getInt(8));
                temp.setFechaHora(cursor.getString(9));
                temp.setEditing(cursor.getInt(10)>0);

                temp.setnOrdenProceso(cursor.getString(11));


                temp.setUnidades(cursor.getString(12));


                temp.setKilos(cursor.getString(13));




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

                if(temp.getIdCalibre()>0){
                    TipoCalibreVO tca = new TipoCalibreDAO(ctx).consultarByidCalibre(temp.getIdCalibre());
                    temp.setNameTipoCalibre(tca.getName());
                    temp.setIdTipoCalibre(tca.getId());
                    Log.d(TAG,"name tc:"+temp.getNameTipoCalibre());
                    //obteiedno datos de  Calibre
                    CalibreDAO calibreDAO = new CalibreDAO(ctx);
                    temp.setNameCalibre(calibreDAO.consultarByid(temp.getIdCalibre()).getName());
                    Log.d(TAG,"name c:"+temp.getNameCalibre());
                }
                if(temp.getIdDestino()>0){
                    //obteniendo datos  de TipoCalibre
                    temp.setNameDestino(new DestinoDAO(ctx).consultarByid(temp.getIdDestino()).getName());
                }

                if(temp.getIdCategoria()>0){
                    //obteniendo datos  de categoria
                    temp.setNameCategoria(new CategoriaDAO(ctx).consultarByid(temp.getIdCategoria()).getName());
                }
                if(temp.getIdEnvase()>0){
                    //obteniendo datos  de categoria
                    temp.setNameEnvase(new EnvaseDAO(ctx).consultarById(temp.getIdEnvase()).getName());
                }
                produccionVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"produc ListAll error: "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();


        }finally {
            db.close();
            c.close();
        }

        return produccionVOList;
    }


    public ProduccionVO getEditing(){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();

        ProduccionVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_PRODUCCION_COL_ID        +", " +//0
                            "P."+TABLE_PRODUCCION_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_PRODUCCION_COL_IDPLANTA +", " +//2
                            "P."+TABLE_PRODUCCION_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_PRODUCCION_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_PRODUCCION_COL_IDENVASE +", "+//5
                            "P."+TABLE_PRODUCCION_COL_IDDESTINO+", "+//6
                            "P."+TABLE_PRODUCCION_COL_IDCATEGORIA+", "+//7
                            "P."+TABLE_PRODUCCION_COL_IDCALIBRE+", "+//8
                            "P."+TABLE_PRODUCCION_COL_FECHAHORA+", "+//9
                            "P."+TABLE_PRODUCCION_COL_EDITING+", "+//10
                            "P."+TABLE_PRODUCCION_COL_NORDENPROCESO+", "+//11
                            "P."+TABLE_PRODUCCION_COL_UNIDADES+", "+//12
                            "P."+TABLE_PRODUCCION_COL_KILOS+//13
                            " FROM "+
                            TABLE_PRODUCCION+" as P "+
                            " WHERE "+
                            "P."+TABLE_PRODUCCION_COL_EDITING+"="+"1"
                    ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                Log.d(TAG,"hay primero");

                temp = new ProduccionVO();
                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setIdDestino(cursor.getInt(6));
                temp.setIdCategoria(cursor.getInt(7));
                temp.setIdCalibre(cursor.getInt(8));
                temp.setFechaHora(cursor.getString(9));
                temp.setEditing(cursor.getInt(10)>0);
                temp.setnOrdenProceso(cursor.getString(11));
                temp.setUnidades(cursor.getString(12));
                temp.setKilos(cursor.getString(13));

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

                if(temp.getIdCalibre()>0){
                    TipoCalibreVO tca = new TipoCalibreDAO(ctx).consultarByidCalibre(temp.getIdCalibre());
                    temp.setNameTipoCalibre(tca.getName());
                    temp.setIdTipoCalibre(tca.getId());
                    Log.d(TAG,"name tc:"+temp.getNameTipoCalibre());
                    //obteiedno datos de  Calibre
                    CalibreDAO calibreDAO = new CalibreDAO(ctx);
                    temp.setNameCalibre(calibreDAO.consultarByid(temp.getIdCalibre()).getName());
                    Log.d(TAG,"name c:"+temp.getNameCalibre());
                }
                if(temp.getIdDestino()>0){
                    //obteniendo datos  de TipoCalibre
                    temp.setNameDestino(new DestinoDAO(ctx).consultarByid(temp.getIdDestino()).getName());
                }

                if(temp.getIdCategoria()>0){
                    //obteniendo datos  de categoria
                    temp.setNameCategoria(new CategoriaDAO(ctx).consultarByid(temp.getIdCategoria()).getName());
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
                        TABLE_PRODUCCION+
                     " SET "+
                        TABLE_PRODUCCION_COL_FECHAHORA+" = datetime('now','localtime')  "+
                     " WHERE " +
                        TABLE_PRODUCCION_COL_ID+"="+String.valueOf(id);
        db.execSQL(sql);
        /*int res = db.update(TABLE_RECEPCION,values,TABLE_RECEPCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        */
        db.close();
        c.close();
        return  flag;
    }

    


    public ProduccionVO intentarNuevo(){
        //obtener el q  se esta editando
        ProduccionVO produccion = getEditing();
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        Long id = null;
        if(produccion==null){//si retorno vacio osea q no hay editando creamos uno nuevo
            try {
                ContentValues values = new ContentValues();
                values.put(TABLE_PRODUCCION_COL_EDITING, true);
                id = db.insert(TABLE_PRODUCCION,TABLE_PRODUCCION_COL_ID, values);
                c.close();
                produccion = getEditing();
                Log.d(TAG,"--->"+produccion.getFechaHora());
               // Toast.makeText(ctx, "Nueva Proceso :" + id, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(ctx, "Error nuevo Proceso :"+ e.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Intentar nuevo ErroP "+e.toString());
            }finally {
                db.close();
                c.close();
            }
        } else{
           // Toast.makeText(ctx,"Abriendo Visita "/*+resVisita.getId()*/,Toast.LENGTH_SHORT).show();
        }
        return produccion;
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

        int res = db.delete(TABLE_PRODUCCION,TABLE_PRODUCCION_COL_ID+"=?",parametros);
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
        List<ProduccionVO> produccionVOList = new ProduccionDAO(ctx).listarNoEditable();
        for(int i=0;i<produccionVOList.size();i++){
            new MuestraDAO(ctx).clearTableUpload(produccionVOList.get(i).getId(),new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());
            deleteById(produccionVOList.get(i).getId());
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
        ProduccionVO produccionVO = new ProduccionDAO(ctx).buscarById((long)id);
            new MuestraDAO(ctx).clearTableUpload(produccionVO.getId(),new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());
            deleteById(produccionVO.getId());
            flag=true;

        db.close();
        conn.close();
        return flag;
    }


    public  ProduccionVO buscarById(Long id){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ProduccionVO temp = null;
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_PRODUCCION_COL_ID        +", " +//0
                            "P."+TABLE_PRODUCCION_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_PRODUCCION_COL_IDPLANTA +", " +//2
                            "P."+TABLE_PRODUCCION_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_PRODUCCION_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_PRODUCCION_COL_IDENVASE +", "+//5
                            "P."+TABLE_PRODUCCION_COL_IDDESTINO+", "+//6
                            "P."+TABLE_PRODUCCION_COL_IDCATEGORIA+", "+//7
                            "P."+TABLE_PRODUCCION_COL_IDCALIBRE+", "+//8
                            "P."+TABLE_PRODUCCION_COL_FECHAHORA+", "+//9
                            "P."+TABLE_PRODUCCION_COL_EDITING+", "+//10
                            "P."+TABLE_PRODUCCION_COL_NORDENPROCESO+", "+//11
                            "P."+TABLE_PRODUCCION_COL_UNIDADES+", "+//12
                            "P."+TABLE_PRODUCCION_COL_KILOS+//13
                            " FROM "+
                            TABLE_PRODUCCION+" as P "+
                            " WHERE "+
                            "P."+TABLE_PRODUCCION_COL_ID+"="+String.valueOf(id)                   ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                temp = new ProduccionVO();
                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setIdDestino(cursor.getInt(6));

                temp.setIdCategoria(cursor.getInt(7));

                temp.setIdCalibre(cursor.getInt(8));
                temp.setFechaHora(cursor.getString(9));
                temp.setEditing(cursor.getInt(10)>0);
                temp.setnOrdenProceso(cursor.getString(11));
                temp.setUnidades(cursor.getString(12));
                temp.setKilos(cursor.getString(13));

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

                if(temp.getIdCalibre()>0){
                    TipoCalibreVO tca = new TipoCalibreDAO(ctx).consultarByidCalibre(temp.getIdCalibre());
                    temp.setNameTipoCalibre(tca.getName());
                    temp.setIdTipoCalibre(tca.getId());
                    Log.d(TAG,"name tc:"+temp.getNameTipoCalibre());
                    //obteiedno datos de  Calibre
                    CalibreDAO calibreDAO = new CalibreDAO(ctx);
                    temp.setNameCalibre(calibreDAO.consultarByid(temp.getIdCalibre()).getName());
                    Log.d(TAG,"name c:"+temp.getNameCalibre());
                }
                if(temp.getIdDestino()>0){
                    //obteniendo datos  de TipoCalibre
                    temp.setNameDestino(new DestinoDAO(ctx).consultarByid(temp.getIdDestino()).getName());
                }

                if(temp.getIdCategoria()>0){
                    //obteniendo datos  de categoria
                    temp.setNameCategoria(new CategoriaDAO(ctx).consultarByid(temp.getIdCategoria()).getName());
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
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public  ProduccionVO buscarByIdToUpload(Long id){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ProduccionVO temp = null;
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_PRODUCCION_COL_ID        +", " +//0
                            "P."+TABLE_PRODUCCION_COL_IDRECEPCION+", "+//1
                            "P."+TABLE_PRODUCCION_COL_IDPLANTA +", " +//2
                            "P."+TABLE_PRODUCCION_COL_IDFUNDO   +", " +//3
                            "P."+TABLE_PRODUCCION_COL_IDVARIEDAD+", " +//4
                            "P."+TABLE_PRODUCCION_COL_IDENVASE +", "+//5
                            "P."+TABLE_PRODUCCION_COL_IDDESTINO+", "+//6
                            "P."+TABLE_PRODUCCION_COL_IDCATEGORIA+", "+//7
                            "P."+TABLE_PRODUCCION_COL_IDCALIBRE+", "+//8
                            "P."+TABLE_PRODUCCION_COL_FECHAHORA+", "+//9
                            "P."+TABLE_PRODUCCION_COL_EDITING+", "+//10
                            "P."+TABLE_PRODUCCION_COL_NORDENPROCESO+", "+//11
                            "P."+TABLE_PRODUCCION_COL_UNIDADES+", "+//12
                            "P."+TABLE_PRODUCCION_COL_KILOS+//13
                            " FROM "+
                            TABLE_PRODUCCION+" as P "+
                            " WHERE "+
                            "P."+TABLE_PRODUCCION_COL_ID+"="+String.valueOf(id)                   ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                temp = new ProduccionVO();
                temp.setId(cursor.getInt(0));
                temp.setIdRecepcion(cursor.getInt(1));
                temp.setIdPlanta(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());
                temp.setIdFundo(cursor.getInt(3));
                temp.setIdVariedad(cursor.getInt(4));
                temp.setIdEnvase(cursor.getInt(5));
                temp.setIdDestino(cursor.getInt(6));
                temp.setIdCategoria(cursor.getInt(7));
                temp.setIdCalibre(cursor.getInt(8));
                temp.setFechaHora(cursor.getString(9));
                temp.setEditing(cursor.getInt(10)>0);

                temp.setnOrdenProceso(cursor.getString(11));
                if(temp.getnOrdenProceso().equals("") || temp.getnOrdenProceso()==null || temp.getnOrdenProceso().isEmpty()){
                    temp.setnOrdenProceso("S/N");
                }


                temp.setUnidades(cursor.getString(12));
                if(temp.getUnidades().equals("") || temp.getUnidades()==null || temp.getUnidades().isEmpty()){
                    temp.setUnidades("0");
                }


                temp.setKilos(cursor.getString(13));
                if(temp.getKilos().equals("") || temp.getKilos()==null || temp.getKilos().isEmpty()){
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
                if(temp.getIdCalibre()>0){
                    TipoCalibreVO tca = new TipoCalibreDAO(ctx).consultarByidCalibre(temp.getIdCalibre());
                    temp.setNameTipoCalibre(tca.getName());
                    temp.setIdTipoCalibre(tca.getId());
                    Log.d(TAG,"name tc:"+temp.getNameTipoCalibre());
                    //obteiedno datos de  Calibre
                    CalibreDAO calibreDAO = new CalibreDAO(ctx);
                    temp.setNameCalibre(calibreDAO.consultarByid(temp.getIdCalibre()).getName());
                    Log.d(TAG,"name c:"+temp.getNameCalibre());
                }
                if(temp.getIdDestino()>0){
                    //obteniendo datos  de TipoCalibre
                    temp.setNameDestino(new DestinoDAO(ctx).consultarByid(temp.getIdDestino()).getName());
                }

                if(temp.getIdCategoria()>0){
                    //obteniendo datos  de categoria
                    temp.setNameCategoria(new CategoriaDAO(ctx).consultarByid(temp.getIdCategoria()).getName());
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
        values.put(TABLE_PRODUCCION_COL_EDITING,false);
        int res = db.update(TABLE_PRODUCCION,values,TABLE_PRODUCCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        c.close();
        return flag;

    }



    public boolean setBasicos(int id, int idFundo, int idVariedad, int idEnvase, String nProceso, String kilos, String unidades, int idPlanta,int idDestino,int idCalibre,int idCategoria, int IDRECEPCION) {
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        ContentValues values = new ContentValues();
        values.put(TABLE_PRODUCCION_COL_IDFUNDO,idFundo);
        values.put(TABLE_PRODUCCION_COL_IDVARIEDAD,idVariedad);
        values.put(TABLE_PRODUCCION_COL_IDENVASE,idEnvase);
        values.put(TABLE_PRODUCCION_COL_NORDENPROCESO,nProceso);
        values.put(TABLE_PRODUCCION_COL_KILOS,kilos);
        values.put(TABLE_PRODUCCION_COL_UNIDADES,unidades);
        values.put(TABLE_PRODUCCION_COL_IDPLANTA,idPlanta);
        values.put(TABLE_PRODUCCION_COL_IDDESTINO,idDestino);
        values.put(TABLE_PRODUCCION_COL_IDCATEGORIA,idCategoria);
        values.put(TABLE_PRODUCCION_COL_IDCALIBRE,idCalibre);
        values.put(TABLE_PRODUCCION_COL_IDRECEPCION,IDRECEPCION);
        int res = db.update(TABLE_PRODUCCION,values,TABLE_PRODUCCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;

        }
        c.close();
        return  flag;
    }

}
