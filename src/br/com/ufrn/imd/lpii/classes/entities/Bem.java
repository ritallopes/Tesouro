package br.com.ufrn.imd.lpii.classes.entities;

/**
 *Classe Bem - representação do bem(patrimônio).
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, José Lúcio da Silva Júnior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 * hehe
 */
public class Bem implements Comparable<Bem> {
    /**
     * Param
     */
    private Integer codigo;
    private String nome;
    private String tombo;
    private String descricao;
    private Localizacao localizacao;
    private Categoria categoria;

    //Construtores
    public Bem(){}

    public Bem(Integer codigo, String nome, String tombo, String descricao, Localizacao localizacao, Categoria categoria) {
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

    public String getTombo() {
        return tombo;
    }

    public void setTombo(String tombo) {
        this.tombo = tombo;
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


    @Override
    public String toString() {
        return "Codigo: "+this.codigo.toString()+"\n"+"" +
                "Nome: "+this.nome+"\n"+
                "Descricao: "+this.descricao+"\n"+
                "Nome da Categoria: " + this.categoria.getNome()+"\n" +
                "Localizacao : "+ this.localizacao.getNome()+"\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bem)){
            return false;
        }
        return ((this.codigo == ((Bem)obj).getCodigo()) && (this.categoria == ((Bem)obj).getCategoria()) && (this.localizacao == ((Bem)obj).getLocalizacao()));
    }

    @Override
    public int compareTo(Bem o) {
        return nome.compareTo(o.nome);
    }
}
