package edu.game.three.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutoNumberManagerTest {

    private NumberManager numberManager = new AutoNumberManager();

    @Test
    public void testNextNumber() {
        int result = numberManager.getNextNumber(18);
        assertThat(result, equalTo(6));
    }

    @Test
    public void testNextNumberPlusCase() {
        int result = numberManager.getNextNumber(56);
        assertThat(result, equalTo(19));
    }

    @Test
    public void testNextNumberMinusCase() {
        int result = numberManager.getNextNumber(19);
        assertThat(result, equalTo(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnableToAnswerOnNegativeNumber() {
        int result = numberManager.getNextNumber(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnableToAnswerOnZero() {
        int result = numberManager.getNextNumber(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnableToAnswerOn1() {
        int result = numberManager.getNextNumber(1);
    }

    @Test
    public void testInitialNumber() {
        int result = numberManager.getInitialNumber();
        assertThat(result, greaterThan(10));
        assertThat(result, lessThanOrEqualTo(1000));
    }

}