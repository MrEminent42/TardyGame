package cell;

import main.World;
import utilities.Pos;

public abstract class CellOccupant {
	
	protected World world;
	protected Pos pos;
	protected boolean solid = false;
	// TODO
	
	public CellOccupant(World world, Pos pos) {
		if (pos.getX() > World.GRID_SIZE || pos.getY() > World.GRID_SIZE) {
			throw new IllegalArgumentException();
		}
		
		this.pos = pos;
		this.world = world;
	}
	
	@Deprecated
	public int getX() {
		return pos.getX();
	}
	
	@Deprecated
	public int getY() {
		return pos.getY();
	}
	
	public Pos getPos() {
		return this.pos;
	}
	
	public boolean isSolid() {
		return this.solid;
	}
	
	public abstract void draw();
}
