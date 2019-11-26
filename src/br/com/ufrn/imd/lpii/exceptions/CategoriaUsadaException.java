package br.com.ufrn.imd.lpii.exceptions;
/**
 *Classe de excecao caso a categoria esteja sendo utilizada.
 *
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, Jose Lucio da Silva Junior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public class CategoriaUsadaException extends Exception{
    private static final long serialVersionUID = 1L;
    /**
     * Metodo que identifica se a categoria esta sendo usada.
     * @return retorna mensagem caso a categoria esteja em uso.
     */
    public String getMessage(){
        return "Categoria está sendo usada!";
    }
}
