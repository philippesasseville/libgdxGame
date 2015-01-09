package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = MyGdxGame.TITLE;
        cfg.width = MyGdxGame.V_WIDTH * MyGdxGame.SCALE;
        cfg.height = MyGdxGame.V_HEIGHT * MyGdxGame.SCALE;
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
