package br.com.ufrn.imd.lpii.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {
    private ArrayList<Text> messages;


    @Override
    public void start(Stage stage) throws Exception {
        TextFlow screen = new TextFlow();
        Button button = new Button("Clique Aqui");
        button.setOnAction(e -> update(screen));
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
        stage.setScene(scene);
        stage.setTitle("TextFlow test");
        stage.show();
    }

    private void update(TextFlow screen) {
        screen.getChildren().add(new Text("Hehe\n"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
