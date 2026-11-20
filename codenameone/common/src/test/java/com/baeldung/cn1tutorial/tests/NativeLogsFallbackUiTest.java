package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.testsupport.UiTestSupport;
import com.codename1.io.Log;

/**
 * Verifies the simulator fallback path of the merged logs form.
 */
public class NativeLogsFallbackUiTest extends AbstractDailyRoutineUiTest {
    /**
     * Opens the logs form and verifies both section headers plus the simulator fallback message.
     */
    @Override
    public boolean runTest() {
        Log.deleteLog();
        com.codename1.ui.CN.callSeriallyAndWait(() -> context.showNativeLogs(null));
        assertNotNull(UiTestSupport.waitForComponent("nativeLogsArea", 5000), "The merged logs form should expose the logs text area");
        String text = UiTestSupport.componentText("nativeLogsArea");
        assertTrue(
                text.indexOf("==== CN1 LOGS ====") >= 0 || text.indexOf("==== cn1.logs.section ====") >= 0,
                "The merged logs should include the CN1 section header"
        );
        assertTrue(
                text.indexOf("==== NATIVE LOGS ====") >= 0 || text.indexOf("==== native.logs.section ====") >= 0,
                "The merged logs should include the native section header"
        );
        assertTrue(
                text.indexOf("Native logs are only available on Android and iOS. In the simulator and other ports, this screen stays as a graceful fallback.") >= 0,
                "The merged logs should include the native fallback message"
        );
        return true;
    }
}
