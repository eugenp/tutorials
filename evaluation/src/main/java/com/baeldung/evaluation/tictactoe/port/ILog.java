package com.baeldung.evaluation.tictactoe.port;

import java.util.List;

public interface ILog {
    void saveGame(String playerXName, String playerOName, String game);
}
