package com.baeldung.hexagonalArchitecture.adapters;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagonalArchitecture.domain.Player;
import com.baeldung.hexagonalArchitecture.ports.PlayerRepository;

public class PlayerRepositoryInMemoryImpl implements PlayerRepository {
    private List<Player> players = new ArrayList<>();

    @Override
    public boolean savePlayer(Player player) {
        return players.add(player);
    }

    @Override
    public Player getPlayer(String name) {
        return players.stream()
            .filter(p -> p.getName()
                .equals(name))
            .findAny()
            .orElse(null);
    }

}
