package com.mora.gonzalez.javier.softcuatro.archivo;

import android.content.Context;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mora.gonzalez.javier.softcuatro.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoPDF {
    private final String NOMBRE_DIRECTORIO = "pdfs";
    private String nombreArchivo;
    private String tipoPago;
    private int numeroRegistros;
    private ArrayList<String[]> dataSQL;
    private Context context;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private boolean itsOK;

    private JSONArray jsonArray;

    public ArchivoPDF(Context context, String nombreArchivo, String tipoPago){
        this.context = context;
        this.nombreArchivo = nombreArchivo;
        this.tipoPago = tipoPago;
    }
    public ArchivoPDF(Context context, String nombreArchivo, String tipoPago, int numeroRegistros){
        this.context = context;
        this.nombreArchivo = nombreArchivo;
        this.tipoPago = tipoPago;
        this.numeroRegistros = numeroRegistros;
    }
    public void generarPFDWebServiceHilo(final String direccionM){

        System.out.println("Primera linea");
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String direccion = direccionM;

                        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, direccion, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        System.out.println("Despues");
                                        System.out.println(response);
                                        Document documento = new Document();
                                        String mensajeUsuario = "";
                                        try {
                                            File file = crearArchivo(nombreArchivo);
                                            if(file != null && file.getAbsolutePath() != null){
                                                FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
                                                PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);
                                                documento.open();
                                                documento.add(new Paragraph("Pagos: "+ tipoPago + "\n\n"));
                                                JSONArray dataBD = response.getJSONArray("pago");
                                                for(int i=0; i<dataBD.length(); ++i){
                                                    documento.add(new Paragraph("ID pago: " + dataBD.getJSONObject(i).getString("id")));
                                                    documento.add(new Paragraph("Cuenta: " + dataBD.getJSONObject(i).getString("cuenta")));
                                                    documento.add(new Paragraph("Nombre: " + dataBD.getJSONObject(i).getString("nombre")));
                                                    documento.add(new Paragraph("Monto: " + dataBD.getJSONObject(i).getString("monto")));

                                                }
                                                mensajeUsuario = "Archivo creado con exito";

                                            }
                                        } catch (JSONException | FileNotFoundException | DocumentException e) {
                                            mensajeUsuario = "Algo salio mal :/";
                                            e.printStackTrace();
                                        } finally {
                                            documento.close();
                                        }
                                        Toast.makeText(context, mensajeUsuario, Toast.LENGTH_SHORT).show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, "Vaya, no se creo el archivo :(", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(jsonObjectRequest);
                    }
                }
        ).start();

    }
    public boolean generarPFDWebService(String direccion){
        itsOK  = false;
        System.out.println("Primera linea");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, direccion, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Despues");
                        System.out.println(response);
                        Document documento = new Document();
                        try {
                            File file = crearArchivo(nombreArchivo);
                            if(file != null && file.getAbsolutePath() != null){
                                FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
                                PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);
                                documento.open();
                                documento.add(new Paragraph("Pagos: "+ tipoPago + "\n\n"));
                                JSONArray dataBD = response.getJSONArray("pago");
                                for(int i=0; i<dataBD.length(); ++i){
                                    documento.add(new Paragraph("ID pago: " + dataBD.getJSONObject(i).getString("id")));
                                    documento.add(new Paragraph("Cuenta: " + dataBD.getJSONObject(i).getString("cuenta")));
                                    documento.add(new Paragraph("Nombre: " + dataBD.getJSONObject(i).getString("nombre")));
                                    documento.add(new Paragraph("Monto: " + dataBD.getJSONObject(i).getString("monto")));

                                }
                                Toast.makeText(context, "creado con exito", Toast.LENGTH_SHORT).show();
                                itsOK = true;
                            }
                        } catch (JSONException | FileNotFoundException | DocumentException e) {
                            e.printStackTrace();
                        } finally {
                            documento.close();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return itsOK;
    }
    public boolean generarPDF(String tipoDePago){
        boolean var = false;
        Document documento = new Document();

        try{
            File file = crearArchivo(this.nombreArchivo);
            if(file != null && file.getAbsolutePath() != null){
                FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
                PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);
                documento.open();
                documento.add(new Paragraph("Pagos: "+ tipoPago + "\n\n"));
                for (int i = 0; i < numeroRegistros; i++) {
                    documento.add(new Paragraph("ID pago: " + this.dataSQL.get(i)[0]));
                    documento.add(new Paragraph("Cuenta: " + this.dataSQL.get(i)[1]));
                    documento.add(new Paragraph("Nombre: " + this.dataSQL.get(i)[2]));
                    documento.add(new Paragraph("monto" + this.dataSQL.get(i)[3]));
                    documento.add(new Paragraph("\n\n\n"));
                }
                var = true;
            }

        }catch (DocumentException | IOException e){
            e.printStackTrace();
        } finally {
            documento.close();
        }
        return var;
    }
    public File crearArchivo(String nombreFichero){
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null) {
            fichero = new File(ruta, nombreFichero);
        }
        return fichero;
    }
    public File getRuta(){
        File ruta = null;
        boolean comprobacion = true;
        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //this.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
            ruta = new File(
                    this.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),
                    NOMBRE_DIRECTORIO
            );
            if(ruta!=null){
                comprobacion = ruta.mkdir();
                if(!comprobacion){
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }
    public void setNombreArchivo(String nombreArchivo){
        this.nombreArchivo = nombreArchivo;
    }
    public  String getNombreArchivo(){
        return this.nombreArchivo;
    }
    public void setDataSQL(ArrayList<String[]>dataSQL){
        this.dataSQL = dataSQL;
    }
    public ArrayList<String[]> getDataSQL(){
        return this.dataSQL;
    }
}
