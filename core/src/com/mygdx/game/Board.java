package com.mygdx.game;

import static com.mygdx.game.Utils.Constants.SCREEN_HEIGHT;
import static com.mygdx.game.Utils.Constants.SCREEN_WIDTH;

/**
 * Created by linux on 3/24/18.
 */

//TODO: IMPLEMENT ITERATOR!!!!

public class Board {
    public static int rows;
    public static int columns;
    private float tileHeight;
    private float tileWidth;
    private static Tile[][] board;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.tileWidth = SCREEN_WIDTH / columns;
        this.tileHeight = SCREEN_HEIGHT / rows;
        board = new Tile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Tile(tileHeight, tileWidth, i, j);
            }
        }
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

}


