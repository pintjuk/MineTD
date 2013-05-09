package kth.pintjukarlsson.minetd;

import java.util.Random;

import kth.pintjukarlsson.graph.ImuteblePosition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.keyframe.Keyframe;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {

	private int hp;
	private int maxhp;

	private int speed;
	private AssetManager assetManager;
	private ImuteblePosition[] path;
	private int nextGoal = 0;
	private Animation walk;
	private Animation explode;
	private Texture walkcyle;

	private Texture explodetiles;
	private boolean facesLeft = true;
	private float internalTime = 0;
	private float explodeTimer = 0;
	private boolean playAnimation = false;

	private boolean isDead = false;
	private static Random rand = new Random(5454);

	public boolean isDead() {
		return isDead;
	}

	/**
	 * Creates a new enemy with full hitpoints at position x, y. The enemy moves
	 * at (speed) (units) per second.
	 * 
	 */
	public Enemy(int x, int y, int maxhp, int speed, MineTD g,
			ImuteblePosition[] path) {
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
	 * Increase the enemy's hitpoints by x. (x > 0) Cannot increase past max
	 * amount.
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
		Vector2 nextTarget = new Vector2((float) path[nextGoal].getX(),
				(float) path[nextGoal].getY());
		Vector2 dir = new Vector2(nextTarget);
		dir.sub(this.getPos());
		dir.nor();
		dir.scl(speed * Gdx.graphics.getDeltaTime());
		if (dir.x < 0)
			facesLeft = true;
		else
			facesLeft = false;
		this.translate(dir);
		if (nextTarget.epsilonEquals(getPos(), 0.1f)) {
			if ((nextGoal + 1) < path.length) {
				nextGoal++;
			}
		}

	}

	/**
	 * {@inheritDoc Entity}
	 */
	@Override
	public void Draw(SpriteBatch batch){
		if(walkcyle==null)
			init();
		internalTime+=Gdx.graphics.getDeltaTime();
		TextureRegion frame = walk.getKeyFrame(internalTime);
		if(!playAnimation){
		if(facesLeft) {
			batch.draw(frame, getPos().x, getPos().y, 1, 1);
			} else {
			batch.draw(frame, getPos().x+1, getPos().y, -1, 1);
		}
		}
		
		if(playAnimation){
			explodeTimer+=Gdx.graphics.getDeltaTime();
			 frame = explode.getKeyFrame(explodeTimer);
			 batch.draw(frame, getPos().x, getPos().y, 0.5f, 0.5f, 1, 1, 5, 5, 0);
			 if( explode.getKeyFrameIndex(explodeTimer)==8){
				 playAnimation=false;
				 isDead=true;
			 }
		}
	}

	private void init() {
		assetManager = getGame().getAssetManager();
		walkcyle = assetManager.get("data/pony.png", Texture.class);
		
		TextureRegion[] regions = TextureRegion.split(walkcyle, 39, 34)[rand.nextInt(3)];
		walk = new Animation(0.15f, regions[0], regions[1], regions[2],
				regions[3]);
		walk.setPlayMode(Animation.LOOP);
		explodetiles = assetManager.get("data/exp.png", Texture.class);
		TextureRegion[][] regionsExplosion = TextureRegion.split(explodetiles,
				32, 32);
		explode = new Animation(1 / 15f, regionsExplosion[0][0],
				regionsExplosion[0][1], regionsExplosion[0][2],
				regionsExplosion[1][0], regionsExplosion[1][1],
				regionsExplosion[1][2], regionsExplosion[2][0],
				regionsExplosion[2][1], regionsExplosion[2][2]);
		explode.setPlayMode(Animation.LOOP);
	}

	public void takeDamage(float damage) {
		hp-=damage;
		if(hp<0){
			playAnimation = true;
		}
		// TODO Auto-generated method stub

	}
}