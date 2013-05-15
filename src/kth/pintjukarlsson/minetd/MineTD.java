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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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

	public PlayerStats getPlayerStats() {
		return playerStats;
	}

	private OrthographicCamera camera;
	private InputMultiplexer InMultiplexer;
	private AssetManager assetManager;
	private MouseInputAdapter input;
	private EnemyManager enemiesManager;
	private GameMap level;
	private BuildingManager buildingManager;
	private UIManager uiManager;
	private PlayerStats playerStats;
	private SpriteBatch guiBatch;
	private int w, h;
	@Override
	public void create() {
		input = new MouseInputAdapter(this);
		InMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(InMultiplexer);
		enemiesManager = new EnemyManager(1, 0, this);
		buildingManager = new BuildingManager(this);
		uiManager = new UIManager(this);
		assetManager = new AssetManager();
		playerStats = new PlayerStats(this);
		guiBatch = new SpriteBatch();
		loadAssets();
		
		setupCam();
		
		level = new GameMap();
		level.loadAssets(assetManager);

		level.resetDebugDraw();
		
		input.init();
		InMultiplexer.addProcessor(this.input);
		
		enemiesManager.init();
		buildingManager.init();
		uiManager.init();
		camera.translate(new Vector2((float) level.getFinish().getX()-camera.position.x, (float)  level.getFinish().getY()-camera.position.y));
		
		MsgPrinter.print("Welcome to MineTD!", 2f);
		MsgPrinter.print("Good luck!", 5f);
		MsgPrinter.print("", 5f);
		MsgPrinter.print("Build turrets by costracting squere units of 2x2 elements to defend the cacke!", 30f);
		MsgPrinter.print("Overlaping squer units will be part and bost the same turret.", 30f);
		MsgPrinter.print("The upper left element of every squer will determen what stat will boosted", 30f);
		MsgPrinter.print("and the type of every other element in a squer will determin the power of the boost", 30f);
		MsgPrinter.print("", 30f);
		MsgPrinter.print("DIRT: makes minial tower with no busts. GRAVLE: boostes bullet speed. ", 30f);
		MsgPrinter.print(" STONE: boostes fire rate. IRON: boosts Damag. GOLD: bosts Range", 30f);
		 
	}

	private void loadAssets() {
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("data/pony.png", Texture.class);
		assetManager.load("data/gun.png", Texture.class);
		assetManager.load("data/exp.png", Texture.class);
		assetManager.load("data/map.tmx", TiledMap.class);
		assetManager.load("data/map1.tmx", TiledMap.class);
		assetManager.load("data/map2.tmx", TiledMap.class);
		assetManager.finishLoading();
	}

	public EnemyManager getEnemiesManager() {
		return enemiesManager;
	}

	

	private void setupCam() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		//float h = Gdx.graphics.getHeight() - uiManager.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 1.5f;
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
		//level.DrawPathGraph();
		getBatch().begin();
		enemiesManager.Draw(getBatch());
		buildingManager.Draw(getBatch());
		getBatch().end();
		uiManager.Draw();
		//guiBatch.begin();
	//	buildingManager.Draw(guiBatch);
		//guiBatch.end();
		
	}

	private void updateGame() {
		camera.update();
		enemiesManager.Update();
		buildingManager.Update();
		uiManager.Update();
		if(playerStats.getLives() <=0)
			throw new YouAreDeadException();
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
