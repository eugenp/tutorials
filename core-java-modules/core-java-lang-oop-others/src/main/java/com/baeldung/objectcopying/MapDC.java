package com.baeldung.objectcopying;

import java.util.ArrayList;
import java.util.List;

public class MapDC extends Map {

    public MapDC(double x, double y, List<Point> points) {
        super(x, y, points);
    }

    public MapDC(MapDC mapDC) throws CloneNotSupportedException {
        this(mapDC.x, mapDC.y, null);
        this.points = (List<Point>) mapDC.clone();
    }

    @Override
    public MapDC clone() throws CloneNotSupportedException {
        MapDC mapDC = (MapDC) super.clone();
        mapDC.points = new ArrayList<Point>(this.points);
        return mapDC;
    }
}
