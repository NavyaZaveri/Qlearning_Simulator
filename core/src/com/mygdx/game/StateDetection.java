package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;


public class StateDetection extends ApplicationAdapter {
    // we will use 32px/unit in world
    public final static float VP_WIDTH = 1200;
    public final static float VP_HEIGHT = 1200;

    private OrthographicCamera camera;
    private Vector3 v;
    Board board;
    SpriteBatch batch;
    BitmapFont font;

    Vector3 tp = new Vector3();
    boolean dragging;

    private Tile detectTile(float x, float y) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getTile(i, j).getRectangle().contains(x, y)) {
                    System.out.println(board.getTile(i, j).getId());
                    return board.getTile(i, j);
                }

            }
        }
        return null;
    }


    @Override
    public void create() {
        camera = new OrthographicCamera(1200, 1200);
        camera.setToOrtho(false, 1200, 1200);
        board = new Board(5, 5);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        font = new BitmapFont();
        setFont();
        font.setColor(Color.BLUE);
        Vector3 tp = new Vector3();
        boolean dragging;
    }

    private void toggle(Tile tile) {

        if (!tile.isGoal() &&!tile.isFire()){
            tile.makeFire();
        }
       else if (tile.isFire()) {
            tile.makeGoal();
        }
        else if (tile.isGoal()){
            tile.makeNeutral();
        }

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) System.out.println("pressed");

        camera.update();
        batch.begin();
        displayBoard();
        batch.end();
        if (Gdx.input.justTouched()) {
            tp.x = Gdx.input.getX();
            tp.y = Gdx.input.getY();
            camera.unproject(tp);
            Tile t = detectTile(tp.x, tp.y);
            System.out.println("x cord is" + tp.x);
            toggle(t);
            batch.begin();
            font.draw(batch, "wooooo", tp.x, tp.y);
            batch.end();
        }
    }

    private void setFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-ExtraBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);// font size 12 pixels
        generator.dispose();
    }

    private void displayBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = board.getTile(i, j);

                batch.draw(tile.getNeutralTileImage(), i * tile.getWidth(), j * tile.getHeight(),
                        tile.getWidth(), tile.getHeight());

                if (tile.isFire())
                    batch.draw(tile.getFireImage(), i * tile.getWidth(), j * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());
                if (tile.isGoal())
                    batch.draw(tile.getGoalImage(), i * tile.getWidth(), j * tile.getHeight(),
                            tile.getWidth(), tile.getHeight());

                font.draw(batch, tile.getCentreX() + "", tile.getCentreX(), tile.getCentreY());


            }
        }
    }


}