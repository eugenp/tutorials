package com.baeldung.before.all.global;

import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.LauncherSessionListener;

public class GlobalDatabaseSessionListener implements LauncherSessionListener {

    @Override
    public void launcherSessionOpened(LauncherSession session) {
        // Global setup before session starts
        System.out.println("launcherSessionOpened");
    }

    @Override
    public void launcherSessionClosed(LauncherSession session) {
        // Global teardown after session ends
        System.out.println("launcherSessionClosed");
    }
}