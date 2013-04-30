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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameMap {
	private TiledMap map;
	private TiledMapRenderer renderer;
	private PositionGraph graph;
	private ArrayList<LinkDebug> links = new ArrayList<LinkDebug>();
	private TiledMapTileLayer pathingLayer;
	Vector2 testPosition= new Vector2();
	LinkDebug lol = new LinkDebug(0, 0, 0, 0, 0);
	
	void init(){
		renderer = new OrthogonalTiledMapRenderer(map, 1f /32f);
		graph = new PositionGraph(((TiledMapTileLayer)map.getLayers().get(1)).getWidth(), 
								  ((TiledMapTileLayer)map.getLayers().get(1)).getWidth());
	}
	
	public void Draw(OrthographicCamera camera){
		renderer.setView(camera);
		renderer.render();
	}
	
	public void DrawPathGraph(){
		//for(LinkDebug d: links){
		//	d.Draw();
		//}
		graph.DibugDraw();
		for(LinkDebug d: links){
			d.Draw();
		}
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
					}
				}
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
	 * 
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
		Cell cell = new Cell();
		pathingLayer.setCell(x, y, cell);
		graph.removeAllLinksTo(new ImuteblePosition(x, y));
		return true;
	}
	
	public boolean  removeTile(int tile, int x, int y){
		pathingLayer.setCell(x, y, null);
		setGraphTile(x, y, pathingLayer);
		return false;
	}
	
	private void setGraphTile(int x , int y, TiledMapTileLayer layer){
		int h=layer.getHeight();
		int w= layer.getWidth();
		if(layer.getCell(x+1, y)==null &&x<w)
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y));
		if(layer.getCell(x-1, y)==null&&x>0)
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y));
		if(layer.getCell(x, y+1)==null&&y<h)
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x, y+1));
		if(layer.getCell(x, y-1)==null&&y>0)
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x, y-1));
		
	/*	if(layer.getCell(x+1, y+1)==null&&y<h&&x<w)
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y+1));
		if(layer.getCell(x-1, y-1)==null&&y>0&&x>0)
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y-1));
		if(layer.getCell(x-1, y+1)==null&&y<h&&x>0)
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y+1));
		if(layer.getCell(x+1, y-1)==null&&x<w&&y>0)
			graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y-1));
	
		*/
	}
	public ImuteblePosition[] getPath(ImuteblePosition a, ImuteblePosition b){
		return Dijkstra.findPath(graph, a, b);
	}
	
	public void loadAssets(AssetManager assetManager){
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("data/level1.tmx", TiledMap.class);
		assetManager.finishLoading();
		map = assetManager.get("data/level1.tmx");
		pathingLayer = (TiledMapTileLayer) map.getLayers().get(1);
		//map.getLayers().get(0).setVisible(false);
		init();
		buildGraph();
	}
	
	public void resetDebugDraw(){
		graph.reBuildDibugImg();
	}
}
