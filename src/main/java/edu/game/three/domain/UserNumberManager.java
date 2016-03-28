package edu.game.three.domain;

import java.util.Scanner;

public class UserNumberManager implements NumberManager {

    @Override
    public int getNextNumber(int number) {
        System.out.println(String.format("Last number is %d. Input the next number: ", number));
        Scanner scanner = new Scanner(System.in);
        int nextNumber = scanner.nextInt();
        if (nextNumber != NumbersUtil.calculateNextNumber(number)) {
            System.out.println("Error: number not correct. Try again:");
            return getNextNumber(number);
        }
        return nextNumber;
    }

    @Override
    public int getInitialNumber() {
        System.out.println("Input the initial game number: ");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        if (number < 1) {
            System.out.println("Error: number should be >= 1");
            return getInitialNumber();
        }
        return number;
    }

}
