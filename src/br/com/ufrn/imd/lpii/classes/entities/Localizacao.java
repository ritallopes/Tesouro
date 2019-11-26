package br.com.ufrn.imd.lpii.classes.entities;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

/**
 *Classe que representa as localizacoes em que os bens(patrimonios) podem ser alocados.
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, Jose Lucio da Silva Junior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public class Localizacao implements Comparable<Localizacao>{


    private Integer codigo;
    private String nome;
    private String descricao;

    //Construtores
    public Localizacao() {
    }
    /**
     * Construtor.
     *
     * @param codigo codigo unico da localizacao a ser cadastrada no banco de dados.
     * @param nome  nome da localizacao(ex.: Natal, Mossoro,etc...).
     * @param descricao  descricao da localizacao(ex.: quente, mais quente ainda, etc...).
     */
    public Localizacao(Integer codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    /**
     * Metodo que acessa o codigo da localizacao.
     * @return retorna o codigo da localizacao.
     */
    public Integer getCodigo() {
        return codigo;
    }
    /**
     *Metodo que modifica o codigo da localizacao.
     * @param codigo codigo unico da localizacao.
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    /**
     * Metodo que acessa o nome da localizacao.
     * @return retorna o nome da localizacao.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Metodo que modifica o nome da localizacao.
     * @param nome nome da localizacao
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Metodo que acessa a descricao da localizacao.
     * @return retorna a descricao da localizacao.
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Metodo que modifica a descricao da localizacao.
     * @param descricao descricao da localizacao.
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
        if (!(obj instanceof Localizacao)){
            return false;
        }
        return (this.codigo == ((Localizacao)obj).getCodigo());
    }


    @Override
    public int compareTo(Localizacao o) {
        return nome.compareTo(o.nome);
    }
}