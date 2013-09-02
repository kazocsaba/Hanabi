package hu.kazocsaba.hanabi.model;

/**
 * A card that is unknown to a player.
 *
 * @author Kazó Csaba
 */
public final class UnknownCard {
	private final int id;

	/**
	 * Creates a new unknown card.
	 * 
	 * @param id a number used to identify this card during a game
	 */
	public UnknownCard(int id) {
		this.id = id;
	}

	/**
	 * Returns the ID of the card.
	 * 
	 * @return a number used to identify this card during a game
	 */
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + id;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UnknownCard && ((UnknownCard)obj).id == id;
	}
	
}
