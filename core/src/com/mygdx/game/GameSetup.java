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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.mygdx.game.Utils.Constants.COLUMNS;
import static com.mygdx.game.Utils.Constants.ROWS;
import static com.mygdx.game.Utils.Constants.SCREEN_HEIGHT;
import static com.mygdx.game.Utils.Constants.SCREEN_WIDTH;


public class GameSetup implements Screen {

    private OrthographicCamera camera;
    private Set<Tile> goalStates;
    private Set<Tile> fireStates;
    Board board;
    SpriteBatch batch;
    BitmapFont font;
    Vector3 mousePos;
    Boolean finishedSetup = false;
    GameScreen g;

    public GameSetup() {

        camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        board = new Board(ROWS, COLUMNS);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        setFont();
        font.setColor(Color.BLUE);
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
            Tile t = detectTile(mousePos.x, mousePos.y);
            System.out.println("x cord is" + mousePos.x);
            toggle(t);
        }
    }


    private Tile detectTile(float x, float y) {
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

    private void setFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-ExtraBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);// font size 12 pixels
        generator.dispose();
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
