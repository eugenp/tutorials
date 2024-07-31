package com.baeldung.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents months in a year.
 */
public enum Month {
    /**
     * January.
     */
    JANUARY("January", 1),
    /**
     * February.
     */
    FEBRUARY("February", 2),
    /**
     * March.
     */
    MARCH("March", 3),
    /**
     * April.
     */
    APRIL("April", 4),
    /**
     * May.
     */
    MAY("May", 5),
    /**
     * June.
     */
    JUNE("June", 6),
    /**
     * July.
     */
    JULY("July", 7),
    /**
     * August.
     */
    AUGUST("August", 8),
    /**
     * September.
     */
    SEPTEMBER("September", 9),
    /**
     * October.
     */
    OCTOBER("October", 10),
    /**
     * November.
     */
    NOVEMBER("November", 11),
    /**
     * December.
     */
    DECEMBER("December", 12),
    ;

    private final String value;
    private final int code;

    Month(String value, int code) {
        this.value = value;
        this.code = code;
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
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the month if found else <code>Optional.empty()</code>
     */
    public static Optional<Month> findByName(String name) {
        return Arrays.stream(values()).filter(month -> month.name().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Find by code.
     *
     * @param code the code
     * @return the month if found else <code>Optional.empty()</code>
     */
    public static Optional<Month> findByCode(int code) {
        return Arrays.stream(values()).filter(month -> month.getCode() == code).findFirst();
    }

    /**
     * Finds month by value.
     *
     * @param value value
     * @return month if value is valid
     * @throws IllegalArgumentException if month not found for given value
     */
    public static Month findByValue(String value) {
        return Arrays.stream(values()).filter(month -> month.getValue().equalsIgnoreCase(value)).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
