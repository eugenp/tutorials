package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.DailyRoutine;
import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.model.AppSettings;
import com.codename1.ui.Form;

/**
 * Small application facade passed to forms and services.
 * <p>
 * Instead of exposing the whole {@link DailyRoutine} class everywhere, forms receive this context
 * object with just the dependencies and navigation helpers they need. For students this also shows
 * a lightweight alternative to full dependency-injection frameworks.
 */
public class AppContext {
    private final DailyRoutine app;
    private final ActivityStore activityStore;
    private final SettingsRepository settingsRepository;
    private final LocalizationService localizationService;
    private PlaceSearchService placeSearchService;
    private AppSettings settings;

    /**
     * Creates the runtime context shared by the forms.
     *
     * @param app lifecycle entry point and navigation owner
     * @param activityStore in-memory activity state
     * @param settingsRepository persisted settings repository
     * @param localizationService localization helper
     * @param placeSearchService remote search service
     * @param settings current normalized settings
     */
    public AppContext(
            DailyRoutine app,
            ActivityStore activityStore,
            SettingsRepository settingsRepository,
            LocalizationService localizationService,
            PlaceSearchService placeSearchService,
            AppSettings settings
    ) {
        this.app = app;
        this.activityStore = activityStore;
        this.settingsRepository = settingsRepository;
        this.localizationService = localizationService;
        this.placeSearchService = placeSearchService;
        this.settings = settings.normalized();
    }

    /**
     * Resolves one localized string.
     *
     * @param key bundle key
     * @return localized text, or the key itself when missing
     */
    public String text(String key) {
        return localizationService.text(key);
    }

    /**
     * Resolves and formats one localized string with numbered placeholders.
     *
     * @param key bundle key
     * @param args replacement arguments
     * @return formatted localized text
     */
    public String format(String key, Object... args) {
        return localizationService.format(key, args);
    }

    /**
     * @return shared activity store
     */
    public ActivityStore getActivityStore() {
        return activityStore;
    }

    /**
     * @return shared settings repository
     */
    public SettingsRepository getSettingsRepository() {
        return settingsRepository;
    }

    /**
     * @return localization service
     */
    public LocalizationService getLocalizationService() {
        return localizationService;
    }

    /**
     * @return currently configured place-search service
     */
    public PlaceSearchService getPlaceSearchService() {
        return placeSearchService;
    }

    /**
     * Replaces the place-search service, mainly for tests.
     *
     * @param placeSearchService replacement implementation
     */
    public void setPlaceSearchService(PlaceSearchService placeSearchService) {
        this.placeSearchService = placeSearchService;
    }

    /**
     * @return current normalized settings
     */
    public AppSettings getSettings() {
        return settings;
    }

    /**
     * Updates the in-memory settings snapshot.
     *
     * @param settings new settings
     */
    public void setSettings(AppSettings settings) {
        this.settings = settings.normalized();
    }

    /**
     * Applies new settings through the app lifecycle owner.
     *
     * @param newSettings new settings
     * @param refreshCurrentForm whether the current form should be refreshed immediately
     */
    public void applySettings(AppSettings newSettings, boolean refreshCurrentForm) {
        app.applySettings(newSettings, refreshCurrentForm);
    }

    /**
     * Shows the home form.
     */
    public void showHome() {
        app.showHome();
    }

    /**
     * Shows the activity editor.
     *
     * @param previous previous form for back navigation
     * @param activity activity to edit, or {@code null} to create a new one
     */
    public void showActivityForm(Form previous, Activity activity) {
        app.showActivityForm(previous, activity);
    }

    /**
     * Shows the activity details form.
     *
     * @param previous previous form for back navigation
     * @param activity activity to display
     */
    public void showActivityDetails(Form previous, Activity activity) {
        app.showActivityDetails(previous, activity);
    }

    /**
     * Shows the settings form.
     *
     * @param previous previous form for back navigation
     */
    public void showSettings(Form previous) {
        app.showSettings(previous);
    }

    /**
     * Shows the about form.
     *
     * @param previous previous form for back navigation
     */
    public void showAbout(Form previous) {
        app.showAbout(previous);
    }

    /**
     * Shows the combined CN1/native logs form.
     *
     * @param previous previous form for back navigation
     */
    public void showNativeLogs(Form previous) {
        app.showNativeLogs(previous);
    }

    /**
     * Shows the custom crash report form.
     *
     * @param throwable failure to display
     */
    public void showCrash(Throwable throwable) {
        app.showCrash(throwable);
    }
}
