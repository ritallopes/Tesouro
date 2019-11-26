package br.com.ufrn.imd.lpii.exceptions;
/**
 *Classe de excecao caso nao seja encontrada a categoria no banco de dados
 *
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, Jose Lucio da Silva Junior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public class CategoriaNaoEncontradaException extends Exception{
    private static final long serialVersionUID = 1L;
    /**
     * Metodo que retorna a informacao de que a categoria nao foi encontrado.
     * @return retorna mensagem de falha.
     */
    public String getMessage(){
        return "Categoria com parametro especificado não foi encontrado!";
    }
}
