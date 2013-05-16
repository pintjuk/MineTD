package kth.pintjukarlsson.minetd.interfaces;

import java.util.ArrayList;

import kth.pintjukarlsson.minetd.Enemy;

import com.badlogic.gdx.math.Vector2;

public interface EnemyManagerService extends Drawable, Updatable {

	void init();

	Enemy getClosestTo(Vector2 v);

	ArrayList<Enemy> getEnemies();

}
