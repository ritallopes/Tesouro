package br.com.ufrn.imd.lpii.classes.entities;

/**
 *Classe que representa as categorias dos bens(patrimônios).
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, José Lúcio da Silva Júnior, Rita de Cassia Lino Lopes
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
     * @param codigo código único da cateogira a ser cadastrada no banco de dados.
     * @param nome  nome da categoria.
     * @param descricao  descrição da categoria(ex.: Automóvel elétrico, móveis de metal, etc...).
     */
    public Categoria(Integer codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    /**
     * Método que acessa o código da categoria.
     * @return retorna o código da categoria.
     */
    public Integer getCodigo() {
        return codigo;
    }
    /**
     *Método que modifica o código da categoria.
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    /**
     * Método que acessa o nome da categoria.
     * @return retorna o nome da categoria.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Método que modifica o nome da categoria.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Método que acessa a descrição da categoria.
     * @return retorna a descrição da categoria.
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Método que modifica a descrição da categoria.
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
