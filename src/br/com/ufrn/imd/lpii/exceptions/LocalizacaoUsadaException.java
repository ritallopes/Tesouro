package br.com.ufrn.imd.lpii.exceptions;

public class LocalizacaoUsadaException extends Exception{
    private static final long serialVersionUID = 1L;

    public String getMessage(){
        return "Localização está sendo usada!";
    }
}
