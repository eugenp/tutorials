package com.baeldung.cn1tutorial;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.model.AppSettings;
import com.baeldung.cn1tutorial.service.ActivityRepository;
import com.baeldung.cn1tutorial.service.ActivityStore;
import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.service.GeoapifyPlaceSearchService;
import com.baeldung.cn1tutorial.service.LocalizationService;
import com.baeldung.cn1tutorial.service.NativeLogSupport;
import com.baeldung.cn1tutorial.service.NetworkSupport;
import com.baeldung.cn1tutorial.service.PlaceSearchService;
import com.baeldung.cn1tutorial.service.SettingsRepository;
import com.baeldung.cn1tutorial.ui.AboutForm;
import com.baeldung.cn1tutorial.ui.ActivityDetailsForm;
import com.baeldung.cn1tutorial.ui.ActivityForm;
import com.baeldung.cn1tutorial.ui.AutoShrinkSupport;
import com.baeldung.cn1tutorial.ui.CrashReportForm;
import com.baeldung.cn1tutorial.ui.HomeForm;
import com.baeldung.cn1tutorial.ui.NativeLogsForm;
import com.baeldung.cn1tutorial.ui.SettingsForm;
import com.baeldung.cn1tutorial.util.ThrowableUtil;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.system.Lifecycle;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.CN;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Locale;

/**
 * Main Codename One lifecycle entry point for the tutorial app.
 * <p>
 * This class wires together repositories, services and forms, then delegates almost all UI work to
 * the form classes. Keeping the lifecycle setup here makes the rest of the code easier to read,
 * especially for students approaching Codename One for the first time.
 */
public class DailyRoutine extends Lifecycle {
    private static DailyRoutine activeInstance;
    private static boolean edtErrorHandlerInstalled;
    private static DailyRoutine crashOwner;

    private AppContext appContext;
    private Hashtable baseThemeProps;
    private String themeName;

    /**
     * Bootstraps repositories, services, localization and error handling.
     * <p>
     * In CN1 {@code init()} runs once when the app starts, before any form is shown.
     */
    @Override
    public void init(Object context) {
        super.init(context);
        activeInstance = this;
        crashOwner = this;
        configureCn1LogCapture();
        NativeLogSupport.initOnce();
        logEnvironmentDetails();
        CN.setProperty("WebLoadingHidden", "true");
        Toolbar.setGlobalToolbar(true);
        Toolbar.setOnTopSideMenu(true);
        Toolbar.setEnableSideMenuSwipe(false);
        Log.setReportingLevel(Log.REPORTING_NONE);
        Log.setLevel(Log.DEBUG);
        Log.bindCrashProtection(false);
        ConnectionRequestDefaults.apply();
        installEdtErrorHandler();
        try {
            ActivityRepository activityRepository = createActivityRepository();
            SettingsRepository settingsRepository = createSettingsRepository();
            ActivityStore activityStore = new ActivityStore(activityRepository);
            Resources themeResources = getTheme();
            LocalizationService localizationService = createLocalizationService(themeResources);
            PlaceSearchService placeSearchService = createPlaceSearchService();
            themeName = resolveThemeName(themeResources);
            baseThemeProps = copyThemeProps(themeResources.getTheme(themeName));

            AppSettings settings = settingsRepository.load();
            appContext = createAppContext(activityStore, settingsRepository, localizationService, placeSearchService, settings);
            applySettings(settings, false);
            activityStore.load();
        } catch (Exception ex) {
            Log.e(ex);
            showCrash(ex);
        }
    }

    /**
     * Disables the built-in crash screen so the tutorial can demonstrate a custom one.
     */
    @Override
    protected void bindCrashProtection() {
        // Intentionally disabled so the tutorial can demonstrate custom crash reporting.
    }

    /**
     * Limits the app to two networking threads, which is enough for this small sample.
     *
     * @return network thread count
     */
    @Override
    protected int getNetworkThreadCount() {
        return 2;
    }

    /**
     * Routes unexpected framework-level network errors to the shared helper.
     *
     * @param err network event raised by CN1
     */
    @Override
    protected void handleNetworkError(com.codename1.io.NetworkEvent err) {
        NetworkSupport.handleUnexpectedNetworkError(appContext, err);
    }

    /**
     * Displays the first form after initialization.
     */
    @Override
    public void runApp() {
        if (appContext == null) {
            return;
        }
        showHome();
    }

    /**
     * Reapplies theme-dependent sizing when the app returns to the foreground.
     * <p>
     * This is especially important for the auto-shrink helper because font/theme changes can happen
     * while the app is suspended.
     */
    @Override
    public void start() {
        super.start();
        Form current = CN.getCurrentForm();
        if (current != null) {
            AutoShrinkSupport.prepareForThemeRefresh(current);
            current.refreshTheme();
            AutoShrinkSupport.refreshTitleComponent(current);
            current.revalidate();
            AutoShrinkSupport.resetAndApply(current);
            AutoShrinkSupport.resetAndApplyLater(current);
        }
    }

    /**
     * Shows the home screen.
     */
    public void showHome() {
        new HomeForm(appContext).show();
    }

    /**
     * Shows the activity editor.
     *
     * @param previous previous form for back navigation
     * @param activity activity to edit, or {@code null} to create a new one
     */
    public void showActivityForm(Form previous, Activity activity) {
        new ActivityForm(appContext, previous, activity).show();
    }

    /**
     * Shows the activity details screen.
     *
     * @param previous previous form for back navigation
     * @param activity activity to display
     */
    public void showActivityDetails(Form previous, Activity activity) {
        new ActivityDetailsForm(appContext, previous, activity).show();
    }

    /**
     * Shows the settings screen.
     *
     * @param previous previous form for back navigation
     */
    public void showSettings(Form previous) {
        new SettingsForm(appContext, previous).show();
    }

    /**
     * Shows the about screen.
     *
     * @param previous previous form for back navigation
     */
    public void showAbout(Form previous) {
        new AboutForm(appContext, previous).show();
    }

    /**
     * Shows the diagnostics/logs screen.
     *
     * @param previous previous form for back navigation
     */
    public void showNativeLogs(Form previous) {
        new NativeLogsForm(appContext, previous).show();
    }

    /**
     * Applies user settings to localization, theme and persisted preferences.
     *
     * @param settings new settings to apply
     * @param refreshCurrentForm whether the currently displayed form should refresh immediately
     */
    public void applySettings(AppSettings settings, boolean refreshCurrentForm) {
        if (appContext == null) {
            return;
        }
        AppSettings normalized = settings.normalized();
        appContext.setSettings(normalized);
        if (baseThemeProps != null) {
            UIManager.getInstance().setThemeProps(copyThemeProps(baseThemeProps));
        }
        appContext.getLocalizationService().installBundle(normalized.languageCode());
        CN.setDarkMode(normalized.darkModeOverride());
        UIManager.getInstance().zoomFonts(normalized.fontScaleFactor());
        Form current = CN.getCurrentForm();
        if (refreshCurrentForm && current != null) {
            AutoShrinkSupport.prepareForThemeRefresh(current);
            current.refreshTheme();
            AutoShrinkSupport.refreshTitleComponent(current);
            current.revalidate();
            AutoShrinkSupport.resetAndApply(current);
            AutoShrinkSupport.resetAndApplyLater(current);
        }
        try {
            appContext.getSettingsRepository().save(normalized);
        } catch (RuntimeException ex) {
            Log.e(ex);
            Dialog.show(appContext.text("error.title"), appContext.text("settings.save.error"), appContext.text("ok"), null);
        }
    }

    /**
     * Shows the custom crash report form.
     *
     * @param throwable failure to render
     */
    public void showCrash(Throwable throwable) {
        String title = appContext != null ? appContext.text("crash.title") : "Crash Report";
        new CrashReportForm(appContext, throwable, title).show();
    }

    /**
     * @return runtime context shared by the forms
     */
    public AppContext getAppContext() {
        return appContext;
    }

    /**
     * @return active app instance, mainly for tests and diagnostics
     */
    public static DailyRoutine getActiveInstance() {
        return activeInstance;
    }

    /**
     * Installs a global EDT error handler once per process.
     * <p>
     * In CN1 most UI code runs on the Event Dispatch Thread, so this catches the kind of crashes
     * students are most likely to trigger while experimenting with forms.
     */
    private void installEdtErrorHandler() {
        crashOwner = this;
        if (edtErrorHandlerInstalled) {
            return;
        }
        edtErrorHandlerInstalled = true;
        CN.addEdtErrorHandler(event -> {
            Object source = event.getSource();
            Throwable throwable;
            if (source instanceof Throwable) {
                throwable = (Throwable) source;
            } else {
                throwable = new RuntimeException(String.valueOf(source));
            }
            Log.e(throwable);
            DailyRoutine owner = crashOwner;
            if (owner != null) {
                owner.showCrash(throwable);
            }
        });
    }

    /**
     * Factory method kept protected so tests can replace the repository.
     *
     * @return activity repository
     */
    protected ActivityRepository createActivityRepository() {
        return new ActivityRepository();
    }

    /**
     * Factory method kept protected so tests can replace the repository.
     *
     * @return settings repository
     */
    protected SettingsRepository createSettingsRepository() {
        return new SettingsRepository();
    }

    /**
     * Factory method kept protected so tests can replace localization behavior.
     *
     * @param themeResources loaded theme resources
     * @return localization service
     */
    protected LocalizationService createLocalizationService(Resources themeResources) {
        return new LocalizationService(themeResources);
    }

    /**
     * Factory method kept protected so tests can replace the network service.
     *
     * @return place search service
     */
    protected PlaceSearchService createPlaceSearchService() {
        return new GeoapifyPlaceSearchService();
    }

    /**
     * Factory method that assembles the shared app context.
     *
     * @param activityStore in-memory activity state
     * @param settingsRepository settings repository
     * @param localizationService localization service
     * @param placeSearchService place search backend
     * @param settings current app settings
     * @return configured app context
     */
    protected AppContext createAppContext(
            ActivityStore activityStore,
            SettingsRepository settingsRepository,
            LocalizationService localizationService,
            PlaceSearchService placeSearchService,
            AppSettings settings
    ) {
        return new AppContext(
                this,
                activityStore,
                settingsRepository,
                localizationService,
                placeSearchService,
                settings
        );
    }

    /**
     * Creates a defensive copy of theme properties before they are mutated at runtime.
     *
     * @param source theme property table
     * @return copied table, or {@code null}
     */
    @SuppressWarnings("rawtypes")
    private Hashtable copyThemeProps(Hashtable source) {
        return source == null ? null : new Hashtable(source);
    }

    /**
     * Resolves the theme name embedded in the resource file.
     *
     * @param resources loaded theme resources
     * @return first available theme name, or a default label
     */
    private String resolveThemeName(Resources resources) {
        String[] themeNames = resources.getThemeResourceNames();
        if (themeNames != null && themeNames.length > 0) {
            return themeNames[0];
        }
        return "Theme";
    }

    /**
     * Starts the CN1 log file with a fresh environment report for the current run.
     */
    private void logEnvironmentDetails() {
        Log.deleteLog();
        Log.p(buildEnvironmentReport());
    }

    /**
     * Configures CN1's file-based logger so the diagnostics form can read it back later.
     */
    private void configureCn1LogCapture() {
        try {
            String logFile = FileSystemStorage.getInstance().getAppHomePath() + "daily-routine-cn1.log";
            Log.getInstance().setFileURL(logFile);
            Log.getInstance().setFileWriteEnabled(true);
        } catch (RuntimeException ex) {
            Log.e(ex);
        }
    }

    /**
     * Builds a plain-text report with environment details useful while diagnosing port-specific
     * issues such as browser support, Java runtime differences or native library lookup problems.
     *
     * @return multiline diagnostic report
     */
    public String buildEnvironmentReport() {
        String userHome = property("user.home");
        String javaHome = property("java.home");
        StringBuilder out = new StringBuilder();
        out.append("Daily Routine environment").append('\n');
        out.append("codenameone.revision=").append(codenameOneRevision()).append('\n');
        out.append("java.version=").append(property("java.version")).append('\n');
        out.append("java.vendor=").append(property("java.vendor")).append('\n');
        out.append("java.runtime.name=").append(property("java.runtime.name")).append('\n');
        out.append("java.runtime.version=").append(property("java.runtime.version")).append('\n');
        out.append("java.vm.name=").append(property("java.vm.name")).append('\n');
        out.append("java.vm.version=").append(property("java.vm.version")).append('\n');
        out.append("java.home=").append(javaHome).append('\n');
        out.append("JAVA_HOME=").append(environment("JAVA_HOME")).append('\n');
        out.append("os.name=").append(property("os.name")).append('\n');
        out.append("os.version=").append(property("os.version")).append('\n');
        out.append("os.arch=").append(property("os.arch")).append('\n');
        out.append("user.dir=").append(property("user.dir")).append('\n');
        out.append("user.home=").append(userHome).append('\n');
        out.append("locale.default=").append(String.valueOf(Locale.getDefault())).append('\n');
        out.append("file.encoding=").append(property("file.encoding")).append('\n');
        out.append("sun.jnu.encoding=").append(property("sun.jnu.encoding")).append('\n');
        out.append("java.awt.headless=").append(property("java.awt.headless")).append('\n');
        out.append("cn1.javase.implementation=").append(property("cn1.javase.implementation")).append('\n');
        out.append("cef.dir=").append(property("cef.dir")).append('\n');
        out.append("LD_LIBRARY_PATH=").append(environment("LD_LIBRARY_PATH")).append('\n');
        out.append("java.library.path=").append(property("java.library.path")).append('\n');
        out.append("sun.boot.library.path=").append(property("sun.boot.library.path")).append('\n');
        out.append("simulator=").append(CN.isSimulator()).append('\n');
        out.append("browser.native.supported=").append(browserSupport()).append('\n');
        out.append("~/.codenameone/cef.exists=").append(pathExists(userHome + "/.codenameone/cef")).append('\n');
        out.append("~/.codenameone/cef/lib/linux64.exists=").append(pathExists(userHome + "/.codenameone/cef/lib/linux64")).append('\n');
        out.append("~/.m2/codenameone-cef.exists=").append(pathExists(userHome + "/.m2/repository/com/codenameone/codenameone-cef")).append('\n');
        return out.toString();
    }

    /**
     * Probes native browser support without letting a failing peer component crash diagnostics.
     *
     * @return boolean support value or a textual error
     */
    private String browserSupport() {
        try {
            return String.valueOf(BrowserComponent.isNativeBrowserSupported());
        } catch (Throwable throwable) {
            return "ERROR: " + ThrowableUtil.stackTraceToString(throwable);
        }
    }

    /**
     * Reads the Codename One revision hash from the same bundled resource used by CN1's logger.
     *
     * @return Codename One revision hash, or {@code <unavailable>}
     */
    private String codenameOneRevision() {
        String revision = resourceText("/cn1-version-numbers").trim();
        return revision.length() == 0 ? "<unavailable>" : revision;
    }

    /**
     * Loads a small classpath resource through CN1's portable resource lookup.
     *
     * @param path absolute resource path
     * @return resource contents, or an empty string when unavailable
     */
    private String resourceText(String path) {
        InputStream input = null;
        try {
            input = CN.getResourceAsStream(path);
            return input == null ? "" : Util.readToString(input);
        } catch (IOException ex) {
            Log.e(ex);
            return "";
        } finally {
            Util.cleanup(input);
        }
    }

    /**
     * Reads a system property through CN1's abstraction so it works consistently across ports.
     *
     * @param key property name
     * @return property value, or {@code <unset>}
     */
    private String property(String key) {
        return CN.getProperty(key, "<unset>");
    }

    /**
     * Reads one environment-like value through the same CN1 abstraction used elsewhere in the app.
     *
     * @param key variable name
     * @return variable value, or {@code <unset>}
     */
    private String environment(String key) {
        return CN.getProperty(key, "<unset>");
    }

    /**
     * Checks the existence of a file-system path using CN1 storage APIs.
     *
     * @param absolutePath absolute native path
     * @return {@code true} when the path exists
     */
    private boolean pathExists(String absolutePath) {
        if (absolutePath == null || absolutePath.length() == 0 || "<unset>".equals(absolutePath)) {
            return false;
        }
        String path = absolutePath.startsWith("file:") ? absolutePath : "file://" + absolutePath;
        return FileSystemStorage.getInstance().exists(path);
    }

    /**
     * Centralizes one-time global networking defaults.
     */
    private static final class ConnectionRequestDefaults {
        /**
         * Applies request defaults that keep error handling inside the app instead of the framework.
         */
        private static void apply() {
            com.codename1.io.ConnectionRequest.setHandleErrorCodesInGlobalErrorHandler(false);
            com.codename1.io.ConnectionRequest.setReadResponseForErrorsDefault(true);
        }
    }
}
