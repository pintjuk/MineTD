package kth.pintjukarlsson.minetd;

import java.util.logging.Level;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Game extends com.badlogic.gdx.Game {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private InputMultiplexer InMultiplexer;
	private AssetManager assetManager;
	private GameMap level;
	@Override
	public void create() {		
		InMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(InMultiplexer);
		assetManager = new AssetManager();
		
		setupCam();
		
		batch = new SpriteBatch();
		
		level = new GameMap();
		level.loadAssets(assetManager);
		level.setTile(1, 2, 7);
		level.setTile(1, 2, 7);
		level.setTile(1, 3, 7);
		level.setTile(1, 2, 6);
		level.removeTile(0, 2, 7);
		level.removeTile(0, 0, 0);
		level.removeTile(0, 0, 2);
		level.removeTile(0, 0, 1);
		level.repathtest();
		level.resetDebugDraw();
	}

	private void setupCam() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 1f;
		camera.update();
		InMultiplexer.addProcessor(new OrthoCamController(camera));
	}

	@Override
	public void dispose() {
		batch.dispose();
		assetManager.dispose();
		
	}
	
	/**
	 * yes it is obvious
	 */
	@Override
	public void render() {	
		//update game
		updateGame();
		//rander 
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		level.Draw(camera);
		level.DrawPathGraph();
		batch.setProjectionMatrix(camera.combined);
		
	}

	private void updateGame() {
		camera.update();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
