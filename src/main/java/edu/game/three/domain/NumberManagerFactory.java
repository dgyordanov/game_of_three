package edu.game.three.domain;

/**
 * Factory which return instance of either automatic or manual number manager
 */
public class NumberManagerFactory {

    private static final NumberManager AUTO_NUMBER_MANAGER = new AutoNumberManager();

    private static final NumberManager MANUAL_NUMBER_MANAGER = new UserNumberManager();

    public static NumberManager getInstance(boolean manual) {
        return manual ? MANUAL_NUMBER_MANAGER : AUTO_NUMBER_MANAGER;
    }

}
