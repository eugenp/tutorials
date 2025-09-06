package com.baeldung.before.all.global;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

public class GlobalDatabaseListener implements TestExecutionListener {

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        // Global setup
        System.out.println("GlobalDatabaseListener # testPlanExecutionStarted ");
        // Example: DatabaseConnectionPool.initialize();
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        // Global teardown
        System.out.println("GlobalDatabaseListener # testPlanExecutionFinished");
        // Example: DatabaseConnectionPool.shutdown();
    }
}
