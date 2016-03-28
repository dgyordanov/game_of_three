package edu.game.three.domain;

import java.util.Random;

/**
 * Component which which provides a number for a player turn
 *
 * @author Diyan Yordanov
 */
public class AutoNumberManager implements NumberManager {

    private static final Random RANDOM_GENERATOR = new Random();

    @Override
    public int getNextNumber(int number) {
        return NumbersUtil.calculateNextNumber(number);
    }

    @Override
    public int getInitialNumber() {
        // Return a number between 1 - 1000
        return RANDOM_GENERATOR.nextInt(1000 - 10) + 11;
    }

}
