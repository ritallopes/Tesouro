package br.com.ufrn.imd.lpii.classes.entities;

/**
 *Classe que representa um bem(patrimônio).
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, José Lúcio da Silva Júnior, Rita de Cassia Lino Lopes
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
     * @param codigo código único do bem que será usado para cadastrar o bem no banco de dados.
     * @param nome  nome do bem.
     * @param tombo  registro do bem em alguma localização.
     * @param descricao  descrição do bem(ex.: cor, tamanho, etc...).
     * @param localizacao  localização em que se encontra o bem.
     * @param categoria  categoria do bem(ex.: móvel, eletrodoméstico, etc...).
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
     * Método que acessa o código do bem.
     * @return retorna o código do bem.
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     *Método que modifica o código do bem
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * Método que acessa o nome do bem.
     * @return retorna o nome do bem.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que modifica o nome do bem.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método que acessa o tombo.
     * @return retorna o tombo.
     */
    public String getTombo() {
        return tombo;
    }

    /**
     * Método que acessa a descrição do bem.
     * @return retorna a descrição do bem.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Método que modifica a descrição do bem.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Método que acessa a localização do bem.
     * @return retorna a localização do bem.
     */
    public Localizacao getLocalizacao() {
        return localizacao;
    }

    /**
     * Método que modifica a localização do bem.
     */
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * Método que acessa a categoria do bem.
     * @return retorna a categoria do bem.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Método que modifica a categoria do bem.
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
