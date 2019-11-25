package br.com.ufrn.imd.lpii.classes.gui;


import br.com.ufrn.imd.lpii.classes.main.Bot;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

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

    public void finishButtonPressed(){
        Bot.desativarBot(botStatus);
    }

    public void quitButtonPressed(){
        closeProgram();
    }

    private void closeProgram(){
        Bot.desativarBot(botStatus);
        Platform.exit();
    }


}
