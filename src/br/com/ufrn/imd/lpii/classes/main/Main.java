package br.com.ufrn.imd.lpii.classes.main;

import br.com.ufrn.imd.lpii.classes.persistence.ConnectionCategoria;
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

        ArrayList<HashMap<String, String> > campos = connectionCategoria.listarCategoria();


        for (HashMap<String, String> tupla  : campos){
            for (String key : tupla.keySet()){
                System.out.println(key +" : "+ tupla.get(key));
            }
            System.out.println();
        }

       // Bot.inicializacaoBot("1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc");
        connectionCategoria.desconectar();


    }
}
