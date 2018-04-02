package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by linux on 4/2/18.
 */

public final class FontUtils {
    private static FontUtils instance;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private FontUtils() {
        setFont();
    }

    private void setFont() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-ExtraBold.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    public static FontUtils getInstance() {

        if (instance == null) {
            instance = new FontUtils();
        }

        return instance;
    }

    public BitmapFont getFont(int size, Color color) {
        parameter.size = size;
        font = generator.generateFont(parameter);
        font.setColor(color);
        generator.dispose();
        return font;
    }

}
