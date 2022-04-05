package main;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cell.AutoMove;
import cell.CellOccupant;
import cell.ColorFill;
import cell.OtherStudent;
import cell.Player;
import cell.Table;
import cell.monster.Lowman;
import cell.monster.Monster;
import cell.monster.Vandenboom;
import search.AStarSearcher;
import search.GreedySearcher;
import ui.GameScreen;
import utilities.Pos;
import utilities.StdDraw;

public class World {
	
	public static final int GRID_SIZE = 22;
	
	public static enum MoveDirection {
		UP, DOWN, LEFT, RIGHT;
		
		// TODO: fix implementations
		public MoveDirection[] getDirections() {
			return new MoveDirection[] {UP, DOWN, LEFT, RIGHT};
		}
	}
	
	private Player player;
	private ArrayList<CellOccupant> occupants;
	private long startMilis;
	
	/* 
	 * LEVELS:
	 * 1: Alone, only other students
	 * 2: Vandenboom
	 * 3: Lowman
	 */
	private int level = 1;

	private GameScreen screen;
	
	private ScheduledExecutorService stepExecutor, keyboardExecutor, spawnStudentExecutor;
	
	public World() {
		AStarSearcher.getInstance().setWorld(this);
		GreedySearcher.getInstance().setWorld(this);
		
		this.screen = new GameScreen(this, 100);
		this.occupants = new ArrayList<CellOccupant>();
		this.player = new Player(this, new Pos(8, 4));
		
		init();
	}
	
	public void init() {
		
		// add starting occupants
		addOccupant(this.player);
		
		this.startMilis = Calendar.getInstance().getTimeInMillis();
		
		// generate random tables
		int frequency = 19;
		Random random = new Random();
		for (int row = 0; row < World.GRID_SIZE; row ++) {
			for (int col = 0; col < World.GRID_SIZE; col++) {
				if (random.nextInt(99) + 1 <= frequency) {
					if (!isOccupied(new Pos(row, col)))
						addOccupant(new Table(this, new Pos(row, col)));
				}
			}
		}
		
		
		// schedule step executor
		stepExecutor = Executors.newScheduledThreadPool(1);
		stepExecutor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				step();
			}
		}, 100, 500, TimeUnit.MILLISECONDS);
		
		// schedule keyboard listener
		keyboardExecutor = Executors.newScheduledThreadPool(1);
		keyboardExecutor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				keyboard();
			}
		}, 1, 5, TimeUnit.MILLISECONDS);
		
		// schedule student spawner
		spawnStudentExecutor = Executors.newScheduledThreadPool(1);
		spawnStudentExecutor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				spawnStudent();
			}
		}, 5, 3, TimeUnit.SECONDS);
		
		
		// schedule vandenboom spawn
		Executors.newScheduledThreadPool(1).schedule(new Runnable() {
			public void run() {
				spawnVandenboom();
			}
		}, 15, TimeUnit.SECONDS);
		
		// schedule lowman spawn
		Executors.newScheduledThreadPool(1).schedule(new Runnable() {
			public void run() {
				spawnLowman();
			}
		}, 30, TimeUnit.SECONDS);
		
		// schedule level 4
		Executors.newScheduledThreadPool(1).schedule(new Runnable() {
			public void run() {
				incrementLevel();
				screen.setBottomText("Look out, other students are trying to trap you!", StdDraw.BLACK);
			}
		}, 45, TimeUnit.SECONDS);
		
		
		// run initial welcome  text
		screen.setBottomText("Welcome...to the cafeteria!", StdDraw.BLACK);
		StdDraw.pause(2500);
		screen.setBottomText("Use W/A/S/D to move around.", StdDraw.BLACK);
		StdDraw.pause(2500);
		screen.setBottomText("You can't hop over the tables.", StdDraw.BLACK);
		StdDraw.pause(2500);
		screen.setBottomText("Some other students (who don't have class) will also be in the cafeteria.", 
				StdDraw.BLACK);
		StdDraw.pause(4000);
		screen.setBottomText("Oh no, you're late for class!", StdDraw.RED);
		
	}
	
	/**
	 * listens for keyboard input
	 */
	public void keyboard() {
		if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			this.quit();
			return;
		}
		this.player.listen();
	}
	
	// DEBUG METHOD
	public void testSearch() {
		
		ColorFill origin = new ColorFill(this, new Pos(5, 10), StdDraw.GREEN);
		ColorFill goal = new ColorFill(this, new Pos(14, 2), StdDraw.RED);
		
		addOccupant(origin);
		addOccupant(goal);
		
		//generateWalls(30);
		
		ArrayList<Pos> path =  AStarSearcher.getInstance().search(origin.getPos(), goal.getPos());
		
		for (Pos step : path) {
			if (step.equals(origin.getPos()) || step.equals(goal.getPos())) continue;
			addOccupant(new ColorFill(this, step, StdDraw.MAGENTA));
		}
		
	}
	
	
	// BEGIN MECHANICAL/RUNNER METHODS
	
	/**
	 * @return all of the occupants in this world
	 */
	public ArrayList<CellOccupant> getOccupants() {
		return this.occupants;
	}
	
	/**
	 * Move all AutoMove CellOccupants one step forward
	 */
	public void step() {
		for (CellOccupant occ : occupants) {
			try { // TODO remove
				if (occ instanceof AutoMove) ((AutoMove) occ).move();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		checkFinish();
	}
	
	/**
	 * spawns an OtherStudent
	 */
	public void spawnStudent() {
		addOccupant(new OtherStudent(this));
	}
	
	/**
	 * run the sequence to spawn a vandenboom
	 */
	public void spawnVandenboom() {
		screen.setBottomText("Here comes Vandenboom...", StdDraw.BLACK);
		Vandenboom v = new Vandenboom(this, getRandomEmptyPos(), player);
		addOccupant(v);
		incrementLevel();
		
		// delay text
		StdDraw.pause(2000);
		screen.setBottomText("Mission: don't get caught.", StdDraw.BLACK);
		StdDraw.pause(4000);
		screen.setBottomText("", StdDraw.BLACK);
	}
	
	/**
	 * run the sequence to spawn a lowman
	 */
	public void spawnLowman() { 
		screen.setBottomText("Vandenboom is getting tired.", StdDraw.BLACK);
		StdDraw.pause(2500);
		screen.setBottomText("He called in Lowman to help catch you.", StdDraw.BLACK);
		StdDraw.pause(2500);
		
		Lowman l = new Lowman(this, getRandomEmptyPos(), player);
		addOccupant(l);
		incrementLevel();
		
		screen.setBottomText("Lowman has the ability to hop OVER tables.", StdDraw.BLACK);
	}
	
	/**
	 * Check whether or not the game is finished: if a monster is in the same Pos as the player
	 * @return true if a monster is in the same place as a player, false otherwise
	 */
	public void checkFinish() {
		for (CellOccupant occ : occupants) {
			if (occ instanceof Player) continue;
			
			if (occ instanceof Monster) {
				if (occ.getPos().equals(this.getPlayerPos())) this.quit();
			}
		}
	}
	
	// BEGIN PAUSE/STOP METHODS
	
	/**
	 * stop and shut down the world
	 */
	public void quit() {
		this.keyboardExecutor.shutdownNow();
		this.stepExecutor.shutdownNow();
		this.screen.stopRefresh();
		this.screen.refresh();
		this.screen.refresh();
		this.screen.refresh();
		
		
		
		StdDraw.clear();
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setScale(0, 100);

		StdDraw.setFont(new Font("Arial", Font.BOLD, 40));
		StdDraw.text(50, 65, "You lost!");
		StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
		StdDraw.text(50, 45, "Level: " + this.level);
		long millis = Calendar.getInstance().getTimeInMillis() - this.startMilis;
		StdDraw.text(50, 40, 
				"Time: " + String.format("%02d:%02d", (millis / (1000 * 60) % 60), ((millis / 1000) % 60)));
		
		StdDraw.show();
		
	}
	
	
	// BEGIN MISC. METHODS
	
	/**
	 * Adds an occupant to the map if the cell is vacant
	 * 	Vacant: if the cell is empty or the current occupant is non-solid
	 * @param occ the occupant to add
	 * @return whether or not the addition was successful
	 */
	public boolean addOccupant(CellOccupant occ) {
		if (occ.isSolid() && isTable(occ.getPos())) {
			return false;
		}
		if (occ instanceof Player) occupants.add(0, occ);
		else if (occ instanceof Monster) occupants.add(1, occ);
		else occupants.add(occ);
		return true;
	}
	
	/**
	 * @param pos the position to search
	 * @return true if the CellOccupant in the specified Pos is a table, false otherwise
	 */
	public boolean isTable(Pos pos) {
		for (CellOccupant occ : occupants) {
			if (occ.getPos().equals(pos)) {
				return occ instanceof Table;
			}
		}
		
		return false;
	}
	
	/**
	 * @return an empty random position within the grid
	 */
	public Pos getRandomEmptyPos() {
		Random random = new Random();
		Pos r = new Pos(random.nextInt(World.GRID_SIZE), random.nextInt(World.GRID_SIZE));
		if (isOccupied(r))new Pos(random.nextInt(World.GRID_SIZE), random.nextInt(World.GRID_SIZE));
		if (isOccupied(r))new Pos(random.nextInt(World.GRID_SIZE), random.nextInt(World.GRID_SIZE));
		if (isOccupied(r))new Pos(random.nextInt(World.GRID_SIZE), random.nextInt(World.GRID_SIZE));
		
		return r;
	}
	
	/**
	 * @param pos the position to search
	 * @return true if the CellOccupant in the specified Pos is solid, false otherwise
	 */
	public boolean isOccupied(Pos pos) {
		for (CellOccupant occ : occupants) {
			if (occ.getPos().equals(pos)) {
				return occ.isSolid();
			}
		}
		return false;
	}
	
	/**
	 * @return the player's position
	 */
	public Pos getPlayerPos() {
		return this.player.getPos();
	}
	
	/**
	 * @return the time, in milliseconds, that the game started
	 */
	public long getStartMilis() {
		return this.startMilis;
	}
	
	/**
	 * @return the current level of the game
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * increments the level of the game
	 */
	private void incrementLevel() {
		this.level++;
	}
	
	// BEGIN STATIC METHODS
	
	public static boolean isInBounds(Pos pos) {
		return 
				pos.getX() < World.GRID_SIZE &&
				pos.getX() >= 0 &&
				pos.getY() < World.GRID_SIZE &&
				pos.getY() >= 0;
	}
	
}
