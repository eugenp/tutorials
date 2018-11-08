package com.baeldung.portsadapters.secondary;

import com.baeldung.portsadapters.core.Point;

import java.util.Deque;
import java.util.LinkedList;

public class HistoryAdapter implements History {
    private Deque<Point> inMemory = new LinkedList<>();

    @Override
    public void save(Point point) {
        inMemory.add(point);
    }

    @Override
    public Point undo() {
        inMemory.removeLast();
        return inMemory.peekLast();
    }
}