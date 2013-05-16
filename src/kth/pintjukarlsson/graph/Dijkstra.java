package kth.pintjukarlsson.graph;

import java.util.PriorityQueue;
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
	public static ImmutablePosition[] findPath(PositionGraph graph, ImmutablePosition from, ImmutablePosition to){
		if(from.equals(to))
			return new ImmutablePosition[]{};
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
				ImmutablePosition next = iter.next();
				
				if(!visited[next.getY()][next.getX()]){
					visited[next.getY()][next.getX()]=true;
					float tonextcost = calcCost(bestPath.path.peek(), next);
					alternetPaths.add(bestPath.clone().add(next, graph));
				}

			}
		}
	//System.gc();
		return new ImmutablePosition[]{};
	}
	
	
	private static class Path implements Comparable<Path>{
		private float cost=0;
		private float toGoal=0;
		private final float balsnce= 1.2f;
		private Stack<ImmutablePosition> path;
		private ImmutablePosition goal;
		@Override
		public int compareTo(Path o) {
			
				//return (int)(cost-o.cost);
			return (int)((cost+(toGoal*balsnce))-(o.cost+(o.toGoal*balsnce)));
		}
		public static Path make(ImmutablePosition g){
			Path result = new Path();
			result.path = new Stack<ImmutablePosition>();
			result.goal = g;
			return result;
		}
		
		public Path clone() {
			Path clone = new Path();
			try{
				clone.path=(Stack<ImmutablePosition>) path.clone();
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
		public Path add(ImmutablePosition node, PositionGraph graph){
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
		public ImmutablePosition[] toArray(){
			ImmutablePosition[] result = new ImmutablePosition[path.size()];
			Object[] array = path.toArray(); 
			for(int i=0;i<array.length; i++)
				result[i]=(ImmutablePosition)array[i];
			return result;
		}
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder("[");
			for(ImmutablePosition i: this.path)
				s.append(i+", ");
			s.append("]");
			return s.toString();
		}
	}
	
	public static float calcCost(ImmutablePosition a, ImmutablePosition b){
		return a.manhatanDistance(b);
	}
	
	
	
}
