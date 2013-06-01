package gomoku.gui;

import java.awt.Point;

import gomoku.algorithm.NUM;
import gomoku.kernel.Board;;

public class BoardGUI extends Board
{
	static final int TOP = 10;
	static final int LEFT = 10;
	private CellGUI cell[][];
	private Point board[];
	
	public BoardGUI()
	{
		super();
		int x_position = LEFT;
		int y_position = TOP;
		cell = new CellGUI[NUM.BOARDSIZE][NUM.BOARDSIZE];
		y_position = TOP;
		for(int i = 0; i < NUM.BOARDSIZE; i++)
		{
			x_position = LEFT;
			for(int j = 0; j < NUM.BOARDSIZE; j++)
			{
				cell[i][j] = new CellGUI(x_position, y_position);
				x_position += NUM.CELLSIZE;
			}
			y_position += NUM.CELLSIZE;
		}
	}
	
	public Point getLocation(int i, int j)
	{
		return cell[i][j].getLocation();
	}
	
	public Point[] getBoard()
	{
		board = new Point[NUM.BOARDSIZE*4];
		int k = 0;
		for(int i = 0; i < NUM.BOARDSIZE; i++)
		{
			board[k] = cell[i][0].getLocation();
			board[k + 1] = cell[i][NUM.BOARDSIZE-1].getLocation();
			k += 2;
		}
		for(int i = 0; i < NUM.BOARDSIZE; i++)
		{
			board[k] = cell[0][i].getLocation();
			board[k + 1] = cell[NUM.BOARDSIZE-1][i].getLocation();
			k += 2;
		}
		return board;
	}
	
	public CellGUI getCell(int i, int j)
	{
		return cell[i][j];
	}
	
}
