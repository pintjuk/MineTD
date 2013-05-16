package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kth.pintjukarlsson.minetd.interfaces.BuildingManagerService;
import kth.pintjukarlsson.minetd.interfaces.GameMapService;
import kth.pintjukarlsson.minetd.interfaces.GameService;
import kth.pintjukarlsson.minetd.interfaces.MouseInputAdapterService;
import kth.pintjukarlsson.minetd.listeners.MapInteractionListener;
/**
 * 
 * @author pintjuk&karlsson
 *
 */
public class BuildingManager implements BuildingManagerService{
	
	
	private ArrayList<Building> buildings;
	private final GameService game;
	private MouseInputAdapterService input;
	private GameMapService map;
	private final BuildingManager MAMMA = this;
	public BuildingManager(GameService game) {
		buildings = new ArrayList<>();
		this.game = game;
		
	}
	@Override
	public void init(){
		input = game.getInput();
		map = game.getLevel();
		input.setMapInteractionListener(new MapInteractionListener() {
			@Override
			public void onTileRM(int x, int y, TileType t) {
				removeAllStatsAtt(x, y);
					
			}
			
			@Override
			public void onTileAdded(int x, int y, TileType t) {
				TileType t11 = map.getTileType(x-1, y+1);
				TileType t21 = map.getTileType(x, y+1);
				TileType t31 = map.getTileType(x+1, y+1);
				TileType t12 = map.getTileType(x-1, y);
				TileType t32 = map.getTileType(x+1, y);
				TileType t13 = map.getTileType(x-1, y-1);
				TileType t23 = map.getTileType(x, y-1);
				TileType t33 = map.getTileType(x+1, y-1);
				/*
				 *    ::
				 *    :*
				 */
				if(		  map.hasBuilt(x, y+1)
						&&map.hasBuilt(x-1, y)
						&&map.hasBuilt(x-1, y+1)){
					
					addToBuildingOreNewBuilding(new Stat(x-1, y+1
							, t11, t21, t12, t));
				}
				
				/*
				 *    :*
				 *    ::
				 */
				else
				if(		  map.hasBuilt(x, y-1)
						&&map.hasBuilt(x-1, y)
						&&map.hasBuilt(x-1, y-1)){
					addToBuildingOreNewBuilding(new Stat(x-1, y
							, t12, t, t13, t23));
				}
				
				/*
				 *    ::
				 *    *:
				 */
				else
				if(		  map.hasBuilt(x, y+1)
						&&map.hasBuilt(x+1, y)
						&&map.hasBuilt(x+1, y+1)){
					addToBuildingOreNewBuilding(new Stat(x, y+1
							, t21, t31, t, t32));
					
				}
				
				/*
				 *     *:
				 *     ::
				 */
				else
				if(		  map.hasBuilt(x, y-1)
						&&map.hasBuilt(x+1, y)
						&&map.hasBuilt(x+1, y-1)){
					addToBuildingOreNewBuilding(new Stat(x, y
							, t, t32, t23, t33));
				}
			}

			private void addToBuildingOreNewBuilding(Stat stat) {
				
				Building b = MAMMA.getBuildingWithStatAt(stat.getX()-1, stat.getY()+1);
				if(b ==null)
					     b = MAMMA.getBuildingWithStatAt(stat.getX(), stat.getY()+1);
				if(b ==null)
						 b = MAMMA.getBuildingWithStatAt(stat.getX()+1, stat.getY()+1);
				if(b ==null)
					 b = MAMMA.getBuildingWithStatAt(stat.getX()-1, stat.getY());
				if(b ==null)
					 b = MAMMA.getBuildingWithStatAt(stat.getX()+1, stat.getY());
				if(b ==null)
					 b = MAMMA.getBuildingWithStatAt(stat.getX()-1, stat.getY()-1);
				if(b ==null)
					 b = MAMMA.getBuildingWithStatAt(stat.getX(), stat.getY()-1);
				if(b ==null)
					b = MAMMA.getBuildingWithStatAt(stat.getX()+1, stat.getY()-1);
				if(b ==null){
					b = new Building(0, 0, game);
					buildings.add(b);
				}
				b.addStat(stat);
			}
		});
	}
	private void removeAllStatsAtt(int x, int y){
		ArrayList<Building> torm = new ArrayList<>();
		for (Building b : buildings) {
			b.removeStatsThahHave(x, y);
			if(b.stats.size()==0){
				torm.add(b);
			}
		}
		for(Building b : torm) 
			buildings.remove(b);
	}
	
	private Building getBuildingWithStatAt(int x, int y){
		for (Building b : buildings) {
			if(b.hasStat(x,y))
				return b;
		}
		return null;
	}

	/**Runs the Draw method for each existing Building
	 * 
	 * @param batch
	 */
	@Override
	public void draw(SpriteBatch batch) {
		for (Building b : buildings)
			b.Draw(batch);
		
	}
	/** Runs the Update method for each existing Building
	 * 
	 */
	@Override
	public void update() {
		for (Building b : buildings)
			b.Update();
		
	}
	
}
