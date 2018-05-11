package com.piskas.prueba1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText txtRut;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtFechaNacimiento;
    private Button btnEnviar;
    private Button btnCerrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References ==============================================================================
        txtRut = (EditText) findViewById(R.id.txtRut);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtFechaNacimiento = (EditText) findViewById(R.id.txtFechaNacimiento);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);


        // Set Listeners ===========================================================================
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Asignacion de Variables =========================================================
                int edad = 0;
                String strRut = txtRut.getText().toString();
                String strNombre = txtNombre.getText().toString();
                String strApellido = txtApellido.getText().toString();
                String strFechaNacimiento = txtFechaNacimiento.getText().toString();


                // Validacion de Vacios ============================================================
                if (strRut.trim().isEmpty())
                    txtRut.setError("Debe Ingresar Rut");


                if (strNombre.trim().isEmpty())
                    txtNombre.setError("Debe Ingresar Nombre");


                if( strApellido.trim().isEmpty())
                    txtApellido.setError("Debe Ingresar Apellido");


                if (strFechaNacimiento.trim().isEmpty()) {
                    txtFechaNacimiento.setError("Debe Ingresar Fecha");

                } else {
                    // Validacion de Formato de Fecha ==============================================
                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
                    sdfDate.setLenient(false);

                    Calendar calDateBirth = Calendar.getInstance(new Locale("es", "CL"));
                    Calendar calDateNow = Calendar.getInstance(new Locale("es", "CL"));

                    Date dateBirth = new Date();
                    Date dateNow = new Date();

                    int difMonth = 0;
                    int difDay = 0;

                    try {
                        dateBirth = sdfDate.parse(strFechaNacimiento);

                        calDateBirth.setTime(dateBirth);
                        calDateNow.setTime(dateNow);

                        edad = (calDateNow.get(Calendar.YEAR) - calDateBirth.get(Calendar.YEAR));
                        difMonth = (calDateNow.get(Calendar.MONTH) - calDateBirth.get(Calendar.MONTH));
                        difDay = (calDateNow.get(Calendar.DAY_OF_MONTH) - calDateBirth.get(Calendar.DAY_OF_MONTH));

                        if (difMonth < 0 || (difMonth == 0 && difDay < 0))
                            --edad;

                        // Validacion de Edad ======================================================
                        if (dateBirth.getTime() > dateNow.getTime()) {
                            txtFechaNacimiento.setError("La fecha de nacimiento debe ser menor o igual a hoy");

                        } else if(edad < 18 || edad > 150) {
                            txtFechaNacimiento.setError("Debe tener entre 18 y 150 años de edad");

                        } else {
                            // Validaciones Correctas, se envian datos =============================
                            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                            intent.putExtra("rut", strRut);
                            intent.putExtra("nombre", strNombre);
                            intent.putExtra("apellido", strApellido);
                            intent.putExtra("fechaNacimiento", strFechaNacimiento);
                            intent.putExtra("edad", edad);
                            startActivity(intent);

                            // Limpia EditTexts ====================================================
                            txtRut.setText("");
                            txtNombre.setText("");
                            txtApellido.setText("");
                            txtFechaNacimiento.setText("");
                        }

                    } catch (ParseException e) {
                        txtFechaNacimiento.setError("Problemas con el formato de la hora (dd/MM/yyyy)");
                    }
                }
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
