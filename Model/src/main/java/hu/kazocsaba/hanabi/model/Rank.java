package hu.kazocsaba.hanabi.model;

/**
 * The possible ranks (numbers) of a Hanabi card.
 * @author Kazó Csaba
 */
public enum Rank {
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5);
	private final int value;

	private Rank(int value) {
		this.value = value;
	}

	/**
	 * Returns the number value of this rank.
	 * 
	 * @return the number value of this rank (1 for ONE, 2 for TWO etc.)
	 */
	public int getValue() {
		return value;
	}
	
}
