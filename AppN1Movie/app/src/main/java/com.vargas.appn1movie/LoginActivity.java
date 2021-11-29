package com.vargas.appn1movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnEntrar, btnCadastrar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener; // vai ficar monitorando se está logado ou nao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar();
            }
        });

        auth = FirebaseAuth.getInstance(); // iniciar uma instancia do firebaseauth

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //verificar se tem um usuario logado
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        auth.addAuthStateListener(authStateListener);

    }

    private void entrar(){

        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Insira o e-mail e a senha.",Toast.LENGTH_LONG).show(); // .show() no final para mostrar a mensagem em tempo longo
        }else{
            // logar com email e senha
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "E-mail ou senha inválidos.", Toast.LENGTH_LONG).show(); // se der erro no login, mostra esta mensagem
                    }
                }
            });
        }



    }

    private void cadastrar(){

        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Insira o e-mail e a senha.",Toast.LENGTH_LONG).show(); // .show() no final para mostrar a mensagem em tempo longo
        }else{

            // na linha abaixo, chamei o método de criar user com email e senha, passando por parametro o email e senha informados
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //startActivity(intent);
                    }
                }
            });
        }

    }
}