package com.beemelonstudio.fourelements.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.beemelonstudio.fourelements.FourElements;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = FourElements.TITLE;
		config.width = FourElements.V_WIDTH;
		config.height = FourElements.V_HEIGHT;

		new LwjglApplication(new FourElements(), config);
	}
}
