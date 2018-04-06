package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Agents.*;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mygdx.game.Enums.Action.DOWN;
import static com.mygdx.game.Enums.Action.LEFT;
import static com.mygdx.game.Enums.Action.RIGHT;
import static com.mygdx.game.Enums.Action.UP;
import static com.mygdx.game.Utils.GameConstants.*;


public final class GameScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    /*hardcoded offset that shrinks the
    agent size to avoid tile detection anomalies*/
    private final int agentPosOffset = 50;

    private Agent agent;
    private Board board;
    private SimpleDirectedWeightedGraph<Tile, Reward> graph;
    private Tile startState;
    private List<Tile> fireStates = new ArrayList<>();
    private List<Tile> goalStates = new ArrayList<>();

    public GameScreen(SpriteBatch batch, Board board, Set<Tile> goalStates, Set<Tile> fireStates) {
        
        agent = new QlearningAgent(SCREEN_HEIGHT / ROWS - agentPosOffset,
                                  SCREEN_WIDTH / COLUMNS - agentPosOffset);
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        this.board = board;
        this.goalStates.addAll(goalStates);
        this.fireStates.addAll(fireStates);
        graph = new SimpleDirectedWeightedGraph<>(Reward.class);

        initStateActionPairs();
        populateGraph();
        startState = board.getStartState();

        agent.setCurrentState(startState);
        agent.resetPosition(startState.getCentreX(), startState.getCentreY());
        agent.makeNewMove();
    }


    private void initStateActionPairs() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Tile tile = board.getTile(i, j);
                agent.setQ_table(tile);
            }
        }
    }

    private Boolean agentOutOfBounds() {
        if (agent.getX() > SCREEN_WIDTH ||
                agent.getX() < 0 - agent.getWidth() ||
                agent.getY() > SCREEN_HEIGHT ||
                agent.getY() < 0 - agent.getHeight()
                ) {
            Gdx.app.log("INFO", "OUT OF BOUNDS!");
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
        return getNewState().isFire();
    }


    private Boolean isAgentInGoalState() {
        return getNewState().isGoal();
    }

    //sets up the reward ("edges") and states ("vertices")
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


    //sets negative values to edges directed at fire states
    private void setFireEdges() {

        for (Tile tile : fireStates) {

            Set<Reward> rewards = graph.incomingEdgesOf(tile);
            for (Reward r : rewards) {
                System.out.println("fire " + tile.getId());
                graph.setEdgeWeight(r, NEGATIVE_REWARD);
            }
        }

    }

    //sets positives rewards to edges directed at goal states
    private void setGoalEdges() {

        for (Tile tile : goalStates) {

            Set<Reward> rewards = graph.incomingEdgesOf(tile);
            for (Reward r : rewards) {
                graph.setEdgeWeight(r, POSITIVE_REWARD);
                System.out.println("goal" + tile.getId());
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
                   agent.getY(), agent.getWidth(),
                   agent.getHeight());
    }

    @Override
    public void show() {

    }


    private Boolean changeOfstate() {
        return (detectOverlap()
                && agent.currentKnownState != getNewState()
                && getNewState() != null);

    }

    //plays out a move made by the user using keyboard
    private void makeForcedMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) agent.forceMove(UP);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) agent.forceMove(LEFT);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) agent.forceMove(RIGHT);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) agent.forceMove(DOWN);
    }

    private Boolean detectUserIncreasedSpeed() {
        return Gdx.input.isKeyPressed(Input.Keys.A);
    }

    private Boolean detectUserDecreasedSpeed() {
        return Gdx.input.isKeyPressed(Input.Keys.S);
    }


    private void displayGame() {
        batch.begin();
        board.display(batch);
        displayRobot();
        batch.end();
    }

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        displayGame();

        if (detectUserIncreasedSpeed()) agent.increaseSpeed();
        if (detectUserDecreasedSpeed()) agent.decreaseSpeed();

        //edge case: goes back to previous position and makes a new move if the agent moves out of screen
        if (agentOutOfBounds()) {
            agent.resetPosition(agent.currentKnownState.getCentreX(),
                    agent.currentKnownState.getCentreY());

            agent.makeNewMove();
            return;
        }

        /*
        If there is a change of state from q1 to q2, do the following:

        1) Update the qtable for the entry (q1,action).
        2) Set the player's current state to q2.
        3) Make a new move.

        Otherwise, keep moving.
        */

        if (changeOfstate()) {
            double reward = graph.getEdge(agent.currentKnownState, getNewState()).getWeight();
            System.out.println(reward);

            agent.updateQ_table(reward, getNewState());

            if (isAgentInFireState() || isAgentInGoalState())
                agent.resetPosition(startState.getCentreX(), startState.getCentreY());

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
