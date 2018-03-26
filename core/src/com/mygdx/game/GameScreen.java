package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;


import java.util.stream.Collectors;

import static com.mygdx.game.Action.DOWN;
import static com.mygdx.game.Action.LEFT;
import static com.mygdx.game.Action.RIGHT;
import static com.mygdx.game.Action.UP;

/**
 * Created by linux on 3/24/18.
 */

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    public static final int SCREEN_HEIGHT = 1200;
    public static final int SCREEN_WIDTH = 1200;
    public static final int cols = 3;
    public static final int rows = 3;
    public int robotOffset = 150;
    public QAgent agent = new QAgent(SCREEN_HEIGHT / cols - robotOffset, SCREEN_WIDTH / rows - robotOffset);
    Board board;
    SimpleDirectedWeightedGraph<Tile, Reward> graph;
    BitmapFont font;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        board = new Board(rows, cols);
        graph = new SimpleDirectedWeightedGraph<Tile, Reward>(Reward.class);
        populateGraph();
        showGraph();
        font = new BitmapFont();
        agent.setCurrentState(board.getTile(0, 0));
    }

    private void showGraph() {
        for (Tile v1 : graph.vertexSet()) {
            System.out.println(v1.getId());
            Gdx.app.log("info", "vertex");
            for (Reward r : graph.outgoingEdgesOf(v1)) {
                System.out.println("neighbor");
                System.out.println(graph.getEdgeTarget(r).getId());
            }
            System.out.println("                ");
        }
    }


    private void setHorizontalEdges() {
        for (int i = 0; i < rows; i++) {
            Tile v1 = board.getTile(i, 0);
            for (int j = 1; j < cols; j++) {

                Tile v2 = board.getTile(i, j);
                Reward r1 = graph.addEdge(v1, v2);
                graph.setEdgeWeight(r1, 20);
                Reward r2 = graph.addEdge(v2, v1);
                graph.setEdgeWeight(r2, 20);
                v1 = v2;
            }
        }
    }

    private void setVerticalEges() {
        for (int i = 0; i < cols; i++) {
            Tile v1 = board.getTile(0, i);
            for (int j = 1; j < rows; j++) {
                Tile v2 = board.getTile(j, i);
                Reward r1 = graph.addEdge(v1, v2);
                Reward r2 = graph.addEdge(v2, v1);
                graph.setEdgeWeight(r2, 20);
                graph.setEdgeWeight(r1, 20);
                v1 = v2;
            }
        }
    }

    private void populateGraph() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);
                graph.addVertex(tile);
            }
        }
        setHorizontalEdges();
        setVerticalEges();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void resetAgentPosition() {

    }

    private void drawBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);
                batch.draw(tile.getImage(), i * tile.getWidth(), j * tile.getHeight(),
                        tile.getWidth(), tile.getHeight());
                font.draw(batch, tile.getId(), i * tile.getWidth(), j * tile.getHeight());

            }
        }
    }


    private Boolean detectOverlap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.getRectangle().contains(agent.pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Tile getCurrentState() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.getRectangle().contains(agent.pos)) {
                    return tile;
                }
            }
        }
        return null;

    }


    private void drawRobot() {
        batch.draw(agent.getImage(), agent.getX(), agent.getY(), agent.getWidth(), agent.getHeight());
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
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) agent.forceMove(UP);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) agent.forceMove(LEFT);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) agent.forceMove(RIGHT);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) agent.forceMove(DOWN);
        if (detectOverlap() && agent.currentState != getCurrentState()) {
            System.out.println(getCurrentState().getId());
            System.out.println(graph.getEdge(agent.currentState,getCurrentState()).getWeight());
            agent.currentState = getCurrentState();
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
