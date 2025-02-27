package com.neoapps.app.snakegame;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class GameBoard extends Stage {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private Snake snake;

    private Apple apple;

    private GraphicsContext graphicsContext;

    AnimationTimer animationTimer;

    public GameBoard() {
        setComponents();
        setTitle("Sneak Game");
        startAnimation();
    }

    public void setComponents() {

        snake = new Snake();
        snake.increaseSize(WIDTH / 2, HEIGHT / 2);
        snake.increaseSize((WIDTH / 2) + Snake.getTileSize(), (HEIGHT / 2) + Snake.getTileSize());

        apple = new Apple(WIDTH, HEIGHT, Snake.getTileSize());

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();

        VBox vBox = new VBox(canvas);

        Scene scene = new Scene(vBox, WIDTH, HEIGHT);
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.UP && !snake.getDirection().equals("DOWN")) snake.setDirection("UP");
            if (key == KeyCode.DOWN && !snake.getDirection().equals("UP")) snake.setDirection("DOWN");
            if (key == KeyCode.LEFT && !snake.getDirection().equals("RIGHT")) snake.setDirection("LEFT");
            if (key == KeyCode.RIGHT && !snake.getDirection().equals("LEFT")) snake.setDirection("RIGHT");
        });
        setScene(scene);
        scene.getRoot().requestFocus();
    }

    public void startAnimation() {
        animationTimer = new AnimationTimer() {

            long lastUpdate = 0;

            @Override
            public void handle(long now) {

                if (now - lastUpdate >= 100_000_000) { // Cada 100ms

                    update();
                    draw();
                    lastUpdate = now;
                }

            }
        };

        animationTimer.start();
    }

    public void draw() {
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);

        graphicsContext.setFill(Color.GREEN);
        for (int[] location : snake.getBody()) {
            graphicsContext.fillRect(location[0], location[1], Snake.getTileSize(), Snake.getTileSize());
        }

        graphicsContext.setFill(Color.RED);
        graphicsContext.fillOval(apple.getX(), apple.getY(), Snake.getTileSize(), Snake.getTileSize());


    }

    public void update() {
        int newX = snake.getHead()[0];
        int newY = snake.getHead()[1];

        switch (snake.getDirection()) {
            case "DOWN" -> newY += Snake.getTileSize();
            case "UP" -> newY -= Snake.getTileSize();
            case "LEFT" -> newX -= Snake.getTileSize();
            case "RIGHT" -> newX += Snake.getTileSize();
        }

        if (newX < 0 || newX >= WIDTH || newY < 0 || newY >= HEIGHT || hasCollided(newX, newY)) {
            animationTimer.stop();
            showGameOverMessage();
        }

        snake.increaseSize(newX, newY);

        if (newX == apple.getX() && newY == apple.getY()) {
            spawnApple();
        } else {
            snake.decreaseSize();
        }
    }

    public void restartGame() {
        snake.setDirection("RIGHT");
        setComponents();
        startAnimation();
    }

    public boolean hasCollided(int x, int y) {
        for (int i = 1; i < snake.getSize(); i++) {
            if (snake.getBody().get(i)[0] == x && snake.getBody().get(i)[1] == y) {
                return true;
            }
        }

        return false;
    }

    public void spawnApple() {
        apple = new Apple(WIDTH, HEIGHT, Snake.getTileSize());
    }

    public void showGameOverMessage() {

        Label lblGameOver = new Label("Game Over");
        lblGameOver.setFont(new Font(30));

        Button btnTryAgain = new Button("Try Again");
        btnTryAgain.setCursor(Cursor.HAND);
        btnTryAgain.setOnAction(event -> restartGame());

        VBox vBox = new VBox(lblGameOver, btnTryAgain);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, WIDTH, HEIGHT);
        setScene(scene);

    }

}
