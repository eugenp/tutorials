package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.service.ActivityRepository;
import com.baeldung.cn1tutorial.util.IOUtil;

/**
 * Verifies that malformed persisted JSON fails clearly instead of being silently ignored.
 */
public class CorruptStorageCrashUiTest extends AbstractDailyRoutineUiTest {
    /**
     * Writes invalid JSON to the activity file and checks the resulting failure path.
     */
    @Override
    public boolean runTest() throws Exception {
        ActivityRepository repository = new ActivityRepository();
        IOUtil.writeUtf8File(repository.filePath(), "{invalid-json");
        try {
            context.getActivityStore().load();
            fail("A corrupted activities.json file should fail fast");
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().indexOf("Unable to load activities") >= 0, "The storage failure should be clearly reported");
            assertNotNull(ex.getCause(), "The storage failure should preserve the root cause");
            assertTrue(ex.getCause().getMessage().indexOf("Malformed activities JSON") >= 0, "The root cause should identify malformed JSON");
        }
        return true;
    }
}
