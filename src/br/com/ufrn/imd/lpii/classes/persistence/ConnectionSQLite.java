package br.com.ufrn.imd.lpii.classes.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ConnectionSQLite {

    //Variaveis com padrão default para que as classes filhas possam herdar as variaveis de conexão
    Connection connection = null;
    Statement statement = null;

    public Boolean conectar() {
        try {
            // arquivo do banco de dados
            String url = "jdbc:sqlite:src/br/com/ufrn/imd/lpii/data/tesouro.db";
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


    public Boolean apagarTabela(String nomeTabela){
        try {
            if (connection.isClosed() == false){
                statement = connection.createStatement();
                String sql = "DROP TABLE IF EXISTS "+nomeTabela+";";
                statement.executeUpdate(sql);
                statement.close();
                return true;
            }else{
                conectar();
                apagarTabela(nomeTabela);
            }
        }catch (SQLException e){
            System.out.println("Erro ao APAGAR tabela Categoria");
            return false;
        }

        return null;
    }

}
