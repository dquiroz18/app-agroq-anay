package com.ibao.agroq.views;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ibao.agroq.R;
import com.ibao.agroq.models.dao.CalibreDAO;
import com.ibao.agroq.models.dao.CategoriaDAO;
import com.ibao.agroq.models.dao.CultivoDAO;
import com.ibao.agroq.models.dao.DestinoDAO;
import com.ibao.agroq.models.dao.EmpresaDAO;
import com.ibao.agroq.models.dao.EnvaseDAO;
import com.ibao.agroq.models.dao.FundoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.ProduccionDAO;
import com.ibao.agroq.models.dao.TipoCalibreDAO;
import com.ibao.agroq.models.dao.VariedadDAO;
import com.ibao.agroq.models.dao.ZonaDAO;
import com.ibao.agroq.models.vo.entitiesDB.CalibreVO;
import com.ibao.agroq.models.vo.entitiesDB.CategoriaVO;
import com.ibao.agroq.models.vo.entitiesDB.CultivoVO;
import com.ibao.agroq.models.vo.entitiesDB.DestinoVO;
import com.ibao.agroq.models.vo.entitiesDB.EmpresaVO;
import com.ibao.agroq.models.vo.entitiesDB.EnvaseVO;
import com.ibao.agroq.models.vo.entitiesDB.FundoVO;
import com.ibao.agroq.models.vo.entitiesDB.TipoCalibreVO;
import com.ibao.agroq.models.vo.entitiesDB.VariedadVO;
import com.ibao.agroq.models.vo.entitiesDB.ZonaVO;
import com.ibao.agroq.models.vo.entitiesInternal.ProduccionVO;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActivityBasicsProduccion extends AppCompatActivity {

    private static List<CategoriaVO> listCategoria;
    private static List<String> listNombreCategoria;

    private static List<DestinoVO> listDestino;
    private static List<String> listNombreDestino;

    private static List<TipoCalibreVO> listTipoCalibre;
    private static List<String> listNombreTipoCalibre;

    private static List<CalibreVO> listCalibre;
    private static List<String> listNombreCalibre;

    private static List<ZonaVO> listZonas;
    private static List<String> listNombreZonas;

    private static List<EmpresaVO> listEmpresas;
    private static List<String> listNombreEmpresas;

    private static List<FundoVO> listFundos;
    private static List<String> listNombreFundos;

    private static List<CultivoVO> listCultivos;
    private static List<String> listNombreCultivos;

    private static List<VariedadVO> listVariedades;
    private static List<String> listNombreVariedades;

    private static List<EnvaseVO> listEnvases;
    private static List<String> listNombreEnvases;

    private String TAG = ActivityBasicsProduccion.class.getSimpleName();


    private ArrayAdapter<CharSequence> adaptadorZonas ;
    private ArrayAdapter<CharSequence> adaptadorCultivos;


    private static Spinner spnCategoria;
    private static Spinner spnDestino;
    private static Spinner spnTipoCalibre;
    private static Spinner spnCalibre;




    private static Spinner spnZona;
    private static Spinner spnEmpresa;
    private static Spinner spnFundo;
    private static Spinner spnVariedad;
    private static Spinner spnCultivo;
    private static Spinner spnEnvase;
    private static EditText eTextNOrdenProceso;
    private static EditText eTextKilos;
    private static EditText eTextUnidades;
    private static TextView tViewFechaHora;

    private static ProduccionVO PRODUCCION;

    private static Bundle recibidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basics_produccion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setupActionBar();
        tViewFechaHora = (TextView) findViewById(R.id.tViewFechaRecepcion);

        //odificadors  de produccion
        spnCategoria = (Spinner) findViewById(R.id.spnCategoria);//
        spnDestino = (Spinner) findViewById(R.id.spnDestino);//
        spnTipoCalibre = (Spinner) findViewById(R.id.spnTipoCalibre);//
        spnCalibre = (Spinner) findViewById(R.id.spnCalibre);


        spnZona = (Spinner) findViewById(R.id.spnZona);
        spnEmpresa = (Spinner) findViewById(R.id.spnProductor);
        spnFundo = (Spinner) findViewById(R.id.spnFundo);
        spnCultivo = (Spinner) findViewById(R.id.spnCultivo);
        spnVariedad = (Spinner) findViewById(R.id.spnVariedad);
        spnEnvase = (Spinner) findViewById(R.id.spnTipoEnvase);
        eTextNOrdenProceso = (EditText) findViewById(R.id.eTextNOrdenProceso);
         eTextKilos = (EditText) findViewById(R.id.eTextKilos);
        eTextUnidades = (EditText) findViewById(R.id.eTextNUnidades);

        //cargar los nombre a ram
        cargarEnvases();
        cargarZonas();
        cargarCultivos();

        cargarCategoria();
        cargarDestino();
        cargarTipoCalibre();

        //inicializando adaptadpres de spn iniciales
        adaptadorZonas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreZonas);
        adaptadorCultivos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreCultivos);
        ArrayAdapter<CharSequence> adaptadorEnvases = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreEnvases);

        ArrayAdapter<CharSequence> adaptadorCategorias = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreCategoria);
        ArrayAdapter<CharSequence> adaptadorDestino = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreDestino);
        ArrayAdapter<CharSequence> adaptadorTipoCalibre = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreTipoCalibre);


        //seteando esos adaptadores
        spnZona.setAdapter(adaptadorZonas);
        spnCultivo.setAdapter(adaptadorCultivos);
        spnEnvase.setAdapter(adaptadorEnvases);

        spnCategoria.setAdapter(adaptadorCategorias);
        spnDestino.setAdapter(adaptadorDestino);
        spnTipoCalibre.setAdapter(adaptadorTipoCalibre);

        //reciviendo parametros de la actividad anterior
        recibidos = getIntent().getExtras();

        if(recibidos!=null){//si se recibe datos
            PRODUCCION = new ProduccionDAO(getBaseContext()).buscarById((long)recibidos.getInt(ActivityProceso.REQUEST_ID));
            Gson gson = new Gson();
            String jsonproduccion = gson.toJson(PRODUCCION);
            Log.d(TAG,jsonproduccion);


            Log.d(TAG, "SEND IS NOT NULL");

            tViewFechaHora.setText(PRODUCCION.getFechaHora());

            //si no es la primera ves q se accede a editar
            if(recibidos.getInt("isFirst")==0){//si no es la primera vez q ingresan

                Log.d(TAG,"produccion = "+String.valueOf(PRODUCCION.getIdZona()+" "+ PRODUCCION.getIdEmpresa()+" "+ PRODUCCION.getIdFundo()));

                eTextNOrdenProceso.setText(PRODUCCION.getnOrdenProceso());
                eTextKilos.setText(PRODUCCION.getKilos());
                eTextUnidades.setText(PRODUCCION.getUnidades());
                spnCultivo.setEnabled(false);

                for(int w=0;w<listZonas.size();w++){
                    if(listZonas.get(w).getId()== PRODUCCION.getIdZona()){
                        spnZona.setSelection(w/*+1*/);
                        Log.d(TAG,"Zona ENCONTRADA POS = "+(w/*+1*/)+" "+spnZona.getSelectedItem().toString());
                        spnZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int q, long l) {
                                //  if(q != 0){
                                cargarEmpresas();
                                spnEmpresa.setEnabled(true);
                                ArrayAdapter<CharSequence> adaptadorEmpresas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreEmpresas);
                                spnEmpresa.setAdapter(adaptadorEmpresas);
                                for(int i=0;i<listEmpresas.size();i++){
                                    if(listEmpresas.get(i).getId()== PRODUCCION.getIdEmpresa()){
                                        spnEmpresa.setSelection(i/*+1*/);
                                        Log.d(TAG,"EMPRESA ENCONTRADA POS = "+i/*+1*/);
                                        spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                //if(i != 0){
                                                cargarFundos();
                                                spnFundo.setEnabled(true);
                                                ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreFundos);
                                                spnFundo.setAdapter(adaptadorFundos);
                                                for(int j = 0 ; j<listFundos.size();j++){
                                                    if(listFundos.get(j).getId()== PRODUCCION.getIdFundo()){
                                                        spnFundo.setSelection(j/*+1*/);
                                                        spnFundo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                //if(position !=0){
                                                                /*
                                                                cargarContactos();
                                                                spnContacto.setEnabled(true);
                                                                ArrayAdapter<CharSequence> adaptadorContactos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreContactos);
                                                                spnContacto.setAdapter(adaptadorContactos);
                                                                for(int k =0 ; k< listContactos.size();k++){
                                                                    if(listContactos.get(k).getId()==idContacto){
                                                                        spnContacto.setSelection(k+1);
                                                                    }
                                                                }
                                                                */
                                                                        /*}else{
                                                                            spnContacto.setEnabled(false);
                                                                            spnContacto.setSelection(0);
                                                                        }*/
                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> parent) {

                                                            }
                                                        });


                                                    }
                                                }

                                /*                    }else{
                                                        spnFundo.setEnabled(false);
                                                        spnFundo.setSelection(0);
                                                    }
                                  */              }
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                                return;
                                            }
                                        });
                                        break;
                                    }
                                }
                                /*}else{
                                    spnEmpresa.setEnabled(false);
                                    spnEmpresa.setSelection(0);
                                    spnFundo.setEnabled(false);
                                    spnFundo.setSelection(0);
                                }*/
                            }
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                        break;
                    }else {
                        Log.d(TAG,"Zona no ENCONTRADA POS = "+(w/*+1*/)+" "+spnZona.getSelectedItem().toString());
                    }
                }

                for(int i=0;i<listCultivos.size();i++) {
                    if (listCultivos.get(i).getId() == PRODUCCION.getIdCultivo()) {
                        Log.d(TAG, "CULTIVO ENCONTRADA POS = " + i /*+ 1*/);
                        spnCultivo.setSelection(i /*+ 1*/);
                        spnCultivo.setEnabled(false);
                        spnCultivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                // if(i != 0){
                                cargarVariedades();
                                spnVariedad.setEnabled(true);
                                ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreVariedades);
                                spnVariedad.setAdapter(adaptadorFundos);
                                for(int j = 0 ; j<listVariedades.size();j++){
                                    if(listVariedades.get(j).getId()== PRODUCCION.getIdVariedad()){
                                        spnVariedad.setSelection(j/*+1*/);
                                    }
                                }/*
                                }else{
                                    spnVariedad.setEnabled(false);
                                    spnVariedad.setSelection(0);
                                }*/
                            }
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });

                    }
                }

                for(int i=0;i<listEnvases.size();i++) {
                    if (listEnvases.get(i).getId() == PRODUCCION.getIdEnvase()) {
                        Log.d(TAG, "envase ENCONTRADo POS = " + i /*+ 1*/);
                        spnEnvase.setSelection(i /*+ 1*/);

                    }
                }


                for(int i=0;i<listDestino.size();i++) {
                    if (listDestino.get(i).getId() == PRODUCCION.getIdDestino()) {
                        Log.d(TAG, "destino ENCONTRADo POS = " + i /*+ 1*/);
                        spnDestino.setSelection(i /*+ 1*/);

                    }
                }

                for(int i=0;i<listCategoria.size();i++) {
                    if (listCategoria.get(i).getId() == PRODUCCION.getIdCategoria()) {
                        Log.d(TAG, "categoria ENCONTRADo POS = " + i /*+ 1*/);
                        spnCategoria.setSelection(i /*+ 1*/);

                    }
                }


                for(int i=0;i<listTipoCalibre.size();i++) {
                    if (listTipoCalibre.get(i).getId() == PRODUCCION.getIdTipoCalibre()) {
                        Log.d(TAG, "TIPO CALIBRE ENCONTRADO POS = " + i /*+ 1*/);
                        spnTipoCalibre.setSelection(i /*+ 1*/);
                        spnTipoCalibre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                // if(i != 0){
                                cargarCalibre();
                                spnCalibre.setEnabled(true);
                                ArrayAdapter<CharSequence> adaptadorCalibre = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreCalibre);
                                spnCalibre.setAdapter(adaptadorCalibre);
                                for(int j = 0 ; j<listCalibre.size();j++){
                                    if(listCalibre.get(j).getId()== PRODUCCION.getIdCalibre()){
                                        spnCalibre.setSelection(j/*+1*/);
                                    }
                                }/*
                                }else{
                                    spnVariedad.setEnabled(false);
                                    spnVariedad.setSelection(0);
                                }*/
                            }
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });

                    }
                }

            }else {//si es la primera vez q ingresan
                Log.d(TAG, "es primera edicion");

                spnZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int q, long l) {
                        //if(q != 0){
                        cargarEmpresas();
                        spnEmpresa.setEnabled(true);
                        ArrayAdapter<CharSequence> adaptadorEmpresas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreEmpresas);
                        spnEmpresa.setAdapter(adaptadorEmpresas);
                        spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //   if(i != 0){
                                cargarFundos();
                                spnFundo.setEnabled(true);
                                ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreFundos);
                                spnFundo.setAdapter(adaptadorFundos);
                                spnFundo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        //    if(position != 0){
                                        /*
                                        cargarContactos();
                                        spnContacto.setEnabled(true);
                                        ArrayAdapter<CharSequence> adaptadorContactos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreContactos);
                                        spnContacto.setAdapter(adaptadorContactos);
                                         */
                                            /*   }else{
                                                    spnContacto.setEnabled(false);
                                                    spnContacto.setSelection(0);
                                                }
                                            */
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                   /* }else{
                                        spnFundo.setEnabled(false);
                                        spnFundo.setSelection(0);
                                    }*/
                            }
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });

                        /*}else{
                            spnEmpresa.setSelection(0);
                            spnEmpresa.setEnabled(false);
                            spnFundo.setSelection(0);
                            spnFundo.setEnabled(false);
                        }*/
                    }
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                });


                cargarCultivos();
                Log.d(TAG,"nCultivos:"+listCultivos.size());
                for(int i=0;i<listCultivos.size();i++) {
                    if (listCultivos.get(i).getId() == new LoginDataDAO(getBaseContext()).verficarLogueo().getIdCultivo())
                    {
                        Log.d(TAG, "CULTIVO ENCONTRADA POS = " + i /*+ 1*/);
                        spnCultivo.setSelection(i /*+ 1*/);
                        spnCultivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //if(i != 0){
                                cargarVariedades();
                                spnVariedad.setEnabled(true);
                                spnCultivo.setEnabled(false);
                                ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreVariedades);
                                spnVariedad.setAdapter(adaptadorFundos);
                        /*}else{
                            spnVariedad.setEnabled(false);
                            spnVariedad.setSelection(0);
                        }*/
                            }
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                        //cargarVariedades();
                    }else {
                        Log.d(TAG,"cultino "+listCultivos.get(i).getId()+" no encontrado aun"+new LoginDataDAO(getBaseContext()).verficarLogueo().getIdCultivo());
                    }
                }



                spnTipoCalibre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int q, long l) {


                        cargarCalibre();

                        ArrayAdapter<CharSequence> adaptadorCalibre = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreCalibre);
                        spnCalibre.setAdapter(adaptadorCalibre);

                    }
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                });


            }
        }




    }



    private void cargarCalibre() {
        CalibreDAO calibreDAO = new CalibreDAO(getBaseContext());
        int posTipoCalibre = spnTipoCalibre.getSelectedItemPosition();
        listCalibre = calibreDAO.listByIdTipoCalibre(listTipoCalibre.get(posTipoCalibre/*-1*/).getId());
        cargarNombreCalibre();
    }

    private void cargarNombreCalibre() {
        listNombreCalibre = new ArrayList<String>();
        //listNombreVariedades.add(getString(R.string.cabezeraSpnVariedad));
        for(CalibreVO cal : listCalibre){
            listNombreCalibre.add(cal.getName());
        }
    }


    private void cargarTipoCalibre() {
        TipoCalibreDAO tipoCalibreDAO = new TipoCalibreDAO(getBaseContext());
        listTipoCalibre = tipoCalibreDAO.listTipoCalibre();
        cargarNombreTipoCalibre();
    }

    private void cargarNombreTipoCalibre() {
        listNombreTipoCalibre = new ArrayList<String>();
        //    listNombreCultivos.add(getString(R.string.cabezeraSpnCultivo));
        for(TipoCalibreVO tcal : listTipoCalibre){
            listNombreTipoCalibre.add(tcal.getName());
        }
    }

    private void cargarDestino() {
        DestinoDAO destinoDAO = new DestinoDAO(getBaseContext());
        listDestino = destinoDAO.listDestinos();
        cargarNombreDestino();
    }

    private void cargarNombreDestino() {
        listNombreDestino = new ArrayList<String>();
        //    listNombreCultivos.add(getString(R.string.cabezeraSpnCultivo));
        for(DestinoVO des : listDestino){
            listNombreDestino.add(des.getName());
        }
    }

    private void cargarCategoria() {
        CategoriaDAO cultivoDAO = new CategoriaDAO(getBaseContext());
        listCategoria = cultivoDAO.listCategorias();
        cargarNombreCategoria();
    }

    private void cargarNombreCategoria() {
        listNombreCategoria = new ArrayList<String>();
        //    listNombreCultivos.add(getString(R.string.cabezeraSpnCultivo));
        for(CategoriaVO cat : listCategoria){
            listNombreCategoria.add(cat.getName());
        }
    }

    private void cargarVariedades() {
        VariedadDAO variedadDAO = new VariedadDAO(getBaseContext());
        int posCultivo = spnCultivo.getSelectedItemPosition();
        listVariedades = variedadDAO.listarVariedadByIdCultivo(listCultivos.get(posCultivo/*-1*/).getId());
        cargarNombreVariedades();
    }

    private void cargarNombreVariedades() {
        listNombreVariedades = new ArrayList<String>();
        //listNombreVariedades.add(getString(R.string.cabezeraSpnVariedad));
        for(VariedadVO var : listVariedades){
            listNombreVariedades.add(var.getName());
        }
    }

    private void cargarCultivos(){
        Log.d(TAG,"CARGANDO CULTIVOS");
        CultivoDAO cultivoDAO = new CultivoDAO(getBaseContext());
        listCultivos = cultivoDAO.listCultivos();
        cargarNombreCultivos();
    }

    private void cargarNombreCultivos() {
        listNombreCultivos = new ArrayList<String>();
        //    listNombreCultivos.add(getString(R.string.cabezeraSpnCultivo));
        for(CultivoVO cul : listCultivos){
            listNombreCultivos.add(cul.getName());
        }
    }

    private void cargarFundos() {
        Log.d(TAG,"cargando Fundos ");
        FundoDAO fundoDAO = new FundoDAO(getBaseContext());
        int pos = spnEmpresa.getSelectedItemPosition();
        listFundos = fundoDAO.listarByIdEmpresa(listEmpresas.get(pos/*-1*/).getId());
        cargarNombreFundos();
    }

    private void cargarNombreFundos(){
        listNombreFundos = new ArrayList<String>();
        //    listNombreFundos.add(getString(R.string.cabezeraSpnFundo));
        for(FundoVO fun : listFundos){
            listNombreFundos.add(fun.getName());
        }
    }

    private void cargarZonas() {
        ZonaDAO zonaDAO = new ZonaDAO(getBaseContext());
        listZonas = zonaDAO.listZonas();
        //  Toast.makeText(getBaseContext(),""+listZonas.size(),Toast.LENGTH_LONG).show();
        cargarNombreZonas();
    }
    private void cargarNombreZonas(){
        listNombreZonas = new ArrayList<String>();
        //  listNombreZonas.add(getString(R.string.cabezeraSpnZona));
        for(ZonaVO zon : listZonas){
            listNombreZonas.add(zon.getName());
        }
    }


    private void cargarEmpresas() {
        Log.d(TAG,"cargando empresas ");
        EmpresaDAO empresaDAO = new EmpresaDAO(getBaseContext());
        int posZona = spnZona.getSelectedItemPosition();
        listEmpresas = empresaDAO.listEmpresasByIdZona(listZonas.get(posZona/*-1*/).getId());
        cargarNombreEmpresas();
    }
    private void cargarNombreEmpresas(){
        listNombreEmpresas = new ArrayList<String>();
        //listNombreEmpresas.add(getString(R.string.cabezeraSpnEmpresa));
        for(EmpresaVO emp : listEmpresas){
          //  Log.d(TAG,"emrpesa "+emp.getName());
            listNombreEmpresas.add(emp.getName());
        }
    }

    private void cargarEnvases() {
        EnvaseDAO envaseDAO = new EnvaseDAO(getBaseContext());
        int pos = spnEnvase.getSelectedItemPosition();
        listEnvases = envaseDAO.listarByIdTipoProceso(new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso());
        cargarNombreEnvases();
    }
    private void cargarNombreEnvases(){
        listNombreEnvases = new ArrayList<String>();
        //listNombreEmpresas.add(getString(R.string.cabezeraSpnEmpresa));
        for(EnvaseVO emp : listEnvases){
            listNombreEnvases.add(emp.getName());
        }
    }

    /*
        private void setupActionBar(){
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }*/
    @Override
    public boolean onSupportNavigateUp() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
        onBackPressed();
        return false;
    }

    final static int REQUEST_IMPORT_RECEPCION = 162;

    public void importRecepcion(View view){

        Intent intent = new Intent(this,ActivityListRecepcionesOP.class);
        startActivityForResult(intent,REQUEST_IMPORT_RECEPCION);
    }

    static final public String  RESPONSE_NORDENPROCESO  = "NRODENPROCESO";
    static final public String RESPONSE_IDFUNDO     = "IDFUNDO";
    static final public String RESPONSE_IDVARIEDAD  = "IDVARIEDAD";
    static final public String RESPONSE_IDRECEPCION  = "IDRECEPCION";


    static private int IDRECEPCION =0;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        //Toast.makeText(this,"onactivity resytk",Toast.LENGTH_LONG).show();
        // visita = new VisitaDAO(ctx).getEditing();
        switch (requestCode){
            case REQUEST_IMPORT_RECEPCION:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    Log.d(TAG,"RESULT OKAAA!");

                    Bundle b = data.getExtras();

                    String nOrdenProceso  = b.getString(RESPONSE_NORDENPROCESO);

                    eTextNOrdenProceso.setText(nOrdenProceso);

                    int idFundo = b.getInt(RESPONSE_IDFUNDO);
                    int idEmpresa = new EmpresaDAO(getBaseContext()).consultarEmpresaByidFundo(idFundo).getId();
                    int idZona = new ZonaDAO(getBaseContext()).consultarByidEmpresa(idEmpresa).getId();


                    int idVariedad = b.getInt(RESPONSE_IDVARIEDAD);
                    int idCultivo = new CultivoDAO(getBaseContext()).consultarByIdVariedad(idVariedad).getId();

                    IDRECEPCION = b.getInt(RESPONSE_IDRECEPCION);

                    Log.d(TAG,"id:"+idCultivo+" -- idVariedad:"+idVariedad);
                    Log.d(TAG,"idCultivo:"+idCultivo+" -- idVariedad:"+idVariedad);

                    PRODUCCION.setIdVariedad(idVariedad);
                    PRODUCCION.setIdCultivo(idCultivo);
                    PRODUCCION.setIdFundo(idFundo);
                    PRODUCCION.setIdZona(idZona);
                    PRODUCCION.setIdEmpresa(idEmpresa);
                    Log.d(TAG,"f1");
                    //seter los  valores de  los  campos

/*
                    spnZona.setAdapter(null);
                    spnCultivo.setAdapter(null);
*/
                    spnZona = (Spinner) findViewById(R.id.spnZona);
                    spnEmpresa = (Spinner) findViewById(R.id.spnProductor);
                    spnFundo = (Spinner) findViewById(R.id.spnFundo);
                    spnCultivo = (Spinner) findViewById(R.id.spnCultivo);
                    spnVariedad = (Spinner) findViewById(R.id.spnVariedad);

                    cargarZonas();
                    cargarCultivos();

                    adaptadorZonas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreZonas);
                    adaptadorCultivos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreCultivos);

                    spnZona.setAdapter(adaptadorZonas);
                    spnCultivo.setAdapter(adaptadorCultivos);

                    for(int w=0;w<listZonas.size();w++){
                        if(listZonas.get(w).getId()== PRODUCCION.getIdZona()){
                            spnZona.setSelection(w/*+1*/);
                            Log.d(TAG,"Zona ENCONTRADA POS = "+(w/*+1*/)+" "+spnZona.getSelectedItem().toString());
                            spnZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> adapterView, View view, int q, long l) {
                                    //  if(q != 0){
                                    cargarEmpresas();
                                    spnEmpresa.setEnabled(true);
                                    ArrayAdapter<CharSequence> adaptadorEmpresas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreEmpresas);
                                    spnEmpresa.setAdapter(adaptadorEmpresas);
                                    for(int i=0;i<listEmpresas.size();i++){
                                        if(listEmpresas.get(i).getId()== PRODUCCION.getIdEmpresa()){
                                            spnEmpresa.setSelection(i/*+1*/);
                                            Log.d(TAG,"EMPRESA ENCONTRADA POS = "+i/*+1*/);
                                            spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                    //if(i != 0){
                                                    cargarFundos();
                                                    spnFundo.setEnabled(true);
                                                    ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreFundos);
                                                    spnFundo.setAdapter(adaptadorFundos);
                                                    for(int j = 0 ; j<listFundos.size();j++){
                                                        if(listFundos.get(j).getId()== PRODUCCION.getIdFundo()){
                                                            spnFundo.setSelection(j/*+1*/);
                                                            spnFundo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                    //if(position !=0){
                                                                /*
                                                                cargarContactos();
                                                                spnContacto.setEnabled(true);
                                                                ArrayAdapter<CharSequence> adaptadorContactos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreContactos);
                                                                spnContacto.setAdapter(adaptadorContactos);
                                                                for(int k =0 ; k< listContactos.size();k++){
                                                                    if(listContactos.get(k).getId()==idContacto){
                                                                        spnContacto.setSelection(k+1);
                                                                    }
                                                                }
                                                                */
                                                                        /*}else{
                                                                            spnContacto.setEnabled(false);
                                                                            spnContacto.setSelection(0);
                                                                        }*/
                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> parent) {

                                                                }
                                                            });


                                                        }
                                                    }

                                /*                    }else{
                                                        spnFundo.setEnabled(false);
                                                        spnFundo.setSelection(0);
                                                    }
                                  */              }
                                                public void onNothingSelected(AdapterView<?> adapterView) {
                                                    return;
                                                }
                                            });
                                            break;
                                        }
                                    }
                                /*}else{
                                    spnEmpresa.setEnabled(false);
                                    spnEmpresa.setSelection(0);
                                    spnFundo.setEnabled(false);
                                    spnFundo.setSelection(0);
                                }*/
                                }
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    return;
                                }
                            });
                            break;
                        }else {
                            Log.d(TAG,"Zona no ENCONTRADA POS = "+(w/*+1*/)+" "+spnZona.getSelectedItem().toString());
                        }
                    }

                    for(int i=0;i<listCultivos.size();i++) {
                        if (listCultivos.get(i).getId() == PRODUCCION.getIdCultivo()) {
                            Log.d(TAG, "CULTIVO ENCONTRADA POS = " + i /*+ 1*/);
                            spnCultivo.setSelection(i /*+ 1*/);
                            spnCultivo.setEnabled(false);
                            Log.d(TAG,"antes de buscar variedad1");
                            spnCultivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    // if(i != 0){
                                    cargarVariedades();
                                    spnVariedad.setEnabled(true);
                                    ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreVariedades);
                                    spnVariedad.setAdapter(adaptadorFundos);
                                    Log.d(TAG,"antes de buscar variedad2");
                                    for(int j = 0 ; j<listVariedades.size();j++){
                                        if(listVariedades.get(j).getId()== PRODUCCION.getIdVariedad()){
                                            Log.d(TAG, "variedad ENCONTRADA POS = " + i /*+ 1*/);
                                            spnVariedad.setSelection(j/*+1*/);
                                        }
                                    }/*
                                }else{
                                    spnVariedad.setEnabled(false);
                                    spnVariedad.setSelection(0);
                                }*/
                                }
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    return;
                                }
                            });

                        }
                    }


                }else{
                    Toast.makeText(getBaseContext(),"No se eligió Recepción",Toast.LENGTH_LONG).show();
                }

                break;

        }

    }

    public void pressListo(View view){

        Intent returnIntent = new Intent();

        String nProceso = eTextNOrdenProceso.getText().toString();
        String kilos = eTextKilos.getText().toString();
        String unidades = eTextUnidades.getText().toString();
/*
        if(     !(nProceso==null || nProceso.equals(""))
                &&
                !(kilos==null || kilos.equals(""))
                &&
                !(unidades==null || unidades.equals(""))
            ){
*/
            int idFundo = listFundos.get(spnFundo.getSelectedItemPosition()/*-1*/).getId();
            int idVariedad = listVariedades.get(spnVariedad.getSelectedItemPosition()/*-1*/).getId();
            int idEnvase = listEnvases.get(spnEnvase.getSelectedItemPosition()/*-1*/).getId();

            int idDestino = listDestino.get(spnDestino.getSelectedItemPosition()/*-1*/).getId();
            int idCalibre = listCalibre.get(spnCalibre.getSelectedItemPosition()/*-1*/).getId();
            int idCategoria = listCategoria.get(spnCategoria.getSelectedItemPosition()/*-1*/).getId();
            Log.d(TAG,"guardando "+"f:"+idFundo+" v:"+idVariedad+" env:"+idEnvase+" des:"+idDestino+" cal:"+idCalibre+" cat:"+idCategoria);


            ProduccionDAO produccionDAO = new ProduccionDAO(getBaseContext());

            boolean res =  produccionDAO.setBasicos
                    (PRODUCCION.getId(),idFundo,idVariedad,idEnvase,nProceso,kilos,unidades,new LoginDataDAO(getBaseContext()).verficarLogueo().getIdPlanta(),idDestino,idCalibre,idCategoria,IDRECEPCION);
            if(!res){
                Toast.makeText(getBaseContext(),"Error al intententar Modificar Verifique los Datos",Toast.LENGTH_SHORT).show();
            }else{
                // Toast.makeText(getBaseContext(),"Informacion Actualizada",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }

/*
        }else{
            Toast.makeText(getBaseContext(),"Faltan ingresar algún campo",Toast.LENGTH_SHORT).show();
        }
*/

    }

}
