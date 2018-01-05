import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CrossGenTest {

	CrossGen model;

	@Before
	public void setUp() throws Exception {
		model = new CrossGen();
		model.initialize();
	}

	@Test
	public void hasherHashesMultipleAnswersToSameClue() {
		assertTrue(model.clueTable.containsKey("PALES"));
		assertTrue(model.clueTable.get("PALES").contains("Lightens up?"));
		assertTrue(model.clueTable.get("PALES").contains("Turns white"));
		assertTrue(model.clueTable.get("PALES").contains("Loses color"));
		assertFalse(model.clueTable.get("PALES").contains("Blanched"));
	}

	@Test
	public void answersOfLengthPopulates() {
		model.populateAnswersOfLength(model.wordList.get(0));
		for (String answer : model.answersOfLength) {
			assertTrue(answer.length() == 5);
		}
	}

	@Test
	public void answersAdded() {
		Word add = new Word(3, 0, 0, 0);
		model.addToPuzzle(add, "ADD");

		assertTrue(model.layout[0][0] == 'A');
		assertTrue(model.layout[0][1] == 'D');
		assertTrue(model.layout[0][2] == 'D');
	}

	@Test
	public void contradictionsAreCaught() {
		Word test = new Word(5, 0, 0, 0);
		Word contradiction = new Word(5, 1, 2, 0);
		Word noContradiction = new Word( 5, 1, 2, 0);

		model.addToPuzzle(test, "MONTY");

		assertTrue(model.checkIfWordContradicts(contradiction, "CLASH") == true); //A in CLASH should clash with N in MONTY
		assertFalse(model.checkIfWordContradicts(noContradiction, "NOSIR") == true); //N in NOSIR should not clash with N in MONTY

		Word test2 = new Word(3, 1, 0, 2); 
		assertTrue(model.checkIfWordContradicts(test2, "CLA") == true); //L in CLA should clash with an @ sign
	}
	
	@Test
	public void checkCompletionChecksCorrectly() {
		
	}

}
