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
import com.mygdx.game.Agents.*;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mygdx.game.Utils.Action.DOWN;
import static com.mygdx.game.Utils.Action.LEFT;
import static com.mygdx.game.Utils.Action.RIGHT;
import static com.mygdx.game.Utils.Action.UP;
import static com.mygdx.game.Utils.Constants.*;

/**
 * Created by linux on 3/24/18.
 */

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private int agentPosOffset = 50;
    public Agent agent;
    private Board board;
    private SimpleDirectedWeightedGraph<Tile, Reward> graph;
    private static BitmapFont font;
    private Tile startState;
    private List<Tile> fireStates = new ArrayList<>();
    private List<Tile> goalStates = new ArrayList<>();

    public GameScreen(SpriteBatch batch, Board board, Set<Tile> goalStates, Set<Tile> fireStates) {
        agent = new QlearningAgent(SCREEN_HEIGHT / ROWS - agentPosOffset, SCREEN_WIDTH / COLUMNS - agentPosOffset);
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.board = board;
        this.goalStates.addAll(goalStates);
        this.fireStates.addAll(fireStates);
        graph = new SimpleDirectedWeightedGraph<Tile, Reward>(Reward.class);
        initStateActionPairs();
        setFont();
        agent.setCurrentState(board.getTile(0, 0));
        agent.resetPosition(board.getTile(0, 0));
        startState = board.getTile(0, 0);
        populateGraph();
        // showGraph();
    }


    private void initStateActionPairs() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
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
                System.out.println("neighbor");
                System.out.println(graph.getEdgeTarget(r).getId());
            }
            System.out.println("                ");
        }
    }

    private Boolean agentOutOfBounds() {
        if (agent.getX() > SCREEN_WIDTH ||
                agent.getX() < 0 - agent.getWidth() ||
                agent.getY() > SCREEN_HEIGHT ||
                agent.getY() < 0 - agent.getHeight()
                ) {
            Gdx.app.log("INFO", "OUT OF BOUNDS!!!!!");
            return true;
        }
        return false;
    }


    private void setHorizontalEdges() {
        for (int i = 0; i < ROWS; i++) {
            Tile v1 = board.getTile(i, 0);
            for (int j = 1; j < COLUMNS; j++) {

                Tile v2 = board.getTile(i, j);
                setEdge(v1, v2, NEUTRAL_REWARD);
                setEdge(v2, v1, NEUTRAL_REWARD);
                v1 = v2;
            }
        }
    }

    private void setEdge(Tile v1, Tile v2, float rewardValue) {
        Reward r = graph.addEdge(v1, v2);
        graph.setEdgeWeight(r, rewardValue);

    }

    private void setVerticalEdges() {
        for (int i = 0; i < COLUMNS; i++) {
            Tile v1 = board.getTile(0, i);
            for (int j = 1; j < ROWS; j++) {
                Tile v2 = board.getTile(j, i);
                setEdge(v1, v2, NEUTRAL_REWARD);
                setEdge(v2, v1, NEUTRAL_REWARD);
                v1 = v2;
            }
        }
    }

    private Boolean isAgentInFireState() {

        for (Tile tile : fireStates) {
            if (getNewState().isFire()) {
                System.out.println("detected fireeeee");
                return true;
            }
        }
        return false;
    }


    private Boolean isAgentInGoalState() {

        for (Tile tile : goalStates) {
            if (getNewState().isGoal()) {
                return true;
            }
        }
        return false;
    }

    private void populateGraph() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
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

    private void setFireEdges() {

        for (Tile tile : fireStates) {

            Set<Reward> rewards = graph.incomingEdgesOf(tile);
            for (Reward r : rewards) {
                System.out.println("fire " + tile.getId());
                graph.setEdgeWeight(r, NEGATIVE_REWARD);
            }
        }

    }

    private void setGoalEdges() {

        for (Tile tile : goalStates) {

            Set<Reward> rewards = graph.incomingEdgesOf(tile);
            for (Reward r : rewards) {
                graph.setEdgeWeight(r, POSITIVE_REWARD);
                System.out.println("goal" + tile.getId());
            }
        }
    }

    private void displayBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Tile tile = board.getTile(i, j);


                batch.draw(tile.getNeutralTileImage(), j * tile.getWidth(), i * tile.getHeight(),
                        tile.getWidth(), tile.getHeight());

                if (tile.isFire())
                    batch.draw(tile.getFireImage(), j * tile.getWidth(), i * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());
                if (tile.isGoal())
                    batch.draw(tile.getGoalImage(), j * tile.getWidth(), i * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());

                //font.draw(batch, tile.getId(), tile.getCentreX(), tile.getCentreY());
                Double value = agent.getBestValueAtState(tile);
                font.draw(batch, value + "", tile.getCentreX(), tile.getCentreY());

            }
        }
    }


    private Boolean detectOverlap() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.getRectangle().overlaps(agent.pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Tile getNewState() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.getRectangle().contains(agent.pos)) {
                    return tile;
                }
            }
        }
        return null;

    }


    private void displayRobot() {
        batch.draw(agent.getImage(), agent.getX(),
                agent.getY(), agent.getWidth(), agent.getHeight());
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

    private void displayGame() {

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


        if (agentOutOfBounds()) {
            agent.resetPosition(agent.currentKnownState);
            agent.makeNewMove();
            return;
        }

        if (changeOfstate()) {

            double reward = graph.getEdge(agent.currentKnownState, getNewState()).getWeight();
            System.out.println(reward);

            agent.updateQ_table(reward, getNewState());

            if (isAgentInFireState() || isAgentInGoalState())
                agent.resetPosition(startState);

            agent.setCurrentState(getNewState());
            agent.makeNewMove();

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
