package edu.game.three.domain;

/**
 * Manages game sessions with all their turns
 *
 * @author Diyan Yordanov
 */
public interface GameSessionManager {

    /**
     * Get a game session. If session doesn't exist yet, it will be created.
     *
     * @param uuid - the uuid of the needed session.
     * @return - the session for the current game
     */
    GameSession getGameSession(String uuid);

    /**
     * Create new game session
     *
     * @param manualGame - is the game manual from the actual player side
     * @return - a new session
     */
    GameSession createNewSession(boolean manualGame);

}
