package com.baeldung.shallowcopyvsdeepcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShallowCopyUnitTest {

    @Test
    void whenCreatingShallowCopy_thenObjectsShouldNotBeSame() {
        Author author = new Author(1, "Baeldung");
        Course course = new Course(1, "Spring Masterclass", author);
        Course shallowCopy = new Course(course);

        Assertions.assertNotSame(course, shallowCopy);
    }

    @Test
    void whenModifyingShallowCopy_thenOriginalObjectShouldChange() {
        Author author = new Author(1, "Baeldung");
        Course course = new Course(1, "Spring Masterclass", author);
        Course shallowCopy = new Course(course);

        shallowCopy.getAuthor()
            .setAuthorName("Eugen");

        Assertions.assertEquals(shallowCopy.getAuthor()
            .getAuthorName(), course.getAuthor()
            .getAuthorName());
    }
}
