package com.piskas.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ConnectException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText etRut, etNombre, etCorreo;
    private Button btnLeer, btnLeerTodos, btnGuardar, btnEliminarTodos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // References ==============================================================================
        etRut = (EditText) findViewById(R.id.etRut);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        btnLeer = (Button) findViewById(R.id.btnLeer);
        btnLeerTodos = (Button) findViewById(R.id.btnLeerTodos);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnEliminarTodos = (Button) findViewById(R.id.btnEliminarTodos);


        // Set Listeners ===========================================================================
        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLeerDatos();
            }
        });
        btnLeerTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLeerTodosLosDatos();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuardarDatos();
            }
        });
        btnEliminarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEliminarDatos();
            }
        });
    }

    private void getLeerDatos() {
        SharedPreferences preferences = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);

        etRut.setText(preferences.getString("rut", ""));
        etNombre.setText(preferences.getString("nombre", ""));
        etCorreo.setText(preferences.getString("correo", ""));
    }

    private void getLeerTodosLosDatos() {
        SharedPreferences preferences = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        Map<String,?> claves = preferences.getAll();

        for (Map.Entry<String,?> valor: claves.entrySet())
            Log.d("SharedPreferences", "Clave: " + valor.getKey() + ", Valor: " + valor.getValue());

        Toast.makeText(this, "Datos en Consola", Toast.LENGTH_SHORT).show();
        setClearFields();
    }

    private void setGuardarDatos() {
        SharedPreferences preferences = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("rut", etRut.getText().toString());
        editor.putString("nombre", etNombre.getText().toString());
        editor.putString("correo", etCorreo.getText().toString());

        editor.apply();

        Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show();
        setClearFields();
    }

    private void setEliminarDatos() {
        SharedPreferences preferences = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove("rut");
        editor.remove("nombre");
        editor.remove("correo");

        editor.apply();

        Toast.makeText(this, "Datos Eliminados", Toast.LENGTH_SHORT).show();
        setClearFields();
    }

    private void setClearFields() {
        etRut.setText("");
        etNombre.setText("");
        etCorreo.setText("");
    }

}