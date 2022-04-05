package cell;

import java.awt.Color;

import main.World;
import ui.GameScreen;
import utilities.Pos;
import utilities.StdDraw;

public class ColorFill extends CellOccupant {
	
	Color color;
	
	public ColorFill(World world, Pos pos, Color color) {
		super(world, pos);
		this.color = color;
	}

	@Override
	public void draw() {
		StdDraw.setPenColor(color);
		StdDraw.filledSquare(pos.getX(), pos.getY(), GameScreen.CELL_SIZE/2);
		StdDraw.setPenColor(StdDraw.BLACK);
	}
	
	
}
