package edu.game.three.domain;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread safe stores the sessions in memory
 */
@Repository
public class GameSessionManagerImpl implements GameSessionManager {

    // This is a potential memory leak and in long term it could be replaced with
    // a date structure that cleans the records after a timeout. May be Guava could help.
    private Map<String, GameSession> sessionStore = new ConcurrentHashMap<>();

    @Override
    public GameSession getGameSession(String uuid) {
        sessionStore.putIfAbsent(uuid, new GameSession(uuid, false));
        return sessionStore.get(uuid);
    }

    @Override
    public GameSession createNewSession(boolean manualGame) {
        GameSession newGameSession = new GameSession(UUID.randomUUID().toString(), manualGame);
        sessionStore.put(newGameSession.getUuid(), newGameSession);
        return newGameSession;
    }
}
