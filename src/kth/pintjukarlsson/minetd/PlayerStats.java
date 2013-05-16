package kth.pintjukarlsson.minetd;

import java.util.HashMap;

import kth.pintjukarlsson.minetd.interfaces.GameService;
import kth.pintjukarlsson.minetd.interfaces.PlayerStatsService;


public class PlayerStats implements PlayerStatsService{
	
	private int energy = 8;
	private HashMap<TileType, Integer> resourceMap;
	private TileType tileSelected = tilesforselect[0];
	private static TileType[] tilesforselect= new TileType[]{TileType.DIRT, TileType.GRAVEL, TileType.STONE, TileType.IRON, TileType.GOLD};
	private int lives = 10;
	
	public PlayerStats(){
		resourceMap = new HashMap<>();
		for (TileType tt : tilesforselect) {
			switch (tt) {
			case DIRT:
				resourceMap.put(tt, 50);
				break;
			case GRAVEL:
				resourceMap.put(tt, 2);
				break;
			case STONE:
				resourceMap.put(tt, 1);
				break;
			default:
				resourceMap.put(tt, 0);
			}
		}
	}
// not sure if this is good enough or there should be tile IDs or something
	// (just trying to get it to work atm)
	/**
	 * Buttons are created from the usable tiles defined in this class. (tilesforselect)
	 * Every buttons name is set to the String representation of the TileType. (tt.toString())
	 * When a button is clicked, the current tile selection is set to the
	 * appropriate TileType through this method.
	 * 
	 * @param tilename
	 * Tilename String. If there's no usable TileType defined with
	 * such a name, we throw an IllegalArgumentException.
	 */
	@Override
	public void setSelect(String tilename){
		for (TileType tt : tilesforselect) {
			if (tilename.equals(tt.toString())) {
				tileSelected = tt;
				System.out.println(tileSelected);
				return;
			}
		}
		throw new IllegalArgumentException(tilename + " is not a usable tile");
	}
	/**
	 * Returns the currently selected TileType.
	 */
	public TileType getSelect() {
		return tileSelected;
	}
	/**
	 * returns energy
	 */
	@Override
	public int getEnergy()
	{
		return energy;
	}
	
	@Override
	public boolean hasEnergy(){
		if(energy<=0)
			return false;
		return true;
	}
	@Override
	public void popEnergy(){
		energy--;
	}
	@Override
	public void pushEnergy(){
		energy++;
	}
	@Override
	public int getLives() {
		return lives;
	}
	
	/**
	 * Returns the current amount of the specified TileType.
	 */
	public int getAmount(String tilename) {
	for (TileType tt : tilesforselect) {
		if (tilename.equals(tt.toString())) {
			return resourceMap.get(tt);
		}
	}
	throw new IllegalArgumentException(tilename + " is not a usable tile");
	}
	
	public void addAmount(String tilename, int amount) {
		for (TileType tt : tilesforselect) {
			if (tilename.equals(tt.toString())) {
				int x = resourceMap.get(tt);
				resourceMap.put(tt, amount+x);
			}
 		}
	}

	/**
	 * Returns the array of usable tiles defined in this class.
	 */
	public static TileType[] getUsableTiles() {
		return tilesforselect;
	}
	public void reduseLives() {
		lives--;
		
	}
}
