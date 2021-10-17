package com.vargas.appn1movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome;
    private Spinner spGeneros;
    private Spinner spAno;
    private Spinner spNota;
    private Button btnSalvar;
    private String acao;
    private Filme filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById(R.id.etNome);
        spGeneros = findViewById(R.id.spGeneros);
        spAno = findViewById(R.id.spAno);
        spNota = findViewById(R.id.spNota);
        btnSalvar = findViewById(R.id.btnSalvar);

        acao = getIntent().getStringExtra("acao");
        if(acao.equals("editar")){
            carregarFormulario();
        }
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void carregarFormulario(){
        int id = getIntent().getIntExtra("idFilme",0);
        filme = FilmeDAO.getFilmeById(this, id);
        etNome.setText(filme.getNome());
        String[] generos = getResources().getStringArray(R.array.Generos);
        for (int i = 1; i < generos.length; i++){
            if(filme.getGenero().equals(generos[i])){
                spGeneros.setSelection(i);
                break;
            }
        }
    }

    private void salvar(){
        String nome = etNome.getText().toString();
        if(nome.isEmpty() || spGeneros.getSelectedItemPosition() == 0 ){
            Toast.makeText(this, "Você deve preencher todos os campos.", Toast.LENGTH_LONG).show();
        }else{

            if(acao.equals("inserir")) {
                filme = new Filme();
            }
            filme.setNome(nome);
            filme.setGenero(spGeneros.getSelectedItem().toString());

            if(acao.equals("inserir")) {
                FilmeDAO.inserir(this, filme);
                etNome.setText("");
                spGeneros.setSelection(0);
            }else {
                FilmeDAO.editar(this, filme);
                finish();
            }
        }
    }

}