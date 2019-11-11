package br.com.ufrn.imd.lpii.classes.main;

import br.com.ufrn.imd.lpii.classes.persistence.ConnectionBem;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionCategoria;
import br.com.ufrn.imd.lpii.classes.persistence.ConnectionLocalizacao;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage window;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            window = primaryStage;
            TextFlow screen = new TextFlow();
            Button button = new Button("Iniciar Bot");
            button.setOnAction(e -> iniciarBot());
            VBox layout = new VBox();
            layout.setSpacing(20);
            layout.setPadding(new Insets(10, 5, 20, 5));
            StackPane stackpane = new StackPane();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(screen);
            scrollPane.setVvalue(1.0d);
            scrollPane.setPrefHeight(500);

            layout.getChildren().addAll(scrollPane, button);
            stackpane.getChildren().add(layout);

            Scene scene = new Scene(stackpane, 400, 400);
            window.setScene(scene);
            window.setTitle("TextFlow test");
            window.show();
    }

    private void iniciarBot() {
        Task taskRunner = new Task();
        Thread threadBot= new Thread(taskRunner);
        threadBot.start();
    }

    public class Task implements Runnable {
        @Override
        public void run() {
            Bot.inicializacaoBot("1048746356:AAEDDgr7PPTnQ0hQuxSaZdDp3AVVYErsTDc");
        }
    }

    public static Stage getWindow() {
        return window;
    }

    public void updateScreen(){
        System.out.println("teste");
    }
}
