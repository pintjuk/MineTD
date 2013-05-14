package kth.pintjukarlsson.minetd;

import java.util.HashMap;

import kth.pintjukarlsson.minetd.listeners.MapInteractionListener;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PlayerStats {
	
	private int energy = 8;
	private HashMap<TileType, Integer> resourceMap;
	private TileType tileSelected = tilesforselect[0];
	private static TileType[] tilesforselect= new TileType[]{TileType.DIRT, TileType.GRAVEL, TileType.STONE};
	private MineTD game;
	private int lives = 40;
	//private GameMap map;
	private MouseInputAdapter input;
	public PlayerStats(MineTD game){
		this.game = game;
		//this.map =this.game.getLevel();
		input = game.getInput();
		
		resourceMap = new HashMap<>();
		for (TileType tt : tilesforselect) {
			resourceMap.put(tt, 3);
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
	/*
	 * return energy
	 */
	public int getEnergy()
	{
		return energy;
	}
	
	public boolean hasEnergy(){
		if(energy<=0)
			return false;
		return true;
	}
	public void popEnergy(){
		energy--;
	}
	public void pushEnergy(){
		energy++;
	}
	
	public int getLives() {
		return lives;
	}

//	public TileType popSelected(){
//		if(resourceCount[selected]<=0)
//			return null;
//		resourceCount[selected]--;
//		return tilesforselect[selected];
//	}
	//public void Draw(SpriteBatch batch){
	//	font.draw(batch, "hej", 10, 10);
	//}
	
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
}
