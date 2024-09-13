package com.baeldung.java14.recordsCustomConstructors;

import java.util.Map;

record UserPreference(Map<String, String> preferences, Boolean superUser) {

    public static final UserPreference DEFAULT = new UserPreference(Map.of("language", "EN", "timezone", "UTC"), false);
}