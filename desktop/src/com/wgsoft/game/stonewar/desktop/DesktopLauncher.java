package com.wgsoft.game.stonewar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wgsoft.game.stonewar.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.title = "Stone war";
		config.width = 360;
		config.height = 640;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
