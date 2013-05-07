package kth.pintjukarlsson.minetd;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum TileType {
	STONE(1, 10),
	DIRT(2, 2),
	BEDROCK(3, 15),
	SAND(4, 5),
	GRAVEL(5, 6),
	LAVA(6, 20);
	private int index;
	private float value;
	
	private static final Map<Integer,TileType> lookup 
    = new HashMap<Integer,TileType>();
	
	TileType(int i, float v){
		index=i; value =v;
	}

	public int getIndex() {
		return index;
	}

	public float getValue() {
		return value;
	}


	static {
	     for(TileType s : EnumSet.allOf(TileType.class))
	          lookup.put(s.getIndex(), s);
	}
	
	public static TileType get(int index) { 
	      return lookup.get(index); 
	 }
	
	
}
