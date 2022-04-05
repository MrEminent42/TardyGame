package search;

import utilities.Pos;

public class AStarSearchNode {

	public static final int MOVEMENT_COST = 1;
	
	private Pos pos;
	private Pos goal;
	private AStarSearchNode parent;
	
	// step cost to here
	private int g;
	// heuristic
	private int h;
	
	/**
	 * Create a node for a Pos
	 * @param pos the Pos that this node represents
	 * @param goal the goal position that you're searching for
	 * @param parent the parent node (can be left null)
	 */
	public AStarSearchNode(Pos pos, Pos goal, AStarSearchNode parent) {
		this.pos = pos;
		this.parent = parent;
		this.goal = goal;
		
		refreshValues();
	}
	
	private void refreshValues() {
		
		// calculate step cost to here
		if (this.parent != null) {
			g = parent.g + MOVEMENT_COST;
		} else {
			g = 0;
		}
		
		// calculate distance to goal
		// this.getPos() == null??
		this.h = SearchTools.getManhattanDistance(this.getPos(), goal);
	}
	
	public void setParent(AStarSearchNode parent) {
		this.parent = parent;
	}
	
	public AStarSearchNode getParent() {
		return this.parent;
	}
	
	public Pos getPos() {
		return this.pos;
	}
	
	public int getTotalCost() {
		refreshValues();
		return this.g + this.h;
	}
	
	public int getGCost() {
		refreshValues();
		return this.g;
	}
	
	public int getHCost() {
		refreshValues();
		return this.h;
	}
	
	
	public boolean equals(Object o) {
		return ((AStarSearchNode) o).getPos().equals(this.getPos());
	}
	
}
