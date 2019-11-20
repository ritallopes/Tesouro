package br.com.ufrn.imd.lpii.classes.main;

import br.com.ufrn.imd.lpii.classes.gui.Gui;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionBem;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionCategoria;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionLocalizacao;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.text.Text;

import java.io.IOException;


public class Main{

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

    public static void main(String[] args) throws IOException {
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
