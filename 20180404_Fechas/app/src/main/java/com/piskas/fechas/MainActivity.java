package com.piskas.fechas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtFechaNac;
    private TextView lblResultado;
    private Button btnAceptar;
    private Button btnLimpiar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // References ==============================================================================
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtFechaNac = (EditText) findViewById(R.id.txtFechaNac);
        lblResultado = (TextView) findViewById(R.id.lblResultado);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);


        // Listeners ===============================================================================
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = txtNombre.getText().toString();
                String strDateBirth = txtFechaNac.getText().toString();

                // Validacion de campos vacios =====================================================
                if (strName.isEmpty()) {
                    txtNombre.setError("Debe ingresar un Nombre");
                }

                if (strDateBirth.isEmpty()) {
                    txtFechaNac.setError("Debe ingresar una Fecha de Nacimiento");

                } else {
                    // Formatos de fechas ==========================================================
                    SimpleDateFormat sdfDateName = new SimpleDateFormat("EEEE dd ' de ' MMMM ' del año ' yyyy", new Locale("es", "CL"));
                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "CL"));
                    sdfDate.setLenient(false); // La fecha tiene que cumplir el formato exacto

                    // Variables Calendar ==========================================================
                    Calendar calDateBirth = Calendar.getInstance(new Locale("es", "CL"));
                    Calendar calDateNow = Calendar.getInstance(new Locale("es", "CL"));

                    // Variables Date ==============================================================
                    Date dateBirth = new Date();
                    Date dateNow = new Date();

                    // Variables Int ===============================================================
                    int difYear = 0;
                    int difMonth = 0;
                    int difDay = 0;


                    // Validacion de fecha =========================================================
                    try {
                        dateBirth = sdfDate.parse(strDateBirth);

                        calDateBirth.setTime(dateBirth);
                        calDateNow.setTime(dateNow);

                        difYear = (calDateNow.get(Calendar.YEAR) - calDateBirth.get(Calendar.YEAR));
                        difMonth = (calDateNow.get(Calendar.MONTH) - calDateBirth.get(Calendar.MONTH));
                        difDay = (calDateNow.get(Calendar.DAY_OF_MONTH) - calDateBirth.get(Calendar.DAY_OF_MONTH));

                        if (difMonth < 0 || (difMonth == 0 && difDay < 0))
                            --difYear;


                    } catch (ParseException e) {
                        lblResultado.setText("");
                        txtFechaNac.setError("Problemas con el formato de la hora");
                    }


                    // Validaciones del ejercicio ==================================================
                    if (dateBirth.getTime() > dateNow.getTime()) {
                        lblResultado.setText("");
                        txtFechaNac.setError("La fecha de nacimiento debe ser menor o igual a hoy");

                    } else if(difYear < 0) {
                        lblResultado.setText("");
                        txtFechaNac.setError("La edad debe ser mayor o igual a 0");

                    } else if(difYear > 150) {
                        lblResultado.setText("");
                        txtFechaNac.setError("La edad debe ser como maximo 150");

                    } else {
                        lblResultado.setText(
                                "Felices " + difYear + " años " + strName + ". " +
                                        "Haz nacido un día " + sdfDateName.format(dateBirth) + ". " +
                                        (difDay == 0 && difMonth == 0 ? "¡¡Feliz Cumpleaños!!" : ""));
                    }
                }
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNombre.setText("");
                txtFechaNac.setText("");
                lblResultado.setText("");
            }
        });
    }
}
