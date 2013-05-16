package kth.pintjukarlsson.minetd.interfaces;

import java.util.ArrayList;

import kth.pintjukarlsson.minetd.Enemy;

import com.badlogic.gdx.math.Vector2;
/**
 * 
 * @author pintjuk
 *
 */
public interface EnemyManagerService extends Drawable, Updatable {
	/**
	 * 
	 */
	void init();
	/**
	 * 
	 * @param v
	 * @return
	 */
	Enemy getClosestTo(Vector2 v);
	/**
	 * 
	 * @return
	 */
	ArrayList<Enemy> getEnemies();

}
