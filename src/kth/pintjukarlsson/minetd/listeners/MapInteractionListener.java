
package kth.pintjukarlsson.minetd.listeners;

import kth.pintjukarlsson.minetd.TileType;
/**
 * 
 * @author pintjuk
 */
public interface MapInteractionListener {
	/**
	 * 
	 * @param x
	 * @param y
	 * @param t
	 */
	void onTileAdded(int x, int y, TileType t);
	/**
	 * 
	 * @param x
	 * @param y
	 * @param t
	 */
	void onTileRM(int x, int y, TileType t);
}
