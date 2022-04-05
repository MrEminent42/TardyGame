package cell;

import java.awt.event.KeyEvent;

import main.World;
import ui.GameScreen;
import utilities.Pos;
import utilities.StdDraw;

public class Player extends CellOccupant {

	public Player(World world, Pos pos) {
		super(world, pos);
		this.solid = true;
	}

	@Override
	public void draw() {
		// green outline
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.setPenRadius(0.005);
		StdDraw.filledSquare(pos.getX(), pos.getY(), GameScreen.CELL_SIZE / 2 - 0.005);
		StdDraw.setPenRadius(GameScreen.DEFAULT_PEN_RADIUS);
		StdDraw.setPenColor(StdDraw.BLACK);
		
		StdDraw.picture(pos.getX(), pos.getY(), "player.gif", GameScreen.CELL_SIZE, GameScreen.CELL_SIZE);
	}
	
	public void listen() {
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_W) || StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
			while (StdDraw.isKeyPressed(KeyEvent.VK_W) || StdDraw.isKeyPressed(KeyEvent.VK_UP));
			Pos to = new Pos(this.pos.getX(), this.pos.getY() + 1);
			if (World.isInBounds(to) && !world.isOccupied(to))
				this.pos = to;
		}
		
		else if (StdDraw.isKeyPressed(KeyEvent.VK_A) || StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
			while (StdDraw.isKeyPressed(KeyEvent.VK_A) || StdDraw.isKeyPressed(KeyEvent.VK_LEFT));
			Pos to = new Pos(this.pos.getX() - 1, this.pos.getY());
			if (World.isInBounds(to) && !world.isOccupied(to))
				this.pos = to;
			
		}
		
		else if (StdDraw.isKeyPressed(KeyEvent.VK_S) || StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
			while (StdDraw.isKeyPressed(KeyEvent.VK_S) || StdDraw.isKeyPressed(KeyEvent.VK_DOWN));
			Pos to = new Pos(this.pos.getX(), this.pos.getY() - 1);
			if (World.isInBounds(to) && !world.isOccupied(to))
				this.pos = to;
			
		}
		
		else if (StdDraw.isKeyPressed(KeyEvent.VK_D) || StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
			while (StdDraw.isKeyPressed(KeyEvent.VK_D) || StdDraw.isKeyPressed(KeyEvent.VK_RIGHT));
			Pos to = new Pos(this.pos.getX() + 1, this.pos.getY());
			if (World.isInBounds(to) && !world.isOccupied(to))
				this.pos = to;

		}

		world.checkFinish();
	}

}
