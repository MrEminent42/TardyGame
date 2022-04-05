package cell;

import main.World;
import utilities.Pos;
import utilities.StdDraw;

public class Wall extends ColorFill {

	public Wall(World world, Pos pos) {
		super(world, pos, StdDraw.BLACK);
		this.solid = true;
	}

}
