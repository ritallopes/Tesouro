package br.com.ufrn.imd.lpii.classes.entities.categoriaDeBem;

public class Categoria {
    private String codigo;
    private String nome;
    private String descricao;

    //Construtores
    public Categoria() {
    }

    public Categoria(String codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

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
