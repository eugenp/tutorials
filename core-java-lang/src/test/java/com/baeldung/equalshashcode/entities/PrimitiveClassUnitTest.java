package com.baeldung.equalshashcode.entities;

import org.junit.Assert;
import org.junit.Test;

public class PrimitiveClassUnitTest {

    @Test
    public void testTwoEqualsObjects() {

        PrimitiveClass aObject = new PrimitiveClass(false, 2);
        PrimitiveClass bObject = new PrimitiveClass(false, 2);
        PrimitiveClass dObject = new PrimitiveClass(true, 2);

        Assert.assertTrue(aObject.equals(bObject) && bObject.equals(aObject));

        Assert.assertTrue(aObject.hashCode() == bObject.hashCode());

        Assert.assertFalse(aObject.equals(dObject));
        Assert.assertFalse(aObject.hashCode() == dObject.hashCode());
    }

}
