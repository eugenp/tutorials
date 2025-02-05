package com.baeldung.enums;

/**
 * Represents day in a week.
 */
public enum Weekday {
    /**
     * Monday.
     */
    MONDAY("Monday"),
    /**
     * Tuesday.
     */
    TUESDAY("Tuesday"),
    /**
     * Wednesday.
     */
    WEDNESDAY("Wednesday"),
    /**
     * Thursday.
     */
    THURSDAY("Thursday"),
    /**
     * Friday.
     */
    FRIDAY("Friday"),
    /**
     * Saturday.
     */
    SATURDAY("Saturday"),
    /**
     * Sunday.
     */
    SUNDAY("Sunday"),
    ;

    private final String value;

    Weekday(String value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the weekday if found else null
     */
    public static Weekday findByName(String name) {
        Weekday result = null;
        for (Weekday day : values()) {
            if (day.name().equalsIgnoreCase(name)) {
                result = day;
                break;
            }
        }
        return result;
    }

    /**
     * Find by value.
     *
     * @param value the value
     * @return the weekday if found else null
     */
    public static Weekday findByValue(String value) {
        Weekday result = null;
        for (Weekday day : values()) {
            if (day.getValue().equalsIgnoreCase(value)) {
                result = day;
                break;
            }
        }
        return result;
    }
}
