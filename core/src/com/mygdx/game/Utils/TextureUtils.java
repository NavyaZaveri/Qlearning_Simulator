package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by linux on 3/31/18.
 */

public final class TextureUtils {

    public static Texture getFireImage() {
        return new Texture(Gdx.files.internal("fire.png"));
    }

    public static Texture getGoalImage() {
        return new Texture(Gdx.files.internal("water.png"));
    }

    public static Texture getRobotImage() {
        return new Texture(Gdx.files.internal("Robot.png"));
    }

}
