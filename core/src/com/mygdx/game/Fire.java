package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by linux on 3/27/18.
 */

public class Fire {

    public int row;
    public int col;

    private static final Texture img = new Texture(Gdx.files.internal("fire.png"));

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Texture getImg() {
        return img;
    }


}
