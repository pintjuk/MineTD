package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface UIService
{
  public boolean needsGL20( );    
  public void init( );
  public void resume( );
  public void Draw();
  public void resize(int width, int height);
  public void pause( );
  public void dispose( );
}