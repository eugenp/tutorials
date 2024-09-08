package com.baeldung.copy.shallow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class ShallowCopyUnitTest {

    @Test
    void whenCopyConstructor_thenNewObjectCreated() {

        // create the original to be cloned
        Founder founder = new Founder("Eugen", 2011);
        Company original = new Company("Baeldung", founder);

        // create copy
        Company copy = new Company(original);

        // verify they are not the same object
        assertNotSame(original, copy);
        // verify modification to copy impacts original
        copy.getFounder()
            .setName("Dan");
        assertEquals("Dan", original.getFounder()
            .getName());
    }

    @Test
    void whenCloneCopy_thenNewObjectCreated() {

        // create the original to be cloned
        Founder founder = new Founder("Eugen", 2011);
        Company original = new Company("Baeldung", founder);

        // create clone
        Company clone = original.clone();

        // verify they are not the same object
        assertNotSame(original, clone);
        // verify modification to clone impacts original
        clone.getFounder()
            .setName("Dan");
        assertEquals("Dan", original.getFounder()
            .getName());

    }
}
