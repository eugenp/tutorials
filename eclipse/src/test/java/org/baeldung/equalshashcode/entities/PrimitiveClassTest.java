package org.baeldung.equalshashcode.entities;

import org.junit.Assert;
import org.junit.Test;

public class PrimitiveClassTest {

    @Test
    public void testTwoEqualsObjects() {

        PrimitiveClass aObject = new PrimitiveClass(false, 2);
        PrimitiveClass bObject = new PrimitiveClass(false, 2);
        PrimitiveClass dObject = new PrimitiveClass(true, 2);

        // equals()
        Assert.assertTrue(aObject.equals(bObject) && bObject.equals(aObject));
        // hashCode()
        Assert.assertTrue(aObject.hashCode() == bObject.hashCode());
        // non-equal objects are not equals() and have different hashCode()
        Assert.assertFalse(aObject.equals(dObject));
        Assert.assertFalse(aObject.hashCode() == dObject.hashCode());
    }

}
