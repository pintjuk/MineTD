package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.ApplicationListener;

public interface UIService extends ApplicationListener
{
  public boolean needsGL20( );    
  public void create( );
  public void resume( );
  public void render( );
  public void resize(int width, int height);
  public void pause( );
  public void dispose( );
}