package kth.pintjukarlsson.graph;

public class ImuteblePosition {
	private int x;
	private int y;
	private int hash;
	public ImuteblePosition(int x, int y){
		this.x = x;
		this.y=y;
		this.hash = x+y*10000;
	}

	public int getX() {
		return x;
	}

	

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return this.hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(!obj.getClass().equals(ImuteblePosition.class))
			return false;
		ImuteblePosition i = (ImuteblePosition)obj;
		if(x!= i.x)
			return false;
		if(y!=i.y)
			return false;
		return true;
	}

	


	
}
