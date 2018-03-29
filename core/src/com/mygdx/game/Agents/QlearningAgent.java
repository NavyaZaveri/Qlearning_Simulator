package com.mygdx.game.Agents;

import com.mygdx.game.Action;
import com.mygdx.game.Tile;

import org.javatuples.Pair;

/**
 * Created by linux on 3/29/18.
 */

public class QlearningAgent extends Agent {

    public QlearningAgent(int height, int width) {
        super(height, width);
    }

    @Override
    public void updateQ_table(double reward, Tile newState) {
        for (Pair<Tile, Action> key : q_table.keySet()) {
            if (key.getValue0().getId() == currentKnownState.getId() && key.getValue1() == currentAction) {
                /*Gdx.app.log("location+action", key.getValue0().getId() + key.getValue1());
                Gdx.app.log("previous value:", q_table.get(key) + "");
                */

                //bellman equation
                Double oldValue = q_table.get(key);
                double newValue = reward + discountFactor * getBestValueAtState(newState);
                System.out.println("get best value at state " + newState.getId() + getBestValueAtState(newState));
                q_table.put(key, (oldValue + learningRate * (newValue - oldValue)));



                /*GameScreen.batch.begin();
                GameScreen.font.draw(GameScreen.batch, oldValue + newValue + "", key.getValue0().getCentreX(), key.getValue0().getCentreY());
                GameScreen.batch.end();
                Gdx.app.log("updated state/action:", q_table.get(key) + "");
                Gdx.app.log("INFO", "UPDATE DONE");
                Gdx.app.log("INFO", "###############################################");
                Gdx.app.log("INFO", "NEW STATE stuff begins");*/
                break;
            }

        }
    }
}

