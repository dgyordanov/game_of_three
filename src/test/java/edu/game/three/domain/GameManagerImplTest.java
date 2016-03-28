package edu.game.three.domain;

import edu.game.three.jms.GameClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class GameManagerImplTest {

    public static final String TEST_PLAYER_NAME_1 = "testPlayer";
    public static final String TEST_PLAYER_NAME_2 = "another player";
    private GameSessionManager gameSessionManager;
    private GameClient gameClient;
    private GameManager gameManager;
    private InputStream stdin;

    @Before
    public void setup() {
        gameSessionManager = new GameSessionManagerImpl();
        gameClient = mock(GameClient.class);
        gameManager = new GameManagerImpl(gameSessionManager, gameClient);

        ReflectionTestUtils.setField(gameManager, "playerName", TEST_PLAYER_NAME_1);

        stdin = System.in;
    }

    @After
    public void tearDown() {
        reset(gameClient);
        System.setIn(stdin);
    }

    @Test
    public void testStartGame() {
        // Test with auto numbering
        enterDataInSystemIn("n");

        gameManager.startGame();

        ArgumentCaptor<GameTurn> startGameTurnArg = ArgumentCaptor.forClass(GameTurn.class);
        verify(gameClient).sendNextNumber(startGameTurnArg.capture());

        GameTurn gameTurn = startGameTurnArg.getValue();
        GameSession gameSession = gameSessionManager.getGameSession(gameTurn.getGameUUID());

        assertThat(gameTurn.getGameUUID(), not(nullValue()));
        assertThat(gameTurn.getNumber(), greaterThanOrEqualTo(10));
        assertThat(gameTurn.getNumber(), lessThanOrEqualTo(1000));
        assertThat(gameTurn.getPlayerName(), equalTo(TEST_PLAYER_NAME_1));

        assertThat(gameSession.getUuid(), equalTo(gameTurn.getGameUUID()));
        assertThat(gameSession.isManual(), equalTo(false));
        assertThat(gameSession.getLastTurnActor(), equalTo(TEST_PLAYER_NAME_1));
    }

    @Test
    public void testNextNumber() {
        GameSession session = gameSessionManager.createNewSession(false);
        session.getTurns().add(90);
        session.setLastTurnActor(TEST_PLAYER_NAME_1);

        gameManager.processTurn(new GameTurn(session.getUuid(), 30, TEST_PLAYER_NAME_2));

        ArgumentCaptor<GameTurn> responseGameTurnArg = ArgumentCaptor.forClass(GameTurn.class);
        verify(gameClient).sendNextNumber(responseGameTurnArg.capture());

        GameTurn gameTurn = responseGameTurnArg.getValue();

        assertThat(gameTurn.getGameUUID(), equalTo(session.getUuid()));
        assertThat(gameTurn.getNumber(), equalTo(10));
        assertThat(gameTurn.getPlayerName(), equalTo(TEST_PLAYER_NAME_1));

        assertThat(session.getLastTurnActor(), equalTo(TEST_PLAYER_NAME_1));
        assertThat(session.isManual(), equalTo(false));
        assertThat(session.getTurns(), equalTo(Arrays.asList(90, 30, 10)));
    }

    @Test
    public void testNextNumberSameUserTwiceNotAllowed() {
        GameSession session = gameSessionManager.createNewSession(false);
        session.getTurns().add(90);
        session.setLastTurnActor(TEST_PLAYER_NAME_2);

        gameManager.processTurn(new GameTurn(session.getUuid(), 30, TEST_PLAYER_NAME_2));

        verify(gameClient, never()).sendNextNumber(Matchers.any(GameTurn.class));

        assertThat(session.getLastTurnActor(), equalTo(TEST_PLAYER_NAME_2));
        assertThat(session.getTurns(), equalTo(Arrays.asList(90)));
    }

    @Test
    public void testNextNumberInvalidNumberNotAllowed() {
        GameSession session = gameSessionManager.createNewSession(false);
        session.getTurns().add(90);
        session.setLastTurnActor(TEST_PLAYER_NAME_1);

        gameManager.processTurn(new GameTurn(session.getUuid(), 20, TEST_PLAYER_NAME_2));

        verify(gameClient, never()).sendNextNumber(Matchers.any(GameTurn.class));

        assertThat(session.getLastTurnActor(), equalTo(TEST_PLAYER_NAME_1));
        assertThat(session.getTurns(), equalTo(Arrays.asList(90)));
    }

    private void enterDataInSystemIn(String data) {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    public void testNewGameRequest() {
        String newGameUuid = UUID.randomUUID().toString();
        gameManager.processTurn(new GameTurn(newGameUuid, 20, TEST_PLAYER_NAME_2));

        GameSession session = gameSessionManager.getGameSession(newGameUuid);

        ArgumentCaptor<GameTurn> responseGameTurnArg = ArgumentCaptor.forClass(GameTurn.class);
        verify(gameClient).sendNextNumber(responseGameTurnArg.capture());

        assertThat(session.getLastTurnActor(), equalTo(TEST_PLAYER_NAME_1));
        assertThat(session.getTurns(), equalTo(Arrays.asList(20, 7)));

        assertThat(responseGameTurnArg.getValue().getNumber(), equalTo(7));
        assertThat(responseGameTurnArg.getValue().getPlayerName(), equalTo(TEST_PLAYER_NAME_1));
        assertThat(responseGameTurnArg.getValue().getGameUUID(), equalTo(newGameUuid));
    }

    @Test
    public void testGameLost() {
        GameSession session = gameSessionManager.createNewSession(false);
        session.getTurns().addAll(Arrays.asList(9, 3));
        session.setLastTurnActor(TEST_PLAYER_NAME_1);

        gameManager.processTurn(new GameTurn(session.getUuid(), 1, TEST_PLAYER_NAME_2));

        verify(gameClient, never()).sendNextNumber(Matchers.any(GameTurn.class));

        assertThat(session.getLastTurnActor(), equalTo(TEST_PLAYER_NAME_2));
        assertThat(session.getTurns(), equalTo(Arrays.asList(9, 3, 1)));
    }

    @Test
    public void testInvalidNumber() {
        GameSession session = gameSessionManager.createNewSession(false);
        session.getTurns().addAll(Arrays.asList(9));
        session.setLastTurnActor(TEST_PLAYER_NAME_1);

        gameManager.processTurn(new GameTurn(session.getUuid(), -1, TEST_PLAYER_NAME_2));

        verify(gameClient, never()).sendNextNumber(Matchers.any(GameTurn.class));

        assertThat(session.getLastTurnActor(), equalTo(TEST_PLAYER_NAME_1));
        assertThat(session.getTurns(), equalTo(Arrays.asList(9)));
    }

}