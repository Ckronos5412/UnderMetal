package com.desarollounder.undermetal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityLocalDetalle extends AppCompatActivity {

    TextView txtNombre, txtDir;
    ImageView imgLocal;

    //Local
    RequestQueue requestQueue;
    String showUrlLocal = "http://undermetal.esy.es/android/getLocalId.php?IdLocal=";


    //Eventos
    private RecyclerView rv;
    private AdapterEventoAll adapterEventoAll;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<clsEvento> eventos = new ArrayList<>();
    String showUrlEvento = "http://undermetal.esy.es/android/getEventoIdLocal.php?IdLocal=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_detalle);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("IdLocal");

        txtNombre = (TextView) findViewById(R.id.txt_nombre_local);
        txtDir = (TextView) findViewById(R.id.txt_dir_local);
        imgLocal = (ImageView) findViewById(R.id.imgLocal);

        rv=(RecyclerView) findViewById(R.id.rv_local_detalle);

        gridLayoutManager = new GridLayoutManager(this,1);
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);

        cargaDatosEventos(id, new CallBack() {
            @Override
            public void onSuccess(ArrayList<clsEvento> eventos) {
                // Do Stuff
                adapterEventoAll = new AdapterEventoAll(this, eventos);
                rv.setAdapter(adapterEventoAll);
            }

            @Override
            public void onFail(String msg) {
                // Do Stuff
            }
        });

        Log.d("Test: ",eventos.get(0).getDescripcionEvento());
    }

    private void cargaDatosLocal(String id) {
        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrlLocal + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

                try {
                    JSONObject local = new JSONObject(response.toString());
                    txtNombre.setText(local.getJSONObject("Local").getString("nombreLocal"));
                    txtDir.setText(local.getJSONObject("Local").getString("dirLocal"));
                    Glide.with(getApplicationContext())
                            .load("http://undermetal.esy.es/resourses/images/local/" +
                                    local.getJSONObject("Local").getString("IdLocal") + ".jpg")
                            .into(imgLocal);

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

    private void cargaDatosEventos(String id, CallBack onCallBack){
        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrlEvento + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

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