package br.com.ufrn.imd.lpii.classes.main;


import br.com.ufrn.imd.lpii.classes.gui.Gui;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionBem;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionCategoria;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionLocalizacao;

import javafx.application.Application;

import java.io.IOException;

/**
 *Classe principal.
 *
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, Jose Lucio da Silva Junior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public class Main{
    /**
     * Metodo que cria as tabelas do banco de dados ao iniciar o programa.
     * @return retorna true ou false dependendo do sucesso da operacao de criacao.
     */
    public static Boolean criarTabelas(){

        Boolean retorno;
        ConnectionLocalizacao localizacao = new ConnectionLocalizacao();
        localizacao.conectar();
        retorno = localizacao.criarTabela();
        localizacao.desconectar();

        ConnectionCategoria categoria = new ConnectionCategoria();
        categoria.conectar();
        retorno = categoria.criarTabela();
        categoria.desconectar();

        ConnectionBem bem = new ConnectionBem();
        bem.conectar();
        retorno = bem.criarTabela();
        bem.desconectar();

        return retorno;


    }

    /**
     * Metodo principal.
     *
     * Chama o metodo para criar as tabelas e executa a interface grefica do programa dentro de uma thread.
     * @param args parametro passado pela linha de comando.
     */
    public static void main(String[] args){
        criarTabelas();
        Thread userInterface = new Thread(new Runnable() {
            @Override
            public void run() {
                Application.launch(Gui.class, args);
            }
        });
        userInterface.start();
    }

}
