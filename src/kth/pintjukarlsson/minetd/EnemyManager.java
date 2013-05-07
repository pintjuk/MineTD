package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import kth.pintjukarlsson.graph.ImuteblePosition;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EnemyManager {

	private final static int MAX_ENEMIES = 25;
	private int[] spawnPos;
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	private int numEnemies;
	private int enemyHP;
	private int enemySpeed;
	
	private MineTD game;
	private ImuteblePosition[] startToGoal;
	/**
	 *  Creates a new enemy manager that spawns stuff at the location x, y.
	 *  It starts out with some default values, and uses the maximum number of enemies.
	 */
	public EnemyManager(int x, int y, MineTD g) {
		spawnPos = new int[] {x, y};
		startToGoal = new ImuteblePosition[]{ new ImuteblePosition(0, 0),
											  new ImuteblePosition(1, 2),
											  new ImuteblePosition(1, 4)};
		game = g;
		numEnemies = MAX_ENEMIES;
		enemyHP = 10;
		enemySpeed = 1;
		enemies.add(new Enemy(x, y, enemyHP, enemySpeed, game, startToGoal));
	}
	
	// Spawns a new wave of enemies in a square at the default spawn location
	private void spawnWave() {
		int side = (int)Math.ceil(Math.sqrt(numEnemies)); // side of square
		for (int i=0; i<numEnemies; i++) {
			int x = spawnPos[0]+(i%side);
			int y = spawnPos[1]+(i/side);
			enemies.add(new Enemy(x, y, enemyHP, enemySpeed, game, startToGoal));
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
	public void Draw(SpriteBatch batch) {
		for (Enemy e : enemies)
			e.Draw(batch);
	}
}
