import static org.junit.Assert.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;

public class FixedLayoutTest {

	FixedLayout fixedLayout;
	char[][] layout;
	ArrayList<Word> wordList;

	@Before
	public void setUp() throws Exception {
		fixedLayout = new FixedLayout();
		layout = fixedLayout.getLayout();
		wordList = fixedLayout.getWordList();
	}

	@Test
	public void wordListIsInOrder() {
		for (int i = 0; i < wordList.size() - 1; i++) {
			assertTrue(wordList.get(i).getLength() >= wordList.get(i + 1).getLength());
		}
	}

}
