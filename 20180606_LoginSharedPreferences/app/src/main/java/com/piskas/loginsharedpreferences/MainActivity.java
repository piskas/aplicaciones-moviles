package com.piskas.loginsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsuario;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // References ==============================================================================
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        btnSalir = (Button) findViewById(R.id.btnSalir);


        // Set Listeners ===========================================================================
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Get Preferences =========================================================================
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) tvUsuario.setText("Bienvenido " + bundle.getString("usuario", ""));
    }
}