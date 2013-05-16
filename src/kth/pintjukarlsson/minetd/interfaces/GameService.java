package kth.pintjukarlsson.minetd.interfaces;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameService {
	
	OrthographicCamera getCamera();
	
	EnemyManagerService getEnemiesManager();
	
	SpriteBatch getMapBatch();
	
	InputMultiplexer getInputMultiplexer();
	
	AssetManager getAssetManager();
	
	MouseInputAdapterService getInput();
	
	GameMapService getLevel();
	
	PlayerStatsService getPlayerStats();
}
