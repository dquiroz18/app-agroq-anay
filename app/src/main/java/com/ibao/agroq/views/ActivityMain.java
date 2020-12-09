package com.ibao.agroq.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ibao.agroq.BuildConfig;
import com.ibao.agroq.R;
import com.ibao.agroq.models.dao.DescarteDAO;
import com.ibao.agroq.models.dao.DespachoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.ProduccionDAO;
import com.ibao.agroq.models.dao.RecepcionDAO;


public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentMain.OnFragmentInteractionListener{

    private FragmentMain myFragment = null;
    public static final String TAG = ActivityMain.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myFragment = new FragmentMain();


        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();

        //verificar y estan actualizar de inmediato

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        String NombreU = new LoginDataDAO(getBaseContext()).verficarLogueo().getName();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        ((TextView) headerView.findViewById(R.id.tViewUserName)).setText(NombreU);
    }
    private Dialog dialogClose;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.version) {
            try {
                Toast.makeText(getBaseContext(),"Versión "+BuildConfig.VERSION_NAME+" code."+BuildConfig.VERSION_CODE,Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.logout) {
            int i=0;
            String reco = "No olvide sincronizar sus procesos de:";

            if (new RecepcionDAO(getBaseContext()).listAll().size() > 0) {
                reco=reco+"\n*Recepcion";
                i++;
            }
            if (new ProduccionDAO(getBaseContext()).listAll().size() > 0) {
                reco=reco+"\n*Proceso";
                i++;
            }
            if (new DespachoDAO(getBaseContext()).listAll().size() > 0) {
                reco=reco+"\n*Despacho";
                i++;
            }
            if (new DescarteDAO(getBaseContext()).listAll().size() > 0) {
                reco=reco+"\n*Descarte";
                i++;
            }

            if(i>0){
                showPopupRecomendacion(reco);
            }else {
                Toast.makeText(getBaseContext(), "Cerrando Sesión...", Toast.LENGTH_LONG).show();
                new LoginDataDAO(getBaseContext()).borrarTable();
                Intent intent = new Intent(getBaseContext(), ActivityPreloader.class);
                startActivity(intent);
                //finish();
            }

            //return true;
        }
        if(id == R.id.changeProcess) {


            new LoginDataDAO(getBaseContext()).editIdTipoProceso(0);
            Intent intent = new Intent(getBaseContext(), ActivityPostloader.class);
            startActivity(intent);
            finish();

            /*switch (new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()) {
                case 1:
                    if (new RecepcionDAO(getBaseContext()).listAll().size() > 0) {
                        if (new RecepcionDAO(getBaseContext()).getEditing() == null) {
                            Toast.makeText(getBaseContext(), "   ¡ Aun no sincroniza\ntodas sus Recepciones !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "   ¡No puede cambiar proceso,\ntermine su Recepcion y sincronize!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        new LoginDataDAO(getBaseContext()).editIdTipoProceso(0);
                        Intent intent = new Intent(getBaseContext(), ActivityPostloader.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case 2:
                    if (new ProduccionDAO(getBaseContext()).listAll().size() > 0) {
                        if (new ProduccionDAO(getBaseContext()).getEditing() == null) {
                            Toast.makeText(getBaseContext(), "   ¡ Aun no sincroniza\ntodos sus Procesos !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "   ¡No puede cambiar proceso,\ntermine su Proceso y sincronize!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        new LoginDataDAO(getBaseContext()).editIdTipoProceso(0);
                        Intent intent = new Intent(getBaseContext(), ActivityPostloader.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case 3:
                    if (new DespachoDAO(getBaseContext()).listAll().size() > 0) {
                        if (new ProduccionDAO(getBaseContext()).getEditing() == null) {
                            Toast.makeText(getBaseContext(), "   ¡ Aun no sincroniza\ntodos sus Despachos !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "   ¡No puede cambiar proceso,\ntermine su Despacho y sincronize!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        new LoginDataDAO(getBaseContext()).editIdTipoProceso(0);
                        Intent intent = new Intent(getBaseContext(), ActivityPostloader.class);
                        startActivity(intent);
                        finish();
                    }
                    break;

                case 4:
                    if (new DescarteDAO(getBaseContext()).listAll().size() > 0) {
                        if (new DescarteDAO(getBaseContext()).getEditing() == null) {
                            Toast.makeText(getBaseContext(), "   ¡ Aun no sincroniza\ntodos sus Descartes !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "   ¡No puede cambiar proceso,\ntermine su Descarte y sincronize!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        new LoginDataDAO(getBaseContext()).editIdTipoProceso(0);
                        Intent intent = new Intent(getBaseContext(), ActivityPostloader.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
            }
            */
        }
        return super.onOptionsItemSelected(item);
    }
    private void showPopupRecomendacion(String mensaje){
        dialogClose = new Dialog(this);
        dialogClose.setContentView(R.layout.dialog_recomendaciones);
        Button btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close_dialog);
        ImageView iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);
        TextView tViewRecomendacion = dialogClose.findViewById(R.id.tViewRecomendacion);
        tViewRecomendacion.setText(mensaje);
        iViewDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose.dismiss();
            }
        });
        btnDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose.dismiss();
            }
        });

        dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogClose.show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean isFrame = false;
        Intent i = null;

        boolean isUpload = false;
        boolean isDownload = false;
/*
        if (id == R.id.upload) {
            //comprobar internet
            isUpload = true;
        }
*/
        if (!(isUpload || isDownload)) {
            if (isFrame) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();
            } else {
                startActivity(i);
            }
        } else {
            isConnectedToInternetToUpdate(isDownload ? "down" : "up");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    void isConnectedToInternetToUpdate(final String caso) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                //verificamos si tenemos internet
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    // Si hay conexión a Internet en este momento
                    //Toast.makeText(getBaseContext(),"Conectando...",Toast.LENGTH_SHORT).show();
                    switch (caso) {
                        case "up":

                            switch (new LoginDataDAO(getBaseContext()).verficarLogueo().getIdTipoProceso()){
                                case 1:
                                    if (new RecepcionDAO(getBaseContext()).listarNoEditable().size() > 0) {
                                        startActivity(
                                                new Intent(getBaseContext(), ActivityUpload.class)
                                        );
                                        finish();
                                    } else {
                                        Toast.makeText(getBaseContext(), "No hay Recepciones para sincronizar",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    break;

                                case 2:
                                    if (new ProduccionDAO(getBaseContext()).listarNoEditable().size() > 0) {
                                        startActivity(
                                                new Intent(getBaseContext(), ActivityUpload.class)
                                        );
                                        finish();
                                    } else {
                                        Toast.makeText(getBaseContext(), "No hay Producciones para sincronizar",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    break;

                                case 3:
                                    if (new DespachoDAO(getBaseContext()).listarNoEditable().size() > 0) {
                                        startActivity(
                                                new Intent(getBaseContext(), ActivityUpload.class)
                                        );
                                        finish();
                                    } else {
                                        Toast.makeText(getBaseContext(), "No hay Despachos para sincronizar",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    break;

                                case 4:
                                    if (new DescarteDAO(getBaseContext()).listarNoEditable().size() > 0) {
                                        startActivity(
                                                new Intent(getBaseContext(), ActivityUpload.class)
                                        );
                                        finish();
                                    } else {
                                        Toast.makeText(getBaseContext(), "No hay Descartes para sincronizar",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    break;
                            }




                            break;
                        case "down":
  /*
                            startActivity(
                                    new Intent(getBaseContext(), ActivityUpdate.class)
                            );
                            finish();
                            */
                            break;

                    }
                } else {
                    Toast.makeText(getBaseContext(), "Comprueba tu conexión a internet.\n" +
                                    "Inténtalo nuevamente.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        }, 500);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
