package kth.pintjukarlsson.minetd;

public class BuildingManager {
	
	private final static int MAX_BUILDINGS = 256;
	
	private Building[] buildings;
	
	public BuildingManager() {
		buildings = new Building[MAX_BUILDINGS];
	}

	// Runs the Update method for each existing Building
	public void Update() {
		for (Building b : buildings) {
			b.Update();
		}
	}
	// Runs the Draw method for each existing Building
	public void Draw() {
		for (Building b : buildings) {
			b.Draw();
		}
	}
}
