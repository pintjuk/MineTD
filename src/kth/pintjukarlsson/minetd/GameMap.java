package kth.pintjukarlsson.minetd;

import java.util.ArrayList;
import java.util.HashMap;

import kth.pintjukarlsson.graph.Dijkstra;
import kth.pintjukarlsson.graph.ImmutablePosition;
import kth.pintjukarlsson.graph.PositionGraph;
import kth.pintjukarlsson.minetd.interfaces.GameMapService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameMap implements GameMapService {
	

	
	private HashMap<ImmutablePosition, Float> activeLavaBricks= new HashMap<>();

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private PositionGraph graph;
	private TiledMapTileLayer pathingLayer;
	private ImmutablePosition span, finish; 
	private ImmutablePosition[] pathGoalToFinish;
	
	
	void init(){
		renderer = new OrthogonalTiledMapRenderer(map, 1f /16f);
		
		graph = new PositionGraph(((TiledMapTileLayer)map.getLayers().get(1)).getWidth(), 
								  ((TiledMapTileLayer)map.getLayers().get(1)).getWidth());
	}
	
	@Override
	public ImmutablePosition getSpan() {
		return span;
	}
	
	@Override
	public ImmutablePosition getFinish() {
		return finish;
	}
	
	@Override
	public OrthogonalTiledMapRenderer getRenderer(){
		return  renderer;
	}
	
	@Override
	public void draw(OrthographicCamera camera){
		ArrayList<ImmutablePosition> torm= new ArrayList<ImmutablePosition>();
		for(ImmutablePosition t: activeLavaBricks.keySet()){
			activeLavaBricks.put(t, activeLavaBricks.get(t)-Gdx.graphics.getDeltaTime());
			if (activeLavaBricks.get(t)<=0){
				torm.add(t);
			}
		}
		for(ImmutablePosition t: torm){
			floawLava(t);
			Cell c = new Cell();
			c.setTile(map.getTileSets().getTile(462));
			pathingLayer.setCell(t.getX(), t.getY(), c);
			activeLavaBricks.remove(t);
		}
		renderer.setView(camera);
		renderer.render();
	}
	
	@Override
	public TileType getTileType(int x, int y){
		Cell c = pathingLayer.getCell(x, y);
		if(c==null)
			return null;
		return TileType.get(c.getTile().getId());
	}
	
	private void floawLava(ImmutablePosition t) {
		int x = t.getX();
		int y= t.getY();
		int h=pathingLayer.getHeight();
		int w= pathingLayer.getWidth();
		
		if(this.pathingLayer.getCell(x+1, y)==null &&x<w){
			this.activeLavaBricks.put(new ImmutablePosition(x+1, y), 0.8f);
		}
		if(this.pathingLayer.getCell(x-1, y)==null&&x>0)
			this.activeLavaBricks.put(new ImmutablePosition(x-1, y), 0.8f);
			
		if(this.pathingLayer.getCell(x, y+1)==null&&y<h)
			this.activeLavaBricks.put(new ImmutablePosition(x, y+1), 0.8f);
			
		if(this.pathingLayer.getCell(x, y-1)==null&&y>0)
			this.activeLavaBricks.put(new ImmutablePosition(x, y-1), 0.8f);
			
	}
	
	void DrawPathGraph(){
		
		graph.DibugDraw();
		
	}
	
	

	private void buildGraph(){
			
			TiledMapTileLayer layer = pathingLayer;
			int h=layer.getHeight();
			int w= layer.getWidth();
			for (int y = 0; y <= h; y++){
				for (int x = 0; x <= w; x++){
					
					Cell cell = layer.getCell(x, y);
					if (cell == null) {
						setGraphTile(x, y, layer);
					}else{
						if(cell.getTile().getProperties().get("type")!=null)
							if(cell.getTile().getProperties().get("type").equals("finish")){
								finish = new ImmutablePosition(x, y);
								setGraphTile(x, y, layer);
								((TiledMapTileLayer)map.getLayers().get(1)).setCell(x, y, cell);
								pathingLayer.setCell(x, y, null);
							}
						if(cell.getTile().getProperties().get("type")!=null)
							if(cell.getTile().getProperties().get("type").equals("start")){
								span = new ImmutablePosition(x, y);
								setGraphTile(x, y, layer);
								((TiledMapTileLayer)map.getLayers().get(1)).setCell(x, y, cell);
								pathingLayer.setCell(x, y, null);
							}
						
					}
				}
			}
	}
	
	
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
	@Override
	public boolean setTile(TileType tile, int x, int y){
		if (getPath(new ImmutablePosition(x, y), this.finish).length==0)
			return false;
		graph.removeAllLinksTo(new ImmutablePosition(x, y));
		int length = calcPathStartToFinish().length;
		if(length==0){
			setGraphTile(x, y, pathingLayer);
			graph.reBuildDibugImg();
			return false;
		}
		Cell cell = new BuildingCell();
		cell.setTile(map.getTileSets().getTile(tile.getBuildIndex()));
		
		pathingLayer.setCell(x, y, cell);
		graph.reBuildDibugImg();
		updatePathStartToFinish();
		return true;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public TileType  removeTile( int x, int y){
		TileType t = getTileType(x, y);
		if(!hasNullNighbour(x,y))
		  return null;
		
		pathingLayer.setCell(x, y, null);
		
		setGraphTile(x, y, pathingLayer);
		graph.reBuildDibugImg();
		updatePathStartToFinish();
		
		handleNighboreLava(x,y);
		return t;
	}
	@Override
	public ImmutablePosition[] getPath(ImmutablePosition a, ImmutablePosition b){
		return Dijkstra.findPath(graph, a, b);
	}
	@Override
	public ImmutablePosition[] calcPathStartToFinish(){
		 return getPath(this.span, this.finish);
	}
	@Override
	public void updatePathStartToFinish(){
		pathGoalToFinish = calcPathStartToFinish();
	}
	@Override
	public ImmutablePosition[] getPathStartToFinish(){
		return this.pathGoalToFinish;
	}
	
	@Override
	public void loadAssets(AssetManager assetManager){
		map = assetManager.get("data/map.tmx");
		pathingLayer = (TiledMapTileLayer) map.getLayers().get(2);
		init();
		buildGraph();
		updatePathStartToFinish();
	}
	@Override
	public boolean hasBuilt(int x, int y) {
		Cell c = pathingLayer.getCell(x, y);
		if(c != null)
			if(c instanceof BuildingCell)
				return true;
		return false;
	}
	
	@Override
	public boolean spotFree(int x, int y){
		return pathingLayer.getCell(x, y)==null;
	}
	
	private void handleNighboreLava(int x, int y) {
		int h=this.pathingLayer.getHeight();
		int w= this.pathingLayer.getWidth();
		
	
		
		if(this.pathingLayer.getCell(x+1, y)!=null &&x<w)
			if(pathingLayer.getCell(x+1, y).getTile().getId() ==462)
				this.activeLavaBricks.put(new ImmutablePosition(x+1, y), 0.8f);
		if(this.pathingLayer.getCell(x-1, y)!=null&&x>0)
			if(pathingLayer.getCell(x-1, y).getTile().getId() ==462)
				this.activeLavaBricks.put(new ImmutablePosition(x-1, y), 0.8f);
		if(this.pathingLayer.getCell(x, y+1)!=null&&y<h)
			if(pathingLayer.getCell(x, y+1).getTile().getId() ==462)
				this.activeLavaBricks.put(new ImmutablePosition(x, y+1), 0.8f);
		if(this.pathingLayer.getCell(x, y-1)!=null&&y>0)
			if(pathingLayer.getCell(x, y-1).getTile().getId() ==462)
				this.activeLavaBricks.put(new ImmutablePosition(x, y-1), 0.8f);
				
		
	}
	private boolean hasNullNighbour(int x, int y) {
		int h=this.pathingLayer.getHeight();
		int w= this.pathingLayer.getWidth();
		
		if(new ImmutablePosition(x+1, y).equals(finish))
			return true;
		if(new ImmutablePosition(x-1, y).equals(finish))
			return true;
		if(new ImmutablePosition(x, y+1).equals(finish))
			return true;
		if(new ImmutablePosition(x, y-1).equals(finish))
			return true;
		
		if(this.pathingLayer.getCell(x+1, y)==null &&x<w){
			if(getPath(new ImmutablePosition(x+1, y), this.finish).length>0)
				return true;
		}
		if(this.pathingLayer.getCell(x-1, y)==null&&x>0)
			if(getPath(new ImmutablePosition(x-1, y), this.finish).length>0)
				return true;
		if(this.pathingLayer.getCell(x, y+1)==null&&y<h)
			if(getPath(new ImmutablePosition(x, y+1), this.finish).length>0)
				return true;
		if(this.pathingLayer.getCell(x, y-1)==null&&y>0)
			if(getPath(new ImmutablePosition(x, y-1), this.finish).length>0)
				return true;
		return false;
		
	}
	
	private void setGraphTile(int x , int y, TiledMapTileLayer layer){
		int h=layer.getHeight();
		int w= layer.getWidth();
		
		if(layer.getCell(x+1, y)==null &&x<w)
			graph.addBi(new ImmutablePosition(x, y), new ImmutablePosition(x+1, y));
		if(layer.getCell(x-1, y)==null&&x>0)
			graph.addBi(new ImmutablePosition(x, y), new ImmutablePosition(x-1, y));
		if(layer.getCell(x, y+1)==null&&y<h)
			graph.addBi(new ImmutablePosition(x, y), new ImmutablePosition(x, y+1));
		if(layer.getCell(x, y-1)==null&&y>0)
			graph.addBi(new ImmutablePosition(x, y), new ImmutablePosition(x, y-1));
		
		//there seems to be bugs in deagonal links 
		
		/*if(layer.getCell(x+1, y+1)==null&&y<h&&x<w
				&&layer.getCell(x+1, y)==null
				&&layer.getCell(x, y+1)==null)
			graph.addBi(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y+1));
		
		if(layer.getCell(x-1, y-1)==null&&y>0&&x>0
				&&layer.getCell(x-1, y)==null
				&&layer.getCell(x, y-1)==null)
			graph.addBi(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y-1));
		
		if(layer.getCell(x-1, y+1)==null&&y<h&&x>0
				&&layer.getCell(x-1, y)==null
				&&layer.getCell(x, y+1)==null)
			graph.addBi(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y+1));
		
		if(layer.getCell(x+1, y-1)==null&&x<w&&y>0
				&&layer.getCell(x+1, y)==null
				&&layer.getCell(x, y-1)==null)
			graph.addBi(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y-1));*/
	
		
	}
	void resetDebugDraw(){
		graph.reBuildDibugImg();
	}

	
}
