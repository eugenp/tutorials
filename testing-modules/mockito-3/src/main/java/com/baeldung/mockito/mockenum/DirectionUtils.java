package com.baeldung.mockito.mockenum;

public class DirectionUtils {

    private DirectionUtils() {
    }

    public static String getDescription(Direction direction) {
        return switch (direction) {
            case NORTH -> "You're headed north.";
            case EAST -> "You're headed east.";
            case SOUTH -> "You're headed south.";
            case WEST -> "You're headed west.";
            default -> throw new IllegalArgumentException();
        };
    }
}
