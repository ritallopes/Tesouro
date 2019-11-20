package br.com.ufrn.imd.lpii.exceptions;

public class LocalizacaoNaoEncontradaException extends Exception
{
    private static final long serialVersionUID = 1L;

    public String getMessage(){
        return "Localizaçao com parametro especificado não foi encontrado!";
    }
}
