package com.piskas.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView txtNombre;
    private TextView txtEdad;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // References ==============================================================================
        txtNombre = (TextView) findViewById(R.id.lblNombre);
        txtEdad = (TextView) findViewById(R.id.lblEdad);
        btnVolver = (Button) findViewById(R.id.btnVolver);


        // Bundle ==================================================================================
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String usuario = bundle.getString("usuario");
            String clave = bundle.getString("clave");

            txtNombre.setText("Usuario: " + usuario);
            txtEdad.setText("Clave: " + clave);
        }


        // Listeners ===============================================================================
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}