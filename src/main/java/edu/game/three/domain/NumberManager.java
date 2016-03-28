package edu.game.three.domain;

/**
 * Handle the number producing by the current player
 */
public interface NumberManager {

    /**
     * Produces the next number of the game
     *
     * @param number - the number from the last opponent turn
     * @return - number for the current player turn
     */
    int getNextNumber(int number);

    /**
     * Generate an initial number to start a game
     *
     * @return - game initial number
     */
    int getInitialNumber();
}
