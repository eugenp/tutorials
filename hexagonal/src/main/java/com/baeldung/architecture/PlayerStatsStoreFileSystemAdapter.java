package com.baeldung.architecture;

import java.time.Month;
import java.time.Year;
import com.google.inject.Inject;

public class PlayerStatsStoreFileSystemAdapter implements PlayerStatsStorePort {
    private final String directoryPath = "/Users/vaibhavtripathi/baeldung";
    private final FileSystemManager fileSystemManager;

    @Inject
    public PlayerStatsStoreFileSystemAdapter(FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
    }

    @Override
    public boolean store(MonthlyStats stats, Player player, Month month, Year year) {
        String filename = getFileName(player, month, year);
        return fileSystemManager.put(stats, filename, directoryPath);
    }

    @Override
    public boolean remove(Player player, Month month, Year year) {
        String filename = getFileName(player, month, year);
        return fileSystemManager.delete(filename, directoryPath);
    }

    public String getFileName(Player player, Month month, Year year) {
        return player.getName()+"~"+month.name()+"~"+year.getValue();
    }
}
