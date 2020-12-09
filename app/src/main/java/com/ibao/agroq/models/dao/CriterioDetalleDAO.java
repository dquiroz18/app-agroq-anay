package com.ibao.agroq.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.agroq.ConexionSQLiteHelper;
import com.ibao.agroq.models.vo.entitiesDB.CalibreVO;
import com.ibao.agroq.models.vo.entitiesDB.CriterioVO;
import com.ibao.agroq.models.vo.entitiesDB.TolEvaVO;
import com.ibao.agroq.models.vo.entitiesInternal.CriterioDetalleVO;
import com.ibao.agroq.models.vo.entitiesInternal.EvaluacionDetalleVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.agroq.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.agroq.utilities.Utilities.DATABASE_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIODETALLE;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIODETALLE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIODETALLE_COL_IDCRITERIO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIODETALLE_COL_VALOR;

import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_CODIGO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_ISPHOTO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_NAME;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TIPODATO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TOLERANCIAMAXIMO;
import static com.ibao.agroq.utilities.Utilities.TABLE_CRITERIO_COL_TOLERANCIAMINIMO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESCARTE_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_DESPACHO_COL_IDCULTIVO;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE_COL_IDEVALUACION;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACIONDETALLE_COL_IDMUESTRA;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_EVALUACION_COL_ISMATSEC;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_CANTIDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_MUESTRA_COL_IDPROCESO;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_PRODUCCION_COL_IDVARIEDAD;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_ID;
import static com.ibao.agroq.utilities.Utilities.TABLE_RECEPCION_COL_IDVARIEDAD;

public class CriterioDetalleDAO {

    String TAG = CriterioDetalleDAO.class.getSimpleName();

    Context ctx;

    public CriterioDetalleDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean editValor(int id, String valor) {

        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_CRITERIODETALLE_COL_VALOR,valor);
        int res = db.update(TABLE_CRITERIODETALLE,values,TABLE_CRITERIODETALLE_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        c.close();
        return  flag;

    }

    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CRITERIODETALLE,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public List<CriterioDetalleVO> createAllByIdEvaluacionDetalle(int idEvaluacionDetalle){
        ConexionSQLiteHelper con= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        EvaluacionDetalleVO evaluacionDetalleVO = new EvaluacionDetalleDAO(ctx).consultarByid(idEvaluacionDetalle);

        List<CriterioVO> criterioVOList = new CriterioDAO(ctx).listarByIdEvaluacionIdTipoProceso(
                evaluacionDetalleVO.getIdEvaluacion(),
                new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso()
        );



        List<CriterioDetalleVO> criterioDetalleVOList = new ArrayList<>();

        Log.d(TAG,"intentando insertar "+criterioVOList.size()+" criteriosdetalle");

        for(CriterioVO c :criterioVOList){
            Log.d(TAG,"isnertando: ID:"+c.getId()+" "+c.getName()+" "+c.getIdEvaluacion()+" "+c.getIdTipoProceso());
            int idCriterio = c.getId();
            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
                values.put(TABLE_CRITERIODETALLE_COL_IDCRITERIO,idCriterio);
                values.put(TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE,idEvaluacionDetalle);
            Long temp = db.insert(TABLE_CRITERIODETALLE,TABLE_CRITERIODETALLE_COL_ID,values);
            if(temp>0){
                Log.d(TAG,"ISNERTADO CRIERIO :"+temp+" en ED:"+idEvaluacionDetalle);
            }else {
                Log.d(TAG,"no se pudo insertar en ED:"+idEvaluacionDetalle);
            }
            db.close();
            conn.close();
            criterioDetalleVOList.add(consultarByid(temp));
        }
        con.close();
        return criterioDetalleVOList;
    }

    public CriterioDetalleVO consultarByid(long id){
        ConexionSQLiteHelper con= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = con.getReadableDatabase();
        CriterioDetalleVO temp = null;
        try{
            int idTipoProceso;
            int nFrutos;
            temp = new CriterioDetalleVO();
            TolEvaVO tolEva;
            Cursor cursor ;
            cursor= db.rawQuery(
                    "SELECT " +
                            " * "+
                            " FROM "+
                            TABLE_EVALUACIONDETALLE+" as ED"+
                            " WHERE " +
                            "ED."+TABLE_EVALUACIONDETALLE_COL_ID+" = "+String.valueOf(id)
                    ,null);
            int TP = new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso();
            switch (TP) {
                case 1:
                    cursor = db.rawQuery(

                            "SELECT " +
                                    "CD."+TABLE_CRITERIODETALLE_COL_ID+", " +//0
                                    "CD."+TABLE_CRITERIODETALLE_COL_VALOR+", "+//1
                                    "CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+", "+//2
                                    "CD."+TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+", "+//3
                                    "M."+TABLE_MUESTRA_COL_CANTIDAD+", "+//4
                                    "R."+TABLE_RECEPCION_COL_IDVARIEDAD+", "+//5
                                    "E."+TABLE_EVALUACION_COL_ID+","+//6
                                    "E."+TABLE_EVALUACION_COL_ISMATSEC+", "+//7
                                    "C."+TABLE_CRITERIO_COL_NAME+", "+//8
                                    "C."+TABLE_CRITERIO_COL_TIPODATO+", "+//9
                                    "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+//10
                                    "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+//11
                                    "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+//12
                                    "C."+TABLE_CRITERIO_COL_CODIGO+" "+//13
                                    " FROM "+
                                    TABLE_CRITERIO+" as C, "+
                                    TABLE_CRITERIODETALLE+" as CD, "+
                                    TABLE_MUESTRA+" as M, "+
                                    TABLE_EVALUACION+" as E, "+
                                    TABLE_RECEPCION+" as R, "+
                                    TABLE_EVALUACIONDETALLE+" as ED"+
                                    " WHERE "+
                                    "C."+TABLE_CRITERIO_COL_ID+" = "+"CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+
                                    " AND "+
                                    "CD."+TABLE_CRITERIODETALLE_COL_ID+" = "+String.valueOf(id)+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_ID+"="+"ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+"="+"CD."+TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+" = "+"E."+TABLE_EVALUACION_COL_ID+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_IDPROCESO+" = "+"R."+TABLE_RECEPCION_COL_ID


                            ,null);
                    cursor.moveToFirst();
                    temp.setId(cursor.getInt(0));
                    temp.setValor(cursor.getString(1));
                    temp.setIdCriterio(cursor.getInt(2));
                    temp.setIdEvaluacionDetalle(cursor.getInt(3));

                    temp.setNameCriterio(cursor.getString(8));
                    temp.setTipoDatoCriterio(cursor.getString(9));
                    temp.setFotografiableCriterio(cursor.getInt(10)>0);
                    temp.setTolMinCriterio(cursor.getFloat(11));
                    temp.setTolMaxCriterio(cursor.getFloat(12));
                    temp.setCodigo(cursor.getInt(13));

/*
            CriterioVO cri = new CriterioDAO(ctx).consultarByid(temp.getIdCriterio());
                    temp.setNameCriterio(cri.getName());
                    temp.setTipoDatoCriterio(cri.getTipoDato());
                    temp.setFotografiableCriterio(cri.isFotografiable());
                    temp.setTolMinCriterio(cri.getTolMin());
                    temp.setTolMaxCriterio(cri.getTolMax());
                    temp.setCodigo(cri.getCodigo());
  */
                    nFrutos = cursor.getInt(4);
                    if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                        temp.setPorcentaje((1.0f*Float.valueOf(temp.getValor()==null||temp.getValor().isEmpty()||temp.getValor().equals("")?"0":temp.getValor())/(1.0f*nFrutos))*100.0f);

                    }

                    idTipoProceso = TP;
                    if(temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                        Log.d(TAG, "f1");
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f*Float.valueOf(temp.getValor()==null || temp.getValor().isEmpty() || temp.getValor().equals("")?"0":temp.getValor())));
                        }


                        tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5),cursor.getInt(6),idTipoProceso,cursor.getInt(7)>0);

                        if(tolEva!=null){
                            temp.setTolMinCriterio(tolEva.getTolMin());
                            temp.setTolMaxCriterio(tolEva.getTolMax());
                            Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                            Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                        }else {
                            Log.d(TAG,"sintol eva");

                        }
                    }

                    if(temp.getTolMaxCriterio()!=temp.getTolMinCriterio() && temp.getTolMinCriterio()>0){
                        if(temp.getPorcentaje()<temp.getTolMinCriterio()){
                            //cambiar icono a good
                            if(cursor.getInt(7)>0){
                                temp.setCalificacion(3);
                            }else {
                                temp.setCalificacion(1);
                            }
                        }else {
                            if(temp.getPorcentaje()<=temp.getTolMaxCriterio()){
                                if(cursor.getInt(7)>0 ){
                                    temp.setCalificacion(1);
                                }else {
                                    temp.setCalificacion(2);
                                }

                            }else {
                                //cambiar  a  ic mal
                                temp.setCalificacion(3);
                            }
                        }
                    }else {
                        temp.setCalificacion(0);
                    }
                    break;

                case 2:
                    cursor = db.rawQuery(

                            "SELECT " +
                            "CD."+TABLE_CRITERIODETALLE_COL_ID+", " +//0
                            "CD."+TABLE_CRITERIODETALLE_COL_VALOR+", "+//1
                            "CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+", "+//2
                            "CD."+TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+", "+//3
                            "M."+TABLE_MUESTRA_COL_CANTIDAD+", "+//4
                            "R."+TABLE_PRODUCCION_COL_IDVARIEDAD+", "+//5
                            "E."+TABLE_EVALUACION_COL_ID+","+//6
                            "E."+TABLE_EVALUACION_COL_ISMATSEC+", "+//7
                            "C."+TABLE_CRITERIO_COL_NAME+", "+//8
                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+//9
                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+//10
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+//11
                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+//12
                            "C."+TABLE_CRITERIO_COL_CODIGO+" "+//13
                            " FROM "+
                            TABLE_CRITERIO+" as C, "+
                            TABLE_CRITERIODETALLE+" as CD, "+
                            TABLE_MUESTRA+" as M, "+
                            TABLE_EVALUACION+" as E, "+
                            TABLE_PRODUCCION+" as R, "+
                            TABLE_EVALUACIONDETALLE+" as ED"+
                            " WHERE "+
                            "C."+TABLE_CRITERIO_COL_ID+" = "+"CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+
                            " AND "+
                            "CD."+TABLE_CRITERIODETALLE_COL_ID+" = "+String.valueOf(id)+
                            " AND "+
                            "M."+TABLE_MUESTRA_COL_ID+"="+"ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+
                            " AND "+
                            "ED."+TABLE_EVALUACIONDETALLE_COL_ID+"="+"CD."+TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+
                            " AND "+
                            "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+" = "+"E."+TABLE_EVALUACION_COL_ID+
                            " AND "+
                            "M."+TABLE_MUESTRA_COL_IDPROCESO+" = "+"R."+TABLE_PRODUCCION_COL_ID


                            ,null);
                    cursor.moveToFirst();
                    temp.setId(cursor.getInt(0));
                    temp.setValor(cursor.getString(1));
                    temp.setIdCriterio(cursor.getInt(2));
                    temp.setIdEvaluacionDetalle(cursor.getInt(3));

                    temp.setNameCriterio(cursor.getString(8));
                    temp.setTipoDatoCriterio(cursor.getString(9));
                    temp.setFotografiableCriterio(cursor.getInt(10)>0);
                    temp.setTolMinCriterio(cursor.getFloat(11));
                    temp.setTolMaxCriterio(cursor.getFloat(12));
                    temp.setCodigo(cursor.getInt(13));

/*
            CriterioVO cri = new CriterioDAO(ctx).consultarByid(temp.getIdCriterio());
                    temp.setNameCriterio(cri.getName());
                    temp.setTipoDatoCriterio(cri.getTipoDato());
                    temp.setFotografiableCriterio(cri.isFotografiable());
                    temp.setTolMinCriterio(cri.getTolMin());
                    temp.setTolMaxCriterio(cri.getTolMax());
                    temp.setCodigo(cri.getCodigo());
  */
                    nFrutos = cursor.getInt(4);
                    if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                        temp.setPorcentaje((1.0f*Float.valueOf(temp.getValor()==null||temp.getValor().isEmpty()||temp.getValor().equals("")?"0":temp.getValor())/(1.0f*nFrutos))*100.0f);

                    }

                    idTipoProceso = TP;

                    if(temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                        Log.d(TAG, "f1");
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                             temp.setPorcentaje((1.0f*Float.valueOf(temp.getValor()==null || temp.getValor().isEmpty() || temp.getValor().equals("")?"0":temp.getValor())));

                        }


                        tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5),cursor.getInt(6),idTipoProceso,cursor.getInt(7)>0);

                        if(tolEva!=null){
                            temp.setTolMinCriterio(tolEva.getTolMin());
                            temp.setTolMaxCriterio(tolEva.getTolMax());
                            Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                            Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                        }else {
                            Log.d(TAG,"sintol eva");

                        }
                    }


                    CalibreVO cal = new CalibreDAO(ctx).consultarTolCalibreByidCriterioDetalle(temp.getId());
                    if(cal != null){
                        Log.d(TAG,"encontro CALIBRE");
                        temp.setTolMinCriterio(cal.getPesoMin().isEmpty() || cal.getPesoMin()==null || cal.getPesoMin().equals("")? 0.0f:Float.valueOf(cal.getPesoMin()));
                        temp.setTolMaxCriterio(cal.getPesoMax().isEmpty() || cal.getPesoMax()==null || cal.getPesoMin().equals("")? 0.0f:Float.valueOf(cal.getPesoMax()));
                        Log.d(TAG,"tol Min"+temp.getTolMinCriterio());
                        Log.d(TAG,"tol Max"+temp.getTolMaxCriterio());
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f * Float.valueOf(
                                    temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("")
                                            ? "0" :
                                            temp.getValor())));
                        }


                    }else {
                        Log.d(TAG,"NO ENC CALIBRE");
                    }


                    if(temp.getTolMaxCriterio()!=temp.getTolMinCriterio() && temp.getTolMinCriterio()>0){
                        if(temp.getPorcentaje()<temp.getTolMinCriterio()){
                            //cambiar icono a good
                            if(cursor.getInt(7)>0|| cal !=null){
                                temp.setCalificacion(3);
                            }else {
                                    temp.setCalificacion(1);
                            }
                        }else {
                            if(temp.getPorcentaje()<=temp.getTolMaxCriterio()){
                                if(cursor.getInt(7)>0|| cal !=null){
                                    temp.setCalificacion(1);
                                }else {

                                    temp.setCalificacion(2);
                                }

                            }else {
                                //cambiar  a  ic mal
                                temp.setCalificacion(3);
                            }
                        }

                    }else {
                        temp.setCalificacion(0);
                    }
                    Log.d(TAG,"CALIF CRID:"+temp.getCalificacion());

                    break;


                case 3:
                    cursor = db.rawQuery(

                            "SELECT " +
                                    "CD."+TABLE_CRITERIODETALLE_COL_ID+", " +//0
                                    "CD."+TABLE_CRITERIODETALLE_COL_VALOR+", "+//1
                                    "CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+", "+//2
                                    "CD."+TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+", "+//3
                                    "M."+TABLE_MUESTRA_COL_CANTIDAD+", "+//4
                                    "R."+TABLE_DESPACHO_COL_IDCULTIVO+", "+//5
                                    "E."+TABLE_EVALUACION_COL_ID+","+//6
                                    "E."+TABLE_EVALUACION_COL_ISMATSEC+", "+//7
                                    "C."+TABLE_CRITERIO_COL_NAME+", "+//8
                                    "C."+TABLE_CRITERIO_COL_TIPODATO+", "+//9
                                    "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+//10
                                    "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+//11
                                    "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+//12
                                    "C."+TABLE_CRITERIO_COL_CODIGO+" "+//13
                                    " FROM "+
                                    TABLE_CRITERIO+" as C, "+
                                    TABLE_CRITERIODETALLE+" as CD, "+
                                    TABLE_MUESTRA+" as M, "+
                                    TABLE_EVALUACION+" as E, "+
                                    TABLE_DESPACHO+" as R, "+
                                    TABLE_EVALUACIONDETALLE+" as ED"+
                                    " WHERE "+
                                    "C."+TABLE_CRITERIO_COL_ID+" = "+"CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+
                                    " AND "+
                                    "CD."+TABLE_CRITERIODETALLE_COL_ID+" = "+String.valueOf(id)+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_ID+"="+"ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+"="+"CD."+TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+" = "+"E."+TABLE_EVALUACION_COL_ID+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_IDPROCESO+" = "+"R."+TABLE_DESPACHO_COL_ID


                            ,null);
                    Log.d(TAG,"LISTANDO CD:"+cursor.getCount());
                    cursor.moveToFirst();
                    temp.setId(cursor.getInt(0));
                    temp.setValor(cursor.getString(1));
                    temp.setIdCriterio(cursor.getInt(2));
                    temp.setIdEvaluacionDetalle(cursor.getInt(3));

                    temp.setNameCriterio(cursor.getString(8));
                    temp.setTipoDatoCriterio(cursor.getString(9));
                    temp.setFotografiableCriterio(cursor.getInt(10)>0);
                    temp.setTolMinCriterio(cursor.getFloat(11));
                    temp.setTolMaxCriterio(cursor.getFloat(12));
                    temp.setCodigo(cursor.getInt(13));

/*
            CriterioVO cri = new CriterioDAO(ctx).consultarByid(temp.getIdCriterio());
                    temp.setNameCriterio(cri.getName());
                    temp.setTipoDatoCriterio(cri.getTipoDato());
                    temp.setFotografiableCriterio(cri.isFotografiable());
                    temp.setTolMinCriterio(cri.getTolMin());
                    temp.setTolMaxCriterio(cri.getTolMax());
                    temp.setCodigo(cri.getCodigo());
  */
                    nFrutos = cursor.getInt(4);
                    if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                       temp.setPorcentaje((1.0f*Float.valueOf(temp.getValor()==null||temp.getValor().isEmpty()||temp.getValor().equals("")?"0":temp.getValor())/(1.0f*nFrutos))*100.0f);

                    }

                    idTipoProceso = TP;
                    if(temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                        Log.d(TAG, "f1");
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f*Float.valueOf(temp.getValor()==null || temp.getValor().isEmpty() || temp.getValor().equals("")?"0":temp.getValor())));

                        }


                        tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5),cursor.getInt(6),idTipoProceso,cursor.getInt(7)>0);

                        if(tolEva!=null){
                            temp.setTolMinCriterio(tolEva.getTolMin());
                            temp.setTolMaxCriterio(tolEva.getTolMax());
                            Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                            Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                        }else {
                            Log.d(TAG,"sintol eva");

                        }
                    }

                    if(temp.getTolMaxCriterio()!=temp.getTolMinCriterio() && temp.getTolMinCriterio()>0){
                        if(temp.getPorcentaje()<temp.getTolMinCriterio()){
                            //cambiar icono a good
                            if(cursor.getInt(7)>0){
                                temp.setCalificacion(3);
                            }else {
                                temp.setCalificacion(1);
                            }
                        }else {
                            if(temp.getPorcentaje()<=temp.getTolMaxCriterio()){
                                if(cursor.getInt(7)>0 ){
                                    temp.setCalificacion(1);
                                }else {
                                    temp.setCalificacion(2);
                                }

                            }else {
                                //cambiar  a  ic mal
                                temp.setCalificacion(3);
                            }
                        }
                    }else {
                        temp.setCalificacion(0);
                    }
                    break;
                case 4:
                    cursor = db.rawQuery(

                            "SELECT " +
                                    "CD."+TABLE_CRITERIODETALLE_COL_ID+", " +//0
                                    "CD."+TABLE_CRITERIODETALLE_COL_VALOR+", "+//1
                                    "CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+", "+//2
                                    "CD."+TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+", "+//3
                                    "M."+TABLE_MUESTRA_COL_CANTIDAD+", "+//4
                                    "R."+TABLE_DESCARTE_COL_IDVARIEDAD+", "+//5
                                    "E."+TABLE_EVALUACION_COL_ID+","+//6
                                    "E."+TABLE_EVALUACION_COL_ISMATSEC+", "+//7
                                    "C."+TABLE_CRITERIO_COL_NAME+", "+//8
                                    "C."+TABLE_CRITERIO_COL_TIPODATO+", "+//9
                                    "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+//10
                                    "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+//11
                                    "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+//12
                                    "C."+TABLE_CRITERIO_COL_CODIGO+" "+//13
                                    " FROM "+
                                    TABLE_CRITERIO+" as C, "+
                                    TABLE_CRITERIODETALLE+" as CD, "+
                                    TABLE_MUESTRA+" as M, "+
                                    TABLE_EVALUACION+" as E, "+
                                    TABLE_DESCARTE+" as R, "+
                                    TABLE_EVALUACIONDETALLE+" as ED"+
                                    " WHERE "+
                                    "C."+TABLE_CRITERIO_COL_ID+" = "+"CD."+TABLE_CRITERIODETALLE_COL_IDCRITERIO+
                                    " AND "+
                                    "CD."+TABLE_CRITERIODETALLE_COL_ID+" = "+String.valueOf(id)+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_ID+"="+"ED."+TABLE_EVALUACIONDETALLE_COL_IDMUESTRA+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_ID+"="+"CD."+TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+
                                    " AND "+
                                    "ED."+TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+" = "+"E."+TABLE_EVALUACION_COL_ID+
                                    " AND "+
                                    "M."+TABLE_MUESTRA_COL_IDPROCESO+" = "+"R."+TABLE_DESCARTE_COL_ID


                            ,null);
                    cursor.moveToFirst();
                    temp.setId(cursor.getInt(0));
                    temp.setValor(cursor.getString(1));
                    temp.setIdCriterio(cursor.getInt(2));
                    temp.setIdEvaluacionDetalle(cursor.getInt(3));

                    temp.setNameCriterio(cursor.getString(8));
                    temp.setTipoDatoCriterio(cursor.getString(9));
                    temp.setFotografiableCriterio(cursor.getInt(10)>0);
                    temp.setTolMinCriterio(cursor.getFloat(11));
                    temp.setTolMaxCriterio(cursor.getFloat(12));
                    temp.setCodigo(cursor.getInt(13));

/*
            CriterioVO cri = new CriterioDAO(ctx).consultarByid(temp.getIdCriterio());
                    temp.setNameCriterio(cri.getName());
                    temp.setTipoDatoCriterio(cri.getTipoDato());
                    temp.setFotografiableCriterio(cri.isFotografiable());
                    temp.setTolMinCriterio(cri.getTolMin());
                    temp.setTolMaxCriterio(cri.getTolMax());
                    temp.setCodigo(cri.getCodigo());
  */
                    nFrutos = cursor.getInt(4);
                    if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                         temp.setPorcentaje((1.0f*Float.valueOf(temp.getValor()==null||temp.getValor().isEmpty()||temp.getValor().equals("")?"0":temp.getValor())/(1.0f*nFrutos))*100.0f);

                    }

                    idTipoProceso = TP;
                    if(temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                        Log.d(TAG, "f1");
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f*Float.valueOf(temp.getValor()==null || temp.getValor().isEmpty() || temp.getValor().equals("")?"0":temp.getValor())));

                        }


                        tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5),cursor.getInt(6),idTipoProceso,cursor.getInt(7)>0);

                        if(tolEva!=null){
                            temp.setTolMinCriterio(tolEva.getTolMin());
                            temp.setTolMaxCriterio(tolEva.getTolMax());
                            Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                            Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                        }else {
                            Log.d(TAG,"sintol eva");

                        }
                    }

                    if(temp.getTolMaxCriterio()!=temp.getTolMinCriterio() && temp.getTolMinCriterio()>0){
                        if(temp.getPorcentaje()<temp.getTolMinCriterio()){
                            //cambiar icono a good
                            if(cursor.getInt(7)>0){
                                temp.setCalificacion(3);
                            }else {
                                temp.setCalificacion(1);
                            }
                        }else {
                            if(temp.getPorcentaje()<=temp.getTolMaxCriterio()){
                                if(cursor.getInt(7)>0 ){
                                    temp.setCalificacion(1);
                                }else {
                                    temp.setCalificacion(2);
                                }

                            }else {
                                //cambiar  a  ic mal
                                temp.setCalificacion(3);
                            }
                        }
                    }else {
                        temp.setCalificacion(0);
                    }
                    break;

            }





            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"CONSULTAR BYID: "+e.toString());
        }finally {
            db.close();
            con.close();
        }
        return temp;
    }


    public List<CriterioDetalleVO> listByIdEvaluacionDetalle(int idEvaluacionDetalle){
        ConexionSQLiteHelper con= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = con.getReadableDatabase();
        List<CriterioDetalleVO> criterioDetalleVOList = new  ArrayList<CriterioDetalleVO>();
        try{
            int idTipoProceso;
            int nFrutos;

            TolEvaVO tolEva;
            Cursor cursor ;
            cursor= db.rawQuery(
                    "SELECT " +
                            " * "+
                            " FROM "+
                            TABLE_EVALUACIONDETALLE
                    ,null);
            int TP = new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso();
            switch (TP) {
                case 1:
                    cursor = db.rawQuery(
                            "SELECT " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_ID + ", " +//0
                                    "CD." + TABLE_CRITERIODETALLE_COL_VALOR + ", " +//1
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO + ", " +//2
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + ", " +//3
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//4
                                    "R." + TABLE_RECEPCION_COL_IDVARIEDAD + ", " +//5
                                    "E." + TABLE_EVALUACION_COL_ID + "," +//6
                                    "E." + TABLE_EVALUACION_COL_ISMATSEC + ", " +//7
                                    "C." + TABLE_CRITERIO_COL_NAME + ", " +//8
                                    "C." + TABLE_CRITERIO_COL_TIPODATO + ", " +//9
                                    "C." + TABLE_CRITERIO_COL_ISPHOTO + ", " +//10
                                    "C." + TABLE_CRITERIO_COL_TOLERANCIAMINIMO + ", " +//11
                                    "C." + TABLE_CRITERIO_COL_TOLERANCIAMAXIMO + ", " +//12
                                    "C." + TABLE_CRITERIO_COL_CODIGO + " " +//13
                                    " FROM " +
                                    TABLE_CRITERIO + " as C, " +
                                    TABLE_CRITERIODETALLE + " as CD, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_EVALUACION + " as E, " +
                                    TABLE_RECEPCION + " as R, " +
                                    TABLE_EVALUACIONDETALLE + " as ED" +
                                    " WHERE " +
                                    "C." + TABLE_CRITERIO_COL_ID + " = " + "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO +
                                    " AND " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + " = " + String.valueOf(idEvaluacionDetalle) +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_ID + "=" + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + "=" + "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_RECEPCION_COL_ID
                            , null);

                    while (cursor.moveToNext()) {
                        CriterioDetalleVO temp = new CriterioDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setValor(cursor.getString(1));
                        temp.setIdCriterio(cursor.getInt(2));
                        temp.setIdEvaluacionDetalle(cursor.getInt(3));

//                            "C."+TABLE_CRITERIO_COL_NAME+", "+//8
//                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+//9
//                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+//10
//                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+//11
//                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+//12
//                            "C."+TABLE_CRITERIO_COL_CODIGO+" "+//13

                        temp.setNameCriterio(cursor.getString(8));
                        temp.setTipoDatoCriterio(cursor.getString(9));
                        temp.setFotografiableCriterio(cursor.getInt(10) > 0);
                        temp.setTolMinCriterio(cursor.getFloat(11));
                        temp.setTolMaxCriterio(cursor.getFloat(12));
                        temp.setCodigo(cursor.getInt(13));

                        nFrutos = cursor.getInt(4);

                        //int idMuestra = new EvaluacionDetalleDAO(ctx).consultarByid(temp.getIdEvaluacionDetalle()).getIdCriterioDetalle();
                        // int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor()) / (1.0f * nFrutos)) * 100.0f);

                        }


                         idTipoProceso = TP;
                        if (temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                            Log.d(TAG, "f1");
                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor())));

                            }

                            tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5), cursor.getInt(6), idTipoProceso, cursor.getInt(7) > 0);

                            if (tolEva != null) {
                                temp.setTolMinCriterio(tolEva.getTolMin());
                                temp.setTolMaxCriterio(tolEva.getTolMax());
                                Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                                Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                            }else {
                                Log.d(TAG,"sintol eva");

                            }
                        }


                        if (temp.getTolMaxCriterio() != temp.getTolMinCriterio() && temp.getTolMinCriterio() > 0) {
                            if (temp.getPorcentaje() < temp.getTolMinCriterio()) {
                                //cambiar icono a good
                                if (cursor.getInt(7) > 0) {
                                    temp.setCalificacion(3);
                                } else {
                                    temp.setCalificacion(1);
                                }
                            } else {
                                if (temp.getPorcentaje() <= temp.getTolMaxCriterio()) {
                                    if (cursor.getInt(7) > 0) {
                                        temp.setCalificacion(1);
                                    } else {
                                        temp.setCalificacion(2);
                                    }

                                } else {
                                    //cambiar  a  ic mal
                                    temp.setCalificacion(3);
                                }
                            }
                        } else {
                            temp.setCalificacion(0);
                        }
                        criterioDetalleVOList.add(temp);
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "numero de criterios:" + criterioDetalleVOList.size());
                    break;

                case 2:
                    cursor = db.rawQuery(
                            "SELECT " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_ID + ", " +//0
                                    "CD." + TABLE_CRITERIODETALLE_COL_VALOR + ", " +//1
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO + ", " +//2
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + ", " +//3
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//4
                                    "R." + TABLE_PRODUCCION_COL_IDVARIEDAD + ", " +//5
                                    "E." + TABLE_EVALUACION_COL_ID + "," +//6
                                    "E." + TABLE_EVALUACION_COL_ISMATSEC + ", " +//7
                                    "C." + TABLE_CRITERIO_COL_NAME + ", " +//8
                                    "C." + TABLE_CRITERIO_COL_TIPODATO + ", " +//9
                                    "C." + TABLE_CRITERIO_COL_ISPHOTO + ", " +//10
                                    "C." + TABLE_CRITERIO_COL_TOLERANCIAMINIMO + ", " +//11
                                    "C." + TABLE_CRITERIO_COL_TOLERANCIAMAXIMO + ", " +//12
                                    "C." + TABLE_CRITERIO_COL_CODIGO + " " +//13
                                    " FROM " +
                                    TABLE_CRITERIO + " as C, " +
                                    TABLE_CRITERIODETALLE + " as CD, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_EVALUACION + " as E, " +
                                    TABLE_PRODUCCION + " as R, " +
                                    TABLE_EVALUACIONDETALLE + " as ED" +
                                    " WHERE " +
                                    "C." + TABLE_CRITERIO_COL_ID + " = " + "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO +
                                    " AND " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + " = " + String.valueOf(idEvaluacionDetalle) +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_ID + "=" + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + "=" + "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_PRODUCCION_COL_ID
                            , null);

                    while (cursor.moveToNext()) {
                        CriterioDetalleVO temp = new CriterioDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setValor(cursor.getString(1));
                        temp.setIdCriterio(cursor.getInt(2));
                        temp.setIdEvaluacionDetalle(cursor.getInt(3));

//                            "C."+TABLE_CRITERIO_COL_NAME+", "+//8
//                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+//9
//                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+//10
//                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+//11
//                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+//12
//                            "C."+TABLE_CRITERIO_COL_CODIGO+" "+//13

                        temp.setNameCriterio(cursor.getString(8));
                        temp.setTipoDatoCriterio(cursor.getString(9));
                        temp.setFotografiableCriterio(cursor.getInt(10) > 0);
                        temp.setTolMinCriterio(cursor.getFloat(11));
                        temp.setTolMaxCriterio(cursor.getFloat(12));
                        temp.setCodigo(cursor.getInt(13));

                        nFrutos = cursor.getInt(4);

                        //int idMuestra = new EvaluacionDetalleDAO(ctx).consultarByid(temp.getIdEvaluacionDetalle()).getIdCriterioDetalle();
                        // int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor()) / (1.0f * nFrutos)) * 100.0f);

                        }
                         idTipoProceso = TP;
                        if (temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                            Log.d(TAG, "f1");
                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor())));

                            }

                            tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5), cursor.getInt(6), idTipoProceso, cursor.getInt(7) > 0);

                            if (tolEva != null) {
                                temp.setTolMinCriterio(tolEva.getTolMin());
                                temp.setTolMaxCriterio(tolEva.getTolMax());
                                Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                                Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                            }else {
                                Log.d(TAG,"sintol eva");

                            }
                        }


                        CalibreVO cal = new CalibreDAO(ctx).consultarTolCalibreByidCriterioDetalle(temp.getId());
                        if(cal != null){
                            Log.d(TAG,"encontro CALIBRE");
                            temp.setTolMinCriterio(cal.getPesoMin().isEmpty() || cal.getPesoMin()==null || cal.getPesoMin().equals("")? 0.0f:Float.valueOf(cal.getPesoMin()));
                            temp.setTolMaxCriterio(cal.getPesoMax().isEmpty() || cal.getPesoMax()==null || cal.getPesoMin().equals("")? 0.0f:Float.valueOf(cal.getPesoMax()));
                            Log.d(TAG,"tol Min"+temp.getTolMinCriterio());
                            Log.d(TAG,"tol Max"+temp.getTolMaxCriterio());
                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(
                                        temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("")
                                                ? "0" :
                                                temp.getValor())));
                            }

                        }else {
                            Log.d(TAG,"NO ENC CALIBRE");
                        }


                        if(temp.getTolMaxCriterio()!=temp.getTolMinCriterio() && temp.getTolMinCriterio()>0){
                            if(temp.getPorcentaje()<temp.getTolMinCriterio()){
                                //cambiar icono a good
                                if(cursor.getInt(7)>0|| cal !=null){
                                    temp.setCalificacion(3);
                                }else {
                                    temp.setCalificacion(1);
                                }
                            }else {
                                if(temp.getPorcentaje()<=temp.getTolMaxCriterio()){
                                    if(cursor.getInt(7)>0 || cal !=null){

                                        temp.setCalificacion(1);
                                    }else {

                                        temp.setCalificacion(2);
                                    }

                                }else {
                                    //cambiar  a  ic mal
                                    temp.setCalificacion(3);
                                }
                            }

                        }else {
                            temp.setCalificacion(0);
                        }
                        criterioDetalleVOList.add(temp);
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "numero de criterios:" + criterioDetalleVOList.size());

                    break;
                case 3:
                    cursor = db.rawQuery(
                            "SELECT " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_ID + ", " +//0
                                    "CD." + TABLE_CRITERIODETALLE_COL_VALOR + ", " +//1
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO + ", " +//2
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + ", " +//3
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//4
                                    "R." + TABLE_DESPACHO_COL_IDCULTIVO + ", " +//5
                                    "E." + TABLE_EVALUACION_COL_ID + "," +//6
                                    "E." + TABLE_EVALUACION_COL_ISMATSEC + ", " +//7
                                    "C." + TABLE_CRITERIO_COL_NAME + ", " +//8
                                    "C." + TABLE_CRITERIO_COL_TIPODATO + ", " +//9
                                    "C." + TABLE_CRITERIO_COL_ISPHOTO + ", " +//10
                                    "C." + TABLE_CRITERIO_COL_TOLERANCIAMINIMO + ", " +//11
                                    "C." + TABLE_CRITERIO_COL_TOLERANCIAMAXIMO + ", " +//12
                                    "C." + TABLE_CRITERIO_COL_CODIGO + " " +//13
                                    " FROM " +
                                    TABLE_CRITERIO + " as C, " +
                                    TABLE_CRITERIODETALLE + " as CD, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_EVALUACION + " as E, " +
                                    TABLE_DESPACHO + " as R, " +
                                    TABLE_EVALUACIONDETALLE + " as ED" +
                                    " WHERE " +
                                    "C." + TABLE_CRITERIO_COL_ID + " = " + "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO +
                                    " AND " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + " = " + String.valueOf(idEvaluacionDetalle) +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_ID + "=" + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + "=" + "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_DESPACHO_COL_ID
                            , null);

                    while (cursor.moveToNext()) {
                        CriterioDetalleVO temp = new CriterioDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setValor(cursor.getString(1));
                        temp.setIdCriterio(cursor.getInt(2));
                        temp.setIdEvaluacionDetalle(cursor.getInt(3));

//                            "C."+TABLE_CRITERIO_COL_NAME+", "+//8
//                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+//9
//                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+//10
//                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+//11
//                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+//12
//                            "C."+TABLE_CRITERIO_COL_CODIGO+" "+//13

                        temp.setNameCriterio(cursor.getString(8));
                        temp.setTipoDatoCriterio(cursor.getString(9));
                        temp.setFotografiableCriterio(cursor.getInt(10) > 0);
                        temp.setTolMinCriterio(cursor.getFloat(11));
                        temp.setTolMaxCriterio(cursor.getFloat(12));
                        temp.setCodigo(cursor.getInt(13));

                        nFrutos = cursor.getInt(4);

                        //int idMuestra = new EvaluacionDetalleDAO(ctx).consultarByid(temp.getIdEvaluacionDetalle()).getIdCriterioDetalle();
                        // int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor()) / (1.0f * nFrutos)) * 100.0f);

                        }
                        idTipoProceso = TP;
                        if (temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                            Log.d(TAG, "f1");
                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor())));

                            }

                            tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5), cursor.getInt(6), idTipoProceso, cursor.getInt(7) > 0);

                            if (tolEva != null) {
                                temp.setTolMinCriterio(tolEva.getTolMin());
                                temp.setTolMaxCriterio(tolEva.getTolMax());
                                Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                                Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                            }else {
                                Log.d(TAG,"sintol eva");

                            }
                        }


                        if (temp.getTolMaxCriterio() != temp.getTolMinCriterio() && temp.getTolMinCriterio() > 0) {
                            if (temp.getPorcentaje() < temp.getTolMinCriterio()) {
                                //cambiar icono a good
                                if (cursor.getInt(7) > 0) {
                                    temp.setCalificacion(3);
                                } else {
                                    temp.setCalificacion(1);
                                }
                            } else {
                                if (temp.getPorcentaje() <= temp.getTolMaxCriterio()) {
                                    if (cursor.getInt(7) > 0) {
                                        temp.setCalificacion(1);
                                    } else {
                                        temp.setCalificacion(2);
                                    }

                                } else {
                                    //cambiar  a  ic mal
                                    temp.setCalificacion(3);
                                }
                            }
                        } else {
                            temp.setCalificacion(0);
                        }
                        criterioDetalleVOList.add(temp);
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "numero de criterios:" + criterioDetalleVOList.size());
                    break;

                case 4:
                    cursor = db.rawQuery(
                            "SELECT " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_ID + ", " +//0
                                    "CD." + TABLE_CRITERIODETALLE_COL_VALOR + ", " +//1
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO + ", " +//2
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + ", " +//3
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//4
                                    "R." + TABLE_DESCARTE_COL_IDVARIEDAD + ", " +//5
                                    "E." + TABLE_EVALUACION_COL_ID + "," +//6
                                    "E." + TABLE_EVALUACION_COL_ISMATSEC + ", " +//7
                                    "C." + TABLE_CRITERIO_COL_NAME + ", " +//8
                                    "C." + TABLE_CRITERIO_COL_TIPODATO + ", " +//9
                                    "C." + TABLE_CRITERIO_COL_ISPHOTO + ", " +//10
                                    "C." + TABLE_CRITERIO_COL_TOLERANCIAMINIMO + ", " +//11
                                    "C." + TABLE_CRITERIO_COL_TOLERANCIAMAXIMO + ", " +//12
                                    "C." + TABLE_CRITERIO_COL_CODIGO + " " +//13
                                    " FROM " +
                                    TABLE_CRITERIO + " as C, " +
                                    TABLE_CRITERIODETALLE + " as CD, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_EVALUACION + " as E, " +
                                    TABLE_DESCARTE + " as R, " +
                                    TABLE_EVALUACIONDETALLE + " as ED" +
                                    " WHERE " +
                                    "C." + TABLE_CRITERIO_COL_ID + " = " + "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO +
                                    " AND " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + " = " + String.valueOf(idEvaluacionDetalle) +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_ID + "=" + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + "=" + "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_DESCARTE_COL_ID
                            , null);

                    while (cursor.moveToNext()) {
                        CriterioDetalleVO temp = new CriterioDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setValor(cursor.getString(1));
                        temp.setIdCriterio(cursor.getInt(2));
                        temp.setIdEvaluacionDetalle(cursor.getInt(3));

//                            "C."+TABLE_CRITERIO_COL_NAME+", "+//8
//                            "C."+TABLE_CRITERIO_COL_TIPODATO+", "+//9
//                            "C."+TABLE_CRITERIO_COL_ISPHOTO+", "+//10
//                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMINIMO+", "+//11
//                            "C."+TABLE_CRITERIO_COL_TOLERANCIAMAXIMO+", "+//12
//                            "C."+TABLE_CRITERIO_COL_CODIGO+" "+//13

                        temp.setNameCriterio(cursor.getString(8));
                        temp.setTipoDatoCriterio(cursor.getString(9));
                        temp.setFotografiableCriterio(cursor.getInt(10) > 0);
                        temp.setTolMinCriterio(cursor.getFloat(11));
                        temp.setTolMaxCriterio(cursor.getFloat(12));
                        temp.setCodigo(cursor.getInt(13));

                        nFrutos = cursor.getInt(4);

                        //int idMuestra = new EvaluacionDetalleDAO(ctx).consultarByid(temp.getIdEvaluacionDetalle()).getIdCriterioDetalle();
                        // int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());

                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor()) / (1.0f * nFrutos)) * 100.0f);

                        }

                        idTipoProceso = TP;
                        if (temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                            Log.d(TAG, "f1");

                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor())));

                            }


                            tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5), cursor.getInt(6), idTipoProceso, cursor.getInt(7) > 0);

                            if (tolEva != null) {
                                temp.setTolMinCriterio(tolEva.getTolMin());
                                temp.setTolMaxCriterio(tolEva.getTolMax());
                                Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                                Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                            }else {
                                Log.d(TAG,"sintol eva");

                            }
                        }


                        if (temp.getTolMaxCriterio() != temp.getTolMinCriterio() && temp.getTolMinCriterio() > 0) {
                            if (temp.getPorcentaje() < temp.getTolMinCriterio()) {
                                //cambiar icono a good
                                if (cursor.getInt(7) > 0) {
                                    temp.setCalificacion(3);
                                } else {
                                    temp.setCalificacion(1);
                                }
                            } else {
                                if (temp.getPorcentaje() <= temp.getTolMaxCriterio()) {
                                    if (cursor.getInt(7) > 0) {
                                        temp.setCalificacion(1);
                                    } else {
                                        temp.setCalificacion(2);
                                    }

                                } else {
                                    //cambiar  a  ic mal
                                    temp.setCalificacion(3);
                                }
                            }
                        } else {
                            temp.setCalificacion(0);
                        }
                        criterioDetalleVOList.add(temp);
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "numero de criterios:" + criterioDetalleVOList.size());
                    break;

            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"error AL LISTAR POR ID EVADE: "+e.toString());
        }finally {
            db.close();
            con.close();
        }
        return criterioDetalleVOList;
    }


    public List<CriterioDetalleVO> listarAll(){
        ConexionSQLiteHelper con= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = con.getReadableDatabase();
        List<CriterioDetalleVO> criterioDetalleVOList = new  ArrayList<CriterioDetalleVO>();
        try{

            int idTipoProceso;
            int nFrutos;

            TolEvaVO tolEva;
            Cursor cursor ;
            cursor= db.rawQuery(
                    "SELECT " +
                            " * "+
                            " FROM "+
                            TABLE_EVALUACIONDETALLE
                    ,null);
            int TP = new LoginDataDAO(ctx).verficarLogueo().getIdTipoProceso();
            switch (TP) {
                case 1:

                    cursor = db.rawQuery(
                            "SELECT " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_ID + ", " +//0
                                    "CD." + TABLE_CRITERIODETALLE_COL_VALOR + ", " +//1
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO + ", " +//2
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + ", " +//3
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//4
                                    "R." + TABLE_RECEPCION_COL_IDVARIEDAD + ", " +//5
                                    "E." + TABLE_EVALUACION_COL_ID + "," +//6
                                    "E." + TABLE_EVALUACION_COL_ISMATSEC +//7
                                    " FROM " +
                                    TABLE_CRITERIODETALLE + " as CD, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_EVALUACION + " as E, " +
                                    TABLE_RECEPCION + " as R, " +
                                    TABLE_EVALUACIONDETALLE + " as ED" +
                                    " WHERE " +
                                    "M." + TABLE_MUESTRA_COL_ID + "=" + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + "=" + "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_RECEPCION_COL_ID
                            , null);

                    while (cursor.moveToNext()) {
                        CriterioDetalleVO temp = new CriterioDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setValor(cursor.getString(1));
                        temp.setIdCriterio(cursor.getInt(2));
                        temp.setIdEvaluacionDetalle(cursor.getInt(3));
                        CriterioVO cri = new CriterioDAO(ctx).consultarByid(temp.getIdCriterio());
                        temp.setNameCriterio(cri.getName());
                        temp.setTipoDatoCriterio(cri.getTipoDato());
                        temp.setFotografiableCriterio(cri.isFotografiable());
                        temp.setTolMinCriterio(cri.getTolMin());
                        temp.setTolMaxCriterio(cri.getTolMax());
                        temp.setCodigo(cri.getCodigo());

                        nFrutos = cursor.getInt(4);

                        //int idMuestra = new EvaluacionDetalleDAO(ctx).consultarByid(temp.getIdEvaluacionDetalle()).getIdCriterioDetalle();

                        // int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());

                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor()) / (1.0f * nFrutos)) * 100.0f);
                        }


                        idTipoProceso = TP;
                        if (temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                            Log.d(TAG, "f1");

                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor())));
                            }


                            tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5), cursor.getInt(6), idTipoProceso, cursor.getInt(7) > 0);

                            if (tolEva != null) {
                                temp.setTolMinCriterio(tolEva.getTolMin());
                                temp.setTolMaxCriterio(tolEva.getTolMax());
                                Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                                Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                            }else {
                                Log.d(TAG,"sintol eva");

                            }
                        }
                        String val = cursor.getString(1);

                        temp.setValor(val == null || val.isEmpty() || val.equals("") ? "0" : val);


                        if (temp.getTolMaxCriterio() != temp.getTolMinCriterio() && temp.getTolMinCriterio() > 0) {
                            if (temp.getPorcentaje() < temp.getTolMinCriterio()) {
                                //cambiar icono a good
                                if (cursor.getInt(7) > 0) {
                                    temp.setCalificacion(3);
                                } else {
                                    temp.setCalificacion(1);
                                }
                            } else {
                                if (temp.getPorcentaje() <= temp.getTolMaxCriterio()) {
                                    if (cursor.getInt(7) > 0) {
                                        temp.setCalificacion(1);
                                    } else {
                                        temp.setCalificacion(2);
                                    }

                                } else {
                                    //cambiar  a  ic mal
                                    temp.setCalificacion(3);
                                }
                            }
                        } else {
                            temp.setCalificacion(0);
                        }
                        criterioDetalleVOList.add(temp);
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "numero de criterios:" + criterioDetalleVOList.size());

                    break;

                case 2:
                    cursor = db.rawQuery(
                            "SELECT " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_ID + ", " +//0
                                    "CD." + TABLE_CRITERIODETALLE_COL_VALOR + ", " +//1
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO + ", " +//2
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + ", " +//3
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//4
                                    "R." + TABLE_PRODUCCION_COL_IDVARIEDAD + ", " +//5
                                    "E." + TABLE_EVALUACION_COL_ID + "," +//6
                                    "E." + TABLE_EVALUACION_COL_ISMATSEC +//7
                                    " FROM " +
                                    TABLE_CRITERIODETALLE + " as CD, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_EVALUACION + " as E, " +
                                    TABLE_PRODUCCION + " as R, " +
                                    TABLE_EVALUACIONDETALLE + " as ED" +
                                    " WHERE " +
                                    "M." + TABLE_MUESTRA_COL_ID + "=" + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + "=" + "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_PRODUCCION_COL_ID
                            , null);

                    while (cursor.moveToNext()) {
                        CriterioDetalleVO temp = new CriterioDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setValor(cursor.getString(1));
                        temp.setIdCriterio(cursor.getInt(2));
                        temp.setIdEvaluacionDetalle(cursor.getInt(3));
                        CriterioVO cri = new CriterioDAO(ctx).consultarByid(temp.getIdCriterio());
                        temp.setNameCriterio(cri.getName());
                        temp.setTipoDatoCriterio(cri.getTipoDato());
                        temp.setFotografiableCriterio(cri.isFotografiable());
                        temp.setTolMinCriterio(cri.getTolMin());
                        temp.setTolMaxCriterio(cri.getTolMax());
                        temp.setCodigo(cri.getCodigo());

                        nFrutos = cursor.getInt(4);

                        //int idMuestra = new EvaluacionDetalleDAO(ctx).consultarByid(temp.getIdEvaluacionDetalle()).getIdCriterioDetalle();

                        // int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor()) / (1.0f * nFrutos)) * 100.0f);

                        }

                        idTipoProceso = TP;
                        if (temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                            Log.d(TAG, "f1");
                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor())));

                            }


                            tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5), cursor.getInt(6), idTipoProceso, cursor.getInt(7) > 0);

                            if (tolEva != null) {
                                temp.setTolMinCriterio(tolEva.getTolMin());
                                temp.setTolMaxCriterio(tolEva.getTolMax());
                                Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                                Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                            }else {
                                Log.d(TAG,"sintol eva");

                            }
                        }
                        String val = cursor.getString(1);

                        temp.setValor(val == null || val.isEmpty() || val.equals("") ? "0" : val);


                        CalibreVO cal = new CalibreDAO(ctx).consultarTolCalibreByidCriterioDetalle(temp.getId());
                        if(cal != null ){
                            Log.d(TAG,"encontro CALIBRE");
                            temp.setTolMinCriterio(cal.getPesoMin().isEmpty() || cal.getPesoMin()==null || cal.getPesoMin().equals("")? 0.0f:Float.valueOf(cal.getPesoMin()));
                            temp.setTolMaxCriterio(cal.getPesoMax().isEmpty() || cal.getPesoMax()==null || cal.getPesoMin().equals("")? 0.0f:Float.valueOf(cal.getPesoMax()));
                            Log.d(TAG,"tol Min"+temp.getTolMinCriterio());
                            Log.d(TAG,"tol Max"+temp.getTolMaxCriterio());

                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(
                                        temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("")
                                                ? "0" :
                                                temp.getValor())));
                            }




                        }else {
                            Log.d(TAG,"NO ENC CALIBRE");
                        }


                        if(temp.getTolMaxCriterio()!=temp.getTolMinCriterio() && temp.getTolMinCriterio()>0){
                            if(temp.getPorcentaje()<temp.getTolMinCriterio()){
                                //cambiar icono a good
                                if(cursor.getInt(7)>0|| cal !=null){
                                    temp.setCalificacion(3);
                                }else {
                                    temp.setCalificacion(1);
                                }
                            }else {
                                if(temp.getPorcentaje()<=temp.getTolMaxCriterio()){
                                    if(cursor.getInt(7)>0|| cal !=null){
                                        temp.setCalificacion(1);
                                    }else {

                                        temp.setCalificacion(2);
                                    }

                                }else {
                                    //cambiar  a  ic mal
                                    temp.setCalificacion(3);
                                }
                            }

                        }else {
                            temp.setCalificacion(0);
                        }
                        criterioDetalleVOList.add(temp);
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "numero de criterios:" + criterioDetalleVOList.size());


                    break;
                case 3:

                    cursor = db.rawQuery(
                            "SELECT " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_ID + ", " +//0
                                    "CD." + TABLE_CRITERIODETALLE_COL_VALOR + ", " +//1
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO + ", " +//2
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + ", " +//3
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//4
                                    "R." + TABLE_DESPACHO_COL_IDCULTIVO + ", " +//5
                                    "E." + TABLE_EVALUACION_COL_ID + "," +//6
                                    "E." + TABLE_EVALUACION_COL_ISMATSEC +//7
                                    " FROM " +
                                    TABLE_CRITERIODETALLE + " as CD, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_EVALUACION + " as E, " +
                                    TABLE_DESPACHO + " as R, " +
                                    TABLE_EVALUACIONDETALLE + " as ED" +
                                    " WHERE " +
                                    "M." + TABLE_MUESTRA_COL_ID + "=" + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + "=" + "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_DESPACHO_COL_ID
                            , null);

                    while (cursor.moveToNext()) {
                        CriterioDetalleVO temp = new CriterioDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setValor(cursor.getString(1));
                        temp.setIdCriterio(cursor.getInt(2));
                        temp.setIdEvaluacionDetalle(cursor.getInt(3));
                        CriterioVO cri = new CriterioDAO(ctx).consultarByid(temp.getIdCriterio());
                        temp.setNameCriterio(cri.getName());
                        temp.setTipoDatoCriterio(cri.getTipoDato());
                        temp.setFotografiableCriterio(cri.isFotografiable());
                        temp.setTolMinCriterio(cri.getTolMin());
                        temp.setTolMaxCriterio(cri.getTolMax());
                        temp.setCodigo(cri.getCodigo());

                        nFrutos = cursor.getInt(4);

                        //int idMuestra = new EvaluacionDetalleDAO(ctx).consultarByid(temp.getIdEvaluacionDetalle()).getIdCriterioDetalle();

                        // int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                           temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor()) / (1.0f * nFrutos)) * 100.0f);

                        }

                        idTipoProceso = TP;
                        if (temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                            Log.d(TAG, "f1");
                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                 temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor())));

                            }


                            tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5), cursor.getInt(6), idTipoProceso, cursor.getInt(7) > 0);

                            if (tolEva != null) {
                                temp.setTolMinCriterio(tolEva.getTolMin());
                                temp.setTolMaxCriterio(tolEva.getTolMax());
                                Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                                Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                            }else {
                                Log.d(TAG,"sintol eva");

                            }
                        }
                        String val = cursor.getString(1);

                        temp.setValor(val == null || val.isEmpty() || val.equals("") ? "0" : val);


                        if (temp.getTolMaxCriterio() != temp.getTolMinCriterio() && temp.getTolMinCriterio() > 0) {
                            if (temp.getPorcentaje() < temp.getTolMinCriterio()) {
                                //cambiar icono a good
                                if (cursor.getInt(7) > 0) {
                                    temp.setCalificacion(3);
                                } else {
                                    temp.setCalificacion(1);
                                }
                            } else {
                                if (temp.getPorcentaje() <= temp.getTolMaxCriterio()) {
                                    if (cursor.getInt(7) > 0) {
                                        temp.setCalificacion(1);
                                    } else {
                                        temp.setCalificacion(2);
                                    }

                                } else {
                                    //cambiar  a  ic mal
                                    temp.setCalificacion(3);
                                }
                            }
                        } else {
                            temp.setCalificacion(0);
                        }
                        criterioDetalleVOList.add(temp);
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "numero de criterios:" + criterioDetalleVOList.size());

                    break;
                case 4:

                    cursor = db.rawQuery(
                            "SELECT " +
                                    "CD." + TABLE_CRITERIODETALLE_COL_ID + ", " +//0
                                    "CD." + TABLE_CRITERIODETALLE_COL_VALOR + ", " +//1
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDCRITERIO + ", " +//2
                                    "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE + ", " +//3
                                    "M." + TABLE_MUESTRA_COL_CANTIDAD + ", " +//4
                                    "R." + TABLE_DESCARTE_COL_IDVARIEDAD + ", " +//5
                                    "E." + TABLE_EVALUACION_COL_ID + "," +//6
                                    "E." + TABLE_EVALUACION_COL_ISMATSEC +//7
                                    " FROM " +
                                    TABLE_CRITERIODETALLE + " as CD, " +
                                    TABLE_MUESTRA + " as M, " +
                                    TABLE_EVALUACION + " as E, " +
                                    TABLE_DESCARTE + " as R, " +
                                    TABLE_EVALUACIONDETALLE + " as ED" +
                                    " WHERE " +
                                    "M." + TABLE_MUESTRA_COL_ID + "=" + "ED." + TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_ID + "=" + "CD." + TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +
                                    " AND " +
                                    "ED." + TABLE_EVALUACIONDETALLE_COL_IDEVALUACION + " = " + "E." + TABLE_EVALUACION_COL_ID +
                                    " AND " +
                                    "M." + TABLE_MUESTRA_COL_IDPROCESO + " = " + "R." + TABLE_DESCARTE_COL_ID
                            , null);

                    while (cursor.moveToNext()) {
                        CriterioDetalleVO temp = new CriterioDetalleVO();
                        temp.setId(cursor.getInt(0));
                        temp.setValor(cursor.getString(1));
                        temp.setIdCriterio(cursor.getInt(2));
                        temp.setIdEvaluacionDetalle(cursor.getInt(3));
                        CriterioVO cri = new CriterioDAO(ctx).consultarByid(temp.getIdCriterio());
                        temp.setNameCriterio(cri.getName());
                        temp.setTipoDatoCriterio(cri.getTipoDato());
                        temp.setFotografiableCriterio(cri.isFotografiable());
                        temp.setTolMinCriterio(cri.getTolMin());
                        temp.setTolMaxCriterio(cri.getTolMax());
                        temp.setCodigo(cri.getCodigo());

                        nFrutos = cursor.getInt(4);

                        //int idMuestra = new EvaluacionDetalleDAO(ctx).consultarByid(temp.getIdEvaluacionDetalle()).getIdCriterioDetalle();

                        // int nFrutos = Integer.valueOf(new MuestraDAO(ctx).consultarById(idMuestra).getCantidad());
                        if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                            temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor()) / (1.0f * nFrutos)) * 100.0f);

                        }

                        idTipoProceso = TP;
                        if (temp.getTipoDatoCriterio().equals("PORCENTAJE")) {
                            Log.d(TAG, "f1");
                            if(!temp.getTipoDatoCriterio().equals("TEXTO")){
                                temp.setPorcentaje((1.0f * Float.valueOf(temp.getValor() == null || temp.getValor().isEmpty() || temp.getValor().equals("") ? "0" : temp.getValor())));
                            }


                            tolEva = new EvaluacionDetalleDAO(ctx).consTolEvaByIdVariedadIdTipoPIdEva(cursor.getInt(5), cursor.getInt(6), idTipoProceso, cursor.getInt(7) > 0);

                            if (tolEva != null) {
                                temp.setTolMinCriterio(tolEva.getTolMin());
                                temp.setTolMaxCriterio(tolEva.getTolMax());
                                Log.d(TAG,"tolEva Min"+temp.getTolMinCriterio());
                                Log.d(TAG,"tolEva Max"+temp.getTolMaxCriterio());

                            }else {
                                Log.d(TAG,"sintol eva");

                            }
                        }
                        String val = cursor.getString(1);

                        temp.setValor(val == null || val.isEmpty() || val.equals("") ? "0" : val);


                        if (temp.getTolMaxCriterio() != temp.getTolMinCriterio() && temp.getTolMinCriterio() > 0) {
                            if (temp.getPorcentaje() < temp.getTolMinCriterio()) {
                                //cambiar icono a good
                                if (cursor.getInt(7) > 0) {
                                    temp.setCalificacion(3);
                                } else {
                                    temp.setCalificacion(1);
                                }
                            } else {
                                if (temp.getPorcentaje() <= temp.getTolMaxCriterio()) {
                                    if (cursor.getInt(7) > 0) {
                                        temp.setCalificacion(1);
                                    } else {
                                        temp.setCalificacion(2);
                                    }

                                } else {
                                    //cambiar  a  ic mal
                                    temp.setCalificacion(3);
                                }
                            }
                        } else {
                            temp.setCalificacion(0);
                        }
                        criterioDetalleVOList.add(temp);
                        // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "numero de criterios:" + criterioDetalleVOList.size());

                    break;

            }

            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(TAG,"error AL LISTAR POR ID EVADE: "+e.toString());
        }finally {
            db.close();
            con.close();
        }
        return criterioDetalleVOList;
    }


    public boolean clearTableUpload(int id){

        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros = {
                String.valueOf(id)
        };

        int res = db.delete(TABLE_CRITERIODETALLE,TABLE_CRITERIODETALLE_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean deleteById(int id){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        int res = db.delete(TABLE_CRITERIODETALLE,TABLE_CRITERIODETALLE_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean deleteByIdEvaluacionDetalle(int idEvaluacionDetalle){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros =
                {
                        String.valueOf(idEvaluacionDetalle)
                };
        int res = db.delete(TABLE_CRITERIODETALLE,TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
