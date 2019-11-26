package br.com.ufrn.imd.lpii.classes.entities;

/**
 *Classe que representa as categorias dos bens(patrimonios).
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, Jose Lucio da Silva Junior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public class Categoria implements Comparable<Categoria> {
    private Integer codigo;
    private String nome;
    private String descricao;

    //Construtores
    public Categoria() {
    }
    /**
     * Construtor.
     *
     * @param codigo codigo unico da categoria a ser cadastrada no banco de dados.
     * @param nome  nome da categoria.
     * @param descricao  descricao da categoria(ex.: Automovel eletrico, moveis de metal, etc...).
     */
    public Categoria(Integer codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    /**
     * Metodo que acessa o codigo da categoria.
     * @return retorna o codigo da categoria.
     */
    public Integer getCodigo() {
        return codigo;
    }
    /**
     *Metodo que modifica o codigo da categoria.
     *@param codigo codigo unico da categoria.
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    /**
     * Metodo que acessa o nome da categoria.
     * @return retorna o nome da categoria.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Metodo que modifica o nome da categoria.
     * @param nome nome da categoria.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Metodo que acessa a descricao da categoria.
     * @return retorna a descricao da categoria.
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Metodo que modifica a descricao da categoria.
     * @param descricao descricao da categoria.
     */
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

    @Override
    public int compareTo(Categoria o) {
        return nome.compareTo(o.nome) ;
    }
}
