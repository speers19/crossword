import edu.princeton.cs.algs4.StdOut;

public class Mosaic {

	char[][] board = new char[11][11];
	CrossGen panel0;
	CrossGen panel1;
	CrossGen panel2;
	CrossGen panel3;

	public void initializeBoard() {

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				board[i][j] = '_';
			}
		}

		for (int i = 0; i < 11; i++) {
			board[5][i] = '@';
			board[i][5] = '@';
		}
	}

	public void printPuzzle() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				StdOut.print(board[i][j]);
				StdOut.print(" ");
			}
			StdOut.println();
		}

		printClues(panel0, 0);
		printClues(panel1, 1);
		printClues(panel2, 2);
		printClues(panel3, 3);
	}

	public void printClues(CrossGen panel, int panelNo) {

		// clueList = panel.clueList;
		// clueInfo = panel.clueInfo;

		int X; // x coordinate
		int Y; // y coordinates
		int D; // direction...0 across, 1 down
		int R = panelNo & 1; // if panelNo is odd, it has been rotated
		String direction;

		for (int i = 0; i < panel.clueList.size(); i++) {
			X = panel.clueInfo.get(i).get(0);
			Y = panel.clueInfo.get(i).get(1);
			D = panel.clueInfo.get(i).get(2);

			if (D == R) {
				direction = "Across: ";
			} else {
				direction = "Down: ";
			}

			if (panelNo == 1) {
				Y = Y + 6;
			} else if (panelNo == 2) {
				X = X + 6;
				Y = Y + 6;
			} else if (panelNo == 3) {
				X = X + 6;
			}

			StdOut.print(X + ", " + Y + " " + direction);
			StdOut.print(panel.clueList.get(i));
			StdOut.println();
		}
	}

	public char[][] rotateArray(char[][] layout) {
		char[][] rot = new char[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				rot[j][4 - i] = layout[i][j];
			}
		}

		char[][] flip = new char[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				flip[i][Math.abs(j - 4)] = rot[i][j];
			}
		}
		return flip;
	}

	public void addToBoard(char[][] panel, int panelNo) {
		if ((panelNo & 1) != 0) {
			panel = rotateArray(panel);
		}

		if (panelNo == 0) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					board[i][j] = panel[i][j];
				}
			}
		} else if (panelNo == 1) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					board[i][j + 6] = panel[i][j];
				}
			}
		} else if (panelNo == 2) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					board[i + 6][j + 6] = panel[i][j];
				}
			}
		} else if (panelNo == 3) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					board[i + 6][j] = panel[i][j];
				}
			}
		}
	}

	public void run() {

		initializeBoard();

		boolean complete = false;

		StdOut.println("Working...");

		while (!complete) {
			panel0 = new CrossGen();
			complete = panel0.run();
			if (!complete) {
				StdOut.println("Panel 0 trying again");
			}
		}

		StdOut.println("25% done...");

		complete = false;

		while (!complete) {
			panel1 = new CrossGen();
			complete = panel1.run();
			if (!complete) {
				StdOut.println("Panel 1 trying again");
			}
		}

		StdOut.println("50% done...");

		complete = false;

		while (!complete) {
			panel2 = new CrossGen();
			complete = panel2.run();
			if (!complete) {
				StdOut.println("Panel 2 trying again");
			}
		}

		StdOut.println("75% done...");

		complete = false;

		while (!complete) {
			panel3 = new CrossGen();
			complete = panel3.run();
			if (!complete) {
				StdOut.println("Panel 3 trying again");
			}
		}

		StdOut.println("Complete!");

		addToBoard(panel0.layout, 0);
		addToBoard(panel1.layout, 1);
		addToBoard(panel2.layout, 2);
		addToBoard(panel3.layout, 3);

		printPuzzle();

		System.exit(0);
	}

	public static void main(String[] args) {
		new Mosaic().run();
	}

}
