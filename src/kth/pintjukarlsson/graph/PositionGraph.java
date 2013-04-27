package kth.pintjukarlsson.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A graph with a fixed number of 2d vertices implemented using adjacency table.
 * 
 * @author pintjuk
 * @version 0.1
 */
public class PositionGraph implements Graph {
	/**
	 * The map edges[v] contains the key-value pair (w, c) if there is an edge
	 * from v to w; c is the cost assigned to this edge. The maps may be null
	 * and are allocated only when needed.
	 */
	private final Hashtable<Integer, ArrayList<ImuteblePosition>> edges;
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
		edges = new Hashtable<Integer, ArrayList<ImuteblePosition>>(w*h+1);
		width=w;hight=h;
		
	}

	/**
	 * Add an edge without checking parameters.
	 */
	private void addEdge(ImuteblePosition from, ImuteblePosition to) {
		if(edges.get(from.hashCode())==null){
			edges.put(from.hashCode(), new ArrayList<ImuteblePosition>());
		}
		for(int i = 0; i<edges.get(from).size();i++){
			if(edges.get(from.hashCode()).get(i).equals(to))
				return;
		}
		numEdges++;
		edges.get(from.hashCode()).add(to);
		
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numVertices() {
		return width*hight;
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numEdges() {
		return numEdges;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public int degree(ImuteblePosition v) throws IllegalArgumentException {
	 /*   boolean[] links = new boolean[numVertices()];
		for(int i = 0; i<=vertices;i++){
			if(edges.get(i)!=null)
				if(edges.get(i)!=null)
					 for(Integer[] edg: edges.get(i)){
						 if(i==v&&!edg[2].equals(1)){
							 links[edg[0]]=true;
						 }
						 else
						 if(edg[0].equals(v)){
							 links[i]=true;
						 }
						 
					 }
		}
		int counts=0;
		for(boolean b: links){
			if(b)
				counts++;
		}
		return counts;*/
		return 0;
	}
	
	static class VertecisIt implements VertexIterator{
		int counter=0;
		ArrayList<ImuteblePosition> v;
		public VertecisIt(ArrayList<ImuteblePosition> vertecis){v=vertecis;}
		
		@Override
		public boolean hasNext() {
			if (v==null)
				return false;
			boolean t =counter<v.size();
			return t;
		}
		

		@Override
		public ImuteblePosition next() throws NoSuchElementException {
			if(!hasNext())
				throw new NoSuchElementException();
			return v.get(counter++);
		}
		
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public VertexIterator neighbors(ImuteblePosition v) {
		return new VertecisIt( edges.get(v.hashCode()));
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public boolean hasEdge(ImuteblePosition v, ImuteblePosition w) {
		if(edges.get(v.hashCode())==null)
			return false;
		for(ImuteblePosition edg: edges.get(v.hashCode())){
			 if(edg.equals(w))
				 return true;
		}
		return false;
	}

	
	
	/**
	 * checks that input invariants hold for add functions
	 * returns fals if invarants hold
	 */
	private boolean addInvariantchek(ImuteblePosition v, ImuteblePosition w){
		return 0>v.getX()&&v.getX()>width&&
				0>v.getY()&&v.getY()>hight&&
				0>w.getX()&&w.getX()>width&&
				0>w.getY()&&w.getY()>hight;
	}
	
	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(ImuteblePosition from, ImuteblePosition to) {
		if(addInvariantchek(from, to))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns");
		this.addEdge(from, to);
		
	}

	

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(ImuteblePosition v, ImuteblePosition w) {
		if(addInvariantchek(v,w))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns");
		this.addEdge(v, w);
		this.addEdge(w,v);
	}


	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void remove(ImuteblePosition from, ImuteblePosition to) {
		if(addInvariantchek(from, to))
			throw new IllegalArgumentException("from or to is outside of the range " +
					"of cornerns");
		rmEdge(from,to);
		
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void removeBi(ImuteblePosition v, ImuteblePosition w) {
		if(addInvariantchek(v, w))
			throw new IllegalArgumentException("from or to is outside of the range" +
					" of cornerns");
		rmEdge(v,w);
		rmEdge(w,v);
		
	}
	private void rmEdge(ImuteblePosition v, ImuteblePosition w){
		if(edges.get(v)==null)
			return;
		ImuteblePosition toremove=null;
		if(edges.get(v)!=null)
			for(ImuteblePosition i: edges.get(v.hashCode())){
				if(i.equals(w)){
					toremove=i;
					numEdges--;
				}
			}
		if(toremove==null)
			return;
		edges.get(v.hashCode()).remove(toremove);
		
	}
	

	/**
	 * Returns a string representation of this graph.
	 * 
	 * @return a String representation of this graph
	 */
	@Override
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
}