package kth.pintjukarlsson.minetd;

import java.util.logging.Level;

import kth.pintjukarlsson.minetd.interfaces.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
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

/**
 * Called by the Main class for a given platform.
 * Handles everything that goes on within the game.
 * 
 * @author Daniil Pintjuk
 * @author Markus Karlsson
 */
public class MineTD 
	extends Game
	implements GameService 
{
	private OrthographicCamera camera;
	private InputMultiplexer inputMultiplexer;
	private AssetManager assetManager;
	private MouseInputAdapter input;
	private EnemyManagerService enemyManager;
	private GameMapService level;
	private BuildingManager buildingManager;
	private UIManager uiManager;
	private PlayerStatsService playerStats;
	private Music bgMusic;
	private float w, h;
	
	/**
	 * Creates a new game application.
	 */
	@Override
	public void create() {
		// Create field objects.
		input = new MouseInputAdapter(this);
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
		enemyManager = new EnemyManager(1, 0, this);
		buildingManager = new BuildingManager(this);
		uiManager = new UIManager(this);
		assetManager = new AssetManager();
		playerStats = new PlayerStats();
		
		loadAssets();
		setupCam();
		
		// Initialize map and interface.
		level = new GameMap();
		level.loadAssets(assetManager);
		//level.resetDebugDraw();
		camera.translate(new Vector2((float) level.getFinish().getX()-camera.position.x, (float)  level.getFinish().getY()-camera.position.y));
		
		// Initialize input processing.
		input.init();
		inputMultiplexer.addProcessor(this.input);
		
		// Initialize managers.
		enemyManager.init();
		buildingManager.init();
		uiManager.init();
		
		// Set background music.
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("data/bgmusic.mp3"));
		bgMusic.setVolume(0.5f);
		bgMusic.setLooping(true);
		bgMusic.play();
		
		// Display welcome text.
		MsgPrinter.print("Welcome to MineTD!", 2f);
		MsgPrinter.print("Good luck!", 5f);
		
		MsgPrinter.print("", 5f);
		MsgPrinter.print("Build turrets by costracting squere units of 2x2 elements to defend the cacke agenst the ENEMY!", 30f);
		MsgPrinter.print("Overlaping squer units will be part and bost the same turret.", 30f);
		MsgPrinter.print("The upper left element of every squer will determen what stat will be boosted;", 30f);
		MsgPrinter.print("and the type of every other element in a squer will determin the power of the boost.", 30f);
		MsgPrinter.print("", 30f);
		MsgPrinter.print("DIRT: makes minial tower with no busts. GRAVLE: boostes bullet speed. ", 30f);
		MsgPrinter.print(" STONE: boostes fire rate. IRON: boosts Damag. GOLD: bosts Range", 30f);
		MsgPrinter.print("", 30f);
		MsgPrinter.print("Use right muse button to build and mine, use the gui to select material for bulding.", 30f);
		MsgPrinter.print("Hold down left mous button and drag to look around the map.", 30f);
		MsgPrinter.print("", 30f);
		MsgPrinter.print("Dont die, KILL THE ENEMY!", 30f);
		 
	}

	/**
	 * Loads game assets into the AssetManager.
	 */
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

	/**
	 * Sets up the camera to fill the game window.
	 */
	private void setupCam() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 1.5f;
		camera.update();
	}

	/**
	 * Dispose of assets.
	 */
	@Override
	public void dispose() {
		assetManager.dispose();	
	}
	
	/**
	 * Updates the game state, clears the screen
	 * and renders everything that goes on within the game. 
	 */
	@Override
	public void render() {	
		updateGame();
		
		// Clear the screen.
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		level.draw(camera);
		//level.DrawPathGraph(); // Debug graph for map + unit pathing.
		
		// Sending Draw jobs to our SpriteBatch.
		getMapBatch().begin();
		enemyManager.draw(getMapBatch());
		buildingManager.draw(getMapBatch());
		getMapBatch().end();
		uiManager.draw(getMapBatch());
	}

	/**
	 * Updates the game state.
	 */
	private void updateGame() {
		camera.update();
		enemyManager.update();
		buildingManager.update();
		uiManager.update();
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
	
	// Various get methods
	@Override
	public OrthographicCamera getCamera() {
		return camera;
		
	}
	@Override
	public EnemyManagerService getEnemiesManager() {
		return enemyManager;
	}
	@Override
	public SpriteBatch getMapBatch() {
	  return level.getRenderer().getSpriteBatch();
	}
	@Override
	public InputMultiplexer getInputMultiplexer() {
		return inputMultiplexer;
	}
	@Override
	public AssetManager getAssetManager() {
		return assetManager;
	}
	@Override
	public MouseInputAdapterService getInput() {
		return input;
	}
	@Override
	public GameMapService getLevel() {
		return level;
	}
	@Override
	public PlayerStatsService getPlayerStats() {
		return playerStats;
	}

	
}
