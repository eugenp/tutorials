package com.baeldung.cn1tutorial.model;

/**
 * Enumerates the fixed categories offered by the tutorial app.
 * <p>
 * Each enum value carries:
 * <ul>
 *     <li>a stable storage code used in JSON, so localization never leaks into persisted data;</li>
 *     <li>a localization key used by the UI;</li>
 *     <li>a sort order used by {@link com.baeldung.cn1tutorial.service.ActivityStore}.</li>
 * </ul>
 */
public enum ActivityCategory {
    APPOINTMENT("appointment", "category.appointment", 0),
    ROUTINE("routine", "category.routine", 1),
    WORK("work", "category.work", 2),
    STUDY("study", "category.study", 3),
    EXERCISE("exercise", "category.exercise", 4),
    MEAL("meal", "category.meal", 5),
    MEDICATION("medication", "category.medication", 6),
    HYGIENE("hygiene", "category.hygiene", 7),
    CARE("care", "category.care", 8),
    HOUSEHOLD("household", "category.household", 9),
    SHOPPING("shopping", "category.shopping", 10),
    FAMILY("family", "category.family", 11),
    OTHER("other", "category.other", 12);

    private final String storageCode;
    private final String labelKey;
    private final int sortOrder;

    ActivityCategory(String storageCode, String labelKey, int sortOrder) {
        this.storageCode = storageCode;
        this.labelKey = labelKey;
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the stable value written to JSON.
     *
     * @return storage-safe category code
     */
    public String storageCode() {
        return storageCode;
    }

    /**
     * Returns the localization key used to render the category in the UI.
     *
     * @return bundle key for the category label
     */
    public String labelKey() {
        return labelKey;
    }

    /**
     * Returns the ordinal used by the home screen sort logic.
     *
     * @return category sort position
     */
    public int sortOrder() {
        return sortOrder;
    }

    /**
     * Resolves a category from persisted JSON data.
     * <p>
     * The method accepts both the new storage code and the enum name so existing JSON written by
     * older revisions keeps loading correctly.
     *
     * @param value raw persisted value
     * @return matching category, or {@link #ROUTINE} as a defensive fallback
     */
    public static ActivityCategory fromStorage(String value) {
        if (value == null || value.trim().length() == 0) {
            return ROUTINE;
        }
        for (ActivityCategory category : values()) {
            if (category.storageCode.equalsIgnoreCase(value) || category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        return ROUTINE;
    }
}
