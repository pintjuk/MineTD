package kth.pintjukarlsson.minetd;

import java.util.ArrayList;
import java.util.Random;

import kth.pintjukarlsson.graph.ImmutablePosition;
import kth.pintjukarlsson.minetd.interfaces.EnemyManagerService;
import kth.pintjukarlsson.minetd.interfaces.GameService;
import kth.pintjukarlsson.minetd.interfaces.PlayerStatsService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EnemyManager implements EnemyManagerService {

	private final static int MAX_ENEMIES = 30;
	private int[] spawnPos;
	
	private ArrayList<Enemy> enemies;
	
	private int numEnemies;
	private int enemyHP;
	private int enemySpeed;
	
	private int hordCount=0;
	private float timeToNextEnemy = 1;
	private float elpesdtimeToNextEenemy=0;
	
	private int hordeEnemyCount = 0;
	private int hordeEnemies = 3;
	private float hordeElapsedTime = 0;
	private float timeToNextHorde = 6;
	
	
	private GameService game;
	private PlayerStatsService playerStats;
	private ImmutablePosition[] startToGoal;
	private Random rand = new Random(545454545);
	private boolean timeout;
	private boolean runTimeoutClock; 
	/**
	 *  Creates a new enemy manager that spawns stuff at the location x, y.
	 *  It starts out with some default values, including half the maximum number of enemies.
	 */
	public EnemyManager(int x, int y, GameService g) {
		spawnPos = new int[] {x, y};
		enemies = new ArrayList<Enemy>();
		numEnemies = MAX_ENEMIES/2;
		startToGoal = new ImmutablePosition[]{ new ImmutablePosition(0, 0),
											  new ImmutablePosition(1, 2),
											  new ImmutablePosition(1, 4)};
		game = g;
		enemyHP = 12;
		enemySpeed = 2;
	}

	
	
	
	private boolean spawn() {
		if(!timeout){
			elpesdtimeToNextEenemy+=Gdx.graphics.getDeltaTime();
		if(elpesdtimeToNextEenemy>timeToNextEnemy)
		{
			hordeEnemyCount++;
			enemies.add(new Enemy(startToGoal[0].getX(), startToGoal[0].getY(), (int) (enemyHP+rand.nextInt(hordCount+1)+Math.log(hordCount)), (int)(this.enemySpeed+((float)rand.nextInt(hordCount+1))/10f),game,this.startToGoal));
			elpesdtimeToNextEenemy=0;
			timeToNextEnemy = rand.nextInt(2)+0.1f;
			if(hordeEnemyCount>=hordeEnemies){
				hordeEnemyCount=0;
				hordCount++;
				hordeEnemies = rand.nextInt(hordCount+10)+3;
				timeout = true;
				runTimeoutClock =false;
			}
		}
		}else{
			if(runTimeoutClock)
				hordeElapsedTime+=Gdx.graphics.getDeltaTime();
			if(hordeElapsedTime>= timeToNextHorde){
				hordeElapsedTime=0;
				timeout=false;
				MsgPrinter.print("Enemies inbound", 4);
			}
		}
		if(enemies.size()==0)
			runTimeoutClock = true;
		
		return true;
	}
	@Override
	public void init(){
		this.startToGoal =  game.getLevel().getPathStartToFinish();
		this.playerStats = game.getPlayerStats();
		
		
	}
	// Runs the update() method for each existing enemy
	@Override
	public void update() {
		ArrayList<Enemy> torm = new ArrayList<Enemy>();
		for (Enemy e : enemies){
			e.Update();
			if(e.isDead()){
				playerStats.pushEnergy();
				torm.add(e);
			}
			if(e.getPos().epsilonEquals(startToGoal[startToGoal.length-1].getVec(),0.4f)){
				playerStats.reduseLives();
				torm.add(e);
			}
		}
		for (Enemy e : torm)
			enemies.remove(e);
		
		spawn();
	}
	
	@Override
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

	// Runs the Draw() method for each existing enemy
	@Override
	public void draw(SpriteBatch batch) {
		for (Enemy e : enemies)
			e.Draw(batch);
	}
}
