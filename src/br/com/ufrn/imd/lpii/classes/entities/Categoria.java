package br.com.ufrn.imd.lpii.classes.entities;

public class Categoria {
    private Integer codigo;
    private String nome;
    private String descricao;

    //Construtores
    public Categoria() {
    }

    public Categoria(Integer codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
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

    @Override
    public String toString() {
        return "Codigo: "+this.codigo.toString()+"\n"+"" +
                "Nome: "+this.nome+"\n"+
                "Descricao: "+this.descricao+"\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Categoria)){
            return false;
        }
        return (this.codigo == ((Categoria)obj).getCodigo());
    }
}
