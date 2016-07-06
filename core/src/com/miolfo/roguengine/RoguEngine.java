package com.miolfo.roguengine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class RoguEngine extends Game {

	MainGame game;

	@Override
	public void create () {
		//game = new MainGame();
		setScreen(new MainGame());
	}

	@Override
	public void dispose(){
		game.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		//game.resize(width, height);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		//game.render();
	}
}
