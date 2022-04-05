package utilities;

public class Pos {
	
	int x, y;
	
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public boolean equals(Object other) {
		Pos p = (Pos) other;
		return this.x == p.x && this.y == p.y;
	}
	
}