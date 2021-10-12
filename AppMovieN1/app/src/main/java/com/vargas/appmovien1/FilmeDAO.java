package com.vargas.appmovien1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    public static void inserir(Context context, Filme filme){
        ContentValues values = new ContentValues();
        values.put("nome", filme.getNome());
        values.put("categoria", filme.getCategoria());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase(); //pegar uma referência do banco com permissão de escrita

        db.insert("AppMovieN1", null, values);

    }

    public static void editar(Context context, Filme filme){
        ContentValues values = new ContentValues();
        values.put("nome", filme.getNome());
        values.put("categoria", filme.getCategoria());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase(); //pegar uma referência do banco com permissão de escrita

        db.update("AppMovieN1", values, "id = " + filme.getId(), null);

    }
    // para excluir, só preciso do id do filme
    public static void excluir(Context context, int idFilme){


        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase(); //pegar uma referência do banco com permissão de escrita

        db.delete("AppMovieN1", "id = " + idFilme, null);

    }
    //coleção de filmes
    public static List<Filme> getFilmes(Context context){
        List<Filme> lista = new ArrayList<>();

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase(); //pegar uma referência do banco com permissão de leitura

        Cursor cursor = db.rawQuery("SELECT * FROM filmes ORDER BY nome", null); // como usei o * ele vai retornar todas as colunas: id na coluna 0, nome na coluna 1 e categoria na coluna 2

        if(cursor.getCount() > 0){
            cursor.moveToFirst(); // sempre que buscar um novo elemento na tabela, volta pra primeira posição
            do{
                Filme f = new Filme();
                f.setId(cursor.getInt(0)); // passar o index da coluna dentro do parântese
                f.setNome(cursor.getString(1)); // index do nome
                f.setCategoria(cursor.getString(2)); // index da categoria
                lista.add(f); // adicionar na linha o filme que foi criado neste laõ; quando terminar, vai sair do laço e encerrar a lista


            }while (cursor.moveToNext()); // enquanto existir um próximo, este laço irá se repetir
        }

        return lista; // na pior das hipóteses, retorna uma lista sem dados ou, se der certo, retorna com a lista recém criada

    }

    public static Filme getFilmesById(Context context, int idFilme){

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM filmes WHERE id = " + idFilme, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            Filme f = new Filme();
            f.setId(cursor.getInt(0)); // passar o index da coluna dentro do parântese
            f.setNome(cursor.getString(1)); // index do nome
            f.setCategoria(cursor.getString(2)); // index da categoria
            return f;

        }else{
            return null;
        }


    }

}
