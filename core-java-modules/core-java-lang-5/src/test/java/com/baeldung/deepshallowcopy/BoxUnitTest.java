package com.baeldung.deepshallowcopy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.jupiter.api.Test;

public class BoxUnitTest {
    @Test
    void givenBox_whenShallowCopy_thenSameInnerObject() throws CloneNotSupportedException {
        com.baeldung.deepshallowcopy.shallow.Box originalBox = new com.baeldung.deepshallowcopy.shallow.Box();
        originalBox.smallerBoxes = new String[] { "Box1", "Box2", "Box3" };

        com.baeldung.deepshallowcopy.shallow.Box copiedBox = originalBox.clone(); // shallow copy

        // Assert that the two objects are different
        assertNotSame(originalBox, copiedBox);

        // Assert that the smallerBoxes arrays are the same
        assertSame(originalBox.smallerBoxes, copiedBox.smallerBoxes);

        // Modify the original object and assert that the copied object is also affected
        originalBox.smallerBoxes[0] = "ModifiedBox1";
        assertEquals("ModifiedBox1", copiedBox.smallerBoxes[0]);
    }

    @Test
    void givenBox_whenDeepCopy_thenDifferentInnerObject() throws CloneNotSupportedException {
        com.baeldung.deepshallowcopy.deep.Box originalBox = new com.baeldung.deepshallowcopy.deep.Box();
        originalBox.smallerBoxes = new String[] { "Box1", "Box2", "Box3" };

        com.baeldung.deepshallowcopy.deep.Box copiedBox = originalBox.clone(); // deep copy

        // Assert that the two objects are different
        assertNotSame(originalBox, copiedBox);

        // Assert that the smallerBoxes arrays are different
        assertNotSame(originalBox.smallerBoxes, copiedBox.smallerBoxes);

        // Modify the original object and assert that the copied object is not affected
        originalBox.smallerBoxes[0] = "ModifiedBox1";
        assertNotEquals("ModifiedBox1", copiedBox.smallerBoxes[0]);
    }
}
