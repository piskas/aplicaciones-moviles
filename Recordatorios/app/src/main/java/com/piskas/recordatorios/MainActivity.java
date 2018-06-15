package com.piskas.recordatorios;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText etTitulo, etRecordatorio;
    private ImageButton btnAgregar, btnRealizar, btnPostergar;
    private Spinner spnTipoRecordatorio;
    private ListView lstRecordatorios;
    private ArrayAdapter<Recordatorio> adpRecordatorios;
    private ArrayAdapter<String> adpTiposRecordatorio;

    private String usuario;
    private final static String strAgregados = "_agregados", strRealizados = "_realizados", strPostergados = "_postergados";
    private String[] tiposRecordatorio = new String[] { "Recordatorios Agregados", "Recordatorios Realizados", "Recordatorios Postergados" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Extras ==============================================================================
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) finish();
        else usuario = bundle.getString("usuario").toLowerCase();


        // References ==============================================================================
        etTitulo = (EditText) findViewById(R.id.etTitulo);
        etRecordatorio = (EditText) findViewById(R.id.etRecordatorio);
        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnRealizar = (ImageButton) findViewById(R.id.btnRealizar);
        btnPostergar = (ImageButton) findViewById(R.id.btnPostergar);
        spnTipoRecordatorio = (Spinner) findViewById(R.id.spnTipoRecordatorio);
        lstRecordatorios = (ListView) findViewById(R.id.lstRecordatorios);


        // Set ArrayAdapter ========================================================================
        adpTiposRecordatorio = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tiposRecordatorio);
        spnTipoRecordatorio.setAdapter(adpTiposRecordatorio);

        adpRecordatorios = new ArrayAdapter<Recordatorio>(this, android.R.layout.simple_list_item_1, getRecordatorios(0));
        lstRecordatorios.setAdapter(adpRecordatorios);


        // Set Listeners ===========================================================================
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitulo.getText().toString().isEmpty()) {
                    etTitulo.setError("Debe ingresar un titulo");

                } else if (etRecordatorio.getText().toString().isEmpty()) {
                    etRecordatorio.setError("Debe ingresar un recordatorio");

                } else {
                    addRecordatorio(new Recordatorio(etTitulo.getText().toString(), etRecordatorio.getText().toString()), 0);
                }
            }
        });
        btnRealizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitulo.getText().toString().isEmpty()) {
                    etTitulo.setError("Debe ingresar un titulo");

                } else if (etRecordatorio.getText().toString().isEmpty()) {
                    etRecordatorio.setError("Debe ingresar un recordatorio");

                } else {
                    addRecordatorio(new Recordatorio(etTitulo.getText().toString(), etRecordatorio.getText().toString()), 1);
                }
            }
        });
        btnPostergar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitulo.getText().toString().isEmpty()) {
                    etTitulo.setError("Debe ingresar un titulo");

                } else if (etRecordatorio.getText().toString().isEmpty()) {
                    etRecordatorio.setError("Debe ingresar un recordatorio");

                } else {
                    addRecordatorio(new Recordatorio(etTitulo.getText().toString(), etRecordatorio.getText().toString()), 2);
                }
            }
        });
        lstRecordatorios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                if (item instanceof Recordatorio) {
                    Recordatorio recordatorio = (Recordatorio) item;
                    etTitulo.setText(recordatorio.getTitulo());
                    etRecordatorio.setText(recordatorio.getRecordatorio());

                } else {
                    etTitulo.setText("");
                    etRecordatorio.setText("");
                }
            }
        });
        lstRecordatorios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                if (item instanceof Recordatorio) {
                    Recordatorio recordatorio = (Recordatorio) item;
                    removeRecordatorio(recordatorio);
                    return true;

                } else {
                    etTitulo.setText("");
                    etRecordatorio.setText("");
                    return false;
                }
            }
        });
        spnTipoRecordatorio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                notifyDataSetChanged(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private ArrayList<Recordatorio> getRecordatorios(int tipoRecordatorio) {
        String spName;

        switch (tipoRecordatorio) {
            case 1: spName = usuario.concat(strRealizados); break;
            case 2: spName = usuario.concat(strPostergados); break;
            default: spName = usuario.concat(strAgregados); break;
        }

        SharedPreferences spRecordatorios = getSharedPreferences(spName, Context.MODE_PRIVATE);
        ArrayList<Recordatorio> recordatorios = new ArrayList<>();

        for (Map.Entry<String, ?> entry : spRecordatorios.getAll().entrySet())
            recordatorios.add(new Recordatorio(entry.getKey(), (String) entry.getValue()));

        return recordatorios;
    }
    private void addRecordatorio(Recordatorio recordatorio, int tipoRecordatorio) {
        SharedPreferences spRecordatoriosAgregados = getSharedPreferences(usuario.concat(strAgregados), Context.MODE_PRIVATE);
        SharedPreferences spRecordatoriosRealizados = getSharedPreferences(usuario.concat(strRealizados), Context.MODE_PRIVATE);
        SharedPreferences spRecordatoriosPostergados = getSharedPreferences(usuario.concat(strPostergados), Context.MODE_PRIVATE);

        switch (tipoRecordatorio) {
            case 1:
                spRecordatoriosAgregados.edit().remove(recordatorio.getTitulo()).apply();
                spRecordatoriosRealizados.edit().putString(recordatorio.getTitulo(), recordatorio.getRecordatorio()).apply();
                spRecordatoriosPostergados.edit().remove(recordatorio.getTitulo()).apply();
                break;

            case 2:
                spRecordatoriosAgregados.edit().remove(recordatorio.getTitulo()).apply();
                spRecordatoriosRealizados.edit().remove(recordatorio.getTitulo()).apply();
                spRecordatoriosPostergados.edit().putString(recordatorio.getTitulo(), recordatorio.getRecordatorio()).apply();
                break;

            default:
                spRecordatoriosAgregados.edit().putString(recordatorio.getTitulo(), recordatorio.getRecordatorio()).apply();
                spRecordatoriosRealizados.edit().remove(recordatorio.getTitulo()).apply();
                spRecordatoriosPostergados.edit().remove(recordatorio.getTitulo()).apply();
                break;
        }


        Toast.makeText(MainActivity.this, "Recordatorio Realizado", Toast.LENGTH_SHORT).show();

        if (spnTipoRecordatorio.getSelectedItemPosition() == tipoRecordatorio)
            notifyDataSetChanged(tipoRecordatorio);
        else
            spnTipoRecordatorio.setSelection(tipoRecordatorio);
    }
    private void removeRecordatorio(final Recordatorio recordatorio) {
           AlertDialog.Builder opcDialogo = new AlertDialog
                    .Builder(MainActivity.this)
                    .setMessage("¿Está seguro que desea eliminar el recordatorio '" + recordatorio.getTitulo() + "'?")
                    .setTitle("Eliminar Recordatorio")
                    .setIcon(R.drawable.ic_remove)
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String spName;

                            switch (spnTipoRecordatorio.getSelectedItemPosition()) {
                                case 1: spName = usuario.concat(strRealizados); break;
                                case 2: spName = usuario.concat(strPostergados); break;
                                default: spName = usuario.concat(strAgregados); break;
                            }

                            SharedPreferences spRecordatorios = getSharedPreferences(spName, Context.MODE_PRIVATE);
                            spRecordatorios.edit().remove(recordatorio.getTitulo()).apply();

                            notifyDataSetChanged(spnTipoRecordatorio.getSelectedItemPosition());
                            Toast.makeText(MainActivity.this, "Recordatorio Eliminado", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog dialog = opcDialogo.create();
            dialog.show();
    }
    private void notifyDataSetChanged(int tipoRecordatorio) {
        etTitulo.setText("");
        etTitulo.setError(null);
        etRecordatorio.setText("");
        etRecordatorio.setError(null);
        adpRecordatorios = new ArrayAdapter<Recordatorio>(this, android.R.layout.simple_list_item_1, getRecordatorios(tipoRecordatorio));
        lstRecordatorios.setAdapter(adpRecordatorios);
    }
}
