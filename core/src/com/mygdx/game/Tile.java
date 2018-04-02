package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Enums.State;
import com.mygdx.game.Utils.TextureUtils;

public class Tile {

    private final Texture img = TextureUtils.getInstance().getNeutralTileImage();
    private final Texture fireImage = TextureUtils.getInstance().getFireImage();
    private final Texture goalImage = TextureUtils.getInstance().getGoalImage();

    private Rectangle tile;
    private float centreX;
    private float centreY;
    private String id;
    private State state;


    public Tile(float height, float width, int i, int j) {
        tile = new Rectangle();
        tile.height = height;
        tile.width = width;
        tile.x = j * width;
        tile.y = i * height;
        id = "[" + i + "," + j + "]";
        centreX = tile.x + width / 2;
        centreY = tile.y + height / 2;

        //default state
        state = State.NEUTRAL;

    }

    public Texture getNeutralTileImage() {
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
        state = State.FIRE;
    }

    public void makeGoal() {
        state = State.GOAL;
    }

    public void makeNeutral() {
        state = State.NEUTRAL;
    }

    public Boolean isFire() {
        return state == State.FIRE;
    }

    public Boolean isGoal() {
        return state == State.GOAL;
    }

    public Boolean isNeutral() {
        return state == State.NEUTRAL;
    }

}
