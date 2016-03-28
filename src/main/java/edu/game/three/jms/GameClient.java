package edu.game.three.jms;

import edu.game.three.domain.GameTurn;

/**
 * Component which is responsible to send data to the other player
 *
 * @author Diyan Yordanov
 */
public interface GameClient {

    /**
     * Send the next number of the game to the other player
     *
     * @param gameTurn - the next number together with the game UUID
     */
    void sendNextNumber(GameTurn gameTurn);
}
