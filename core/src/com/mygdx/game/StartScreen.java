package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen extends Game {
    SpriteBatch batch;
    Texture backgroundImge;
    Boolean gameStarted = false;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render() {
        if (gameStarted) {
            super.render();

        } else if (Gdx.input.isTouched()) {
            super.setScreen(new GameSetup());
            gameStarted = true;

        }
        else {
            batch.begin();
            font.draw(batch, "Weclome! Click anywhere to beign", 200, 200);
            batch.end();
        }
    }
}

