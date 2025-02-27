package com.neoapps.app.snakegame;

import java.util.Random;

public class Apple {

    private final int[] location = new int[2];

    public Apple(int xBounds, int yBounds, int height) {
        Random random = new Random();
        location[0] = random.nextInt(xBounds / height) * height;
        location[1] = random.nextInt(yBounds / height) * height;
    }

    public int getX() {
        return location[0];
    }

    public int getY() {
        return location[1];
    }
}
