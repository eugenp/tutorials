package com.baeldung.javasisst;


class ThreeDimensionalPoint {
    private int x = 0;
    private int y = 0;
    private int z = 0;

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
