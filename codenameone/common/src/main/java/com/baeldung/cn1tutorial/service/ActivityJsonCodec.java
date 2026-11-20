package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.model.ActivityCategory;
import com.baeldung.cn1tutorial.model.AppearanceMode;
import com.baeldung.cn1tutorial.model.AppSettings;
import com.baeldung.cn1tutorial.model.PlaceInfo;
import com.codename1.io.JSONParser;
import com.codename1.processing.Result;
import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Serializes and deserializes the app's local JSON files.
 * <p>
 * Keeping codec logic separate from repositories makes the persistence format easy to test and
 * keeps file-system code out of the JSON parsing paths.
 */
public class ActivityJsonCodec {
    /**
     * Encodes the full activity list into JSON.
     *
     * @param activities activities to serialize
     * @return JSON document
     */
    public String encodeActivities(List<Activity> activities) {
        Map<String, Object> root = new LinkedHashMap<String, Object>();
        root.put("version", Integer.valueOf(1));
        List<Map<String, Object>> encoded = new ArrayList<Map<String, Object>>();
        for (Activity activity : activities) {
            encoded.add(toMap(activity));
        }
        root.put("activities", encoded);
        return Result.fromContent(root).toString();
    }

    /**
     * Decodes the activity file.
     *
     * @param json JSON document
     * @return decoded activities
     * @throws IOException if the JSON does not match the expected shape
     */
    public List<Activity> decodeActivities(String json) throws IOException {
        JSONParser parser = new JSONParser();
        parser.setStrict(true);
        Map<String, Object> root = parser.parseJSON(new StringReader(json));
        if (root == null || !root.containsKey("activities")) {
            throw new IOException("Malformed activities JSON");
        }
        List<Activity> decoded = new ArrayList<Activity>();
        Object rawActivities = root.get("activities");
        if (!(rawActivities instanceof List)) {
            throw new IOException("Malformed activities JSON");
        }
        List list = (List) rawActivities;
        for (Object item : list) {
            if (item instanceof Map) {
                decoded.add(fromMap((Map<String, Object>) item));
            }
        }
        return decoded;
    }

    /**
     * Encodes the settings file.
     *
     * @param settings settings to serialize
     * @return JSON document
     */
    public String encodeSettings(AppSettings settings) {
        Map<String, Object> root = new LinkedHashMap<String, Object>();
        root.put("version", Integer.valueOf(2));
        root.put("languageCode", settings.languageCode());
        root.put("fontScalePercent", Integer.valueOf(settings.fontScalePercent()));
        root.put("appearanceMode", settings.appearanceMode().name());
        return Result.fromContent(root).toString();
    }

    /**
     * Decodes the settings file.
     *
     * @param json JSON document
     * @return normalized settings
     * @throws IOException if parsing fails
     */
    public AppSettings decodeSettings(String json) throws IOException {
        JSONParser parser = new JSONParser();
        Map<String, Object> root = parser.parseJSON(new StringReader(json));
        String languageCode = stringValue(root.get("languageCode"));
        int fontScale = intValue(root.get("fontScalePercent"), AppSettings.DEFAULT_FONT_SCALE);
        AppearanceMode appearanceMode = AppearanceMode.fromStorage(stringValue(root.get("appearanceMode")));
        return new AppSettings(languageCode, fontScale, appearanceMode).normalized();
    }

    /**
     * Encodes one activity into a JSON-friendly map.
     *
     * @param activity activity to serialize
     * @return JSON-friendly map
     */
    private Map<String, Object> toMap(Activity activity) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("id", activity.id());
        map.put("title", activity.title());
        map.put("category", activity.category().storageCode());
        if (activity.date() != null) {
            map.put("date", activity.date().toString());
        }
        if (activity.time() != null) {
            map.put("time", activity.time().toString());
        }
        map.put("notes", activity.notes());
        map.put("completed", Boolean.valueOf(activity.completed()));
        map.put("updatedAt", activity.updatedAt().toString());
        if (activity.place() != null) {
            map.put("place", toMap(activity.place()));
        }
        return map;
    }

    /**
     * Encodes one place into a nested JSON-friendly map.
     *
     * @param place place to serialize
     * @return JSON-friendly map
     */
    private Map<String, Object> toMap(PlaceInfo place) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("placeId", place.placeId());
        map.put("name", place.name());
        map.put("address", place.address());
        map.put("latitude", Double.valueOf(place.latitude()));
        map.put("longitude", Double.valueOf(place.longitude()));
        return map;
    }

    /**
     * Decodes one activity entry from the parsed JSON structure.
     *
     * @param map parsed JSON map
     * @return decoded activity
     */
    private Activity fromMap(Map<String, Object> map) {
        String id = stringValue(map.get("id"));
        String title = stringValue(map.get("title"));
        ActivityCategory category = ActivityCategory.fromStorage(stringValue(map.get("category")));
        LocalDate date = parseDate(stringValue(map.get("date")));
        LocalTime time = parseTime(stringValue(map.get("time")));
        String notes = stringValue(map.get("notes"));
        boolean completed = booleanValue(map.get("completed"));
        Instant updatedAt = parseInstant(stringValue(map.get("updatedAt")));
        PlaceInfo place = null;
        Object rawPlace = map.get("place");
        if (rawPlace instanceof Map) {
            place = fromPlace((Map<String, Object>) rawPlace);
        }
        return new Activity(id, title, category, date, time, notes, completed, place, updatedAt);
    }

    /**
     * Decodes a nested place map.
     *
     * @param map parsed JSON place map
     * @return decoded place
     */
    private PlaceInfo fromPlace(Map<String, Object> map) {
        return new PlaceInfo(
                stringValue(map.get("placeId")),
                stringValue(map.get("name")),
                stringValue(map.get("address")),
                doubleValue(map.get("latitude")),
                doubleValue(map.get("longitude"))
        );
    }

    /**
     * Normalizes any JSON value into a string.
     *
     * @param value raw JSON value
     * @return string representation, never {@code null}
     */
    private String stringValue(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    /**
     * Parses an integer with a fallback.
     *
     * @param value raw JSON value
     * @param fallback value used when parsing fails
     * @return parsed or fallback integer
     */
    private int intValue(Object value, int fallback) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return Integer.parseInt(stringValue(value));
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    /**
     * Parses a boolean from JSON content.
     *
     * @param value raw JSON value
     * @return parsed boolean
     */
    private boolean booleanValue(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        return "true".equalsIgnoreCase(stringValue(value));
    }

    /**
     * Parses a double with a defensive fallback.
     *
     * @param value raw JSON value
     * @return parsed or fallback double
     */
    private double doubleValue(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(stringValue(value));
        } catch (NumberFormatException ex) {
            return 0d;
        }
    }

    /**
     * Parses a local date, tolerating missing values.
     *
     * @param text ISO date text
     * @return parsed date, or {@code null}
     */
    private LocalDate parseDate(String text) {
        return text == null || text.length() == 0 ? null : LocalDate.parse(text);
    }

    /**
     * Parses a local time, tolerating missing values.
     *
     * @param text ISO time text
     * @return parsed time, or {@code null}
     */
    private LocalTime parseTime(String text) {
        return text == null || text.length() == 0 ? null : LocalTime.parse(text);
    }

    /**
     * Parses an instant, using the current time when the source data is missing.
     *
     * @param text ISO instant text
     * @return parsed instant
     */
    private Instant parseInstant(String text) {
        return text == null || text.length() == 0 ? Instant.now() : Instant.parse(text);
    }
}
