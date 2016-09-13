package org.baeldung.equalshashcode.entities;

import org.junit.Assert;
import org.junit.Test;

public class PrimitiveClassTest {

    @Test
    public void testTwoEqualsObjects() {

        PrimitiveClass aObject = new PrimitiveClass(false, 2);
        PrimitiveClass bObject = new PrimitiveClass(false, 2);
        PrimitiveClass dObject = new PrimitiveClass(true, 2);

<<<<<<< HEAD
        Assert.assertTrue(aObject.equals(bObject) && bObject.equals(aObject));

        Assert.assertTrue(aObject.hashCode() == bObject.hashCode());

=======
        // equals()
        Assert.assertTrue(aObject.equals(bObject) && bObject.equals(aObject));
        // hashCode()
        Assert.assertTrue(aObject.hashCode() == bObject.hashCode());
        // non-equal objects are not equals() and have different hashCode()
>>>>>>> 25a939665971866fadd0b1b78e09dd732d736193
        Assert.assertFalse(aObject.equals(dObject));
        Assert.assertFalse(aObject.hashCode() == dObject.hashCode());
    }

}
