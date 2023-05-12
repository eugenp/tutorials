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
        com.baeldung.deepshallowcopy.shallow.Box copiedBox = originalBox.clone();
        assertNotSame(originalBox, copiedBox);
        assertSame(originalBox.smallerBoxes, copiedBox.smallerBoxes);
        originalBox.smallerBoxes[0] = "ModifiedBox1";
        assertEquals("ModifiedBox1", copiedBox.smallerBoxes[0]);
    }

    @Test
    void givenBox_whenDeepCopy_thenDifferentInnerObject() throws CloneNotSupportedException {
        com.baeldung.deepshallowcopy.deep.Box originalBox = new com.baeldung.deepshallowcopy.deep.Box();
        originalBox.smallerBoxes = new String[] { "Box1", "Box2", "Box3" };
        com.baeldung.deepshallowcopy.deep.Box copiedBox = originalBox.clone();
        assertNotSame(originalBox, copiedBox);
        assertNotSame(originalBox.smallerBoxes, copiedBox.smallerBoxes);
        originalBox.smallerBoxes[0] = "ModifiedBox1";
        assertNotEquals("ModifiedBox1", copiedBox.smallerBoxes[0]);
    }
}
