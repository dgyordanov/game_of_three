package edu.game.three.domain;

/**
 * A game mediator which stays in the middle of the business logic. It is a clue between the
 * GameSessionManager, JMS and NumberManager.
 *
 * @author Diyan Yordanov
 */
public interface GameManager {

    /**
     * Process a game turn.
     */
    void processTurn(GameTurn gameTurn);

    /**
     * Start a new game
     */
    void startGame();
}
