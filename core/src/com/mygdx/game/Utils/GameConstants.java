package com.mygdx.game.Utils;


public class GameConstants {
    public static final float SCREEN_HEIGHT = 1200;
    public static final float SCREEN_WIDTH = 1200;


    //decided by the user
    public static int ROWS;
    public static int COLUMNS;


    public static final float POSITIVE_REWARD = 10;
    public static final float NEGATIVE_REWARD = -10;
    public static final float NEUTRAL_REWARD = 0;

    public static void setROWS(int rows) {
        ROWS = rows;
    }

    public static void setCOLUMNS(int columns) {
        COLUMNS = columns;
    }

}
