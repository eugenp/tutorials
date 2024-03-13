package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.baeldung.java21.RecordPattern.Color;
import com.baeldung.java21.RecordPattern.ColoredPoint;
import com.baeldung.java21.RecordPattern.Point;
import com.baeldung.java21.RecordPattern.RandomPoint;

public class RecordPatternUnitTest {

    @Test
    public void whenNoRecordPattern_thenReturnOutput() {
        assertEquals(5, RecordPattern.beforeRecordPattern(new Point(2, 3)));
    }
    
    @Test
    public void whenRecordPattern_thenReturnOutput() {
        assertEquals(5, RecordPattern.afterRecordPattern(new Point(2, 3)));
    }
    
    @Test
    public void whenRecordPattern_thenReturnColorOutput() {
        ColoredPoint coloredPoint = new ColoredPoint(new Point(2, 3), Color.GREEN);
        RandomPoint randomPoint = new RandomPoint(coloredPoint);
        assertEquals(Color.GREEN, RecordPattern.getRamdomPointColor(randomPoint));
    }
}
