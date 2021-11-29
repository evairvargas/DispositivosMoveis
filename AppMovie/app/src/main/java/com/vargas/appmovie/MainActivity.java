package com.vargas.appmovie;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvMovies;
    private ArrayAdapter adapter;
    private List<Filme> listaDeFilmes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); // A ligação entre a classe e um arquivo xml é esta linha, por isso o R.layout.activity_main
        lvMovies = findViewById(R.id.lvMovies);
        carregarFilmes();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //as linhas abaixo são para criar uma relação/ligação entre a tela que estou para a tela que quero ir;
                Intent intent = new Intent(MainActivity.this,FormularioActivity.class); // tem 2 parâmetrros: a tela que estou "MainActivity" e tela que quero ir "FormularioActivity;
                startActivity(intent); // startei a activity informando a intent criada na linha acima, para que quando clicar no botão, o app vai direcionar pra tela de formulário;
            }
        });
    }

    //Toda vez que esta tela for reiniciada após alguma ação feita em outra tela, a lista será atualizada através deste bloco abaixo:
    @Override
    protected void onRestart() {
        super.onRestart();
        carregarFilmes(); // quando a tela reiniciar, a lista será carregada com a devida atualização;
    }

    private void carregarFilmes(){

        listaDeFilmes = filmesDAO.getFilmes(this); // listaDeFilmes recebe o filmesDAO (pois os métodos que manipulam dados estão dentro de filmesDAO), o contexto é essa própria tela;
        if (listaDeFilmes.size() == 0) { //Se o tamanho desta lista for igual a 0, ela vai retornar a lista vazia abaixo:
            // Criei um filme com o nome de 'empty' pra que isso se exiba e o usuário não consiga interagir caso a lista retorne vazia;
            Filme empty = new Filme("Empty List", "Null");
            listaDeFilmes.add(empty);// add o emptylist
            lvMovies.setEnabled(false); // com isso, o usuário não conseguirá editar a lista vazia, pois ela só existe justamente pra mostrar que está vazio;
        }else{
            lvMovies.setEnabled(true); // se tiver alguma quantidade de produto que não seja vazio, ele vai habilitar o listview;
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeFilmes);
        lvMovies.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Serve para criar um menu.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Programar as ações de quando este menu for clicado.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}