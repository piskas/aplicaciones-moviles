package com.piskas.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsuario;
    private EditText txtClave;
    private CheckBox chkRecordar;
    private Switch swtRecordar;
    private Button btnAceptar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // References ==============================================================================
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtClave = (EditText) findViewById(R.id.txtClave);
        chkRecordar = (CheckBox) findViewById(R.id.chkRecordar);
        swtRecordar = (Switch) findViewById(R.id.swtRecordar);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);


        // Listeners ===============================================================================
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validacion de campos vacios, con un limite de caracteres ========================
                if (txtUsuario.getText().length() < 8)
                    txtUsuario.setError("Nombre de usuario debe contener al menos 8 caracteres.");

                if (txtClave.getText().length() < 6)
                    txtClave.setError("La clave debe tener al menos 6 caracteres.");

                else {
                    // Validacion: Clave debe tener al menos una mayuscula, una minuscula y un digito
                    boolean bolMayus = false, bolMinus = false, bolDigit = false;

                    for (int i = 0; i < txtClave.getText().length(); i++) {
                        char chrClave = txtClave.getText().charAt(i);

                        if (!bolMayus) bolMayus = Character.isUpperCase(chrClave);
                        if (!bolMinus) bolMinus = Character.isLowerCase(chrClave);
                        if (!bolDigit) bolDigit = Character.isDigit(chrClave);
                    }

                    // Verifica validacion de clave
                    if (!(bolMayus && bolMinus && bolDigit)) {
                        txtClave.setError("La clave debe tener al menos una mayuscula, una minuscula y un digito");

                    } else {
                        // Validacion Correcta =====================================================
                        Toast.makeText(getApplicationContext(), "Validacion Correcta", Toast.LENGTH_SHORT).show();

                        // Levanta Intent con el detalle del usuario
                        if (chkRecordar.isChecked()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("usuario", txtUsuario.getText().toString());
                            intent.putExtra("clave", txtClave.getText().toString());
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chkRecordar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "Estado CheckBox: " + chkRecordar.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });
        swtRecordar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "Estado Switch: " + swtRecordar.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
