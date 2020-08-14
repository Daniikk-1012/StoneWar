package com.wgsoft.game.stonewar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.wgsoft.game.stonewar.screens.LoadingScreen;
import com.wgsoft.game.stonewar.screens.MainMenuScreen;
import com.wgsoft.game.stonewar.screens.MatchSettingsScreen;
import com.wgsoft.game.stonewar.screens.SettingsScreen;

import java.util.Locale;

import static com.wgsoft.game.stonewar.Const.*;

public class MyGdxGame extends Game implements Localizable {
	public AssetManager assetManager;
	public boolean loaded;

	public Preferences prefs;

	public I18NBundle bundle;

	public SpriteBatch batch;

	public Skin skin;

	public Stage bubbleBackgroundStage;

	public TransitionScreen transitionScreen;
	public LoadingScreen loadingScreen;

	public MainMenuScreen mainMenuScreen;
	public SettingsScreen settingsScreen;
	public MatchSettingsScreen matchSettingsScreen;

	public MyGdxGame(){
		game = this;
	}
	
	@Override
	public void create () {
		assetManager = new AssetManager();

		batch = new SpriteBatch();

		transitionScreen = new TransitionScreen();

		new LoadingScreen();

		prefs = Gdx.app.getPreferences("com.wgsoft.game.stonewar");
		init();

		setScreen(loadingScreen);
	}

	public void init(){
		if(prefs.getBoolean("firstRun", true)){
			prefs.putBoolean("firstRun", false);
			initBundle(null, null, null);
			localize();
			if(bundle.getLocale().getCountry().equals("")) {
				prefs.putString("settings.language", bundle.getLocale().getLanguage());
			}else if(bundle.getLocale().getVariant().equals("")){
				prefs.putString("settings.language", bundle.getLocale().getLanguage()+"_"+bundle.getLocale().getCountry());
			}else{
				prefs.putString("settings.language", bundle.getLocale().getLanguage()+"_"+bundle.getLocale().getCountry()+"_"+bundle.getLocale().getVariant());
			}
			prefs.putFloat("settings.music", DEFAULT_SETTINGS_MUSIC_VOLUME);
			prefs.putFloat("settings.sound", DEFAULT_SETTINGS_SOUND_VOLUME);
			prefs.flush();
		}else{
			String[] localeStrings = prefs.getString("settings.language").split("_");
			if(localeStrings.length == 1){
				initBundle(localeStrings[0], null, null);
			}else if(localeStrings.length == 2){
				initBundle(localeStrings[0], localeStrings[1], null);
			}else{
				initBundle(localeStrings[0], localeStrings[1], localeStrings[2]);
			}
			localize();
			//TODO Music and sound volume init
		}
	}

	public void localize(){
		if(loaded) {
			mainMenuScreen.localize();
			settingsScreen.localize();
			matchSettingsScreen.localize();
		}else{
			loadingScreen.localize();
		}
	}

	public void initBundle(String s1, String s2, String s3){
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

	//Do NOT dispose loadingScreen
	@Override
	public void dispose () {
		super.dispose();

		assetManager.dispose();

		batch.dispose();

		transitionScreen.dispose();

		if(loaded) {
			mainMenuScreen.dispose();
			settingsScreen.dispose();
			matchSettingsScreen.dispose();
		}
	}
}
