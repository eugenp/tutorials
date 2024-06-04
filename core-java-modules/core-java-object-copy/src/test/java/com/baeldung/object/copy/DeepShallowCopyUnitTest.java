package com.baeldung.object.copy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeepShallowCopyUnitTest {

    @Test
    public void testShallowCopy() {
        // Setup parent
        Parent originalDad = new Parent("homer");
        Child child1 = new Child("bart");
        Child child2 = new Child("lisa");

        originalDad.child1 = child1;
        originalDad.child2 = child2;

        // Copy parent shallow
        Parent newDad = new Parent(originalDad, false);

        // Check basic assumptions
        assertEquals("homer", newDad.name);
        assertEquals("bart", newDad.child1.name);
        assertEquals("lisa", newDad.child2.name);

        // Check mutating child
        child1.name = "peter";

        // Assert name changed from point of view of newDad
        assertEquals("peter", newDad.child1.name);
    }

    @Test
    public void testDeepCopy() {
        // Setup parent
        Parent originalDad = new Parent("homer");
        Child child1 = new Child("bart");
        Child child2 = new Child("lisa");

        originalDad.child1 = child1;
        originalDad.child2 = child2;

        // Copy parent deep
        Parent newDad = new Parent(originalDad, true);

        // Check basic assumptions
        assertEquals("homer", newDad.name);
        assertEquals("bart", newDad.child1.name);
        assertEquals("lisa", newDad.child2.name);

        // Check mutating child
        child1.name = "peter";

        // Assert name did not change from point of view of newDad
        assertEquals("bart", newDad.child1.name);
    }

}
