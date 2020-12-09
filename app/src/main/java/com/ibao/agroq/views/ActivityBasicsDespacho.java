package com.ibao.agroq.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.ibao.agroq.R;
import com.ibao.agroq.models.dao.DespachoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.vo.entitiesInternal.DespachoVO;

import java.util.Date;

public class ActivityBasicsDespacho extends AppCompatActivity {


    private String TAG = ActivityBasicsDespacho.class.getSimpleName();


    Dialog dialog;

    private static TextView tViewProducto;
    private static TextView tViewPlanta;

    private static EditText eTextNContenedor;
    private static EditText eTextNameControlador;
    private static EditText eTextTermo1;
    private static EditText eTextTermo2;
    private static EditText eTextTermo3;


    private static EditText eTextOrigen;
    private static EditText eTextCliente;
    private static EditText eTextRG;
    private static EditText eTextPL;
    private static RadioButton rbAereo;
    private static RadioButton rbMaritimo;
    private static EditText eTextGGN;
    private static EditText eTextNReserva;
    private static EditText eTextTransportModel;
    private static EditText eTextTemperatura;
    private static EditText eTextCO2;
    private static EditText eTextO2;
    private static EditText eTextTecnologia;


    private static CheckBox cBoxPreFrio;
    private static EditText eTextNPallets;
    private static EditText eTextNCajasPallet;
    private static EditText eTextPesoCaja;




    private static TextView tViewFechaInspeccion;
    private static TextView tViewFechaCarga;
    private static TextView tViewFechaLlegada;
    private static TextView tViewFechaSalida;

    String fechaInspeccion="00-00-0000";
    String fechaCarga="00-00-0000";
    String fechaLlegada="00-00-0000";
    String fechaSalida="00-00-0000";
    private static ImageView iViewEditFechaInspeccion;
    private static ImageView iViewEditFechaCarga;
    private static ImageView iViewEditFechaLlegada;
    private static ImageView iViewEditFechaSalida;

    private static DespachoVO DESPACHO;

    private static Bundle recibidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basics_despacho);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setupActionBar();
        tViewFechaInspeccion = (TextView) findViewById(R.id.tViewFechaRecepcion);
        tViewFechaCarga = (TextView) findViewById(R.id.tViewFechaCarga);
        tViewFechaLlegada = (TextView) findViewById(R.id.tViewFechaLlegada);
        tViewFechaSalida = (TextView) findViewById(R.id.tViewFechaSalida);

        tViewProducto = (TextView) findViewById(R.id.tViewProducto);
        tViewPlanta = (TextView) findViewById(R.id.tViewNFrutos);

        iViewEditFechaInspeccion = (ImageView) findViewById(R.id.iViewEditFechaInspeccion);
        iViewEditFechaCarga = (ImageView) findViewById(R.id.iViewEditFechaCarga);
        iViewEditFechaLlegada = (ImageView) findViewById(R.id.iViewEditFechaLlegada);
        iViewEditFechaSalida = (ImageView) findViewById(R.id.iViewEditFechaSalida);


        eTextNContenedor= (EditText) findViewById(R.id.eTextNContenedor);
        eTextNameControlador= (EditText) findViewById(R.id.eTextNameControlador);
        eTextTermo1= (EditText) findViewById(R.id.eTextTermo1);
        eTextTermo2= (EditText) findViewById(R.id.eTextTermo2);
        eTextTermo3= (EditText) findViewById(R.id.eTextTermo3);

        eTextOrigen= (EditText) findViewById(R.id.eTextOrigen);
        eTextCliente= (EditText) findViewById(R.id.eTextCliente);
        eTextRG= (EditText) findViewById(R.id.eTextRG);
        eTextPL= (EditText) findViewById(R.id.eTextPL);
        rbAereo= (RadioButton) findViewById(R.id.rbAereo);
        rbMaritimo= (RadioButton) findViewById(R.id.rbMaritimo);
        eTextGGN= (EditText) findViewById(R.id.eTextGGN);
        eTextNReserva= (EditText) findViewById(R.id.eTextNReserva);
        eTextTransportModel= (EditText) findViewById(R.id.eTextTransportModel);
        eTextTemperatura= (EditText) findViewById(R.id.eTextTemperatura);
        eTextCO2= (EditText) findViewById(R.id.eTextCO2);
        eTextO2= (EditText) findViewById(R.id.eTextO2);
        eTextTecnologia= (EditText) findViewById(R.id.eTextTecnologia);


        cBoxPreFrio= (CheckBox) findViewById(R.id.cBoxPreFrio);
        eTextNPallets= (EditText) findViewById(R.id.eTextNPallets);
        eTextNCajasPallet= (EditText) findViewById(R.id.eTextNCajasPallet);
        eTextPesoCaja= (EditText) findViewById(R.id.eTextPesoCaja);





        iViewEditFechaInspeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogFecha(tViewFechaInspeccion);
            }
        });
        iViewEditFechaCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogFecha(tViewFechaCarga);
            }
        });
        iViewEditFechaLlegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogFecha(tViewFechaLlegada);
            }
        });
        iViewEditFechaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogFecha(tViewFechaSalida);
            }
        });

        cBoxPreFrio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cBoxPreFrio.setText("Si");
                }else {
                    cBoxPreFrio.setText("No");
                }
            }
        });

        //reciviendo parametros de la actividad anterior
        recibidos = getIntent().getExtras();

        if(recibidos!=null){//si se recibe datos
            DESPACHO = new DespachoDAO(getBaseContext()).buscarById((long)recibidos.getInt(ActivityProceso.REQUEST_ID));

            Log.d(TAG, "SEND IS NOT NULL");

            tViewFechaInspeccion.setText(DESPACHO.getFechaInspeccion());
            tViewPlanta.setText(DESPACHO.getNamePlanta());
            tViewProducto.setText(DESPACHO.getNameCultivo());

            Log.d(TAG, "es primera edicion");
            tViewFechaCarga.setText(DESPACHO.getFechaCarga()==null?"":DESPACHO.getFechaCarga());
            tViewFechaLlegada.setText(DESPACHO.getFechaLlegada()==null?"":DESPACHO.getFechaLlegada());
            tViewFechaSalida.setText(DESPACHO.getFechaSalida()==null?"":DESPACHO.getFechaSalida());
            eTextNContenedor.setText(DESPACHO.getnContenedor()==null?"":DESPACHO.getnContenedor());
            eTextNameControlador.setText(DESPACHO.getNameControlador()==null?"":DESPACHO.getNameControlador());
            eTextTermo1.setText(DESPACHO.getTermo1()==null?"":DESPACHO.getTermo1());
            eTextTermo2.setText(DESPACHO.getTermo2()==null?"":DESPACHO.getTermo2());
            eTextTermo3.setText(DESPACHO.getTermo3()==null?"":DESPACHO.getTermo3());


            eTextOrigen.setText(DESPACHO.getOrigen()==null?"":DESPACHO.getOrigen());
            eTextCliente.setText(DESPACHO.getCliente()==null?"":DESPACHO.getCliente());
            eTextRG.setText(DESPACHO.getRG()==null?"":DESPACHO.getRG());
            eTextPL.setText(DESPACHO.getPL()==null?"":DESPACHO.getPL());
            String tp = DESPACHO.getTP()==null?"M":"";
            if (tp.compareToIgnoreCase("A") == 0)
                rbAereo.setChecked(true);
            else
                rbMaritimo.setChecked(true);
            eTextGGN.setText(DESPACHO.getGGN()==0?"":""+DESPACHO.getGGN());
            eTextNReserva.setText(DESPACHO.getnReserva()==null?"":DESPACHO.getnReserva());
            eTextTransportModel.setText(DESPACHO.getTrasportModel()==null?"":DESPACHO.getTrasportModel());
            eTextTemperatura.setText(DESPACHO.getTemperatura()==0?"":""+DESPACHO.getTemperatura());
            eTextCO2.setText(DESPACHO.getDioxidoCarbono()==0?"":""+DESPACHO.getDioxidoCarbono());
            eTextO2.setText(DESPACHO.getOxigeno()==0?"":""+DESPACHO.getOxigeno());
            eTextTecnologia.setText(DESPACHO.getTecnologia()==null?"":DESPACHO.getTecnologia());


            cBoxPreFrio.setChecked(DESPACHO.isPreFrio());

            eTextNPallets.setText(DESPACHO.getnPallets()==0?"":""+DESPACHO.getnPallets());
            eTextNCajasPallet.setText(DESPACHO.getnCajasPallet()==0?"":""+DESPACHO.getnCajasPallet());
            eTextPesoCaja.setText(DESPACHO.getPesoCaja()==0?"":""+DESPACHO.getPesoCaja());

        }
        //Booking -> AWB
        //Vessel -> AIRLINE
        //Container Number -> LICENSE PLATE TERMOKING
        //Termographs 1 -> KIND TERMOKING
        //Termographs 2 -> T° TERMOKING

        final TextView lblMod1 = findViewById(R.id.textView79);
        final TextView lblMod2 = findViewById(R.id.textView80);
        final TextView lblMod3 = findViewById(R.id.textView76);
        final TextView lblMod4 = findViewById(R.id.textView65);
        final TextView lblMod5 = findViewById(R.id.textView66);

        int idCultivo = new LoginDataDAO(this).verficarLogueo().getIdCultivo();

        if (idCultivo == 1)
            rbAereo.setVisibility(View.GONE);
        else{
            rbAereo.setVisibility(View.VISIBLE);
            rbAereo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rbAereo.isChecked()){
                        lblMod1.setText("AWB");
                        lblMod2.setText("Airline");
                        lblMod3.setText("License Plate");
                        lblMod4.setText("Kind Termoking");
                        lblMod5.setText("T° Termoking");
                    }
                }
            });
            rbMaritimo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rbMaritimo.isChecked()){
                        lblMod1.setText("Booking");
                        lblMod2.setText("Vessel");
                        lblMod3.setText("Container Number");
                        lblMod4.setText("Termographs 1");
                        lblMod5.setText("Termographs 2");
                    }
                }
            });
        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
        onBackPressed();
        return false;
    }


    public void openDialogFecha(final TextView tViewFecha) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.datepicker);

        final DatePicker datePicker = (DatePicker)dialog.findViewById(R.id.datePicker1);

        String strFecha= tViewFecha.getText().toString();
        final String fecha = strFecha;
        int year=0;
        int month=0;
        int day=0;
        try{
            String[] parts = fecha.split("-");
            year = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1])-1;
            day = Integer.parseInt(parts[2]);
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }


        if(year != 0 && month != 0 && day != 0) {
            datePicker.updateDate(year, month, day);
        }

        final Button btn_aceptar = (Button)dialog.findViewById(R.id.btn_aceptar);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth()+1;
                int year = datePicker.getYear();

                String dia = String.valueOf(day);
                String mes = String.valueOf(month);
                String anio = String.valueOf(year);

                if(dia.length()==1){
                    dia = "0"+dia;
                }

                if(mes.length()==1){
                    mes = "0"+mes;
                }

                Date date = new Date();
                date.setYear(Integer.parseInt(anio));
                date.setMonth(Integer.parseInt(mes+1));
                date.setDate(Integer.parseInt(dia));

                String fechaF = anio+"-"+mes+"-"+dia;


                tViewFecha.setText(fechaF);

                dialog.dismiss();

                //BuscarServicios();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }



    public void pressListo(View view){

        Intent returnIntent = new Intent();

        String fechaInspeccion = tViewFechaInspeccion.getText().toString();
        String fechaCarga = tViewFechaCarga.getText().toString();
        String fechaLlegada = tViewFechaLlegada.getText().toString();
        String fechaSalida = tViewFechaSalida.getText().toString();

        String nContenedor = eTextNContenedor.getText().toString();
        String nameControlador = eTextNameControlador.getText().toString();
        String termo1 = eTextTermo1.getText().toString();
        String termo2 = eTextTermo2.getText().toString();
        String termo3 = eTextTermo3.getText().toString();

        String Origen = eTextOrigen.getText().toString();
        String cliente = eTextCliente.getText().toString();
        int GGN =0;
        try {
         GGN =   Integer.valueOf(eTextGGN.getText().toString());
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }

        String nReserva = eTextNReserva.getText().toString();
        String transportModel = eTextTransportModel.getText().toString();
        float temperatura=0.0f;
        try {
            temperatura = Float.valueOf(eTextTemperatura.getText().toString());
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }

        float CO2 =0.0f;
        try {
            CO2 = Float.valueOf(eTextCO2.getText().toString());
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }

        float O2 =0.0f;
        try {
            O2 = Float.valueOf(eTextO2.getText().toString());
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }


        String tecnologia = eTextTecnologia.getText().toString();

        Boolean isPreFrio = cBoxPreFrio.isChecked();

        int nPallets =0;
        try {
            nPallets = Integer.valueOf(eTextNPallets.getText().toString());
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }

        int nCajasPallets =0;
        try {
            nCajasPallets = Integer.valueOf(eTextNCajasPallet.getText().toString());
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }

        float pesoCaja =0.0f;
        try {
            pesoCaja = Float.valueOf(eTextPesoCaja.getText().toString());
            Log.d(TAG,"pesoCaja : "+pesoCaja);
        }catch (Exception e){
            Log.d(TAG,"pesoCajaXE : "+e.toString());
        }

        String rg = eTextRG.getText().toString();
        String pl = eTextPL.getText().toString();
        String tp = "A";
        if (rbMaritimo.isChecked()) tp = "M";
        DespachoDAO recepcionDAO = new DespachoDAO(getBaseContext());

            boolean res =  recepcionDAO.setBasicos
                    (       DESPACHO.getId(),
                            fechaInspeccion,
                            fechaCarga,
                            fechaLlegada,
                            fechaSalida,

                            nContenedor,
                            nameControlador,
                            termo1,
                            termo2,
                            termo3,

                            Origen,
                            cliente,
                            GGN,
                            nReserva,
                            transportModel,
                            temperatura,
                            CO2,
                            O2,
                            tecnologia,

                            isPreFrio,
                            nPallets,
                            nCajasPallets,
                            pesoCaja,
                            rg,
                            pl,
                            tp

                    );
            if(!res){
            Snackbar.make(view, "Error al intententar Modificar Verifique los Datos", Snackbar.LENGTH_LONG)
                    .show();

            }else{
                Snackbar.make(view, "Datos Guardados", Snackbar.LENGTH_LONG)
                        .show();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }

    }

}
