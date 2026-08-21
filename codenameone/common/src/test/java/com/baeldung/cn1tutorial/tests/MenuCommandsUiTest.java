package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.testsupport.UiTestSupport;

/**
 * Verifies that the top-level side menu exposes the expected commands.
 */
public class MenuCommandsUiTest extends AbstractDailyRoutineUiTest {
    /**
     * Enumerates the side-menu commands when the current JavaSE setup supports it.
     */
    @Override
    public boolean runTest() {
        if (Boolean.getBoolean("java.awt.headless") || "jmf".equalsIgnoreCase(System.getProperty("cn1.javase.implementation", ""))) {
            log("Skipping side-menu enumeration assertions in headless/JMF mode.");
            return true;
        }
        assertTrue(UiTestSupport.hasSideMenuCommand("Home"), "Home should be present in the side menu commands");
        assertTrue(UiTestSupport.hasSideMenuCommand("Settings"), "Settings should be present in the side menu commands");
        assertTrue(UiTestSupport.hasSideMenuCommand("CN1 + Native Logs"), "CN1 + Native Logs should be present in the side menu commands");
        assertTrue(UiTestSupport.hasSideMenuCommand("About"), "About should be present in the side menu commands");
        return true;
    }
}
