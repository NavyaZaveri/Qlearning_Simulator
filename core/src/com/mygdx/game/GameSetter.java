package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by linux on 3/31/18.
 */

public class GameSetter implements Screen {

    private OrthographicCamera camera;
    private Vector3 v;
    private Set<Tile> goalStates;
    private Set<Tile> fireStates;
    Board board;
    SpriteBatch batch;
    BitmapFont font;
    Vector3 mousePos;
    Boolean finishedSetup = false;
    GameScreen g;

    public GameSetter() {

        camera = new OrthographicCamera(1200, 1200);
        camera.setToOrtho(false, 1200, 1200);
        board = new Board(5, 5);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        font = new BitmapFont();
        font.setColor(Color.BLUE);
        mousePos = new Vector3();

        goalStates = new HashSet<>();
        fireStates = new HashSet<>();
    }

    private void toggle(Tile tile) {

        if (!tile.isGoal() && !tile.isFire()) {
            tile.makeFire();
        } else if (tile.isFire()) {
            tile.makeGoal();
        } else if (tile.isGoal()) {
            tile.makeNeutral();
        }
    }

    public Set<Tile> getGoalStates() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.isGoal()) {
                    this.goalStates.add(tile);
                }

            }
        }
        return Collections.unmodifiableSet(this.goalStates);
    }

    public Set<Tile> getFireStates() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.isFire()) {
                    this.fireStates.add(tile);
                }

            }
        }
        return Collections.unmodifiableSet(this.fireStates);

    }

    @Override
    public void render(float delta) {
        if (finishedSetup) {
            g.render(delta);
            return;
        }
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            getFireStates();
            getGoalStates();
            g = new GameScreen(batch, this.board,goalStates,fireStates);
            finishedSetup = true;
        }

        batch.begin();
        displayBoard();
        batch.end();
        if (Gdx.input.justTouched()) {
            mousePos.x = Gdx.input.getX();
            mousePos.y = Gdx.input.getY();
            camera.unproject(mousePos);
            Tile t = detectTile(mousePos.x, mousePos.y);
            System.out.println("x cord is" + mousePos.x);
            toggle(t);
            batch.begin();
            font.draw(batch, "wooooo", mousePos.x, mousePos.y);
            batch.end();
        }
    }

    private Tile detectTile(float x, float y) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getTile(i, j).getRectangle().contains(x, y)) {
                    System.out.println(board.getTile(i, j).getId());
                    return board.getTile(i, j);
                }

            }
        }
        return null;
    }


    private void displayBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = board.getTile(i, j);

                batch.draw(tile.getNeutralTileImage(), i * tile.getWidth(), j * tile.getHeight(),
                        tile.getWidth(), tile.getHeight());

                if (tile.isFire())
                    batch.draw(tile.getFireImage(), i * tile.getWidth(), j * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());
                if (tile.isGoal())
                    batch.draw(tile.getGoalImage(), i * tile.getWidth(), j * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());

                font.draw(batch, tile.getCentreX() + "", tile.getCentreX(), tile.getCentreY());


            }
        }
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
