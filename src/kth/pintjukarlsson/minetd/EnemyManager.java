package kth.pintjukarlsson.minetd;

import java.util.ArrayList;
import java.util.Random;

import kth.pintjukarlsson.graph.ImuteblePosition;

import com.badlogic.gdx.Gdx;
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
	private int hordCount=0;
	private float timeToNextEnemy = 1;
	private float elpesdtimeToNextEenemy=0;
	private float timeToNext=1;
	private int hordEnemyCount = 0;
	private int hordEnemies = 3;
	private float timeToNextHord = 6;
	private float elepcedTimebetwineHords = 0;
	
	private MineTD game;
	private PlayerStats pStat;
	private ImuteblePosition[] startToGoal;
	private Random rand = new Random(545454545);
	private boolean timeout;
	private boolean runTimoutCloc; 
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
		enemyHP = 12;
		enemySpeed = 2;
	}
	
	// Spawns a new wave of enemies in a square at the default spawn location.
	// (Only spawn a wave if the previous wave is cleared.) 
	// Returns true if successful. 
	/*public boolean spawnWave() {
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
	}*/
	
	
	
	private boolean spawn() {
		if(!timeout){
			elpesdtimeToNextEenemy+=Gdx.graphics.getDeltaTime();
		if(elpesdtimeToNextEenemy>timeToNext)
		{
			hordEnemyCount++;
			enemies.add(new Enemy(startToGoal[0].getX(), startToGoal[0].getY(), (int) (enemyHP+rand.nextInt(hordCount+1)+Math.log(hordCount)), (int)(this.enemySpeed+((float)rand.nextInt(hordCount+1))/10f),game,this.startToGoal));
			elpesdtimeToNextEenemy=0;
			timeToNextEnemy = rand.nextInt(2)+0.1f;
			if(hordEnemyCount>=hordEnemies){
				hordEnemyCount=0;
				hordCount++;
				hordEnemies = rand.nextInt(hordCount+10)+3;
				timeout = true;
				runTimoutCloc =false;
			}
		}
		}else{
			if(runTimoutCloc)
				elepcedTimebetwineHords+=Gdx.graphics.getDeltaTime();
			if(elepcedTimebetwineHords>= timeToNextHord){
				elepcedTimebetwineHords=0;
				timeout=false;
				MsgPrinter.print("Enemies inbound", 4);
			}
		}
		if(enemies.size()==0)
			runTimoutCloc = true;
		
		return true;
	}
	public void init(){
		this.startToGoal =  game.getLevel().getPathStartToFinish();
		this.pStat = game.getPlayerStats();
		
		
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
		
		spawn();
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
