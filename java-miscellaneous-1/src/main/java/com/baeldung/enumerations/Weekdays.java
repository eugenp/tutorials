package com.baeldung.enumerations;

/**
 * Represents.
 */
public enum Weekdays {
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

    Weekdays(String value) {
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
    public static Weekdays findByName(String name) {
        Weekdays result = null;
        for (Weekdays day : values()) {
            if (day.name().equals(name)) {
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
    public static Weekdays findByValue(String value) {
        Weekdays result = null;
        for (Weekdays day : values()) {
            if (day.getValue().equals(value)) {
                result = day;
                break;
            }
        }
        return result;
    }
}
