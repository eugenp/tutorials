package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.DailyRoutine;
import com.codename1.io.Log;
import com.codename1.testing.AbstractTest;

/**
 * Verifies that the diagnostic environment report can be generated at runtime.
 */
public class EnvironmentLoggingTest extends AbstractTest {
    /**
     * Executes the environment logging test.
     */
    @Override
    public boolean runTest() {
        DailyRoutine app = DailyRoutine.getActiveInstance();
        assertNotNull(app, "App instance should exist before environment logging test runs");

        String report = app.buildEnvironmentReport();
        Log.p(report);

        assertTrue(report.startsWith("Daily Routine environment"), "Environment report header should be present");
        assertTrue(report.contains("codenameone.revision="), "Report should include Codename One revision");
        assertTrue(report.contains("java.version="), "Report should include java.version");
        assertTrue(report.contains("os.name="), "Report should include os.name");
        assertTrue(report.contains("os.arch="), "Report should include os.arch");
        assertTrue(report.contains("simulator="), "Report should include simulator flag");
        assertTrue(report.contains("browser.native.supported="), "Report should include BrowserComponent capability");
        return true;
    }
}
