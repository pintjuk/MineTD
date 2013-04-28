package kth.pintjukarlsson.graph;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

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
	
	public float distance(ImuteblePosition i){
		float dx = this.getX()-i.getX();
		float dy = this.getY()-i.getY();
		return (float)Math.sqrt( (double)dx*dx+dy*dy);
	}
	
	public float distanceSq(ImuteblePosition i){
		float dx = this.getX()-i.getX();
		float dy = this.getY()-i.getY();
		return dx*dx+dy*dy;
	}
	
	public float manhatanDistance(ImuteblePosition i){
		float dx = this.getX()>i.getX()?this.getX()-i.getX():i.getX()-this.getX();
		float dy = this.getY()>i.getY()?this.getY()-i.getY():i.getY()-this.getY();
		return dx+dy;
	}

	@Override
	public String toString() {
		return "("+x+", "+y+")";
	}

	


	
}
