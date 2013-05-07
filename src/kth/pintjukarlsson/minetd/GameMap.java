package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

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
	private ArrayList<LinkDebug> links = new ArrayList<LinkDebug>();
	private TiledMapTileLayer pathingLayer;
	Vector2 testPosition= new Vector2();
	LinkDebug lol = new LinkDebug(0, 0, 0, 0, 0);
	private ImuteblePosition span, finish;
	
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
		
		graph.DibugDraw();
		for(LinkDebug d: links){
			d.Draw();
		}
	}

	private void buildGraph(){
			
			TiledMapTileLayer layer = pathingLayer;
			int h=layer.getHeight();
			int w= layer.getWidth();
			ArrayList<ImuteblePosition> torm = new ArrayList<ImuteblePosition>();
			for (int y = 0; y <= h; y++){
				for (int x = 0; x <= w; x++){
					
					Cell cell = layer.getCell(x, y);
					if (cell == null) {
						setGraphTile(x, y, layer);
					}else{
						if(cell.getTile().getId()==269){
							finish = new ImuteblePosition(x, y);
							setGraphTile(x, y, layer);
							//((TiledMapTileLayer)map.getLayers().get(1)).setCell(x, y, cell);
							//removeTile(0, x, y);
						
							
						}else 
						if(cell.getTile().getId()==196){
							span = new ImuteblePosition(x, y);
							setGraphTile(x, y, layer);
							//((TiledMapTileLayer)map.getLayers().get(1)).setCell(x, y, cell);
							//removeTile(0, x, y);
						}
						/*if(cell.getTile().getId()==226||cell.getTile().getId()==3){
							torm.add(new ImuteblePosition(x, y));
						}else{
							System.out.println(cell.getTile().getId());
						}*/
					}
				}
			}
			
			for(ImuteblePosition p: torm){
				removeTile(0, p.getX(), p.getY());
			}
	}
	
	public void repathtest(){
		links.clear();
		ImuteblePosition[] i = getPath(new ImuteblePosition(0, 7), new ImuteblePosition(17+13, 2));
		ImuteblePosition old = i[0];
		for(ImuteblePosition z: i){
			links.add(new LinkDebug(old.getX()+0.5f,old.getY()+0.5f, z.getX()+0.5f, z.getY()+0.5f, 0));
			old = z;
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
	public boolean setTile(int tile, int x, int y){
		//if (getPath(this.finish, new ImuteblePosition(x, y)).length==0)
		//	return false;
		graph.removeAllLinksTo(new ImuteblePosition(x, y));
		int length = getPathStartToFinish().length;
		if(length==0){
			setGraphTile(x, y, pathingLayer);
			graph.reBuildDibugImg();
			return false;
		}
			
		Cell cell = new Cell();
		cell.setTile(pathingLayer.getCell(1, 1).getTile());
		pathingLayer.setCell(x, y, cell);
		graph.reBuildDibugImg();
		return true;
	}
	
	public boolean  removeTile(int tile, int x, int y){
		if(!hasNullNighbour(x,y))
		  return false;
		
		pathingLayer.setCell(x, y, null);
		
		setGraphTile(x, y, pathingLayer);
		graph.reBuildDibugImg();
		return true;
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
		
		/*if((layer.getCell(x+1, y+1)==null&&y<h&&x<w)
				&&(layer.getCell(x+1, y)==null &&x<w)
				&&(layer.getCell(x, y+1)==null&&y<h))
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y+1));
		
		if((layer.getCell(x-1, y-1)==null&&y>0&&x>0)
				&&(layer.getCell(x-1, y)==null&&x>0)
				&&(layer.getCell(x, y-1)==null&&y>0))
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y-1));
		
		if((layer.getCell(x-1, y+1)==null&&y<h&&x>0)
				&&(layer.getCell(x-1, y)==null&&x>0)
				&&(layer.getCell(x, y+1)==null&&y<h))
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y+1));
		
		if((layer.getCell(x+1, y-1)==null&&x<w&&y>0)
				&&(layer.getCell(x+1, y)==null &&x<w)
				&&(layer.getCell(x, y-1)==null&&y>0))
		graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y-1));*/
	
		
	}
	public ImuteblePosition[] getPath(ImuteblePosition a, ImuteblePosition b){
		return Dijkstra.findPath(graph, a, b);
	}
	public ImuteblePosition[] getPathStartToFinish(){
		return getPath(this.span, this.finish);
	}
	
	public void loadAssets(AssetManager assetManager){
		map = assetManager.get("data/map.tmx");
		pathingLayer = (TiledMapTileLayer) map.getLayers().get(2);
		init();
		buildGraph();
	}
	
	public void resetDebugDraw(){
		graph.reBuildDibugImg();
	}
}
