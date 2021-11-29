package com.vargas.appn1movie;

public class Filme {

    private String id;
    public int ano, nota;
    public String nome, genero;


    public Filme() {

    }

    public Filme(String nome, String genero) {
        this.nome = nome;
        this.genero = genero;
    }

    @Override
    public String toString() {
        return nome + "-" + genero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
