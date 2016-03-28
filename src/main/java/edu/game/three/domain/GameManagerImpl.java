package edu.game.three.domain;

import edu.game.three.jms.GameClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class GameManagerImpl implements GameManager {

    private GameSessionManager gameSessionManager;

    private GameClient gameClient;

    @Value("${player.name}")
    private String playerName;

    @Value("${player.active}")
    private boolean playerActive;

    @Autowired
    public GameManagerImpl(GameSessionManager gameSessionManager, GameClient gameClient) {
        this.gameSessionManager = gameSessionManager;
        this.gameClient = gameClient;
    }

    @Override
    public void processTurn(GameTurn opponentTurn) {
        GameSession session = gameSessionManager.getGameSession(opponentTurn.getGameUUID());

        if (!validateTurn(session, opponentTurn)) {
            System.out.println("Invalid turn. It will be skipper.");
            return;
        }

        session.getTurns().add(opponentTurn.getNumber());
        session.setLastTurnActor(opponentTurn.getPlayerName());

        if (opponentTurn.getNumber() == 1) {
            System.out.println("You lost");
            startGame();
            return;
        }

        NumberManager numberManager = NumberManagerFactory.getInstance(session.isManual());
        int responseNumber = numberManager.getNextNumber(opponentTurn.getNumber());
        session.getTurns().add(responseNumber);
        session.setLastTurnActor(playerName);

        gameClient.sendNextNumber(new GameTurn(session.getUuid(), responseNumber, playerName));

        if (responseNumber == 1) {
            System.out.println("You won");
            startGame();
        }

    }


    @Override
    public void startGame() {
        if (!playerActive) {
            return;
        }
        boolean manual = getManual();

        NumberManager numberManager = NumberManagerFactory.getInstance(manual);

        int startNumber = numberManager.getInitialNumber();

        GameSession gameSession = gameSessionManager.createNewSession(manual);
        gameSession.getTurns().add(startNumber);
        gameSession.setLastTurnActor(playerName);

        System.out.println(String.format("################ new game stared with session ID %s ################",
                gameSession.getUuid()));

        gameClient.sendNextNumber(new GameTurn(gameSession.getUuid(), startNumber, playerName));
    }

    private boolean validateTurn(GameSession session, GameTurn gameTurn) {
        // In case of new game request, player name is null and session turns are empty
        if (gameTurn.getPlayerName() != null && gameTurn.getPlayerName().equals(session.getLastTurnActor())) {
            System.out.println(String.format("ERROR: duplicate turn by player: %s", gameTurn.getPlayerName()));
            return false;
        }

        if (gameTurn.getNumber() < 1) {
            System.out.println(String.format("The received %d number in invalid", gameTurn.getNumber()));
        }
        List<Integer> turns = session.getTurns();
        if (!turns.isEmpty()) {
            // Not a new game. Validate the requested turn.
            Integer lastNumber = turns.get(session.getTurns().size() - 1);
            int expectedNumber = NumbersUtil.calculateNextNumber(lastNumber);
            if (expectedNumber != gameTurn.getNumber()) {
                System.out.println(String.format("The received %d number in invalid. Expected number is %d",
                        gameTurn.getNumber(), expectedNumber));
                return false;
            }

        }

        return true;
    }

    private boolean getManual() {
        System.out.println("A new game could be started. Do you want a manual game? [y/n] - default no.");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().equalsIgnoreCase("y");
    }
}
