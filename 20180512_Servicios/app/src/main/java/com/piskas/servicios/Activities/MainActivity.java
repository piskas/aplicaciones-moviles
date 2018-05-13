package com.piskas.servicios.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.piskas.servicios.Clases.Cliente;
import com.piskas.servicios.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1000;

    private Cliente cliente;
    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayAdapter<Cliente> clientesAdap;

    private AutoCompleteTextView actRut;
    private EditText txtNombre, txtApellido, txtFechaNacimiento, txtCredito;
    private Button btnGuardar, btnEliminar, btnServicios;
    private SimpleDateFormat sdfDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // References ==============================================================================
        actRut = (AutoCompleteTextView) findViewById(R.id.actRut);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtFechaNacimiento = (EditText) findViewById(R.id.txtFechaNacimiento);
        txtCredito = (EditText) findViewById(R.id.txtCredito);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnServicios = (Button) findViewById(R.id.btnServicios);


        // Set Initial =============================================================================
        btnEliminar.setEnabled(false);
        btnServicios.setEnabled(false);
        txtCredito.setEnabled(false);


        // Format Date =============================================================================
        sdfDate = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "CL"));
        sdfDate.setLenient(false); // La fecha tiene que cumplir el formato exacto


        // Set AutoComplete & Adapter ==============================================================
        try {
            clientes.add(new Cliente("11.111.111-1", "Luis", "Arellano", sdfDate.parse("28/10/1991")));
        } catch (Exception ex) { }
        clientesAdap = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes);
        actRut.setAdapter(clientesAdap);
        actRut.setThreshold(1);


        // Set Listeners ===========================================================================
        actRut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                if (item instanceof Cliente) {
                    cliente = (Cliente) item;

                    txtNombre.setText(cliente.getNombre());
                    txtApellido.setText(cliente.getApellido());
                    txtFechaNacimiento.setText(sdfDate.format(cliente.getFechaNacimiento()));
                    txtCredito.setText(cliente.getCredito());

                    btnGuardar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    btnServicios.setEnabled(true);
                }
            }
        });
        actRut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLimpiaFormularioCliente();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validacion de vacios ============================================================
                if (actRut.getText().toString().isEmpty()) {
                    actRut.setError("Debe digitar un Rut");

                } else if (txtNombre.getText().toString().isEmpty()) {
                    txtNombre.setError("Debe escribir un Nombre");

                } else if (txtApellido.getText().toString().isEmpty()) {
                    txtApellido.setError("Debe escribir un Apellido");

                } else if (txtFechaNacimiento.getText().toString().isEmpty()) {
                    txtFechaNacimiento.setError("Debe digitar una Fecha de Nacimiento");

                } else {
                    // Se pasan a variables los campos =============================================
                    String strRut = actRut.getText().toString();
                    String strNombre = txtNombre.getText().toString();
                    String strApellido = txtApellido.getText().toString();
                    Date dteFechaNacimiento;


                    // Se valida formato de Fecha ==================================================
                    try {
                        dteFechaNacimiento = sdfDate.parse(txtFechaNacimiento.getText().toString());

                        if (dteFechaNacimiento.getTime() > new Date().getTime()) {
                            txtFechaNacimiento.setError("La fecha de nacimiento debe ser de hoy hacia atras");
                            return;
                        }
                    } catch (Exception ex) {
                        txtFechaNacimiento.setError("El formato de fecha debe ser dd-MM-yyyy");
                        return;
                    }


                    // Se crea o modifica cliente ==================================================
                    if (cliente == null) {
                        cliente = new Cliente(
                                strRut,
                                strNombre,
                                strApellido,
                                dteFechaNacimiento);
                    } else {
                        cliente.setNombre(strNombre);
                        cliente.setApellido(strApellido);
                        cliente.setFechaNacimiento(dteFechaNacimiento);
                    }


                    // Busca al cliente para modificar, si no lo encuentra lo agrega ===============
                    if (setRegistraCliente(cliente, true)) {
                        btnEliminar.setEnabled(true);
                        btnServicios.setEnabled(true);
                     }
                }
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cliente == null) {
                    Toast.makeText(MainActivity.this, "Debe buscar un cliente", Toast.LENGTH_SHORT).show();

                } else {
                    AlertDialog.Builder opcDialogo = new AlertDialog
                            .Builder(MainActivity.this)
                            .setMessage("¿Está seguro que desea eliminar a " + cliente.getNombreCompleto() + "?")
                            .setTitle("Eliminar Cliente")
                            .setIcon(R.drawable.ic_remove)
                            .setNegativeButton("Cancelar", null)
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (setEliminaCliente(cliente.getRut()))
                                        setLimpiaFormularioCliente();

                                }
                            });
                    AlertDialog dialog = opcDialogo.create();
                    dialog.show();
                }
            }
        });
        btnServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cliente == null) {
                    Toast.makeText(MainActivity.this, "Debe buscar un cliente", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(MainActivity.this, ServiciosActivity.class);
                    intent.putExtra("cliente", cliente);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            cliente = (Cliente) data.getSerializableExtra("cliente");
            setRegistraCliente(cliente, false);
        }
    }

    private void setLimpiaFormularioCliente() {
        cliente = null;

        actRut.setText("");
        actRut.setError(null);
        txtNombre.setText("");
        txtNombre.setError(null);
        txtApellido.setText("");
        txtApellido.setError(null);
        txtFechaNacimiento.setText("");
        txtFechaNacimiento.setError(null);
        txtCredito.setText("");
        txtCredito.setError(null);

        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnServicios.setEnabled(false);
    }

    private boolean setRegistraCliente(Cliente cliente, boolean blnMuestraMensaje) {
        // Busca el cliente en la lista, para modificarlo ==========================================
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getRut().equals(cliente.getRut())) {
                clientes.set(i, cliente);
                notifyDataSetChanged();
                if (blnMuestraMensaje) Toast.makeText(getApplicationContext(), "Cliente modificado con exito", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        // Si no encuentra al cliente en la lista, lo agrega =======================================
        clientes.add(cliente);
        notifyDataSetChanged();
        if (blnMuestraMensaje) Toast.makeText(MainActivity.this, "Cliente agregado con exito", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean setEliminaCliente(String rut) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getRut().equals(rut)) {
                clientes.remove(i);
                notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Cliente eliminado con exito", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    // No funciona clientesAdap.notifyDataSetChanged, se vuelve a instanciar clientesAdap en actRut
    private void notifyDataSetChanged() {
        //clientesAdap.notifyDataSetChanged();
        clientesAdap = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes);
        actRut.setAdapter(clientesAdap);
    }
}
