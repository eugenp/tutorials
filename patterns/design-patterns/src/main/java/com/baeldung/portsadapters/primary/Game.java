package com.baeldung.portsadapters.primary;

import com.baeldung.portsadapters.core.Point;

public interface Game {
    Game runForward();

    Game runDiagonallyUp();

    Point currentPosition();

    Game startFromPreviousPosition();
}