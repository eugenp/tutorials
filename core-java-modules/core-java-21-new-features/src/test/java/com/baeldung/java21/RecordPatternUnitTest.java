package com.baeldung.java21;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.java21.RecordPattern.Color;
import com.baeldung.java21.RecordPattern.ColoredPoint;
import com.baeldung.java21.RecordPattern.Point;
import com.baeldung.java21.RecordPattern.RandomPoint;

public class RecordPatternUnitTest {

    @Test
    public void whenNoRecordPattern_thenReturnOutput() {
        Assert.assertEquals(5, RecordPattern.beforeRecordPattern(new Point(2, 3)));
    }
    
    @Test
    public void whenRecordPattern_thenReturnOutput() {
        Assert.assertEquals(5, RecordPattern.afterRecordPattern(new Point(2, 3)));
    }
    
    @Test
    public void whenRecordPattern_thenReturnColorOutput() {
        ColoredPoint coloredPoint = new ColoredPoint(new Point(2, 3), Color.GREEN);
        RandomPoint randomPoint = new RandomPoint(coloredPoint);
        Assert.assertEquals(Color.GREEN, RecordPattern.getRamdomPointColor(randomPoint));
    }
}
