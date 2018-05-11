package com.piskas.prueba1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetalleActivity extends AppCompatActivity {
    private TextView lblRut;
    private TextView lblNombre;
    private TextView lblEdad;
    private TextView lblValidaHasta;
    private CheckBox chkSuscripcion;
    private Button btnPagar;
    private Button btnConfirmar;
    private Button btnCerrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // References ==============================================================================
        lblRut = (TextView) findViewById(R.id.lblRut);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblEdad = (TextView) findViewById(R.id.lblEdad);
        lblValidaHasta = (TextView) findViewById(R.id.lblValidaHasta);
        chkSuscripcion = (CheckBox) findViewById(R.id.chkSuscripcion);
        btnPagar = (Button) findViewById(R.id.btnPagar);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);


        // Get Extras ==============================================================================
        Bundle bundle = getIntent().getExtras();


        // Se Valida Si Vienen Extras ==============================================================
        if (bundle != null) {
            String rut = bundle.getString("rut");
            String nombre = bundle.getString("nombre");
            String apellido = bundle.getString("apellido");
            int edad = bundle.getInt("edad");

            lblRut.setText(": " + rut);
            lblNombre.setText(": " + nombre + " " + apellido);
            lblEdad.setText(": " + edad + " años");

            Calendar calDateNow = Calendar.getInstance(new Locale("es", "CL"));
            Date dateNow = new Date();
            calDateNow.setTime(dateNow);
            calDateNow.add(Calendar.MONTH, 3);
            dateNow.setTime(calDateNow.getTimeInMillis());

            SimpleDateFormat sdfDate = new SimpleDateFormat(": dd/MM/yyyy");
            lblValidaHasta.setText(sdfDate.format(dateNow).toString());

        //} else {
        //    Toast.makeText(this, "Sin Extras", Toast.LENGTH_SHORT).show();
        }


        // Set Listeners ===========================================================================
        chkSuscripcion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnPagar.setEnabled(!chkSuscripcion.isChecked());
                btnConfirmar.setEnabled(chkSuscripcion.isChecked());

                int meses = chkSuscripcion.isChecked() ? 3 : 12;

                Calendar calDateNow = Calendar.getInstance(new Locale("es", "CL"));
                Date dateNow = new Date();
                calDateNow.setTime(dateNow);
                calDateNow.add(Calendar.MONTH, meses);
                dateNow.setTime(calDateNow.getTimeInMillis());

                SimpleDateFormat sdfDate = new SimpleDateFormat(": dd/MM/yyyy");
                lblValidaHasta.setText(sdfDate.format(dateNow).toString() + (meses == 12 ? " (1 año)" : ""));
            }
        });
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cliente Ingresado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cliente Ingresado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
