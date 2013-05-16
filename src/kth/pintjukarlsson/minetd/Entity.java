package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Superclass for units (enemies) and buildings.
 *
 */
public abstract class Entity {
	
	private Vector2 pos;
	private Texture texture;
	private MineTD game;
	
	protected MineTD getGame(){
		return game;
	}
	
	public Texture getTexture() {
		return texture;
	}

	protected void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Entity(int x, int y, MineTD g) {
		pos = new Vector2(x, y);
		game = g;
	}

	public Vector2 getPos() {
		return pos;
	}
	public void setPos(Vector2 p){
		pos = p;
	}
	public void translate(Vector2 p){
		pos.add(p);
	}
	/**
	 * Updates the internal state of this entity.
	 */
	abstract void Update();
	
	/**
	 * Draws the graphical representation of this entity.
	 */
	public void Draw(SpriteBatch batch){
		batch.draw(texture, pos.x, pos.y, 1f, 1f);
	}
}