package br.com.ufrn.imd.lpii.entities.localizacao;

public class Localizacao {
    private String nome;
    private String descricao;

    //Construtores
    public Localizacao() {
    }

    public Localizacao(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
