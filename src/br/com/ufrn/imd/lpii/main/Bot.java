package br.com.ufrn.imd.lpii.main;

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

import java.util.List;

public class Bot {
    private static Estado estado = Estado.standby;
    private static String localizacao;
    private static String descricao;

    public static void inicializacaoBot(String token){

        //token do nosso bot patrimonial: 1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc

        //criação do objeto bot com as informações de acesso
        TelegramBot bot = TelegramBotAdapter.build(token);

        //objeto responsavel por receber as mensagens
        GetUpdatesResponse updatesResponse;

        //objeto responsavel por gerenciar o envio de respostas
        SendResponse sendResponse;

        //objeto responsavel por gerenciar o envio de ações do chat
        BaseResponse baseResponse;

        //controle de offset, isto é, a partir desse ID serão lidas as mensagens pendentes na fila
        int m = 0, contador = 0;
        boolean check = false;
        //loop infinito, que pode ser alterado para algum timer de intervalo curto
            while (!check) {
                System.out.println("Info: Buscando novas mensagens...");
                //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
                updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

                //lista de mensagens
                List<Update> updates = updatesResponse.updates();

                //análise de cada ação da mensagem
                for (Update update : updates) {
                    //atualização do offset
                    m = update.updateId() + 1;
                    String mensagem = update.message().text();

                    System.out.println("Recebendo mensagem: " + update.message().text());

                    //envio de 'escrevendo' antes de mandar a resposta
                    baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

                    //verificação de ação de chat foi enviada com sucesso
                    System.out.println("Resposta de ChatAction foi enviada? " + baseResponse.isOk());

                    //envio da mensagem de resposta
                    //brincando com o bot
                    if(estado == Estado.cadastrar_localizacao){
                        if(contador == 0){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localização"));
                            localizacao = update.message().text();
                            contador++;

                        }else{
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira a descricao do local"));
                            descricao = update.message().text();
                            contador++;
                        }
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Local: "+ localizacao));
                        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Descricao: " + descricao));
                        break;
                    }

                    if (update.message().text().equals("/cadastrar_localizacao")) {
                        estado = Estado.cadastrar_localizacao;
                        break;

                       /* sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localização"));
                        if(update.message().text().charAt(0) != '/'){
                            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), update.message().text()));
                        }
                        contador = 1;*/

                        //check = true;
                        //sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "" + Localizacao.cadastrarLocalizacao(bot, update)));
                       // break;

                    }
                    if (update.message().text().equals("/cadastrar_categoria_do_bem")) {
                        //sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "" + Localizacao.cadastrarLocalizacao(bot, update)));


                    }if(update.message().text().equals("/cadastrar_bem")){

                    } if(update.message().text().equals("/listar_localizacoes")){

                    } if(update.message().text().equals("/listar_categorias")){

                    } if(update.message().text().equals("/listar_bens_por_localizacao")){

                    } if(update.message().text().equals("/buscar_bem_por_codigo")){

                    } if(update.message().text().equals("/buscar_bem_por_nome")){

                    } if(update.message().text().equals("/buscar_bem_por_descricao")){

                    } if(update.message().text().equals("/movimentar_bem")){

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




}
