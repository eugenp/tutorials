package com.baeldung.enums;

/**
 * Represents directions.
 */
public enum Direction {
    EAST, WEST, SOUTH, NORTH;

    /**
     * Finds direction by name.
     *
     * @param name the name
     * @return the direction if found else null
     */
    public static Direction findByName(String name) {
        Direction result = null;
        for (Direction direction : values()) {
            if (direction.name().equalsIgnoreCase(name)) {
                result = direction;
                break;
            }
        }
        return result;
    }

}
