package cell.monster;

import java.util.ArrayList;

import cell.Player;
import main.World;
import search.GreedySearcher;
import ui.GameScreen;
import utilities.Pos;
import utilities.StdDraw;

public class Lowman extends Monster {

	public Lowman(World world, Pos pos, Player target) {
		super(world, pos, target);
	}

	@Override
	public void move() {
		
		ArrayList<Pos> path = GreedySearcher.getInstance().search(this.pos, target.getPos());
		Pos to = path.get(1);
		
		for (int i = 1; i <= 10; i++) {
			if (world.isTable(path.get(i))) continue;
			else {
				to = path.get(i);
				break;
			}
		}
		
		this.pos = to;
	}
	
	@Override
	public void draw() {
		StdDraw.picture(pos.getX(), pos.getY(), "lowman.jpg", GameScreen.CELL_SIZE, GameScreen.CELL_SIZE);
		super.draw();
	}
	
	
	
}
