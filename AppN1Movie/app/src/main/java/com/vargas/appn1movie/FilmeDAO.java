package com.vargas.appn1movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    public static void inserir(Context context, Filme filme) {
        ContentValues values = new ContentValues();
        values.put("nome", filme.getNome());
        values.put("genero", filme.getGenero());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.insert("filmes", null, values);

    }

    public static void editar(Context context, Filme filme) {
        ContentValues values = new ContentValues();
        values.put("nome", filme.getNome());
        values.put("genero", filme.getGenero());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.update("filmes", values, "id = " + filme.getId(), null);

    }

    public static void excluir(Context context, int idFilme) {

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.delete("filmes", "id = " + idFilme, null);
    }

    public static List<Filme> getFilmes(Context context) {
        List<Filme> lista = new ArrayList<>();

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM filmes ORDER BY nome ", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Filme f = new Filme();
                f.setId(cursor.getInt(0));
                f.setNome(cursor.getString(1));
                f.setGenero(cursor.getString(2));
                lista.add(f);

            } while (cursor.moveToNext());
        }
        return lista;
    }

    public static Filme getFilmeById(Context context, int idFilme) {

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM filmes WHERE id = " + idFilme, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Filme f = new Filme();
            f.setId(cursor.getInt(0));
            f.setNome(cursor.getString(1));
            f.setGenero(cursor.getString(2));
            return f;
        }else{
            return null;
        }
    }
}
