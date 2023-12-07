package com.baeldung.algorithms.latlondistance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GeoDistanceUnitTest {
    @Test
    public void testCalculateDistance() {
        double lat1 = 40.714268; // New York
        double lon1 = -74.005974;
        double lat2 = 34.0522; // Los Angeles
        double lon2 = -118.2437;

        double equirectangularDistance = EquirectangularApproximation.calculateDistance(lat1, lon1, lat2, lon2);
        double haversineDistance = HaversineDistance.calculateDistance(lat1, lon1, lat2, lon2);
        double vincentyDistance = VincentyDistance.calculateDistance(lat1, lon1, lat2, lon2);

        double expectedDistance = 3944;
        assertTrue(Math.abs(equirectangularDistance - expectedDistance) < 100);
        assertTrue(Math.abs(haversineDistance - expectedDistance) < 10);
        assertTrue(Math.abs(vincentyDistance - expectedDistance) < 0.5);

    }

}