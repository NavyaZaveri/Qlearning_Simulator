package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Utils.FontUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.mygdx.game.Utils.GameConstants.*;

public class GameSetup implements Screen {

    private OrthographicCamera camera;
    private Set<Tile> goalStates;
    private Set<Tile> fireStates;
    private Board board;
    private SpriteBatch batch;
    private BitmapFont font;
    private Vector3 mousePos;
    private Boolean finishedSetup = false;
    private GameScreen g;

    public GameSetup() {

        camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        board = new Board(ROWS, COLUMNS);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        mousePos = new Vector3();
        goalStates = new HashSet<>();
        fireStates = new HashSet<>();
    }


    private void toggle(Tile tile) {

        if (tile.isNeutral()) {
            tile.makeFire();
        } else if (tile.isFire()) {
            tile.makeGoal();
        } else if (tile.isGoal()) {
            tile.makeNeutral();
        }
    }

    //@returns: an immutable set of goal states
    private Set<Tile> getGoalStates() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.isGoal()) {
                    this.goalStates.add(tile);
                }

            }
        }
        return Collections.unmodifiableSet(this.goalStates);
    }

    //@returns: an immutable set of goal states
    private Set<Tile> getFireStates() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.isFire()) {
                    this.fireStates.add(tile);
                }

            }
        }
        return Collections.unmodifiableSet(this.fireStates);

    }

    private void clearScreen(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }


    @Override
    public void render(float delta) {
        if (finishedSetup) {
            g.render(delta);
            return;
        }
        camera.update();
        clearScreen();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            getFireStates();
            getGoalStates();
            g = new GameScreen(batch, this.board, this.goalStates, this.fireStates);
            finishedSetup = true;
        }


        batch.begin();
        board.display(batch);
        batch.end();

        if (Gdx.input.justTouched()) {
            mousePos.x = Gdx.input.getX();
            mousePos.y = Gdx.input.getY();
            camera.unproject(mousePos);
            Tile t = detectTileOnClick(mousePos.x, mousePos.y);
            toggle(t);
        }
    }


    //finds the tile closest to on mouse coordinates
    private Tile detectTileOnClick(float x, float y) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board.getTile(i, j).getRectangle().contains(x, y)) {
                    System.out.println(board.getTile(i, j).getId());
                    return board.getTile(i, j);
                }

            }
        }
        return null;
    }


    @Override
    public void show() {

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
