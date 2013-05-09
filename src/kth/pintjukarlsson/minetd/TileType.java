package kth.pintjukarlsson.minetd;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum TileType {
	GRAVEL(2,7, 1.2f),
	DIRT(3,8, 1.1f),
	BEDROCK(3,3, 15),
	SAND(4,3, 5),
	STONE(5,5, 6),
	LAVA(6,6, 20);
	private int index;
	private int buildIndex;
	private float value;
	
	private static final Map<Integer,TileType> lookup 
    = new HashMap<Integer,TileType>();
	
	TileType(int i, int bi ,float v){
		index=i; value =v; buildIndex = bi;
	}

	public int getIndex() {
		return index;
	}

	public float getValue() {
		return value;
	}


	static {
	     for(TileType s : EnumSet.allOf(TileType.class)){
	          lookup.put(s.getIndex(), s);
	          if(!lookup.containsKey(s.getBuildIndex()))
	        	  lookup.put(s.getBuildIndex(), s);
	     }
	}
	
	public int getBuildIndex() {
		return buildIndex;
	}

	

	public static TileType get(int index) { 
	      return lookup.get(index); 
	 }
	
	
}
