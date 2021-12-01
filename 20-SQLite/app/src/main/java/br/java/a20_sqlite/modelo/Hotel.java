package br.java.a20_sqlite.modelo;

import java.io.Serializable;

public class Hotel implements Serializable {

    public long id;
    public String nome;
    public String endereco;
    public float estrelas;

    public Hotel(String nome, String endereco, float estrelas) {
        this.nome = nome;
        this.endereco = endereco;
        this.estrelas = estrelas;
    }

    public Hotel(long id, String nome, String endereco, float estrelas) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.estrelas = estrelas;
    }

    @Override
    public String toString() {
        return nome;
    }
}
