package gomoku.kernel;

public class Cell {
	private int state;
	public static final int FREE = 0;
	public static final int O = 1;
	public static final int X = 2;

	public int getSate() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Cell(int state) {
		super();
		this.state = state;
	}
	
	public boolean isFree()
	{
		return (state == FREE);
	}
	
	public boolean isO()
	{
		return (state == O);
	}
	
	public boolean isX()
	{
		return (state == X);
	}
}
