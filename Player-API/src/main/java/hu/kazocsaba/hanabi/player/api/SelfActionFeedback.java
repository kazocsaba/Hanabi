package hu.kazocsaba.hanabi.player.api;

import hu.kazocsaba.hanabi.model.Card;
import hu.kazocsaba.hanabi.model.Player;
import hu.kazocsaba.hanabi.model.Rank;
import hu.kazocsaba.hanabi.model.Suit;
import hu.kazocsaba.hanabi.model.UnknownCard;

/**
 * Represents actions performed by a player and perceived by the same player. A player must not
 * assume that any of her actions are actually performed until she receives a callback to this
 * interface.
 *
 * @author Kazó Csaba
 */
public interface SelfActionFeedback {

	/**
	 * Returns the player whose perception is represented by this object.
	 *
	 * @return this player
	 */
	public Player getSelf();

	/**
	 * This player draws a card.
	 *
	 * @param card the card that this player has drawn
	 */
	public void draws(UnknownCard card);
	
	/**
	 * This player discards a card.
	 * 
	 * @param card the card that this player has discarded
	 * @param actualCard the actual card it turned out to be
	 */
	public void discards(UnknownCard card, Card actualCard);
	
	/**
	 * This player plays a card.
	 * 
	 * @param card the card this player has played
	 * @param actualCard the actual card it turned out to be
	 * @param success whether the card has been successfully played; if {@code false}, then this
	 * card remains in this player's hand in its original position
	 */
	public void plays(UnknownCard card, Card actualCard, boolean success);
	
	/**
	 * This player gives a hint to another player.
	 * 
	 * The target player learns which of her cards have the given rank.
	 * 
	 * @param target the player receiving the hint
	 * @param rank the rank that is revealed
	 */
	public void givesHint(Player target, Rank rank);
	
	/**
	 * This player gives a hint to another player.
	 * 
	 * The target player learns which of her cards have the given suit.
	 * 
	 * @param target the player receiving the hint
	 * @param suit the suit that is revealed
	 */
	public void givesHint(Player target, Suit suit);
}
