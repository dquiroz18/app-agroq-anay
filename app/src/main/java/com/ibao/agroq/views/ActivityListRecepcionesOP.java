package com.ibao.agroq.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibao.agroq.R;
import com.ibao.agroq.app.AppController;
import com.ibao.agroq.models.dao.CultivoDAO;
import com.ibao.agroq.models.dao.EmpresaDAO;
import com.ibao.agroq.models.dao.FundoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.PlantaDAO;
import com.ibao.agroq.models.dao.VariedadDAO;
import com.ibao.agroq.models.dao.ZonaDAO;
import com.ibao.agroq.models.vo.entitiesDB.CultivoVO;
import com.ibao.agroq.models.vo.entitiesDB.EmpresaVO;
import com.ibao.agroq.models.vo.entitiesDB.FundoVO;
import com.ibao.agroq.models.vo.entitiesDB.ZonaVO;
import com.ibao.agroq.models.vo.entitiesInternal.RecepcionVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ibao.agroq.utilities.Utilities.URL_DOWNLOAD_RECEPCION_OP;


public class ActivityListRecepcionesOP extends AppCompatActivity {

    static ListView lViewRecepcionOP;

    static List<RecepcionVO> recepcionVOList;
    static String TAG = ActivityListRecepcionesOP.class.getSimpleName();
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recepciones_op);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recepcionVOList = new ArrayList<>();
        lViewRecepcionOP = (ListView) findViewById(R.id.lViewRecepcionOP);


        adapter = new BaseAdapter(){

            @Override
            public int getCount() {
                return recepcionVOList.size();
            }

            @Override
            public RecepcionVO getItem(int position) {
                return recepcionVOList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return recepcionVOList.get(position).getId();
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = convertView;
                final LayoutInflater inflater;
                inflater = LayoutInflater.from(getBaseContext());
                v = inflater.inflate(R.layout.item_view_recepcion_op,null);

                TextView tViewFechaRecepcion = v.findViewById(R.id.tViewFechaRecepcion);
                TextView tViewNOrdenProceso = v.findViewById(R.id.tViewNOrdenProceso);
                TextView tViewPlanta = v.findViewById(R.id.tViewNFrutos);
                TextView tViewProductor = v.findViewById(R.id.tViewProductor);
                TextView tViewVariedad = v.findViewById(R.id.tViewCultivo);
                TextView tViewFundo = v.findViewById(R.id.tViewFundo);

                tViewFechaRecepcion.setText(getItem(position).getFechaHora());
                tViewNOrdenProceso.setText(getItem(position).getnOrdenProceso());
                tViewPlanta.setText(getItem(position).getNamePlanta());
                tViewProductor.setText(getItem(position).getNameEmpresa());
                tViewFundo.setText(getItem(position).getNameFundo());
                tViewVariedad.setText(getItem(position).getNameCultivo()+" - "+getItem(position).getNameVariedad());


                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent returnIntent = new Intent();
                            returnIntent.putExtra(ActivityBasicsProduccion.RESPONSE_NORDENPROCESO,getItem(position).getnOrdenProceso());
                            returnIntent.putExtra(ActivityBasicsProduccion.RESPONSE_IDFUNDO,getItem(position).getIdFundo());
                            returnIntent.putExtra(ActivityBasicsProduccion.RESPONSE_IDVARIEDAD,getItem(position).getIdVariedad());
                            returnIntent.putExtra(ActivityBasicsProduccion.RESPONSE_IDRECEPCION,getItem(position).getId());

                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                });


                return v;
            }

        };

        lViewRecepcionOP.setAdapter(adapter);

        BuscarRecepcionOP();
        //r.run();

    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(!recepcionVOList.isEmpty()){
                BuscarRecepcionOP();
            }else {
                try {
                    Thread.sleep(500);
                    run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    finish();
                    Toast.makeText(getBaseContext(),"Error de espera",Toast.LENGTH_LONG);
                }
            }
        }
    };

    ProgressDialog progress;
    private void BuscarRecepcionOP() {
                 progress = new ProgressDialog(ActivityListRecepcionesOP.this);
                 progress.setCancelable(false);
                 progress.setMessage("Intentando descargar Recepciones");
                 progress.show();
            StringRequest sr = new StringRequest(Request.Method.POST,
                    URL_DOWNLOAD_RECEPCION_OP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                                        progress.dismiss();
                            try {
                                JSONArray main = new JSONArray(response);
                                if(main.length()>0){
                                    recepcionVOList.clear();
                                }

                                final int IDCULTIVO = new LoginDataDAO(getBaseContext()).verficarLogueo().getIdCultivo();
                                final int IDPLANTA = new LoginDataDAO(getBaseContext()).verficarLogueo().getIdPlanta();

                                for(int i=0;i<main.length();i++){
                                    JSONObject data = new JSONObject(main.get(i).toString());
                                    RecepcionVO temp = new RecepcionVO();
                                    if( data.getInt("idPlanta") == IDPLANTA
                                           &&
                                        data.getInt("idCultivo") == IDCULTIVO
                                    ){

                                        temp.setId(data.getInt("id"));
                                        temp.setnOrdenProceso(data.getString("ordenProceso"));
                                        temp.setFechaHora(data.getString("fechaRecepcion"));
                                        temp.setIdPlanta(data.getInt("idPlanta"));
                                        temp.setNamePlanta(new PlantaDAO(getBaseContext()).consultarByid(temp.getIdPlanta()).getName());
                                        temp.setIdFundo(data.getInt("idFundo"));
                                        temp.setIdCultivo(data.getInt("idCultivo"));
                                        temp.setIdVariedad(data.getInt("idVariedad"));
                                        if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                                            //    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                                            //obteniedo datos d e fundo
                                            FundoDAO fundoDAO = new FundoDAO(getBaseContext());
                                            FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                                            String nameFundo = f.getName();
                                            temp.setNameFundo(nameFundo);
                                            //obteniedno datos d e empresa
                                            EmpresaVO empresaVO = new EmpresaDAO(getBaseContext()).consultarEmpresaByidFundo(temp.getIdFundo());
                                            temp.setIdEmpresa(empresaVO.getId());
                                            temp.setNameEmpresa(empresaVO.getName());

                                            ZonaVO zonaVO = new ZonaDAO(getBaseContext()).consultarByid(empresaVO.getIdZona());
                                            temp.setIdZona(zonaVO.getId());
                                            temp.setNameZona(zonaVO.getName());

                                        }

                                        if(temp.getIdVariedad()>0){
                                            //obteniendo datos  de cultivo
                                            CultivoDAO cultivoDAO = new CultivoDAO(getBaseContext());
                                            CultivoVO cultivoVO = cultivoDAO.consultarByIdVariedad(temp.getIdVariedad());
                                            temp.setNameCultivo(cultivoVO.getName());
                                            temp.setIdCultivo(cultivoVO.getId());
                                            //obteiedno datos de  variedad
                                            VariedadDAO variedadDAO = new VariedadDAO(getBaseContext());
                                            temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                                        }

                                        recepcionVOList.add(temp);
                                    }
                                }

                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                Log.d("CATEGORIADOWN ",e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.dismiss();
                            Toast.makeText(getBaseContext(),TAG+" Error conectando con el servidor",Toast.LENGTH_LONG).show();

                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
/*
                UsuarioVO temp = new LoginHelper(getBaseContext()).verificarLogueo();
                params.put("id",String.valueOf(temp.getId()));
                params.put("idInspector",String.valueOf(temp.getCodigo()));
*/
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<String, String>();
                    headers.put("Content-Type","application/x-www-form-urlencoded");
                    return headers;
                }
            };

            AppController.getInstance().addToRequestQueue(sr);
        }



}
