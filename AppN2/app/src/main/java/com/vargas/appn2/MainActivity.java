package com.vargas.appn2;

// CALCULO DO IMC: PESO / (ALTURA X ALTURA)

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;

    EditText nome, altura, peso;
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

                resultadoIMC();

            }
        });
    }
    public void resultadoIMC(){

        if(TextUtils.isEmpty(nome.getText().toString())){
            nome.setError("Preencha o campo com seu nome.");
            return;
        }
        if(TextUtils.isEmpty(altura.getText().toString())){
            altura.setError("Preencha o campo com a sua altura.");
            return;
        }
        if(TextUtils.isEmpty(peso.getText().toString())){
            peso.setError("Preencha o campo com seu peso.");
            return;
        }

        Intent intent = new Intent(MainActivity.this, ResultadoIMC.class); // quando clicar e iniciar esse método, vai mandar pra tela de ResultadoIMC
        intent.putExtra("nome", nome.getText().toString()); // vai mandar pra tela ResultadoIMC o nome coletado nesta tela atual por parâmetro
        intent.putExtra("altura", altura.getText().toString()); // conforme a linha acima, vai mandar também a altura via parâmetro
        intent.putExtra("peso", peso.getText().toString()); // o mesmo vale para o peso
        startActivity(intent); // quando clicar em calcular, ele vai iniciar a intent do ResultadoIMC
    }

}