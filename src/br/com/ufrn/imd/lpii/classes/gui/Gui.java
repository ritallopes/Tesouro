package br.com.ufrn.imd.lpii.classes.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingDeque;

public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        root.setUserData(new Date());
        primaryStage.setTitle("Bot Window");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }
}
