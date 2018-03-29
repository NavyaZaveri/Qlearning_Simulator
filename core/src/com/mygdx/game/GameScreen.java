package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.mygdx.game.Action.DOWN;
import static com.mygdx.game.Action.LEFT;
import static com.mygdx.game.Action.RIGHT;
import static com.mygdx.game.Action.UP;

/**
 * Created by linux on 3/24/18.
 */

public class GameScreen implements Screen {
    private Random random;
    public static SpriteBatch batch;
    private OrthographicCamera camera;
    public static final int SCREEN_HEIGHT = 1200;
    public static final int SCREEN_WIDTH = 1200;
    public static final int cols = 5;
    public static final int rows = 5;
    public int agentPosOffset = 100;
    public QAgent agent = new QAgent(SCREEN_HEIGHT / cols - agentPosOffset, SCREEN_WIDTH / rows - agentPosOffset);
    Board board;
    private SimpleDirectedWeightedGraph<Tile, Reward> graph;
    public static BitmapFont font;
    private Tile startState;
    private List<Tile> fireStates = new ArrayList<>();
    private List<Tile> goalStates = new ArrayList<>();

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        board = new Board(rows, cols);
        graph = new SimpleDirectedWeightedGraph<Tile, Reward>(Reward.class);
        initStateActionPairs();
        setFont();
        agent.setCurrentState(board.getTile(0, 0));
        agent.resetPosition(board.getTile(0, 0));
        startState = board.getTile(0, 0);
        generateFire();
        generateGoal();
        populateGraph();
    }

    private void generateFire() {
        Tile t1 = board.getTile(2, 2);
        Tile t2 = board.getTile(3, 3);
        t1.makeFire();
        t2.makeFire();
        fireStates.add(t1);
        fireStates.add(t2);

    }

    private void generateGoal() {
        Tile t1 = board.getTile(2, 3);
        t1.makeGoal();
        goalStates.add(t1);
    }


    private void initStateActionPairs() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);
                agent.setQ_table(tile);
            }
        }
    }


    private void setFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-ExtraBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);// font size 12 pixels
        generator.dispose();
    }

    private void showGraph() {
        for (Tile v1 : graph.vertexSet()) {
            System.out.println(v1.getId());
            Gdx.app.log("info", "vertex");
            for (Reward r : graph.outgoingEdgesOf(v1)) {
                //System.out.println("neighbor");
                //System.out.println(graph.getEdgeTarget(r).getId());
            }
            System.out.println("                ");
        }
    }

    private Boolean agentOutofBounds() {
        if (agent.getX() > SCREEN_WIDTH || agent.getX() < 0 - agent.getWidth() || agent.getY() > SCREEN_HEIGHT ||
                agent.getY() < 0 - agent.getHeight()
                ) {
            Gdx.app.log("INFO", "OUT OF BOUNDS!!!!!");
            return true;
        }
        return false;

    }


    private void setHorizontalEdges() {
        for (int i = 0; i < rows; i++) {
            Tile v1 = board.getTile(i, 0);
            for (int j = 1; j < cols; j++) {

                Tile v2 = board.getTile(i, j);
                Reward r1 = graph.addEdge(v1, v2);
                graph.setEdgeWeight(r1, 0);
                Reward r2 = graph.addEdge(v2, v1);
                graph.setEdgeWeight(r2, 0);
                v1 = v2;
            }
        }
    }

    private void setVerticalEdges() {
        for (int i = 0; i < cols; i++) {
            Tile v1 = board.getTile(0, i);
            for (int j = 1; j < rows; j++) {
                Tile v2 = board.getTile(j, i);
                Reward r1 = graph.addEdge(v1, v2);
                Reward r2 = graph.addEdge(v2, v1);
                graph.setEdgeWeight(r2, 0);
                graph.setEdgeWeight(r1, 0);
                v1 = v2;
            }
        }
    }

    public Boolean isAgentInFireState() {

        for (Tile tile : fireStates) {
            if (getNewState().getId() == tile.getId()) return true;
        }
        return false;

    }

    public Boolean isAgentInGoalState() {

        for (Tile tile : goalStates) {
            if (getNewState().getId() == tile.getId()) return true;
        }
        return false;
    }

    private void populateGraph() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);
                graph.addVertex(tile);
            }
        }
        setHorizontalEdges();
        setVerticalEdges();
        setFireEdges();
        setGoalEdges();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    private void resetAgentPosition() {

    }

    private void setFireEdges() {

        //replace with fireTile or something


        for (Tile tile : fireStates) {

            Set<Reward> rewards = graph.incomingEdgesOf(tile);
            for (Reward r : rewards) {
                graph.setEdgeWeight(r, -10);
            }
        }

    }

    private void setGoalEdges() {

        for (Tile tile : goalStates) {

            Set<Reward> rewards = graph.incomingEdgesOf(tile);
            for (Reward r : rewards) {
                graph.setEdgeWeight(r, 10);
            }
        }
    }

    private void displayBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);


                batch.draw(tile.getTileImage(), i * tile.getWidth(), j * tile.getHeight(),
                        tile.getWidth(), tile.getHeight());

                if (tile.isFire())
                    batch.draw(tile.getFireImage(), i * tile.getWidth(), j * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());
                if (tile.isGoal())
                    batch.draw(tile.getGoalImage(), i * tile.getWidth(), j * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());

                //font.draw(batch, tile.getId(), tile.getCentreX(), tile.getCentreY());

            }
        }
    }


    private Boolean detectOverlap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.getRectangle().overlaps(agent.pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Tile getNewState() {
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


    private void displayRobot() {
        batch.draw(agent.getImage(), agent.getX(), agent.getY(), agent.getWidth(), agent.getHeight());
    }

    @Override
    public void show() {

    }


    private Boolean changeOfstate() {
        return detectOverlap() && agent.currentKnownState != getNewState() && getNewState() != null;

    }

    private void makeForcedMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) agent.forceMove(UP);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) agent.forceMove(LEFT);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) agent.forceMove(RIGHT);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) agent.forceMove(DOWN);
    }

    public void displayGame() {

        batch.begin();
        displayBoard();
        displayRobot();
        batch.end();
    }


    /*game strategy:
    IF there is a change of state from q1 to q2, do the following
    1) update the qtable for the entry (q1,action)
    2) set the player's current state to q2
    3) play out a move
    otherwise, keep moving in action direction
     */

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        displayGame();

        //handles user input
        makeForcedMove();


        if (agentOutofBounds()) {
            agent.resetPosition(agent.currentKnownState);
            agent.makeNewMove();
            return;
        }

        if (changeOfstate()) {

            double reward = graph.getEdge(agent.currentKnownState, getNewState()).getWeight();
            System.out.println(reward);

            agent.updateQ_table((int) reward, getNewState());

            if (isAgentInFireState() || isAgentInGoalState())
                agent.resetPosition(startState);

            agent.setCurrentState(getNewState());
            agent.makeNewMove();
            // System.out.println(getNewState().getId()+"");

        } else {
            agent.keepMoving();
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
