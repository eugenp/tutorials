package com.baeldung.modelmapper.repository;

import com.baeldung.modelmapper.domain.Game;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample in-memory Game Repository
 */
public class GameRepository {

    private final List<Game> gameStore = new ArrayList<>();

    public GameRepository() {
        // initialize some test data
        this.gameStore.add(new Game(1L, "Game 1"));
        this.gameStore.add(new Game(2L, "Game 2"));
        this.gameStore.add(new Game(3L, "Game 3"));
    }

    public Game findById(Long id) {
        return this.gameStore.stream()
          .filter(g -> g.getId().equals(id))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("No Game found"));
    }

}
