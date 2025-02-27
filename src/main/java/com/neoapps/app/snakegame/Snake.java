package com.neoapps.app.snakegame;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static final int TILE_SIZE = 20;

    private String direction = "RIGHT";

    private final List<int[]> body = new ArrayList<>();

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public int getSize() {
        return body.size();
    }

    public int[] getHead() {
        return body.getFirst();
    }

    public List<int[]> getBody() {
        return body;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void increaseSize(int x, int y) {
        body.addFirst(new int[]{x, y});
    }

    public void decreaseSize() {
        body.removeLast();
    }
}
