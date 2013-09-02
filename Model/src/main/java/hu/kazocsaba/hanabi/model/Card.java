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
	private final int id;

	/**
	 * Creates a new card.
	 * @param suit the suit of the new card
	 * @param rank the rank of the new card
	 * @param id a number used to identify cards during a game
	 * @throws NullPointerException if either argument is {@code null}
	 */
	public Card(Suit suit, Rank rank, int id) {
		this.suit = Objects.requireNonNull(suit);
		this.rank = Objects.requireNonNull(rank);
		this.id = id;
	}

	public UnknownCard asUnknown() {
		return new UnknownCard(id);
	}
	
	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	public int getId() {
		return id;
	}

	@Override
	public int compareTo(Card o) {
		return ComparisonChain.start()
					.compare(suit, o.suit)
					.compare(rank, o.rank)
					.compare(id, o.id)
					.result();
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + suit.hashCode();
		hash = 97 * hash + rank.hashCode();
		hash = 97 * hash + id;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Card))
			return false;
		final Card other = (Card) obj;
		return suit == other.suit && rank == other.rank && id == other.id;
	}
	
}
