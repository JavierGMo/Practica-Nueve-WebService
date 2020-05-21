package com.mora.gonzalez.javier.softcuatro.hilo;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mora.gonzalez.javier.softcuatro.archivo.ArchivoPDF;

public class HiloPDF extends AsyncTask<ArchivoPDF, Integer, Boolean> {

    private String direccion, nombreArchivo, tipoPago;

    private Context context;
    public HiloPDF(String direccion, String nombreArchivo, String tipoPago,  Context context){
        this.direccion = direccion;
        this.nombreArchivo = nombreArchivo;
        this.tipoPago = tipoPago;
        this.context = context;
    }
    @Override
    protected Boolean doInBackground(ArchivoPDF... archivoPDFS) {
        try {

            return archivoPDFS[0].generarPFDWebService(direccion);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(Boolean result) {
        if(result){
            Toast.makeText(context, "Archivo creado con exito, hilo", Toast.LENGTH_LONG).show();
        }else{
            //falso
            Toast.makeText(context, "Archivo no creado, hilo", Toast.LENGTH_LONG).show();
        }
    }
}
