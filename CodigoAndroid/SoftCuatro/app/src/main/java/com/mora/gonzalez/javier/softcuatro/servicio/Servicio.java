package com.mora.gonzalez.javier.softcuatro.servicio;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Servicio implements Response.ErrorListener, Response.Listener<JSONObject> {
    private Context contexto;
    private String cuenta, nombre, monto;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    private ArrayList<String[]> datos;
    private JSONArray jsonArray;

    public Servicio(Context contexto){
        this.contexto = contexto;
    }

    public Servicio(Context contexto, String cuenta, String nombre, String monto) {
        this.contexto = contexto;
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.monto = monto;
    }
    public void ejecucionDeServicio(String URL){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(contexto, "Exito", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contexto, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            protected Map<String, String>  getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Content-Type", "application/x-www-form-urlencoded");

                parametros.put("cuenta", cuenta);
                parametros.put("nombre", nombre);
                parametros.put("monto", monto);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(contexto);
        //envamos la solicitud
        requestQueue.add(stringRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
