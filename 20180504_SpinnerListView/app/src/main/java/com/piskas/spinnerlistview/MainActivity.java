package com.piskas.spinnerlistview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner spnClientes;
    private ListView lstClientes;
    private EditText txtNombreCliente;
    private ImageButton btnAddItem;

    private ArrayAdapter<String> spnAdapter;
    private ArrayAdapter<String> lstAdapter;

    private String[] clientesStr = new String[] { "Seleccione Cliente", "Juan", "Maria", "Fernando" };
    private ArrayList<String> clientes = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // References ==============================================================================
        spnClientes = (Spinner) findViewById(R.id.spnClientes);
        lstClientes = (ListView) findViewById(R.id.lstClientes);
        txtNombreCliente = (EditText) findViewById(R.id.txtNombreCliente);
        btnAddItem = (ImageButton) findViewById(R.id.btnAddItem);


        // Spinner =================================================================================
        spnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clientesStr);
        spnClientes.setAdapter(spnAdapter);
        spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) setAgregarItem(clientesStr[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // ListView ================================================================================
        lstAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientes);
        lstClientes.setAdapter(lstAdapter);
//        lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, clientes.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });
        lstClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setEliminarItem(lstClientes.getItemAtPosition(position).toString(), position);
                return false;
            }
        });


        // ImageButton =============================================================================
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNombreCliente = txtNombreCliente.getText().toString();

                if(strNombreCliente.isEmpty()) {
                    txtNombreCliente.setError("Debe ingresar un nombre");

                } else {
                    setAgregarItem(strNombreCliente);
                    txtNombreCliente.setText("");
                }
            }
        });
    }


    // Método para agregar item ====================================================================
    private void setAgregarItem(final String strNombreCliente) {
        AlertDialog.Builder opcDialogo = new AlertDialog.Builder(MainActivity.this);
        opcDialogo.setMessage("¿Está seguro que desea agregar a " + strNombreCliente + "?");
        opcDialogo.setTitle("Agregar Item");
        opcDialogo.setIcon(R.drawable.ic_add_item);
        opcDialogo.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clientes.add(strNombreCliente);
                lstAdapter.notifyDataSetChanged();
            }
        });
        opcDialogo.setNegativeButton("Cancelar", null);

        AlertDialog dialog = opcDialogo.create();
        dialog.show();
    }


    // Método para eliminar item ===================================================================
    private void setEliminarItem(String strNombreCliente, final int position) {
        AlertDialog.Builder opcDialogo = new AlertDialog.Builder(MainActivity.this);
        opcDialogo.setMessage("¿Está seguro que desea eliminar al " + strNombreCliente + "?");
        opcDialogo.setTitle("Eliminar Item");
        opcDialogo.setIcon(R.drawable.ic_delete_item);
        opcDialogo.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clientes.remove(position);
                lstAdapter.notifyDataSetChanged();
            }
        });
        opcDialogo.setNegativeButton("Cancelar", null);

        AlertDialog dialog = opcDialogo.create();
        dialog.show();
    }
}