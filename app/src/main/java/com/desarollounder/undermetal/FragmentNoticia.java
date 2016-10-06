package com.desarollounder.undermetal;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.ArrayList;


public class FragmentNoticia extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView rv;
    private AdapterNoticia adapterNoticia;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<clsNoticia> noticias = new ArrayList<>();

    RequestQueue requestQueue;
    String showUrl = "http://undermetal.esy.es/android/getNoticiaIdCV.php?IdNoticia=";

    public FragmentNoticia() {
        // Required empty public constructor
    }

    public static FragmentNoticia newInstance(String param1, String param2) {
        FragmentNoticia fragment = new FragmentNoticia();
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
        View view = inflater.inflate(R.layout.fragment_noticia, container, false);

        rv=(RecyclerView) view.findViewById(R.id.rv);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);

        cargaDatos(0, new CallBack() {
            @Override
            public void onSuccess(ArrayList<clsNoticia> noticias) {
                // Do Stuff
                adapterNoticia = new AdapterNoticia(getContext(),noticias);
                rv.setAdapter(adapterNoticia);
            }

            @Override
            public void onFail(String msg) {
                // Do Stuff
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == noticias.size()-1){
                    cargaDatos(Integer.valueOf(noticias.get(noticias.size()-1).getId()),new CallBack() {
                        @Override
                        public void onSuccess(ArrayList<clsNoticia> noticias) {

                            Log.d("ASD: ", String.valueOf(noticias.get(noticias.size()-1).getId()));
                            adapterNoticia.notifyDataSetChanged();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

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
                    JSONArray locales = response.getJSONArray("Noticia");
                    for (int i = 0; i < locales.length(); i++) {
                        JSONObject local = locales.getJSONObject(i);

                        noticias.add(new clsNoticia(local.getInt("IdNoticia"),local.getString("cuerpoNoticia")
                                , local.getString("cuerpoNoticia")));
                    }

                } catch (JSONException e) {
                    noticias.add(new clsNoticia(16,"No hay más datos",""));
                    e.printStackTrace();
                }

                onCallBack.onSuccess(noticias);

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
        void onSuccess(ArrayList<clsNoticia> noticias);

        void onFail(String msg);
    }
}
