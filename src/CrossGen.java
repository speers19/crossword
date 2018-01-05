
/**
 * Make sure "clues.csv" is in the project folder.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import edu.princeton.cs.algs4.*;

public class CrossGen {

	HashMap<String, ArrayList<String>> clueTable = new HashMap<String, ArrayList<String>>();
	ArrayList<String> answersOfLength = new ArrayList<String>();
	FixedLayout fixedLayout = new FixedLayout();
	char[][] layout = new char[5][5];
	ArrayList<Word> wordList;
	ArrayList<String> clueList = new ArrayList<String>();
	ArrayList<ArrayList<Integer>> clueInfo = new ArrayList<ArrayList<Integer>>(); 
	
	/**
	 * Takes parsed CSV file and adds answers as keys, and clues as values.
	 * There can be multiple clues to an answer.
	 */
	public void hashCluesAndAnswers(List<String> line) {

		String clue = line.get(13);
		String answer = line.get(14);
		ArrayList<String> clues;

		if (clueTable.containsKey(answer)) {
			clues = clueTable.get(answer);
		} else {
			clues = new ArrayList<String>();
		}

		clues.add(clue);
		clueTable.put(answer, clues);
	}

	/** Uses the CSVReader class to parse lines from the clues.csv file. */
	public void parseCSV() {

		String csvFile = "clues.csv";

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(csvFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			List<String> line = CSVReader.parseLine(scanner.nextLine());
			hashCluesAndAnswers(line);
		}

		scanner.close();

	}

	/** Prints the current state of the puzzle layout. */
	public void printPuzzle(char[][] puzzle) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				StdOut.print(puzzle[i][j]);
				StdOut.print(" ");
			}
			StdOut.println();
		}
	}

	/** Prints all the clues used by answers in the finished panel. */
	public void printClues() {
		for (int i = 0; i < clueList.size(); i++) {
			StdOut.println(clueList.get(i));
		}

	}

	/**
	 * Goes through hash table of clues and answers and adds answer to
	 * answersOfLength ArrayList if it match the length of the parameter word.
	 */
	public void populateAnswersOfLength(Word word) {
		answersOfLength.clear();
		for (String answer : clueTable.keySet()) {
			if (answer.length() == word.getLength()) {
				answersOfLength.add(answer);
			}
		}
	}

	/**
	 * Returns true if a given Word object and answer text will clash with an
	 * answer that is already on the board. Returns false if the given Word
	 * object and answer text will fit.
	 */
	public boolean checkIfWordContradicts(Word word, String wordText) {
		int counter = 0;
		boolean contradiction = false;

		if (word.getDirection() == 0) { // try not to include uncommon letters
										// until the last word
			for (int i = 0; i < wordText.length(); i++) {
				if (wordText.charAt(i) == 'Z' || wordText.charAt(i) == 'J' || wordText.charAt(i) == 'X'
						|| wordText.charAt(i) == 'G' || wordText.charAt(i) == 'Q' || wordText.charAt(i) == 'K'
						|| wordText.charAt(i) == 'V' || wordText.charAt(i) == 'B') {
					contradiction = true;
				}
			}
		}

		if (contradiction == true) {
			return contradiction;
		}

		if (word.getDirection() == 0) {
			for (int i = 0; i < word.getLength(); i++) {
				if (layout[word.getY()][word.getX() + counter] != '_') {
					if (layout[word.getY()][word.getX() + counter] != wordText.charAt(counter)) {
						contradiction = true;
					}
				}
				counter++;
			}
		} else if (word.getDirection() == 1) {
			for (int i = 0; i < word.getLength(); i++) {
				if (layout[word.getY() + counter][word.getX()] != '_') {
					if (layout[word.getY() + counter][word.getX()] != wordText.charAt(counter)) {
						contradiction = true;
					}
				}
				counter++;
			}
		}
		return contradiction;
	}

	/**
	 * Adds given answer text to the puzzle layout based on information in the
	 * given Word object.
	 */
	public void addToPuzzle(Word word, String wordText) {
		int counter = 0;
		if (word.getDirection() == 0) {
			for (int i = 0; i < word.getLength(); i++) {
				layout[word.getY()][word.getX() + counter] = wordText.charAt(counter);
				counter++;
			}
		} else if (word.getDirection() == 1) {
			for (int i = 0; i < word.getLength(); i++) {
				layout[word.getY() + counter][word.getX()] = wordText.charAt(counter);
				counter++;
			}
		}
		addToClueList(word, wordText);
	}

	/**
	 * Adds a random clue associated with an answer to a list of clues, and
	 * updates clueInfo correspondingly
	 */
	public void addToClueList(Word word, String wordText) {

		// String clueCoords = word.getX() + ", " + word.getY() + " ";
		// String clueDirection;

		// if (word.getDirection() == 0) {
		// clueDirection = "Across: ";
		// } else {
		// clueDirection = "Down: ";
		// }

		ArrayList<String> clues = clueTable.get(wordText);

		String clue = clues.get(StdRandom.uniform(clues.size()));
		clueList.add(clue);
		
		ArrayList<Integer> clueCoords = new ArrayList<Integer>();
		
		clueCoords.add(word.getX());
		clueCoords.add(word.getY());
		clueCoords.add(word.getDirection());
		
		clueInfo.add(clueCoords);
	}

	/**
	 * Checks to see if there are any blank spaces in the puzzle layout. If not,
	 * returns true.
	 */
	public boolean checkCompletion(char[][] layout) {
		boolean complete = true;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (layout[i][j] == '_') {
					complete = false;
				}
			}
		}
		return complete;
	}

	/** Hashes CSV information and gets layout information. Used for tests. */
	public void initialize() {
		parseCSV();
		layout = fixedLayout.getLayout();
		wordList = fixedLayout.getWordList();
	}

	/**
	 * Our main non-static method! Initializes clues/answers/wordList/puzzle
	 * layout, then goes through each Word object, assigns a random answer of
	 * the same length, and tries to put it on the board. If it contradicts
	 * something on the board already, try a different random word. Once there
	 * are no words left that fit, exits the program.
	 */
	public boolean run() {
		initialize();

		for (Word word : wordList) {
			if (answersOfLength.isEmpty()) {
				populateAnswersOfLength(word);
			} else if (!answersOfLength.isEmpty() && answersOfLength.get(0).length() != word.getLength()) {
				populateAnswersOfLength(word);
			}

			String wordText = "";
			boolean contradiction = true;
			while (contradiction) {
				if (answersOfLength.size() == 0) {
					// StdOut.println("Fatal error trying to add word with
					// length " + word.getLength() + " with direction "
					// + word.getDirection() + " at coordinates " + word.getX()
					// + ", " + word.getY());
					// printPuzzle(layout);
					return false;
				}
				wordText = answersOfLength.get(StdRandom.uniform(answersOfLength.size()));
				answersOfLength.remove(wordText);
				contradiction = checkIfWordContradicts(word, wordText);
			}

			addToPuzzle(word, wordText);

			if (checkCompletion(layout)) {
				StdOut.println("Crossword complete");
				StdOut.println("");
				printPuzzle(layout);
				StdOut.println("");
				printClues();
				return true;
			}
		}
		return false;
	}

}
