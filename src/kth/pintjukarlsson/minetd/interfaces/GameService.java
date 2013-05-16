package kth.pintjukarlsson.minetd.interfaces;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameService {
	/**
	 * 
	 * @return
	 */
	OrthographicCamera getCamera();
	/**
	 * 
	 * @return
	 */
	EnemyManagerService getEnemiesManager();
	/**
	 * 
	 * @return
	 */
	SpriteBatch getMapBatch();
	/**
	 * 
	 * @return
	 */
	InputMultiplexer getInputMultiplexer();
	/**
	 * 
	 * @return
	 */
	AssetManager getAssetManager();
	/**
	 * 
	 * @return
	 */
	MouseInputAdapterService getInput();
	/**
	 * 
	 * @return
	 */
	GameMapService getLevel();
	/**
	 * 
	 * @return
	 */
	PlayerStatsService getPlayerStats();
}
