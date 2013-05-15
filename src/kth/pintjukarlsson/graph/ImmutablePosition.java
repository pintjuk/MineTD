package kth.pintjukarlsson.graph;

import com.badlogic.gdx.math.Vector2;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class ImmutablePosition {
	private int x;
	private int y;
	private int hash;
	public ImmutablePosition(int x, int y){
		this.x = x;
		this.y=y;
		this.hash = x+y*10000;
	}
	public Vector2 getVec(){
		return new Vector2(x, y);
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
		if(!obj.getClass().equals(ImmutablePosition.class))
			return false;
		ImmutablePosition i = (ImmutablePosition)obj;
		if(x!= i.x)
			return false;
		if(y!=i.y)
			return false;
		return true;
	}
	
	public float distance(ImmutablePosition i){
		float dx = this.getX()-i.getX();
		float dy = this.getY()-i.getY();
		return (float)Math.sqrt( (double)dx*dx+dy*dy);
	}
	
	public float distanceSq(ImmutablePosition i){
		float dx = this.getX()-i.getX();
		float dy = this.getY()-i.getY();
		return dx*dx+dy*dy;
	}
	
	public float manhatanDistance(ImmutablePosition i){
		float dx = this.getX()>i.getX()?this.getX()-i.getX():i.getX()-this.getX();
		float dy = this.getY()>i.getY()?this.getY()-i.getY():i.getY()-this.getY();
		return dx+dy;
	}

	@Override
	public String toString() {
		return "("+x+", "+y+")";
	}

	


	
}
