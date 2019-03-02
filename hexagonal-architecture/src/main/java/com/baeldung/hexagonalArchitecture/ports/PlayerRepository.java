package com.baeldung.hexagonalArchitecture.ports;

import com.baeldung.hexagonalArchitecture.domain.Player;

public interface PlayerRepository {
    public boolean savePlayer(Player player);

    public Player getPlayer(String name);

    // other methods such as delete, get highest scorer, etc
}
