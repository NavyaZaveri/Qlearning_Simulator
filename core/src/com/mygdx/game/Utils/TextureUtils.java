package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Collections;

/**
 * Created by linux on 3/31/18.
 */

public final class TextureUtils {
    private static TextureUtils instance;

    private Texture fireImage;
    private Texture goalImage;
    private Texture agentImage;
    private Texture neutralTileImage;


    private TextureUtils() {
        fireImage = new Texture(Gdx.files.internal("fire.png"));
        goalImage = new Texture(Gdx.files.internal("water.png"));
        agentImage = new Texture(Gdx.files.internal("Robot.png"));
        neutralTileImage = new Texture(Gdx.files.internal("squre_tile.png"));
    }

    public static TextureUtils getInstance() {
        if (instance == null) {
            instance = new TextureUtils();
        }
        return instance;
    }


    public Texture getFireImage() {
        return fireImage;
    }

    public Texture getGoalImage() {
        return goalImage;
    }

    public Texture getRobotImage() {
        return agentImage;
    }
    public Texture getNeutralTileImage(){
        return neutralTileImage;
    }


}
