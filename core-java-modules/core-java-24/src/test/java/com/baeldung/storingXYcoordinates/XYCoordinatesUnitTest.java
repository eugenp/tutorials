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
 

}
