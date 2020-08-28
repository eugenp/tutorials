package com.baeldung.hexagonalarchitecture2.adapters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.baeldung.hexagonalarchitecture2.core.domain.GameInfo;
import com.baeldung.hexagonalarchitecture2.core.domain.Move;
import com.baeldung.hexagonalarchitecture2.core.ports.GameRepository;

public class InMemoryGameRepositoryAdapter implements GameRepository {

    private final Map<Integer, GameInfo> games = new HashMap<>();
    private final Random idGenerator = new Random();

    public int saveNewGame(Move move, String key) {
        int newId = idGenerator.nextInt();
        games.put(newId, new GameInfo(newId, move, key));
        return newId;
    }

    public GameInfo getAndRemoveGame(int id) throws GameNotFoundException {
        GameInfo gameInfo = games.remove(id);
        if (gameInfo == null) {
            throw new GameNotFoundException("Unable to retrieve match with id: " + id);
        }
        return gameInfo;
    }
}
