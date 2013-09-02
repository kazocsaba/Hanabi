package hu.kazocsaba.hanabi.gm.internal;

import com.google.common.base.Function;
import hu.kazocsaba.hanabi.gm.GameState;
import hu.kazocsaba.hanabi.gm.PlayerAction;
import hu.kazocsaba.hanabi.gm.PlayerCandidate;
import hu.kazocsaba.hanabi.model.Card;
import hu.kazocsaba.hanabi.model.Deck;
import hu.kazocsaba.hanabi.model.Player;
import hu.kazocsaba.hanabi.model.Suit;
import hu.kazocsaba.hanabi.model.UnknownCard;
import hu.kazocsaba.hanabi.player.api.Action;
import hu.kazocsaba.hanabi.player.api.Feedback;
import hu.kazocsaba.hanabi.player.api.SelfActionFeedback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A game master that manages the entirety of the game state.
 *
 * @author Kazó Csaba
 */
public class InternalGameMaster {
	private final LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
	
	private Thread thread = new Thread("Game master") {

		@Override
		public void run() {
			while (true) {
				try {
					taskQueue.take().run();
				} catch (InterruptedException e) {
					break;
				}
			}
		}
		
	};
	
	private InternalGameMaster() {}
	
	public static InternalGameMaster start() {
		InternalGameMaster gm = new InternalGameMaster();
		gm.thread.start();
		return gm;
	}
	
	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 5;
	private static final int MAX_HINTS = 10;
	
	private GameState state = GameState.PREPARING;
	
	private final List<Player> players = new ArrayList<>();
	private final Map<Player, Feedback> playerFeedbacks = new HashMap<>();
	
	private int currentPlayer = 0;
	private Deck deck;
	private Map<Player, List<Card>> hands = new HashMap<>();
	private int hintsAvailable;

	public void requestJoin(PlayerCandidate candidate) {
		try {
			taskQueue.put(new PlayerJoinRequest(candidate));
		} catch (InterruptedException e) {
			
		}
	}
	class PlayerListener implements PlayerCandidate.PlayerActionListener {
		private final Player player;

		public PlayerListener(Player player) {
			this.player = Objects.requireNonNull(player);
		}
		
		@Override
		public void onPlayerAction(Action action) {
			taskQueue.add(new PlayerActionTask(new PlayerAction(player, action)));
		}
		
	}
	private class PlayerJoinRequest implements Runnable {
		private final PlayerCandidate candidate;

		public PlayerJoinRequest(PlayerCandidate candidate) {
			this.candidate = candidate;
		}
		
		@Override
		public void run() {
			if (state != GameState.PREPARING) {
				candidate.rejectGameStarted();
				return;
			}
			if (players.size() == MAX_PLAYERS) {
				candidate.rejectGameFull();
				return;
			}
			Player player = new Player() {
			};
			players.add(player);
			playerFeedbacks.put(player, candidate.accept(new PlayerListener(player)));
		}
	}
	private class StartGameRequest implements Runnable {

		@Override
		public void run() {
			startGame();
		}
		
	}
	private class PlayerActionTask implements Runnable {
		private final PlayerAction playerAction;

		public PlayerActionTask(PlayerAction playerAction) {
			this.playerAction = Objects.requireNonNull(playerAction);
		}

		@Override
		public void run() {
			if (playerAction.getAction() instanceof Action.GiveHint) {
				giveHint(playerAction.getPlayer(), (Action.GiveHint)playerAction.getAction());
			} else {
				throw new AssertionError("Unknown action: "+playerAction.getAction().getClass().getName());
			}
		}

	}
	/*
	 * Functions that are run from the single worker thread.
	 */
	
	private void startGame() {
		if (state != GameState.PREPARING) {
			return;
		}
		if (players.size() < MIN_PLAYERS) {
			return;
		}
		deck = Deck.createRandom();
		hands.clear();
		for (Player player: players) {
			hands.put(player, new ArrayList<Card>(4));
		}
		for (Player player: players) {
			for (int cardIndex = 0; cardIndex < 4; cardIndex++) {
				Card card = deck.draw();
				hands.get(player).add(card);
				
				for (Player playerToNotify: players) {
					if (player == playerToNotify)
						playerFeedbacks.get(playerToNotify).getSelfActionFeedback().draws(card.asUnknown());
					else
						playerFeedbacks.get(playerToNotify).getOtherPlayerActionFeedback().draws(card);
				}
			}
		}
		currentPlayer = 0;
		hintsAvailable = MAX_HINTS;
		state = GameState.INGAME;
		for (Player playerToNotify: players)
			playerFeedbacks.get(playerToNotify).getGameFeedback().gameStarts(new ArrayList<>(players));
		notifyTurnStarts();
	}
	private void notifyTurnStarts() {
		for (Feedback feedback: playerFeedbacks.values())
			feedback.getGameFeedback().turnStarts(players.get(currentPlayer));
	}
	
	private void giveHint(Player player, Action.GiveHint giveHint) {
		if (state != GameState.INGAME) {
			return;
		}
		if (player != players.get(currentPlayer)) {
			return;
		}
		if (giveHint.getTarget().equals(player)) {
			return;
		}
		if (hintsAvailable == 0) {
			return;
		}
		List<UnknownCard> hintedCards=new ArrayList<>(4);
		for (Card card: hands.get(giveHint.getTarget()))
			if (giveHint.matches(card))
				hintedCards.add(card.asUnknown());
		
		for (Player playerToNotify: players)
			if (playerToNotify == player) {
				giveHint.provideFeedback(playerFeedbacks.get(playerToNotify).getSelfActionFeedback());
			} else if (playerToNotify == giveHint.getTarget()) {
				giveHint.provideFeedback(playerFeedbacks.get(playerToNotify).getOtherPlayerActionFeedback(), hintedCards);
			} else {
				giveHint.provideFeedback(playerFeedbacks.get(playerToNotify).getOtherPlayerActionFeedback());
			}
		hintsAvailable--;
		currentPlayer = (currentPlayer + 1) % players.size();
		notifyTurnStarts();
	}
}
