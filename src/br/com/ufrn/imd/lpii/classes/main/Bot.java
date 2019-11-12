package br.com.ufrn.imd.lpii.classes.main;

import br.com.ufrn.imd.lpii.classes.entities.bens.Bem;
import br.com.ufrn.imd.lpii.classes.entities.categoriaDeBem.Categoria;
import br.com.ufrn.imd.lpii.classes.entities.localizacao.Localizacao;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionBem;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionCategoria;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionLocalizacao;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Bot{
    //precisamos de variaveis estaticas pois se nao perderemos os dados inseridos pelo usuario antes de inserir no banco de dados
    static Estado estado = Estado.standby;
    static String localizacao;
    static String descricao;
    static Integer codigo;
    static String nome;
    static String categoria;

    public static <localizacao> void inicializacaoBot(String token){

        //token do nosso bot patrimonial: 1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc

        //criação do objeto bot com as informações de acesso
        TelegramBot bot = TelegramBotAdapter.build(token);

        //objeto responsavel por receber as mensagens
        GetUpdatesResponse updatesResponse = null;

        //objeto responsavel por gerenciar o envio de respostas
        SendResponse sendResponse;

        //objeto responsavel por gerenciar o envio de ações do chat
        BaseResponse baseResponse;

        //controle de offset, isto é, a partir desse ID serão lidas as mensagens pendentes na fila
        int m = 0, contador = 0;

        //display.getChildren().add(new Text("Ok"));
        //loop infinito, que pode ser alterado para algum timer de intervalo curto
            while (true) {
                System.out.println("Info: Buscando novas mensagens...");
                //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
                try{
                    updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));
                }catch (Exception e){
                    e.printStackTrace();
                }

                //lista de mensagens
                List<Update> updates = updatesResponse.updates();

                //análise de cada ação da mensagem
                for (Update update : updates) {
                    //atualização do offset
                    m = update.updateId() + 1;

                    String mensagem = update.message().text();
                   // System.out.println("Recebendo mensagem: " + update.message().text());

                    //envio de 'escrevendo' antes de mandar a resposta
                    baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

                    //verificação de ação de chat foi enviada com sucesso
                    System.out.println("Resposta de ChatAction foi enviada? " + baseResponse.isOk());


                    //se o estado for stand-by(padrao)
                    if(estado == Estado.standby){
                        //se o usuario quer cadastrar localizacao
                        if(update.message().text().equals("/cadastrar_localizacao")){
                            //enviando ao usuario a mensagem para inserir a localizacao
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localização"));
                            //mudando o estado
                            estado = Estado.cadastrar_localizacao;
                            break;
                        }
                        //se o usuario quer cadastrar categoria do bem
                        if(update.message().text().equals("/cadastrar_categoria_do_bem")){
                            //enviando ao usuario a mensagem para inserir o código
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o código da categoria(ex: 123)"));
                            //mudando o estado
                            estado = Estado.cadastrar_categoria_do_bem;
                            break;
                        }
                        //se o usuario quer cadastrar categoria do bem
                        if(update.message().text().equals("/cadastrar_bem")){
                            //enviando ao usuario a mensagem para inserir o código do bem
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o código do bem(ex: 123)"));
                            //mudando o estado
                            estado = Estado.cadastrar_bem;
                            break;
                        }


                    }

                    //se o estado tiver sido alterado para cadastrar_localizacao
                    if(estado == Estado.cadastrar_localizacao){
                        if(contador == 0){
                            //pede ao usuario o proximo campo que deve ser inserido
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira a descricao do local"));
                            localizacao = update.message().text();
                            contador++;
                            break;
                        }else{
                            descricao = update.message().text();
                            contador++;
                            estado = Estado.standby; //depois de todos os campos preeenchidos, volta ao estado standd-by
                        }
                        contador = 0;
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Local: "+ localizacao));
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Descricao: " + descricao));
                        Localizacao local = new Localizacao(null, localizacao, descricao);

                        ConnectionLocalizacao connectionLocalizacao = new ConnectionLocalizacao();
                        connectionLocalizacao.conectar();
                        if(connectionLocalizacao.cadastrarLocalizacao(local)){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Localizacao inserida com sucesso!"));
                        }
                        connectionLocalizacao.desconectar();
                        break;
                    }
                    //se o estado tiver sido alterado para categoria de bem
                    if(estado == Estado.cadastrar_categoria_do_bem){
                        if(contador == 0){
                            //pede ao usuario o proximo campo que deve ser inserido
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da categoria"));
                            String codigoStr = update.message().text();
                            codigo = Integer.parseInt(codigoStr);
                            contador++;
                            break;
                        }else if(contador == 1){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira a descrição da categoria"));
                            nome = update.message().text();
                            contador++;
                            break;
                        }else{
                            descricao = update.message().text();
                            contador++;
                            estado = Estado.standby; //depois de todos os campos preeenchidos, volta ao estado standd-by
                        }
                        contador = 0;
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Código: "+ codigo));
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Nome: " + nome));
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Descricao: " + descricao));
                        Categoria categoria = new Categoria(codigo, nome, descricao);

                        ConnectionCategoria connectionCategoria = new ConnectionCategoria();
                        connectionCategoria.conectar();
                        if(connectionCategoria.cadastrarCategoria(categoria)){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Categoria inserida com sucesso!"));
                        }
                        connectionCategoria.desconectar();
                        break;
                    }

                    //se o estado tiver sido alterado para cadastrar bem
                    if(estado == Estado.cadastrar_bem){
                        if(contador == 0){
                            //pede ao usuario o proximo campo que deve ser inserido
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome do bem"));
                            String codigoStr = update.message().text();
                            codigo = Integer.parseInt(codigoStr);
                            contador++;
                            break;
                        }else if(contador == 1){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira a descrição do bem"));
                            nome = update.message().text();
                            contador++;
                            break;
                        }else if(contador == 2){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localização"));
                            descricao = update.message().text();
                            contador++;
                            break;
                        }else if(contador == 3){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da categoria"));
                            localizacao = update.message().text();
                            contador++;
                            break;

                        }

                        else{
                            categoria = update.message().text();
                            contador++;
                            estado = Estado.standby; //depois de todos os campos preeenchidos, volta ao estado standd-by
                        }
                        contador = 0;
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Código: "+ codigo));
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Nome: " + nome));
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Descricao: " + descricao));
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Localizacao: " + localizacao));
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Categoria: " + categoria));

                        Localizacao local = buscarLocalizacao(localizacao);
                        Categoria cat = buscarCategoria(categoria);

                        Bem bem = new Bem(codigo, nome, descricao, local, cat);

                        ConnectionBem connectionBem = new ConnectionBem();
                        connectionBem.conectar();
                        if(connectionBem.cadastrarBem(bem)){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem inserido com sucesso!"));
                        }
                        connectionBem.desconectar();
                        break;
                    }

                    if(update.message().text().equals("/cadastrar_bem")){

                    }
                    if(update.message().text().equals("/listar_localizacoes")){
                        ConnectionLocalizacao connectionLocalizacao = new ConnectionLocalizacao();
                        connectionLocalizacao.conectar();
                        ArrayList<Localizacao> localizacoes = connectionLocalizacao.listarLocalizacoes();
                        String resposta = "";
                        for (Localizacao localizacao : localizacoes){
                           resposta += localizacao.toString();
                            resposta +="-----------\n";
                        }
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));

                        connectionLocalizacao.desconectar();

                    }


                    if(update.message().text().equals("/listar_categorias")){
                        ConnectionCategoria connectionCategoria = new ConnectionCategoria();

                        connectionCategoria.conectar();
                        ArrayList<Categoria> categorias= connectionCategoria.listarCategorias();
                        String resposta = "";
                        for (Categoria categoria : categorias){
                            resposta += categoria.toString();
                            resposta +="-----------\n";
                        }
                        connectionCategoria.desconectar();


                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));

                    }
                    if(update.message().text().equals("/listar_bens_por_localizacao")){

                    }
                    if(update.message().text().equals("/buscar_bem_por_codigo")){


                        String codigo = "0"; //ler codigo digitado pelo user
                        ConnectionBem connectionBem = new ConnectionBem();
                        connectionBem.conectar();

                        ArrayList<Bem> bens = connectionBem.buscarBemByAtributo("codigo", codigo );//

                        String resposta="";
                        for (Bem bem : bens){
                            resposta += bem.toString();
                            resposta += "---------------\n";
                        }
                        connectionBem.desconectar();
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));


                    }
                    if(update.message().text().equals("/buscar_bem_por_nome")){
                        String nome = "0"; //ler nome digitado pelo user
                        ConnectionBem connectionBem = new ConnectionBem();
                        connectionBem.conectar();
                        ArrayList<Bem> bens = connectionBem.buscarBemByAtributo("nome", nome );
                        String resposta="";
                        for (Bem bem : bens){
                            resposta += bem.toString();
                            resposta += "---------------\n";
                        }
                        connectionBem.desconectar();
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));

                    }
                    if(update.message().text().equals("/buscar_bem_por_descricao")){
                        String nome = "0"; //ler descricao digitada pelo user
                        ConnectionBem connectionBem = new ConnectionBem();
                        connectionBem.conectar();
                        ArrayList<Bem> bens = connectionBem.buscarBemByAtributo("descricao", descricao );
                        String resposta="";
                        for (Bem bem : bens){
                            resposta += bem.toString();
                            resposta += "---------------\n";
                        }
                        connectionBem.desconectar();
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));
                    }
                    if(update.message().text().equals("/movimentar_bem")){

                    } else if (update.message().text().equals("você é um autobot?")) {

                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Não. Também não conheço Optimus Prime... ops..."));
                        //verificação se a mensagem foi enviada com sucesso
                        System.out.println("Mensagem enviada? " + sendResponse.isOk());
                    } else {
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Não entendi..."));

                        //verificação se a mensagem foi enviada com sucesso
                        System.out.println("Mensagem enviada? " + sendResponse.isOk());
                        break;
                    }

                }

            }
    }

    private static Localizacao buscarLocalizacao(String local) {
        ConnectionLocalizacao connectionLocalizacao = new ConnectionLocalizacao();
        connectionLocalizacao.conectar();
        ArrayList<Localizacao> localizacoes = connectionLocalizacao.listarLocalizacoes();

        for (Localizacao localizacao : localizacoes){
            if(localizacao.getNome().equals(local)){
                connectionLocalizacao.desconectar();
                return localizacao;
            }
        }
        connectionLocalizacao.desconectar();
        return null;
    }

    private static Categoria buscarCategoria(String cat) {
        ConnectionCategoria connectionCategoria = new ConnectionCategoria();
        connectionCategoria.conectar();
        ArrayList<Categoria> categorias= connectionCategoria.listarCategorias();

        for (Categoria categoria : categorias){
            if(categoria.getNome().equals(cat)){
                connectionCategoria.desconectar();
                return categoria;
            }
        }
        connectionCategoria.desconectar();
        return null;
    }

}
