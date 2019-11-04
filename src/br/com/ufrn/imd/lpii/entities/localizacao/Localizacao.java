package br.com.ufrn.imd.lpii.entities.localizacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

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
    public static boolean cadastrarLocalizacao(TelegramBot bot, Update update) {
        //NAO esta sendo utilizado
//        String local = null;
//        SendResponse sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Insira o nome da localização"));
//
//        if(update.message().text().charAt(0) != '/'){
//            local = update.message().text();
//        }
//        if(local == null){
//            return false;
//        }
//        //SendResponse sendResponse2 = bot.execute(new SendMessage(update.message().chat().id(), "Digite a descricao de " + local + " : "));
//
//        sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Sua localizacao é: " + local));
//
//        return true;
        return true;

    }


}