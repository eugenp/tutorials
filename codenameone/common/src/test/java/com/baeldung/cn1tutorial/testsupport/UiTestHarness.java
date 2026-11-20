package com.baeldung.cn1tutorial.testsupport;

import com.baeldung.cn1tutorial.model.AppSettings;
import com.baeldung.cn1tutorial.service.ActivityRepository;
import com.baeldung.cn1tutorial.service.NetworkSupport;
import com.baeldung.cn1tutorial.service.PlaceSearchService;
import com.baeldung.cn1tutorial.service.SettingsRepository;
import com.codename1.testing.TestUtils;
import com.codename1.ui.CN;

/**
 * Tiny launcher for running the app with injected fake dependencies in tests.
 */
public class UiTestHarness {
    public final InMemoryActivityRepository activityRepository;
    public final InMemorySettingsRepository settingsRepository;
    public final PlaceSearchService placeSearchService;
    public final TestDailyRoutine app;

    /**
     * Convenience constructor that wires the default in-memory repositories.
     *
     * @param settings initial settings snapshot
     * @param placeSearchService fake search service
     */
    public UiTestHarness(AppSettings settings, FakePlaceSearchService placeSearchService) {
        this(new InMemoryActivityRepository(), new InMemorySettingsRepository(settings), placeSearchService);
    }

    /**
     * Full constructor used by tests that want to supply custom repositories or services.
     *
     * @param activityRepository repository to inject
     * @param settingsRepository repository to inject
     * @param placeSearchService service to inject
     */
    public UiTestHarness(
            ActivityRepository activityRepository,
            SettingsRepository settingsRepository,
            PlaceSearchService placeSearchService
    ) {
        this.activityRepository = activityRepository instanceof InMemoryActivityRepository
                ? (InMemoryActivityRepository) activityRepository
                : null;
        this.settingsRepository = settingsRepository instanceof InMemorySettingsRepository
                ? (InMemorySettingsRepository) settingsRepository
                : null;
        this.placeSearchService = placeSearchService;
        this.app = new TestDailyRoutine(activityRepository, settingsRepository, placeSearchService);
    }

    /**
     * Initializes and launches the test app on the EDT.
     */
    public void launch() {
        CN.callSeriallyAndWait(() -> {
            app.init(null);
            app.runApp();
        });
        TestUtils.waitFor(200);
    }

    /**
     * Resets global network hooks used by tests.
     */
    public void cleanup() {
        NetworkSupport.resetRetryScheduler();
    }
}
