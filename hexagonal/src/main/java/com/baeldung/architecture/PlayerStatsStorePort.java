package com.baeldung.architecture;

import java.time.Month;
import java.time.Year;

public interface PlayerStatsStorePort {
    boolean store(MonthlyStats stats, Player player, Month month, Year year);
    boolean remove(Player player, Month month, Year year);
}
