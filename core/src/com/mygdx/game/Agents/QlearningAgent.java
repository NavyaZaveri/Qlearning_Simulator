package com.mygdx.game.Agents;

import com.mygdx.game.Enums.Action;
import com.mygdx.game.Tile;

import org.javatuples.Pair;

/**
 * Created by linux on 3/29/18.
 */

public class QlearningAgent extends Agent {

    public QlearningAgent(float height, float width) {
        super(height, width);
    }

    @Override
    public void updateQ_table(double reward, Tile newState) {
        for (Pair<Tile, Action> key : q_table.keySet()) {
            if (key.getValue0().getId().equals(currentKnownState.getId()) && key.getValue1() == currentAction) {


                //bellman equation
                Double oldValue = q_table.get(key);
                double newValue = reward + discountFactor * getBestValueAtState(newState);

                q_table.put(key, (oldValue + learningRate * (newValue - oldValue)));
                break;

            }

        }
    }
}

