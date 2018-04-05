package com.mygdx.game.Agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Enums.Action;
import com.mygdx.game.Tile;
import com.mygdx.game.Utils.TextureUtils;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static com.mygdx.game.Enums.Action.DOWN;
import static com.mygdx.game.Enums.Action.LEFT;
import static com.mygdx.game.Enums.Action.RIGHT;
import static com.mygdx.game.Enums.Action.UP;


public abstract class Agent {

    protected List<Action> actionList;
    private static final float epsilon = 0.1f;
    protected Texture img;
    private Random random;
    protected Action currentAction;
    public Tile currentKnownState;
    protected static final float discountFactor = 0.05f;
    protected static final float learningRate = 0.01f;
    protected Map<Pair<Tile, Action>, Double> q_table;


    public static float current_speed = 500;
    public static final float max_speed = 700;
    public static final float min_spped = 100;
    private Action action;
    public final Rectangle pos;

    public Agent(float height, float width) {
        pos = new Rectangle();
        actionList = Arrays.asList(Action.class.getEnumConstants());
        pos.height = height;
        pos.width = width;
        q_table = new HashMap<>();
        random = new Random();
        img = TextureUtils.getInstance().getRobotImage();

    }

    /*@param Tile:
      @returns Double: the value assoicated with best action possible at a given state
     */
    public Double getBestValueAtState(Tile tile) {

        return Collections.max(q_table.entrySet().stream().
                filter(x -> x.getKey().getValue0().getId().equals(tile.getId())).
                map(x -> x.getValue()).collect(Collectors.toList()));
    }


    /* @param Tile
       @returns Action: the best possible action on a given Tile.

       Iterates through all possibles (tile,action) tuples and
       finds the best one. Selects a random action
       if there are many best actions.
     */
    public Action getBestActionAtState(Tile tile) {
        Map<Action, Double> actionToValue = q_table.entrySet().stream().
                filter(x -> x.getKey().getValue0().getId().equals(tile.getId())).
                collect(Collectors.toMap(x -> x.getKey().getValue1(),
                                         x -> x.getValue()));


        double bestValue = Collections.max(actionToValue.values());

        List<Action> bestActions = actionToValue.entrySet().stream().
                filter(x -> x.getValue() == bestValue).
                map(x -> x.getKey()).collect(
                Collectors.toList());


        //if there are multiple best actions, return a random action
        return bestActions.get(random.nextInt(bestActions.size()));
    }

    public abstract void updateQ_table(double reward, Tile newState);

    protected Action getRandomAction() {
        return actionList.get(random.nextInt(actionList.size()));
    }


    /*epsilon-greedy strategy
     @returns Action*/
    protected Action getAction() {

        if (epsilon > Math.random()) {
            return actionList.get(random.nextInt(actionList.size()));
        } else {
            Action bestAction = getBestActionAtState(currentKnownState);
            return bestAction;
        }
    }


    public void makeNewMove() {
        action = getAction();
        currentAction = action;
        move();
    }

    //initializes the q_table to 0's
    public void setQ_table(Tile tile) {
        for (Action action : actionList) {
            Pair<Tile, Action> tile_action = new Pair<>(tile, action);
            q_table.put(tile_action, 0.0);
        }
    }

    //the user can force a move
    public void forceMove(Action action) {
        currentAction = action;
        move();
    }

    protected void move() {
        if (currentAction == UP) {
            pos.y += current_speed * Gdx.graphics.getDeltaTime();
        }
        if (currentAction == DOWN) {
            pos.y -= current_speed * Gdx.graphics.getDeltaTime();
        }

        if (currentAction == RIGHT) {
            pos.x += current_speed * Gdx.graphics.getDeltaTime();
        }

        if (currentAction == LEFT) {
            pos.x -= current_speed * Gdx.graphics.getDeltaTime();
        }

    }

    public void increaseSpeed(){
        current_speed =  Math.min(current_speed+20,max_speed);
    }

    public void decreaseSpeed(){
        current_speed = Math.max(current_speed-20,min_spped);
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


    public void resetPosition(float x, float y) {
        pos.x = x - pos.width / 2;
        pos.y = y - pos.height / 2;
    }

    public void keepMoving() {
        move();
    }

}
