package com.mora.gonzalez.javier.softcuatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mora.gonzalez.javier.softcuatro.servicio.Servicio;

public class ActualizarPagoActivity extends AppCompatActivity {
    private EditText nombreJ, cuentaJ, montoJ;
    private Button btnActualizarPagos;
    private Bundle parametros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pago);
        //Parametros
        parametros = this.getIntent().getExtras();

        //EditText
        nombreJ = findViewById(R.id.edtxtnombre);
        cuentaJ = findViewById(R.id.edtxtcuenta);
        montoJ = findViewById(R.id.edtxtmontox);
        //Boton
        btnActualizarPagos = findViewById(R.id.btnxactualizarweb);
        if(parametros.getInt("opcion") == 0){
            Toast.makeText(ActualizarPagoActivity.this, "Ingresa los datos de cuenta de banco", Toast.LENGTH_LONG).show();
        }else if(parametros.getInt("opcion") == 1){
            Toast.makeText(ActualizarPagoActivity.this, "Ingresa tus datos de cuenta de PayPal", Toast.LENGTH_LONG).show();
        }
        btnActualizarPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCampo(nombreJ) && validarCampo(cuentaJ) && validarCampo(montoJ)){
                    Servicio servicioActualizar = new Servicio(
                            ActualizarPagoActivity.this,
                            cuentaJ.getText().toString(),
                            nombreJ.getText().toString(),
                            montoJ.getText().toString());
                    if(parametros.getInt("opcion") == 0){
                        servicioActualizar.ejecucionDeServicio("http://localhost:80/webservicepagos/crudpagobanco/updatebanco.php");
                    }else if(parametros.getInt("opcion") == 1){
                        servicioActualizar.ejecucionDeServicio("http://localhost:80/webservicepagos/crudpaypal/updatepay.php");
                    }
                }else{
                    Toast.makeText(ActualizarPagoActivity.this, "Vaya no llenaste los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean validarCampo(EditText editText){return editText != null && editText.getText() != null && !editText.getText().toString().equals("");}
}
