package br.com.ufrn.imd.lpii.classes.gui;


import br.com.ufrn.imd.lpii.classes.main.Bot;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

/**
 *Classe que controla os elementos da interface.
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, José Lúcio da Silva Júnior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public class MainScreenController {

    @FXML
    private StackPane stackPane;

    @FXML
    private TextArea textArea;

    @FXML
    private Label botStatus;

    @FXML
    private Button startButton;

    @FXML
    private Button finishButton;

    @FXML
    private Button quitButton;

    /**
     * Método chamado ao pressionar o botão iniciar Bot.
     *
     * Cria-se uma nova tarefa que inicializa o bot e a executa em outro thread.
     */
    public void startButtonPressed(){

        Task<Void> botTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Bot.inicializacaoBot("1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc", textArea, botStatus);
                return null;
            }

        };
        Thread botThread = new Thread(botTask);
        botThread.start();
    }

    /**
     * Método chamado ao pressionar o botão encerrar Bot.
     *
     * Desativa o bot.
     */
    public void finishButtonPressed(){
        Bot.desativarBot(botStatus);
    }
    /**
     * Método chamado ao pressionar o sair.
     *
     * Chama o método closeProgram para fechar o programa.
     */
    public void quitButtonPressed(){
        closeProgram();
    }
    /**
     * Método que finaliza a execução do programa.
     *
     * Desativa-se o bot e em seguida fecha o programa.
     */
    private void closeProgram(){
        Bot.desativarBot(botStatus);
        Platform.exit();
    }


}
