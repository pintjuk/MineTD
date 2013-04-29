package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.math.Vector2;

public class EnemyManager {

	private final static int MAX_ENEMIES = 25;
	private int[] spawnPos;
	
	private Enemy[] enemies;
	
	private int numEnemies;
	private int enemyHP;
	private int enemySpeed;
	
	/**
	 *  Creates a new enemy manager that spawns stuff at the location x, y.
	 *  It starts out with some default values, and uses the maximum number of enemies.
	 */
	public EnemyManager(int x, int y) {
		spawnPos = new int[] {x, y};
		
		enemies = new Enemy[MAX_ENEMIES];
		numEnemies = MAX_ENEMIES;
		enemyHP = 10;
		enemySpeed = 50;
	}
	
	// Spawns a new wave of enemies in a square at the default spawn location
	private void spawnWave() {
		int side = (int)Math.ceil(Math.sqrt(numEnemies)); // side of square
		for (int i=0; i<numEnemies; i++) {
			int x = spawnPos[0]+(i%side);
			int y = spawnPos[1]+(i/side);
			enemies[i] = new Enemy(x, y, enemyHP, enemySpeed);
		}
		// Increase difficulty parameters for next wave
		enemyHP *= 1.5;
		enemySpeed += 1;
		
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
