package com.vargas.appmovien1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "AppMovieN1";
    private static final int VERSAO = 1;

    public Banco(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override // Criação da estrutura do banco de dados
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS filmes (" + " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + " nome TEXT NOT NULL , " + " categoria TEXT )");
    }

    @Override // Este método é para quando o usuário atualizar o app
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
