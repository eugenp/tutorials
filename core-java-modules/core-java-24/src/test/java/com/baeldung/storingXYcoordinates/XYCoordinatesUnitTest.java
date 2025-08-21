package com.baeldung.storingXYcoordinates;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class XYCoordinatesUnitTest {

    @Test
    void givenAPoint_whenUsingGetter_thenPointReturnsCoordinatesCorrectly() {
        // Given a point with coordinates (10, 20)
        Point point = new Point(10, 20);

        // Then its getters should return the correct values
        assertEquals(10, point.getX(), "X coordinate should be 10");
        assertEquals(20, point.getY(), "Y coordinate should be 20");
    }
  
    @Test
    void givenTwoPointsWithSameCoordinates_whenComparedForEquality_thenShouldBeEqual() {
        Point point1 = new CustomPoint(5, -3);
        Point point2 = new CustomPoint(5, -3);
        
        assertEquals(point1, point2, "Points with same coordinates should be equal");
    }

    @Test
    void givenAPointRecord_whenUsingAccessorMethods_thenRecordReturnsCoordinatesCorrectly() {
        // Given a record with coordinates (30, 40)
        PointRecord point = new PointRecord(30, 40);

        // Then its accessor methods should return the correct values
        assertEquals(30, point.x(), "X coordinate should be 30");
        assertEquals(40, point.y(), "Y coordinate should be 40");
    }

    @Test
    void givenTwoRecordsWithSameCoordinates_whenComparedForEquality_thenShouldBeEqual() {
        PointRecord point1 = new PointRecord(7, 8);
        PointRecord point2 = new PointRecord(7, 8);

        assertEquals(point1, point2, "Records with same coordinates should be equal");
    }
    
    @Test
    void givenAnAWTPoint_whenAccessingItsFieldsAndGetters_thenReturnsCoordinatesCorrectly() {
        // Given an AWT Point
        java.awt.Point point = new java.awt.Point(50, 60);

        // Then its public fields should hold the correct values
        assertEquals(50, point.x);
        assertEquals(60, point.y);

        // And its getters should also work
        assertEquals(50.0, point.getX());
        assertEquals(60.0, point.getY());
    }

    @Test
    void givenArrayOfCoordinates_whenAccessingArrayIndices_thenReturnsCoordinatesAtCorrectIndices() {
        // Given an array representing coordinates (15, 25)
        int[] coordinates = new int[2];
        coordinates[0] = 15; // X
        coordinates[1] = 25; // Y

        // Then index 0 should be X and index 1 should be Y
        assertEquals(15, coordinates[0], "Index 0 should be the X coordinate");
        assertEquals(25, coordinates[1], "Index 1 should be the Y coordinate");
    }
}
