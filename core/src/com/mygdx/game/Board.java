package com.mygdx.game;

/**
 * Created by linux on 3/24/18.
 */

public class Board {
    public static int rows;
    public static int columns;
    private int tileHeight;
    private int tileWidth;
    private static Tile[][] board;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.tileWidth = GameScreen.SCREEN_WIDTH / rows;
        this.tileHeight = GameScreen.SCREEN_HEIGHT / columns;
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


