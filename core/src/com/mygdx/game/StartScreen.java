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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.mygdx.game.Utils.FontUtils;

public final class StartScreen extends Game {
    private SpriteBatch batch;
    private Boolean gameStarted = false;
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = FontUtils.getInstance().getFont(30, Color.BLUE);
    }

    private void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render() {
        clearScreen();

        if (gameStarted) {
            super.render();

        } else if (Gdx.input.isTouched()) {
            super.setScreen(new InputHandler(batch));
            gameStarted = true;

        } else {
            batch.begin();
            font.draw(batch, "Welcome! Click anywhere to begin", 70, 200);
            batch.end();

        }
    }
}

