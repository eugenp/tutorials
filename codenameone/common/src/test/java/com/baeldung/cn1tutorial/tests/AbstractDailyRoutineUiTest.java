package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.DailyRoutine;
import com.baeldung.cn1tutorial.model.AppSettings;
import com.baeldung.cn1tutorial.model.AppearanceMode;
import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.service.NetworkSupport;
import com.baeldung.cn1tutorial.testsupport.FakePlaceSearchService;
import com.codename1.testing.AbstractTest;
import com.codename1.ui.CN;

/**
 * Shared base class for UI tests running against the tutorial app.
 * <p>
 * It redirects repositories to test-only storage, installs fakes and relaunches the home screen so
 * each test starts from a predictable state without deleting the developer's real app data.
 */
abstract class AbstractDailyRoutineUiTest extends AbstractTest {
    protected DailyRoutine app;
    protected AppContext context;
    protected FakePlaceSearchService placeSearchService;
    private String previousAppHomeOverride;

    /**
     * Prepares a clean app state before each UI test.
     */
    @Override
    public void prepare() {
        app = DailyRoutine.getActiveInstance();
        assertNotNull(app, "The Codename One test runner should already have started the app");
        context = app.getAppContext();
        assertNotNull(context, "The application context should be initialized");
        previousAppHomeOverride = System.getProperty("dailyroutine.appHome");
        System.setProperty("dailyroutine.appHome", CN.getAppHomePath()
                + "tests/ui-" + System.currentTimeMillis() + "-" + Math.abs(hashCode()) + "/");
        context.getActivityStore().reset();
        context.getSettingsRepository().reset();
        placeSearchService = new FakePlaceSearchService();
        context.setPlaceSearchService(placeSearchService);
        app.applySettings(new AppSettings("en", AppSettings.DEFAULT_FONT_SCALE, AppearanceMode.SYSTEM), false);
        CN.callSeriallyAndWait(() -> app.showHome());
        waitForFormTitle("Daily Routine");
        waitFor(150);
    }

    /**
     * Restores global test hooks and clears repositories after each test.
     */
    @Override
    public void cleanup() {
        try {
            NetworkSupport.resetRetryScheduler();
            NetworkSupport.resetUiDelegate();
            if (context != null) {
                context.getActivityStore().reset();
                context.getSettingsRepository().reset();
            }
        } finally {
            if (previousAppHomeOverride == null) {
                System.clearProperty("dailyroutine.appHome");
            } else {
                System.setProperty("dailyroutine.appHome", previousAppHomeOverride);
            }
            previousAppHomeOverride = null;
        }
    }
}
