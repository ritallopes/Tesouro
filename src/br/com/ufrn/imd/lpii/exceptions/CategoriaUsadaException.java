package br.com.ufrn.imd.lpii.exceptions;

public class CategoriaUsadaException extends Exception{
    private static final long serialVersionUID = 1L;

    public String getMessage(){
        return "Categoria está sendo usada!";
    }
}
