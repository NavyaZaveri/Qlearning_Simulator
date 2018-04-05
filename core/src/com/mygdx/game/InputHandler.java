package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.mygdx.game.Utils.FontUtils;
import com.mygdx.game.Utils.GameConstants;


/*
Allows the user to set the dimensions of the game board
 */

public final class InputHandler implements Screen {

    private Stage stage;
    private Spinner rowSpinner;
    private Spinner colSpinner;
    private Boolean inputHandlingDone = false;
    private BitmapFont font;
    private SpriteBatch batch;
    private int rows;
    private int columns;
    private GameSetup g;

    private Spinner createSpinner(String title, float x, float y) {
        IntSpinnerModel intModel = new IntSpinnerModel(2, 2, 10, 1);
        Spinner intSpinner = new Spinner(title, intModel);

        intSpinner.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                System.out.println("changed int spinner to: " + intModel.getValue());
            }
        });

        intSpinner.setPosition(x, y);
        intSpinner.setWidth(500);
        return intSpinner;

    }

    private void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    public InputHandler(SpriteBatch batch) {
        VisUI.load();
        stage = new Stage();

        //hardcoded positions for row bar and column bar
        rowSpinner = createSpinner("ROWS", 30, 200);
        colSpinner = createSpinner("COLUMNS", 30, 100);

        stage.addActor(rowSpinner);
        stage.addActor(colSpinner);
        Gdx.input.setInputProcessor(stage);

        this.batch = batch;
        font = FontUtils.getInstance().getFont(30, Color.BROWN);
    }

    @Override
    public void show() {

    }

    private Boolean inputConfirmed() {
        return Gdx.input.isKeyPressed(Input.Keys.ENTER);

    }

    private void setGameRowValue() {
        rows = Integer.parseInt(rowSpinner.getModel().getText());
        GameConstants.setROWS(rows);
    }

    private void setGameColumnValue() {
        columns = Integer.parseInt(colSpinner.getModel().getText());
        GameConstants.setCOLUMNS(columns);

    }

    @Override
    public void render(float delta) {
        clearScreen();
        if (inputHandlingDone) {
            g.render(delta);
            return;
        }

        stage.draw();
        batch.begin();

        //hardcoded font position
        font.draw(batch, "SET MATRIX DIMENSIONS", Gdx.graphics.getWidth() / 2 - 200, 300);
        batch.end();

        if (inputConfirmed()) {
            setGameRowValue();
            setGameColumnValue();

            g = new GameSetup(batch);
            inputHandlingDone = true;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
