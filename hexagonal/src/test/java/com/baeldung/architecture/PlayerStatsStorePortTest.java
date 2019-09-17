package com.baeldung.architecture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.time.Year;

public class PlayerStatsStorePortTest {
    private PlayerStatsStorePort testClass;

    @Before
    public void setup() {
        testClass = new PlayerStatsStoreFileSystemAdapter(new FileSystemManager());
    }

    @Test
    public void testStatsStorage() {
        MonthlyStats stats = new MonthlyStats(2,5,10,5,7.4);
        Player player = new Player(PlayerType.FORWARD, "Ronaldo", "Juventus");
        testClass.store(stats, player, Month.FEBRUARY, Year.now());
        Assert.assertTrue(testClass.remove(player, Month.FEBRUARY, Year.now()));
    }
}
