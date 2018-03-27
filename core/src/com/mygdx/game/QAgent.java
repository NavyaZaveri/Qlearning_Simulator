package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
    private static final float discountFactor = 0.01f;
    private Map<Pair<Tile, Action>, Integer> q_table = new HashMap<>();


    private float speed = 100;
    public Action action;
    public Rectangle pos;

    public QAgent(int height, int width) {
        pos = new Rectangle();
        actionList = Arrays.asList(Action.class.getEnumConstants());
        pos.height = height;
        pos.width = width;
    }

    private Integer getBestvalueAtstate(Tile tile) {
        List<Integer> values = new ArrayList<>();
        for (Map.Entry<Pair<Tile, Action>, Integer> entry : q_table.entrySet()) {
            if (entry.getKey().getValue0().getId() == tile.getId()) {
                values.add(entry.getValue());

            }
        }
        return Collections.max(values, new Comparator<Integer>() {
            @Override
            public int compare(Integer t1, Integer t2) {
                return t1 - t2;
            }
        });
    }

    private Action chooseBestAction(Tile tile) {
        Map<Action, Integer> actionToValue = new HashMap<>();


        for (Map.Entry<Pair<Tile, Action>, Integer> x : q_table.entrySet()) {
            if (x.getKey().getValue0().getId()
                    == tile.getId()) {
                actionToValue.put(x.getKey().getValue1(), x.getValue());
            }
        }

        Action action = Collections.max(actionToValue.entrySet(),
                (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();

        return action;
    }


    public void updateQ_table(int reward) {

        for (Pair<Tile, Action> key : q_table.keySet()) {
            if (key.getValue0().getId() == currentKnownState.getId() && key.getValue1() == currentAction) {
                Gdx.app.log("location+action", key + "");
                Gdx.app.log("previous value:", q_table.get(key) + "");
                q_table.put(key, reward + q_table.get(key));
                Gdx.app.log("new value:", q_table.get(key) + "");
                System.out.println(key.getValue0().getId() + key.getValue1());
                Gdx.app.log("INFO", "UPDATE DONE");
                break;
            }

        }
    }

    private Action getRandomAction() {
        return actionList.get(random.nextInt(actionList.size()));
    }


    private Action getAction() {
        return actionList.get(random.nextInt(actionList.size()));
    }


    //the robot has detected a change in state. It needs to make a move now.
    public void makeNewMove() {
        action = getAction();
        currentAction = action;
        System.out.println("the best action is" + chooseBestAction(currentKnownState));
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
        currentKnownState = tile;

    }

    public void resetLocation(){
    }

    public void resetPosition(Tile t){
        pos.x = t.getCentreX();
        pos.y = t.getCentreY();
    }

    public void keepMoving(){
        move();
    }


}
