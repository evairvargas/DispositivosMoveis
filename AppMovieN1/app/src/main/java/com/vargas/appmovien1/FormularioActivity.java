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
    private String acao;
    private Filme filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById(R.id.etNome); // o 1º etNome, é o que foi criado aqui, o 2º é aquele criado no XML
        spCategoria = findViewById(R.id.spinner); // o 1º etNome, é o que foi criado aqui, o 2º é aquele criado no XML
        btnSalvar = findViewById(R.id.btnSalvar); // o 1º etNome, é o que foi criado aqui, o 2º é aquele criado no XML

        acao = getIntent().getStringExtra("acao");
        if (acao.equals("editar")){ // se a açao for igual à editar...
            carregarFormulario(); // ...carregar formulário
        }

        // dizer pro botão que deve chamar o método 'salvar' quando o usuário clicar nele
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void carregarFormulario(){
        int id = getIntent().getIntExtra("idFilme", 0); // o getIntExtra espera receber 2 parâmetros
        filme = FilmeDAO.getFilmesById(this, id);
        etNome.setText(filme.getNome());
        String[] categorias = getResources().getStringArray(R.array.Categorias); // esta linha serve pra buscar as categorias criadas no arquivos strings.xml
        for (int i = 1; i < categorias.length; i++){ // o laço começa na posição 1, pois a posicao 0 é o "selecione a categoria"
            if (filme.getCategoria().equals(categorias[i])){ // se a categoria do filme for igual ao item do array de categoria...
                spCategoria.setSelection(i); // setar ele e para o laço for
                break;
            }
        }
    }

    private void salvar(){
        String nome = etNome.getText().toString();

        if(nome.isEmpty() || spCategoria.getSelectedItemPosition() == 0){ // se o nome tiver vazio ou se o item selecionado for zero...
            Toast.makeText(this, "Você não preencheu todos os campos.", Toast.LENGTH_LONG).show(); // mostrar a tela q vai aparecer, o texto que vai aparecer e por quanto tempo vai aparecer este texto
        }else {

            if (acao.equals("inserir")){
                filme = new Filme();
            }
            filme.setNome(nome);
            filme.setCategoria(spCategoria.getSelectedItem().toString()); //pedi pra trazer apenas a string que tiver ali dentro

            if (acao.equals("inserir")) {
                FilmeDAO.inserir(this, filme); // chamei o filmedao, dei a referência desta tela e informei qual filme quero inserir
                etNome.setText(""); // limpa o texto
                spCategoria.setSelection(0);
            }else{
                FilmeDAO.editar(this, filme);
                finish();
            }
        }
    }

}