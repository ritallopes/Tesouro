package br.com.ufrn.imd.lpii.classes.persistence;

import br.com.ufrn.imd.lpii.classes.entities.Localizacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionLocalizacao extends ConnectionSQLite{


    /**
     * Método para criação da tabela Localizacao no banco de dados
     * @return Boolean indicando se ocorreu tudo pelo fluxo normal ou aconteceu alguma exceção
     * */
    public Boolean criarTabela(){
        try {
            if (connection.isClosed() == false){

                statement = connection.createStatement();
                //se a tabela já existir no banco ele continua se capturar exceção
                //o codigo como autoincrement é gerado pelo próprio banco de dados de forma incremental
                String sql = "CREATE TABLE IF NOT EXISTS LOCALIZACAO" +
                        "(CODIGO INTEGER PRIMARY KEY  AUTOINCREMENT," +
                        " NOME          TEXT    NOT NULL, " +
                        " DESCRICAO          TEXT     NOT NULL);";


                statement.executeUpdate(sql);
                statement.close();


                return true;
            }else{
                conectar();
                criarTabela();
            }
        }catch (SQLException e){
            System.out.println("Erro ao criar tabela Categoria");
            return false;
        }

        return null;
    }


    public Boolean cadastrarLocalizacao( String nome, String descricao){
        try {
            if (connection.isClosed() == false){
                statement = connection.createStatement();
                String sql ="INSERT INTO LOCALIZACAO ( NOME, DESCRICAO) " +
                        "VALUES (\""+nome+"\",\""+descricao+"\");";
                statement.executeUpdate(sql);
                statement.close();
                return true;
            }else{
                conectar();
                criarTabela();
                cadastrarLocalizacao( nome, descricao);
            }
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar");
            e.printStackTrace();
            return false;
        }
        return null;
    }


    public Boolean cadastrarLocalizacao(Localizacao localizacao){
        try {
            if (!connection.isClosed()){
                statement = connection.createStatement();
                String sql ="INSERT INTO LOCALIZACAO ( NOME, DESCRICAO) " +
                        "VALUES (\""+localizacao.getNome()+"\",\""+localizacao.getDescricao()+"\");";
                statement.executeUpdate(sql);
                statement.close();
                return true;
            }else{
                conectar();
                criarTabela();
                cadastrarLocalizacao(localizacao);
            }
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar");
            e.printStackTrace();
            return false;
        }
        return true;
    }



    /**
     *Método que a tabela Localizacao no banco e retorna todos os valores nela presentes
     * @return  ArrayList<HashMap<String, String> > : ArrayList com um map(chave, valor) indicando o nome o atributo e o valor dele em cada tupla
     * */
    public ArrayList<HashMap<String, String>> listarLocalizacaoToArray(){
        ArrayList< HashMap<String, String> > camposList = null; //array para retornar todos campos cadastrados organizando-os em 3-tuplas

        try {
            if (connection.isClosed() == false){
                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM LOCALIZACAO;" );

                camposList = new ArrayList<>();

                //organizando o Set lido do banco em outra variável (arraylist)
                while ( rs.next() ) {
                    HashMap<String, String> tupla = new HashMap<>();
                    tupla.clear();
                    Integer codigo = rs.getInt("codigo");
                    tupla.put("codigo", codigo.toString());

                    String  nome = rs.getString("nome");
                    tupla.put("nome", nome);

                    String descricao  = rs.getString("descricao");
                    tupla.put("descricao", descricao);

                    camposList.add(tupla);
                }
                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                listarLocalizacaoToArray();
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar");
            e.printStackTrace();

        }
        return camposList;
    }


    /**
     *Método que a tabela Localizacao no banco e retorna todos os valores nela presentes
     * @return  ArrayList<Localizacao> : ArrayList de localizacoes presentes no banco
     * */
    public ArrayList<Localizacao> listarLocalizacoes(){
        ArrayList<Localizacao> localizacoes = null; //array para retornar todos campos cadastrados organizando-os em 3-tuplas

        try {
            if (connection.isClosed() == false){

                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM LOCALIZACAO;" );


                localizacoes = new ArrayList<>();

                //organizando o Set lido do banco em outra variável (arraylist)
                while ( rs.next() ) {

                    Localizacao localizacao = null;
                    Integer codigo = rs.getInt(1);
                    String  nome = rs.getString("nome");
                    String descricao  = rs.getString("descricao");

                    localizacao = new Localizacao(codigo, nome, descricao);

                    localizacoes.add(localizacao);
                }
                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                return listarLocalizacoes();
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar");
            e.printStackTrace();

        }
        return localizacoes;
    }

    /**Busca no banco uma Localizacao com o id especificado
     * @param codigo: codigo pelo qual se procurará a localizacao
     * @return Localizacao: retorna a única Localizacao encontrada, podendo ser null caso não seja encontrado*/
    public Localizacao buscarLocalizacaoByCodigo(Integer codigo){
        Localizacao localizacao = null;
        try {
            if (connection.isClosed() == false){
                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM LOCALIZACAO WHERE CODIGO="+codigo.toString()+";" );


                //organizando o Set lido do banco em outra variável (arraylist)
                while ( rs.next() ) {
                    Integer codigo_ = rs.getInt("codigo");
                    String  nome = rs.getString("nome");
                    String descricao  = rs.getString("descricao");
                    localizacao = new Localizacao(codigo_, nome, descricao);
                }

                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                return buscarLocalizacaoByCodigo(codigo);
            }
        }catch (SQLException e){
            System.out.println("Erro ao buscar");
            e.printStackTrace();
        }
        return localizacao;
    }
}
