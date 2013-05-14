package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import kth.pintjukarlsson.debugdraw.LinkDebug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import kth.pintjukarlsson.minetd.listeners.*;

public class MouseInputAdapter extends InputAdapter {
	private OrthographicCamera camera;
	private GameMap level;
	private MineTD game;
	private PlayerStats pStats;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	private boolean leftButtonDown = false;
	private ArrayList<MapInteractionListener> milisteners = new ArrayList<>();
	
	private boolean initilized= false;

	public MouseInputAdapter (MineTD g) {
		this.game = g;
	}
	
	public void init(){
		this.camera = game.getCamera();
		this.level = game.getLevel();
		this.pStats = game.getPlayerStats(); 
	}

	
	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		camera.unproject(curr.set(x, y, 0));
		if(leftButtonDown){
			if (!(last.x == -1 && last.y == -1 && last.z == -1)){
				camera.unproject(delta.set(last.x, last.y, 0));
				delta.sub(curr);
				camera.position.add(delta.x, delta.y, 0);
			}
		}
		last.set(x, y, 0);
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		last.set(-1, -1, -1);
		if(Buttons.LEFT==button)
			leftButtonDown = false;
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		Vector3 m = new Vector3();
		camera.unproject(m.set(screenX, screenY, 0));
		int mapx=(int)(m.x);
		int mapy=(int)(m.y);
		if(Buttons.RIGHT==button){
			if(level.spotFree(mapx, mapy)){
				TileType tt = game.getPlayerStats().getSelect();
				if (game.getPlayerStats().getAmount(tt.toString()) > 0) {
					if(level.setTile(tt, mapx,mapy)){
						game.getPlayerStats().addAmount(tt.toString(), -1);
						for(MapInteractionListener i: milisteners){
							i.onTileAdded(mapx,mapy, tt);
						}
					}
				}
			}
			else {
				//level.removeTile(1, mapx,mapy);

				if(pStats.hasEnergy()){
					TileType t =level.removeTile( mapx,mapy);
					if(t != null){
						game.getPlayerStats().addAmount(t.toString(), 1);
						if(t!=null){
							pStats.popEnergy();
							for(MapInteractionListener i: milisteners){
								i.onTileRM(mapx,mapy, t);
							}
						}
					}
				}
				
			}
		}
		//level.resetDebugDraw();
		if(Buttons.LEFT==button)
			leftButtonDown = true;
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return super.mouseMoved(screenX, screenY);
	}
	
	public void setMapInteractionListener(MapInteractionListener m){
		milisteners.add(m);
	}
}
