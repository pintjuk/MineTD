package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Building extends Entity {
	ArrayList<Stat> stats = new ArrayList<>();
	// Creates a new Building object at x, y
	final private float defoultFireRate= 1;
	final private int defoultRange= 4;
	final private float defoultDamage=1;
	public Building(int x, int y, MineTD g) {
		super(x, y, g);
	}
	
	/**
	 * {@inheritDoc Entity}
	 */
	@Override
	public void Update() {
	}
	/**
	 * {@inheritDoc Entity}
	 */
	@Override
	public void Draw(SpriteBatch batch) {
		super.Draw(batch);
	}
	
	public float getFireRate(){
		return defoultFireRate*getGetCombinedPowerOfAllStatsOfType(TileType.DIRT);
	}
	public float getRange(){
		return defoultRange*getGetCombinedPowerOfAllStatsOfType(TileType.SAND);
	}
	
	public float getDamage(){
		return defoultDamage*getGetCombinedPowerOfAllStatsOfType(TileType.STONE);
	}
	
	public float getGetCombinedPowerOfAllStatsOfType(TileType t){
		float total=1;
		for(Stat s: this.stats)
			if(s.getFirstTileType().equals(t)){
				total*=s.getValue();
			}
		
		return total;
	}
	public boolean hasStat(int x, int y){
		for(Stat s: this.stats){
			if(s.containts(x, y))
				return true;
		}
		return false;
	}
	
	public void rmStat(int x, int y){
		Stat torm = null;
		for(Stat s: this.stats){
			if(s.containts(x, y))
				torm=s;
		}
		if(torm!=null)
			stats.remove(torm);
	}
	public void addStat(Stat s){
		stats.add(s);
	}
	
}
