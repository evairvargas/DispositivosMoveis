package com.vargas.appmovien1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {
    
    private EditText etNome;
    private Spinner spCategoria;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById(R.id.etNome); // o 1º etNome, é o que foi criado aqui, o 2º é aquele criado no XML
        spCategoria = findViewById(R.id.spinner); // o 1º etNome, é o que foi criado aqui, o 2º é aquele criado no XML
        btnSalvar = findViewById(R.id.btnSalvar); // o 1º etNome, é o que foi criado aqui, o 2º é aquele criado no XML

        // dizer pro botão que deve chamar o método 'salvar' quando o usuário clicar nele
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void salvar(){
        String nome = etNome.getText().toString();
        if(nome.isEmpty() || spCategoria.getSelectedItemPosition() == 0){ // se o nome tiver vazio ou se o item selecionado for zero...
            Toast.makeText(this, "Você não preencheu todos os campos.", Toast.LENGTH_LONG).show(); // mostrar a tela q vai aparecer, o texto que vai aparecer e por quanto tempo vai aparecer este texto
        }else{
            Filme filme = new Filme();
            filme.setNome(nome);
            filme.setCategoria(spCategoria.getSelectedItem().toString()); //pedi pra trazer apenas a string que tiver ali dentro
            FilmeDAO.inserir(this, filme); // chamei o filmedao, dei a referência desta tela e informei qual filme quero inserir
            etNome.setText(""); // limpa o texto
            spCategoria.setSelection(0);
        }
    }

}