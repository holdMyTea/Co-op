package com.forsenboyz.rise42.coop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.forsenboyz.rise42.coop.App;

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

	private void doStuff(){
		TexturePacker.process("/home/rise42/Projects/Co-op/assets_base/characters",
				"/home/rise42/Projects/Co-op/Coop/core/assets/","characters");
	}

}
