package gomoku.algorithm;

import gomoku.kernel.Board;

public class Assistant {
	private int LEVEL;
	private boolean space;
	private int offre;
	private boolean att;

	public Assistant(int level) {
		LEVEL = level;
	}

	public void getMove(int state, int cell[], Board board) {
		switch (LEVEL) {
		case NUM.AMATEUR:
			evalScore(state, cell, board);
			break;
		case NUM.SEMIPRO:
			break;
		case NUM.PROFESIONAL:
			break;
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////
	public boolean isBoard(int r, int c) {
		if (r == 0 || c == 0 || r == NUM.WINSIZE - 1 || c == NUM.WINSIZE - 1)
			return true;
		return false;
	}

	public int getLength(int state, int r, int c, int direction, Board board) {
		int num = 0;
		int dx = NUM.DX[direction];
		int dy = NUM.DY[direction];
		offre = 0;
		space = false;
		while (board.getStateCell(r, c) == state) {
			num++;
			r += dy;
			c += dx;
			if (r < 0 || r >= NUM.BOARDSIZE || c < 0 || c >= NUM.BOARDSIZE)
				break;
			if (board.getStateCell(r, c) == 0) {
				space = true;
				break;
			}
		}
		r += dy;
		c += dx;
		if((!space) || r < 0 || r >= NUM.BOARDSIZE || c < 0 || c >= NUM.BOARDSIZE)
			return num;
		if(board.getStateCell(r, c) == state)
			space = false;
		while (board.getStateCell(r, c) == state) {
			offre++;
			r += dy;
			c += dx;
			if (r < 0 || r >= NUM.BOARDSIZE || c < 0 || c >= NUM.BOARDSIZE)
				break;
			if (board.getStateCell(r, c) == 0) {
				space = true;
				break;
			}
		}
		return num;
	}

	public int getLengthFree(int state, int r, int c, int direction, Board board) {
		int num = 0;
		int dx = NUM.DX[direction];
		int dy = NUM.DY[direction];
		while (r >= 0 && r < NUM.BOARDSIZE && c >= 0 && c < NUM.BOARDSIZE && 
				(board.getStateCell(r, c) != 3 - state)) {
				num++;
				r += dy;
				c += dx;
		}
		return num;
	}

	public long calScore(int state, int r, int c, Board board) {
		int d1, d2;
		int num;
		att = false;
		int plus[] = new int[NUM.WINSIZE];
		int len_free = 0;
		int score_len[] = new int[NUM.WINSIZE];
		long score = 0;
		boolean space1;
		boolean space2;
		int offre1 = 0;
		int offre2 = 0;
		for (d1 = 0; d1 < 4; d1++) {
			num = getLength(state, r, c, d1, board);
			len_free = getLengthFree(state, r, c, d1, board);
			offre1 = offre;
			space1 = space;
			d2 = d1 + 4;
			num += getLength(state, r, c, d2, board) - 1;
			len_free += getLengthFree(state, r, c, d2, board) - 1;
			offre2 = offre;
			space2 = space;
			offre = Math.max(offre1, offre2);
			if(num + offre < NUM.WINSIZE - 1)
				num += offre;
			
			if(len_free < NUM.WINSIZE)
			{
				num = 0;
			}
			else if ((!space1 || !space2) && num < NUM.WINSIZE)
			{
				if(num == NUM.WINSIZE-1)
					att = true;
				num--;
				plus[num]++;
			}
			if (num < 1)
				num = 1;
			if (num > NUM.WINSIZE)
				num = NUM.WINSIZE;
			score_len[num - 1]++;
		}

		for (int i = 1; i < NUM.WINSIZE; i++) {
			score += score_len[i] * Math.pow(i + 1, i + 1) + plus[i];
		}
		if (r == NUM.BOARDSIZE / 2 && c == NUM.BOARDSIZE / 2)
			score += 2;
		else if (r == NUM.BOARDSIZE / 2 + 2 && c == NUM.BOARDSIZE / 2 + 2)
			score += 1;

		return score;
	}

	public void evalScore(int state, int cell[], Board board) {
		int _r, _c;
		long _score = 0;
		long rival_score = 0;
		long _rival_score = 0;
		long score = 0;
		long total_score = 0;
		long _total_score = 0;
		int best_total_r = 0;
		int best_total_c = 0;
		int rival = 3 - state;
		cell[0] = 0;
		cell[1] = 0;
		for (_r = 0; _r < NUM.BOARDSIZE; _r++) {
			for (_c = 0; _c < NUM.BOARDSIZE; _c++) {
				if (board.getStateCell(_r, _c) == 0) {
					board.setStateCell(state, _r, _c);
					_score = calScore(state, _r, _c, board);
					if(att && _score < NUM.ATTCORE)
						_score = NUM.ATTCORE;
					if (_score > score) {
						score = _score;
						cell[0] = _r;
						cell[1] = _c;
					}
					board.setStateCell(0, _r, _c);
					if(score >= NUM.WINSCORE)
					{
						cell[0] = _r;
						cell[1] = _c;
						return;
					}
					
					board.setStateCell(rival, _r, _c);
					_rival_score = calScore(rival, _r, _c, board);
					if (_rival_score > rival_score) {
						rival_score = _rival_score;
					}
					board.setStateCell(0, _r, _c);
					_total_score = _score + _rival_score;
					if(_total_score > total_score)
					{
						total_score = _total_score;
						best_total_r = _r;
						best_total_c = _c;
					}
				}
			}
		}
		if ((rival_score >= Math.pow(NUM.WINSIZE-2, NUM.WINSIZE-2))) {
			cell[0] = best_total_r;
			cell[1] = best_total_c;
		}
		
		return;
	}

	public void evalScoreNew(int state, int cell[], Board board) {
		int _r, _c;
		long _score = Integer.MIN_VALUE;
		long rival_score = 0;
		long score = 0;
		int rival = 3 - state;
		cell[0] = 0;
		cell[1] = 0;
		for (_r = 0; _r < NUM.BOARDSIZE; _r++) {
			for (_c = 0; _c < NUM.BOARDSIZE; _c++) {
				if (board.getStateCell(_r, _c) == 0) {
					board.setStateCell(state, _r, _c);
					score = calScore(state, _r, _c, board);
					if(score >= NUM.WINSCORE)
					{
						_score = score;
						cell[0] = _r;
						cell[1] = _c;
						return;
					}
					board.setStateCell(0, _r, _c);
					board.setStateCell(rival, _r, _c);
					rival_score = calScore(rival, _r, _c, board);
					board.setStateCell(0, _r, _c);
					score = score + rival_score;
					if (score > _score) {
						_score = score;
						cell[0] = _r;
						cell[1] = _c;
					}

				}
			}
		}
		return;
	}

	public boolean isWiner(int state, Board board) {
		int num;
		int d2;
		for (int i = 0; i < NUM.BOARDSIZE; i++) {
			for (int j = 0; j < NUM.BOARDSIZE; j++) {
				num = 0;
				for (int d1 = 0; d1 < 4; d1++) {
					d2 = d1 + 4;
					num = getLength(state, i, j, d1, board);
					num += getLength(state, i, j, d2, board) - 1;
					if (num >= NUM.WINSIZE)
						return true;
				}
			}
		}
		return false;
	}
}
