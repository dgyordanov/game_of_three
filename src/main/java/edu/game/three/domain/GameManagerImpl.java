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

    private NumberManager numberManager;

    private GameClient gameClient;

    @Value("${player.name}")
    private String playerName;

    @Autowired
    public GameManagerImpl(GameSessionManager gameSessionManager, NumberManager numberManager, GameClient gameClient) {
        this.gameSessionManager = gameSessionManager;
        this.numberManager = numberManager;
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
            return;
        }

        int responseNumber = numberManager.getNextNumber(opponentTurn.getNumber());
        session.getTurns().add(responseNumber);
        session.setLastTurnActor(playerName);

        if (responseNumber == 1) {
            System.out.println("You won");
        }

        gameClient.sendNextNumber(new GameTurn(session.getUuid(), responseNumber, playerName));
    }


    @Override
    public void startGame() {
        int startNumber = numberManager.getInitialNumber();

        GameSession gameSession = gameSessionManager.createNewSession(getManual());
        gameSession.getTurns().add(startNumber);
        gameSession.setLastTurnActor(playerName);

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
        System.out.println("Do you want a manual game? [y/n] - default no.");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().equalsIgnoreCase("y");
    }
}
