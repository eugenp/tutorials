package com.baeldung.hexagonalArchitecture.adapters;

import com.baeldung.hexagonalArchitecture.domain.Player;
import com.baeldung.hexagonalArchitecture.ports.PlayerRepository;

public class PlayerRepositoryDBImpl implements PlayerRepository {

    @Override
    public boolean savePlayer(Player player) {
        return false;
    }

    @Override
    public Player getPlayer(String name) {
        return null;
    }

}
