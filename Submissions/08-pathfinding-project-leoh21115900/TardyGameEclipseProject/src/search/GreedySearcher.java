package search;

import java.util.ArrayList;

import main.World;
import utilities.Pos;

public class GreedySearcher {
	
	static GreedySearcher instance;
	
	public static GreedySearcher getInstance() {
		if (instance == null) instance = new GreedySearcher();
		return instance;
	}
	
	
	
	World world;
	
	private GreedySearcher() {  }
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	// TODO occupied??
	public ArrayList<Pos> search(Pos origin, Pos goal) {
		ArrayList<Pos> path = new ArrayList<Pos>();
		
		path.add(origin);
		
		while(!path.get(path.size() - 1).equals(goal)) {
			Pos open = path.get(path.size() - 1);
			
			Pos best = null;
			
			for (Pos neighbor : SearchTools.getNeighborPositions(open)) {
				if (best == null) {
					best = neighbor;
					continue;
				}
				
				else  {
					// if this neighbor is cheaper, then this is the best
					if (SearchTools.getManhattanDistance(best, goal) 
							> SearchTools.getManhattanDistance(neighbor, goal)) {
						best = neighbor;
					}
				}
			}
			
			path.add(best);
		}
		
		
		return path;
	}
	
	
	
}