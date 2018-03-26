package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
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
    public Tile currentState;
    private static final float discountFactor = 0.01f;
    private Map<Pair<Tile, Action>, Integer> q_table = new HashMap<>();


    private float speed = 200;
    public Action action;
    public Rectangle pos;

    public QAgent(int height, int width) {
        pos = new Rectangle();
        actionList = Arrays.asList(Action.class.getEnumConstants());
        pos.height = height;
        pos.width = width;
    }

    private Action chooseBestAction(Tile tile) {
        Action bestAction;
        int bestValue;


        return null;
    }


    public void updateq_table(int reward) {

        for (Pair<Tile, Action> key : q_table.keySet()) {
            if (key.getValue0().getId() == currentState.getId() && key.getValue1() == currentAction) {
                q_table.put(key, reward + q_table.get(key));
                System.out.println("updated");
                System.out.println(q_table.get(key));
                System.out.println(key.getValue0().getId()+key.getValue1());
                break;
            }

        }
    }


    private Action getAction() {
        return actionList.get(random.nextInt(actionList.size()));
    }

    public void makeNewMove() {
        action = getAction();
        currentAction = action;
        move();
    }

    public void setq_table(Tile tile) {
        for (Action action : actionList) {
            Pair<Tile, Action> tile_action = new Pair<>(tile, action);
            q_table.put(tile_action, 0);
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
        currentState = tile;

    }


}
