package com.mora.gonzalez.javier.softcuatro.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mora.gonzalez.javier.softcuatro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdaptadorJSON extends BaseAdapter {
    private Context context;
    private JSONArray data;
    private int layout;
    public AdaptadorJSON(Context context, int layout, JSONArray data){
        this.context = context;
        this.layout = layout;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return data.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vistaPersonal = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        vistaPersonal = layoutInflater.inflate(R.layout.campos_pagos, null);
        //Para poder obtener lo que hay en el arrayjson
        JSONObject jsonTmp = null;
        try{
            jsonTmp = data.getJSONObject(position);
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Obtenemos los datos
        String cuentaX = jsonTmp!=null?jsonTmp.optString("cuenta"):"#";
        String nombreCompleto = jsonTmp!=null?jsonTmp.optString("nombre"):"#";;
        String monto = jsonTmp!=null?jsonTmp.optString("monto"):"#";;
        System.out.println("********cuenta: "+cuentaX);
        TextView cuentaLlenar = vistaPersonal.findViewById(R.id.txtTipoCuenta),
                nombreCompletoLlenar = vistaPersonal.findViewById(R.id.txtnombreCompleto),
                montoPagarLlenar = vistaPersonal.findViewById(R.id.txtMonto);
        cuentaLlenar.setText(cuentaX);
        nombreCompletoLlenar.setText(nombreCompleto);
        montoPagarLlenar.setText(monto);



        return vistaPersonal;
    }
}

