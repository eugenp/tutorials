package com.baeldung.cn1tutorial.service;

import com.codename1.testing.AbstractTest;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Verifies bundle availability, fallbacks and runtime installation.
 */
public class LocalizationBundleTest extends AbstractTest {
    /**
     * Executes the localization bundle test.
     */
    @Override
    public boolean runTest() {
        Resources resources = Resources.getGlobalResources();
        assertNotNull(resources, "Global resources should be available during tests");
        LocalizationService localizationService = new LocalizationService(resources);

        Enumeration locales = resources.listL10NLocales("Bundle");
        StringBuilder localeList = new StringBuilder();
        while (locales != null && locales.hasMoreElements()) {
            Object next = locales.nextElement();
            if (localeList.length() > 0) {
                localeList.append(", ");
            }
            localeList.append(next);
        }
        log("Bundle locales: " + localeList);

        Hashtable<String, String> defaultBundle = resources.getL10N("Bundle", "");
        Hashtable<String, String> englishBundle = resources.getL10N("Bundle", "en");
        Hashtable<String, String> italianBundle = resources.getL10N("Bundle", "it");

        log("Default settings.title: " + value(defaultBundle, "settings.title"));
        log("English settings.title: " + value(englishBundle, "settings.title"));
        log("Italian settings.title: " + value(italianBundle, "settings.title"));

        assertNotNull(defaultBundle, "Default bundle should exist");
        assertNotNull(italianBundle, "Italian bundle should exist");
        assertEqual("Settings", value(defaultBundle, "settings.title"));
        assertTrue(englishBundle == null || "Settings".equals(value(englishBundle, "settings.title")));
        assertEqual("Impostazioni", value(italianBundle, "settings.title"));

        localizationService.installBundle("it");
        assertEqual("Impostazioni", UIManager.getInstance().localize("settings.title", "missing"), "Italian localization should resolve");
        assertEqual("Daily Routine", UIManager.getInstance().localize("home.title", "missing"));
        assertEqual("Informazioni", UIManager.getInstance().localize("menu.about", "missing"));
        assertEqual("Impostazioni", UIManager.getInstance().localize("settings.title", "missing"));
        assertEqual("missing.key", UIManager.getInstance().localize("missing.key", "missing.key"), "Fallback behavior should preserve missing keys");
        return true;
    }

    /**
     * Reads one key from a bundle while tolerating a missing bundle.
     *
     * @param bundle bundle to inspect
     * @param key key to read
     * @return stored value or the string {@code "null"}
     */
    private String value(Hashtable<String, String> bundle, String key) {
        return bundle == null ? "null" : bundle.get(key);
    }
}
