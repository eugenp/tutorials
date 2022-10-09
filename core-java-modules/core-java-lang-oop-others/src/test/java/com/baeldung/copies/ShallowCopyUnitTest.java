package com.baeldung.copies;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class ShallowCopyUnitTest {

    private static final String JOHN_DOE = "John Doe";
    private static final String JANE_DOE = "Jane Doe";
    private static final int AGE_JOHN_DOE = 24;
    private static final int AGE_JANE_DOE = 21;

    private ShallowCopy originalObject;

    @BeforeEach
    void init() {
        final Person person = new Person(JOHN_DOE, AGE_JOHN_DOE);
        originalObject = new ShallowCopy(person);
    }

    @Test
    void GivenShallowCopy_WhenUnchanged_ThenEqual() {
        final ShallowCopy shallowCopy = originalObject.clone();

        assertClone(shallowCopy, JOHN_DOE, AGE_JOHN_DOE);
    }

    @Test
    void GivenShallowCopy_WhenChanged_ThenEqual() {
        final ShallowCopy shallowCopy = originalObject.clone();

        shallowCopy.getPerson()
          .setName(JANE_DOE);
        shallowCopy.getPerson()
          .setAge(AGE_JANE_DOE);

        assertClone(shallowCopy, JANE_DOE, AGE_JANE_DOE);
    }

    @Test
    void GivenShallowCopyFromCopyCtor_WhenUnchanged_ThenEqual() {
        final ShallowCopy shallowCopy = new ShallowCopy(originalObject);

        assertClone(shallowCopy, JOHN_DOE, AGE_JOHN_DOE);
    }

    @Test
    void GivenShallowCopyFromCopyCtor_WhenChanged_ThenEqual() {
        final ShallowCopy shallowCopy = new ShallowCopy(originalObject);

        shallowCopy.getPerson()
          .setName(JANE_DOE);
        shallowCopy.getPerson()
          .setAge(AGE_JANE_DOE);

        assertClone(shallowCopy, JANE_DOE, AGE_JANE_DOE);
    }

    private void assertClone(@Nonnull final ShallowCopy shallowCopy, @Nonnull final String name, final int age) {
        assertNotSame(shallowCopy, originalObject);
        assertSame(shallowCopy.getPerson(), originalObject.getPerson());
        assertSame(name, originalObject.getPerson()
          .getName());
        assertSame(name, shallowCopy.getPerson()
          .getName());
        assertSame(age, originalObject.getPerson()
          .getAge());
        assertSame(age, shallowCopy.getPerson()
          .getAge());
    }
}