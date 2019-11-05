package br.com.ufrn.imd.lpii.classes.main;

import br.com.ufrn.imd.lpii.classes.persistence.ConnectionBem;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionCategoria;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionLocalizacao;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionSQLite;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ConnectionCategoria connectionCategoria = new ConnectionCategoria();
        connectionCategoria.conectar();
        connectionCategoria.criarTabela();

        connectionCategoria.cadastrarCategoria( "categoria1", "teste de categoria");
        connectionCategoria.cadastrarCategoria("categoria2", "teste de categoria");
        connectionCategoria.cadastrarCategoria( "categoria2", "teste de categoria");

        ConnectionLocalizacao connectionLocalizacao = new ConnectionLocalizacao();
        connectionLocalizacao.conectar();
        connectionLocalizacao.criarTabela();

        connectionLocalizacao.cadastrarLocalizacao( "Localizacao1", "teste de Localizacao");


        ConnectionBem connectionBem = new ConnectionBem();
        connectionBem.conectar();
        connectionBem.criarTabela();

        connectionBem.cadastrarBem( "Localizacao1", "teste de Localizacao", 1, 1);




        /*
        ArrayList<HashMap<String, String> > campos = connectionCategoria.listarCategoria();


        for (HashMap<String, String> tupla  : campos){
            for (String key : tupla.keySet()){
                System.out.println(key +" : "+ tupla.get(key));
            }
            System.out.println();
        }*/

       // Bot.inicializacaoBot("1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc");
        connectionBem.desconectar();
        connectionLocalizacao.desconectar();

        connectionCategoria.desconectar();


    }
}
