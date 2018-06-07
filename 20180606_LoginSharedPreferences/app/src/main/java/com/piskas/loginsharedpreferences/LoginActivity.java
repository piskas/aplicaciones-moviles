package com.piskas.loginsharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class LoginActivity extends AppCompatActivity {
    private final static int REQUEST_CODE = 1000;
    private EditText etUsuario, etClave;
    private Switch swtRecordarUsuario, swtRecordarClave;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // References ==============================================================================
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etClave = (EditText) findViewById(R.id.etClave);
        swtRecordarUsuario = (Switch) findViewById(R.id.swtRecordarUsuario);
        swtRecordarClave = (Switch) findViewById(R.id.swtRecordarClave);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Set Listeners ===========================================================================
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsuario.getText().toString().trim().isEmpty()) {
                    etUsuario.setError("Debe ingresar usuario");
                }

                if (etClave.getText().toString().trim().isEmpty()) {
                    etClave.setError("Debe ingresar clave");

                } else {
                    setSavePreferences();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("usuario", etUsuario.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        swtRecordarUsuario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!swtRecordarUsuario.isChecked())
                    swtRecordarClave.setChecked(false);
            }
        });
        swtRecordarClave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!swtRecordarUsuario.isChecked())
                    swtRecordarClave.setChecked(false);
            }
        });


        // Init Preferences ========================================================================
        setInitPreferences();
    }

    private void setInitPreferences() {
        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        swtRecordarUsuario.setChecked(preferences.getBoolean("blnRecordarUsuario", false));
        swtRecordarClave.setChecked(preferences.getBoolean("blnRecordarClave", false));


        if (swtRecordarUsuario.isChecked())
            etUsuario.setText(preferences.getString("strUsuario", ""));
        else
            etUsuario.setText("");


        if (swtRecordarUsuario.isChecked() && swtRecordarClave.isChecked())
            etClave.setText(preferences.getString("strClave", ""));
        else
            etClave.setText("");
    }
    private void setSavePreferences() {
        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        if (swtRecordarUsuario.isChecked()) {
            preferences
                    .edit()
                    .putBoolean("blnRecordarUsuario", true)
                    .putString("strUsuario", etUsuario.getText().toString())
                    .apply();
        } else {
            preferences
                    .edit()
                    .putBoolean("blnRecordarUsuario", false)
                    .remove("strUsuario")
                    .apply();
        }

        if (swtRecordarUsuario.isChecked() && swtRecordarClave.isChecked()) {
            preferences
                    .edit()
                    .putBoolean("blnRecordarClave", true)
                    .putString("strClave", etClave.getText().toString())
                    .apply();
        } else {
            preferences
                    .edit()
                    .putBoolean("blnRecordarClave", false)
                    .remove("strClave")
                    .apply();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) setInitPreferences();
    }
}
