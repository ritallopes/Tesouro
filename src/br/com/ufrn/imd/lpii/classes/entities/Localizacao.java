package br.com.ufrn.imd.lpii.classes.entities;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

/**
 *Classe que representa as localizações em que os bens(patrimônios) podem ser alocados.
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, José Lúcio da Silva Júnior, Rita de Cassia Lino Lopes
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
     * @param codigo código único da localização a ser cadastrada no banco de dados.
     * @param nome  nome da localização(ex.: Natal, Mossoró,etc...).
     * @param descricao  descrição da localização(ex.: quente, mais quente ainda, etc...).
     */
    public Localizacao(Integer codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    /**
     * Método que acessa o código da localização.
     * @return retorna o código da localização.
     */
    public Integer getCodigo() {
        return codigo;
    }
    /**
     *Método que modifica o código da localização.
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    /**
     * Método que acessa o nome da localização.
     * @return retorna o nome da localização.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Método que modifica o nome da localização.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Método que acessa a descrição da localização.
     * @return retorna a descrição da localização.
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Método que modifica a descrição da localização.
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