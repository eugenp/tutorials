package com.baeldung.objectcopying;

public class Point  implements Cloneable {
    double x;
    double y;
    public Point(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public Point clone() throws CloneNotSupportedException {
        return (Point) super.clone();
    }
}
