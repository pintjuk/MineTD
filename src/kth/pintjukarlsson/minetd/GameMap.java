package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

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
	//ArrayList<LinkDebug> links = new ArrayList<LinkDebug>();
	
	void init(){
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
		
	}
	
	public void Draw(OrthographicCamera camera){
		renderer.setView(camera);
		renderer.render();
	}
	
	public void DrawPathGraph(){
		/*/_for(LinkDebug d: links){
			d.Draw();
		}*/
	}
	
	public void buildDebugGraph(){
		
			TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
			for (int y = 0; y <= layer.getHeight(); y++) {
				for (int x = 0; x <= layer.getWidth(); x++) {
					Cell cell = layer.getCell(x, y);
					if (cell == null) {
						
						/*if(layer.getCell(x+1, y)==null)
							links.add(new LinkDebug(x+0.5f, y+0.5f, x+1.5f, y+0.5f, 200));
						if(layer.getCell(x-1, y)==null)
							links.add(new LinkDebug(x+0.5f, y+0.5f, x-0.5f, y+0.5f, 200));
						if(layer.getCell(x, y+1)==null)
							links.add(new LinkDebug(x+0.5f, y+0.5f, x+0.5f, y+1.5f, 200));
						if(layer.getCell(x, y-1)==null)
							links.add(new LinkDebug(x+0.5f, y+0.5f, x+0.5f, y-0.5f, 200));
						if(layer.getCell(x+1, y+1)==null)
							links.add(new LinkDebug(x+0.5f, y+0.5f, x+1.5f, y+1.5f, 200));
						if(layer.getCell(x-1, y-1)==null)
							links.add(new LinkDebug(x+0.5f, y+0.5f, x-0.5f, y-0.5f, 200));
						if(layer.getCell(x-1, y+1)==null)
							links.add(new LinkDebug(x+0.5f, y+0.5f, x-0.5f, y+1.5f, 200));
						if(layer.getCell(x+1, y-1)==null)
							links.add(new LinkDebug(x+0.5f, y+0.5f, x+1.5f, y-0.5f, 200));*/
					}
				}
			}
	}
	
	public void loadAssets(AssetManager assetManager){
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("data/level1.tmx", TiledMap.class);
		assetManager.finishLoading();
		map = assetManager.get("data/level1.tmx");
		map.getLayers().get(0).setVisible(false);
		init();
	}
}
