package kth.pintjukarlsson.minetd;

import kth.pintjukarlsson.graph.ImuteblePosition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {
	
	private int hp;
	private int maxhp;
	
	private int speed;
	private AssetManager assetManager;
	private ImuteblePosition[] path;
	private int nextGoal=0;
	
	/**
	 * Creates a new enemy with full hitpoints at position x, y.
	 * The enemy moves at (speed) (units) per second.
	 * 
	 */
	public Enemy(int x, int y, int maxhp, int speed, MineTD g, ImuteblePosition[] path) {
		super(x, y, g);
		this.maxhp = maxhp;
		hp = maxhp;
		this.speed = speed;
		this.path = path;
	}
	// Health setters/getters and alive check
	/**
	 * Decrease the enemy's hitpoints by x. (x > 0)
	 */
	public void damage(int x) {
		if (x > 0)
			hp -= x;
	}	
	/**
	 * Increase the enemy's hitpoints by x. (x > 0)
	 * Cannot increase past max amount.
	 */
	public void heal(int x) {
		if (x > 0)
			hp += x;
		if (hp > maxhp)
			hp = maxhp;
	}
	/**
	 * Set hitpoints to x.
	 */
	public void setHealth(int x) {
		hp = x;
	}
	/**
	 * Set maximum hitpoints to x.
	 */
	public void setMaxHealth(int x) {
		maxhp = x;
	}
	/**
	 * Returns the current number of hitpoints.
	 */
	public int getHealth() {
		return hp;
	}
	/**
	 * Returns the maximum number of hitpoints.
	 */
	public int getMaxHealth() {
		return maxhp;
	}
	/**
	 * 
	 */
	public boolean isAlive() {
		return (hp > 0);
	}
	// Speed setters/getters
	/**
	 * Sets the movement speed of the unit, in terms of (TODO)
	 */
	public void setSpeed(int x) {
		speed = x;
	}
	/**
	 * Returns the enemy's movement speed.
	 */
	public int getSpeed() {
		return speed;
	}
	
	// Update and draw methods
	/**
	 * {@inheritDoc Entity}
	 */
	@Override
	public void Update() {
		Vector2 nextTarget = new Vector2((float)path[nextGoal].getX(),(float)path[nextGoal].getY());
		Vector2 dir = new Vector2(nextTarget);
		dir.sub(this.getPos());
		dir.nor();
		dir.scl(speed*Gdx.graphics.getDeltaTime());
		this.translate(dir);
		if(nextTarget.epsilonEquals(getPos(), 0.1f)){
			if((nextGoal+1)<path.length){
				nextGoal++;
			}
		}
		
	}
	/**
	 * {@inheritDoc Entity}
	 */
	@Override
	public void Draw(SpriteBatch batch){
		if(getTexture()==null)
			init();
		super.Draw(batch);
	}
	private void init()  {
		assetManager = getGame().getAssetManager();
		setTexture(assetManager.get("data/pony.png", Texture.class));
	}
}