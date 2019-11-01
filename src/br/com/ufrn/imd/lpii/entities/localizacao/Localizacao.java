package br.com.ufrn.imd.lpii.entities.localizacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class Localizacao {
    private String nome;
    private String descricao;

    //Construtores
    public Localizacao() {
    }

    public Localizacao(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //método em fase de testes, pode estar meio maluco
    public static boolean cadastrarLocalizacao(TelegramBot bot, Update update){
        //System.out.println("digite a localizacao: ");

        SendResponse sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "digite a localizacao: "));
        String mensagem = update.message().text();
        System.out.println(mensagem);
        return true;
    }
}
