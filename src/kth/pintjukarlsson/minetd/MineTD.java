package kth.pintjukarlsson.minetd;

import java.util.logging.Level;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.math.Matrix4;

public class MineTD 
	extends Game
{
	public OrthographicCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public InputMultiplexer getInMultiplexer() {
		return InMultiplexer;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public MouseInputAdapter getInput() {
		return input;
	}

	public GameMap getLevel() {
		return level;
	}

	

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private InputMultiplexer InMultiplexer;
	private AssetManager assetManager;
	private MouseInputAdapter input;
	private GameMap level;
	private int w, h;
	@Override
	public void create() {		
		InMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(InMultiplexer);
		assetManager = new AssetManager();
		
		setupCam();
		
		this.input = new MouseInputAdapter(this);
		
		
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
		
		input.init();
		InMultiplexer.addProcessor(this.input);
	}

	private void setupCam() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 2f;
		camera.update();
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
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//level.Draw(camera);
		System.out.println(camera.view);
		System.out.println(camera.projection);
		camera.apply(Gdx.gl10);
		
		level.DrawPathGraph();
		//batch.setProjectionMatrix(camera.combined);
		input.testDraw(w, h);
		
	}

	private void updateGame() {
		camera.update();
		
	}

	@Override
	public void resize(int width, int height) {
		w=width;h=height;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	
}
