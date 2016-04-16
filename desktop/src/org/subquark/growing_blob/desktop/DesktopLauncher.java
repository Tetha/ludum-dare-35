package org.subquark.growing_blob.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.subquark.growing_blob.GrowingBlobGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Growing blob";
        config.width = 800;
        config.height = 480;

		new LwjglApplication(new GrowingBlobGame(), config);
	}
}
