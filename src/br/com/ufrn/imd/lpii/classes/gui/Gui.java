package br.com.ufrn.imd.lpii.classes.gui;

import br.com.ufrn.imd.lpii.classes.main.Bot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        //root.setUserData(new Date());
        primaryStage.setTitle("Tesouro - Gerenciamento de Banco de Dados");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Bot.desativarBot(new Label("Desativado"));
            primaryStage.close();
        });
    }

}
