package com.baeldung.javasisst;


public class ThreeDimensionalPoint {
    public int x = 0;
    public int y = 0;
    public int z = 0;

    public ThreeDimensionalPoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
