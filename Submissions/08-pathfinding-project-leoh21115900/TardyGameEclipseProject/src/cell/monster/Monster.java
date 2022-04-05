package cell.monster;

import cell.AutoMove;
import cell.CellOccupant;
import cell.Player;
import main.World;
import ui.GameScreen;
import utilities.Pos;
import utilities.StdDraw;

public abstract class Monster extends CellOccupant implements AutoMove {
	
	Player target;
	
	public Monster(World world, Pos pos, Player target) {
		super(world, pos);
		this.target = target;
	}
	
	public void draw() {
		// red outline
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.005);
		StdDraw.square(pos.getX(), pos.getY(), GameScreen.CELL_SIZE/2 - 0.005);
		StdDraw.setPenRadius(GameScreen.DEFAULT_PEN_RADIUS);
		StdDraw.setPenColor(StdDraw.BLACK);
	}
	
	
}
