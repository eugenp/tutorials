package com.baeldung.java14.recordscustomconstructors;

import java.util.Map;

record UserPreference(Map<String, String> preferences, boolean superUser) {

    public static final UserPreference DEFAULT = new UserPreference(Map.of("language", "EN", "timezone", "UTC"), false);
}