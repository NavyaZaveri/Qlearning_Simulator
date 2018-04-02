package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.Utils.Constants.COLUMNS;
import static com.mygdx.game.Utils.Constants.ROWS;
import static com.mygdx.game.Utils.Constants.SCREEN_HEIGHT;
import static com.mygdx.game.Utils.Constants.SCREEN_WIDTH;


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

    public void display(SpriteBatch batch) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Tile tile = getTile(i, j);

                batch.draw(tile.getNeutralTileImage(), j * tile.getWidth(), i * tile.getHeight(),
                        tile.getWidth(), tile.getHeight());

                if (tile.isFire())
                    batch.draw(tile.getFireImage(), j * tile.getWidth(), i * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());
                if (tile.isGoal())
                    batch.draw(tile.getGoalImage(), j * tile.getWidth(), i * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());

                //font.draw(batch, tile.getId(), tile.getCentreX(), tile.getCentreY());
               // Double value = agent.getBestValueAtState(tile);
               // font.draw(batch, value + "", tile.getCentreX(), tile.getCentreY());

            }
        }

    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

}


