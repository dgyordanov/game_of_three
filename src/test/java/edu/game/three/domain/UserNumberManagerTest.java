package edu.game.three.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserNumberManagerTest {

    private NumberManager numberManager = new UserNumberManager();

    private InputStream stdin;

    @Before
    public void setup() {
        stdin = System.in;
    }

    @After
    public void tearDown() {
        System.setIn(stdin);
    }

    @Test
    public void testNextNumber() {
        enterDataInSystemIn("6");
        int result = numberManager.getNextNumber(18);
        assertThat(result, equalTo(6));
    }

    @Test
    public void testInitialNumber() {
        enterDataInSystemIn("100");
        int result = numberManager.getInitialNumber();
        assertThat(result, equalTo(100));
    }

    private void enterDataInSystemIn(String data) {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

}