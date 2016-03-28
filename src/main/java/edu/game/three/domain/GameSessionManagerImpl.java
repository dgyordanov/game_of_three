package edu.game.three.domain;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread safe stores the sessions in memory
 */
public class GameSessionManagerImpl implements GameSessionManager {

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
