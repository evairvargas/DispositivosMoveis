package com.vargas.appcrianastempi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;   // importou o "private Button botao;"
import android.widget.EditText; // importou o "private EditText etValor;"

public class MainActivity extends AppCompatActivity {

    private EditText etValor;   // criação de classe. nome de classe no java, sempre com letra maiúscula
                                // o nome do atributo não precisa ter o mesmo nome do id dado lá no xml, mas pode dar como prática quando se tornarem o mesmo elemento
    private Button botao;       // esta classe aqui nao teve o mesmo nome do id criado no xml, para fins comparativos sobre o que foi dito no comentário acima

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //vinculação dos atributos nos elementos da tela
        etValor = findViewById(R.id.etValor); // procurar uma view pelo Id
                                              // "R.id.etValor" significa que esse 'etValor' está referenciado na classe R, que é a referência criada automaticamente
        botao = findViewById(R.id.btnMultiplicar); // está fazendo referência entre a classe 'botao' com o 'btnMultiplicar' através da classe R

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });
    }
        // criar um método para calcular (o que o sistema fará quando o usuário clicar no botão)
    private void calcular(){
        // pegar o conteúdo que o usuário preencheu, transformar para double, sendo assim possível multiplicar por 2 e devolver ao usuário esse resultado
        String texto = etValor.getText().toString(); // get text to string = pegar o texto inserido pelo usuário e transformar em string
        double valor = Double.valueOf(texto); // chamamos a classe "Double" onde o "valueOf" recebe uma string e retorna um double, guardado dentro da variável "valor"
        // double com d é o valor primitivo (com número flutuante); Double com D é a classe, onde dentro dele possui arquivo para trabalhar com números flutuantes
        double resultado = valor * 2; // o texto inserido pelo usuário, que foi guardado na variável "valor", será multiplicado por 2 e guardada na variável "resultado"

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        // "alertdialog.builder" é um alerta que aparecerá na tela do usuário; esse alerta é chamado de "alerta" neste exemplo
        // para que esta janela apareça na frente da tela, ela precisa receber uma referência da classe que controla a tela.
        // a classe que controla essa tela, é a própria classe, e ela está dentro dela mesmo (main activity)
        // com isso, basta digitar 'this' para que o sistema entenda que o alerta estará ali dentro
        alerta.setTitle("Resultado"); // Setamos/definimos o título do alerta
        alerta.setMessage(String.valueOf(resultado)); //setamos/definimos a mensagem, ou seja, o que aparece no meio do alerta; neste caso, será o resultado
        // para definir/setar esse botão positivo, precisamos definir 2 parâmetros: texto que irá aparecer e uma possível ação
        // neste caso abaixo, o texto será "OK", e o listener será "null" pois não quero fazer nenhuma ação quando o usuário clicar no "ok"
        alerta.setPositiveButton("OK" , null);
        alerta.show(); // linha dedicada a mostrar o alerta na tela
    }

}