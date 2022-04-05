package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import main.World;
import utilities.Pos;

public class AStarSearcher {
	
	static AStarSearcher instance;
	
	public static AStarSearcher getInstance() {
		if (instance == null) instance = new AStarSearcher();
		return instance;
	}
	
	
	
	
	World world;

	private AStarSearcher() {	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public ArrayList<Pos> search(Pos originPos, Pos goalPos) {
		
		// create visitedList and openList
		ArrayList<AStarSearchNode> visitedList = new ArrayList<AStarSearchNode>();
		PriorityQueue<AStarSearchNode> openList = new PriorityQueue<AStarSearchNode>(5, new Comparator<AStarSearchNode>() {
			
			@Override
			public int compare(AStarSearchNode a, AStarSearchNode b) {
				return Integer.compare(a.getTotalCost(), b.getTotalCost());
			}
			
		});
		
		// add origin node
		openList.add(new AStarSearchNode(originPos, goalPos, null));
		//SearchDebugGameScreen debugScreen = new SearchDebugGameScreen(world, visitedList, openList);
		
		
		// main queue loop
		while (!openList.isEmpty()) {
			AStarSearchNode current = openList.poll();
			
			// continue if already visited
			if (visitedList.contains(current)) continue;
			// mark as visited
			visitedList.add(current);
			
			// if goal is reached then finish
			if (current.getPos().equals(goalPos)) {
				return findPath(current);
			}
			
			// load neighbors into queue
			neighbors: for (Pos neighborPos : SearchTools.getNeighborPositions(current.getPos())) {
				if (world.isTable(neighborPos)) continue neighbors;
				AStarSearchNode neighbor = new AStarSearchNode(neighborPos, goalPos, current);
				if (!visitedList.contains(neighbor) && !openList.contains(neighbor)) openList.add(neighbor);
			}
			
			
			
			// BEGIN DEBUG SCREEN \\
//			{
//				ArrayList<AStarSearchNode> allNodes = new ArrayList<AStarSearchNode>();
//				allNodes.addAll(visitedList);
//				
//				for (AStarSearchNode openNode : openList) {
//					if (allNodes.contains(openNode)) continue;
//					allNodes.add(openNode);
//				}
//				
//				debugScreen.refresh();
//				
//				while (!StdDraw.isKeyPressed(KeyEvent.VK_ENTER));
//				while (StdDraw.isKeyPressed(KeyEvent.VK_ENTER));
//			}
			// END DEBUG SCREEN \\
			
		}
		
		return null;
	}
	
	private ArrayList<Pos> findPath(AStarSearchNode dest) {
		if (dest == null) return new ArrayList<Pos>();
		
		ArrayList<Pos> previous = findPath(dest.getParent());
		previous.add(dest.getPos());
		return previous;
	}
	
}
