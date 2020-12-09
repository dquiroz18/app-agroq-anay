package com.ibao.agroq.views;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ibao.agroq.R;
import com.ibao.agroq.helpers.LoginHelper;
import com.ibao.agroq.models.dao.CultivoDAO;
import com.ibao.agroq.models.dao.PlantaDAO;
import com.ibao.agroq.models.vo.entitiesDB.CultivoVO;
import com.ibao.agroq.models.vo.entitiesDB.PlantaVO;

import java.util.ArrayList;
import java.util.List;

public class ActivityLogin extends AppCompatActivity {

    private static List<PlantaVO> listPlantas;
    private static List<String> listNamePlantas;

    private static List<CultivoVO> listCultivos;
    private static List<String> listNameCultivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Animation animBtn =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(),R.anim.press_btn);
        final Button btnEnter = (Button) findViewById(R.id.btnEnter);

        final EditText eTextUser = (EditText) findViewById(R.id.eTextUsuario);
        final EditText eTextPassword = (EditText) findViewById(R.id.eTextPassword);

        cargarCultivos();
        cargarPlantas();

        final Spinner spnPlanta = (Spinner) findViewById(R.id.spnZona);
        final Spinner spnCultivo = (Spinner) findViewById(R.id.spnCultivo);

        ArrayAdapter<CharSequence> adaptadorPlantas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNamePlantas);
        ArrayAdapter<CharSequence> adaptadorCultivos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNameCultivos);

        spnPlanta.setAdapter(adaptadorPlantas);
        spnCultivo.setAdapter(adaptadorCultivos);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animBtn);
                Handler handler = new Handler();
                handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                //btnEnter.setClickable(false);
                                //Intent intent = new Intent(getBaseContext(),ActivityPostloader.class);
                                //startActivity(intent);


                                if(spnPlanta.getSelectedItemPosition()>0 && spnCultivo.getSelectedItemPosition()>0){
                                    Log.d("autentification","intentando");
                                    LoginHelper loginHelper = new LoginHelper(ActivityLogin.this);
                                    loginHelper.intentoLogueo(
                                            eTextUser.getText().toString(),
                                            eTextPassword.getText().toString(),
                                            listPlantas.get(spnPlanta.getSelectedItemPosition()-1).getId(),
                                            listCultivos.get(spnCultivo.getSelectedItemPosition()-1).getId()
                                    );
                                }else {
                                    if(spnPlanta.getSelectedItemPosition()==0){
                                        Toast.makeText(getBaseContext(),"Elija una Planta",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getBaseContext(),"Elija un Cultivo",Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }
                        },200
                );
            }
        });


        ImageView iViewPasswordSetVisible = (ImageView) findViewById(R.id.iViewPassword);
        iViewPasswordSetVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animBtn);
                if(eTextPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    eTextPassword.setInputType( InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else {
                    eTextPassword.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                }
                eTextPassword.setSelection(eTextPassword.getText().length());
            }
        });


        final ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.constCombo);
        cl.setVisibility(View.INVISIBLE);

        final Animation animLayout =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(),R.anim.left_in);

        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        cl.startAnimation(animLayout);
                        cl.setVisibility(View.VISIBLE);
                    }
                },200
        );

    }
    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        moveTaskToBack(true);

    }

    private void cargarPlantas() {
        PlantaDAO plantaDAO = new PlantaDAO(getBaseContext());
        listPlantas = plantaDAO.listPlantas();
        //  Toast.makeText(getBaseContext(),""+listZonas.size(),Toast.LENGTH_LONG).show();
        cargarNombrePlantas();
    }
    private void cargarNombrePlantas(){
        listNamePlantas = new ArrayList<String>();
        listNamePlantas.add(getString(R.string.cabezeraSpnPlanta));
        for(PlantaVO zon : listPlantas){
            listNamePlantas.add(zon.getName());
        }
    }

    private void cargarCultivos() {
        CultivoDAO cultivoDAO = new CultivoDAO(getBaseContext());
        listCultivos = cultivoDAO.listCultivos();
        //  Toast.makeText(getBaseContext(),""+listZonas.size(),Toast.LENGTH_LONG).show();
        cargarNombreCultivos();
    }
    private void cargarNombreCultivos(){
        listNameCultivos = new ArrayList<String>();
        listNameCultivos.add(getString(R.string.cabezeraSpnCultivo));
        for(CultivoVO zon : listCultivos){
            listNameCultivos.add(zon.getName());
        }
    }


}
