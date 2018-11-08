package com.baeldung.portsadapters.primary;

import com.baeldung.portsadapters.core.Direction;
import com.baeldung.portsadapters.core.Player;
import com.baeldung.portsadapters.core.Point;
import com.baeldung.portsadapters.secondary.History;

import java.util.stream.IntStream;

public class GameAdapter implements Game {
    private Player player;

    public GameAdapter(History history) {
        this.player = new Player(history);
    }

    @Override
    public Game runForward() {
        IntStream.range(0, 4).forEach(i -> player.move(Direction.RIGHT));
        return this;
    }

    @Override
    public Game runDiagonallyUp() {
        IntStream.range(0, 2).forEach(i -> {
            player.move(Direction.RIGHT);
            player.move(Direction.UP);
        });
        return this;
    }

    @Override
    public Game startFromPreviousPosition() {
        IntStream.range(0, 4).forEach(i -> player.startFromPreviousPosition());
        return this;
    }

    @Override
    public Point currentPosition() {
        return player.currentPosition();
    }
}