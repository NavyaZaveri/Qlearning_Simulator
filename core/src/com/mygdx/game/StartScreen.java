package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen extends Game {
	SpriteBatch batch;
	Texture img;
	@Override
	public void create() {
		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		super.render();
		if (Gdx.input.isTouched()){
			super.setScreen(new GameScreen(batch));
		}
	}
}
