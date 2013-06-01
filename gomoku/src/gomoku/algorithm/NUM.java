package gomoku.algorithm;

public abstract class NUM {
	public static int BOARDSIZE = 20;
	public static final int CELLSIZE = 25;
	public static int WINSIZE = 5;
	public static long WINSCORE = (int) Math.pow(WINSIZE, WINSIZE);
	public static long ATTCORE = (int) Math.pow(WINSIZE-1, WINSIZE-1);
	public static final String TITLE = "Gomoku Cup IFI - Joueur: Assistant de Nguyen Quoc Khai";
	public static final String INFO = "Nguyen Quoc Khai - p17";
	public static String FILE_NAME = "gomoku.txt";
	public static final String START = "Commencer";
	public static final String RESTART = "Recommencer";
	public static final String PLAY = "Play";
	public static final String XWINCMD = "X a gagné";
	public static final String OWINCMD = "O a gagné";
	public static final String NULLCMD = "Guerre est nulle";
	public static final String STARTCMD = "Préparation";
	public static final String PLAYING = "En train de se battre";
	public static final int[] DX = { 0, 1, 1, 1, 0, -1, -1, -1 };
	public static final int[] DY = { -1, -1, 0, 1, 1, 1, 0, -1 };
	public static final int AMATEUR = 0;
	public static final int SEMIPRO = 1;
	public static final int PROFESIONAL = 2;
}
