package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.math.Vector2;

public class EnemyManager {

	private Enemy[] enemies;
	private final static int MAX_ENEMIES = 25;
	private final static int[] spawnPos = {0, 0};
	
	// Creates a new enemy manager that can hold
	// the default number of enemies
	public EnemyManager() {
		enemies = new Enemy[MAX_ENEMIES];
	}
	
	// Spawns a new wave of enemies in a square at the default spawn location
	private void spawnWave() {
		int side = (int)Math.ceil(Math.sqrt(MAX_ENEMIES)); // side of square
		for (int i=0; i<MAX_ENEMIES; i++) {
			enemies[i] = new Enemy(spawnPos[0]+(i%side), spawnPos[1]+(i/side));
		}
	}
	
	// Runs the Update() method for each existing enemy
	public void Update() {
		for (Enemy e : enemies)
			e.Update();
	}
	// Runs the Draw() method for each existing enemy
	public void Draw() {
		for (Enemy e : enemies)
			e.Draw();
	}
}
