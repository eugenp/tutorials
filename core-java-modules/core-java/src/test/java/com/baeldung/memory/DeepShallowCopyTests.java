package com.baeldung.memory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DeepShallowCopyTests {

    @Test
    public void givenShirt_whenShallowCopy_thenObjectReferenceRemainsSame() {
        Cloth cottonCloth = new Cloth("Cotton", "Blue");
        Shirt shirt = new Shirt(10, 10, 40, 2, cottonCloth);

        System.out.println("Before Shallow Copy - Original " + shirt);

        Shirt shallowCopyShirt = shirt.shallowCopy();

        cottonCloth.setColor("Red");

        System.out.println("After Shallow Copy - Original " + shirt);
        System.out.println("Shallow Copied " + shallowCopyShirt);
        assertEquals(shirt.getCloth(), shallowCopyShirt.getCloth());
    }

    @Test
    public void givenShirt_whenDeepCopy_thenObjectReferenceChanges() {
        Cloth cottonCloth = new Cloth("Cotton", "Blue");
        Shirt shirt = new Shirt(10, 10, 40, 2, cottonCloth);

        System.out.println("Before Deep Copy - Original " + shirt);

        Shirt deepCopyShirt = shirt.deepCopy();

        cottonCloth.setColor("Red");

        System.out.println("After Deep Copy - Original " + shirt);
        System.out.println("Deep Copied " + deepCopyShirt);
        assertNotEquals(shirt.getCloth(), deepCopyShirt.getCloth());
    }
}
