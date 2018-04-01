package com.mygdx.game.Agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Utils.Action;
import com.mygdx.game.Tile;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.mygdx.game.Utils.Action.DOWN;
import static com.mygdx.game.Utils.Action.LEFT;
import static com.mygdx.game.Utils.Action.RIGHT;
import static com.mygdx.game.Utils.Action.UP;

/**
 * Created by linux on 3/24/18.
 */

public abstract class Agent {

    protected List<Action> actionList;
    private static final float epsilon = 0.1f;
    private Texture img = new Texture("Robot.png");
    private Random random = new Random();
    protected Action currentAction;
    public Tile currentKnownState;
    protected static final float discountFactor = 0.05f;
    protected static final float learningRate = 0.01f;
    protected Map<Pair<Tile, Action>, Double> q_table = new HashMap<>();


    private static final float speed = 1000;
    public Action action;
    public final Rectangle pos;

    public Agent(float height, float width) {
        pos = new Rectangle();
        actionList = Arrays.asList(Action.class.getEnumConstants());
        pos.height = height;
        pos.width = width;
    }

    public Double getBestValueAtState(Tile tile) {
        double max = -100;


        for (Map.Entry<Pair<Tile, Action>, Double> entry : q_table.entrySet()) {
            if (entry.getKey().getValue0().getId().equals(tile.getId())) {
                max = Math.max(max, entry.getValue());
            }
        }
        return max;
    }

    public Action getBestActionAtState(Tile tile) {
        Map<Action, Double> actionToValue = new HashMap<>();

        for (Map.Entry<Pair<Tile, Action>, Double> x : q_table.entrySet()) {

            if (x.getKey().getValue0().getId()
                    .equals(tile.getId())) {
                actionToValue.put(x.getKey().getValue1(), x.getValue());
            }
        }
        double value = Collections.max(actionToValue.values());

        List<Action> bestActions = new ArrayList<>();
        for (Map.Entry<Action, Double> pair : actionToValue.entrySet()) {
            if (pair.getValue() == value) {
                bestActions.add(pair.getKey());
            }
        }

        //if there are multiple best actions, return a random one
        return bestActions.get(random.nextInt(bestActions.size()));
    }

    public abstract void updateQ_table(double reward, Tile newState);

    protected Action getRandomAction() {
        return actionList.get(random.nextInt(actionList.size()));
    }


    protected Action getAction() {

        if (epsilon > Math.random()) {
            System.out.println("making random move");
            return actionList.get(random.nextInt(actionList.size()));
        } else {
            Action bestAction = getBestActionAtState(currentKnownState);
            return bestAction;
        }
    }


    //the robot has detected a change in state. It needs to make a move now.
    public void makeNewMove() {
        action = getAction();
        Gdx.app.log("INFO", "GOING " + action);
        currentAction = action;
        move();
    }

    public void setQ_table(Tile tile) {
        for (Action action : actionList) {
            Pair<Tile, Action> tile_action = new Pair<>(tile, action);
            q_table.put(tile_action, 0.0);
        }
    }

    public void forceMove(Action action) {
        currentAction = action;
        move();
    }

    public void move() {
        if (currentAction == UP) {
            pos.y += speed * Gdx.graphics.getDeltaTime();
        }
        if (currentAction == DOWN) {
            pos.y -= speed * Gdx.graphics.getDeltaTime();
        }

        if (currentAction == RIGHT) {
            pos.x += speed * Gdx.graphics.getDeltaTime();
        }

        if (currentAction == LEFT) {
            pos.x -= speed * Gdx.graphics.getDeltaTime();
        }

    }


    public Texture getImage() {
        return img;
    }

    public float getWidth() {
        return pos.width;

    }

    public float getHeight() {
        return pos.height;

    }

    public float getX() {
        return pos.x;

    }

    public float getY() {
        return pos.y;

    }

    public void setCurrentState(Tile tile) {
        currentKnownState = tile;
    }

    public void resetLocation() {
    }

    public void resetPosition(Tile t) {
        pos.x = t.getCentreX() - pos.width / 2;
        pos.y = t.getCentreY() - pos.height / 2;
    }

    public void keepMoving() {
        move();
    }

}
