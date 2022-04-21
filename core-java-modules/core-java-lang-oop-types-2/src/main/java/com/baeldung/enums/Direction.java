package com.baeldung.enums;

import java.util.Random;

/**
 * Represents directions.
 */
public enum Direction {
    EAST, WEST, SOUTH, NORTH;

    private static final Random PRNG = new Random();

    /**
     * Generate a random direction.
     *
     * @return a random direction
     */
    public static Direction randomDirection() {
        Direction[] directions = values();
        return directions[PRNG.nextInt(directions.length)];
    }

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
