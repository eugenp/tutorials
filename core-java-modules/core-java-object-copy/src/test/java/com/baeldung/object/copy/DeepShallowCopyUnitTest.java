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
        Parent newDad = new Parent("john");
        newDad.name = originalDad.name;
        newDad.child1 = originalDad.child1;
        newDad.child2 = originalDad.child2;

        // Check basic assumptions
        assertEquals(newDad.name, "homer");
        assertEquals(newDad.child1.name, "bart");
        assertEquals(newDad.child2.name, "lisa");

        // Check mutating child
        child1.name = "peter";

        // Assert name changed from point of view of newDad
        assertEquals(newDad.child1.name, "peter");
    }

    @Test
    public void testDeepCopy() {
        // Setup parent
        Parent originalDad = new Parent("homer");
        Child child1 = new Child("bart");
        Child child2 = new Child("lisa");

        originalDad.child1 = child1;
        originalDad.child2 = child2;

        // Copy parent shallow
        Parent newDad = new Parent("john");
        newDad.name = originalDad.name;
        newDad.child1 = new Child(originalDad.child1.name);
        newDad.child2 = new Child(originalDad.child2.name);

        // Check basic assumptions
        assertEquals(newDad.name, "homer");
        assertEquals(newDad.child1.name, "bart");
        assertEquals(newDad.child2.name, "lisa");

        // Check mutating child
        child1.name = "peter";

        // Assert name did not change from point of view of newDad
        assertEquals(newDad.child1.name, "bart");
    }

}
