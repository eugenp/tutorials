package com.baeldung.cn1tutorial.model;

import com.codename1.l10n.L10NManager;

/**
 * Immutable user preferences persisted locally.
 * <p>
 * The record is normalized before use so the rest of the application can assume values are in the
 * supported range even when the JSON file is old or manually edited.
 */
public record AppSettings(String languageCode, int fontScalePercent, AppearanceMode appearanceMode) {
    public static final int DEFAULT_FONT_SCALE = 100;

    /**
     * Builds the initial settings based on the current device locale.
     *
     * @return default settings for a first app launch
     */
    public static AppSettings defaults() {
        String language = L10NManager.getInstance().getLanguage();
        if (!"it".equalsIgnoreCase(language)) {
            language = "en";
        }
        return new AppSettings(language, DEFAULT_FONT_SCALE, AppearanceMode.SYSTEM);
    }

    /**
     * Sanitizes language, font scale and appearance mode.
     *
     * @return normalized settings ready to be applied to the UI
     */
    public AppSettings normalized() {
        String normalizedLanguage = "it".equalsIgnoreCase(languageCode) ? "it" : "en";
        int normalizedScale = fontScalePercent;
        if (normalizedScale < 80) {
            normalizedScale = 80;
        }
        if (normalizedScale > 130) {
            normalizedScale = 130;
        }
        AppearanceMode normalizedAppearance = appearanceMode == null ? AppearanceMode.SYSTEM : appearanceMode;
        return new AppSettings(normalizedLanguage, normalizedScale, normalizedAppearance);
    }

    /**
     * Converts the stored percentage into the multiplier expected by
     * {@link com.codename1.ui.plaf.UIManager#zoomFonts(float)}.
     *
     * @return font scale factor
     */
    public float fontScaleFactor() {
        return normalized().fontScalePercent() / 100f;
    }

    /**
     * Converts the appearance choice into the value expected by {@code CN.setDarkMode(...)}.
     *
     * @return {@code Boolean.TRUE}, {@code Boolean.FALSE}, or {@code null} for follow-device
     */
    public Boolean darkModeOverride() {
        return normalized().appearanceMode().darkModeOverride();
    }
}
