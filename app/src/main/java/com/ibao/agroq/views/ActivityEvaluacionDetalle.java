package com.ibao.agroq.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibao.agroq.R;
import com.ibao.agroq.helpers.adapters.RViewAdapterCriterioDetalle;
import com.ibao.agroq.models.dao.CriterioDetalleDAO;
import com.ibao.agroq.models.dao.EvaluacionDetalleDAO;
import com.ibao.agroq.models.vo.entitiesInternal.CriterioDetalleVO;
import com.ibao.agroq.models.vo.entitiesInternal.EvaluacionDetalleVO;

import java.util.ArrayList;
import java.util.List;

public class ActivityEvaluacionDetalle extends AppCompatActivity {

    private static RecyclerView rViewCriterioDetalle;
    private static EvaluacionDetalleVO evaluacionDetalleVO;
    private static List<CriterioDetalleVO> criterioDetalleVOList;

    private static TextView tViewCantidadTotal;

    private static TextView tViewPorcentaje;

    private static TextView tView_total;

    private static TextView tView_twoPoints;

    public static final int REQUEST_FOTO=222;
    private  RViewAdapterCriterioDetalle rViewAdapterCriterioDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_detalle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rViewCriterioDetalle = (RecyclerView) findViewById(R.id.rViewCriterioDetalle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                //  initSpruce();
            }
        };

        rViewCriterioDetalle.setLayoutManager(linearLayoutManager);


        final View activityRootView = findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isTyping = false;
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    isTyping = true;

                } else {
                    //to make sure this will call only once when keyboard is hide.
                    if (isTyping) {

                        isTyping = false;
                    }
                }
            }
        });

        tViewCantidadTotal = (TextView) findViewById(R.id.tViewCantidadTotal);
        tViewPorcentaje = (TextView) findViewById(R.id.tViewPorcentaje);
        tView_total = (TextView) findViewById(R.id.tView_total);
        tView_twoPoints = (TextView) findViewById(R.id.tView_twoPoints);

        Bundle b = getIntent().getExtras();
        evaluacionDetalleVO = new EvaluacionDetalleDAO(getBaseContext()).consultarByid(b.getInt("idEvaDe"));
        criterioDetalleVOList= new ArrayList<>();
        criterioDetalleVOList = (new CriterioDetalleDAO(getBaseContext()).listByIdEvaluacionDetalle(evaluacionDetalleVO.getId()));
        //Toast.makeText(getBaseContext(),"cant "+criterioDetalleVOList.size(),Toast.LENGTH_LONG).show();
        setTitle(evaluacionDetalleVO.getNameEvaluacion());


                rViewAdapterCriterioDetalle = new RViewAdapterCriterioDetalle(
                        ActivityEvaluacionDetalle.this,
                        criterioDetalleVOList,
                        rViewCriterioDetalle,
                        true,
                        tViewCantidadTotal,
                        tViewPorcentaje,
                        tView_total,
                        tView_twoPoints
                );
                rViewCriterioDetalle.setAdapter(rViewAdapterCriterioDetalle);

    }
    //hacer q  el edit  text pieda el foco cando se preciosne  en toro lado
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("sss", "dispatchTouchEvent: ");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)ev.getRawX();
                int rawY = (int)ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onSupportNavigateUp() {
        // super.onSupportNavigateUp();
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);

        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Toast.makeText(getBaseContext(),String.valueOf(requestCode),Toast.LENGTH_SHORT).show();
        switch (requestCode) {

            case REQUEST_FOTO:

                criterioDetalleVOList = new CriterioDetalleDAO(getBaseContext()).listByIdEvaluacionDetalle(evaluacionDetalleVO.getId());
                rViewAdapterCriterioDetalle = new RViewAdapterCriterioDetalle(
                        ActivityEvaluacionDetalle.this,
                        criterioDetalleVOList,
                        rViewCriterioDetalle,
                        true,
                        tViewCantidadTotal,
                        tViewPorcentaje,
                        tView_total,
                        tView_twoPoints
                );
                rViewCriterioDetalle.setAdapter(rViewAdapterCriterioDetalle);
                //rViewAdapterCriterioDetalle.notifyDataSetChanged();
                break;
        }

    }
}
