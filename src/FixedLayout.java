import java.util.*;

import edu.princeton.cs.algs4.StdOut;

public class FixedLayout {

	private char[][] layout = new char[][] {
		{'_', '_', '_', '_', '_'} ,
		{'@', '@', '_', '@', '@'} ,
		{'_', '_', '_', '_', '_'} ,
		{'@', '@', '_', '@', '@'} ,
		{'_', '_', '_', '_', '_'} };
		
	ArrayList<Word> wordList = new ArrayList<Word>();

	/**
	 * Runs through layout according to direction, making note of where words will
	 * go and recording their length, direction and starting coordinates. Adds these
	 * words to wordList
	 */
	public void populateWordList(int direction) {
		int counter;

		if (direction == 0) {
			for (int y = 0; y < 5; y++) {
				counter = 0;
				for (int x = 0; x < 5; x++) {
					if (layout[y][x] == ('_')) {
						counter++;
					} else if (layout[y][x] == ('@')) {
						if (counter > 0) {
							wordList.add(new Word(counter, direction, x - counter, y));
						}
						counter = 0;
					}
					if (x == 4 && counter > 0) {
						wordList.add(new Word(counter, direction, x + 1 - counter, y));
					}
				}
			}
		} else {
			for (int x = 0; x < 5; x++) {
				counter = 0;
				for (int y = 0; y < 5; y++) {
					if (layout[y][x] == ('_')) {
						counter++;
					} else if (layout[y][x] == ('@')) {
						if (counter > 0) {
							wordList.add(new Word(counter, direction, x, y - counter));
						}
						counter = 0;
					}
					if (y == 4 && counter > 0) {
						wordList.add(new Word(counter, direction, x, y + 1 - counter));
					}
				}
			}
		}
		Collections.sort(wordList); // sorts in reverse order
	}

	/** Getter for layout. */
	public char[][] getLayout() {
		return layout;
	}

	/** Getter for wordList, also generates the wordList. */
	public ArrayList<Word> getWordList() {
		populateWordList(0);
		populateWordList(1);
		return wordList;
	}

}
