package edu.game.three.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representing a game with the moves
 *
 * @author Diyan Yordanov
 */
class GameSession {

    private String uuid;

    /**
     * List of numbers done be turns in chronological order
     */
    private List<Integer> turns;

    /**
     * Stores who did the last turn. Used for fraud handling.
     */
    private String lastTurnActor;

    /**
     * Indicates if the turns should be done manual by the player
     */
    private boolean manual;

    public GameSession(String uuid, boolean manual) {
        this.uuid = uuid;
        this.manual = manual;
        turns = Collections.synchronizedList(new ArrayList<>());
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Integer> getTurns() {
        return turns;
    }

    public String getLastTurnActor() {
        return lastTurnActor;
    }

    public void setLastTurnActor(String lastTurnActor) {
        this.lastTurnActor = lastTurnActor;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }
}
