package br.com.ufrn.imd.lpii.classes.gui;

import br.com.ufrn.imd.lpii.classes.main.Bot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class mainScreenController {

    @FXML
    private ScrollPane scrollWindow;

    @FXML
    private TextFlow displayArea;

    @FXML
    private Button startButton;

    @FXML
    private Button finishButton;

    public void startButtonPressed(){
        Thread botThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bot.inicializacaoBot("1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc");
            }
        });

        botThread.start();
    }

    public void updateDisplay(String message){
        Text text = new Text(message);
        displayArea.getChildren().add(text);
    }

}
