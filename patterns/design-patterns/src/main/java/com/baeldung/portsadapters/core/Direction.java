package com.baeldung.portsadapters.core;

public enum Direction {
    LEFT(-1, 0), RIGHT(1, 0), UP(0, 1), DOWN(0, -1);

    private int xShift, yShift;

    Direction(int xShift, int yShift) {
        this.xShift = xShift;
        this.yShift = yShift;
    }

    public int xShift() {
        return xShift;
    }

    public int yShift() {
        return yShift;
    }
}