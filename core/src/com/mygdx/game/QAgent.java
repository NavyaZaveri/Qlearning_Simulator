package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


import org.javatuples.Pair;

import static com.mygdx.game.Action.DOWN;
import static com.mygdx.game.Action.LEFT;
import static com.mygdx.game.Action.RIGHT;
import static com.mygdx.game.Action.UP;

/**
 * Created by linux on 3/24/18.
 */

public class QAgent {

    private List<Action> actionList;
    private static final float epsilon = 0.1f;
    private Texture img = new Texture("Robot.png");
    private Random random = new Random();
    private Action currentAction;
    public Tile currentKnownState;
    private static final float discountFactor = 0.05f;
    public static final float learningRate = 0.01f;
    private Map<Pair<Tile, Action>, Double> q_table = new HashMap<>();


    private float speed = 1000;
    public Action action;
    public final Rectangle pos;

    public QAgent(int height, int width) {
        pos = new Rectangle();
        actionList = Arrays.asList(Action.class.getEnumConstants());
        pos.height = height;
        pos.width = width;
    }

    private Double getBestValueAtState(Tile tile) {
        double max = -100;
        List<Double> values = new ArrayList<>();
        for (Map.Entry<Pair<Tile, Action>, Double> entry : q_table.entrySet()) {
            if (entry.getKey().getValue0().getId() == tile.getId()) {
                max = Math.max(max, entry.getValue());
            }
        }
        return max;
    }

    private Action getBestActionAtState(Tile tile) {
        Map<Action, Double> actionToValue = new HashMap<>();


        for (Map.Entry<Pair<Tile, Action>, Double> x : q_table.entrySet()) {
            if (x.getKey().getValue0().getId()
                    == tile.getId()) {
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
        return bestActions.get(random.nextInt(bestActions.size()));
    }


    public void updateQ_table(Integer reward, Tile newState) {

        for (Pair<Tile, Action> key : q_table.keySet()) {
            if (key.getValue0().getId() == currentKnownState.getId() && key.getValue1() == currentAction) {
                Gdx.app.log("location+action", key.getValue0().getId() + key.getValue1());
                Gdx.app.log("previous value:", q_table.get(key) + "");

                //bellman equation
                Double oldValue = q_table.get(key);
                double newValue = reward + discountFactor * getBestValueAtState(newState);
                System.out.println("get best value at state " + newState.getId() + getBestValueAtState(newState));
                q_table.put(key, (oldValue + newValue));


                Gdx.app.log("updated state/action:", q_table.get(key) + "");
                Gdx.app.log("INFO", "UPDATE DONE");
                Gdx.app.log("INFO", "###############################################");
                Gdx.app.log("INFO", "NEW STATE stuff begins");
                break;

            }

        }
    }

    private Action getRandomAction() {
        return actionList.get(random.nextInt(actionList.size()));
    }


    private Action getAction() {

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
        Gdx.app.log("INFO", "DOING" + action);
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
