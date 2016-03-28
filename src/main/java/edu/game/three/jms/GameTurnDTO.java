package edu.game.three.jms;

/**
 * A DTO used for in the communication layer for exchanging data between players
 *
 * @author Diyan Yordanov
 */
public class GameTurnDTO {

    public GameTurnDTO(String gameUUID, int number) {
        this.gameUUID = gameUUID;
        this.number = number;
    }

    private int number;

    private String gameUUID;

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

    @Override
    public String toString() {
        return "GameTurnDTO{" +
                "number=" + number +
                ", gameUUID=" + gameUUID +
                '}';
    }
}
