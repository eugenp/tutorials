package com.baeldung.enums.randomenum;

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
}
