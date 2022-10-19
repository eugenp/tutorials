package com.baeldung.objectcopying

import java.util.List;
import java.util.stream.Collectors;

public class MapRDC extends Map {
    public MapRDC(double x, double y, List<Point> points) {
        super();
        this.x = x;
        this.y = y;
        this.points = points;
    }

    @Override
    public MapRDC clone() throws CloneNotSupportedException {
        MapRDC mapRDC = (MapRDC) super.clone();
        mapRDC.points = this.points.stream()
                .map(p -> {
                    try {
                        return p.clone();
                    } catch (CloneNotSupportedException cne) {
                        throw new RuntimeException(cne);
                    }
                })
                .collect(Collectors.toList());
        return mapRDC;
    }

}
