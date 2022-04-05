package com.baeldung.timer;

import java.util.List;
import java.util.TimerTask;

public class DatabaseMigrationTask extends TimerTask {
    private List<String> oldDatabase;
    private List<String> newDatabase;

    public DatabaseMigrationTask(List<String> oldDatabase, List<String> newDatabase) {
        this.oldDatabase = oldDatabase;
        this.newDatabase = newDatabase;
    }

    @Override
    public void run() {
        newDatabase.addAll(oldDatabase);
    }
}
