package hu.kazocsaba.hanabi.model;

import com.google.common.collect.ComparisonChain;
import java.util.Objects;

/**
 * A Hanabi card. It consists of a suit and a rank, for example red 3.
 * <p>
 * Instances of this class are immutable.
 * @author Kazó Csaba
 */
public final class Card implements Comparable<Card> {

	private final Suit suit;
	private final Rank rank;

	/**
	 * Creates a new card.
	 * @param suit the suit of the new card
	 * @param rank the rank of the new card
	 * @throws NullPointerException if either argument is {@code null}
	 */
	public Card(Suit suit, Rank rank) {
		this.suit = Objects.requireNonNull(suit);
		this.rank = Objects.requireNonNull(rank);
	}

	@Override
	public int compareTo(Card o) {
		return ComparisonChain.start().compare(suit, o.suit).compare(rank, o.rank).result();
	}
}
