package com.piskas.servicios.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.piskas.servicios.Clases.Cliente;
import com.piskas.servicios.Clases.Servicio;
import com.piskas.servicios.R;

import java.util.ArrayList;

public class ServiciosActivity extends AppCompatActivity {
    private Cliente cliente;
    private ArrayList<Servicio> servicios = new ArrayList<>();
    private ArrayAdapter<Servicio> serviciosAdap;
    private ArrayAdapter<Servicio> serviciosClienteAdap;
    private TextView lblNombreCompleto, lblTotal;
    private Spinner spnServicios;
    private ImageButton btnAddServicio, btnRemoveServicio;
    private Button btnVolver;
    private ListView lstContratados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);


        // References ==============================================================================
        lblNombreCompleto = (TextView) findViewById(R.id.lblNombreCompleto);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        spnServicios = (Spinner) findViewById(R.id.spnServicios);
        btnAddServicio = (ImageButton) findViewById(R.id.btnAddServicio);
        btnRemoveServicio = (ImageButton) findViewById(R.id.btnRemoveServicio);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        lstContratados = (ListView) findViewById(R.id.lstContratados);


        // Get Extras ==============================================================================
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            try {
                cliente = (Cliente) bundle.getSerializable("cliente");
                lblNombreCompleto.setText("Cliente: " + cliente.getNombreCompleto());
                lblTotal.setText("Total: " + cliente.getCredito());

            } catch (Exception ex) {
                Toast.makeText(ServiciosActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

        } else {
            Toast.makeText(ServiciosActivity.this, "Debe seleccionar un cliente", Toast.LENGTH_SHORT).show();
            finish();
        }


        // Add Servicios ===========================================================================
        servicios.add(new Servicio(1001, 1000, "Servicio 1"));
        servicios.add(new Servicio(1002, 2000, "Servicio 2"));
        servicios.add(new Servicio(1003, 3000, "Servicio 3"));
        servicios.add(new Servicio(1004, 4000, "Servicio 4"));
        servicios.add(new Servicio(1005, 5000, "Servicio 5"));


        // Set Spinner & Adapter ===================================================================
        serviciosAdap = new ArrayAdapter<Servicio>(this, android.R.layout.simple_spinner_item, servicios);
        spnServicios.setAdapter(serviciosAdap);


        // Set ListView & Adapter ==================================================================
        serviciosClienteAdap = new ArrayAdapter<Servicio>(this, android.R.layout.simple_list_item_1, cliente.getServicios());
        lstContratados.setAdapter(serviciosClienteAdap);


        // Set Listeners ===========================================================================
        btnAddServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Servicio servicio = (Servicio) spnServicios.getSelectedItem();

                for (Servicio servicioCliente: cliente.getServicios()) {
                    if (servicioCliente.getCodigo() == servicio.getCodigo()) {
                        Toast.makeText(ServiciosActivity.this, "Servicio ya contratado", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (cliente.setAgregarServicio(servicio)) {
                    serviciosClienteAdap.notifyDataSetChanged();
                    lblTotal.setText("Total: " + cliente.getCredito());
                    Toast.makeText(ServiciosActivity.this, "Servicio agregado con exito", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRemoveServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Servicio servicio = (Servicio) spnServicios.getSelectedItem();
                setEliminarServicio(servicio);
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("cliente", cliente);
                setResult(MainActivity.REQUEST_CODE, intent);
                finish();
            }
        });
        lstContratados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setEliminarServicio(cliente.getServicios().get(position));
                return true;
            }
        });
    }

    private void setEliminarServicio(final Servicio servicio) {
        for (Servicio servicioCliente: cliente.getServicios()) {
            if (servicioCliente.getCodigo() == servicio.getCodigo()) {
                AlertDialog.Builder opcDialogo = new AlertDialog
                        .Builder(ServiciosActivity.this)
                        .setMessage("¿Está seguro que desea eliminar " + servicio.getDescripcion() + "?")
                        .setTitle("Eliminar Servicio")
                        .setIcon(R.drawable.ic_remove)
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < cliente.getServicios().size(); i++) {
                                    if (cliente.setEliminarServicio(servicio.getCodigo())) {
                                        serviciosClienteAdap.notifyDataSetChanged();
                                        lblTotal.setText("Total: " + cliente.getCredito());
                                        Toast.makeText(ServiciosActivity.this, "Servicio eliminado con exito", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }
                        });
                AlertDialog dialog = opcDialogo.create();
                dialog.show();
                return;
            }
        }

        Toast.makeText(ServiciosActivity.this, "Servicio no contratado", Toast.LENGTH_SHORT).show();
    }
}
