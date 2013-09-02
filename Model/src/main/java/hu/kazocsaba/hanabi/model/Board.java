package hu.kazocsaba.hanabi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The cards that have been played.
 * 
 * @author Kazó Csaba
 */
public final class Board {
	private final Map<Suit, Rank> topPlayedCards = new HashMap<>();
	
	/**
	 * Returns whether it is valid to play a given card.
	 * 
	 * @param card the card to be played
	 * @return whether playing the card is a valid move
	 */
	public boolean canPlay(Card card) {
		return card.getRank().getValue() == getTopValue(card.getSuit()) + 1;
	}
	
	/**
	 * Plays a card.
	 * 
	 * @param card the card to play
	 * @throws IllegalStateException if playing the card is not a valid move
	 */
	public void play(Card card) {
		if (!canPlay(card))
			throw new IllegalStateException("Invalid play");
		topPlayedCards.put(card.getSuit(), card.getRank());
	}
	
	/**
	 * Returns the top played card's value for a given suit. If no card has been played for that
	 * suit, the return value is zero, otherwise the top {@link Rank#getValue() value} of the suit
	 * is returned.
	 * 
	 * @param suit a suit
	 * @return the top value of the suit on the board
	 */
	public int getTopValue(Suit suit) {
		Objects.requireNonNull(suit);
		Rank topRank = topPlayedCards.get(suit);
		if (topRank == null)
			return 0;
		else
			return topRank.getValue();
	}
}
