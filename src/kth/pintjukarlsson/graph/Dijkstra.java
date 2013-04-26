package kth.pintjukarlsson.graph;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Dijkstra {
	/**
	 * Returns an int array representing the cheapest path from "from" to "to".
	 * 
	 * Returns a ziro size array if no path exists, 
	 * for example if from and to are parts of seperet components
	 * @param graph
	 * @param from
	 * @param to
	 * @return
	 */
	public static int[] findPath(Graph graph, int from, int to){
		boolean[] visited = new boolean[graph.numVertices()];
		PriorityQueue<Path> alternetPaths = new PriorityQueue<>();
		
		Path first = Path.make();
		first.add(from, 0);
		alternetPaths.add(first);
		
		
		while (!alternetPaths.isEmpty()){
			Path bestPath = alternetPaths.poll();
			visited[bestPath.path.peek()]=true;
			if(bestPath.path.peek()==to)
					return bestPath.toArray();
			for(VertexIterator iter = graph.neighbors(bestPath.path.peek());
					iter.hasNext();){
				int next = iter.next();
				if(!visited[next]){
					int tonextcost = graph.cost(bestPath.path.peek(), next);
					if(tonextcost>=0)
						alternetPaths.add(bestPath.clone().add(next, tonextcost));
				}
			}
			
		}
		return new int[]{};
	}
	
	
	private static class Path implements Comparable<Path>{
		private int cost=0;
		private Stack<Integer> path;
		@Override
		public int compareTo(Path o) {
			return cost-o.cost;
		}
		public static Path make(){
			Path result = new Path();
			result.path = new Stack<>();
			return result;
		}
		
		public Path clone() {
			Path clone = new Path();
			try{
				clone.path=(Stack<Integer>) path.clone();
			}catch (Exception e) {
					try {
						throw new Exception("WTF, this is IMPOSSIBRUE");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
			}
			clone.cost = cost;
			return clone;
		}
		public Path add(int node, int c){
			path.push(node);
			this.cost+=c;
			return this;
		}
		public int[] toArray(){
			int[] result = new int[path.size()];
			Object[] array = path.toArray(); 
			for(int i=0;i<array.length; i++)
				result[i]=(Integer)array[i];
			return result;
		}
	}
	
	
}
