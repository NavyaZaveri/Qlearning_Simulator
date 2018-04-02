package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Utils.FontUtils;

public class StartScreen extends Game {
    SpriteBatch batch;
    Boolean gameStarted = false;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = FontUtils.getInstance().getFont(30, Color.BLUE);
    }

    @Override
    public void render() {
        if (gameStarted) {
            super.render();

        } else if (Gdx.input.isTouched()) {
            super.setScreen(new GameSetup());
            gameStarted = true;

        } else {
            batch.begin();
            font.draw(batch, "Welcome! Click anywhere to begin", 70, 200);
            batch.end();
        }
    }
}

