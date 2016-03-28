package edu.game.three.jms;

/**
 * Component which is responsible to send data to the other player
 *
 * @author Diyan Yordanov
 */
public interface GameClient {

    /**
     * Send the next number of the game to the other player
     *
     * @param gameTurnDTO - the next number together with the game UUID
     */
    void sendNextNumber(GameTurnDTO gameTurnDTO);
}
