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
	public static ImuteblePosition[] findPath(PositionGraph graph, ImuteblePosition from, ImuteblePosition to){
		boolean[][] visited = new boolean[graph.getHight()][graph.getWidth()];
		PriorityQueue<Path> alternetPaths = new PriorityQueue<>();
		
		Path first = Path.make(to);
		first.add(from);
		alternetPaths.add(first);
		
		
		while (!alternetPaths.isEmpty()){
			Path bestPath = alternetPaths.poll();
			visited[bestPath.path.peek().getY()][bestPath.path.peek().getX()]=true;
			if(bestPath.path.peek()==to)
					return bestPath.toArray();
			for(VertexIterator iter = graph.neighbors(bestPath.path.peek());
					iter.hasNext();){
				ImuteblePosition next = iter.next();
				
				if(!visited[next.getY()][next.getX()]){
					float tonextcost = calcCost(bestPath.path.peek(), next);
					alternetPaths.add(bestPath.clone().add(next));
				}
			}
			
		}
		return new ImuteblePosition[]{};
	}
	
	
	private static class Path implements Comparable<Path>{
		private float cost=0;
		private float toGoal=0;
		private Stack<ImuteblePosition> path;
		private ImuteblePosition goal;
		@Override
		public int compareTo(Path o) {
			if(true)
				return (int)(cost-o.cost);
			return (int)((cost+toGoal)-(o.cost+o.toGoal));
		}
		public static Path make(ImuteblePosition g){
			Path result = new Path();
			result.path = new Stack<>();
			result.goal = g;
			return result;
		}
		
		public Path clone() {
			Path clone = new Path();
			try{
				clone.path=(Stack<ImuteblePosition>) path.clone();
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
		public Path add(ImuteblePosition node){
			if(path.isEmpty()){
				this.cost=0;
				path.push(node);
				return this;
			}
			this.cost+= Dijkstra.calcCost(node, path.peek());
			path.push(node);
			return this;
		}
		public ImuteblePosition[] toArray(){
			ImuteblePosition[] result = new ImuteblePosition[path.size()];
			Object[] array = path.toArray(); 
			for(int i=0;i<array.length; i++)
				result[i]=(ImuteblePosition)array[i];
			return result;
		}
	}
	
	public static float calcCost(ImuteblePosition a, ImuteblePosition b){
		return a.distance(b);
	}
}
