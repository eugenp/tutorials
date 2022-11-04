package com.baeldung.shallowdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void whenObjectIsShallowCopied_andCopyIsModified_thenOriginalIsAlsoModified() {

        Article original = new Article("Java Shallow Copy vs Deep Copy Explained", new Tag("java"));
        Article copy = new Article(original.getTitle(), original.getTag());

        copy.getTag().setName("python");

        assertEquals("python", original.getTag().getName());
    }
}
