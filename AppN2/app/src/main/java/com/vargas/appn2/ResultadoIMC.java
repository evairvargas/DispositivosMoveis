package com.vargas.appn2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultadoIMC extends AppCompatActivity {

    TextView resultado;
    String strNome, strResult;
    Float floatAltura, floatPeso, floatResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_imc);

        resultado = findViewById(R.id.txtResultado); // parametro para buscar o resultado que vai aparecer na segunda tela

        Intent intent = getIntent(); // criei uma intent chamada intent para pegar o que estava na tela anterior

        strNome = intent.getStringExtra("nome"); // usei o getStringExtra para PEGAR os dados da variável strNome
        floatAltura = Float.parseFloat(intent.getStringExtra("altura")); // usei o getIntExtra para PEGAR os dados da variável intAltura
        floatPeso = Float.parseFloat(intent.getStringExtra("peso")); // usei o getIntExtra para PEGAR os dados da variável intPeso

        floatResultado = floatPeso / (floatAltura * floatAltura); // resultado do cálculo do IMC

        strResult = "Olá, " + strNome + "!";
        strResult = strResult + "\n" + "O resultado do seu IMC é " + floatResultado.toString();

                if (floatResultado < 17) {
                    strResult = strResult + "\n" + "Você está muito abaixo do peso.";
                }
                else if(floatResultado < 18.49){
                    strResult = strResult + "\n" + "Você está abaixo do peso.";
                }
                else if(floatResultado < 24.99){
                    strResult = strResult + "\n" + "Você está no peso normal.";
                }
                else if(floatResultado < 29.99){
                    strResult = strResult + "\n" + "Você está acima do peso.";
                }
                else if(floatResultado < 34.99){
                    strResult = strResult + "\n" + "Você está na faixa de obesidade I.";
                }
                else if(floatResultado < 39.99){
                    strResult = strResult + "\n" + "Você está na faixa de obesidade II (severa).";
                }
                else {
                    strResult = strResult + "\n" + "Você está na faixa de obesidade III (mórbida).";
                }

                resultado.setText(strResult);
    }
}

