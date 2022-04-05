package ui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.PriorityQueue;

import cell.ColorFill;
import main.World;
import search.AStarSearchNode;
import utilities.Pos;
import utilities.StdDraw;

public class SearchDebugGameScreen extends GameScreen {
	
	ArrayList<AStarSearchNode> visitedList;
	PriorityQueue<AStarSearchNode> open;
	
	public SearchDebugGameScreen(World cafeteria, ArrayList<AStarSearchNode> visitedList, PriorityQueue<AStarSearchNode> open) {
		super(cafeteria, 100);
		this.visitedList = visitedList;
		this.open = open;
	}
	
	public void refresh() {
//		super.refresh(refreshMilis);
		
		for (AStarSearchNode node : visitedList) {
			new ColorFill(world, node.getPos(), StdDraw.LIGHT_GRAY).draw();
			Pos pos = node.getPos();
			StdDraw.setFont(new Font("", Font.BOLD, 15));
			StdDraw.text(pos.getX() - 0.3, pos.getY() + 0.3, "" + node.getTotalCost());
			StdDraw.text(pos.getX() + 0.3, pos.getY() - 0.3, "" + node.getHCost());
			StdDraw.text(pos.getX() - 0.3, pos.getY() - 0.3, "" + node.getGCost());
		}
		
		for (AStarSearchNode node : open) {
			new ColorFill(world, node.getPos(), StdDraw.ORANGE).draw();
			Pos pos = node.getPos();
			StdDraw.setFont(new Font("", Font.BOLD, 15));
			StdDraw.text(pos.getX() - 0.3, pos.getY() + 0.3, "" + node.getTotalCost());
			StdDraw.text(pos.getX() + 0.3, pos.getY() - 0.3, "" + node.getHCost());
			StdDraw.text(pos.getX() - 0.3, pos.getY() - 0.3, "" + node.getGCost());
		}
		
		StdDraw.show();
	}

}
