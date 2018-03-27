package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by linux on 3/24/18.
 */

public class Tile {

    public static final Texture img = new Texture("squre_tile.png");
    private Rectangle tile = new Rectangle();
    private float centreX;
    private float centreY;
    private String id;

    public Tile(int height, int width, int i, int j) {
        tile.height = height;
        tile.width = width;
        tile.x = i * width;
        tile.y = j * height;
        id = "[" + i + "," + j + "]";
        centreX = tile.x + width / 2;
        centreY = tile.y + height / 2;

    }

    public Texture getImage() {
        return img;
    }

    public String getId() {
        return this.id;
    }

    public float getHeight() {
        return tile.height;
    }

    public float getWidth() {
        return tile.width;
    }

    public Rectangle getRectangle() {
        return this.tile;
    }

    public float getCentreX() {
        return centreX;
    }

    public float getCetreY() {
        return centreY;
    }


}
