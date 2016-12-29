package com.forsenboyz.rise42.coop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.log.Time;
import com.forsenboyz.rise42.coop.network.Connection;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new DesktopLauncher().doEngine();
		//new DesktopLauncher().doStuff();
	}

	private void doEngine(){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = App.WIDTH;
		config.height = App.HEIGHT;
		new LwjglApplication(new App(), config);
	}

}
