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
	private final Hashtable<Integer, ArrayList<Integer[]>> edges;
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
		if (n < 0)
			throw new IllegalArgumentException("n = " + n);
		edges = new Hashtable<Integer, ArrayList<Integer[]>>(w*h+1);
		width=w;hight=h;
		
	}

	/**
	 * Add an edge without checking parameters.
	 */
	private void addEdge(int from, ImuteblePosition to, ImuteblePosition cost) {
		if(edges.get(from)==null){
			edges.put(from, new ArrayList<Integer[]>());
		}
		for(int i = 0; i<edges.get(from).size();i++){
			if(edges.get(from).get(i)[0]==to){
				edges.get(from).remove(i);
				numEdges--;
			}
		}
		int isBacklink=0;
		numEdges++;
		if(edges.get(to)!=null){
			for(int i = 0; i<edges.get(to).size();i++){
				if(edges.get(to).get(i)[0]==from){
					isBacklink=1;
					break;
				}
			}
		}
		
		
		edges.get(from).add(new Integer[]{to, cost, isBacklink});
		
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numVertices() {
		return vertices;
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
	public int degree(int v) throws IllegalArgumentException {
	    boolean[] links = new boolean[numVertices()];
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
		return counts;
	}
	
	static class VertecisIt implements VertexIterator{
		int counter=0;
		ArrayList<Integer[]> v;
		public VertecisIt(ArrayList<Integer[]> vertecis){v=vertecis;}
		@Override
		public boolean hasNext() {
			if (v==null)
				return false;
			boolean t =counter<v.size();
			return t;
		}

		@Override
		public int next() throws NoSuchElementException {
			if(!hasNext())
				throw new NoSuchElementException();
			return v.get(counter++)[0];
		}
		
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public VertexIterator neighbors(int v) {
		return new VertecisIt( edges.get(v));
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public boolean hasEdge(int v, int w) {
		if(edges.get(v)==null)
			return false;
		for(Integer[] edg: edges.get(v)){
			 if(edg[0]==w)
				 return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public int cost(int v, int w) throws IllegalArgumentException {
		if(edges.get(v)==null)
			return NO_COST;
		for(Integer[] edg: edges.get(v)){
			 if(edg[0]==w)
				 return edg[1];
		}
		return NO_COST;
	}
	
	/**
	 * checks that input invariants hold for add functions
	 * returns fals if invarants hold
	 */
	private boolean addInvariantchek(int v, int w){
		return 0>v&&v>vertices&&0>w&&w>vertices;
	}
	private boolean addInvariantchek(int v, int w, int c){
		return addInvariantchek(v, w)&&(c<0);
	}
	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(int from, int to) {
		if(addInvariantchek(from, to))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns");
		this.addEdge(from, to, NO_COST);
		
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(int from, int to, int c) {
		if(addInvariantchek(from, to, c))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns, ore c is les then 0");
		this.addEdge(from, to, c);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(int v, int w) {
		if(addInvariantchek(v,w))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns, ore c is les then 0");
		this.addEdge(v, w, NO_COST);
		this.addEdge(w,v, NO_COST);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(int v, int w, int c) {
		if(addInvariantchek(v,w, c))
			throw new IllegalArgumentException("from or to is outside of the range of cornerns, ore c is les then 0");
		this.addEdge(v, w, c);
		this.addEdge(w,v, c);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void remove(int from, int to) {
		if(addInvariantchek(from, to))
			throw new IllegalArgumentException("from or to is outside of the range " +
					"of cornerns");
		rmEdge(from,to);
		
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void removeBi(int v, int w) {
		if(addInvariantchek(v, w))
			throw new IllegalArgumentException("from or to is outside of the range" +
					" of cornerns");
		rmEdge(v,w);
		rmEdge(w,v);
		
	}
	private void rmEdge(int v, int w){
		if(edges.get(v)==null)
			return;
		boolean reasignBacklink=false;
		Integer[] toremove=null;
		if(edges.get(v)!=null)
			for(Integer[] i: edges.get(v)){
				if(i[0].equals(w)){
					toremove=i;
					numEdges--;
					if(!i[2].equals(1))
						reasignBacklink=true;
				}
			}
		edges.get(v).remove(toremove);
		if(reasignBacklink){
			if(edges.get(w)!=null)
				for(Integer[] i: edges.get(w)){
					if(i[0].equals(v)){
						i[2]=0;
					}
				}
		}
	}
	

	/**
	 * Returns a string representation of this graph.
	 * 
	 * @return a String representation of this graph
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
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
		return sb.toString();
	}
}