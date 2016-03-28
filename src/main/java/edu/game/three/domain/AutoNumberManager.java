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
        if (number <= 1) {
            throw new IllegalArgumentException(String.format("%d is negative. Only positive numbers are accepted.",
                    number));
        }
        int reminder = number % 3;
        switch (reminder) {
            case 0:
                return number / 3;
            case 1:
                return (number + 1) / 3;
            case 2:
                return (number + 1) / 3;
            default:
                return number / 3;
        }
    }

    @Override
    public int getInitialNumber() {
        // Return a number between 1 - 1000
        return RANDOM_GENERATOR.nextInt(1000 - 10) + 11;
    }

}
