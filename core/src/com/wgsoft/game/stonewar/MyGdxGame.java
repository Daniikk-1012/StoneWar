package com.wgsoft.game.stonewar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	public static MyGdxGame game;

	public SpriteBatch batch;

	public MyGdxGame(){
		game = this;
	}
	
	@Override
	public void create () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();

		batch.dispose();
	}
}
