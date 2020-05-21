package com.mora.gonzalez.javier.softcuatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPagos extends BaseAdapter {
    private Context context;
    private ArrayList<String[]> data;
    private int layout;
    AdaptadorPagos(Context context, int layout, ArrayList<String[]> data){
        this.context = context;
        this.layout = layout;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
        //Obtenemos los datos
        String cuentaX = this.data.get(position)[0];
        String nombreCompleto = this.data.get(position)[1];
        String monto = this.data.get(position)[2];
        TextView cuentaLlenar = vistaPersonal.findViewById(R.id.txtTipoCuenta),
                nombreCompletoLlenar = vistaPersonal.findViewById(R.id.txtnombreCompleto),
                montoPagarLlenar = vistaPersonal.findViewById(R.id.txtMonto);
        cuentaLlenar.setText(cuentaX);
        nombreCompletoLlenar.setText(nombreCompleto);
        montoPagarLlenar.setText(monto);



        return vistaPersonal;
    }
}
