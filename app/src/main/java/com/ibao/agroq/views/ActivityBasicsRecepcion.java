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
import com.ibao.agroq.models.dao.CultivoDAO;
import com.ibao.agroq.models.dao.EmpresaDAO;
import com.ibao.agroq.models.dao.EnvaseDAO;
import com.ibao.agroq.models.dao.FundoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.RecepcionDAO;
import com.ibao.agroq.models.dao.VariedadDAO;
import com.ibao.agroq.models.dao.ZonaDAO;
import com.ibao.agroq.models.vo.entitiesDB.CultivoVO;
import com.ibao.agroq.models.vo.entitiesDB.EmpresaVO;
import com.ibao.agroq.models.vo.entitiesDB.EnvaseVO;
import com.ibao.agroq.models.vo.entitiesDB.FundoVO;
import com.ibao.agroq.models.vo.entitiesDB.VariedadVO;
import com.ibao.agroq.models.vo.entitiesDB.ZonaVO;
import com.ibao.agroq.models.vo.entitiesInternal.RecepcionVO;

import java.util.ArrayList;
import java.util.List;

public class ActivityBasicsRecepcion extends AppCompatActivity {

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

    String TAG = ActivityBasicsRecepcion.class.getSimpleName();

    private static Spinner spnZona;
    private static Spinner spnEmpresa;
    private static Spinner spnFundo;
    private static Spinner spnVariedad;
    private static Spinner spnCultivo;
    private static Spinner spnEnvase;
    private static EditText eTextNOrdenProceso;
    private static EditText eTextNOrdenGuia;
    private static EditText eTextKilos;
    private static EditText eTextUnidades;
    private static TextView tViewFechaHora;


    private static RecepcionVO RECEPCION;

    private static Bundle recibidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basics_recepcion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setupActionBar();
        tViewFechaHora = (TextView) findViewById(R.id.tViewFechaRecepcion);

        spnZona = (Spinner) findViewById(R.id.spnZona);
        spnEmpresa = (Spinner) findViewById(R.id.spnProductor);
        spnFundo = (Spinner) findViewById(R.id.spnFundo);
        spnCultivo = (Spinner) findViewById(R.id.spnCultivo);
        spnVariedad = (Spinner) findViewById(R.id.spnVariedad);
        spnEnvase = (Spinner) findViewById(R.id.spnTipoEnvase);
        eTextNOrdenProceso = (EditText) findViewById(R.id.eTextNOrdenProceso);
        eTextNOrdenGuia = (EditText) findViewById(R.id.eTextNGuia);
        eTextKilos = (EditText) findViewById(R.id.eTextKilos);
        eTextUnidades = (EditText) findViewById(R.id.eTextNUnidades);

        //cargar los nombre a ram
        cargarEnvases();
        cargarZonas();
        cargarCultivos();

        //inicializando adaptadpres de spn iniciales
        ArrayAdapter<CharSequence> adaptadorZonas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreZonas);
        ArrayAdapter<CharSequence> adaptadorCultivos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreCultivos);
        ArrayAdapter<CharSequence> adaptadorEnvases = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreEnvases);

        //seteando esos adaptadores
        spnZona.setAdapter(adaptadorZonas);
        spnCultivo.setAdapter(adaptadorCultivos);
        spnEnvase.setAdapter(adaptadorEnvases);

        //reciviendo parametros de la actividad anterior
        recibidos = getIntent().getExtras();

        if(recibidos!=null){//si se recibe datos
            RECEPCION= new RecepcionDAO(getBaseContext()).buscarById((long)recibidos.getInt(ActivityProceso.REQUEST_ID));

            Log.d(TAG, "SEND IS NOT NULL");

            tViewFechaHora.setText(RECEPCION.getFechaHora());

            //si no es la primera ves q se accede a editar
            if(recibidos.getInt("isFirst")==0){//si no es la primera vez q ingresan

                Log.d(TAG,"recepcion = "+String.valueOf(RECEPCION.getIdZona()+" "+RECEPCION.getIdEmpresa()+" "+RECEPCION.getIdFundo()));
                eTextNOrdenGuia.setText(RECEPCION.getnOrdenGuia());
                eTextNOrdenProceso.setText(RECEPCION.getnOrdenProceso());
                eTextKilos.setText(RECEPCION.getKilosRecepcion());
                eTextUnidades.setText(RECEPCION.getUnidadesRecepcion());
                spnCultivo.setEnabled(false);

                for(int w=0;w<listZonas.size();w++){
                    if(listZonas.get(w).getId()==RECEPCION.getIdZona()){
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
                                    if(listEmpresas.get(i).getId()==RECEPCION.getIdEmpresa()){
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
                                                    if(listFundos.get(j).getId()==RECEPCION.getIdFundo()){
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
                    if (listCultivos.get(i).getId() == RECEPCION.getIdCultivo()) {
                        Log.d(TAG, "CULTIVO ENCONTRADA POS = " + i /*+ 1*/);
                        spnCultivo.setSelection(i /*+ 1*/);
                        spnCultivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                // if(i != 0){
                                cargarVariedades();
                                spnVariedad.setEnabled(true);
                                ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreVariedades);
                                spnVariedad.setAdapter(adaptadorFundos);
                                for(int j = 0 ; j<listVariedades.size();j++){
                                    if(listVariedades.get(j).getId()==RECEPCION.getIdVariedad()){
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
                    if (listEnvases.get(i).getId() == RECEPCION.getIdEnvase()) {
                        Log.d(TAG, "envase ENCONTRADo POS = " + i /*+ 1*/);
                        spnEnvase.setSelection(i /*+ 1*/);

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


            }
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
            Log.d(TAG,"emrpesa "+emp.getName());
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

    public void pressListo(View view){

        Intent returnIntent = new Intent();

        String nProceso = eTextNOrdenProceso.getText().toString();
        String nGuia = eTextNOrdenGuia.getText().toString();
        String kilos = eTextKilos.getText().toString();
        String unidades = eTextUnidades.getText().toString();
/*
        if(     !(nProceso==null || nProceso.equals(""))
                &&
                !(nGuia==null || nGuia.equals(""))
                &&
                !(kilos==null || kilos.equals(""))
                &&
                !(unidades==null || unidades.equals(""))
            ){
*/
            int idFundo = listFundos.get(spnFundo.getSelectedItemPosition()/*-1*/).getId();
            int idVariedad = listVariedades.get(spnVariedad.getSelectedItemPosition()/*-1*/).getId();
            int idEnvase = listEnvases.get(spnEnvase.getSelectedItemPosition()/*-1*/).getId();
            Log.d(TAG,"guardando "+"f:"+idFundo+" v:"+idVariedad+" env:"+idEnvase);
            /*
            returnIntent.putExtra(ActivityVisita.REQUEST_EMPRESA,spnEmpresa.getSelectedItem().toString());
            returnIntent.putExtra(ActivityVisita.REQUEST_FUNDO,spnFundo.getSelectedItem().toString());
            returnIntent.putExtra(ActivityVisita.REQUEST_CULTIVO,spnCultivo.getSelectedItem().toString());
            returnIntent.putExtra(ActivityVisita.REQUEST_VARIEDAD,spnVariedad.getSelectedItem().toString());
            returnIntent.putExtra(ActivityVisita.REQUEST_CONTACTO,nameContacto);
            */

            RecepcionDAO recepcionDAO = new RecepcionDAO(getBaseContext());

            boolean res =  recepcionDAO.setBasicos
                    (RECEPCION.getId(),idFundo,idVariedad,idEnvase,nProceso,nGuia,kilos,unidades,new LoginDataDAO(getBaseContext()).verficarLogueo().getIdPlanta());
            if(!res){
                Toast.makeText(getBaseContext(),"Error al intententar Modificar Verifique los Datos",Toast.LENGTH_SHORT).show();

            }else{
                // Toast.makeText(getBaseContext(),"Informacion Actualizada",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }

     /*   }else{
            Toast.makeText(getBaseContext(),"Faltan ingresar algún campo",Toast.LENGTH_SHORT).show();
        }
*/

    }

}
