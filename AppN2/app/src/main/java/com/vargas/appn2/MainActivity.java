package com.vargas.appn2;

// CALCULO DO IMC: PESO / (ALTURA X ALTURA)

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText nome, altura, peso;
    Button btnCalcular;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.etNome);
        altura = findViewById(R.id.etAltura);
        peso = findViewById(R.id.etPeso);
        btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editNome = nome.getText().toString();
                String editAltura = altura.getText().toString();
                String editPeso = peso.getText().toString();
                if(editNome.isEmpty() || editAltura.isEmpty() || editPeso.isEmpty()){
                    Snackbar.make(view,"Preencha todos os dados para calcular",Snackbar.LENGTH_LONG).show();
                }

                resultadoIMC();
            }
        });
    }

    public void resultadoIMC(){
        Intent intent = new Intent(this, ResultadoIMC.class); // quando clicar e iniciar esse método, vai mandar pra tela de ResultadoIMC
        intent.putExtra("nome", nome.getText().toString()); // vai mandar pra tela ResultadoIMC o nome coletado nesta tela atual por parâmetro
        intent.putExtra("altura", altura.getText().toString()); // conforme a linha acima, vai mandar também a altura via parâmetro
        intent.putExtra("peso", peso.getText().toString()); // o mesmo vale para o peso

        startActivity(intent); // quando clicar em calcular, ele vai iniciar a intent do ResultadoIMC
    }

}