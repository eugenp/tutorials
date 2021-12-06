package com.baeldung.enumerations;

/**
 * Represents weekdays.
 */
public enum Weekdays {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday"),
    ;

    private final String value;

    Weekdays(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

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
