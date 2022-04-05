package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import utilities.StdDraw;

public class TardyGame {
	
	public TardyGame() {
		
		StdDraw.setCanvasSize(400, 400);
		StdDraw.setScale(0, 100);
		StdDraw.setPenColor(new Color(234, 245, 255));
		StdDraw.setFont(new Font("Gabriola", Font.BOLD, 80));
		StdDraw.filledSquare(50, 50, 50);
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(50, 62, "TARDY");
		StdDraw.setFont(new Font("Arial", 0, 15));
		StdDraw.text(50, 55, "v1.0 / December 2018");
		StdDraw.text(50, 51, "by Leo Horwitz");
		
		StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
		StdDraw.text(50, 41, "Press enter to start!");
		
		while (!StdDraw.isKeyPressed(KeyEvent.VK_ENTER));
		
		new World();
	}
	
}
