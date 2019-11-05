package br.com.ufrn.imd.lpii.classes.main;

import br.com.ufrn.imd.lpii.classes.persistence.ConnectionSQLite;

public class Main {
    public static void main(String[] args) {
        ConnectionSQLite connectionSQLite = new ConnectionSQLite();
        connectionSQLite.conectar();
        Bot.inicializacaoBot("1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc");
        connectionSQLite.desconectar();


    }
}
