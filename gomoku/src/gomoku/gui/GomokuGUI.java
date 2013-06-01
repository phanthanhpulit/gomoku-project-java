package gomoku.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import gomoku.algorithm.Assistant;
import gomoku.algorithm.NUM;
import gomoku.gui.BoardGUI;
import gomoku.kernel.Cell;

public class GomokuGUI extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ControlPanel controlpanel = new ControlPanel();
	private MouseHandler mouseHandler = new MouseHandler();
	Assistant assistant;
	private Point board[]; // De ve ban co
	int player;
	BoardGUI plateau;
	int nb_tour = 0;
	boolean start = false;

	private Point[] getBoard() {
		board = new Point[NUM.BOARDSIZE * 4];
		plateau = new BoardGUI();
		board = plateau.getBoard();
		return board;
	}

	private Point optimiserLocationAndSetSate(Point p) {
		Point _temp = new Point();
		int cell[] = new int[2];
		for (int i = 0; i < NUM.BOARDSIZE; i++) {
			for (int j = 0; j < NUM.BOARDSIZE; j++) {
				_temp = plateau.getLocation(i, j);
				if (p.distance(_temp) < 10) {
					plateau.input(NUM.FILE_NAME);
					plateau.setStateCell(3 - player, i, j);
					plateau.output(NUM.FILE_NAME);
					if (getWinter() == 3 - player) {
						if (player == Cell.O)
							controlpanel.setAnnonce(NUM.XWINCMD);
						else
							controlpanel.setAnnonce(NUM.OWINCMD);
						controlpanel.setTextPlay(NUM.RESTART);
						repaint();
						return _temp;
					}

					plateau.input(NUM.FILE_NAME);
					assistant.getMove(player, cell, plateau);
					plateau.setStateCell(player, cell[0], cell[1]);
					nb_tour++;
					if (player == Cell.O)
						controlpanel.ajoutHistoire("Tour " + nb_tour + " de O : "
							+ cell[0] + " - " + cell[1]);
					else
						controlpanel.ajoutHistoire("Tour " + nb_tour + " de X : "
								+ cell[0] + " - " + cell[1]);
					plateau.output(NUM.FILE_NAME);
					repaint();
					if (getWinter() == player) {
						if (player == Cell.X)
							controlpanel.setAnnonce(NUM.XWINCMD);
						else
							controlpanel.setAnnonce(NUM.OWINCMD);
						controlpanel.setTextPlay(NUM.RESTART);
						return _temp;
					}
					return _temp;
					
				}
			}
		}
		return _temp;
	}

	public int getWinter() {
		if (assistant.isWiner(Cell.O, plateau))
		{
			nb_tour = 0;
			start = false;
			return Cell.O;
		}
		if (assistant.isWiner(Cell.X, plateau))
		{
			nb_tour = 0;
			start = false;
			return Cell.X;
		}
		return 0;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.blue);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));

		int _boardsize = NUM.BOARDSIZE * 4;
		for (int i = 0; i < _boardsize - 1; i += 2)
			g.drawLine(board[i].x, board[i].y, board[i + 1].x, board[i + 1].y);
		g.drawLine(board[_boardsize - 2].x, board[_boardsize - 2].y,
				board[_boardsize - 1].x, board[_boardsize - 1].y);

		Font font = new Font("Arial", Font.ITALIC, 18);
		g.setFont(font);
		int _state;
		int x, y;
		for (int i = 0; i < NUM.BOARDSIZE; i++) {
			for (int j = 0; j < NUM.BOARDSIZE; j++) {
				x = plateau.getLocation(i, j).x;
				y = plateau.getLocation(i, j).y;
				_state = plateau.getStateCell(i, j);
				if (_state == 1) {
					g.setColor(Color.RED);
					g.drawString("O", x - 7, y + 7);
				}
				if (_state == 2) {
					g.setColor(Color.BLUE);
					g.drawString("X", x - 7, y + 7);
				}
			}
		}
	}

	public GomokuGUI() {
		this.setPreferredSize(new Dimension(500, 500));
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.setLayout(new BorderLayout());
		assistant = new Assistant(NUM.AMATEUR);
		plateau = new BoardGUI();
		getBoard();
	}

	public class ControlPanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private JLabel jlb_information;
		private JLabel jlb_annonce;
		private JButton jbt_play;
		JTextArea jta_histoire;
		JScrollPane sp;

		public ControlPanel() {

			jta_histoire = new JTextArea("Welcome!!!!!!!!!\n");
			jta_histoire.setEditable(true);
			jta_histoire.setLineWrap(true);
			jta_histoire.setWrapStyleWord(true);
			jta_histoire.setBackground(Color.LIGHT_GRAY);
			Font font = new Font("Arial", Font.BOLD, 15);
			jta_histoire.setFont(font);
			this.setPreferredSize(new Dimension(200, 500));
			sp = new JScrollPane(jta_histoire,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp.setPreferredSize(new Dimension(200, 400));

			jlb_information = new JLabel(NUM.INFO);
			jlb_annonce = new JLabel(NUM.STARTCMD);
			jbt_play = new JButton(NUM.START);
			this.setLayout(new GridBagLayout());

			GridBagConstraints gbc0 = new GridBagConstraints();
			gbc0.fill = GridBagConstraints.VERTICAL;
			gbc0.gridx = 0;
			gbc0.gridy = 5;
			this.add(sp, gbc0);

			GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.fill = GridBagConstraints.VERTICAL;
			gbc1.gridx = 0;
			gbc1.gridy = 6;
			this.add(jbt_play, gbc1);

			GridBagConstraints gbc2 = new GridBagConstraints();
			gbc2.fill = GridBagConstraints.VERTICAL;
			gbc2.gridx = 0;
			gbc2.gridy = 7;
			this.add(jlb_information, gbc2);

			GridBagConstraints gbc3 = new GridBagConstraints();
			gbc3.fill = GridBagConstraints.VERTICAL;
			gbc3.gridx = 0;
			gbc3.gridy = 8;
			this.add(jlb_annonce, gbc3);
			jbt_play.addActionListener(this);
		}

		public void setAnnonce(String text) {
			jlb_annonce.setText(text);
		}

		public void ajoutHistoire(String text) {
			String _text = jta_histoire.getText();
			_text += text + "\n";
			jta_histoire.setText(_text);
		}

		public void setTextPlay(String text) {
			jbt_play.setText(text);
		}

		public void actionPerformed(ActionEvent evt) {
			Object src = evt.getSource();
			int cell[] = new int[2];
			if (src == jbt_play) {
				if (!start) {
					plateau = new BoardGUI();
					plateau.output(NUM.FILE_NAME);
					start = true;
					jbt_play.setText(NUM.PLAY);
					setAnnonce(NUM.STARTCMD);
					GomokuGUI.this.repaint();
					jta_histoire.setText("Begin !!!!\n===============");
					return;
				} else {
					plateau.input(NUM.FILE_NAME);
					if(nb_tour == 0)
					{
						setAnnonce(NUM.PLAYING);
						if (plateau.isBlank()) {
							player = 2;
						}
						else
							player = 1;
					}
					nb_tour++;
					plateau.input(NUM.FILE_NAME);
					assistant.getMove(player, cell, plateau);
					plateau.setStateCell(player, cell[0], cell[1]);
					if (player == 1) {
						controlpanel.ajoutHistoire("Tour " + nb_tour
								+ " de O : " + cell[0] + " - " + cell[1]);
					} else {
						controlpanel.ajoutHistoire("Tour " + nb_tour
								+ " de X : " + cell[0] + " - " + cell[1]);
					}
					plateau.output(NUM.FILE_NAME);
					GomokuGUI.this.repaint();
					if (getWinter() == player) {
						if (player == 1)
							controlpanel.setAnnonce(NUM.OWINCMD);
						if (player == 2)
							controlpanel.setAnnonce(NUM.XWINCMD);
						//jbt_play.setText(NUM.RESTART);
						jbt_play.setEnabled(false);
						return;
					}
					if(getWinter() == 3-player)
					{
						if (player == 2)
							controlpanel.setAnnonce(NUM.OWINCMD);
						if (player == 1)
							controlpanel.setAnnonce(NUM.XWINCMD);
						//jbt_play.setText(NUM.RESTART);
						jbt_play.setEnabled(false);
						return;
					}
					if(nb_tour > 100)
					{
						controlpanel.setAnnonce(NUM.NULLCMD);
						start = false;
						nb_tour = 0;
						jbt_play.setEnabled(false);
						return;
					}
					repaint();
				}
			}
		}

	}

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (start) {
				if (plateau.isBlank()) {
					player = 1;
				}
				GomokuGUI.this.repaint();
				optimiserLocationAndSetSate(e.getPoint());
				if(nb_tour > 100)
				{
					controlpanel.setAnnonce(NUM.NULLCMD);
					start = false;
					nb_tour = 0;
					return;
				}
			}
		}
	}

	private void display() {
		JFrame f = new JFrame(NUM.TITLE);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(800, 600));
		Container content = f.getContentPane();
		content.setLayout(new FlowLayout());
		content.add(this);
		content.add(controlpanel);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new GomokuGUI().display();
			}
		});

	}
}
