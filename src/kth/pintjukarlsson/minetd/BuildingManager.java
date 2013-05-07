package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kth.pintjukarlsson.minetd.listeners.MapInteractionListener;

public class BuildingManager {
	
	
	private ArrayList<Building> buildings;
	private final MineTD game;
	private MouseInputAdapter input;
	public BuildingManager(MineTD game) {
		buildings = new ArrayList<>();
		this.game = game;
		
	}
	public void Init(){
		input = game.getInput();
		input.setMapInteractionListener(new MapInteractionListener() {
			
			@Override
			public void onTileRM(int x, int y) {
				
				
			}
			
			@Override
			public void onTileAdded(int x, int y, TileType t) {
				
				
			}
		});
	}

	// Runs the Update method for each existing Building
	public void Update() {
		for (Building b : buildings) {
			b.Update();
		}
	}
	// Runs the Draw method for each existing Building
	public void Draw(SpriteBatch batch) {
		for (Building b : buildings) {
			b.Draw(batch);
		}
	}
}
