package br.com.ufrn.imd.lpii.classes.persistence;

import br.com.ufrn.imd.lpii.classes.entities.Bem;
import br.com.ufrn.imd.lpii.classes.entities.Categoria;
import br.com.ufrn.imd.lpii.classes.entities.Localizacao;
import br.com.ufrn.imd.lpii.classes.main.Bot;
import br.com.ufrn.imd.lpii.exceptions.BemNaoEncontradoException;
import br.com.ufrn.imd.lpii.exceptions.LocalizacaoNaoEncontradaException;

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
            if (!connection.isClosed()){
                statement = connection.createStatement();

                //se a tabela já existir no banco ele continua se capturar exceção
                //o codigo como autoincrement é gerado pelo próprio banco de dados de forma incremental
                String sql = "CREATE TABLE IF NOT EXISTS BEM" +
                        "(CODIGO INTEGER PRIMARY KEY  AUTOINCREMENT," +
                        " NOME          TEXT    NOT NULL, " +
                        "  TOMBO TEXT ," +
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



    public Boolean cadastrarBem(Bem bem){
        try {// String nome, String descricao, Integer codigoLocalizacao, Integer codigoCategoria
            if (!connection.isClosed()){
                System.out.println(bem.toString());
                statement = connection.createStatement();
                String sql ="INSERT INTO BEM(CODIGO, NOME, TOMBO, DESCRICAO, LOCALIZACAOCODIGO, CATEGORIACODIGO) " +
                        "VALUES (\""+bem.getCodigo()+"\", \""+bem.getNome()+"\",\""+bem.getTombo()+"\",\""+bem.getDescricao()+"\","+bem.getLocalizacao().getCodigo()+", "+bem.getCategoria().getCodigo()+");";
                statement.executeUpdate(sql);
                statement.close();
                return true;
            }else{
                conectar();
                criarTabela();
                return cadastrarBem(bem);
            }
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar Bem");
            e.printStackTrace();
            return false;
        }
    }



    /**
     *Método que a tabela Bem no banco e retorna todos os valores nela presentes
     * @return  ArrayList<HashMap<String, String> > : ArrayList com um map(chave, valor) indicando o nome o atributo e o valor dele em cada tupla
     * */
    public ArrayList<HashMap<String, String> > listarBem(){
        ArrayList< HashMap<String, String> > camposList = null; //array para retornar todos campos cadastrados organizando-os em 3-tuplas

        try {
            if (!connection.isClosed()){
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

                    String  tombo = rs.getString("tombo");
                    tupla.put("tombo", tombo);

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


    /**Busca no banco um bem com o atributo correspondente ao enviado
     * @param
     * @return */
    public ArrayList<Bem> buscarBemByAtributo(String atributo, String value){
        ArrayList<Bem> bens = null;
        try {
            if (!connection.isClosed()){
                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM BEM;" );
                //organizando o Set lido do banco em outra variável (arraylist)
                while ( rs.next() ) {
                    Bem bem = null;
                    Integer codigo = rs.getInt("codigo");
                    String  nome = rs.getString("nome");
                    String  tombo = rs.getString("tombo");
                    String descricao  = rs.getString("descricao");
                    Integer localizacaoCodigo  = rs.getInt("localizacaocodigo");
                    Integer categoriaCodigo  = rs.getInt("categoriacodigo");

                    ConnectionLocalizacao connectionLocalizacao = new ConnectionLocalizacao();
                    connectionLocalizacao.conectar();
                    Localizacao localizacao = connectionLocalizacao.buscarLocalizacaoByCodigo(localizacaoCodigo);
                    connectionLocalizacao.desconectar();
                    ConnectionCategoria connectionCategoria = new ConnectionCategoria();
                    connectionCategoria.conectar();
                    Categoria categoria = connectionCategoria.buscarCategoriaByCodigo(categoriaCodigo);
                    connectionCategoria.desconectar();
                    bem = new Bem(codigo, nome,tombo, descricao, localizacao, categoria);
                    System.out.println(bem.toString());
                    bens.add(bem);
                }

                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                return buscarBemByAtributo(atributo, value);
            }
        }catch (SQLException e){
            System.out.println("Erro ao buscar");
            e.printStackTrace();
        }


        return bens;
    }



    public ArrayList<Bem> buscarBemByLocalizacao(Localizacao localizacao){

        return null;
    }

    public Boolean atualizarLocalizacao(Localizacao localizacao, Bem bem) throws LocalizacaoNaoEncontradaException{
        desconectar();
        conectar();
        try {// String nome, String descricao, Integer codigoLocalizacao, Integer codigoCategoria
            if (!connection.isClosed()){
                statement = connection.createStatement();
                if (localizacao.getCodigo() != null){
                    String sql ="UPDATE bem SET localizacaocodigo = "+ localizacao.getCodigo()
                            +" WHERE codigo =  "+bem.getCodigo()+";";
                    System.out.println(sql);
                    statement.executeUpdate(sql);
                    statement.close();
                    return true;
                }else{
                    throw new LocalizacaoNaoEncontradaException();
                }

            }else{
                conectar();
                criarTabela();
                return atualizarLocalizacao(localizacao, bem);
            }
        }catch (SQLException e){
            System.out.println("Erro ao movimentar Bem");
            e.printStackTrace();
            return false;
        }
    }




    public ArrayList<Bem> listarBens(){
        ArrayList<Bem> bens = null; //array para retornar todos campos cadastrados organizando-os em 3-tuplas

        try {
            if (!connection.isClosed()){
                //configurações de variáveis para o banco
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement = connection.createStatement();

                //script SQL
                ResultSet rs = statement.executeQuery( "SELECT * FROM BEM;" );

                bens = new ArrayList<>();

                //organizando o Set lido do banco em outra variável (arraylist)
                while ( rs.next() ) {
                    Bem bem = null;
                    Integer codigo = rs.getInt("codigo");
                    String  nome = rs.getString("nome");
                    String tombo  = rs.getString("tombo");
                    String descricao  = rs.getString("descricao");
                    Integer localInt  = rs.getInt("localizacaocodigo");
                    Integer catInt = rs.getInt("categoriacodigo");
                    Localizacao localizacao = Bot.buscarLocalizacao(localInt);
                    Categoria categoria = Bot.buscarCategoria(catInt);

                    bem = new Bem(codigo, nome, tombo, descricao, localizacao, categoria);

                    bens.add(bem);
                }
                rs.close();
                statement.close();

            }else{
                conectar();
                criarTabela();
                //return "errou"; //listarCategorias();
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar");
            e.printStackTrace();

        }
        return bens;

    }


    public Boolean apagarBem(String campo, String value) throws BemNaoEncontradoException{
        try {// String nome, String descricao, Integer codigoLocalizacao, Integer codigoCategoria
            if (!connection.isClosed()){
                statement = connection.createStatement();

                if (value != null && campo !=null ){
                    String sql ="DELETE FROM bem WHERE "+campo+" = "+value+" ;";
                    System.out.println(sql);
                    statement.executeUpdate(sql);
                    statement.close();
                    return true;
                }else{
                    throw new BemNaoEncontradoException();
                }

            }else{
                conectar();
                criarTabela();
                return apagarBem(campo, value);
            }
        }catch (SQLException e){
            System.out.println("Erro ao apagar Bem");
            e.printStackTrace();
            return false;
        }

    }

}