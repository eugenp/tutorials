package com.baeldung.jts.operations;

import org.locationtech.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JTSOperationUtils {
    private static final Logger log = LoggerFactory.getLogger(JTSOperationUtils.class);

    public static boolean checkContainment(Geometry point, Geometry polygon) {
        boolean isInside = polygon.contains(point);
        log.info("Is the point inside polygon? {}", isInside);
        return isInside;
    }

    public static boolean checkIntersect(Geometry rectangle1, Geometry rectangle2) {
        boolean intersect = rectangle1.intersects(rectangle2);
        Geometry overlap = rectangle1.intersection(rectangle2);

        log.info("Do both rectangle intersect? {}", intersect);
        log.info("Overlapping Area: {}", overlap);
        return intersect;
    }

    public static Geometry getBuffer(Geometry point, int intBuffer) {
        Geometry buffer = point.buffer(intBuffer);
        log.info("Buffer Geometry: {}", buffer);
        return buffer;
    }

    public static double getDistance(Geometry point1, Geometry point2) {
        double distance = point1.distance(point2);
        log.info("Distance: {}",distance);
        return distance;
    }

    public static Geometry getUnion(Geometry geometry1, Geometry geometry2) {
        Geometry union = geometry1.union(geometry2);
        log.info("Union Result: {}", union);
        return union;
    }

    public static Geometry getDifference(Geometry base, Geometry cut) {
        Geometry result = base.difference(cut);
        log.info("Resulting Geometry: {}", result);
        return result;
    }

    public static Geometry validateAndRepair(Geometry invalidGeo) throws Exception {
        boolean valid = invalidGeo.isValid();
        log.info("Is valid Geometry value? {}", valid);

        Geometry repaired = invalidGeo.buffer(0);
        log.info("Repaired Geometry: {}", repaired);
        return repaired;
    }
}
