package com.vargas.appmovien1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvFilmes;
    private ArrayAdapter adapter;
    private List<Filme> listaDeFilmes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvFilmes = findViewById(R.id.lvFilmes);

        carregarFilmes();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });

        //quando o usuário clicar em 1 dos itens, vamos carregar o formulario para editar caso ele queira
        lvFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idFilme = listaDeFilmes.get(position).getId();
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra("acao", "editar"); // ação de editar o item existente que o usuário clicou
                intent.putExtra("idFilme",idFilme);
                startActivity(intent);
            }
        });

        lvFilmes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                excluir(position);
                return true;
            }
        });

    }

    private void excluir(int posicao){
        Filme filme = listaDeFilmes.get(posicao);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluir");
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setMessage("Confirma a exclusão " + filme.getNome() +"?");
        alerta.setNeutralButton("Cancelar",null);

        // quando ele clicar em excluir, fazer o seguinte passo:
        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                FilmeDAO.excluir(MainActivity.this, filme.getId());
                carregarFilmes();
            }
        });
        alerta.show();

    }


    // quando esta tela for reiniciada, a lista será atualizada
    @Override
    protected void onRestart() {
        super.onRestart();
        carregarFilmes();
    }

        private void carregarFilmes(){

        listaDeFilmes =  FilmeDAO.getFilmes(this); // a referência é esta tela mesmo
        if(listaDeFilmes.size() == 0) {
            Filme teste = new Filme("Lista Vazia!","NULL");
            listaDeFilmes.add(teste);
            lvFilmes.setEnabled(false); //desabilitar a tecla para o usuário nao possa editar este filme teste
        }else{
            lvFilmes.setEnabled(true); // se não tiver vazia, vai habilitar o listview
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeFilmes);
        lvFilmes.setAdapter(adapter);

    }

// bloco para criar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // bloco para manipular as ações do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}