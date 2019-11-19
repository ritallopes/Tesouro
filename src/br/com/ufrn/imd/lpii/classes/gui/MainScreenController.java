package br.com.ufrn.imd.lpii.classes.gui;

import br.com.ufrn.imd.lpii.classes.main.Bot;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class MainScreenController {

    @FXML
    private StackPane stackPane;

    @FXML
    private ScrollPane scrollWindow;

    @FXML
    private TextFlow displayArea;

    @FXML
    private Button startButton;

    @FXML
    private Button finishButton;

    @FXML
    private Label botStatus;

    public void startButtonPressed(){

        Task<Void> botTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Bot.inicializacaoBot("1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc", displayArea, botStatus);
                return null;
            }

        };
        Thread botThread = new Thread(botTask);
        botThread.start();
    }
}
