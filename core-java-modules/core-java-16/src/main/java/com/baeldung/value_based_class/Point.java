package com.baeldung.value_based_class;

import java.util.Objects;

import jdk.internal.ValueBased;

/**
 * This class is written with the intention that it can serve as an example of
 * what a Value-based class could be.
 */

@ValueBased
public final class Point {
    private final int x;
    private final int y;
    private final int z;

    private static Point ORIGIN = new Point(0, 0, 0);

    private Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Point valueOfPoint(int x, int y, int z) {
        // returns a cached instance if it is origin, or a new instance
        if (isOrigin(x, y, z))
            return ORIGIN;
        return new Point(x, y, z);
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass())
            return false;
        Point point = (Point) other;
        return x == point.x && y == point.y && z == point.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    private static boolean isOrigin(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0;
    }
}
