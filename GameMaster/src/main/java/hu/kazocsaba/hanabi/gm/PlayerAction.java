package hu.kazocsaba.hanabi.gm;

import hu.kazocsaba.hanabi.model.Player;
import hu.kazocsaba.hanabi.player.api.Action;
import java.util.Objects;

/**
 *
 * @author Kazó Csaba
 */
public class PlayerAction {
	private final Player player;
	private final Action action;

	public PlayerAction(Player player, Action action) {
		this.player = Objects.requireNonNull(player);
		this.action = Objects.requireNonNull(action);
	}

	public Player getPlayer() {
		return player;
	}

	public Action getAction() {
		return action;
	}
	
}
