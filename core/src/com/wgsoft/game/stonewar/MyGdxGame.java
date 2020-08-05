package com.wgsoft.game.stonewar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.wgsoft.game.stonewar.screens.LoadingScreen;
import com.wgsoft.game.stonewar.screens.MainMenuScreen;

import java.util.Locale;

import static com.wgsoft.game.stonewar.Const.*;

public class MyGdxGame extends Game {
	public AssetManager assetManager;
	public boolean loaded;

	public I18NBundle bundle;

	public SpriteBatch batch;

	public Skin skin;

	public LoadingScreen loadingScreen;
	public MainMenuScreen mainMenuScreen;

	public MyGdxGame(){
		game = this;
	}
	
	@Override
	public void create () {
		assetManager = new AssetManager();

		batch = new SpriteBatch();

		loadingScreen = new LoadingScreen();

		localize();

		setScreen(loadingScreen);
	}

	public void localize(){
		localize(null, null, null);
		loadingScreen.localize();
		if(loaded) {
			mainMenuScreen.localize();
		}
	}

	private void localize(String s1, String s2, String s3){
		FileHandle fileHandle = Gdx.files.internal("bundle/bundle");
		if(s1 == null){
			bundle = I18NBundle.createBundle(fileHandle);
		}else{
			Locale locale;
			if(s2 == null){
				locale = new Locale(s1);
			}else if(s3 == null){
				locale = new Locale(s1, s2);
			}else{
				locale = new Locale(s1, s2, s3);
			}
			bundle = I18NBundle.createBundle(fileHandle, locale);
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	//Do NOT dispose loadingScreen, it is disposed automatically
	@Override
	public void dispose () {
		super.dispose();

		assetManager.dispose();

		batch.dispose();

		if(mainMenuScreen != null) {
			mainMenuScreen.dispose();
		}
	}
}
