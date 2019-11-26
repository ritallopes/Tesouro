package br.com.ufrn.imd.lpii.classes.entities;

/**
 *Classe que representa um bem(patrimonio).
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, Jose Lucio da Silva Junior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public class Bem implements Comparable<Bem> {
    private Integer codigo;
    private String nome;
    private String tombo;
    private String descricao;
    private Localizacao localizacao;
    private Categoria categoria;

    //Construtores
    public Bem(){}

    /**
     * Construtor.
     *
     * @param codigo codigo unico do bem que sera usado para cadastrar o bem no banco de dados.
     * @param nome  nome do bem.
     * @param tombo  registro do bem em alguma localização.
     * @param descricao  descricao do bem(ex.: cor, tamanho, etc...).
     * @param localizacao  localizacao em que se encontra o bem.
     * @param categoria  categoria do bem(ex.: movel, eletrodomestico, etc...).
     */
    public Bem(Integer codigo, String nome, String tombo, String descricao, Localizacao localizacao, Categoria categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.categoria = categoria;
    }

    //getters e setters

    /**
     * Metodo que acessa o codigo do bem.
     * @return retorna o codigo do bem.
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     *Metodo que modifica o codigo do bem
     * @param codigo codigo unico do bem.
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * Metodo que acessa o nome do bem.
     * @return retorna o nome do bem.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo que modifica o nome do bem.
     * @param nome nome do bem.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo que acessa o tombo.
     * @return retorna o tombo.
     */
    public String getTombo() {
        return tombo;
    }

    /**
     * Metodo que acessa a descricao do bem.
     * @return retorna a descricao do bem.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Metodo que modifica a descricao do bem.
     * @param descricao descricao do bem.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Metodo que acessa a localizacao do bem.
     * @return retorna a localizacao do bem.
     */
    public Localizacao getLocalizacao() {
        return localizacao;
    }

    /**
     * Metodo que modifica a localizacao do bem.
     * @param localizacao localizacao do bem.
     */
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * Metodo que acessa a categoria do bem.
     * @return retorna a categoria do bem.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Metodo que modifica a categoria do bem.
     * @param categoria categoria do bem.
     */
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
