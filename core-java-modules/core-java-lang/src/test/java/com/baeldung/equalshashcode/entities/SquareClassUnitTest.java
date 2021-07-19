package com.baeldung.equalshashcode.entities;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class SquareClassUnitTest {

    @Test
    public void testEqualsAndHashcodes() {
        Square aObject = new Square(10, Color.BLUE);
        Square bObject = new Square(10, Color.BLUE);

        Square dObject = new Square(20, Color.BLUE);

        Assert.assertTrue(aObject.equals(bObject) && bObject.equals(aObject));

        Assert.assertTrue(aObject.hashCode() == bObject.hashCode());

        Assert.assertFalse(aObject.equals(dObject));
        Assert.assertFalse(aObject.hashCode() == dObject.hashCode());
    }

}
