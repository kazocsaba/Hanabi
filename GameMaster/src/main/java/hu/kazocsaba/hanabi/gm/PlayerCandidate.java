package hu.kazocsaba.hanabi.gm;

import hu.kazocsaba.hanabi.player.api.Action;
import hu.kazocsaba.hanabi.player.api.Feedback;

/**
 *
 * @author Kazó Csaba
 */
public interface PlayerCandidate {
	public static interface PlayerActionListener {
		public void onPlayerAction(Action action);
	}
	public Feedback accept(PlayerActionListener actionListener);
	public void rejectGameStarted();
	public void rejectGameFull();
}
