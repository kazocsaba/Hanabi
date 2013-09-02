package hu.kazocsaba.hanabi.player.api;

import hu.kazocsaba.hanabi.model.Card;
import hu.kazocsaba.hanabi.model.Player;
import hu.kazocsaba.hanabi.model.Rank;
import hu.kazocsaba.hanabi.model.Suit;
import hu.kazocsaba.hanabi.model.UnknownCard;
import java.util.List;
import java.util.Objects;

/**
 * The superclass of the actions a player can take.
 *
 * @author Kazó Csaba
 */
public abstract class Action {
	private Action() {}
	
	/**
	 * The action of giving a hint to another player. The hint reveals the exact set of cards in
	 * the target player's hand that have a certain rank or suit.
	 */
	public static class GiveHint extends Action {
		private final Player target;
		private final Rank rank;
		private final Suit suit;

		private GiveHint(Player target, Rank rank, Suit suit) {
			this.target = target;
			this.rank = rank;
			this.suit = suit;
		}
		public static GiveHint create(Player target, Rank rank) {
			return new GiveHint(Objects.requireNonNull(target), Objects.requireNonNull(rank), null);
		}
		public static GiveHint create(Player target, Suit suit) {
			return new GiveHint(Objects.requireNonNull(target), null, Objects.requireNonNull(suit));
		}

		public Rank getRank() {
			return rank;
		}

		public Suit getSuit() {
			return suit;
		}

		public Player getTarget() {
			return target;
		}
		
		public boolean matches(Card card) {
			return rank != null ? rank == card.getRank() : suit == card.getSuit();
		}
		
		public void provideFeedback(SelfActionFeedback feedback) {
			if (rank == null)
				feedback.givesHint(target, suit);
			else
				feedback.givesHint(target, rank);
		}

		public void provideFeedback(OtherPlayerActionFeedback feedback, List<UnknownCard> hintedCards) {
			if (rank == null)
				feedback.givesHintToMe(hintedCards, suit);
			else
				feedback.givesHintToMe(hintedCards, rank);
		}
		public void provideFeedback(OtherPlayerActionFeedback feedback) {
			if (rank == null)
				feedback.givesHintToOther(target, suit);
			else
				feedback.givesHintToOther(target, rank);
		}
	}
	/**
	 * The action of discarding a card.
	 */
	public static class Discard extends Action {
		private final UnknownCard card;

		private Discard(UnknownCard card) {
			this.card = card;
		}
		
		public static Discard create(UnknownCard card) {
			return new Discard(Objects.requireNonNull(card));
		}

		public UnknownCard getCard() {
			return card;
		}
		
	}
	/**
	 * The action of playing a card.
	 */
	public static class Play extends Action {
		private final Card card;

		private Play(Card card) {
			this.card = card;
		}
		
		public static Play create(Card card) {
			return new Play(Objects.requireNonNull(card));
		}

		public Card getCard() {
			return card;
		}
		
	}
}
