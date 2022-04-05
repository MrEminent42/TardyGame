package ui;

import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.World;
import utilities.StdAudio;
import utilities.StdDraw;

public class GameScreen {
	
	ScheduledExecutorService refresher;
	int refreshMilis;
	
	World world;
	
	Color textColor = StdDraw.BLACK;
	String bottomText = "";
	
	public static final double CELL_SIZE = 1d;
	public static final double DEFAULT_PEN_RADIUS = 0.002;
	
	public GameScreen(World world, int refreshMilis) {
		this.world = world;
		init(refreshMilis);
	}
	
	private void init(int refreshMilis) {
		StdDraw.setCanvasSize(800, 900);
		
		StdDraw.setXscale(-CELL_SIZE/2, World.GRID_SIZE - CELL_SIZE/2 + StdDraw.getPenRadius());
		StdDraw.setYscale(-CELL_SIZE/2 - StdDraw.getPenRadius(), (World.GRID_SIZE - CELL_SIZE/2)*1.125);
		
		StdDraw.enableDoubleBuffering();
		
		this.refreshMilis = refreshMilis;
		refresher = Executors.newScheduledThreadPool(1);
		scheduleRefresher();
	}

	public void scheduleRefresher() {
		refresher.scheduleAtFixedRate(new Runnable() {
			public void run() {
				refresh();
			}
		}, 1, refreshMilis, TimeUnit.MILLISECONDS);
	}
	
	public void refresh() {
		StdDraw.clear();
		
		// draw occupants
		for (int i = world.getOccupants().size() - 1; i >= 0; i--) {
			world.getOccupants().get(i).draw();
		}
		
		// draw grid cubes
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int row = 0; row < World.GRID_SIZE; row++) {
			for (int col = 0; col < World.GRID_SIZE; col++) {
				StdDraw.square(row, col, CELL_SIZE/2);
			}
		}
		
		StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(World.GRID_SIZE/2 - 1, (World.GRID_SIZE + (World.GRID_SIZE * (0.125/2))), this.bottomText);
		
		StdDraw.setFont(new Font("Arial", Font.PLAIN, 15));
		long millis = Calendar.getInstance().getTimeInMillis() - world.getStartMilis();
		String time = String.format("%02d:%02d", (millis / (1000 * 60) % 60), ((millis / 1000) % 60));
		
		StdDraw.text(World.GRID_SIZE/2 - 1, 
				World.GRID_SIZE + (World.GRID_SIZE * (0.125/2)) - 0.8, "You've lasted: " + time);
		StdDraw.text(World.GRID_SIZE/2 - 1, 
				World.GRID_SIZE + (World.GRID_SIZE * (0.125/2)) - 1.3d, "Level: " + world.getLevel());
		
		StdDraw.show();
	}
	
	public void setBottomText(String text, Color col) {
		if (!text.equals("")) StdAudio.play("blip.mid");
		this.bottomText = text;
		this.textColor = col;
	}
	
	
	public void stopRefresh() {
		this.refresher.shutdownNow();
	}
	
	public void resumeRefresh() {
		scheduleRefresher();
	}
	
	
	
	
	// BEGIN STATIC METHODS
	
	public static String prompt(String prompt) {
		return javax.swing.JOptionPane.showInputDialog(prompt);
	}
	
}
