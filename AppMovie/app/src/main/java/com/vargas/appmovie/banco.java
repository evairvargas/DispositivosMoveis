package com.vargas.appmovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class banco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "bancoFilmes"; //o nome do banco é constante privada estática do tipo string, chamada 'bancoFilmes';
    private static final int VERSAO = 1; //a versão do banco é uma constante privada e estática do tipo int, chamada 1;

    public banco(Context context){ /* linha dedicada pra criar o método construtor, do tipo publico, com o mesmo nome da classe*/
        super(context, NOME_BANCO, null, VERSAO); // invocamos o método construtor da superclasse

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( "CREATE TABLE IF NOT EXISTS filmes (  " +
                "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,  " +
                "  nome TEXT NOT NULL , " +
                "  categoria TEXT NOT NULL ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

/*
        Criei uma classe java no meu pacote e chamei de 'banco';
        Mas ela vai herdar da classe SQLiteOpenHelper, por isso coloquei "extends SQLiteOpenHelper";
        ela nos obriga a acrescentar 3 métodos: construtor, onCreate e onUpdate;
        Quando precisar atualizar o app e precisar fazer uma atualização na versão do banco, o próprio android vai chamar o método onUpgrade,
        dentro dela vou programar o que deverá ser feito pra essa atualização ocorrer;
        Criamos 2 constantes pra deixar claro e usar dentro da classe: NOME_BANCO e VERSAO nas linhas 9 e 10;
        Criamos o método construtor com o mesmo nome da classe na linha 12; Com isso, definimos se pode receber parâmetros ou não;
        O método construtor da classe banco, precisa invocar o método construtor da classe SQLiteOpenHelper;
        Para chamar/invocar o método da classe SQLiteOpenHelper (super-classe), usamos o método 'super' dentro da subclasse public Banco();
        Antes, preciso passar um contexto na classe 'banco', pois a classe 'banco' não é uma tela; Vamos receber ela por parâmetro;
        Declaramos o parâmatro do tipo 'context' dentro da classe public banco (linha 12)
        para poder chamar os parâmetros dentro do método 'super' e invocar o método construtor da classe SQLiteOpenHelper;
        Os parâmetros são 4: context, NOME_BANCO, null, VERSAO; todos dentro da linha "super( );"
        1 context - referência da tela que estará sendo usada;
        2 NOME_BANCO - é o nome do banco que vai ser usado;
        3 null - é o cursor factory, ou a forma como os dados serão retornados, definimos de forma nula;
        4 VERSAO - o último parâmtro definido é a versão do banco, já definido no "private static final int VERSAO = 1;";

        botão direito > generate > implement methotds > vai trazer todos os métodos abstratos da superclasse;
        Com isso, a classe não apresentará erro pois terá implantado os dois métodos;
        Conforme informado na linha 34, já temos os 3 métodos criados na classe: construtor, onCreate e onUpdate;
        para usar o banco de dados, precisa criar a estrutura dele;
        Dentro do método public void onCreate, inserimos um comando sqLiteDatabase.execSQL que é de sql, para criar e definir os parâmetros da tabela;
        Esta tabela se chama 'filmes' e tem um Id do tipo inteiro, não nulo, chave primária e autoincrementavel;
        também tem o nome do filme, do tipo texto e não pode ser nulo; por fim, tem uma categoria que não pode ser nula e é do tipo texto;

        No método public void onUpdate, temos no 1° parâmetro o nome do banco do app, no 2° parâmetro é a versão antiga e o 3° parâmetro é a versão nova;
        Dentro do método onUpdate, a gente diz pro android o que foi atualizado no app, pra que ele invoque e possa apresentar na tela do usuário;

        Criamos uma nova classe java indo java > com.vargas.appmovie > botão direito > new > javaclass > filmesDAO;
        Neste padrão DAO, teremos nesta classe todos os métodos referentes aos filmes que irá manipular/trabalhar com o banco;
        A classe produto não terá atributos, apenas métodos, e para nao precisar ir nestas telas onde vamos usar os métodos e instanciar um objeto do tipo DAO com 'new filmesDAO',
        vamos criar métodos de forma estática;
        O CONTEÚDO SEGUE NO ARQUIVO filmes.DAO;



*/