package com.baeldung.cn1tutorial.testsupport;

import com.baeldung.cn1tutorial.DailyRoutine;
import com.baeldung.cn1tutorial.service.ActivityRepository;
import com.baeldung.cn1tutorial.service.PlaceSearchService;
import com.baeldung.cn1tutorial.service.SettingsRepository;

/**
 * Test-specific app subclass that injects fake repositories and services.
 */
public class TestDailyRoutine extends DailyRoutine {
    private final ActivityRepository activityRepository;
    private final SettingsRepository settingsRepository;
    private final PlaceSearchService placeSearchService;

    /**
     * @param activityRepository repository to inject
     * @param settingsRepository repository to inject
     * @param placeSearchService service to inject
     */
    public TestDailyRoutine(
            ActivityRepository activityRepository,
            SettingsRepository settingsRepository,
            PlaceSearchService placeSearchService
    ) {
        this.activityRepository = activityRepository;
        this.settingsRepository = settingsRepository;
        this.placeSearchService = placeSearchService;
    }

    /**
     * Returns the injected repository.
     */
    @Override
    protected ActivityRepository createActivityRepository() {
        return activityRepository;
    }

    /**
     * Returns the injected repository.
     */
    @Override
    protected SettingsRepository createSettingsRepository() {
        return settingsRepository;
    }

    /**
     * Returns the injected fake service.
     */
    @Override
    protected PlaceSearchService createPlaceSearchService() {
        return placeSearchService;
    }
}
