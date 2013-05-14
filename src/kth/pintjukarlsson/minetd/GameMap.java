package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import javax.crypto.Cipher;

import kth.pintjukarlsson.debugdraw.LinkDebug;
import kth.pintjukarlsson.graph.Dijkstra;
import kth.pintjukarlsson.graph.ImuteblePosition;
import kth.pintjukarlsson.graph.PositionGraph;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameMap {
	public ImuteblePosition getSpan() {
		return span;
	}
	public ImuteblePosition getFinish() {
		return finish;
	}

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private PositionGraph graph;
	private TiledMapTileLayer pathingLayer;
	private ImuteblePosition span, finish; 
	private ImuteblePosition[] pathGoalToFinish;
	
	public OrthogonalTiledMapRenderer getRenderer(){
		return  renderer;
	}
	void init(){
		renderer = new OrthogonalTiledMapRenderer(map, 1f /16f);
		
		graph = new PositionGraph(((TiledMapTileLayer)map.getLayers().get(1)).getWidth(), 
								  ((TiledMapTileLayer)map.getLayers().get(1)).getWidth());
	}
	
	public void Draw(OrthographicCamera camera){
		renderer.setView(camera);
		renderer.render();
	}
	
	public void DrawPathGraph(){
		
		//graph.DibugDraw();
		
	}
	
	public TileType getTileType(int x, int y){
		Cell c = pathingLayer.getCell(x, y);
		if(c==null)
			return null;
		return TileType.get(c.getTile().getId());
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
								finish = new ImuteblePosition(x, y);
								setGraphTile(x, y, layer);
								((TiledMapTileLayer)map.getLayers().get(1)).setCell(x, y, cell);
								pathingLayer.setCell(x, y, null);
							}
						if(cell.getTile().getProperties().get("type")!=null)
							if(cell.getTile().getProperties().get("type").equals("start")){
								span = new ImuteblePosition(x, y);
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
	public boolean setTile(TileType tile, int x, int y){
		if (getPath(new ImuteblePosition(x, y), this.finish).length==0)
			return false;
		graph.removeAllLinksTo(new ImuteblePosition(x, y));
		int length = getPathStartToFinish().length;
		if(length==0){
			setGraphTile(x, y, pathingLayer);
			graph.reBuildDibugImg();
			return false;
		}
		Cell cell = new BuildingCell();
		cell.setTile(map.getTileSets().getTile(tile.getBuildIndex()));
		
		pathingLayer.setCell(x, y, cell);
		graph.reBuildDibugImg();
		calcPathStartToFinish();
		return true;
	}
	
	public TileType  removeTile( int x, int y){
		TileType t = getTileType(x, y);
		if(!hasNullNighbour(x,y))
		  return null;
		
		pathingLayer.setCell(x, y, null);
		
		setGraphTile(x, y, pathingLayer);
		graph.reBuildDibugImg();
		calcPathStartToFinish();
		return t;
	}
	
	
	private boolean hasNullNighbour(int x, int y) {
		int h=this.pathingLayer.getHeight();
		int w= this.pathingLayer.getWidth();
		
		if(new ImuteblePosition(x+1, y).equals(finish))
			return true;
		if(new ImuteblePosition(x-1, y).equals(finish))
			return true;
		if(new ImuteblePosition(x, y+1).equals(finish))
			return true;
		if(new ImuteblePosition(x, y-1).equals(finish))
			return true;
		
		if(this.pathingLayer.getCell(x+1, y)==null &&x<w){
			if(getPath(new ImuteblePosition(x+1, y), this.finish).length>0)
				return true;
		}
		if(this.pathingLayer.getCell(x-1, y)==null&&x>0)
			if(getPath(new ImuteblePosition(x-1, y), this.finish).length>0)
				return true;
		if(this.pathingLayer.getCell(x, y+1)==null&&y<h)
			if(getPath(new ImuteblePosition(x, y+1), this.finish).length>0)
				return true;
		if(this.pathingLayer.getCell(x, y-1)==null&&y>0)
			if(getPath(new ImuteblePosition(x, y-1), this.finish).length>0)
				return true;
		return false;
		
	}
	
	public boolean spotFree(int x, int y){
		return pathingLayer.getCell(x, y)==null;
	}
	
	private void setGraphTile(int x , int y, TiledMapTileLayer layer){
		int h=layer.getHeight();
		int w= layer.getWidth();
		
		if(layer.getCell(x+1, y)==null &&x<w)
			graph.addBi(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y));
		if(layer.getCell(x-1, y)==null&&x>0)
			graph.addBi(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y));
		if(layer.getCell(x, y+1)==null&&y<h)
			graph.addBi(new ImuteblePosition(x, y), new ImuteblePosition(x, y+1));
		if(layer.getCell(x, y-1)==null&&y>0)
			graph.addBi(new ImuteblePosition(x, y), new ImuteblePosition(x, y-1));
		
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
	public ImuteblePosition[] getPath(ImuteblePosition a, ImuteblePosition b){
		return Dijkstra.findPath(graph, a, b);
	}
	
	public ImuteblePosition[] calcPathStartToFinish(){
		 pathGoalToFinish = getPath(this.span, this.finish);
		return pathGoalToFinish;
	}
	public ImuteblePosition[] getPathStartToFinish(){
		return this.pathGoalToFinish;
	}
	
	public void loadAssets(AssetManager assetManager){
		map = assetManager.get("data/map.tmx");
		pathingLayer = (TiledMapTileLayer) map.getLayers().get(2);
		init();
		buildGraph();
		calcPathStartToFinish();
	}
	
	public void resetDebugDraw(){
		graph.reBuildDibugImg();
	}
	public boolean hasBuilt(int x, int y) {
		Cell c = pathingLayer.getCell(x, y);
		if(c != null)
			if(c instanceof BuildingCell)
				return true;
		return false;
	}
}
