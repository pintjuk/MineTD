package kth.pintjukarlsson.minetd;

import java.util.logging.Level;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Matrix4;

public class MineTD 
	extends Game
{
	public OrthographicCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch() {
	  return level.getRenderer().getSpriteBatch();
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
	private InputMultiplexer InMultiplexer;
	private AssetManager assetManager;
	private MouseInputAdapter input;
	private EnemyManager enemiesManager = new EnemyManager(1, 0, this);
	private GameMap level;
	private int w, h;
	@Override
	public void create() {		
		InMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(InMultiplexer);
		assetManager = new AssetManager();
		loadAssets();
		
		setupCam();
		
		this.input = new MouseInputAdapter(this);
		
		
		
		level = new GameMap();
		level.loadAssets(assetManager);
		
		//level.repathtest();
		level.resetDebugDraw();
		
		input.init();
		InMultiplexer.addProcessor(this.input);
		
		
	}

	private void loadAssets() {
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("data/pony.png", Texture.class);
		assetManager.load("data/map.tmx", TiledMap.class);
		assetManager.finishLoading();
	}

	private void setupCam() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 2.5f;
		camera.update();
	}

	@Override
	public void dispose() {
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
		
		getBatch().begin();
		//batch.setTransformMatrix(camera.view);
		//batch.setProjectionMatrix(camera.projection);
		enemiesManager.Draw(getBatch());
		getBatch().end();
		
		//level.Draw(camera);
		level.DrawPathGraph();
		//level.DrawPathGraph();
		//batch.setProjectionMatrix(camera.combined);
	
		
	}

	private void updateGame() {
		camera.update();
		enemiesManager.Update();
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
