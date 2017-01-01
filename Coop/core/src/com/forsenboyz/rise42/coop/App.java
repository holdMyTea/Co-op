package com.forsenboyz.rise42.coop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
		// Gdx.gl.glClearColor(1, 0, 0, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();
		stateManager.update(Gdx.graphics.getRawDeltaTime());
        stateManager.render(sb, Gdx.graphics.getRawDeltaTime());
		sb.end();
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}
}
