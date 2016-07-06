package com.miolfo.roguengine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.miolfo.roguengine.RoguEngine;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1196;
		config.resizable = false;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		new LwjglApplication(new RoguEngine(), config);
	}
}
