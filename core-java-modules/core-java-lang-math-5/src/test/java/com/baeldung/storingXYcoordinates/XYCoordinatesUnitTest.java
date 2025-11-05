package com.baeldung.storingXYcoordinates;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.geom.Point2D;

class XYCoordinatesUnitTest {

    @Test
    void givenAPoint_whenUsingGetter_thenPointReturnsCoordinatesCorrectly() {
        // Given a point with coordinates (10.0, 20.5)
        Point point = new Point(10.0, 20.5);

        // Then its getters should return the correct values
        assertEquals(10.0, point.getX(), "X coordinate should be 10.0");
        assertEquals(20.5, point.getY(), "Y coordinate should be 20.5");
    }
  
    @Test
    void givenTwoPointsWithSameCoordinates_whenComparedForEquality_thenShouldBeEqual() {
        Point point1 = new Point(5.1, -3.5);
        Point point2 = new Point(5.1, -3.5);
        
        assertEquals(point1, point2, "Points with same coordinates should be equal");
    }

    @Test
    void givenAPointRecord_whenUsingAccessorMethods_thenRecordReturnsCoordinatesCorrectly() {
        // Given a record with coordinates (30.5, 40.0)
        PointRecord point = new PointRecord(30.5, 40.0);

        // Then its accessor methods should return the correct values
        assertEquals(30.5, point.x(), "X coordinate should be 30.5");
        assertEquals(40.0, point.y(), "Y coordinate should be 40.0");
    }

    @Test
    void givenTwoRecordsWithSameCoordinates_whenComparedForEquality_thenShouldBeEqual() {
        PointRecord point1 = new PointRecord(7.0, 8.5);
        PointRecord point2 = new PointRecord(7.0, 8.5);

        assertEquals(point1, point2, "Records with same coordinates should be equal");
    }
    
    @Test
    void givenAnAWTPoint_whenAccessingItsFieldsAndGetters_thenReturnsCoordinatesCorrectly() {
        // Given an AWT Point
        Point2D.Double point = new Point2D.Double(10.5, 20.5);

        // Then its public fields should hold the correct values
        assertEquals(10.5, point.x);
        assertEquals(20.5, point.y);

        // And its getters should also work
        assertEquals(10.5, point.getX());
        assertEquals(20.5, point.getY());
    }

    @Test
    void givenAnAWTPointForIntegerCoordinates_whenAccessingItsFieldsAndGetters_thenReturnsCoordinatesCorrectly() {
        // Given an AWT Point
        java.awt.Point point = new java.awt.Point(50, 60);

        // Then its public fields should hold the correct values
        assertEquals(50, point.x);
        assertEquals(60, point.y);

        // And its getters should also work
        assertEquals(50, point.getX());
        assertEquals(60, point.getY());
    }
    
    @Test
    void givenArrayOfCoordinates_whenAccessingArrayIndices_thenReturnsCoordinatesAtCorrectIndices() {
        // Given an array representing coordinates (15.0, 25.0)
        double[] coordinates = new double[2];
        coordinates[0] = 15.0; // X
        coordinates[1] = 25.0; // Y

        // Then index 0 should be X and index 1 should be Y
        assertEquals(15.0, coordinates[0], "Index 0 should be the X coordinate");
        assertEquals(25.0, coordinates[1], "Index 1 should be the Y coordinate");
    }
}
