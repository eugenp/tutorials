package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.model.ActivityCategory;
import com.baeldung.cn1tutorial.model.PlaceInfo;
import com.codename1.io.FileSystemStorage;
import com.codename1.testing.AbstractTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Verifies that activities survive a JSON save/load round trip unchanged.
 */
public class ActivityRepositoryRoundTripTest extends AbstractTest {
    /**
     * Executes the round-trip repository test.
     */
    @Override
    public boolean runTest() throws Exception {
        String path = FileSystemStorage.getInstance().getAppHomePath()
                + "tests/activity-round-trip-" + System.currentTimeMillis() + ".json";
        TestActivityRepository repository = new TestActivityRepository(path);
        List<Activity> activities = new ArrayList<Activity>();
        Activity original = new Activity(
                "activity-1",
                "Morning walk",
                ActivityCategory.EXERCISE,
                LocalDate.of(2026, 5, 7),
                LocalTime.of(7, 30),
                "Bring water",
                false,
                new PlaceInfo("place-1", "Villa Borghese", "Rome, Italy", 41.9142, 12.4923),
                Instant.parse("2026-05-07T06:10:00Z")
        );
        activities.add(original);

        try {
            repository.saveActivities(activities);
            List<Activity> loaded = repository.loadActivities();

            assertEqual(1, loaded.size(), "Exactly one activity should be loaded");
            assertEqual(original, loaded.get(0), "The activity should survive a JSON round trip");
            return true;
        } finally {
            repository.reset();
        }
    }

    /**
     * Repository variant that redirects persistence to a test-specific file.
     */
    private static final class TestActivityRepository extends ActivityRepository {
        private final String path;

        /**
         * @param path test file path
         */
        private TestActivityRepository(String path) {
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
