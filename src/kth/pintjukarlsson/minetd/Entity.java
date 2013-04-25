package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
	
public class Entity {
	
	private Vector2 pos;
	private Texture texture;
	
	public Entity(int x, int y) {
		pos = new Vector2(x, y);
	}
	
	
	public Vector2 getPos() {
		return pos;
	}
	
}
