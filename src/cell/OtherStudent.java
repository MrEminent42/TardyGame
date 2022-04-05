package cell;

import java.util.Random;

import main.World;
import search.AStarSearcher;
import ui.GameScreen;
import utilities.Pos;
import utilities.StdDraw;

public class OtherStudent extends CellOccupant implements AutoMove {
	
	Pos target;
	
	public OtherStudent(World world) {
		super(world, world.getRandomEmptyPos());
		this.target = world.getRandomEmptyPos();
		this.solid = true;
	}

	@Override
	public void move() {
		// if level 4 & target isn't already the player
		if (world.getLevel() >= 4 && !target.equals(world.getPlayerPos())) {
			
//			if (new Random().nextInt(100) < 25) {
				target = world.getPlayerPos();
//			}
		}
		// other levels
		else if (this.pos.equals(this.target)) {
			
			if (new Random().nextInt(100) < 15) {
				this.target = world.getRandomEmptyPos();
			}
			return;
		}
		
		
		Pos to;
		try {
			to = AStarSearcher.getInstance().search(this.pos, this.target).get(1);
		} catch (NullPointerException e) {
			this.target = world.getRandomEmptyPos();
			return;
		}
		
		if (world.isOccupied(to)) return;
		
		this.pos = to;
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		StdDraw.picture(pos.getX(), pos.getY(), "otherstudent.png", GameScreen.CELL_SIZE, GameScreen.CELL_SIZE);
	}
	
	
	
}
