package br.com.ufrn.imd.lpii.classes.persistence;

import br.com.ufrn.imd.lpii.classes.entities.Categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionCategoria extends ConnectionSQLite {
    /**
     * Método para criação da tabela categoria no banco de dados
     * @return Boolean indicando se ocorreu tudo pelo fluxo normal ou aconteceu alguma exceção
     * */
     public Boolean criarTabela(){
        try {
            if (connection.isClosed() == false){


                statement = connection.createStatement();
                //se a tabela já existir no banco ele continua se capturar exceção
                //o codigo como autoincrement é gerado pelo próprio banco de dados de forma incremental
                String sql = "CREATE TABLE IF NOT EXISTS CATEGORIA" +
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






    public Boolean cadastrarCategoria( String nome, String descricao){
        try {
            if (connection.isClosed() == false){
                statement = connection.createStatement();
                String sql ="INSERT INTO CATEGORIA ( NOME, DESCRICAO) " +
                             "VALUES (\""+nome+"\",\""+descricao+"\");";
                statement.executeUpdate(sql);
                statement.close();
                return true;
            }else{
                conectar();
                criarTabela();
                cadastrarCategoria( nome, descricao);
            }
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar");
            e.printStackTrace();
            return false;
        }
        return null;
    }


    /**
     * Função cadastrarCategoria é responsável por cadastrar uma categoria no banco
     * @param categoria: categoria a ser cadastrada no banco
     *
     * */

    public Boolean cadastrarCategoria(Categoria categoria){
        try {
            if (connection.isClosed() == false){
                statement = connection.createStatement();
                String sql ="INSERT INTO CATEGORIA ( NOME, DESCRICAO) " +
                        "VALUES (\""+categoria.getNome()+"\",\""+categoria.getDescricao()+"\");";
                statement.executeUpdate(sql);
                statement.close();
                return true;
            }else{
                conectar();
                criarTabela();
                cadastrarCategoria(categoria);
            }
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar");
            e.printStackTrace();
            return false;
        }
        return null;
    }



    /**
     *Método que a tabela categoria no banco e retorna todos os valores nela presentes
     * @return  ArrayList<HashMap<String, String> > : ArrayList com um map(chave, valor) indicando o nome o atributo e o valor dele em cada tupla
     * */
    public ArrayList<HashMap<String, String> > listarCategoriaToArray(){
        ArrayList< HashMap<String, String> > camposList = null; //array para retornar todos campos cadastrados organizando-os em 3-tuplas

        try {
            if (connection.isClosed() == false){
                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM CATEGORIA;" );


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

                    camposList.add(tupla);
                }
                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                return listarCategoriaToArray();
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar");
            e.printStackTrace();

        }
        return camposList;
    }


    /**
     *Método que a tabela categoria no banco e retorna todos os valores nela presentes
     * @return  ArrayList<Categoria> : ArrayList de categorias presentes no banco
     * */
    public ArrayList<Categoria> listarCategorias(){
        ArrayList<Categoria> categorias = null; //array para retornar todos campos cadastrados organizando-os em 3-tuplas

        try {
            if (connection.isClosed() == false){
                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM CATEGORIA;" );


                categorias = new ArrayList<>();

                //organizando o Set lido do banco em outra variável (arraylist)
                while ( rs.next() ) {
                    Categoria categoria = null;
                    Integer codigo = rs.getInt("codigo");
                    String  nome = rs.getString("nome");
                    String descricao  = rs.getString("descricao");

                    categoria = new Categoria(codigo, nome, descricao);

                    categorias.add(categoria);
                }
                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                return listarCategorias();
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar");
            e.printStackTrace();

        }
        return categorias;
    }



    /**Busca no banco uma categoria com o id especificado
     * @param codigo: codigo pelo qual se procurará a categoria
     * @return Categoria: retorna a única categoria encontrada, podendo ser null caso não seja encontrado*/
    public Categoria buscarCategoriaByCodigo(Integer codigo){
        Categoria categoria = null;
        try {
            if (connection.isClosed() == false){
                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM CATEGORIA WHERE CODIGO="+codigo.toString()+";" );


                //organizando o Set lido do banco em outra variável (arraylist)
                while ( rs.next() ) {
                    Integer codigo_ = rs.getInt("codigo");
                    String  nome = rs.getString("nome");
                    String descricao  = rs.getString("descricao");
                    categoria = new Categoria(codigo_, nome, descricao);
                }

                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                return buscarCategoriaByCodigo(codigo);
            }
        }catch (SQLException e){
            System.out.println("Erro ao buscar");
            e.printStackTrace();
        }
        return categoria;
    }
}
