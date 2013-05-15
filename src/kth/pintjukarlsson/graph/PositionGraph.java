package kth.pintjukarlsson.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import kth.pintjukarlsson.debugdraw.LinkDebug;

/**
 * A graph with a fixed number of 2d vertices implemented using adjacency table.
 * 
 * @author pintjuk
 * @version 0.1
 */
public class PositionGraph {
	/**
	 * The map edges[v] contains the key-value pair (w, c) if there is an edge
	 * from v to w; c is the cost assigned to this edge. The maps may be null
	 * and are allocated only when needed.
	 */
	private final Hashtable<Integer, ArrayList<ImmutablePosition>> edges;
	private float[][] cost;
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHight(int hight) {
		this.hight = hight;
	}


	private int width;
	private int hight;
	private int numEdges=0;

	/**
	 * Constructs a HashGraph with n vertices and no edges. Time complexity:
	 * O(n)
	 * 
	 * @throws IllegalArgumentException
	 *             if n < 0
	 */
	public PositionGraph(int w, int h) {
		if (w < 0)
			throw new IllegalArgumentException("w = " + w);
		if (h < 0)
			throw new IllegalArgumentException("h = " + h);
		edges = new Hashtable<Integer, ArrayList<ImmutablePosition>>(w*h+1);
		width=w;hight=h;
		cost=new float[h*w][w*h];
		
	}

	public int getWidth() {
		return width;
	}

	
	public int getHight() {
		return hight;
	}

	

	/**
	 * Add an edge without checking parameters.
	 */
	private void addEdge(ImmutablePosition from, ImmutablePosition to) {
		if(edges.get(toKey(from))==null){
			edges.put(toKey(from), new ArrayList<ImmutablePosition>());
		}
		for(int i = 0; i<edges.get(toKey(from)).size();i++){
			if(edges.get(toKey(from)).get(i).equals(to))
				return;
		}
		numEdges++;
		edges.get(toKey(from)).add(to);
		this.cost[from.getX()+from.getY()][to.getX()+to.getY()]= from.distance(to);
		
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	
	public int numVertices() {
		return width*hight;
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	
	public int numEdges() {
		return numEdges;
	}

	
	
	static class VertecisIt implements VertexIterator{
		int counter=0;
		ArrayList<ImmutablePosition> v;
		public VertecisIt(ArrayList<ImmutablePosition> vertecis){v=vertecis;}
		
		 
		public boolean hasNext() {
			if (v==null)
				return false;
			boolean t =counter<v.size();
			return t;
		}
		

		@Override
		public ImmutablePosition next() throws NoSuchElementException {
			if(!hasNext())
				throw new NoSuchElementException();
			return v.get(counter++);
		}
		
	}

	/**
	 * {@inheritDoc Graph}
	 */
	 
	public VertexIterator neighbors(ImmutablePosition v) {
		return new VertecisIt( edges.get(toKey(v)));
	}

	/**
	 * {@inheritDoc Graph}
	 */
	 
	public boolean hasEdge(ImmutablePosition v, ImmutablePosition w) {
		if(edges.get(toKey(v))==null)
			return false;
		for(ImmutablePosition edg: edges.get(toKey(v))){
			 if(edg.equals(w))
				 return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	 
	public float cost(ImmutablePosition from, ImmutablePosition to) {
		if(addInvariantchek(from, to))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns");
		return this.cost[from.getX()+from.getY()][to.getX()+to.getY()];
		
	}

	
	
	/**
	 * checks that input invariants hold for add functions
	 * returns fals if invarants hold
	 */
	private boolean addInvariantchek(ImmutablePosition v, ImmutablePosition w){
		return 0>v.getX()&&v.getX()>width&&
				0>v.getY()&&v.getY()>hight&&
				0>w.getX()&&w.getX()>width&&
				0>w.getY()&&w.getY()>hight;
	}
	
	/**
	 * {@inheritDoc Graph}
	 */
	 
	public void add(ImmutablePosition from, ImmutablePosition to) {
		if(addInvariantchek(from, to))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns");
		this.addEdge(from, to);
		
	}

	

	/**
	 * {@inheritDoc Graph}
	 */
	public void addBi(ImmutablePosition v, ImmutablePosition w) {
		if(addInvariantchek(v,w))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns");
		this.addEdge(v, w);
		this.addEdge(w,v);
	}


	/**
	 * {@inheritDoc Graph}
	 */
	public void remove(ImmutablePosition from, ImmutablePosition to) {
		if(addInvariantchek(from, to))
			throw new IllegalArgumentException("from or to is outside of the range " +
					"of cornerns");
		rmEdge(from,to);
		
	}

	/**
	 * {@inheritDoc Graph}
	 */
	public void removeBi(ImmutablePosition v, ImmutablePosition w) {
		if(addInvariantchek(v, w))
			throw new IllegalArgumentException("from or to is outside of the range" +
					" of cornerns");
		rmEdge(v,w);
		rmEdge(w,v);
		
	}
	private void rmEdge(ImmutablePosition v, ImmutablePosition w){
		if(edges.get(toKey(v))==null)
			return;
		ImmutablePosition toremove=null;
		for(ImmutablePosition i: edges.get(toKey(v))){
			if(i.equals(w)){
				toremove=i;
				numEdges--;
			}
		}
		if(toremove==null)
			return;
		edges.get(toKey(v)).remove(toremove);
		
	}
	

	/**
	 * Returns a string representation of this graph.
	 * 
	 * @return a String representation of this graph
	 */
	public String toString() {
		/*StringBuilder sb = new StringBuilder("{");
		boolean first =true;
		for(int i = 0; i<vertices;i++){
			if(edges.get(i)==null)
				continue;
			for(Integer[] edg: edges.get(i)){
				if(edg[0].equals(1))
					 continue;
				 if(first)
					 first=false;
				 else
					 sb.append(", ");
				 
				 sb.append("("+i+","+edg[0]+
						 (edg[1]!=NO_COST?(","+edg[1]):
								 			"")+")");
			}
			
			 
			 
		}
		sb.append("}");
		return sb.toString();*/
		return super.toString();
	}
	int toKey(int x, int y){
		return x+(y*(width+1));
	}
	int toKey(ImmutablePosition x){
		return toKey(x.getX(), x.getY());
	}
	ImmutablePosition keyToPos(int key){
		int x = key%width;
		int y = (key-x)/width;
		return new ImmutablePosition(x,y);
	}
	

	private ArrayList<LinkDebug> links = new ArrayList<LinkDebug>();
	public void reBuildDibugImg(){
		for(LinkDebug e: links){
			e.dispole();
		}
		links.clear();
		for(int y= 0;y<getHight();y++){
			for(int x= 0;x<getWidth();x++){
				ArrayList<ImmutablePosition> nexts = edges.get(toKey(new ImmutablePosition(x, y)));
				if(nexts==null)
					continue;
				for(ImmutablePosition b: nexts)
					this.links.add(new LinkDebug (x+0.5f, y+0.5f, b.getX()+0.4f, b.getY()+0.4f, 200));
			}
		}
		/*for(Integer key: edges.keySet()){
			ArrayList<ImuteblePosition> vetecis = edges.get(key.intValue());
			ImuteblePosition vertex1 = keyToPos(key.intValue());
			for(ImuteblePosition vertex2: vetecis){
				links.add(new LinkDebug (vertex1.getX(), vertex1.getY(), vertex2.getX(), vertex2.getY(), 150));
			}
		}*/
	}
	public void removeAllLinksTo(ImmutablePosition to){
		 if(edges.get(toKey(to))!=null){
			 numEdges-=edges.get(toKey(to)).size();
			 edges.remove(toKey(to));
		 }
		 for(int y= 0;y<getHight();y++){
			for(int x= 0;x<getWidth();x++){
				ArrayList<ImmutablePosition> nexts = edges.get(toKey(new ImmutablePosition(x, y)));
				ArrayList<ImmutablePosition> toremove= new ArrayList<>();
				if(nexts==null)
					continue;
				for(ImmutablePosition b: nexts){
					if(b.equals(to))
						toremove.add(b);
				}
				for(ImmutablePosition b: toremove){
					nexts.remove(b);
				}
			}
		}
	}
	public void DibugDraw(){
		
		for(LinkDebug d: links){
			d.Draw();
		}
	}
}