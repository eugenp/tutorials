package com.baeldung.cn1tutorial.model;

/**
 * User-facing choice for the app appearance policy.
 */
public enum AppearanceMode {
    SYSTEM,
    LIGHT,
    DARK;

    /**
     * Resolves the enum from persisted storage.
     *
     * @param value raw JSON value
     * @return matching mode, or {@link #SYSTEM} as a fallback
     */
    public static AppearanceMode fromStorage(String value) {
        if (value == null) {
            return SYSTEM;
        }
        for (AppearanceMode mode : values()) {
            if (mode.name().equalsIgnoreCase(value)) {
                return mode;
            }
        }
        return SYSTEM;
    }

    /**
     * Adapts the enum to Codename One's dark mode API.
     *
     * @return {@code null} to follow the device, or an explicit light/dark override
     */
    public Boolean darkModeOverride() {
        switch (this) {
            case LIGHT:
                return Boolean.FALSE;
            case DARK:
                return Boolean.TRUE;
            default:
                return null;
        }
    }
}
