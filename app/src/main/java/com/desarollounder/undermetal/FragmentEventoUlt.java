package com.desarollounder.undermetal;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentEventoUlt extends Fragment {
    private OnFragmentInteractionListener mListener;

    private RecyclerView rv;
    private AdapterEventoUlt adapterEventoUlt;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<clsEvento> eventos = new ArrayList<>();

    RequestQueue requestQueue;
    String showUrl = "http://undermetal.esy.es/android/getEventoIdCV.php?IdEvento=";

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FragmentEventoUlt() {
        // Required empty public constructor
    }

    public static FragmentEventoUlt newInstance(String param1, String param2) {
        FragmentEventoUlt fragment = new FragmentEventoUlt();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evento_ult, container, false);



        rv=(RecyclerView) view.findViewById(R.id.rv);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);

        cargaDatos(0, new CallBack() {
            @Override
            public void onSuccess(ArrayList<clsEvento> eventos) {
                // Do Stuff
                adapterEventoUlt = new AdapterEventoUlt(getContext(),eventos);
                rv.setAdapter(adapterEventoUlt);
            }

            @Override
            public void onFail(String msg) {
                // Do Stuff
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == eventos.size()-1){
                    cargaDatos(Integer.valueOf(eventos.get(eventos.size()-1).getId()),new CallBack() {
                        @Override
                        public void onSuccess(ArrayList<clsEvento> eventos) {

                            Log.d("ASD: ", String.valueOf(eventos.get(eventos.size()-1).getId()));
                            adapterEventoUlt.notifyDataSetChanged();
                        }

                        @Override
                        public void onFail(String msg) {
                            // Do Stuff
                        }
                    });
                }

            }
        });

        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    String img;
    private void cargaDatos(int valor, final CallBack onCallBack){

        final ProgressDialog progres = new ProgressDialog(getActivity());
        progres.setMessage("Cargando...");
        progres.show();

        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrl+valor, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

                progres.dismiss();

                try {
                    JSONArray locales = response.getJSONArray("Evento");
                    for (int i = 0; i < locales.length(); i++) {
                        JSONObject local = locales.getJSONObject(i);

                        if (local.getString("imagenEvento").equals("noImagen")){
                            img = "http://undermetal.esy.es/resourses/images/evento/"+ local.getInt("IdEvento") + ".jpg";
                        } else {
                            img = "http://undermetal.esy.es/resourses/images/evento/noencontrada.jpg";
                        }
                        eventos.add(new clsEvento(local.getInt("IdEvento")
                                ,local.getString("descripcionEvento")
                                ,local.getString("fechaEvento")
                                ,img));

                        onCallBack.onSuccess(eventos);

//                        Date date = new Date();
//                        try {
//                            date = format.parse(local.getString("fechaEvento"));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public interface CallBack {
        void onSuccess(ArrayList<clsEvento> eventos);

        void onFail(String msg);
    }
}
