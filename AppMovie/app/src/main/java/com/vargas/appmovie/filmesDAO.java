package com.vargas.appmovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class filmesDAO {

    public static void inserir (Context context, Filme filme){ //método publico, do tipo estático e sem retorno (void) com o nome de 'inserir'

        ContentValues values = new ContentValues();
        values.put("nome", filme.getNome());
        values.put("categoria", filme.getCategoria());

        banco conn = new banco(context); // recebemos este contexto por parâmetro (pelo comando public static void inserir) e estamos usando ele pra passar para a classe banco;
        SQLiteDatabase db = conn.getWritableDatabase(); // Criamos um SQLiteDatabase com o nome 'db' para buscar uma referência do banco com permissão de ESCRITA pois vamos inserir no banco;

        db.insert("filme", "null", values);

    }

    public static void editar (Context context, Filme filme){ //método publico, do tipo estático e sem retorno (void) com o nome de 'editar'

        ContentValues values = new ContentValues();
        values.put("nome", filme.getNome());
        values.put("categoria", filme.getCategoria());

        banco conn = new banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.update("filme", values, "id = " + filme.getId(), null);
        // chamei o método update pra fazer a atualização de 4 parâmetros:
        // a tabela que vou editar, os valores que quero editar (nome e categoria conforme linhas 29 e 30), uma string pra where e um array de string se for necessário

    }

    public static void excluir (Context context, int idFilme){ //método publico, do tipo estático e sem retorno (void) com o nome de 'excluir'

        banco conn = new banco(context); //conexão com o banco mantida
        SQLiteDatabase db = conn.getWritableDatabase(); //permissao de escrita no banco

        db.delete("filme", "id = " + idFilme, null); //passei o nome da tabela e a cláusula where, além de um terceiro parâmetro nulo

    }
        // o método abaixo vai retornar uma lista de filmes;
        // montei a consulta pra buscar no banco os filmes cadastrados;
        // vai gerar um objeto 'filme' a partir de cada registro que retornar;
        // add numa lista e retornar a lista;


    public static List<Filme> getFilmes(Context context){ // criei uma coleção de filmes chamada 'filme' através do pacote List; foi criado o Context context pra ter uma referência de onde está o banco

        List<Filme> lista = new ArrayList<>();

        banco conn = new banco(context); // recebemos este contexto por parâmetro (pelo comando public static void inserir) e estamos usando ele pra passar para a classe banco;
        SQLiteDatabase db = conn.getReadableDatabase(); // Criamos um SQLiteDatabase com o nome 'db' para buscar uma referência do banco com permissão de LEITURA pois vamos pesquisar no banco;

        //Pra que cada busca efetuada no banco se torne um filme diferente, vamos criar o objeto "Cursor";
        //O cursor terá o mesmo número de posições que tiver na tabela buscada; cada linha terá 1 novo filme e cada coluna, um atributo deste filme;
        Cursor cursor = db.rawQuery("SELECT * FROM filme ORDER BY nome ", null); //dentro do raw.Query eu passo a consulta que quero fazer;
        //A consulta foi: selecione TUDO da tabela filme por ordem crescente de nome

        if (cursor.getCount() > 0) {// se a quantidade de itens do cursor for > 0...
        // entao vamos percorrer pelo o cursor, criar um filme de cada posição retornada e add na lista
            cursor.moveToFirst(); // mover o cursor para a primeira posição, e agora cria um laço de repetição:
            do {
                //pegue os dados do primeiro filme e...
                Filme filme = new Filme();
                filme.setId(cursor.getInt(0)); // vou buscar do meu cursor o id que é um número inteiro e dentro dele vamos passar o index do Id;
                filme.setNome(cursor.getString(1)); //vai buscar a string com o nome, no index 1 do banco;
                filme.setCategoria(cursor.getString(2)); //vai buscar a categoria em formato string no index 2;
                lista.add(filme); // vai adicionar na lista o filme que acabei de criar nesta busca;
            }while (cursor.moveToNext()); // ... vá pro próximo, enquanto existir um próximo; se não tiver mais, vai retornar false e sair do laço, retornando a tabela.
        }
        // se não, retornar a lista vazia;
        return lista; // para retornar uma lista vazia, caso ocorra algum problema na construção
    }

    public static Filme getFilmeById(Context context, int idFilme){ // Este método retorna apenas 1 filme, por isso se chama "getFilmeById" e add a info pra receber o Id deste produto

        banco conn = new banco(context); // recebemos este contexto por parâmetro (pelo comando public static void inserir) e estamos usando ele pra passar para a classe banco;
        SQLiteDatabase db = conn.getReadableDatabase(); // Criamos um SQLiteDatabase com o nome 'db' para buscar uma referência do banco com permissão de LEITURA pois vamos pesquisar no banco;

        // O cursor seguirá sendo usado pra fazer a busca, conforme parâmetro abaixo;
        Cursor cursor = db.rawQuery("SELECT * FROM filme WHERE id = " + idFilme, null); //dentro do raw.Query eu passo a consulta que quero fazer;
        //A consulta foi: selecione TUDO da tabela filme onde o Id for igual ao Id que eu receber do "idFilme";

        if (cursor.getCount() > 0) {// se a quantidade de itens do cursor for > 0...
                                    // entao vamos percorrer pelo cursor
            cursor.moveToFirst();   // mover o cursor para a primeira posição mas sem o laço, pois vai retornar apenas 1 filme

                Filme filme = new Filme();
                filme.setId(cursor.getInt(0)); // vou buscar do meu cursor o id que é um número inteiro e dentro dele vamos passar o index do Id;
                filme.setNome(cursor.getString(1)); //vai buscar a string com o nome, no index 1 do banco;
                filme.setCategoria(cursor.getString(2)); //vai buscar a categoria em formato string no index 2;
                return filme; // por fim, retorna o filme;
            }else { // caso nao retorne o filme buscado no if, a busca retornará nula
                return null;
            }
    }

}


/*
      Criamos uma nova classe java indo java > com.vargas.appmovie > botão direito > new > javaclass > filmesDAO;
      Neste padrão DAO, teremos nesta classe todos os métodos referentes aos filmes que irá manipular/trabalhar com o banco;
      A classe produto não terá atributos, apenas métodos, e para nao precisar ir nestas telas onde vamos usar os métodos e instanciar um objeto do tipo DAO com 'new filmesDAO',
      vamos criar métodos de forma estática;

      Criamos um método publico, do tipo estático e sem retorno (void) com o nome de 'inserir'
      Este método recebe 2 parâmetros: um contexto e o filme que quero inserir;
      ESTA CLASSE NÃO É UMA TELA, DESTA FORMA, PRECISO RECEBER O CONTEXTO E O FILME POR PARÂMETRO;

      É possível dar um insert no banco no modo tradicional, como um sql, mas podemos usar parâmetros do próprio pacote SQLite e chamar os métodos, passndo apenas os valores;
      Pra isso, precisamos chamar o "ContentValues' e chamar ele de 'values', atribuindo a ele o ContentValues;

      Agora, vamos criar os valores e eles irão entrar neste ContentValues;

      No banco, criamos 3 colunas: id, nome e categoria na tabela filmes;
      Chamei o values e o método 'put' pra adicionar nestes values, os valores que quero inserir no banco, a chave valor será o nome da coluna e depois o nome do filme;
      Como eles virão por parâmetro, ao invés de passar um nome fixo, deixo apenas o parâmetro no "values.put";
      A chave é o nome da coluna, que se chama 'nome', e o valor que é o retorno do método getNome, do filme que estou recebendo por parâmetro no comando "filme.getNome()"
      A chave é o nome da coluna, que se chama 'categoria', e o valor que é o retorno do método getCategoria, da categoria que estou recebendo por parâmetro no comando "filme.getCategoria()"

      Os atributos estão prontos, mas precisamos inserir dentro do banco;
      Vamos criar um objeto do tipo 'banco' com o nome 'conn' para criar a conexão com o banco;
      Na classe banco, criamos um método construtor que recebe um contexto, e então passamos este contexto para o banco;
      Criamos um SQLiteDatabase com o nome 'db' para buscar uma referência do banco com permissão de ESCRITA pois vamos inserir no banco;
      Já temos a referência e os valores que queremos inserir no banco, então vamos chamar um 'db.insert' que já vem pré definido pra receber o nome da tabela, o null column hack e os valores;
      na linha do "db.insert" colocamos o nome da tabela (filme), o nullcolumnhack (null) e os valores (values) que criamos nas linhas acima;
      No SQLite, caso mande um insert sem dado pra nenhuma coluna, ele dá erro e corrompe o arquivo do banco,
      então, quem criou o método insert, criou o parâmetro  nullcolumnhack pra passar um valor pra pelo menos 1 coluna que seja default, para caso este values nao tenha nada, entao ele pegue o valor default;

      Depois de criar o método 'inserir', vamos copiar suas estrutura e colar abaixo, trocando o seu nome para 'editar' e o 'db.insert' para 'db.update';
      O método update recebe 4 parâmetros: o nome da tabela, os valores que queremos editar, uma string para a cláusula WHERE, e um array de string caso tenha mais alguma cláusula WHERE;
      Passamos o nome da tabela, os valores, e teremos que criar uma cláusula where para editar apenas no filme que for respectivo daquele id;
      Também concatenamos com o "filme.getId()" e o último parâmetro será nulo (não teremos mais outra cláusula where);

      Para excluir, precisamos apenas do Id, e não se faz necessários os ContentValues nem o filme inteiro; por isso comentei as linhas do bloco 'excluir' que não preciso usar
      Com isso, alteramos o 2° parâmetro da linha "public static void excluir" para um 'int' e receber apenas o id do filme;
      Chamamos o método 'delete' e precisamos passar o nome da tabela, a cláusula where e mais um array de cláusula where, caso precisar;
      O método 'excluir' se alterou apenas o tipo de parâmetro, puxando apenas o id do filme (ficou int idFilme);
      Comentamos os values e mantemos a conexão com o banco, pegando uma permissão de escrita no banco;
      Executamos o delete no banco, passando o nome da tabela, a cláusula where e o 3° dado é nulo;

      Agora, vamos criar um método pra buscar os filmes no banco;
      Este método vai retornar uma lista de filmes, onde nós vamos montar uma consulta, buscar no banco os filmes cadastrados,
      gerar um objeto 'filme' a partir de cada objeto que for retornado, adicionar eles numa lista e retornar esta lista;

      Chamamos a classe List do pacote java.util pois estamos criando uma coleção de filmes;
      Dentro do comando "public static List", coloquei "Filme" para dizer que será uma coleção de filmes;
      Dei ao nome no método, e pois como vou buscar os filmes no banco, chamei de "getFilmes";
      Precisamos passar o parãmetro de referência da tela que está acessando o banco, então definimos isso no "getFilmes" como Context context;
      As linhas "List<Filme> lista = new ArrayList<>();" e "return lista;" são para que, na pior das hipóteses, vai retornar uma lista vazia;

      Precisamos criar a conexão com o banco e uma permissão de leitura para buscar estes dados do banco;
      Para que, cada registro que eu buscar no banco eu consiga criar um produto diferente, vou criar o objeto "cursor";
      O cursor terá o mesmo número de posições que tiver na tabela buscada; cada linha terá 1 novo filme e cada coluna, um atributo deste filme;


 */