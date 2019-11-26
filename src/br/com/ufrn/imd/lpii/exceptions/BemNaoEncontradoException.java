package br.com.ufrn.imd.lpii.exceptions;

/**
 *Classe de excecao caso nao seja encontrado o bem no banco de dados
 *
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, Jose Lucio da Silva Junior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public class BemNaoEncontradoException extends Exception{
    private static final long serialVersionUID = 1L;

    /**
     * Metodo que retorna a informacao de que o bem nao foi encontrado.
     * @return retorna mensagem de falha.
     */
    public String getMessage() {
        return "Bem com parametro especificado não foi encontrado!";
    }
}
