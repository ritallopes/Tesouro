package br.com.ufrn.imd.lpii.exceptions;

public class BemNaoEncontradoException extends Exception{
    private static final long serialVersionUID = 1L;

    public String getMessage() {
        return "Bem com parametro especificado não foi encontrado!";
    }
}
