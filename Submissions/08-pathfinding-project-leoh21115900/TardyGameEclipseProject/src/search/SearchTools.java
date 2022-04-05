package search;

import java.util.ArrayList;

import main.World;
import utilities.Pos;

public class SearchTools {
	
	private SearchTools() { }
	
	public static ArrayList<Pos> getNeighborPositions(Pos findNeighbors) {
		ArrayList<Pos> neighbors = new ArrayList<Pos>();
		
		// UP
		if (!(findNeighbors.getY() + 1 > World.GRID_SIZE - 1)) {
			neighbors.add(new Pos(findNeighbors.getX(), findNeighbors.getY() + 1));
		}
		
		// RIGHT
		if (!(findNeighbors.getX() + 1 > World.GRID_SIZE - 1)) {
			neighbors.add(new Pos(findNeighbors.getX() + 1, findNeighbors.getY()));
		}
		
		// DOWN
		if (!(findNeighbors.getY() - 1 < 0)) {
			neighbors.add(new Pos(findNeighbors.getX(), findNeighbors.getY() - 1));
		}
		
		// LEFT
		if (!(findNeighbors.getX() - 1 < 0)) {
			neighbors.add(new Pos(findNeighbors.getX() - 1, findNeighbors.getY()));
		}
		
		return neighbors;
	}
	
	public static int getManhattanDistance(Pos a, Pos b) {
		return Math.abs(a.getX() 
				- b.getX()) 
				+ Math.abs(a.getY() - b.getY()) - 1;
	}
}
