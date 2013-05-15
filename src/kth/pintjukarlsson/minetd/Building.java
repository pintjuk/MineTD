package kth.pintjukarlsson.minetd;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Building extends Entity {
	private static class Bullet{
		Vector2 pos;
		Vector2 dir;
		private final float speed=20;
		public Bullet(Vector2 pos, Vector2 dir) {
			this.pos=pos; this.dir=dir;
		}
	}
	ArrayList<Stat> stats = new ArrayList<>();
	ArrayList<Bullet> bullets=new ArrayList<>();
	// Creates a new Building object at x, y
	final private float defoultFireRate= 1;
	final private int defoultRange= 4;
	final private float defoultDamage=1;
	private static Animation fire;
	private static Texture firetile;
	private TextureRegion bullet;
	private AssetManager assetManager;
	private EnemyManager enemies;
	private Vector2 ameDir=Vector2.X;
	private float internalTime=0;
	private float firetime=0;
	private float scale=1;
	private boolean playfire=false;
	private boolean bFire=false;
	
	public Building(int x, int y, MineTD g) {
		super(x, y, g);
	}
	
	/**
	 * {@inheritDoc Entity}
	 */
	@Override
	public void Update() {
		if (enemies == null)
			init();

		Enemy target = enemies.getClosestTo(getPos());
		if (target != null) {
			ameDir = new Vector2(target.getPos()).sub(getPos());
			if(target.getPos().epsilonEquals(this.getPos(), this.getRange()))
				bFire = true;
			else 
				bFire=false;
			
		} else {
			bFire = false;
		}

		// fier
		if (this.bFire) {
			firetime += Gdx.graphics.getDeltaTime();
			if (firetime > 1 / getFireRate()) {
				firetime = 0;
				fire();
			}
		}

		// Update Bullets
		ArrayList<Bullet> torm = new ArrayList<>();
		for (Bullet b : bullets) {
			b.pos.add(new Vector2(b.dir).scl(Gdx.graphics.getDeltaTime()
					* b.speed));
			for (Enemy e : enemies.getEnemies()) {
				if (new Rectangle(e.getPos().x, e.getPos().y, 1, 1).contains(
						b.pos.x, b.pos.y)) {
					e.takeDamage(this.getDamage());
					e.setPos(e.getPos().sub(b.dir.nor().scl(-0.3f)));
					torm.add(b);
				}
			}
			if (b.pos.x > 100 || b.pos.x < 0 || b.pos.y > 100 || b.pos.y < 0)
				torm.add(b);

		}
		for (Bullet b : torm) {
			bullets.remove(b);
		}
	}
	private void fire() {
		playfire=true;
		bullets.add(new Bullet(new Vector2(getPos()), new Vector2(this.ameDir.nor())));
	}

	/**
	 * {@inheritDoc Entity}
	 */
	@Override
	public void Draw(SpriteBatch batch) {
		
		if(fire==null)
			init();
		if(playfire)
			internalTime+=Gdx.graphics.getDeltaTime();
		TextureRegion frame = fire.getKeyFrame(internalTime);
		if(fire.getKeyFrameIndex(internalTime)==2){
			internalTime=0;
			playfire=false;
		}
		batch.draw(frame,getPos().x, getPos().y, 0.4f, 0.4f, 1.2f, 0.9f, scale, scale,((float)Math.atan2(-ameDir.y,-ameDir.x) +(float)Math.PI)*180/(float)Math.PI);
		for(Bullet b: bullets)
			batch.draw(bullet,b.pos.x, b.pos.y, 0.1f, 0.3f, 0.2f,0.6f, scale, scale,((float)Math.atan2(-b.dir.y,-b.dir.x) +(float)Math.PI/2)*180/(float)Math.PI);
		
	}
	
	private void init() {
		assetManager = getGame().getAssetManager();
		enemies = getGame().getEnemiesManager();
		firetile=assetManager.get("data/gun.png", Texture.class);
		TextureRegion[] regions []= TextureRegion.split(firetile, 18, 10);
		TextureRegion[] bulletregions []= TextureRegion.split(firetile, 2, 8);
		fire = new Animation(0.05f, regions[0][0], regions[1][0], regions[2][0]);
		bullet = bulletregions[bulletregions.length-1][bulletregions[bulletregions.length-1].length-1];
		fire.setPlayMode(Animation.LOOP);
	}
	private void updatePos(){
		float x = 0;
		float y = 0;
		for(Stat s: this.stats){
			x += s.getX();
			y += s.getY();
			
		}
		x/=(float)stats.size();
		y/=(float)stats.size();
		
		setPos( new Vector2(0.5f+x,-0.5f+ y));
		scale = 1+(stats.size()-1)/5;
	}
	public float getFireRate(){
		return defoultFireRate*getGetCombinedPowerOfAllStatsOfType(TileType.STONE);
	}
	public float getRange(){
		return defoultRange*getGetCombinedPowerOfAllStatsOfType(TileType.GOLD);
	}
	
	public float getDamage(){
		return defoultDamage*getGetCombinedPowerOfAllStatsOfType(TileType.IRON);
	}
	
	public float getGetCombinedPowerOfAllStatsOfType(TileType t){
		float total=1;
		for(Stat s: this.stats)
			if(s.getFirstTileType().equals(t)){
				total*=s.getValue();
			}
		
		return total;
	}
	public boolean hasStat(int x, int y){
		for(Stat s: this.stats){
			if(s.containts(x, y))
				return true;
		}
		return false;
	}
	
	public void rmStat(int x, int y){
		Stat torm = null;
		for(Stat s: this.stats){
			if(s.containts(x, y))
				torm=s;
		}
		if(torm!=null)
			stats.remove(torm);
	}
	public void addStat(Stat s){
		stats.add(s);
		updatePos();
	}

	public void removeStatsThahHave(int x, int y) {
		ArrayList<Stat> torm = new ArrayList<>();
		for(Stat s: stats){
			if(s.containts(x, y)){
				torm.add(s);
			}
		}
		for(Stat s: torm)
			stats.remove(s);
	}
	
}
