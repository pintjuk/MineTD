package kth.pintjukarlsson.graph;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class PositionGraphTest extends TestCase {	
	protected PositionGraph g0, g1, g5;
	/**
	 * Sets up the test fixture. Called before every test case method.
	 */
	@Override
	protected void setUp() {
		g0 = new PositionGraph(0, 0);
		g1 = new PositionGraph(1,1);
		g5 = new PositionGraph(2,2);
		g1.addBi(new ImmutablePosition(0, 0), new ImmutablePosition(0, 0));
		g5.addBi(new ImmutablePosition(0, 0), new ImmutablePosition(0, 1));
		g5.add(new ImmutablePosition(1, 0), new ImmutablePosition(1, 1));
	}

	/**
	 * Tears down the test fixture. Called after every test case method.
	 */
	@Override
	protected void tearDown() {
		g0 = null;
		g1 = null;
		g5 = null;
	}
	
	

	public void testNumVertices() {
		assertEquals(g0.numVertices(), 0);
		assertEquals(g1.numVertices(), 1);
		assertEquals(g5.numVertices(), 4);
	}
	

	public void testNumEdges() {
		assertEquals(g0.numEdges(), 0);
		assertEquals(g1.numEdges(), 1);
		assertEquals(g5.numEdges(), 3);
		g5.remove(new ImmutablePosition(1, 1), new ImmutablePosition(0, 0));
		assertEquals(g5.numEdges(), 3);
		g5.add(new ImmutablePosition(0, 0), new ImmutablePosition(0, 1));
		assertEquals(g5.numEdges(), 3);
		g5.add(new ImmutablePosition(1, 1), new ImmutablePosition(0, 0));
		assertEquals(g5.numEdges(), 4);
		g5.remove(new ImmutablePosition(0, 0), new ImmutablePosition(0, 1));
		assertEquals(g5.numEdges(), 3);
		g5.remove(new ImmutablePosition(0, 0), new ImmutablePosition(0, 1));
		assertEquals(g5.numEdges(), 3);
	}





	public void testNeighbors() {
		VertexIterator pi = g1.neighbors(new ImmutablePosition(0, 0));
		assertTrue(pi.hasNext());
		assertEquals(pi.next(), new ImmutablePosition(0, 0));
		pi = g5.neighbors(new ImmutablePosition(0, 0));
		assertEquals(pi.next(), new ImmutablePosition(0, 1));
		assertFalse(pi.hasNext());
		g5.add(new ImmutablePosition(0, 0), new ImmutablePosition(0, 0));
		g5.add(new ImmutablePosition(0, 0), new ImmutablePosition(0, 1));
		g5.add(new ImmutablePosition(0, 0), new ImmutablePosition(1, 0));
		g5.add(new ImmutablePosition(0, 0), new ImmutablePosition(1, 1));
		pi = g5.neighbors(new ImmutablePosition(0, 0));
		Set<ImmutablePosition> s = new HashSet<ImmutablePosition>();
		for (int i = 0; i < 4; i++) {
			s.add(pi.next());
		}
		assertEquals(s.size(), 4);
		assertTrue(s.contains(new ImmutablePosition(0, 0)));
		assertTrue(s.contains(new ImmutablePosition(0, 1)));
		assertTrue(s.contains(new ImmutablePosition(1, 0)));
		assertTrue(s.contains(new ImmutablePosition(1, 1)));
		assertFalse(pi.hasNext());
		try {
			pi.next();
			fail();
		} catch (NoSuchElementException e) {
		}
		pi = g5.neighbors(new ImmutablePosition(1, 1));
		assertFalse(pi.hasNext());
		try {
			pi.next();
			fail();
		} catch (NoSuchElementException e) {
		}
	}

	public void testHasEdge() {
		assertTrue(g1.hasEdge(new ImmutablePosition(0, 0), new ImmutablePosition(0, 0)));
		assertFalse(g5.hasEdge(new ImmutablePosition(0, 0), new ImmutablePosition(0, 0)));
		assertFalse(g5.hasEdge(new ImmutablePosition(1, 1), new ImmutablePosition(1, 0)));
		assertTrue(g5.hasEdge(new ImmutablePosition(0, 0), new ImmutablePosition(0, 1)));
		assertTrue(g5.hasEdge(new ImmutablePosition(0, 1), new ImmutablePosition(0, 0)));
		assertFalse(g5.hasEdge(new ImmutablePosition(1, 1), new ImmutablePosition(0, 1)));
		g5.add(new ImmutablePosition(1, 1), new ImmutablePosition(0, 1));
		assertTrue(g5.hasEdge(new ImmutablePosition(1, 1), new ImmutablePosition(0, 1)));
	}

}
