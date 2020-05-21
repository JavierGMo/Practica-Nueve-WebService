package com.mora.gonzalez.javier.softcuatro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mora.gonzalez.javier.softcuatro.adaptador.AdaptadorJSON;
import com.mora.gonzalez.javier.softcuatro.archivo.ArchivoPDF;
import com.mora.gonzalez.javier.softcuatro.bd.PagosClientes;
import com.mora.gonzalez.javier.softcuatro.hilo.HiloPDF;
import com.mora.gonzalez.javier.softcuatro.servicio.Servicio;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RadioGroup opcionesPagos;
    private EditText correoTxt, noTarjetaTxt, nombreCompletoTxt, montoTxt;
    private Button btnPagarJ, btnPDFPagoDigital, btnPDFPagoDirecto, btnActualizarPago;
    private LinearLayout layoutALlenar;
    private ListView listaDeLosPagos;
    private SQLiteDatabase bdPagos;
    private ArrayList<String[]> data;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private AdaptadorJSON miAdaptador;
    private CharSequence[] opcionesAlert = {"Pago en banco", "Pago de PayPal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //BD SQLite
        PagosClientes pagosXClientes = new PagosClientes(this, "PagosChidos", null, 1);
        //Radio Group
        opcionesPagos = findViewById(R.id.radioGroupPagos);
        //ListView
        listaDeLosPagos = findViewById(R.id.listaPagos);
        //Layout que se tiene que llenar
        layoutALlenar = findViewById(R.id.linearLayoutCamposDinamicos);
        //Edit Texts de pagos
        correoTxt = findViewById(R.id.cuentaPayPal);
        noTarjetaTxt = findViewById(R.id.noCuentaBanco);
        nombreCompletoTxt = findViewById(R.id.nombreCompleto);
        montoTxt = findViewById(R.id.montoAPagar);
        //Boton para pagar
        btnPagarJ = findViewById(R.id.buttonDepositar);
        //Botones para hacer los pdfs de los pagos
        btnPDFPagoDigital = findViewById(R.id.btn_pago_digital);
        btnPDFPagoDirecto = findViewById(R.id.btn_pago_efectivo);
        btnActualizarPago = findViewById(R.id.btnxmlactualizar);
        //Iniciar la base para los pagos
        bdPagos = pagosXClientes.getWritableDatabase();


        /*Opciones de radioGroup*/

        opcionesPagos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int check) {
                if(check == R.id.radioPagoBanco){
                    //Limpiar los campos y mostrarlos
                    limpiarCampos();
                    mostrarCampos(View.GONE, View.VISIBLE);
                    listaDeLosPagos.setVisibility(View.GONE);


                    Toast.makeText(MainActivity.this, "Pago en banco", Toast.LENGTH_SHORT).show();
                }else if(check == R.id.radioMostrarPagoBanco){
                    limpiarCampos();
                    desparecerCampos();
                    //select numcuenta, nombrecompleto, monto from PagosBanco
                    //mostrarEnElActivity("select numcuenta, nombrecompleto, monto from PagosBanco");
                    mostrarConWebService("http://localhost:80/webservicepagos/crudpagobanco/consultarpagos.php");
                    Toast.makeText(MainActivity.this, "Pagos directos de bancos", Toast.LENGTH_SHORT).show();
                }else if(check == R.id.radioPagoPayPal){
                    //Limpiar los campos y mostrarlos
                    limpiarCampos();
                    mostrarCampos(View.VISIBLE, View.GONE);
                    listaDeLosPagos.setVisibility(View.GONE);

                    Toast.makeText(MainActivity.this, "Pago en PayPal", Toast.LENGTH_SHORT).show();
                }else if(check == R.id.radioMostarPagoPayPal){
                    limpiarCampos();
                    desparecerCampos();
                    //select correo, nombrecompleto, monto from PagosPayPal
                    //mostrarEnElActivity("select correo, nombrecompleto, monto from PagosPayPal");
                    mostrarConWebService("http://localhost:80/webservicepagos/crudpaypal/consultarpagospay.php");
                    Toast.makeText(MainActivity.this, "Pagos en PayPal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Evento para el boton de pago
        btnPagarJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noTarjetaTxt.getText() != null && !noTarjetaTxt.getText().toString().isEmpty()) {
                    Servicio servicioWeb = new Servicio(
                            MainActivity.this,
                            noTarjetaTxt.getText().toString(),
                            nombreCompletoTxt.getText().toString(),
                            montoTxt.getText().toString()
                            );
                    servicioWeb.ejecucionDeServicio("http://localhost:80/webservicepagos/crudpagobanco/crearpago.php");
                    /*
                    para sqlite
                    ContentValues cv = new ContentValues();
                    cv.put("numcuenta", noTarjetaTxt.getText().toString());
                    cv.put("nombrecompleto", nombreCompletoTxt.getText().toString());
                    cv.put("monto", montoTxt.getText().toString());
                    System.out.println(noTarjetaTxt.getText().toString());
                    //pagosEnelBanc
                    bdPagos.insert("PagosBanco", null, cv);*/
                    Toast.makeText(MainActivity.this, "Hizo un pago", Toast.LENGTH_SHORT).show();

                } else if (correoTxt.getText() != null && !correoTxt.getText().toString().isEmpty()) {
                    Servicio servicioWeb = new Servicio(
                            MainActivity.this,
                            correoTxt.getText().toString(),
                            nombreCompletoTxt.getText().toString(),
                            montoTxt.getText().toString()
                    );
                    servicioWeb.ejecucionDeServicio("http://localhost:80/webservicepagos/crudpaypal/crearpagopay.php");

                    Toast.makeText(MainActivity.this, "Hizo un pago en PayPal", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //Botones para imprimir los pdfs
        //Boton para pagos directos
        btnPDFPagoDirecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String[]> consultaSQLite;
                //PagosBanco

                ArchivoPDF generaPDF = new ArchivoPDF(MainActivity.this,"PagosbancosWeb.pdf", "Pago directo");
                new HiloPDF("http://localhost:80/webservicepagos/crudpagobanco/consultarpagos.php", "PagosbancosWeb.pdf", "Pago banco", MainActivity.this)
                .execute(generaPDF);
                //generaPDF.generarPFDWebServiceHilo("http://localhost:80/webservicepagos/crudpagobanco/consultarpagos.php");


            }
        });
        btnPDFPagoDigital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String[]> consultaSQLite;
                //PagosBanco
                //consultaSQLite = obtenerDatosSQL("PagosPayPal");
                ArchivoPDF pdf = new ArchivoPDF(MainActivity.this,"PagosPayPalWeb.pdf", "Pago paypal");
                //pdf.setDataSQL(consultaSQLite);
                new HiloPDF("http://localhost:80/webservicepagos/crudpaypal/consultarpagospay.php", "PagosPaypalWeb.pdf", "Pago PayPal", MainActivity.this)
                        .execute(pdf);
                //pdf.generarPFDWebServiceHilo("http://localhost:80/webservicepagos/crudpaypal/consultarpagospay.php");
                /*if(pdf.generarPFDWebService("http://localhost:80/webservicepagos/crudpaypal/consultarpagospay.php")){

                   Toast.makeText( MainActivity.this,"Archivo creado con exito, main", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText( MainActivity.this,"Archivo no creado, main", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        btnActualizarPago.setOnClickListener(new View.OnClickListener() {
            Intent moverAActualizar = new Intent(MainActivity.this, ActualizarPagoActivity.class);
            String valorPago = "";
            String nombrePago = "";
            @Override
            public void onClick(View v) {
                builder.setTitle("Tipo de pago");
                builder.setItems(opcionesAlert, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        /*
                        if(i == 0){
                            valorPago = "updatebanco.php";
                        }else if(i == 1){
                            valorPago = "updatepay.php";
                        }*/
                        //otra opcion es darle el valorPago la url para realizar la actualizacion
                        moverAActualizar.putExtra("opcion", i);
                        startActivity(moverAActualizar);
                    }
                });
                AlertDialog alerta = builder.create();
                alerta.show();
                /*Intent moverAActualizar = new Intent(MainActivity.this, ActualizarPagoActivity.class);
                startActivity(moverAActualizar);*/
            }
        });
    }
    private void limpiarCampos(){
        this.correoTxt.setText("");
        this.noTarjetaTxt.setText("");
        this.nombreCompletoTxt.setText("");
        this.montoTxt.setText("");
    }
    private void mostrarCampos(int visibilidadCero, int visibilidadUno){

        correoTxt.setVisibility(visibilidadCero);
        noTarjetaTxt.setVisibility(visibilidadUno);
        nombreCompletoTxt.setVisibility(View.VISIBLE);
        montoTxt.setVisibility(View.VISIBLE);
        btnPagarJ.setVisibility(View.VISIBLE);
    }
    private void desparecerCampos(){
        correoTxt.setVisibility(View.GONE);
        noTarjetaTxt.setVisibility(View.GONE);
        nombreCompletoTxt.setVisibility(View.GONE);
        montoTxt.setVisibility(View.GONE);
        btnPagarJ.setVisibility(View.GONE);
    }
    private ArrayList<String[]> obtenerDatosSQL(String tabla){
        ArrayList<String[]>datos = null;
        Cursor c = bdPagos.rawQuery("select * from "+tabla+";", null);
        if(c != null){
            if(c.moveToFirst()){
                datos = new ArrayList<>();
                do{
                    datos.add(
                            new String[]{
                                    c.getString(0),
                                    c.getString(1),
                                    c.getString(2),
                                    c.getString(3)
                            }
                    );
                }while (c.moveToNext());
            }
            c.close();
        }
        return datos;
    }
    public void mostrarEnElActivity(String consulta){
        Cursor c = bdPagos.rawQuery(consulta, null);
        if(c != null){
            if(c.moveToFirst()){
                data = new ArrayList<>();
                do{
                    data.add(
                            new String[]{
                                    c.getString(0),
                                    c.getString(1),
                                    c.getString(2)
                            }
                    );
                }while (c.moveToNext());
            }else{
                data = null;
            }
            c.close();
        }

        if(data != null){
            AdaptadorPagos adaptadorPagos = new AdaptadorPagos(MainActivity.this, R.layout.campos_pagos, data);
            listaDeLosPagos.setAdapter(adaptadorPagos);
            listaDeLosPagos.setVisibility(View.VISIBLE);
        }
    }
    private void mostrarConWebService(String direccion){
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, direccion, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("**********Response: "+response);

                        miAdaptador = new AdaptadorJSON(MainActivity.this, R.layout.campos_pagos, response.optJSONArray("pago"));
                        listaDeLosPagos.setAdapter(miAdaptador);
                        listaDeLosPagos.setVisibility(View.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);
    }
}
