package hu.kazocsaba.hanabi.player.api;

import hu.kazocsaba.hanabi.model.Card;
import hu.kazocsaba.hanabi.model.Player;
import hu.kazocsaba.hanabi.model.Rank;
import hu.kazocsaba.hanabi.model.Suit;
import hu.kazocsaba.hanabi.model.UnknownCard;
import java.util.List;

/**
 * Represents actions performed by other players as perceived by a particular player.
 *
 * @author Kazó Csaba
 */
public interface OtherPlayerActionFeedback {

	/**
	 * The player performing these actions.
	 *
	 * @return one of the other players
	 */
	public Player getPlayer();

	/**
	 * Returns the player whose perception is represented by this object.
	 *
	 * @return this player
	 */
	public Player getSelf();

	/**
	 * The player draws a card.
	 *
	 * @param card the card that the player has drawn
	 */
	public void draws(Card card);

	/**
	 * The player discards a card.
	 *
	 * @param card the card that the player has discarded
	 */
	public void discards(Card card);

	/**
	 * The player plays a card.
	 *
	 * @param card the card this player has played
	 * @param success whether the card has been successfully played; if {@code false}, then this card
	 * remains in the player's hand in its original position
	 */
	public void plays(Card card, boolean success);

	/**
	 * The player gives a hint to another player.
	 *
	 * The target player learns which of her cards have the given rank.
	 *
	 * @param target the player receiving the hint
	 * @param rank the rank that is revealed
	 */
	public void givesHintToOther(Player target, Rank rank);

	/**
	 * The player gives a hint to another player.
	 *
	 * The target player learns which of her cards have the given suit.
	 *
	 * @param target the player receiving the hint
	 * @param suit the suit that is revealed
	 */
	public void givesHintToOther(Player target, Suit suit);

	/**
	 * The player gives a hint to 'self'.
	 *
	 * @param cards the cards whose rank is revealed
	 * @param rank the rank of the cards
	 */
	public void givesHintToMe(List<UnknownCard> cards, Rank rank);

	/**
	 * The player gives a hint to 'self'.
	 *
	 * @param cards the cards whose suit is revealed
	 * @param suit the suit of the cards
	 */
	public void givesHintToMe(List<UnknownCard> cards, Suit suit);
}
