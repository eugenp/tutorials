package com.baeldung.algorithms.distancebetweenpoints;

import java.awt.geom.Point2D;

public class DistanceBetweenPointsService {

    public double calculateDistanceBetweenPoints(
        double x1, 
        double y1, 
        double x2, 
        double y2) {
        
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public double calculateDistanceBetweenPointsWithHypot(
        double x1, 
        double y1, 
        double x2, 
        double y2) {
        
        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2 - x1);
        
        return Math.hypot(ac, cb);
    }

    public double calculateDistanceBetweenPointsWithPoint2D( 
        double x1, 
        double y1, 
        double x2, 
        double y2) {
        
        return Point2D.distance(x1, y1, x2, y2);

    }

}
