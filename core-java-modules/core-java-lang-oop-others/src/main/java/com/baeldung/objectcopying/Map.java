package com.baeldung.objectcopying;

import java.util.List;

public class Map implements Cloneable {
    int id;
    double x;
    double y;
    List<Point> points;

    public Map(double x, double y, List<Point> points) {
        super();
        this.x = x;
        this.y = y;
        this.points = points;
    }

    @Override
    public Map clone() throws CloneNotSupportedException {
        return (Map) super.clone();
    }

    public Map() {

    }
}
