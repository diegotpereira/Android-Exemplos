package br.java.a07_adapter.modelo;

public class Carro {

    public String modelo;
    public int ano;
    public int fabircante;
    public boolean gasolina;
    public boolean etanol;

    public Carro(String modelo, int ano, int fabircante, boolean gasolina, boolean etanol) {
        this.modelo = modelo;
        this.ano = ano;
        this.fabircante = fabircante;
        this.gasolina = gasolina;
        this.etanol = etanol;
    }
}
