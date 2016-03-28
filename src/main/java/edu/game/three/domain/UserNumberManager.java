package edu.game.three.domain;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Number manager which handles the manual game input
 *
 * @author Diyan Yordanov
 */
public class UserNumberManager implements NumberManager {

    @Override
    public int getNextNumber(int number) {
        System.out.println(String.format("Last number is %d. Input the next number: ", number));
        try {
            Scanner scanner = new Scanner(System.in);
            int nextNumber = scanner.nextInt();
            if (nextNumber != NumbersUtil.calculateNextNumber(number)) {
                System.err.println("Error: number not correct. Try again:");
                return getNextNumber(number);
            }
            return nextNumber;

        } catch (InputMismatchException e) {
            System.err.println("Error: incorrect input. Please try again.");
            return getNextNumber(number);

        }

    }

    @Override
    public int getInitialNumber() {
        System.out.println("Input the initial game number: ");
        try {
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            if (number < 1) {
                System.out.println("Error: number should be >= 1");
                return getInitialNumber();
            }
            return number;
        } catch (InputMismatchException e) {
            System.err.println("Error: incorrect input. Please try again.");
            return getInitialNumber();
        }
    }

}
