package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.CultivoVO;
import com.ibao.agroq.models.vo.entitiesInternal.DespachoVO;
import com.ibao.agroq.models.vo.entitiesInternal.LoginDataVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_CAJASPALLET;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_CLIENTE;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_DIOXIDOCARBONO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_EDITING;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_FECHACARGA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_FECHAHORA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_FECHAINSPECCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_FECHALLEGADA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_FECHASALIDA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_GGN;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_IDCULTIVO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_IDPLANTA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_NAMECONTROLADOR;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_NCONTENEDOR;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_NPALLETS;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_NRESERVA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_ORIGEN;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_OXIGENO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_PESOCAJA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_PL;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_PREFRIO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_RG;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_TECNOLOGIA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_TEMPERATURA;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_TERMOGRAFO1;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_TERMOGRAFO2;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_TERMOGRAFO3;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_TP;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_TRANSPORTMODEL;

public class DespachoDAO {

    String TAG = DespachoDAO.class.getSimpleName();
    Context ctx;

    public DespachoDAO(Context ctx){
        this.ctx = ctx;
    }

    public List<DespachoVO> listarNoEditable(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<DespachoVO> despachoVOList = new ArrayList<>();
        try{
                    Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESPACHO_COL_ID        +", " +//0
                            "P."+TABLE_DESPACHO_COL_IDPLANTA +", " +//1
                            "P."+TABLE_DESPACHO_COL_IDCULTIVO+", " +//2
                            "P."+TABLE_DESPACHO_COL_FECHAHORA+", "+//3
                            "P."+TABLE_DESPACHO_COL_EDITING+", "+//4

                            "P."+TABLE_DESPACHO_COL_ORIGEN+", "+//5
                            "P."+TABLE_DESPACHO_COL_GGN+", "+//6
                            "P."+TABLE_DESPACHO_COL_NRESERVA+", "+//7
                            "P."+TABLE_DESPACHO_COL_TRANSPORTMODEL+", "+//8
                            "P."+TABLE_DESPACHO_COL_NCONTENEDOR+", "+//9

                            "P."+TABLE_DESPACHO_COL_FECHAINSPECCION+", "+//10
                            "P."+TABLE_DESPACHO_COL_FECHACARGA+", "+//11
                            "P."+TABLE_DESPACHO_COL_FECHASALIDA+", "+//12
                            "P."+TABLE_DESPACHO_COL_FECHALLEGADA+", "+//13

                            "P."+TABLE_DESPACHO_COL_TEMPERATURA+", "+//14
                            "P."+TABLE_DESPACHO_COL_DIOXIDOCARBONO+", "+//15
                            "P."+TABLE_DESPACHO_COL_OXIGENO+", "+//16
                            "P."+TABLE_DESPACHO_COL_TECNOLOGIA+", "+//17

                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO1+", "+//18
                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO2+", "+//19
                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO3+", "+//20

                            "P."+TABLE_DESPACHO_COL_NAMECONTROLADOR+", "+//21
                            "P."+TABLE_DESPACHO_COL_NPALLETS+", "+//22
                            "P."+TABLE_DESPACHO_COL_CAJASPALLET+", "+//23
                            "P."+TABLE_DESPACHO_COL_PESOCAJA+", "+//24
                            "P."+TABLE_DESPACHO_COL_PREFRIO+", "+//25
                            "P."+TABLE_DESPACHO_COL_CLIENTE+" "+//26
                            " FROM "+
                            TABLE_DESPACHO+" as P "+
                        " WHERE "+
                            "P."+TABLE_DESPACHO_COL_EDITING+"="+"0"
                    ,null);

            while (cursor.moveToNext() && cursor.getCount()>0){
                DespachoVO temp;
                //Log.d(TAG,"hay primero");
                //Log.d(TAG,"0");
                temp = new DespachoVO();temp.setId(cursor.getInt(0));

                temp.setIdPlanta(cursor.getInt(1));
                temp.setIdCultivo(cursor.getInt(2));
                    temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());

                String t = (cursor.getString(3));
                temp.setFechaHora(t==null || t.isEmpty() || t.equals("")?" ":t);
                temp.setEditing(cursor.getInt(4)>0);


                t =(cursor.getString(5));
                temp.setOrigen(t==null || t.isEmpty() || t.equals("")?" ":t);
                temp.setGGN(cursor.getInt(6));
                t =(cursor.getString(7));
                temp.setnReserva(t==null || t.isEmpty() || t.equals("")?" ":t);
                t=(cursor.getString(8));
                temp.setTrasportModel(t==null || t.isEmpty() || t.equals("")?" ":t);
                t=(cursor.getString(9));
                temp.setnContenedor(t==null || t.isEmpty() || t.equals("")?" ":t);

                t=(cursor.getString(10));
                temp.setFechaInspeccion(t==null || t.isEmpty() || t.equals("")?" ":t);
                t=(cursor.getString(11));
                temp.setFechaCarga(t==null || t.isEmpty() || t.equals("")?" ":t);
                t=(cursor.getString(12));
                temp.setFechaSalida(t==null || t.isEmpty() || t.equals("")?" ":t);
                t=(cursor.getString(13));
                temp.setFechaLlegada(t==null || t.isEmpty() || t.equals("")?" ":t);

                temp.setTemperatura(cursor.getFloat(14));
                temp.setDioxidoCarbono(cursor.getFloat(15));
                temp.setOxigeno(cursor.getFloat(16));
                t=(cursor.getString(17));
                temp.setTecnologia(t==null || t.isEmpty() || t.equals("")?" ":t);

                t=(cursor.getString(18));
                temp.setTermo1(t==null || t.isEmpty() || t.equals("")?" ":t);
                t=(cursor.getString(19));
                temp.setTermo2(t==null || t.isEmpty() || t.equals("")?" ":t);
                t=(cursor.getString(20));
                temp.setTermo3(t==null || t.isEmpty() || t.equals("")?" ":t);


                t=(cursor.getString(21));
                temp.setNameControlador(t==null || t.isEmpty() || t.equals("")?" ":t);
                temp.setnPallets(cursor.getInt(22));
                temp.setnCajasPallet(cursor.getInt(23));
                temp.setPesoCaja(cursor.getFloat(24));
                temp.setPreFrio(cursor.getInt(25)>0);

                t=(cursor.getString(26));
                temp.setCliente(t==null || t.isEmpty() || t.equals("")?" ":t);

                if(temp.getIdCultivo()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByid(temp.getIdCultivo());
                    temp.setNameCultivo(cultivoVO.getName());
                }


                despachoVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"produc NO EDIT "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }

        return despachoVOList;
    }


    public List<DespachoVO> listAll() {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<DespachoVO> DespachoVOList =  new ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESPACHO_COL_ID        +", " +//0
                            "P."+TABLE_DESPACHO_COL_IDPLANTA +", " +//1
                            "P."+TABLE_DESPACHO_COL_IDCULTIVO+", " +//2
                            "P."+TABLE_DESPACHO_COL_FECHAHORA+", "+//3
                            "P."+TABLE_DESPACHO_COL_EDITING+", "+//4

                            "P."+TABLE_DESPACHO_COL_ORIGEN+", "+//5
                            "P."+TABLE_DESPACHO_COL_GGN+", "+//6
                            "P."+TABLE_DESPACHO_COL_NRESERVA+", "+//7
                            "P."+TABLE_DESPACHO_COL_TRANSPORTMODEL+", "+//8
                            "P."+TABLE_DESPACHO_COL_NCONTENEDOR+", "+//9

                            "P."+TABLE_DESPACHO_COL_FECHAINSPECCION+", "+//10
                            "P."+TABLE_DESPACHO_COL_FECHACARGA+", "+//11
                            "P."+TABLE_DESPACHO_COL_FECHASALIDA+", "+//12
                            "P."+TABLE_DESPACHO_COL_FECHALLEGADA+", "+//13

                            "P."+TABLE_DESPACHO_COL_TEMPERATURA+", "+//14
                            "P."+TABLE_DESPACHO_COL_DIOXIDOCARBONO+", "+//15
                            "P."+TABLE_DESPACHO_COL_OXIGENO+", "+//16
                            "P."+TABLE_DESPACHO_COL_TECNOLOGIA+", "+//17

                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO1+", "+//18
                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO2+", "+//19
                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO3+", "+//20

                            "P."+TABLE_DESPACHO_COL_NAMECONTROLADOR+", "+//21
                            "P."+TABLE_DESPACHO_COL_NPALLETS+", "+//22
                            "P."+TABLE_DESPACHO_COL_CAJASPALLET+", "+//23
                            "P."+TABLE_DESPACHO_COL_PESOCAJA+", "+//24
                            "P."+TABLE_DESPACHO_COL_PREFRIO+", "+//25
                            "P."+TABLE_DESPACHO_COL_CLIENTE+", "+//26
                            "P."+TABLE_DESPACHO_COL_RG+", "+//26
                            "P."+TABLE_DESPACHO_COL_PL+", "+//26
                            "P."+TABLE_DESPACHO_COL_TP+//26
                            " FROM "+
                            TABLE_DESPACHO+" as P "+
                            " ORDER BY "+
                                "P."+TABLE_DESPACHO_COL_ID+
                            " DESC "
                    ,null);
            // Toast.makeText(ctx,"contando visitas"+cursor.getCount(),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext() && cursor.getCount()>0){
                DespachoVO temp;
                Log.d(TAG,"hay primero");
                //Log.d(TAG,"0");
                temp = new DespachoVO();
                temp.setId(cursor.getInt(0));
                temp.setIdPlanta(cursor.getInt(1));
                temp.setIdCultivo(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());

                temp.setFechaHora(cursor.getString(3));
                temp.setEditing(cursor.getInt(4)>0);


                temp.setOrigen(cursor.getString(5));

                temp.setGGN(cursor.getInt(6));
                temp.setnReserva(cursor.getString(7));
                temp.setTrasportModel(cursor.getString(8));
                temp.setnContenedor(cursor.getString(9));


                temp.setFechaInspeccion(cursor.getString(10));
                temp.setFechaCarga(cursor.getString(11));
                temp.setFechaSalida(cursor.getString(12));
                temp.setFechaLlegada(cursor.getString(13));


                temp.setTemperatura(cursor.getFloat(14));
                temp.setDioxidoCarbono(cursor.getFloat(15));
                temp.setOxigeno(cursor.getFloat(16));
                temp.setTecnologia(cursor.getString(17));


                temp.setTermo1(cursor.getString(18));
                temp.setTermo2(cursor.getString(19));
                temp.setTermo3(cursor.getString(20));


                temp.setNameControlador(cursor.getString(21));
                temp.setnPallets(cursor.getInt(22));
                temp.setnCajasPallet(cursor.getInt(23));
                temp.setPesoCaja(cursor.getFloat(24));
                temp.setPreFrio(cursor.getInt(25)>0);

                temp.setCliente(cursor.getString(26));
                temp.setRG(cursor.getString(27));
                temp.setPL(cursor.getString(28));
                temp.setTP(cursor.getString(29));


                if(temp.getIdCultivo()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByid(temp.getIdCultivo());
                    temp.setNameCultivo(cultivoVO.getName());
                }

                DespachoVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG,"produc ListAll error: "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();


        }
        db.close();
        c.close();
        return DespachoVOList;
    }


    public DespachoVO getEditing(){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();

        DespachoVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESPACHO_COL_ID        +", " +//0
                            "P."+TABLE_DESPACHO_COL_IDPLANTA +", " +//1
                            "P."+TABLE_DESPACHO_COL_IDCULTIVO+", " +//2
                            "P."+TABLE_DESPACHO_COL_FECHAHORA+", "+//3
                            "P."+TABLE_DESPACHO_COL_EDITING+", "+//4

                            "P."+TABLE_DESPACHO_COL_ORIGEN+", "+//5
                            "P."+TABLE_DESPACHO_COL_GGN+", "+//6
                            "P."+TABLE_DESPACHO_COL_NRESERVA+", "+//7
                            "P."+TABLE_DESPACHO_COL_TRANSPORTMODEL+", "+//8
                            "P."+TABLE_DESPACHO_COL_NCONTENEDOR+", "+//9

                            "P."+TABLE_DESPACHO_COL_FECHAINSPECCION+", "+//10
                            "P."+TABLE_DESPACHO_COL_FECHACARGA+", "+//11
                            "P."+TABLE_DESPACHO_COL_FECHASALIDA+", "+//12
                            "P."+TABLE_DESPACHO_COL_FECHALLEGADA+", "+//13

                            "P."+TABLE_DESPACHO_COL_TEMPERATURA+", "+//14
                            "P."+TABLE_DESPACHO_COL_DIOXIDOCARBONO+", "+//15
                            "P."+TABLE_DESPACHO_COL_OXIGENO+", "+//16
                            "P."+TABLE_DESPACHO_COL_TECNOLOGIA+", "+//17

                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO1+", "+//18
                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO2+", "+//19
                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO3+", "+//20

                            "P."+TABLE_DESPACHO_COL_NAMECONTROLADOR+", "+//21
                            "P."+TABLE_DESPACHO_COL_NPALLETS+", "+//22
                            "P."+TABLE_DESPACHO_COL_CAJASPALLET+", "+//23
                            "P."+TABLE_DESPACHO_COL_PESOCAJA+", "+//24
                            "P."+TABLE_DESPACHO_COL_PREFRIO+", "+//25
                            "P."+TABLE_DESPACHO_COL_CLIENTE+", "+//26
                            "P."+TABLE_DESPACHO_COL_RG+", "+//26
                            "P."+TABLE_DESPACHO_COL_PL+", "+//26
                            "P."+TABLE_DESPACHO_COL_TP+//26
                            " FROM "+
                            TABLE_DESPACHO+" as P "+
                            " WHERE "+
                            "P."+TABLE_DESPACHO_COL_EDITING+"="+"1"
                    ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                Log.d(TAG,"hay primero");

                temp = new DespachoVO();
                temp.setId(cursor.getInt(0));
                temp.setIdPlanta(cursor.getInt(1));
                temp.setIdCultivo(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());

                temp.setFechaHora(cursor.getString(3));
                temp.setEditing(cursor.getInt(4)>0);


                temp.setOrigen(cursor.getString(5));
                temp.setGGN(cursor.getInt(6));
                temp.setnReserva(cursor.getString(7));
                temp.setTrasportModel(cursor.getString(8));
                temp.setnContenedor(cursor.getString(9));


                temp.setFechaInspeccion(cursor.getString(10));
                temp.setFechaCarga(cursor.getString(11));
                temp.setFechaSalida(cursor.getString(12));
                temp.setFechaLlegada(cursor.getString(13));


                temp.setTemperatura(cursor.getFloat(14));
                temp.setDioxidoCarbono(cursor.getFloat(15));
                temp.setOxigeno(cursor.getFloat(16));
                temp.setTecnologia(cursor.getString(17));


                temp.setTermo1(cursor.getString(18));
                temp.setTermo2(cursor.getString(19));
                temp.setTermo3(cursor.getString(20));


                temp.setNameControlador(cursor.getString(21));
                temp.setnPallets(cursor.getInt(22));
                temp.setnCajasPallet(cursor.getInt(23));
                temp.setPesoCaja(cursor.getFloat(24));
                temp.setPreFrio(cursor.getInt(25)>0);

                temp.setCliente(cursor.getString(26));
                temp.setRG(cursor.getString(27));
                temp.setPL(cursor.getString(28));
                temp.setTP(cursor.getString(29));


                if(temp.getIdCultivo()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByid(temp.getIdCultivo());
                    temp.setNameCultivo(cultivoVO.getName());
                }

            }
            cursor.close();

        }catch (Exception e){
            Log.d(TAG,"1getEditing "+e.toString());
            Toast.makeText(ctx,"1getEditing "+e.toString(),Toast.LENGTH_SHORT).show();
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
                        TABLE_DESPACHO+
                     " SET "+
                        TABLE_DESPACHO_COL_FECHAHORA+" = datetime('now','localtime')  "+
                     " WHERE " +
                        TABLE_DESPACHO_COL_ID+"="+String.valueOf(id);
        db.execSQL(sql);
        /*int res = db.update(TABLE_RECEPCION,values,TABLE_RECEPCION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        */
        return  flag;
    }

    


    public DespachoVO intentarNuevo(){
        //obtener el q  se esta editando
        DespachoVO despachoVO = getEditing();
        Long id = null;
        if(despachoVO==null){//si retorno vacio osea q no hay editando creamos uno nuevo
            try {
                ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
                SQLiteDatabase db = c.getWritableDatabase();
                ContentValues values = new ContentValues();
                LoginDataVO ld = new LoginDataDAO(ctx).verficarLogueo();
                values.put(TABLE_DESPACHO_COL_IDCULTIVO, ld.getIdCultivo());
                Log.d(TAG,"idCul: "+ld.getIdCultivo());
                values.put(TABLE_DESPACHO_COL_IDPLANTA, ld.getIdPlanta());
                values.put(TABLE_DESPACHO_COL_EDITING, true);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String x = formatter.format(date);
                values.put(TABLE_DESPACHO_COL_FECHAINSPECCION, x);
                id = db.insert(TABLE_DESPACHO,TABLE_DESPACHO_COL_ID, values);
                c.close();
                despachoVO = getEditing();
                Log.d(TAG,"--->"+despachoVO.getFechaHora());
              //  Toast.makeText(ctx, "Nueva Proceso :" + id, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(ctx, "Error nuevo Proceso :"+ e.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Intentar nuevo ErroP "+e.toString());
            }
        } else{
           // Toast.makeText(ctx,"Abriendo Visita "/*+resVisita.getId()*/,Toast.LENGTH_SHORT).show();
        }
        return despachoVO;
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

        int res = db.delete(TABLE_DESPACHO,TABLE_DESPACHO_COL_ID+"=?",parametros);
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
        List<DespachoVO> despachoVOList = new DespachoDAO(ctx).listarNoEditable();
        for(int i=0;i<despachoVOList.size();i++){
            new MuestraDAO(ctx).clearTableUpload(despachoVOList.get(i).getId(),new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());
            deleteById(despachoVOList.get(i).getId());
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
        DespachoVO despachoVO = new DespachoDAO(ctx).buscarById((long) id);

            new MuestraDAO(ctx).clearTableUpload(despachoVO.getId(),new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso());
            deleteById(despachoVO.getId());
            flag=true;

        db.close();
        conn.close();
        return flag;
    }

    public  DespachoVO buscarById(Long id){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        DespachoVO temp = null;
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "P."+TABLE_DESPACHO_COL_ID        +", " +//0
                            "P."+TABLE_DESPACHO_COL_IDPLANTA +", " +//1
                            "P."+TABLE_DESPACHO_COL_IDCULTIVO+", " +//2
                            "P."+TABLE_DESPACHO_COL_FECHAHORA+", "+//3
                            "P."+TABLE_DESPACHO_COL_EDITING+", "+//4

                            "P."+TABLE_DESPACHO_COL_ORIGEN+", "+//5
                            "P."+TABLE_DESPACHO_COL_GGN+", "+//6
                            "P."+TABLE_DESPACHO_COL_NRESERVA+", "+//7
                            "P."+TABLE_DESPACHO_COL_TRANSPORTMODEL+", "+//8
                            "P."+TABLE_DESPACHO_COL_NCONTENEDOR+", "+//9

                            "P."+TABLE_DESPACHO_COL_FECHAINSPECCION+", "+//10
                            "P."+TABLE_DESPACHO_COL_FECHACARGA+", "+//11
                            "P."+TABLE_DESPACHO_COL_FECHASALIDA+", "+//12
                            "P."+TABLE_DESPACHO_COL_FECHALLEGADA+", "+//13

                            "P."+TABLE_DESPACHO_COL_TEMPERATURA+", "+//14
                            "P."+TABLE_DESPACHO_COL_DIOXIDOCARBONO+", "+//15
                            "P."+TABLE_DESPACHO_COL_OXIGENO+", "+//16
                            "P."+TABLE_DESPACHO_COL_TECNOLOGIA+", "+//17

                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO1+", "+//18
                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO2+", "+//19
                            "P."+TABLE_DESPACHO_COL_TERMOGRAFO3+", "+//20

                            "P."+TABLE_DESPACHO_COL_NAMECONTROLADOR+", "+//21
                            "P."+TABLE_DESPACHO_COL_NPALLETS+", "+//22
                            "P."+TABLE_DESPACHO_COL_CAJASPALLET+", "+//23
                            "P."+TABLE_DESPACHO_COL_PESOCAJA+", "+//24
                            "P."+TABLE_DESPACHO_COL_PREFRIO+", "+//25
                            "P."+TABLE_DESPACHO_COL_CLIENTE+", "+//26
                            "P."+TABLE_DESPACHO_COL_RG+", "+//26
                            "P."+TABLE_DESPACHO_COL_PL+", "+//26
                            "P."+TABLE_DESPACHO_COL_TP+//26
                            " FROM "+
                            TABLE_DESPACHO+" as P "+
                            " WHERE "+
                            "P."+TABLE_DESPACHO_COL_ID+"="+String.valueOf(id)                   ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                temp = new DespachoVO();
                temp.setId(cursor.getInt(0));
                temp.setIdPlanta(cursor.getInt(1));
                temp.setIdCultivo(cursor.getInt(2));
                temp.setNamePlanta(new PlantaDAO(ctx).consultarByid(temp.getIdPlanta()).getName());

                temp.setFechaHora(cursor.getString(3));
                temp.setEditing(cursor.getInt(4)>0);


                temp.setOrigen(cursor.getString(5));
                temp.setGGN(cursor.getInt(6));
                temp.setnReserva(cursor.getString(7));
                temp.setTrasportModel(cursor.getString(8));
                temp.setnContenedor(cursor.getString(9));


                temp.setFechaInspeccion(cursor.getString(10));
                temp.setFechaCarga(cursor.getString(11));
                temp.setFechaSalida(cursor.getString(12));
                temp.setFechaLlegada(cursor.getString(13));


                temp.setTemperatura(cursor.getFloat(14));
                temp.setDioxidoCarbono(cursor.getFloat(15));
                temp.setOxigeno(cursor.getFloat(16));
                temp.setTecnologia(cursor.getString(17));


                temp.setTermo1(cursor.getString(18));
                temp.setTermo2(cursor.getString(19));
                temp.setTermo3(cursor.getString(20));


                temp.setNameControlador(cursor.getString(21));
                temp.setnPallets(cursor.getInt(22));
                temp.setnCajasPallet(cursor.getInt(23));
                temp.setPesoCaja(cursor.getFloat(24));
                temp.setPreFrio(cursor.getInt(25)>0);

                temp.setCliente(cursor.getString(26));
                temp.setRG(cursor.getString(27));
                temp.setPL(cursor.getString(28));
                temp.setTP(cursor.getString(29));



                if(temp.getIdCultivo()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarByid(temp.getIdCultivo());
                    temp.setNameCultivo(cultivoVO.getName());
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
        values.put(TABLE_DESPACHO_COL_EDITING,false);
        int res = db.update(TABLE_DESPACHO,values,TABLE_DESPACHO_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return flag;

    }



    public boolean setBasicos(int id,
                              String fechaInspeccion,
                              String fechaCarga,
                              String fechaLlegada,
                              String fechaSalida,
                              String nContenedor,
                              String nameControlador,
                              String termo1,
                              String termo2,
                              String termo3,
                              String origen,
                              String cliente,
                              int GGN,
                              String nReserva,
                              String transportModel,
                              float temperatura,
                              float CO2,
                              float O2,
                              String tecnologia,
                              Boolean isPreFrio,
                              int nPallets,
                              int nCajasPallets,
                              float pesoCaja,
                              String rg,
                              String pl,
                              String tp){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        ContentValues values = new ContentValues();
        values.put(TABLE_DESPACHO_COL_FECHAINSPECCION,fechaInspeccion);
        values.put(TABLE_DESPACHO_COL_FECHACARGA,fechaCarga);
        values.put(TABLE_DESPACHO_COL_FECHALLEGADA,fechaLlegada);
        values.put(TABLE_DESPACHO_COL_FECHASALIDA,fechaSalida);
        values.put(TABLE_DESPACHO_COL_NCONTENEDOR,nContenedor);
        values.put(TABLE_DESPACHO_COL_NAMECONTROLADOR,nameControlador);
        values.put(TABLE_DESPACHO_COL_TERMOGRAFO1,termo1);
        values.put(TABLE_DESPACHO_COL_TERMOGRAFO2,termo2);
        values.put(TABLE_DESPACHO_COL_TERMOGRAFO3,termo3);
        values.put(TABLE_DESPACHO_COL_ORIGEN,origen);
        values.put(TABLE_DESPACHO_COL_CLIENTE,cliente);
        values.put(TABLE_DESPACHO_COL_GGN,GGN);
        values.put(TABLE_DESPACHO_COL_NRESERVA,nReserva);
        values.put(TABLE_DESPACHO_COL_TRANSPORTMODEL,transportModel);
        values.put(TABLE_DESPACHO_COL_TEMPERATURA,temperatura);
        values.put(TABLE_DESPACHO_COL_DIOXIDOCARBONO,CO2);
        values.put(TABLE_DESPACHO_COL_OXIGENO,O2);
        values.put(TABLE_DESPACHO_COL_TECNOLOGIA,tecnologia);
        values.put(TABLE_DESPACHO_COL_PREFRIO,isPreFrio);
        values.put(TABLE_DESPACHO_COL_NPALLETS,nPallets);
        values.put(TABLE_DESPACHO_COL_CAJASPALLET,nCajasPallets);
        values.put(TABLE_DESPACHO_COL_PESOCAJA,pesoCaja);
        values.put(TABLE_DESPACHO_COL_RG,rg);
        values.put(TABLE_DESPACHO_COL_PL,pl);
        values.put(TABLE_DESPACHO_COL_TP,tp);

        int res = db.update(TABLE_DESPACHO,values,TABLE_DESPACHO_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;

        }
        c.close();
        return  flag;
    }

}
