package kth.pintjukarlsson.minetd;

public class Enemy extends Entity {
	
	private int hp;
	private int maxhp;
	
	private int speed;
	
	public Enemy(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	// Health setters/getters and alive check
	public void damage(int x) {
		hp -= x;
	}	
	public void heal(int x) {
		hp += x;
	}
	public void setHealth(int x) {
		hp = x;
	}
	public void setMaxHealth(int x) {
		maxhp = x;
	}
	public int getHealth() {
		return hp;
	}
	public int getMaxHealth() {
		return maxhp;
	}
	public boolean isAlive() {
		return (hp > maxhp);
	}
	
	// Speed setters/getters
	public void setSpeed(int x) {
		speed = x;
	}
	public int getSpeed() {
		return speed;
	}
}