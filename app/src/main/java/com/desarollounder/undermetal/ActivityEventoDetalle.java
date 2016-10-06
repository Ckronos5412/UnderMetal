package com.desarollounder.undermetal;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class ActivityEventoDetalle extends AppCompatActivity {

    RequestQueue requestQueue;
    String showUrl = "http://undermetal.esy.es/android/getEventoId.php?IdEvento=";

    TextView txtFecha1, txtFecha2;
    TextView txtDescrip1, txtDescrip2;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalle);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("IdEvento");

        txtDescrip1 = (TextView)findViewById(R.id.txtDescripcion1);
        txtFecha1 = (TextView)findViewById(R.id.txtFecha1);

        txtDescrip2 = (TextView)findViewById(R.id.txtDescripcion2);
        txtFecha2 = (TextView)findViewById(R.id.txtFecha2);

        img = (ImageView) findViewById(R.id.img_Evento);

        cargaDatos(id);

        Animation moving = AnimationUtils.loadAnimation(this,R.anim.move);
        Animation fade_in = AnimationUtils.loadAnimation(this,R.anim.scale);
        moving.reset();

        txtFecha1.startAnimation(fade_in);
        txtFecha2.startAnimation(moving);
        txtDescrip1.startAnimation(fade_in);
        txtDescrip2.startAnimation(moving);
    }

    private void cargaDatos(String id) {
        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrl + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

                try {
                    JSONObject evento = new JSONObject(response.toString());
                    txtFecha1.setText("Fecha:");
                    txtDescrip1.setText("Descripción:");
                    txtFecha2.setText(evento.getJSONObject("Evento").getString("fechaEvento"));
                    txtDescrip2.setText(evento.getJSONObject("Evento").getString("descripcionEvento"));
                    Glide.with(getApplicationContext())
                            .load("http://undermetal.esy.es/resourses/images/evento/" +
                                    evento.getJSONObject("Evento").getString("IdEvento") + ".jpg")
                            .into(img);

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
}
