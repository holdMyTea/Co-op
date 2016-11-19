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
		new LwjglApplication(new App(), config);
	}

	/*private void doStuff() {
		//Connection connection = new Connection("192.168.0.30",1488);
		Connection connection = new Connection("localhost",1488);
		connection.connect();
		connection.sendMessage("Kappa");
		synchronized (connection){
			try {
				connection.wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		connection.sendMessage("Keepo");
	}*/

	private void doStuff() {

	}

}
