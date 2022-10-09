package com.baeldung.copies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class DeepCopyUnitTest {

    private static final String JOHN_DOE = "John Doe";
    private static final String JANE_DOE = "Jane Doe";
    private static final int AGE_JOHN_DOE = 24;
    private static final int AGE_JANE_DOE = 21;

    private DeepCopy originalObject;

    @BeforeEach
    void init() {
        final Person person = new Person(JOHN_DOE, AGE_JOHN_DOE);
        originalObject = new DeepCopy(person);
    }

    @Test
    void GivenDeepCopy_WhenUnchanged_ThenEqual() {
        final DeepCopy deepCopy = originalObject.clone();

        assertClone(deepCopy);
    }

    @Test
    void GivenDeepCopy_WhenChanged_ThenDifferent() {
        final DeepCopy deepCopy = originalObject.clone();

        deepCopy.getPerson()
          .setName(JANE_DOE);
        deepCopy.getPerson()
          .setAge(AGE_JANE_DOE);

        assertCloneChanged(deepCopy);
    }

    @Test
    void GivenDeepCopyFromCopyCtor_WhenUnchanged_ThenEqual() {
        final DeepCopy deepCopy = new DeepCopy(originalObject);

        assertClone(deepCopy);
    }

    @Test
    void GivenDeepCopyFromCopyCtor_WhenChanged_ThenDifferent() {
        final DeepCopy deepCopy = new DeepCopy(originalObject);

        deepCopy.getPerson()
          .setName(JANE_DOE);
        deepCopy.getPerson()
          .setAge(AGE_JANE_DOE);

        assertCloneChanged(deepCopy);
    }

    private void assertClone(@Nonnull final DeepCopy deepCopy) {
        assertNotSame(deepCopy, originalObject);
        assertNotSame(deepCopy.getPerson(), originalObject.getPerson());
        assertEquals(deepCopy.getPerson(), originalObject.getPerson());
        assertSame(JOHN_DOE, originalObject.getPerson()
          .getName());
        assertSame(JOHN_DOE, deepCopy.getPerson()
          .getName());
        assertSame(AGE_JOHN_DOE, originalObject.getPerson()
          .getAge());
        assertSame(AGE_JOHN_DOE, deepCopy.getPerson()
          .getAge());
    }

    private void assertCloneChanged(@Nonnull final DeepCopy deepCopy) {
        assertNotSame(deepCopy, originalObject);
        assertNotSame(deepCopy.getPerson(), originalObject.getPerson());
        assertNotEquals(deepCopy.getPerson(), originalObject.getPerson());
        assertNotEquals(deepCopy.getPerson()
          .getName(), originalObject.getPerson()
          .getName());
        assertNotEquals(deepCopy.getPerson()
          .getAge(), originalObject.getPerson()
          .getAge());
    }
}