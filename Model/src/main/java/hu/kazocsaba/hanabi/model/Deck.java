package hu.kazocsaba.hanabi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The cards in a game that have not been drawn yet.
 *
 * @author Kazó Csaba
 */
public final class Deck {

	private final List<Card> cards;

	private Deck(List<Card> cards) {
		this.cards = cards;
	}

	/**
	 * Returns whether the deck is empty.
	 *
	 * @return {@code true} if there are no more cards in this deck, {@code false} otherwise
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	/**
	 * Removes and returns the next card in the deck.
	 *
	 * @return the next card
	 * @throws IllegalStateException if the deck is empty
	 * @see #isEmpty()
	 */
	public Card draw() {
		if (isEmpty())
			throw new IllegalStateException("Deck is empty");
		return cards.remove(cards.size() - 1);
	}

	/**
	 * Creates a traditional, randomly shuffled deck. All the cards will have different IDs.
	 *
	 * @return a deck containing, for each of the five suits, three instances of 1, two instances of
	 * 2, 3, and 4, and one instance of 5 cards, for a total of 50 cards, in random order
	 */
	public static Deck createRandom() {
		List<Card> cards = new ArrayList<>(3 * 5 + 3 * 2 * 5 + 5);
		int id = 0;
		for (Suit suit : Suit.values()) {
			for (int i = 0; i < 3; i++) {
				cards.add(new Card(suit, Rank.ONE, id++));
			}
			for (int i = 0; i < 2; i++) {
				cards.add(new Card(suit, Rank.TWO, id++));
				cards.add(new Card(suit, Rank.THREE, id++));
				cards.add(new Card(suit, Rank.FOUR, id++));
			}
			cards.add(new Card(suit, Rank.FIVE, id++));
		}
		Collections.shuffle(cards);
		return new Deck(cards);
	}
}
