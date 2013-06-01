package gomoku.kernel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import gomoku.algorithm.NUM;
import gomoku.kernel.Cell;

public class Board {
	private Cell cell[][];

	public Board() {
		cell = new Cell[NUM.BOARDSIZE][NUM.BOARDSIZE];
		for (int i = 0; i < NUM.BOARDSIZE; i++)
			for (int j = 0; j < NUM.BOARDSIZE; j++) {
				cell[i][j] = new Cell(Cell.FREE);
			}
	}

	public boolean isBlank() {
		for (int i = 0; i < NUM.BOARDSIZE; i++)
			for (int j = 0; j < NUM.BOARDSIZE; j++)
				if (cell[i][j].getSate() != 0)
					return false;
		return true;
	}

	public void setStateCell(int state, int i, int j) {
		cell[i][j].setState(state);
	}

	public int getStateCell(int i, int j) {
		return cell[i][j].getSate();
	}

	// Totototoot tatatatta
	// ///////////////////////////////////////////////////////////

	public void input(String file_name) {
		FileReader read;
		Scanner scan;
		try {
			read = new FileReader(file_name);
			scan = new Scanner(read);
			for (int i = 0; i < NUM.BOARDSIZE; i++) {
				for (int j = 0; j < NUM.BOARDSIZE; j++) {
					cell[i][j].setState(scan.nextInt());
				}
			}
			scan.close();
		} catch (IOException e) {
		}
	}

	public void output(String file_name) {
		try {
			FileWriter writer = new FileWriter(file_name);
			PrintWriter printer = new PrintWriter(writer);
			for (int i = 0; i < NUM.BOARDSIZE; i++) {
				for (int j = 0; j < NUM.BOARDSIZE; j++) {
					printer.print(cell[i][j].getSate() + " ");
				}
				printer.println();
			}
			printer.close();
		} catch (IOException ex) {
		}
	}

}
