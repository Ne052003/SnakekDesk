package com.neoapps.app.snakegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {


    @Override
    public void start(Stage stage) {

        Button button = new Button("Let's go");
        button.setFont(new Font(20));
        button.setCursor(Cursor.HAND);
        button.setOnAction(event -> {
            GameBoard gameBoard = new GameBoard();
            stage.close();
            gameBoard.show();

        });

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/neoapps/app/snakegame/images/SnakeGameLogo.jpg")));
        ImageView imageView = new ImageView(image);

        VBox vBox = new VBox(imageView, button);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        Scene scene = new Scene(vBox, 600, 600);
        stage.setTitle("SnakeGame");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}