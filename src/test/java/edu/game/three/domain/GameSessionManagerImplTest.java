package edu.game.three.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameSessionManagerImplTest {

    private GameSessionManager gameSessionManager = new GameSessionManagerImpl();

    @Test
    public void testCreateAutoGameSession() {
        GameSession emptySession = gameSessionManager.createNewSession(false);
        assertThat(emptySession.getTurns(), is(emptyIterable()));
        assertThat(emptySession.getLastTurnActor(), is(nullValue()));
        assertThat(emptySession.getUuid(), not(isEmptyOrNullString()));
        assertThat(emptySession.isManual(), is(false));
    }

    @Test
    public void testCreateManualGameSession() {
        GameSession emptySession = gameSessionManager.createNewSession(true);
        assertThat(emptySession.getTurns(), is(emptyIterable()));
        assertThat(emptySession.getLastTurnActor(), is(nullValue()));
        assertThat(emptySession.getUuid(), not(isEmptyOrNullString()));
        assertThat(emptySession.isManual(), is(true));
    }

    @Test
    public void testGetGameSession() {
        GameSession emptySession = gameSessionManager.createNewSession(true);

        emptySession.setLastTurnActor("player1");
        List<Integer> turns = emptySession.getTurns();
        turns.addAll(Arrays.asList(28, 9, 3));

        GameSession session = gameSessionManager.getGameSession(emptySession.getUuid());
        assertThat(session.getTurns(), is(Arrays.asList(28, 9, 3)));
        assertThat(session.getLastTurnActor(), is("player1"));
        assertThat(session.getUuid(), is(emptySession.getUuid()));
        assertThat(session.isManual(), is(true));
    }

    @Test
    public void testGetNotExistingGameSession() {
        // This handles the case when the other player started a game
        GameSession session = gameSessionManager.getGameSession(UUID.randomUUID().toString());
        assertThat(session.getTurns(), is(emptyIterable()));
        assertThat(session.getLastTurnActor(), is(nullValue()));
        assertThat(session.getUuid(), not(isEmptyOrNullString()));
        assertThat(session.isManual(), is(false));
    }

}