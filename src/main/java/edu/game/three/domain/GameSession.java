package edu.game.three.domain;

import java.util.Collection;

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
    private Collection<Integer> turns;

    /**
     * Stores who did the last turn. Used for fraud handling.
     */
    private String lastTurnActor;

    /**
     * Indicates if the turns should be done manual by the player
     */
    private boolean manual;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Collection<Integer> getTurns() {
        return turns;
    }

    public void setTurns(Collection<Integer> turns) {
        this.turns = turns;
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
