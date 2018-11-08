package com.baeldung.portsadapters.core;

import com.baeldung.portsadapters.secondary.History;

public class Player {
    private Point position = new Point(0, 0);
    private History history;

    public Player(History history) {
        this.history = history;
    }

    public void move(Direction direction) {
        position.xAxis(direction.xShift());
        position.yAxis(direction.yShift());
        history.save(position.copy());
    }

    public Point currentPosition() {
        return position;
    }

    public void startFromPreviousPosition() {
        position = history.undo();
    }
}