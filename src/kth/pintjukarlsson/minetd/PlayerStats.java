package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PlayerStats {
	private int power=0;
	private int gravle=0;
	private int dirt=0;
	private int stone=0;
	private TileType[] tilesforselect= new TileType[]{TileType.DIRT, TileType.GRAVEL, TileType.STONE};
	private BitmapFont font;
	public PlayerStats(){
		font= new BitmapFont();
	}
	public void setSelect(){
		
	}
	public TileType popSelected(){
		return null;
	}
	public void Draw(SpriteBatch batch){
		font.draw(batch, "hej", 10, 10);
		
	}

}
