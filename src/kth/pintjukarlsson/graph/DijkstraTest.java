package kth.pintjukarlsson.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class DijkstraTest {

	@Test
	public void test() {
		PositionGraph g1= new PositionGraph(1, 1);
		PositionGraph g2 = new PositionGraph(2, 2);
		PositionGraph g3 = new PositionGraph(4, 4);
		
		g1.addBi(new ImuteblePosition(0, 0), new ImuteblePosition(0, 0));
		
		g2.addBi(new ImuteblePosition(0, 0), new ImuteblePosition(1, 0));
		g2.addBi(new ImuteblePosition(0, 0), new ImuteblePosition(1, 1));
		g2.addBi(new ImuteblePosition(0, 0), new ImuteblePosition(0, 1));
		g2.addBi(new ImuteblePosition(1, 1), new ImuteblePosition(0, 1));
		
		g3.addBi(new ImuteblePosition(0, 0), new ImuteblePosition(1, 0));
		g3.addBi(new ImuteblePosition(0, 0), new ImuteblePosition(1, 1));
		g3.addBi(new ImuteblePosition(0, 0), new ImuteblePosition(0, 1));
		g3.addBi(new ImuteblePosition(1, 1), new ImuteblePosition(0, 1));
		g3.addBi(new ImuteblePosition(1, 1), new ImuteblePosition(1, 2));
		
		assertEquals(Dijkstra.findPath(g1, new ImuteblePosition(0, 0), 
										   new ImuteblePosition(0, 0)).length, 
									   0);
		assertEquals(Dijkstra.findPath(g2, new ImuteblePosition(0, 0), 
				   new ImuteblePosition(1, 1)).length, 
			   2);
		
		assertEquals(Dijkstra.findPath(g3, new ImuteblePosition(0, 0), 
				   new ImuteblePosition(1, 2)).length, 
			   3);
		
	}

}
