package cell.monster;

import cell.Player;
import main.World;
import search.AStarSearcher;
import ui.GameScreen;
import utilities.Pos;
import utilities.StdDraw;

public class Vandenboom extends Monster {
	
	public Vandenboom(World world, Pos pos, Player target) {
		super(world, pos, target);
	}
	
	@Override
	public void move() {
		Pos to = AStarSearcher.getInstance().search(this.pos, target.getPos()).get(1);
		this.pos = to;
	}
	
	@Override
	public void draw() {
		StdDraw.picture(pos.getX(), pos.getY(), "vandenboom.jpg", GameScreen.CELL_SIZE, GameScreen.CELL_SIZE);
		super.draw();
	}
	
	
}
