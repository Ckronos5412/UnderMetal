package com.desarollounder.undermetal;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentEventoAll extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView rv;
    private AdapterEventoAll adapterEventoAll;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<clsEvento> eventos = new ArrayList<>();

    RequestQueue requestQueue;
    String showUrl = "http://undermetal.esy.es/android/getEventos.php";

    public FragmentEventoAll() {
        // Required empty public constructor
    }

    public static FragmentEventoAll newInstance(String param1, String param2) {
        FragmentEventoAll fragment = new FragmentEventoAll();
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
        View view = inflater.inflate(R.layout.fragment_evento_all, container, false);

        rv=(RecyclerView) view.findViewById(R.id.rv);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);

        cargaDatos(0, new CallBack() {
            @Override
            public void onSuccess(ArrayList<clsEvento> eventos) {
                // Do Stuff
                adapterEventoAll = new AdapterEventoAll(getContext(),eventos);
                rv.setAdapter(adapterEventoAll);
            }

            @Override
            public void onFail(String msg) {
                // Do Stuff
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

    private void cargaDatos(int valor, final CallBack onCallBack){
        final ProgressDialog progres = new ProgressDialog(getActivity());
        progres.setMessage("Cargando...");
        progres.show();

        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

                progres.dismiss();

                try {
                    JSONArray locales = response.getJSONArray("Evento");
                    for (int i = 0; i < locales.length(); i++) {
                        JSONObject local = locales.getJSONObject(i);

                        eventos.add(new clsEvento(local.getInt("IdEvento")
                                ,local.getString("descripcionEvento")
                                ,local.getString("fechaEvento")
                                ,""));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                onCallBack.onSuccess(eventos);

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

