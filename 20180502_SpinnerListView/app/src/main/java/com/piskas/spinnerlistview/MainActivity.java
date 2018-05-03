package com.piskas.spinnerlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String[] clientesStr = new String[] { "Juan", "Maria", "Fernando" };
    private Cliente[] clientes = new Cliente[] {
            new Cliente("1111", "Juan", "Perez"),
            new Cliente("2222", "Maria", "Gonzales"),
            new Cliente("3333", "Jose", "Rojo")};

    private ArrayAdapter<String> spnAdapter;
    private ArrayAdapter<Cliente> lstAdapter;
    private Spinner spnClientes;
    private ListView lstClientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // References ==============================================================================
        spnClientes = (Spinner) findViewById(R.id.spnClientes);
        lstClientes = (ListView) findViewById(R.id.lstClientes);


        // Spinner =================================================================================
        spnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clientesStr);
        spnClientes.setAdapter(spnAdapter);
        spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Seleccionó: " + clientesStr[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // ListView ================================================================================
        lstAdapter = new ArrayAdapter<Cliente> (this, android.R.layout.simple_list_item_1, clientes);
        lstClientes.setAdapter(lstAdapter);
        lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, clientes[position].getRut(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}