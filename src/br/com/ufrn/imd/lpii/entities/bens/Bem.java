package br.com.ufrn.imd.lpii.entities.bens;

import br.com.ufrn.imd.lpii.entities.categoriaDeBem.Categoria;
import br.com.ufrn.imd.lpii.entities.localizacao.Localizacao;

public class Bem {
    private Integer codigo;
    private String nome;
    private String descricao;
    private Localizacao localizacao;
    private Categoria categoria;

    //Construtores
    public Bem(){}

    public Bem(Integer codigo, String nome, String descricao, Localizacao localizacao, Categoria categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.categoria = categoria;
    }

    //getters e setters
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
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

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
