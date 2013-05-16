package kth.pintjukarlsson.minetd.interfaces;

import kth.pintjukarlsson.minetd.TileType;

/**
 * 
 * @author pintjuk
 *
 */
public interface PlayerStatsService {
	/**
	 * 
	 */
	void pushEnergy();
	/**
	 * 
	 */
	void reduseLives();
	/**
	 * 
	 * @return
	 */
	int getLives();
	/**
	 * 
	 * @return
	 */
	int getEnergy();
	/**
	 * 
	 * @param tilename
	 * @return
	 */
	int getAmount(String tilename);
	/**
	 * 
	 * @param name
	 */
	void setSelect(String name);
	void popEnergy();
	boolean hasEnergy();
	TileType getSelect();
	void addAmount(String string, int i);

}
