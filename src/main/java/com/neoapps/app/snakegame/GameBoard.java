package com.neoapps.app.snakegame;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;


public class GameBoard extends Stage {

    private final int WIDTH = 600;
    private final int HEIGHT = 640;

    private final int CANVAS_WIDTH = 600;
    private final int CANVAS_HEIGHT = 600;

    private Snake snake;

    private Apple apple;

    private int score = 0;

    private boolean isStopped = false;

    /**
     * Confirms if the last keyboard event (direction) has been updated on screen
     */
    private boolean isFrameUpdated = false;

    private GraphicsContext graphicsContext;

    private AnimationTimer animationTimer;

    private Label lblCurrentScore;

    public GameBoard() {
        setComponents();
        setTitle("Sneak Game");
        setResizable(false);
        startAnimation();
    }

    public void setComponents() {

        snake = new Snake();
        snake.increaseSize(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        snake.increaseSize((CANVAS_WIDTH / 2) + Snake.getTileSize(), (CANVAS_HEIGHT / 2) + Snake.getTileSize());

        apple = new Apple(CANVAS_WIDTH, CANVAS_HEIGHT, Snake.getTileSize());

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();

        Scene scene = getScene(canvas);
        setScene(scene);
        scene.getRoot().requestFocus();
    }

    private Scene getScene(Canvas canvas) {

        Label lblScore = new Label("Score ");
        lblScore.getStyleClass().add("labelScore");

        lblCurrentScore = new Label("0");
        lblCurrentScore.setFont(new Font(15));
        lblCurrentScore.setStyle("-fx-text-fill:green;");

        HBox topBar = new HBox(lblScore, lblCurrentScore);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(20);

        VBox vBox = new VBox(topBar, canvas);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(canvas, Priority.ALWAYS);

        Scene scene = new Scene(vBox, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());
        scene.setOnKeyPressed(event -> {

            KeyCode key = event.getCode();

            if (key == KeyCode.P) {
                toggleAnimation();
            } else if (isFrameUpdated) {
                changeSnakeDirection(event.getCode());
                isFrameUpdated = false;
            }
        });
        return scene;
    }

    public void toggleAnimation() {
        if (isStopped) {
            animationTimer.start();
        } else {
            animationTimer.stop();
        }
        isStopped = !isStopped;
    }

    public void changeSnakeDirection(KeyCode key) {
        switch (key) {
            case KeyCode.UP -> {
                if (!snake.getDirection().equals("DOWN")) snake.setDirection("UP");
            }
            case KeyCode.DOWN -> {
                if (!snake.getDirection().equals("UP")) snake.setDirection("DOWN");
            }
            case KeyCode.RIGHT -> {
                if (!snake.getDirection().equals("LEFT")) snake.setDirection("RIGHT");
            }
            case KeyCode.LEFT -> {
                if (!snake.getDirection().equals("RIGHT")) snake.setDirection("LEFT");
            }
        }
    }

    public void startAnimation() {
        animationTimer = new AnimationTimer() {

            long lastUpdate = 0;

            @Override
            public void handle(long now) {

                if (now - lastUpdate >= 100_000_000) { // Every 100ms
                    update();
                    draw();
                    lastUpdate = now;
                    isFrameUpdated = true;
                }
            }
        };

        animationTimer.start();
    }

    public void draw() {
        graphicsContext.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

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
        if (newX < 0 || newX >= CANVAS_WIDTH || newY < 0 || newY >= CANVAS_HEIGHT || hasCollided(newX, newY)) {
            animationTimer.stop();
            showGameOverMessage();
        }


        snake.increaseSize(newX, newY);

        if (newX == apple.getX() && newY == apple.getY()) {
            spawnApple();
            score++;
            lblCurrentScore.setText(String.valueOf(score));
        } else {
            snake.decreaseSize();
        }
    }

    public void restartGame() {
        snake.setDirection("RIGHT");
        score = 0;
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
        apple = new Apple(CANVAS_WIDTH, CANVAS_HEIGHT, Snake.getTileSize());
    }

    public void showGameOverMessage() {

        Label lblGameOver = new Label("Game Over");
        lblGameOver.setFont(new Font(30));
        lblGameOver.setStyle("-fx-text-fill: White;");

        Label lblFinalScore = new Label(String.valueOf(score));
        lblFinalScore.setFont(new Font(20));
        lblFinalScore.setStyle("-fx-text-fill: White;");

        Button btnTryAgain = new Button("Try Again");
        btnTryAgain.getStyleClass().add("button-black-green");
        btnTryAgain.setOnAction(event -> restartGame());

        VBox vBox = new VBox(lblGameOver, lblFinalScore, btnTryAgain);
        vBox.setStyle("-fx-background-color: black;");
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());
        setScene(scene);

    }

}
