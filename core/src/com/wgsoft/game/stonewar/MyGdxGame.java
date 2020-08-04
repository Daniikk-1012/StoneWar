package com.wgsoft.game.stonewar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.wgsoft.game.stonewar.screens.MainMenuScreen;

import java.util.Locale;

public class MyGdxGame extends Game {
	public static MyGdxGame game;

	public I18NBundle bundle;

	public SpriteBatch batch;

	public Skin skin;

	public MainMenuScreen mainMenuScreen;

	public MyGdxGame(){
		game = this;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		skin = new Skin(Gdx.files.internal("img/skin.json"));
		ObjectMap.Entries<String, BitmapFont> entries = new ObjectMap.Entries<>(skin.getAll(BitmapFont.class));
		for (ObjectMap.Entry<String, BitmapFont> entry : entries){
			entry.value.setUseIntegerPositions(false);
			Array.ArrayIterator<TextureRegion> iterator = new Array.ArrayIterator<>(entry.value.getRegions());
			for(TextureRegion region : iterator){
				region.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			}
		}

		mainMenuScreen = new MainMenuScreen();

		localize(null, null, null);

		setScreen(mainMenuScreen);
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
		mainMenuScreen.localize();
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

		skin.dispose();

		mainMenuScreen.dispose();
	}
}
