package cell;

import main.World;
import ui.GameScreen;
import utilities.Pos;
import utilities.StdDraw;

public class Table extends CellOccupant{
	
	public Table(World world, Pos pos) {
		super(world, pos);
		this.solid = true;
	}

	@Override
	public void draw() {
		StdDraw.picture(pos.getX(), pos.getY(), "table.png", GameScreen.CELL_SIZE, GameScreen.CELL_SIZE);
	}
	
	
}
