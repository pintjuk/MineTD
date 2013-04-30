package kth.pintjukarlsson.minetd;

import kth.pintjukarlsson.debugdraw.LinkDebug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MouseInputAdapter extends InputAdapter {
	private OrthographicCamera camera;
	private GameMap level;
	private MineTD game;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	
	private boolean initilized= false;

	public MouseInputAdapter (MineTD g) {
		this.game = g;
	}
	
	public void init(){
		this.camera = game.getCamera();
		this.level = game.getLevel();
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		camera.unproject(curr.set(x, y, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			camera.unproject(delta.set(last.x, last.y, 0));
			delta.sub(curr);
			camera.position.add(delta.x, delta.y, 0);
		}
		last.set(x, y, 0);
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		last.set(-1, -1, -1);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		Vector3 m = new Vector3();
		camera.unproject(m.set(screenX, screenY, 0));
		
		level.setTile(1, (int)(m.x*2), (int)(m.y*2));
		level.resetDebugDraw();
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		camera.unproject(testPosition.set(screenX, screenY, 0));
		testPosition.x =((float)((int)testPosition.x));
		testPosition.y =((float)((int)testPosition.y));
		return super.mouseMoved(screenX, screenY);
	}
	public void resize(int x, int y){
		
	}
	////////////////////////////////
	Vector3 testPosition= new Vector3();
	LinkDebug lol;
	public void testDraw(int w, int h){
		
		System.out.println(testPosition);
		/**lol = new LinkDebug((testPosition.x*camera.zoom/w)
			,(Gdx.graphics.getHeight()-testPosition.y)*camera.zoom/h, 0, 0, 50, 0);
		lol.Draw();**/
		
		new LinkDebug(testPosition.x,testPosition.y, testPosition.x+1,testPosition.y+1, 50, 255).Draw();
	}
	///////////////////////////////
}
