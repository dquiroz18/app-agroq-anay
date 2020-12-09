package com.ibao.agroq.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.ibao.agroq.R;
import com.ibao.agroq.helpers.adapters.AdapterListDescarte;
import com.ibao.agroq.helpers.adapters.AdapterListDespacho;
import com.ibao.agroq.helpers.adapters.AdapterListProduccion;
import com.ibao.agroq.helpers.adapters.AdapterListRecepcion;
import com.ibao.agroq.models.dao.DescarteDAO;
import com.ibao.agroq.models.dao.DespachoDAO;
import com.ibao.agroq.models.dao.LoginDataDAO;
import com.ibao.agroq.models.dao.ProduccionDAO;
import com.ibao.agroq.models.dao.RecepcionDAO;
import com.ibao.agroq.models.vo.entitiesInternal.DescarteVO;
import com.ibao.agroq.models.vo.entitiesInternal.DespachoVO;
import com.ibao.agroq.models.vo.entitiesInternal.LoginDataVO;
import com.ibao.agroq.models.vo.entitiesInternal.ProduccionVO;
import com.ibao.agroq.models.vo.entitiesInternal.RecepcionVO;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentMain extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static private TextView tViewProccessNameCircle;
    static private TextView tViewProccessName;
    static private ListView lViewProcesos;
    static private List<Object> listProcesos;
    static private TextView tViewStatusNewProceso;

    private OnFragmentInteractionListener mListener;

    public FragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    int delayShowList = 30;

    @Override
    public void onStart() {
        super.onStart();

            lViewProcesos = (ListView) getView().findViewById(R.id.lViewItems);
            listProcesos = new ArrayList<>();
            tViewStatusNewProceso = getView().findViewById(R.id.tViewStatusRegistar);
            tViewProccessName = getView().findViewById(R.id.tViewProccessName);
            tViewProccessNameCircle = getView().findViewById(R.id.tViewProccessNameCircle);

            LoginDataVO loginDataVO =  new LoginDataDAO(getView().getContext()).verficarLogueo();

            tViewProccessName.setText(loginDataVO.getNameTypeProcess());
            tViewProccessNameCircle.setText(tViewProccessName.getText().toString());
        final Animation animBtn =
                android.view.animation.AnimationUtils.loadAnimation(getContext(), R.anim.press_btn);



        final ConstraintLayout cLAdd = (ConstraintLayout) getView().findViewById(R.id.cLayoutAddNew);

        Handler handler = new Handler();
        switch (loginDataVO.getIdTipoProceso()){
            case 1:
                cLAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(animBtn);
                        Handler handler = new Handler();
                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        cLAdd.setClickable(false);
                                        Intent intent = new Intent(getContext(), ActivityProceso.class);

                                        if(new RecepcionDAO(getContext()).getEditing()==null){
                                            RecepcionVO recepcionTemp = new RecepcionDAO(getContext()).intentarNuevo();
                                            intent.putExtra(getString(R.string.isEditable), true);
                                            intent.putExtra(getString(R.string.idProceso), recepcionTemp.getId());
                                        }else {
                                            RecepcionVO recepcionTemp = new RecepcionDAO(getContext()).intentarNuevo();
                                            intent.putExtra(getString(R.string.isEditable), true);
                                            intent.putExtra(getString(R.string.idProceso), recepcionTemp.getId());
                                        }
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                    }
                                },200
                        );
                    }
                });
                handler.postDelayed(new Runnable() {
                    public void run() {

                        listProcesos.clear();
                        RecepcionVO rTemp =new RecepcionDAO(getContext()).getEditing();
                        if(rTemp!=null){
                            tViewStatusNewProceso.setText(R.string.continuar);
                        }
                        for(Object o: new RecepcionDAO(getContext()).listAll()){
                            listProcesos.add((RecepcionVO)o);
                        }
                        AdapterListRecepcion adapterListRecepcion = new AdapterListRecepcion(getContext(),listProcesos,lViewProcesos);
                        lViewProcesos.setAdapter(adapterListRecepcion);
                        setListViewHeightBasedOnChildren(lViewProcesos);
                    }
                }, delayShowList);

                break;


            case 2:
                cLAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(animBtn);
                        Handler handler = new Handler();
                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        cLAdd.setClickable(false);
                                        Intent intent = new Intent(getContext(), ActivityProceso.class);

                                        if(new ProduccionDAO(getContext()).getEditing()==null){
                                            ProduccionVO temp = new ProduccionDAO(getContext()).intentarNuevo();
                                            intent.putExtra(getString(R.string.isEditable), true);
                                            intent.putExtra(getString(R.string.idProceso), temp.getId());
                                        }else {
                                            ProduccionVO temp = new ProduccionDAO(getContext()).intentarNuevo();
                                            intent.putExtra(getString(R.string.isEditable), true);
                                            intent.putExtra(getString(R.string.idProceso), temp.getId());
                                        }
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                    }
                                },200
                        );
                    }
                });

                handler.postDelayed(new Runnable() {
                    public void run() {
                        listProcesos.clear();
                        ProduccionVO pTemp =new ProduccionDAO(getContext()).getEditing();
                        if(pTemp!=null){
                            tViewStatusNewProceso.setText(R.string.continuar);
                        }
                        for(Object o: new ProduccionDAO(getContext()).listAll()){
                            listProcesos.add((ProduccionVO)o);
                        }
                        AdapterListProduccion adapterListProduccion = new AdapterListProduccion(getContext(),listProcesos,lViewProcesos);
                        lViewProcesos.setAdapter(adapterListProduccion);
                        setListViewHeightBasedOnChildren(lViewProcesos);
                    }
                }, delayShowList);
                break;
            case 3:
                cLAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(animBtn);
                        Handler handler = new Handler();
                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        cLAdd.setClickable(false);
                                        Intent intent = new Intent(getContext(), ActivityProceso.class);

                                        if(new DespachoDAO(getContext()).getEditing()==null){
                                            DespachoVO temp = new DespachoDAO(getContext()).intentarNuevo();
                                            intent.putExtra(getString(R.string.isEditable), true);
                                            intent.putExtra(getString(R.string.idProceso), temp.getId());
                                        }else {
                                            DespachoVO temp = new DespachoDAO(getContext()).intentarNuevo();
                                            intent.putExtra(getString(R.string.isEditable), true);
                                            intent.putExtra(getString(R.string.idProceso), temp.getId());
                                        }
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                    }
                                },200
                        );
                    }
                });
                handler.postDelayed(new Runnable() {
                    public void run() {

                        listProcesos.clear();
                        DespachoVO rTemp =new DespachoDAO(getContext()).getEditing();
                        if(rTemp!=null){
                            tViewStatusNewProceso.setText(R.string.continuar);
                        }
                        for(Object o: new DespachoDAO(getContext()).listAll()){
                            listProcesos.add((DespachoVO)o);
                            Log.d("FRAGMENT MAIN",""+String.valueOf(((DespachoVO) o).getIdCultivo()));
                        }
                        AdapterListDespacho adapterListDespacho = new AdapterListDespacho(getContext(),listProcesos,lViewProcesos);
                        lViewProcesos.setAdapter(adapterListDespacho);
                        setListViewHeightBasedOnChildren(lViewProcesos);
                    }
                }, delayShowList);

                break;
            case 4:
                cLAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(animBtn);
                        Handler handler = new Handler();
                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        cLAdd.setClickable(false);
                                        Intent intent = new Intent(getContext(), ActivityProceso.class);

                                        if(new DescarteDAO(getContext()).getEditing()==null){
                                            DescarteVO temp = new DescarteDAO(getContext()).intentarNuevo();
                                            intent.putExtra(getString(R.string.isEditable), true);
                                            intent.putExtra(getString(R.string.idProceso), temp.getId());
                                        }else {
                                            DescarteVO temp = new DescarteDAO(getContext()).intentarNuevo();
                                            intent.putExtra(getString(R.string.isEditable), true);
                                            intent.putExtra(getString(R.string.idProceso), temp.getId());
                                        }
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                    }
                                },200
                        );
                    }
                });
                handler.postDelayed(new Runnable() {
                    public void run() {

                        listProcesos.clear();
                        DescarteVO rTemp =new DescarteDAO(getContext()).getEditing();
                        if(rTemp!=null){
                            tViewStatusNewProceso.setText(R.string.continuar);
                        }
                        for(Object o: new DescarteDAO(getContext()).listAll()){
                            listProcesos.add((DescarteVO)o);
                        }
                        AdapterListDescarte adapterListDescarte = new AdapterListDescarte(getContext(),listProcesos,lViewProcesos);
                        lViewProcesos.setAdapter(adapterListDescarte);
                        setListViewHeightBasedOnChildren(lViewProcesos);
                    }
                }, delayShowList);

                break;


        }


    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec (listView.getWidth (), View.MeasureSpec.EXACTLY);
       // int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: ActivityUpdate argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
