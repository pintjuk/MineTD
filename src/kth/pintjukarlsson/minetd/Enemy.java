package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {
	
	private int hp;
	private int maxhp;
	
	private int speed;
	
	/**
	 * Creates a new enemy with full hitpoints at position x, y.
	 * The enemy moves at (speed) (units) per second.
	 * 
	 */
	public Enemy(int x, int y, int maxhp, int speed) {
		super(x, y);
		this.maxhp = maxhp;
		hp = maxhp;
		this.speed = speed;
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
	}
	/**
	 * {@inheritDoc Entity}
	 */
	@Override
	public void Draw() {
	}
}