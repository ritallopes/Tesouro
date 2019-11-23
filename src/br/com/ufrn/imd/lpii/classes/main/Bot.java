package br.com.ufrn.imd.lpii.classes.main;

import br.com.ufrn.imd.lpii.classes.entities.Bem;
import br.com.ufrn.imd.lpii.classes.entities.Categoria;
import br.com.ufrn.imd.lpii.classes.entities.Localizacao;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionBem;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionCategoria;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionLocalizacao;
import br.com.ufrn.imd.lpii.exceptions.LocalizacaoNaoEncontradaException;
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
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot{
    //precisamos de variaveis estaticas pois se nao perderemos os dados inseridos pelo usuario antes de inserir no banco de dados
    static Estado estado = Estado.STANDBY;
    static String localizacao;
    static String descricao;
    static Integer codigo;
    static String nome;
    static String tombo;
    static String categoria;
    static String bem;


    public static <localizacao> void inicializacaoBot(String token, TextFlow displayArea, Label botStatus) throws IOException, InterruptedException, LocalizacaoNaoEncontradaException {

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
//        FXMLLoader loader = new FXMLLoader(Bot.class.getResource("../gui/mainScreen.fxml"));
//        loader.load();
//        MainScreenController controller = loader.getController();
//        controller.updateDisplay(new Text("Ok"));
        //addLine(displayArea, "Qualquer uma");


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

                //String mensagem = update.message().text();

                addLine(displayArea, "                                              " + update.message().chat().firstName() + " : " + update.message().text() + "\n");

                //envio de 'escrevendo' antes de mandar a resposta
                baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
                updateStatus(botStatus, ChatAction.typing.name());
                //addLine(displayArea, ChatAction.typing.name());

                //verificação de ação de chat foi enviada com sucesso
                //addLine(displayArea, "Resposta de ChatAction foi enviada? " + baseResponse.isOk() + "\n");

                //se o estado for stand-by(padrao)
                if(estado == Estado.STANDBY){
                    //se o usuario quer cadastrar localizacao
                    if(update.message().text().equals("/cadastrar_localizacao")){
                        //enviando ao usuario a mensagem para inserir a localizacao
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localização"));
                        addLine(displayArea, "Insira o nome da localização:\n");
                        //mudando o estado
                        estado = Estado.CADASTRAR_LOCALIZACAO;
                        break;
                    }
                    //se o usuario quer cadastrar categoria do bem
                    if(update.message().text().equals("/cadastrar_categoria_do_bem")){
                        //enviando ao usuario a mensagem para inserir o código
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o código da categoria(ex: 123)"));
                        addLine(displayArea, "Insira o código da categoria(ex: 123)\n");
                        //mudando o estado
                        estado = Estado.CADASTRAR_CATEGORIA_DO_BEM;
                        break;
                    }
                    //se o usuario quer cadastrar categoria do bem
                    if(update.message().text().equals("/cadastrar_bem")){
                        //enviando ao usuario a mensagem para inserir o código do bem
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o código do bem(ex: 123)"));
                        //mudando o estado
                        estado = Estado.CADASTRAR_BEM;
                        break;
                    }

                    //caso o usuário queira movimentar o bem
                    if (update.message().text().equals("/movimentar_bem")){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o código do bem: "));
                        estado = Estado.MOVIMENTAR_BEM;
                        break;
                    }

                    if(update.message().text().equals("/lista_bens_por_localizacao")){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localização: "));
                        estado = Estado.LISTAR_BENS_DE_UMA_LOCALIZACAO;
                        break;
                    }


                    //caso o usuário queira buscar um bem
                    if (update.message().text().equals("/buscar_bem_por_codigo")){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o código do bem: "));
                        estado = Estado.BUSCAR_BEM_POR_CODIGO;
                        break;
                    }

                    if (update.message().text().equals("/buscar_bem_por_nome")){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome do bem: "));
                        estado = Estado.BUSCAR_BEM_POR_NOME;
                        break;
                    }

                    if (update.message().text().equals("/busca_bem_por_descricao")){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira a descricao do bem: "));
                        estado = Estado.BUSCAR_BEM_POR_DESCRICAO;
                        break;
                    }



                }

                //se o estado tiver sido alterado para cadastrar_localizacao
                if(estado == Estado.CADASTRAR_LOCALIZACAO){
                    if(contador == 0){
                        //pede ao usuario o proximo campo que deve ser inserido
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira a descricao do local"));
                        addLine(displayArea, "Insira a descrição do local: \n");
                        localizacao = update.message().text();
                        contador++;
                        break;
                    }else{
                        descricao = update.message().text();
                        contador++;
                        estado = Estado.STANDBY; //depois de todos os campos preeenchidos, volta ao estado standd-by
                    }
                    contador = 0;
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Local: "+ localizacao));
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Descricao: " + descricao));
                    addLine(displayArea, "Local: " + localizacao + "\n" + "Descrição: " + descricao + "\n");
                    localizacao = localizacao.toUpperCase();
                    descricao = descricao.toLowerCase();
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
                if(estado == Estado.CADASTRAR_CATEGORIA_DO_BEM){
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
                        estado = Estado.STANDBY; //depois de todos os campos preeenchidos, volta ao estado standd-by
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
                if(estado == Estado.CADASTRAR_BEM){
                    if(contador == 0){
                        //pede ao usuario o proximo campo que deve ser inserido
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome do bem"));
                        String codigoStr = update.message().text();
                        codigo = Integer.parseInt(codigoStr);
                        contador++;
                        break;
                    }else if(contador == 1){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o tombo do bem"));
                        nome = update.message().text();
                        contador++;
                        break;
                    }else if(contador == 2){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira a descrição do bem"));
                        tombo = update.message().text();
                        contador++;
                        break;
                    }else if(contador == 3){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localização"));
                        descricao = update.message().text();
                        contador++;
                        break;
                    }else if(contador == 4){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da categoria"));
                        localizacao = update.message().text();
                        contador++;
                        break;
                    }

                    else{
                        categoria = update.message().text();
                        contador++;
                        estado = Estado.STANDBY; //depois de todos os campos preeenchidos, volta ao estado standd-by
                    }
                    contador = 0;

                    nome = nome.toUpperCase();
                    descricao = descricao.toLowerCase();
                    localizacao = localizacao.toUpperCase();
                    Localizacao local = buscarLocalizacao(localizacao);
                    if(local == null){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Localização inexistente no banco da dados!"));
                        estado = Estado.STANDBY;
                        break;
                    }
                    Categoria cat = buscarCategoria(categoria);
                    if(cat == null){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Categoria inexistente no banco da dados!"));
                        estado = Estado.STANDBY;
                        break;
                    }
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Código: "+ codigo));
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Nome: " + nome));
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Tombo: " + tombo));
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Descricao: " + descricao));
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Localizacao: " + localizacao));
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Categoria: " + categoria));

                    Bem bem = new Bem(codigo, nome, tombo, descricao, local, cat);

                    ConnectionBem connectionBem = new ConnectionBem();
                    connectionBem.conectar();
                    if(connectionBem.cadastrarBem(bem)){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem inserido com sucesso!"));
                    }
                    connectionBem.desconectar();
                    estado = Estado.STANDBY;
                    break;
                }


                //se o estado tiver sido alterado para cadastrar_localizacao
//                    if(estado == Estado.MOVIMENTAR_BEM){
//
//                        String nome_localizacao;
//                        Bem bemProcurado;
//                        if(contador == 0){
//                            //pede ao usuario o proximo campo que deve ser inserido
//                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localizaçao"));
//                            String codigoStr = update.message().text();
//                            codigo = Integer.parseInt(codigoStr);
//                            contador++;
//                            break;
//                        }else{
//                            nome_localizacao = update.message().text();
//                            contador++;
//                            estado = Estado.STANDBY; //depois de todos os campos preeenchidos, volta ao estado standd-by
//                        }
//                        contador = 0;
//
//                        ConnectionBem connectionBem = new ConnectionBem();
//                        connectionBem.conectar();
//                        ArrayList<Bem> bens = new ArrayList<>();
//                        bens = connectionBem.buscarBemByAtributo("codigo", codigo.toString());
//                        bemProcurado = bens.get(0); //retorno unico
//                        System.out.println(bemProcurado.toString());
//
//                        Localizacao localizacao = buscarLocalizacao(nome_localizacao);
//                        try {
//                            connectionBem.atualizarLocalizacao(localizacao, bemProcurado);
//                        } catch (LocalizacaoNaoEncontradaException e) {
//                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), e.getMessage()));
//                        }
//
//                        bens = connectionBem.buscarBemByAtributo("codigo", codigo.toString());
//                        bemProcurado = bens.get(0); //retorno unico
//                        System.out.println(bemProcurado.toString());
//
//                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), bemProcurado.toString()));
//
//                        connectionBem.desconectar();
//                        estado = Estado.STANDBY;
//                        break;
//                    }

                if(estado == Estado.BUSCAR_BEM_POR_CODIGO){
                    //pede ao usuario o codigo da localização
                    codigo = Integer.parseInt(update.message().text()); //TODO pode disparar uma exceção
                    ConnectionBem connectionBem = new ConnectionBem();
                    connectionBem.conectar();
                    Bem bem = buscarBem(codigo);
                    if(bem != null){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), bem.toString()));
                    }else{
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem não existe!"));
                    }
                    connectionBem.desconectar();
                    estado = Estado.STANDBY;
                    break;
                }

                if(estado == Estado.BUSCAR_BEM_POR_NOME){
                    //pede ao usuario o codigo da localização
                    bem = update.message().text();
                    bem = bem.toUpperCase();
                    ConnectionBem connectionBem = new ConnectionBem();
                    connectionBem.conectar();
                    Bem b = buscarBem(bem);
                    if(b != null){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
                    }else{
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem não existe!"));
                    }
                    connectionBem.desconectar();
                    estado = Estado.STANDBY;
                    break;
                }
                if(estado == Estado.BUSCAR_BEM_POR_DESCRICAO){
                    //pede ao usuario o codigo da localização
                    descricao = update.message().text();
                    descricao = descricao.toLowerCase();
                    ConnectionBem connectionBem = new ConnectionBem();
                    connectionBem.conectar();
                    Bem bem = buscarBemPorDescricao(descricao);
                    if(bem != null){
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), bem.toString()));
                    }else{
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem não existe!"));
                    }
                    connectionBem.desconectar();
                    estado = Estado.STANDBY;
                    break;
                }

                if(estado == Estado.LISTAR_BENS_DE_UMA_LOCALIZACAO){
                    localizacao = update.message().text();
                    localizacao = localizacao.toUpperCase();
                    Localizacao tmp = buscarLocalizacao(localizacao);
                    ConnectionBem connectionBem = new ConnectionBem();
                    connectionBem.conectar();
                    ArrayList<Bem> bens = connectionBem.listarBens();
                    String resposta = "";
                    for(Bem bem : bens){
                        assert tmp != null;
                        if(bem.getLocalizacao().getCodigo().equals(tmp.getCodigo())){
                            resposta += bem.toString();
                            resposta += "-----------\n";
                        }
                    }
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));
                    connectionBem.desconectar();
                    estado = Estado.STANDBY;
                    break;
                }

                if(estado == Estado.MOVIMENTAR_BEM){
                    ConnectionBem connectionBem = new ConnectionBem();
                    connectionBem.conectar();
                    Bem bem = null;
                    if(contador == 0){
                        codigo = Integer.parseInt(update.message().text());
                        bem = buscarBem(codigo);
                        if(bem != null){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira a nova localização do bem"));
                            contador++;
                            break;
                        }else{
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem inexistente!"));
                            estado = Estado.STANDBY;
                            connectionBem.desconectar();
                            break;
                        }

                    }else if(contador == 1){
                        localizacao = update.message().text();
                        Localizacao local = buscarLocalizacao(localizacao);
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Local encontrado: " + local.getNome()));
                        if(local != null){
                            //bem.setLocalizacao(local);
                            if(connectionBem.atualizarLocalizacao(local, bem)){ //todo->parei aqui
                                sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Movimentação do bem realizada com sucesso!"));
                            }else{
                                sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Erro ao movimentar bem"));
                            }
                            contador++;
                            break;
                        }else{
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Localização inexistente!"));
                            estado = Estado.STANDBY;
                            connectionBem.desconectar();
                            break;
                        }


                    }

                    connectionBem.desconectar();
                    estado = Estado.STANDBY;
                    break;
                }


//                    if(update.message().text().equals("/buscar_bem_por_nome")){
//                        String nome = "0"; //ler nome digitado pelo user
//                        ConnectionBem connectionBem = new ConnectionBem();
//                        connectionBem.conectar();
//                        ArrayList<Bem> bens = connectionBem.buscarBemByAtributo("nome", nome );
//                        String resposta="";
//                        for (Bem bem : bens){
//                            resposta += bem.toString();
//                            resposta += "---------------\n";
//                        }
//                        connectionBem.desconectar();
//                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));
//
//                    }
//                    if(update.message().text().equals("/buscar_bem_por_descricao")){
//                        String nome = "0"; //ler descricao digitada pelo user
//                        ConnectionBem connectionBem = new ConnectionBem();
//                        connectionBem.conectar();
//                        ArrayList<Bem> bens = connectionBem.buscarBemByAtributo("descricao", descricao );
//                        String resposta="";
//                        for (Bem bem : bens){
//                            resposta += bem.toString();
//                            resposta += "---------------\n";
//                        }
//                        connectionBem.desconectar();
//                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));
//                    }

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
                    // addLine(displayArea, resposta);

                    connectionLocalizacao.desconectar();
                    estado = Estado.STANDBY;
                    break;

                }


                if(update.message().text().equals("/listar_categorias")){
                    ConnectionCategoria connectionCategoria = new ConnectionCategoria();
                    connectionCategoria.conectar();
                    ArrayList<Categoria> categorias = connectionCategoria.listarCategorias();
                    String resposta = "";
                    for (Categoria categoria : categorias){
                        resposta += categoria.toString();
                        resposta += "-----------\n";
                    }
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), resposta));
                    connectionCategoria.desconectar();
                    estado = Estado.STANDBY;
                    break;
                }



                else if (update.message().text().equals("você é um autobot?")) {

                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Não. Também não conheço Optimus Prime... ops..."));
                    //verificação se a mensagem foi enviada com sucesso
                    System.out.println("Mensagem enviada? " + sendResponse.isOk());
                } else {
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Não entendi..."));
                    addLine(displayArea, "Não entendi....\n");
                    //verificação se a mensagem foi enviada com sucesso
                    System.out.println("Mensagem enviada? " + sendResponse.isOk());
                    break;
                }

            }

        }
    }

    private static Bem buscarBem(Integer codigo) {
        ConnectionBem connectionBem = new ConnectionBem();
        connectionBem.conectar();
        ArrayList<Bem> bens = connectionBem.listarBens();

        for(Bem bem : bens){
            if(bem.getCodigo().equals(codigo)){
                return bem;
            }
        }
        connectionBem.desconectar();
        return null;
    }
    private static Bem buscarBem(String nomeBem) {
        ConnectionBem connectionBem = new ConnectionBem();
        connectionBem.conectar();
        ArrayList<Bem> bens = connectionBem.listarBens();

        for(Bem bem : bens){
            if(bem.getNome().equals(nomeBem)){
                return bem;
            }
        }
        connectionBem.desconectar();
        return null;
    }
    private static Bem buscarBemPorDescricao(String descricao) {
        ConnectionBem connectionBem = new ConnectionBem();
        connectionBem.conectar();
        ArrayList<Bem> bens = connectionBem.listarBens();

        for(Bem bem : bens){
            if(bem.getDescricao().contains(descricao)){
                return bem;
            }
        }
        connectionBem.desconectar();
        return null;
    }


    public static Localizacao buscarLocalizacao(String local) {
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
    public static Localizacao buscarLocalizacao(int codigo) {
        ConnectionLocalizacao connectionLocalizacao = new ConnectionLocalizacao();
        connectionLocalizacao.conectar();
        ArrayList<Localizacao> localizacoes = connectionLocalizacao.listarLocalizacoes();

        for (Localizacao localizacao : localizacoes){
            if(localizacao.getCodigo().equals(codigo)){
                connectionLocalizacao.desconectar();
                return localizacao;
            }
        }
        connectionLocalizacao.desconectar();
        return null;
    }

    public static Categoria buscarCategoria(String cat) {
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
    public static Categoria buscarCategoria(int codigo) {
        ConnectionCategoria connectionCategoria = new ConnectionCategoria();
        connectionCategoria.conectar();
        ArrayList<Categoria> categorias= connectionCategoria.listarCategorias();

        for (Categoria categoria : categorias){
            if(categoria.getCodigo().equals(codigo)){
                connectionCategoria.desconectar();
                return categoria;
            }
        }
        connectionCategoria.desconectar();
        return null;
    }

    private static void addLine(TextFlow displayArea, String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayArea.getChildren().add(new Text(message));
            }
        });
    }

    private static void updateStatus(Label botStatus, String status) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                botStatus.setText(status);
            }
        });
    }

}