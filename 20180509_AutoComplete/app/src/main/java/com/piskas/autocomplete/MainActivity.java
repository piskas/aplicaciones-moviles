package com.piskas.autocomplete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView actPatente;
    private TextView txtMarca;
    private TextView txtModelo;
    private TextView txtAnio;

    private ArrayAdapter<Auto> autosAdap;
    private ArrayList<Auto> autosArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References ==============================================================================
        actPatente = (AutoCompleteTextView) findViewById(R.id.actPatente);
        txtMarca = (TextView) findViewById(R.id.txtMarca);
        txtModelo = (TextView) findViewById(R.id.txtModelo);
        txtAnio = (TextView) findViewById(R.id.txtAnio);


        // Llenado de ArrayList ====================================================================
        autosArray.add(new Auto("AAAA-27", "Toyota", "Yaris", 2010));
        autosArray.add(new Auto("AABB-28", "Mazda", "3", 2011));
        autosArray.add(new Auto("BBBB-29", "Chevrolet", "Spark", 2012));
        autosArray.add(new Auto("BBCC-30", "Fiat", "Palio", 2013));


        // Se instancia el adaptador con ArrayList de Autos ========================================
        autosAdap = new ArrayAdapter<Auto>(this, android.R.layout.simple_list_item_1, autosArray);


        // Se asigna adaptador al AutoCompleteText =================================================
        actPatente.setAdapter(autosAdap);
        actPatente.setThreshold(2); // Autocompleta desde el 2do caracter
        actPatente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position); // Obtiene objeto seleccionado de AutoCompleteText

                if (item instanceof Auto) { // Se pregunta si el item Object es realmente un objeto Auto
                    Auto autoAux = (Auto) item; // Se parsea item en Auto

                    //Toast.makeText(MainActivity.this, autoAux.getMarca(), Toast.LENGTH_SHORT).show();
                    txtMarca.setText("Marca: " + autoAux.getMarca());
                    txtModelo.setText("Modelo: " + autoAux.getModelo());
                    txtAnio.setText("Año: " + autoAux.getAnio());
                }
            }
        });
    }
}
