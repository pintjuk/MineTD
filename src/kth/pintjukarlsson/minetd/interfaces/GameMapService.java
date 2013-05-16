package kth.pintjukarlsson.minetd.interfaces;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import kth.pintjukarlsson.graph.ImmutablePosition;
import kth.pintjukarlsson.minetd.TileType;
/**
 * 
 * @author pintjuk
 *
 */
public interface GameMapService {
	/**
	 * 
	 * @param x
	 * @param i
	 * @return
	 */
	boolean hasBuilt(int x, int i);
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	TileType getTileType(int i, int j);
	
	/**
	 * 
	 * @param camera
	 */
	void draw(OrthographicCamera camera);

	/**
	 * 
	 * @return
	 */
	OrthogonalTiledMapRenderer getRenderer();
	
	/**
	 * 
	 * @return
	 */
	ImmutablePosition getSpan();
	/**
	 * 
	 * @return
	 */
	ImmutablePosition getFinish();
	
	/**
	 * Sets a tile on the mop
	 * @param tile
	 * index of the type of tile
	 * @param x
	 * position along the x axes 
	 * @param y
	 * position along the y axes
	 * @return
	 * returns true if the tile was set sucsesfuly 
	 */
	boolean setTile(TileType tile, int x, int y);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	TileType removeTile(int x, int y);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	boolean spotFree(int x, int y);
	
	/**
	 * 
	 * @return
	 */
	ImmutablePosition[] getPathStartToFinish();
	
	/**
	 * 
	 * @param assetManager
	 */
	void loadAssets(AssetManager assetManager);
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	ImmutablePosition[] getPath(ImmutablePosition a, ImmutablePosition b);
	
	/**
	 * 
	 * @return
	 */
	ImmutablePosition[] calcPathStartToFinish();
	
	/**
	 * 
	 */
	void updatePathStartToFinish();

}
