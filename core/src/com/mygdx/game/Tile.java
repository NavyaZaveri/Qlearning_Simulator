package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by linux on 3/24/18.
 */

public class Tile {

    public static final Texture img = new Texture("android/assets/squre_tile.png");
    private Rectangle tile = new Rectangle();
    private String id;

    public Tile(int height, int width, int i, int j) {
        tile.height = height;
        tile.width = width;
        tile.x = i;
        tile.y = j;
        id = "[" + i + "," + j + "]";
    }

    public Texture getImage() {
        return img;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public float getHeight() {
        return tile.height;
    }

    public float getWidth() {
        return tile.width;
    }

}
