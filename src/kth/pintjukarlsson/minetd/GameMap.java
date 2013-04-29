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

public class GameMap {
	private TiledMap map;
	private TiledMapRenderer renderer;
	private PositionGraph graph;
	private ArrayList<LinkDebug> links = new ArrayList<LinkDebug>();
	
	void init(){
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
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
	
	public void buildDebugGraph(){
			
			TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
			int h=layer.getHeight();
			int w= layer.getWidth();
			for (int y = 0; y <= h; y++){
				for (int x = 0; x <= w; x++){
					Cell cell = layer.getCell(x, y);
					if (cell == null) {
						if(layer.getCell(x+1, y)==null &&x<w)
							graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y));
						if(layer.getCell(x-1, y)==null&&x>0)
							graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y));
						if(layer.getCell(x, y+1)==null&&y<h)
							graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x, y+1));
						if(layer.getCell(x, y-1)==null&&y>0)
							graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x, y-1));
						
						if(layer.getCell(x+1, y+1)==null&&y<h&&x<w)
							graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y+1));
						if(layer.getCell(x-1, y-1)==null&&y>0&&x>0)
							graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y-1));
						if(layer.getCell(x-1, y+1)==null&&y<h&&x>0)
							graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x-1, y+1));
						if(layer.getCell(x+1, y-1)==null&&x<w&&y>0)
							graph.add(new ImuteblePosition(x, y), new ImuteblePosition(x+1, y-1));
					}
				}
			}
			
			
			
			graph.reBuildDibugImg();
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
		graph.removeAllLinksTo(new ImuteblePosition(x, y));
		graph.reBuildDibugImg();
		return true;
	}
	
	public boolean  removeTile(int tile, int x, int y){
		return false;
	}
	public ImuteblePosition[] getPath(ImuteblePosition a, ImuteblePosition b){
		return Dijkstra.findPath(graph, a, b);
	}
	
	public void loadAssets(AssetManager assetManager){
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("data/level1.tmx", TiledMap.class);
		assetManager.finishLoading();
		map = assetManager.get("data/level1.tmx");
		//map.getLayers().get(0).setVisible(false);
		init();
		buildDebugGraph();
	}
}
