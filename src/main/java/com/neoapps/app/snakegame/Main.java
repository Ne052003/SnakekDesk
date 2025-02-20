package com.neoapps.app.snakegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox, 500, 500);
        stage.setTitle("SnakeGame");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}