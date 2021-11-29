package com.vargas.appn1movie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener; // objeto para perceber o que está acontecendo no firebase
    private Query query; // vamos usar isto para definir para qual child de dentro do banco nós vamos monitorar

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance(); // iniciar uma instancia do firebaseauth
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //verificar se tem um usuario logado
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    finish();
                }
            }
        };

        auth.addAuthStateListener(authStateListener);


            lvFilmes = findViewById(R.id.lvFilmes);

        listaDeFilmes = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeFilmes);
        lvFilmes.setAdapter(adapter); // sempre que setar novo filme, vai atualizar a lista

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra("acao","inserir");
                startActivity(intent);
            }
        });

        lvFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Filme filmeSelecionado = listaDeFilmes.get(position);
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra("acao","editar");
                intent.putExtra("idFilme", filmeSelecionado.getId());
                //intent.putExtra("nome", filmeSelecionado.getNome());
                //intent.putExtra("genero",filmeSelecionado.getGenero());
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
        alerta.setTitle("EXCLUIR");
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setMessage("Confirma a exclusão?");
        alerta.setNeutralButton("Cancelar",null);

        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  FilmeDAO.excluir(MainActivity.this, filme.getId());
                // carregarFilmes();
                reference.child("filmes").child(filme.getId()).removeValue(); // removendo a child que for igual a id do produto
            }
        });
        alerta.show();
    }


    @Override
    protected void onRestart() {

        super.onRestart();
        //carregarFilmes();
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarFilmes();
    }

    private void carregarFilmes(){

        listaDeFilmes.clear(); // limpando a lista de filmes

        database = FirebaseDatabase.getInstance(); // pegando uma instancia do database
        reference = database.getReference(); // pegamos uma referencia do banco
        query = reference.child("filmes").orderByChild("nome"); // Criada uma consulta para buscar no nosso banco apenas os childs filmes, ordenadas por nome

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // quando um child for adicionado
                Filme f1 = new Filme(); // cria um novo filme
                f1.setId(snapshot.getKey()); // setar o id e retorna a chave que foi gerada
                f1.setNome(snapshot.child("nome").getValue(String.class)); // informo o nome
                f1.setGenero(snapshot.child("genero").getValue(String.class)); // informo o gênero

                listaDeFilmes.add(f1); // adicionamos o filme na lista
                adapter.notifyDataSetChanged(); // notificamos o adapter de q houve alteração/adição de novo elemento
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // quando um child for modificado
                String idFilme = snapshot.getKey(); // pegamos a chave do filme
                for (Filme filme: listaDeFilmes) { // procuro esta chave na lista de filmes
                    if (filme.getId().equals(idFilme)) { // se o id do nosso filme aqui de dentro, for igual ao id do filme que foi removido lá do firebase...
                        filme.setNome(snapshot.child("nome").getValue(String.class)); // seta o novo nome
                        filme.setGenero(snapshot.child("genero").getValue(String.class)); // seta a nova categoria
                        adapter.notifyDataSetChanged(); // notificamos o adapter da mudança do elemento
                        break; // pausar o laço
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // quando um child for removido
                String idFilme = snapshot.getKey(); // pegamos o id
                for (Filme filme: listaDeFilmes) { // procuramos na lista
                    if (filme.getId().equals(idFilme)) { // se o id do nosso filme aqui de dentro, for igual ao id do filme que foi removido lá do firebase...
                        listaDeFilmes.remove(filme); // ... entao removemos ele da lista
                        adapter.notifyDataSetChanged(); // notificamos o adapter da mudança/exclusão de elemento
                        break; // pausar o laço
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // quando um child for movido
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // quando um child for cancelado
            }
        };

        query.addChildEventListener(childEventListener); // adicionamos um objeto do tipo child event listener para controlar os eventos que podem ocorrer no child desta query

        /* if(listaDeFilmes.size() == 0) {
            Filme fake = new Filme("Lista Vazia", "NULL");
            listaDeFilmes.add(fake);
            lvFilmes.setEnabled(false);
        }else{
            lvFilmes.setEnabled(true);
        } */

    }

    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(childEventListener); // serve para parar de carregar o listener enquanto nao estamos nesta tela
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //menu.add("Sair");

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuAddFilme) {
            Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
            intent.putExtra("acao","inserir");
            startActivity(intent);
            return true;
        }

        if(id == R.id.menuSair){
            FirebaseAuth auth = FirebaseAuth.getInstance(); // criado objeto dentro do proprio options item selected
            auth.signOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}