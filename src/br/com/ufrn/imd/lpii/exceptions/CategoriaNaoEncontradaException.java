package br.com.ufrn.imd.lpii.exceptions;

public class CategoriaNaoEncontradaException extends Exception{
    private static final long serialVersionUID = 1L;

    public String getMessage(){
        return "Categoria com parametro especificado não foi encontrado!";
    }
}
