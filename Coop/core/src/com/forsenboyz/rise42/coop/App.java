package com.forsenboyz.rise42.coop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.forsenboyz.rise42.coop.states.StateManager;

public class App extends ApplicationAdapter {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

	public static final String HOST = "localhost";
	public static final int PORT = 1488;

	private SpriteBatch sb;
	private StateManager stateManager;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		stateManager = new StateManager();
	}

	@Override
	public void render () {
		sb.begin();
		stateManager.update(Gdx.graphics.getRawDeltaTime());
        stateManager.render(sb, Gdx.graphics.getRawDeltaTime());
		sb.end();
		//System.out.println(Gdx.graphics.getFramesPerSecond());
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}
}
