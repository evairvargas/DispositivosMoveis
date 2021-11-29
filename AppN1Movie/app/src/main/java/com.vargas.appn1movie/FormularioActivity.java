package com.vargas.appn1movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome;
    private Spinner spGeneros;
    private Spinner spAno;
    private Spinner spNota;
    private Button btnSalvar;
    private String acao;
    private Filme filme;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

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

        database = FirebaseDatabase.getInstance(); // essa instancia ocorre pelo arquivo json
        reference = database.getReference(); // estou pegando aqui a referência do banco inteiro

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
        String id = getIntent().getStringExtra("idFilme");
        //filme = FilmeDAO.getFilmeById(this, id);
        filme = new Filme();
        filme.setId(id);
        filme.setNome(getIntent().getStringExtra("nome"));
        filme.setGenero(getIntent().getStringExtra("genero"));
        //filme.setNome(reference.child("filmes").child(id).child("nome").get().getResult().getValue(String.class));
        //filme.setGenero(reference.child("filmes").child(id).child("genero").toString());

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
                //  FilmeDAO.inserir(this, filme);
                reference.child("filmes").push().setValue(filme); //
                etNome.setText("");
                spGeneros.setSelection(0);
            }else {
                //  FilmeDAO.editar(this, filme);
                reference.child("filmes").child(filme.getId()).setValue(filme);
                finish();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
     getMenuInflater().inflate(R.menu.menu_main, menu);
     return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSair){
            auth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
}