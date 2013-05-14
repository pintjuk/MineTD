package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import kth.pintjukarlsson.graph.ImuteblePosition;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Vector2;

public class EnemyManager {

	private final static int MAX_ENEMIES = 30;
	private int[] spawnPos;
	
	private ArrayList<Enemy> enemies;
	
	private int numEnemies;
	private int enemyHP;
	private int enemySpeed;
	
	
	private MineTD game;
	private PlayerStats pStat;
	private ImuteblePosition[] startToGoal;
	/**
	 *  Creates a new enemy manager that spawns stuff at the location x, y.
	 *  It starts out with some default values, including half the maximum number of enemies.
	 */
	public EnemyManager(int x, int y, MineTD g) {
		spawnPos = new int[] {x, y};
		enemies = new ArrayList<Enemy>();
		numEnemies = MAX_ENEMIES/2;
		startToGoal = new ImuteblePosition[]{ new ImuteblePosition(0, 0),
											  new ImuteblePosition(1, 2),
											  new ImuteblePosition(1, 4)};
		game = g;
		enemyHP = 10;
		enemySpeed = 2;
	}
	
	// Spawns a new wave of enemies in a square at the default spawn location.
	// (Only spawn a wave if the previous wave is cleared.) 
	// Returns true if successful. 
	public boolean spawnWave() {
		if (enemies.size() != 0)
			return false;
		int side = (int)Math.ceil(Math.sqrt(numEnemies)); // side of square
		for (int i=0; i<numEnemies; i++) {
			int x = spawnPos[0]+(i%side);
			int y = spawnPos[1]+(i/side);
			enemies.add(new Enemy(x, y, enemyHP, enemySpeed, game, startToGoal));
		}
		// Increase difficulty parameters for next wave
		enemyHP *= 1.5;
		enemySpeed += 1;
		if (numEnemies < MAX_ENEMIES)
			numEnemies++;
		return true;
	}
	public void init(){
		this.startToGoal =  game.getLevel().getPathStartToFinish();
		this.pStat = game.getPlayerStats();
		enemies.add(new Enemy(startToGoal[0].getX(), startToGoal[0].getY(), enemyHP, enemySpeed,game,this.startToGoal));
		enemies.add(new Enemy(startToGoal[4].getX(), startToGoal[4].getY(), enemyHP, enemySpeed,game,this.startToGoal));
		enemies.add(new Enemy(startToGoal[0].getX(), startToGoal[1].getY(), enemyHP, enemySpeed,game,this.startToGoal));
		enemies.add(new Enemy(startToGoal[4].getX(), startToGoal[2].getY(), enemyHP, enemySpeed,game,this.startToGoal));
		enemies.add(new Enemy(startToGoal[0].getX(), startToGoal[6].getY(), enemyHP, enemySpeed,game,this.startToGoal));
		enemies.add(new Enemy(startToGoal[4].getX(), startToGoal[9].getY(), enemyHP, enemySpeed,game,this.startToGoal));
		enemies.add(new Enemy(startToGoal[0].getX(), startToGoal[12].getY(), enemyHP, enemySpeed,game,this.startToGoal));
		enemies.add(new Enemy(startToGoal[4].getX(), startToGoal[7].getY(), enemyHP, enemySpeed,game,this.startToGoal));
		
	}
	// Runs the Update() method for each existing enemy
	public void Update() {
		ArrayList<Enemy> torm = new ArrayList<Enemy>();
		for (Enemy e : enemies){
			e.Update();
			if(e.isDead()){
				pStat.pushEnergy();
				torm.add(e);
			}
			if(e.getPos().epsilonEquals(startToGoal[startToGoal.length-1].getVec(),0.4f)){
				pStat.reduseLives();
				torm.add(e);
			}
		}
		for (Enemy e : torm)
			enemies.remove(e);
	}
	// Runs the Draw() method for each existing enemy
	public void Draw(SpriteBatch batch) {
		for (Enemy e : enemies)
			e.Draw(batch);
	}
	public Enemy getClosestTo(Vector2 v){
		Enemy closest=null;
		float dis = 100000000;
		for(Enemy e: enemies){
			float newDist = e.getPos().dst(v);
			if(newDist<dis){
				dis = newDist;
				closest =e;
			}
		}
		//return null;
		return closest;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
}
