package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.model.AppearanceMode;
import com.baeldung.cn1tutorial.model.AppSettings;
import com.codename1.io.FileSystemStorage;
import com.codename1.testing.AbstractTest;

/**
 * Verifies that app settings survive a JSON save/load round trip unchanged.
 */
public class SettingsRepositoryRoundTripTest extends AbstractTest {
    /**
     * Executes the round-trip settings test.
     */
    @Override
    public boolean runTest() throws Exception {
        String path = FileSystemStorage.getInstance().getAppHomePath()
                + "tests/settings-round-trip-" + System.currentTimeMillis() + ".json";
        TestSettingsRepository repository = new TestSettingsRepository(path);
        AppSettings original = new AppSettings("it", 120, AppearanceMode.DARK).normalized();

        try {
            repository.save(original);
            AppSettings loaded = repository.load();

            assertEqual(original, loaded, "The app settings should survive a JSON round trip");
            return true;
        } finally {
            repository.reset();
        }
    }

    /**
     * Repository variant that redirects persistence to a test-specific file.
     */
    private static final class TestSettingsRepository extends SettingsRepository {
        private final String path;

        /**
         * @param path test file path
         */
        private TestSettingsRepository(String path) {
            this.path = path;
        }

        /**
         * Returns the test file path instead of the real app path.
         */
        @Override
        public String filePath() {
            return path;
        }
    }
}
