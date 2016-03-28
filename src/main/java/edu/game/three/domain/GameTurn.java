package edu.game.three.domain;

/**
 * A java bean used for in the communication layer for exchanging data between players
 *
 * @author Diyan Yordanov
 */
public class GameTurn {

    private int number;

    private String gameUUID;

    private String playerName;

    public GameTurn(String gameUUID, int number, String playerName) {
        this.gameUUID = gameUUID;
        this.number = number;
        this.playerName = playerName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGameUUID() {
        return gameUUID;
    }

    public void setGameUUID(String gameUUID) {
        this.gameUUID = gameUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return "GameTurn{" +
                "number=" + number +
                ", gameUUID=" + gameUUID +
                '}';
    }
}
