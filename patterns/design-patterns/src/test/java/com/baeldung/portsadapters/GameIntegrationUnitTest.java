package com.baeldung.portsadapters;

import com.baeldung.portsadapters.core.Point;
import com.baeldung.portsadapters.primary.Game;
import com.baeldung.portsadapters.primary.GameAdapter;
import com.baeldung.portsadapters.secondary.HistoryAdapter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameIntegrationUnitTest {
    @Test
    public void whenGameScriptInvoked_thenPlayerAtExpectedPosition() {
        Game game = new GameAdapter(new HistoryAdapter());

        Point actual = game.runForward().runForward()
                           .startFromPreviousPosition()
                           .runDiagonallyUp()
                           .currentPosition();

        assertEquals(new Point(6, 2), actual);
    }
}