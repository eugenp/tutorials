package com.baeldung.lazy;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class LazyJavaUnitTest {

    @Test
    public void giveHeavyClass_whenInitLazy_thenShouldReturnInstanceOnFirstCall() {
        //when
        ClassWithHeavyInitialization classWithHeavyInitialization = ClassWithHeavyInitialization.getInstance();
        ClassWithHeavyInitialization classWithHeavyInitialization2 = ClassWithHeavyInitialization.getInstance();

        //then
        assertTrue(classWithHeavyInitialization == classWithHeavyInitialization2);
    }
}
