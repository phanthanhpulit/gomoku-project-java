package gomoku.gui;

import java.awt.Point;

import gomoku.kernel.Cell;

public class CellGUI extends Cell{
	private Point location;
	
	public CellGUI(int state, Point p) {
		super(state);
		// TODO Auto-generated constructor stub
		location = new Point();
		location = p;
	}
	
	public CellGUI(int x, int y)
	{
		super(0);
		location = new Point(x, y);
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
}
