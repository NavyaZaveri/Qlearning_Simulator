package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.jgrapht.graph.SimpleGraph;

/**
 * Created by linux on 3/24/18.
 */

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    public static final int SCREEN_HEIGHT = 1200;
    public static final int SCREEN_WIDTH = 1200;
    public static final int cols = 3;
    public static final int rows = 5;
    public QAgent agent = new QAgent(SCREEN_HEIGHT / cols-150, SCREEN_WIDTH / rows-150);
    Board board;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        board = new Board(rows, cols);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);
                batch.draw(tile.getImage(), i * tile.getWidth(), j * tile.getHeight(),
                        tile.getWidth(), tile.getHeight());
            }
        }
    }

    private void drawRobot() {
        //batch.setColor(0,0,0,0.1f);
        batch.draw(agent.getImage(), 30, 30, agent.getWidth(), agent.getHeight());
        batch.setColor(1,1,1,1f);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawBoard();
        drawRobot();
        batch.end();
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
