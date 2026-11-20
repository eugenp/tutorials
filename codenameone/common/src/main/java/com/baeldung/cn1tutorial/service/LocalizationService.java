package com.baeldung.cn1tutorial.service;

import com.codename1.l10n.L10NManager;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.Hashtable;

/**
 * Loads and applies the app localization bundles.
 * <p>
 * CN1 stores localization resources inside the theme resources. This helper hides the
 * resource/bundle API so forms can simply ask for translated text through {@link AppContext}.
 */
public class LocalizationService {
    private final Resources resources;
    private String currentLanguage = "en";

    /**
     * @param resources loaded theme resources that also contain the localization bundles
     */
    public LocalizationService(Resources resources) {
        this.resources = resources;
    }

    /**
     * Installs the requested bundle into {@link UIManager} and updates the CN1 locale.
     *
     * @param languageCode requested language code
     */
    public void installBundle(String languageCode) {
        String language = "it".equalsIgnoreCase(languageCode) ? "it" : "en";
        currentLanguage = language;
        String bundleLocale = "it".equals(language) ? "it" : "";
        Hashtable<String, String> bundle = resources.getL10N("Bundle", bundleLocale);
        if ((bundle == null || bundle.isEmpty()) && bundleLocale.length() > 0) {
            bundle = resources.getL10N("Bundle", "");
        }
        bundle = bundle == null ? new Hashtable<String, String>() : new Hashtable<String, String>(bundle);
        UIManager.getInstance().setBundle(bundle);
        L10NManager.getInstance().setLocale(language, "it".equals(language) ? "IT" : "US");
    }

    /**
     * Resolves one localized string.
     *
     * @param key bundle key
     * @return localized text, or the key itself when missing
     */
    public String text(String key) {
        return UIManager.getInstance().localize(key, key);
    }

    /**
     * Replaces numbered placeholders such as {@code {0}} and {@code {1}}.
     *
     * @param key bundle key
     * @param args replacement arguments
     * @return formatted localized string
     */
    public String format(String key, Object... args) {
        String value = text(key);
        if (args == null) {
            return value;
        }
        String formatted = value;
        for (int i = 0; i < args.length; i++) {
            String token = "{" + i + "}";
            formatted = replace(formatted, token, String.valueOf(args[i]));
        }
        return formatted;
    }

    /**
     * @return currently installed language code
     */
    public String currentLanguage() {
        return currentLanguage;
    }

    /**
     * Simple token replacement used by {@link #format(String, Object...)}.
     *
     * @param text source text
     * @param token placeholder to replace
     * @param replacement replacement text
     * @return replaced string
     */
    private String replace(String text, String token, String replacement) {
        int index = text.indexOf(token);
        if (index < 0) {
            return text;
        }
        StringBuilder builder = new StringBuilder();
        int start = 0;
        while (index >= 0) {
            builder.append(text, start, index);
            builder.append(replacement);
            start = index + token.length();
            index = text.indexOf(token, start);
        }
        builder.append(text.substring(start));
        return builder.toString();
    }
}
