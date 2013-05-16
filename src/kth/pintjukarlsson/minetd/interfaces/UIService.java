package kth.pintjukarlsson.minetd.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface UIService extends Updatable, Drawable
{
	/**
	 * 
	 * @return
	 */
	public boolean needsGL20( );
	/**
	 * 
	 */
	public void init( );
	/**
	 * 
	 */
	public void resume( );
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void resize(int width, int height);
	/**
	 * 
	 */
	public void pause( );
	/**
	 * 
	 */
	public void dispose( );
}