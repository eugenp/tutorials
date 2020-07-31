package com.baeldung.hexagonalarchitecture2.core.ports;

import com.baeldung.hexagonalarchitecture2.core.domain.GameInfo;
import com.baeldung.hexagonalarchitecture2.core.domain.Move;

public interface GameRepository {

    int saveNewGame(Move move, String key);

    GameInfo getAndRemoveGame(int id) throws GameNotFoundException;

    class GameNotFoundException extends Exception {
        public GameNotFoundException(String msg) {
            super(msg);
        }
    }
}
