package hu.kazocsaba.hanabi.player.api;

import hu.kazocsaba.hanabi.model.Player;
import java.util.List;

/**
 * Represents game state changes as perceived by a particular player.
 *
 * @author Kazó Csaba
 */
public interface GameFeedback {

	/**
	 * Returns the player whose perception is represented by this object.
	 *
	 * @return this player
	 */
	public Player getSelf();

	/**
	 * A new game starts.
	 *
	 * @param players a list of the participants of the new game, in the order of their turns
	 */
	public void gameStarts(List<Player> players);

	/**
	 * A player's turn starts.
	 *
	 * @param player the player whose turn starts
	 */
	public void turnStarts(Player player);
}
