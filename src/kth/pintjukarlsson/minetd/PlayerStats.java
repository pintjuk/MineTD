package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PlayerStats {
	// i would really like to put these values together into a map of some sort
	// to avoid switch blocks and code duplication
	private int power=0;
	private int gravel=10;
	private int dirt=10;
	private int stone=10;
	private static TileType[] tilesforselect= new TileType[]{TileType.DIRT, TileType.GRAVEL, TileType.STONE};
	// -----------------
	
	private BitmapFont font;
	private TileType tileSelected = tilesforselect[0];
	public PlayerStats(){
		font= new BitmapFont();
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
	
	public TileType popSelected(){
		return null;
	}
	public void Draw(SpriteBatch batch){
		font.draw(batch, "hej", 10, 10);
		
	}
	
	/**
	 * Returns the current amount of the specified TileType.
	 */
	public int getAmount(String tilename) {
		switch (tilename) {
		case "DIRT":
			return dirt;
		case "GRAVEL":
			return gravel;
		case "STONE":
			return stone;
		default:
			throw new IllegalArgumentException(tilename + " is not a usable tile");
		}
	}
	
	public void addAmount(String tilename, int amount) {
		switch(tilename) {
		case "DIRT":
			dirt += amount;
			break;
		case "GRAVEL":
			gravel += amount;
			break;
		case "STONE":
			stone += amount;
			break;
		}
	}

	/**
	 * Returns the array of usable tiles defined in this class.
	 */
	public static TileType[] getUsableTiles() {
		return tilesforselect;
	}
}
