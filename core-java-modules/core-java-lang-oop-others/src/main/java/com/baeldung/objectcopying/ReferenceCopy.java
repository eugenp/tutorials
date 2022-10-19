package com.baeldung.objectcopying;

public class ReferenceCopy {

    public static void main(String [] args) {

        Point aPoint = new Point(5.0, 7.0);
        Point bPoint = aPoint;
        System.out.println(String.format("x: %f, y: %f", bPoint.x, bPoint.y));
        System.out.println(String.format("aPoint: %s, bPoint: %s", aPoint, bPoint));
        aPoint.x = 17;
        System.out.println(bPoint.x);
        Point cPoint = new Point(aPoint.x, aPoint.y);
        System.out.println(String.format("aPoint: %s, cPoint: %s", aPoint, cPoint));
        aPoint.x = 90;
        System.out.println(cPoint.x);
    }
}
