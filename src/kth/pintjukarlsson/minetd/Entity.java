package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
	
public abstract class Entity {
	
	private Vector2 pos;
	private Texture texture;
	
	public Entity(int x, int y) {
		pos = new Vector2(x, y);
	}

	public Vector2 getPos() {
		return pos;
	}
	/**
	 * Updates the internal state of this entity.
	 */
	abstract void Update();
	/**
	 * Draws the graphical representation of this entity.
	 */
	abstract void Draw();
}