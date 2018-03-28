package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by linux on 3/24/18.
 */

public class Tile {

    public static final Texture img = new Texture(Gdx.files.internal("squre_tile.png"));
    public static final Texture fireImage = new Texture(Gdx.files.internal("fire.png"));
    public static final Texture goalImage = new Texture(Gdx.files.internal("water.png"));

    private Rectangle tile = new Rectangle();
    private float centreX;
    private float centreY;
    private String id;
    public Boolean isFire = false;
    private boolean isGoal = false;


    public Tile(int height, int width, int i, int j) {
        tile.height = height;
        tile.width = width;
        tile.x = i * width;
        tile.y = j * height;
        id = "[" + i + "," + j + "]";
        centreX = tile.x + width / 2;
        centreY = tile.y + height / 2;

    }

    public Texture getTileImage() {
        return img;
    }

    public Texture getFireImage() {
        return fireImage;
    }

    public Texture getGoalImage() {
        return goalImage;
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

    public float getCentreY() {
        return centreY;
    }

    public void makeFire() {
        this.isFire = true;
    }

    public void makeGoal() {
        this.isGoal = true;
    }

    public Boolean isFire() {
        return this.isFire;
    }

    public Boolean isGoal() {
        return this.isGoal;
    }
}
