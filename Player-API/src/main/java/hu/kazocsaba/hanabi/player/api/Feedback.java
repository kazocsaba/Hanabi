package hu.kazocsaba.hanabi.player.api;

import java.util.Objects;

/**
 * Encapsulates feedback implementations for a particular player.
 *
 * @author Kazó Csaba
 */
public final class Feedback {

	private final GameFeedback gameFeedback;
	private final SelfActionFeedback selfActionFeedback;
	private final OtherPlayerActionFeedback otherPlayerActionFeedback;

	public Feedback(GameFeedback gameFeedback,
									 SelfActionFeedback selfActionFeedback,
									 OtherPlayerActionFeedback otherPlayerActionFeedback) {
		this.gameFeedback = Objects.requireNonNull(gameFeedback);
		this.selfActionFeedback = Objects.requireNonNull(selfActionFeedback);
		this.otherPlayerActionFeedback = Objects.requireNonNull(otherPlayerActionFeedback);
	}

	public GameFeedback getGameFeedback() {
		return gameFeedback;
	}

	public SelfActionFeedback getSelfActionFeedback() {
		return selfActionFeedback;
	}

	public OtherPlayerActionFeedback getOtherPlayerActionFeedback() {
		return otherPlayerActionFeedback;
	}
	
}
