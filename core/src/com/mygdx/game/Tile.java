package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by linux on 3/24/18.
 */

public class Tile {

    public static final Texture img = new Texture("android/assets/squre_tile.png");
    private  int width;
    private int height;
    private String id;

    public Tile(int height, int width, int i, int j){
        this.height = height;
        this.width = width;
        id = "["+i+","+j+"]";
    }

    public Texture getImage(){
        return img;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
