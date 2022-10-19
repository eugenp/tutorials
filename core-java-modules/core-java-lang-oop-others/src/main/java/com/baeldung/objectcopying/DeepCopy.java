package com.baeldung.objectcopying;

import java.util.Arrays;

public class DeepCopy {

    public static void main(String [] args) throws CloneNotSupportedException {

        Point aPoint = new Point(5.0, 7.0);
        Point bPoint = aPoint.clone();
        aPoint.x = 11.0;
        aPoint.y = 61.0;
        System.out.println(String.format("x: %f, y: %f", bPoint.x, bPoint.y));
        System.out.println(String.format("aPoint: %s, bPoint: %s", aPoint, bPoint));

        Point [] points = {aPoint, bPoint};
        MapRDC map = new MapRDC(0.0, 10.0, Arrays.asList(points));
        MapRDC map2 = map.clone(); // We are clonning the list
        map.x = 100.0;
        map.y = 300.0;
        System.out.println(String.format("x: %f, y: %f", map2.x, map2.y));
        System.out.println(String.format("map: %s, map2: %s", map, map2));
        map2.points.add(new Point(113,321));
        System.out.println(String.format("map.points: %s, map2.points: %s", map.points, map2.points));
        map.points.get(0).x = 99.0;
        System.out.println(String.format("map2.point0.x: %f", map2.points.get(0).x));
        System.out.println(String.format("map.points0: %s, map2.points0: %s", map.points.get(0), map2.points.get(0)));

    }
}
