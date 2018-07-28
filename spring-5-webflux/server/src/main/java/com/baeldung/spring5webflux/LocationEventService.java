package com.baeldung.spring5webflux;

import java.util.Date;
import java.util.Random;

public class LocationEventService {
    private Random random = new Random(System.currentTimeMillis());
    private GeoPoint previous = new GeoPoint(0.0, 0.0);

    public LocationEvent produceEvent() {
        double newLat = previous.getLatitude() + (random.nextDouble() - 0.5);
        if (newLat > 180.0) {
            newLat -= 360.0;
        } else if (newLat < -180.0) {
            newLat += 360;
        }
        double newLong = previous.getLongitude() + (random.nextDouble() - 0.5);
        if (newLong > 180.0) {
            newLong -= 360.0;
        } else if (newLong < -180.0) {
            newLong += 360.0;
        }
        GeoPoint newGeoPoint = new GeoPoint(newLat, newLong);
        previous = newGeoPoint;
        return new LocationEvent(newGeoPoint, new Date());
    }
}
