package org.baeldung.equalshashcode.entities;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

public class SquareClassTest {

    @Test
    public void testEqualsAndHashcodes() {

        Square aObject = new Square(10, Color.BLUE);
        Square bObject = new Square(10, Color.BLUE);

        Square dObject = new Square(20, Color.BLUE);

        // equals()
        Assert.assertTrue(aObject.equals(bObject) && bObject.equals(aObject));
        // hashCode()
        Assert.assertTrue(aObject.hashCode() == bObject.hashCode());
        // non-equal objects are not equals() and have different hashCode()
        Assert.assertFalse(aObject.equals(dObject));
        Assert.assertFalse(aObject.hashCode() == dObject.hashCode());
    }

}
