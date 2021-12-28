package com.example.a45_databinding.modelo;


import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

public class Usuario extends BaseObservable{

    public ObservableField<String> nome = new ObservableField<>();
    public ObservableField<String> sobrenome = new ObservableField<>();

    public Usuario(String nome, String sobrenome) {
        this.nome.set(nome);
        this.sobrenome.set(sobrenome);
    }
}
