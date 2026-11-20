package com.baeldung.cn1tutorial.util;

import com.codename1.io.Util;

/**
 * Centralizes app-level constants that would otherwise be duplicated in UI and service code.
 * <p>
 * The API key is intentionally only lightly obfuscated. The goal is didactic: show one simple way
 * to avoid pasting a raw key into sample code, not provide real secret management.
 */
public final class AppConfig {
    private static final String GEOAPIFY_API_KEY_ENCODED = "M2QxYGdgZTtvPjppOTw/ISgiKyd2IyEvfS5/LHwuKEE=";
    public static final String GEOAPIFY_TILE_STYLE = "osm-carto";
    public static final String GEOAPIFY_STATIC_STYLE = "osm-carto";
    public static final int GEOAPIFY_RESULT_LIMIT = 5;
    public static final int MAP_ZOOM = 15;

    /**
     * Utility class; do not instantiate.
     */
    private AppConfig() {
    }

    /**
     * Returns the decoded Geoapify API key.
     *
     * @return configured API key
     */
    public static String geoapifyApiKey() {
        return Util.xorDecode(GEOAPIFY_API_KEY_ENCODED);
    }

    /**
     * Returns whether a non-placeholder Geoapify key appears to be configured.
     *
     * @return {@code true} when the key looks usable
     */
    public static boolean isGeoapifyConfigured() {
        String apiKey = geoapifyApiKey();
        return apiKey != null && apiKey.trim().length() > 10;
    }
}
