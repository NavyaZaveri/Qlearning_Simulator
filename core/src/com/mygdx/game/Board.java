package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Utils.FontUtils;

import static com.mygdx.game.Utils.GameConstants.*;


public class Board {
    private static int rows;
    private static int columns;
    private float tileHeight;
    private float tileWidth;
    private static Tile[][] board;
    private Tile startState;
    private BitmapFont font = FontUtils.getInstance().getFont(50, Color.BROWN);

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
        startState = board[0][0];
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

                font.draw(batch, tile.getId() + "", tile.getCentreX(), tile.getCentreY());

            }
        }

    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public Tile getStartState(){
        return startState;
    }

}


