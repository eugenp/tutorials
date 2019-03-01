package com.baeldung.finalkeyword;

import org.junit.Test;
import static org.junit.Assert.*;

public class FinalUnitTest {

    @Test
    public void whenChangedFinalClassProperties_thenChanged() {
        Cat cat = new Cat();
        cat.setWeight(1);

        assertEquals(1, cat.getWeight());

    }

    @Test
    public void whenFinalVariableAssign_thenOnlyOnce() {
        final int i;
        i = 1;
        // i=2;
    }

    @Test
    public void whenChangedFinalReference_thenChanged() {

        final Cat cat = new Cat();
        // cat=new Cat();
        cat.setWeight(5);

        assertEquals(5, cat.getWeight());
    }
}
