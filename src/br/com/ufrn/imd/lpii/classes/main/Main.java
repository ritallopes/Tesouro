package br.com.ufrn.imd.lpii.classes.main;

import br.com.ufrn.imd.lpii.classes.gui.Gui;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.text.Text;

import java.io.IOException;


public class Main{
    public static void main(String[] args) throws IOException {
        
        Thread userInterface = new Thread(new Runnable() {
            @Override
            public void run() {
                Application.launch(Gui.class, args);
            }
        });

        userInterface.start();
    }
}
