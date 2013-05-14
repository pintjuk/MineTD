package kth.pintjukarlsson.minetd;

import kth.pintjukarlsson.minetd.listeners.MapInteractionListener;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PlayerStats {
	private int energy = 8;
	private int[] resorceCount = new int[]{3, 0, 0};
	private TileType[] tilesforselect= new TileType[]{TileType.DIRT, TileType.GRAVEL, TileType.STONE};
	private int selected =0;
	private BitmapFont font;
	private MineTD game;
	//private GameMap map;
	private MouseInputAdapter input;
	public PlayerStats(MineTD game){
		font= new BitmapFont();
		this.game = game;
		//this.map =this.game.getLevel();
		input = game.getInput();
		
	}
	/*
	 * return energy
	 */
	public int getEnergy()
	{
		return energy;
	}
	/*
	 * input 0 to get amount of dirt
	 * input 1 to get amount of gravle
	 * input 2 to get amount of ston
	 */
	public int getAmountOfResorces(int i){
		return resorceCount[i];
	}
	public void setSelect(int i){
		if(selected >3||selected <0)
			return;
		selected = i;
	}
	public boolean hasSelectedMaterial(){
		if(resorceCount[selected]<=0)
			return false;
		return true;
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
	public TileType popSelected(){
		if(resorceCount[selected]<=0)
			return null;
		resorceCount[selected]--;
		return tilesforselect[selected];
	}
	public void Draw(SpriteBatch batch){
		font.draw(batch, "hej", 10, 10);
		
	}
	public void pushMaterial(TileType t) {
		switch (t) {
		case DIRT:
			resorceCount[0]++;
			break;
		case GRAVEL:
			resorceCount[1]++;
			break;
		case STONE:
			resorceCount[2]++;
			break;
		}
		
		
	}

}
