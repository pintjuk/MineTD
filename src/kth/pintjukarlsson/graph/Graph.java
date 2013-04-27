package kth.pintjukarlsson.graph;

/**
 * A graph with a fixed number of vertices. The vertices are numbered from 0 to
 * n-1, were n is the number of vertices in the graph. Edges may be added or
 * removed from the graph. An edge may have an optional non-negative cost.
 * 
 * @author Stefan Nilsson
 * @version 2013-01-01
 */

public interface Graph {
	/**
	 * An edge with no cost has this value.
	 */
	int NO_COST = -1;

	/**
	 * Returns the number of vertices in this graph.
	 * 
	 * @return the number of vertices in this graph
	 */
	int numVertices();

	/**
	 * Returns the number of edges in this graph.
	 * 
	 * @return the number of edges in this graph
	 */
	int numEdges();

	/**
	 * Returns the degree of vertex v.
	 * 
	 * @param v
	 *            vertex
	 * @return the degree of vertex v
	 * @throws IllegalArgumentException
	 *             if v is out of range
	 */
	int degree(ImuteblePosition v) throws IllegalArgumentException;

	/**
	 * Returns an iterator of vertices adjacent to v.
	 * 
	 * @param v
	 *            vertex
	 * @return an iterator of vertices adjacent to v
	 * @throws IllegalArgumentException
	 *             if v is out of range
	 */
	VertexIterator neighbors(ImuteblePosition v) throws IllegalArgumentException;

	/**
	 * Returns true if there is an edge from v to w.
	 * 
	 * @param v
	 *            vertex
	 * @param w
	 *            vertex
	 * @return true if there is an edge from v to w.
	 * @throws IllegalArgumentException
	 *             if v or w are out of range
	 */
	boolean hasEdge(ImuteblePosition v, ImuteblePosition w) throws IllegalArgumentException;



	/**
	 * Inserts a directed edge. (No edge cost is assigned.)
	 * 
	 * @param from
	 *            vertex
	 * @param to
	 *            vertex
	 * @throws IllegalArgumentException
	 *             if from or to are out of range
	 */
	void add(ImuteblePosition from, ImuteblePosition to) throws IllegalArgumentException;

	

	/**
	 * Inserts two edges between v and w. (No edge cost is assigned.)
	 * 
	 * @param v
	 *            vertex
	 * @param w
	 *            vertex
	 * @throws IllegalArgumentException
	 *             if v or w are out of range
	 */
	void addBi(ImuteblePosition v, ImuteblePosition w) throws IllegalArgumentException;

	

	/**
	 * Removes the edge.
	 * 
	 * @param from
	 *            vertex
	 * @param to
	 *            vertex
	 * @throws IllegalArgumentException
	 *             if from or to are out of range
	 */
	void remove(ImuteblePosition from, ImuteblePosition to) throws IllegalArgumentException;

	/**
	 * Removes the edges between v and w.
	 * 
	 * @param v
	 *            vertex
	 * @param w
	 *            vertex
	 * @throws IllegalArgumentException
	 *             if v or w are out of range
	 */
	void removeBi(ImuteblePosition v, ImuteblePosition w) throws IllegalArgumentException;
}
