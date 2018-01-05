
public class Word implements Comparable {

	private int length = 0;
	private int direction = 0; // 0 is horizontal, 1 is vertical
	private int xCoordinate;
	private int yCoordinate;

	/** Constructor for Word object. */
	public Word(int l, int d, int x, int y) {
		length = l;
		direction = d;
		xCoordinate = x;
		yCoordinate = y;
	}

	/** Getter for Word's length. */
	public int getLength() {
		return length;
	}

	/** Getter for Word's direction. */
	public int getDirection() {
		return direction;
	}

	/** Getter for Word's X coordinate. */
	public int getX() {
		return xCoordinate;
	}

	/** Getter for Word's Y coordinate. */
	public int getY() {
		return yCoordinate;
	}

	/** Makes Word objects sort in reverse order. */
	public int compareTo(Object word2) {
		int w1length = this.length;
		int w2length = ((Word) word2).length;
		if (w1length > w2length) {
			return -1;
		} else if (w2length > w1length) {
			return 1;
		} else {
			return 0;
		}
	}

}
