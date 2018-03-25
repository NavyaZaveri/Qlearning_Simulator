package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.mygdx.game.Action.DOWN;
import static com.mygdx.game.Action.LEFT;
import static com.mygdx.game.Action.RIGHT;
import static com.mygdx.game.Action.UP;

/**
 * Created by linux on 3/24/18.
 */

public class QAgent implements Agent {

    private Map<Integer, List<Integer>> stateToAction = new HashMap<Integer, List<Integer>>();
    private List<Action> actionList;
    private float epsilon;
    private Texture img = new Texture("android/assets/robot.png");

    private float speed = 100;
    public Action currentAction;
    public Rectangle pos;

    public QAgent(int height, int width) {
        pos = new Rectangle();
        actionList = Arrays.asList(Action.class.getEnumConstants());
        pos.height = height;
        pos.width = width;
    }

    private Action getAction() {
        return null;
    }

    @Override
    public void move(Action action) {

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
}
