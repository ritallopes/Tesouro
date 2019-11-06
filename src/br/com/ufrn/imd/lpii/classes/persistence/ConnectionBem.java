package br.com.ufrn.imd.lpii.classes.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class ConnectionBem  extends ConnectionSQLite {
    /**
     * Método para criação da tabela BEM no banco de dados
     * @return Boolean indicando se ocorreu tudo pelo fluxo normal ou aconteceu alguma exceção
     * */
    public Boolean criarTabela(){
        try {
            if (connection.isClosed() == false){


                statement = connection.createStatement();

                //se a tabela já existir no banco ele continua se capturar exceção
                //o codigo como autoincrement é gerado pelo próprio banco de dados de forma incremental
                String sql = "CREATE TABLE IF NOT EXISTS BEM" +
                        "(CODIGO INTEGER PRIMARY KEY  AUTOINCREMENT," +
                        " NOME          TEXT    NOT NULL, " +
                        " DESCRICAO          TEXT     NOT NULL," +
                        " LOCALIZACAOCODIGO INTEGER NOT NULL," +
                        " CATEGORIACODIGO INTEGER NOT NULL," +
                        " FOREIGN KEY(LOCALIZACAOCODIGO) REFERENCES LOCALIZACAO (CODIGO),"+
                        " FOREIGN KEY(CATEGORIACODIGO) REFERENCES CATEGORIA (CODIGO));";


                statement.executeUpdate(sql);
                statement.close();


                return true;
            }else{
                conectar();
                criarTabela();
            }
        }catch (SQLException e){
            System.out.println("Erro ao criar tabela Bem");
            e.printStackTrace();
            return false;
        }

        return null;
    }






    public Boolean cadastrarBem( String nome, String descricao, Integer codigoLocalizacao, Integer codigoCategoria){
        try {
            if (connection.isClosed() == false){
                statement = connection.createStatement();
                String sql ="INSERT INTO BEM( NOME, DESCRICAO, LOCALIZACAOCODIGO, CATEGORIACODIGO) " +
                        "VALUES (\""+nome+"\",\""+descricao+"\","+codigoLocalizacao+", "+codigoCategoria+");";
                statement.executeUpdate(sql);
                statement.close();
                return true;
            }else{
                conectar();
                criarTabela();
                cadastrarBem( nome, descricao, codigoLocalizacao, codigoCategoria);
            }
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar Bem");
            e.printStackTrace();
            return false;
        }
        return null;
    }



    /**
     *Método que a tabela Bem no banco e retorna todos os valores nela presentes
     * @return  ArrayList<HashMap<String, String> > : ArrayList com um map(chave, valor) indicando o nome o atributo e o valor dele em cada tupla
     * */
    public ArrayList<HashMap<String, String> > listarBem(){
        ArrayList< HashMap<String, String> > camposList = null; //array para retornar todos campos cadastrados organizando-os em 3-tuplas

        try {
            if (connection.isClosed() == false){
                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM BEM;" );


                camposList = new ArrayList<>();

                //organizando o Set lido do banco em outra variável (arraylist)
                while ( rs.next() ) {
                    HashMap<String, String> tupla= new HashMap<>();
                    tupla.clear();
                    Integer codigo = rs.getInt("codigo");
                    tupla.put("codigo", codigo.toString());

                    String  nome = rs.getString("nome");
                    tupla.put("nome", nome);

                    String descricao  = rs.getString("descricao");
                    tupla.put("descricao", descricao);

                    Integer localizacaoCodigo  = rs.getInt("localizacaocodigo");
                    tupla.put("localizacaocodigo", localizacaoCodigo.toString());

                    Integer categoriaCodigo  = rs.getInt("categoriacodigo");
                    tupla.put("categoriacodigo", categoriaCodigo.toString());

                    camposList.add(tupla);
                }
                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                listarBem();
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar Bem");
            e.printStackTrace();

        }
        return camposList;
    }
}