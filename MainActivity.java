package com.example.ejemplo01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Color;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
//import android.widget.Toast;
import android.content.Intent;



public class MainActivity extends AppCompatActivity {
    private Button boton;
    private EditText nombreEditText;
    private String nombreIngresado;
    private RadioButton radioButtonCirculos;
    private RadioButton radioButtonCruces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreEditText = findViewById(R.id.editTextTextPersonName);
        nombreEditText.setHintTextColor(Color.GRAY);
        nombreEditText.setHint("Ingrese su nombre");
        nombreEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (nombreEditText.getText().toString().equals("Ingrese su nombre")) {
                        nombreEditText.setText("");
                        nombreEditText.setTextColor(Color.BLACK);
                    }
                } else {
                    if (nombreEditText.getText().toString().isEmpty()) {
                        nombreEditText.setText("Ingrese su nombre");
                        nombreEditText.setTextColor(Color.GRAY);
                    }
                }
            }
        });

        nombreEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nombreIngresado = s.toString();
                if (!nombreIngresado.isEmpty()) {
                    nombreEditText.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        radioButtonCirculos = findViewById(R.id.radio_circulos);
        radioButtonCruces = findViewById(R.id.radio_cruces);
        radioButtonCirculos.setChecked(true);
        RadioGroup grupoBotones = findViewById(R.id.radioGroup);
        grupoBotones.clearCheck();
        boton = findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el tipo de ficha seleccionado
                String tipoFicha;
                if (radioButtonCirculos.isChecked()) {
                    tipoFicha = "Círculos";
                } else {
                    tipoFicha = "Cruces";
                }

                // Crea y muestra el cuadro de diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Bienvenido");
                builder.setMessage("Hola " + (nombreIngresado.isEmpty() ? "invitado" : nombreIngresado) + ". Vas a jugar con " + tipoFicha + ".");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // El usuario hizo clic en el botón "Aceptar"
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // El usuario hizo clic en el botón "Cancelar"
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
