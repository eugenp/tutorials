package com.baeldung.shallowdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShallowDeepCopyUnitTest {

    @Test
    public void givenAnObjectAndADependency_whenShallowClonedAndDepencyChanges_ThenDepencyHasNewValue() throws Exception {
        Dependency dependency = new Dependency(13);
        Main main = new Main(dependency);
        Main shallowClone = (Main) main.clone();
        dependency.setValue(17);
        assertEquals(17, shallowClone.getDependency()
            .getValue());
    }

    @Test
    public void givenAnObjectAndADependency_whenDeepClonedAndDepencyChanges_ThenDepencyKeepsOldValue() throws Exception {
        Dependency dependency = new Dependency(13);
        Main main = new Main(dependency);
        Main deepClone = (Main) main.deepClone();
        dependency.setValue(17);
        assertEquals(13, deepClone.getDependency()
            .getValue());
    }
}
