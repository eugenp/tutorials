package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.testsupport.UiTestSupport;

/**
 * Smoke test for the About screen.
 */
public class AboutFormUiTest extends AbstractDailyRoutineUiTest {
    /**
     * Opens the About form and verifies its key widgets.
     */
    @Override
    public boolean runTest() {
        com.codename1.ui.CN.callSeriallyAndWait(() -> context.showAbout(null));
        waitForFormTitle("About");
        assertTrue(UiTestSupport.componentText("aboutBodyLabel").indexOf("Baeldung") >= 0, "The About screen should explain the tutorial context");
        assertNotNull(findByName("aboutBaeldungLink"), "The About screen should expose the Baeldung link");
        return true;
    }
}
