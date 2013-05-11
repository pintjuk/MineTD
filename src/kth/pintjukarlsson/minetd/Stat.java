package kth.pintjukarlsson.minetd;

import com.badlogic.gdx.math.Vector2;

public class Stat {
	private int x, y;
	private TileType[] tiles = new TileType[4];
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Vector2 pos(){
		return new Vector2((float)x, (float)y);
	}
	
	public Stat(int x, int y, TileType t1, TileType t2,
		    					TileType t3, TileType t4){
		this.x=x;
		this.y=y;
		this.tiles[0] = t1;
		this.tiles[1] = t2;
		this.tiles[2] = t3;
		this.tiles[3] = t4;
	}
	
	public boolean containts(int x, int y){
		return (x==this.x||x==this.x+1)&&
				(y==this.y||y==this.y+1);
	}
	
	public TileType getFirstTileType(){
		return tiles[0];
	}
	
	public float getValue(){
		return (tiles[1].getValue()+
				 tiles[2].getValue()+
				 tiles[3].getValue())/3;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Stat))
			return false;
		return ((Stat)obj).x==x&&((Stat)obj).y==y;
	}
}
