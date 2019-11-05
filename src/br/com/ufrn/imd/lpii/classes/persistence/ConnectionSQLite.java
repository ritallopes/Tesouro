package br.com.ufrn.imd.lpii.classes.persistence;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionSQLite {
    private Connection connection = null;
    public Boolean conectar() {
        try {
            // arquivo do banco de dados
            String url = "jdbc:sqlite:src\\br\\com\\ufrn\\imd\\lpii\\data\\tesouro.db";
            // create a connection to the database
            connection = DriverManager.getConnection(url);
            System.out.println("Conectado ao banco de dados");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Conexão não foi estabelecida corretamente");

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Não foi possível fechar o banco");
            }
            return false;
        }
    }

    public Boolean desconectar() {
        try {
            if (connection.isClosed() == false){
                connection.close();
            }
            return true;
        }catch (SQLException e){
            System.out.println("Erro ao fechar banco");
            return false;
        }

    }
}
