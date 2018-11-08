package com.baeldung.portsadapters.secondary;

import com.baeldung.portsadapters.core.Point;

public interface History {
    void save(Point point);

    Point undo();
}