package com.baeldung.clonable;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CopyStrategiesUnitTest {

    @Test
    void testDeepCopy() throws CloneNotSupportedException {
        Human originalHuman = new Human("Bob", List.of("cooking", "swimming"));
        Human deepCopyHuman = (Human) originalHuman.clone();

        assertNotSame(originalHuman, deepCopyHuman); // Objects are different
        assertEquals(originalHuman.getName(), deepCopyHuman.getName()); // Name is the same
        assertNotSame(originalHuman.getHobbies(), deepCopyHuman.getHobbies()); // Hobbies list is different

        originalHuman.setName("Alice");
        assertNotEquals(originalHuman.getName(), deepCopyHuman.getName()); // new object is independent from the source one
    }

    @Test
    void testShallowCopy() throws CloneNotSupportedException {
        Human owner = new Human("Charlie", List.of("cooking", "swimming"));
        Pet originalPet = new Pet("Fluffy", owner);

        Pet shallowCopyPet = (Pet) originalPet.clone();

        assertNotSame(originalPet, shallowCopyPet); // Objects are different
        assertEquals(originalPet.getName(), shallowCopyPet.getName()); // Name is the same
        assertSame(originalPet.getOwner(), shallowCopyPet.getOwner()); // Owner is the same

        // shallow copying copy only links so if the original object would change
        // we it will affect the copy
        owner.setName("Hue");
        assertEquals(originalPet.getOwner().getName(), shallowCopyPet.getOwner().getName());

    }


}
