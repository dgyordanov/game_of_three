package edu.game.three.domain;

public class NumbersUtil {

    public static int calculateNextNumber(int number) {
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
}
