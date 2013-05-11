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
		if(from.equals(to))
			return new ImuteblePosition[]{};
		boolean[][] visited = new boolean[graph.getHight()][graph.getWidth()];
		PriorityQueue<Path> alternetPaths = new PriorityQueue<Path>(10000);
		
		Path first = Path.make(to);
		first.add(from, graph);
		alternetPaths.add(first);
		
		float time = 0;
		while (!alternetPaths.isEmpty()){
			time=System.nanoTime();
			Path bestPath = alternetPaths.poll();
			visited[bestPath.path.peek().getY()][bestPath.path.peek().getX()]=true;
			if(bestPath.path.peek().equals(to))
					return bestPath.toArray();
			
			for(VertexIterator iter = graph.neighbors(bestPath.path.peek());iter.hasNext();){
				ImuteblePosition next = iter.next();
				
				if(!visited[next.getY()][next.getX()]){
					visited[next.getY()][next.getX()]=true;
					float tonextcost = calcCost(bestPath.path.peek(), next);
					alternetPaths.add(bestPath.clone().add(next, graph));
				}

			}
		}
	//System.gc();
		return new ImuteblePosition[]{};
	}
	
	
	private static class Path implements Comparable<Path>{
		private float cost=0;
		private float toGoal=0;
		private final float balsnce= 1.2f;
		private Stack<ImuteblePosition> path;
		private ImuteblePosition goal;
		@Override
		public int compareTo(Path o) {
			
				//return (int)(cost-o.cost);
			return (int)((cost+(toGoal*balsnce))-(o.cost+(o.toGoal*balsnce)));
		}
		public static Path make(ImuteblePosition g){
			Path result = new Path();
			result.path = new Stack<ImuteblePosition>();
			result.goal = g;
			return result;
		}
		
		public Path clone() {
			Path clone = new Path();
			try{
				clone.path=(Stack<ImuteblePosition>) path.clone();
			}catch (Exception e) {
					try {
						throw new Exception("WTF, this is IMPOSSIBRUE"); // this is not helpful :3
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
			}
			clone.cost = cost;
			clone.goal = goal;
			return clone;
		}
		public Path add(ImuteblePosition node, PositionGraph graph){
			if(path.isEmpty()){
				this.cost=0;
				path.push(node);
				toGoal = goal.manhatanDistance(node);
				return this;
			}
			this.cost+= graph.cost(node, path.peek());
			toGoal = goal.manhatanDistance(node);
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
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder("[");
			for(ImuteblePosition i: this.path)
				s.append(i+", ");
			s.append("]");
			return s.toString();
		}
	}
	
	public static float calcCost(ImuteblePosition a, ImuteblePosition b){
		return a.manhatanDistance(b);
	}
	
	
	
}
