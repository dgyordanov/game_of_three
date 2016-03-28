package edu.game.three.domain;

/**
 * Manages game sessions with all their turns
 *
 * @author Diyan Yordanov
 */
public interface GameSessionManager {

    /**
     * Get a game session
     *
     * @param gameTurn - the turn for which the session should be created
     * @return - the session for the current game
     */
    GameSession getGameSession(GameTurn gameTurn);

    /**
     * Create new game session
     *
     * @return - a new session
     */
    GameSession createNewSession();

}
