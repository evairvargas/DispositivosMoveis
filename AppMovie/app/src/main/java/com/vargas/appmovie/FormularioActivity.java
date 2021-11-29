package com.vargas.appmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.Touch;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    // criação de 3 objetos, com o mesmo tipo de objeto da tela que manipula-se;
    private EditText etNome;
    private Spinner spCategoria;
    private Button btnSalvar;

    // dentro do método abaixo, vou inicializar esses atributos, referenciando com os elementos da tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById(R.id.etNome); // etNome é o atributo criado na linha 13, o etNome de dentro do  parêntese é aquele que foi criado no xml, pois estamos referenciando ele;
        spCategoria = findViewById(R.id.spCategoria); // referencia ao spCategoria criado no xml;
        btnSalvar = findViewById(R.id.btnSalvar); // referencia ao btnButton criado no xml;

        //Setei na linha abaixo o que fazer quando o usuário clicar no botão salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    salvar(); // quando o usuário clicar no botão "salvar", o método salvar será chamado para armazenar a info no banco
            }
        });
    }
    //criaçao do método salvar para que possa armazenar o conteúdo inserido pelo usuário:

    private void salvar(){
        String nome = etNome.getText().toString(); // nesta linha, obriga-se que o usuário sempre salve algo que tenha pelo menos um nome;
        // SE o nome estiver vazio OU a categoria for igual a 0...
        if (nome.isEmpty() || spCategoria.getSelectedItemPosition() == 0 ){
        // Vamos mostrar uma mensagem na tela para que ele revise o que foi preenchido:
        //"Toast.makeText" vai mostrar uma mensagem na tela dele; esse comando pede 3 parâmetros: onde vai aparecer, o que vai (texto) e por quanto tempo;
            Toast.makeText(this, "Há campos vazios.", Toast.LENGTH_LONG).show(); // este ".show()" ao final da linha é para mostrar a mensagem na tela;
        }else{
            Filme filme = new Filme(); // criei um objeto do tipo produto;
            filme.setNome(nome); // setei o nome, e a variável será ela própria;
            filme.setCategoria(spCategoria.getSelectedItem().toString()); //como o spinner é do tipo string, puxei via string puxando o item selecionado mas pedi apenas o texto usando o "toString";

            filmesDAO.inserir(this,filme); // chamei a classe 'filmesDAO.inserir'para inserir, dando o contexto da tela que estou e o filme que quero cadastrar;

            etNome.setText(""); //aqui será feita a limpeza do campo;
            spCategoria.setSelection(0,true); // aqui vamos limpar o spinner (jogar ele pra 1ª posição) com 2 parâmetros: posição 0 e true para que ele fique "animado" na tela;
        }
    }
}