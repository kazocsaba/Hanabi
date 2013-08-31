package hu.kazocsaba.hanabi.player.api;

import hu.kazocsaba.hanabi.model.Player;
import hu.kazocsaba.hanabi.model.Rank;
import hu.kazocsaba.hanabi.model.Suit;
import hu.kazocsaba.hanabi.model.UnknownCard;

/**
 * The interface a player can use to communicate. Calling those functions that correspond to game
 * actions only expresses intent, the player must not assume that any of her actions are performed
 * until she receives the appropriate callback to {@link SelfActionFeedback}.
 *
 * @author Kazó Csaba
 */
public interface Action {

	/**
	 * This player has received and processed a feedback. This function should be called in response
	 * to every callback received through the interfaces
	 * {@link GameFeedback}, {@link SelfActionFeedback}, and {@link OtherPlayerActionFeedback}.
	 */
	public void acknowledged();

	/**
	 * This player intends to give a hint to another player.
	 *
	 * The target player will learn which of her cards have the given rank.
	 *
	 * @param target the player receiving the hint
	 * @param rank the rank that is revealed
	 */
	public void giveHint(Player target, Rank rank);

	/**
	 * This player intends to give a hint to another player.
	 *
	 * The target player will learn which of her cards have the given suit.
	 *
	 * @param target the player receiving the hint
	 * @param suit the suit that is revealed
	 */
	public void giveHint(Player target, Suit suit);

	/**
	 * This player intends to discard one of her cards.
	 *
	 * @param card the card to discard
	 */
	public void discard(UnknownCard card);

	/**
	 * This player intends to play one of her cards.
	 *
	 * @param card the card to play
	 */
	public void play(UnknownCard card);
}
