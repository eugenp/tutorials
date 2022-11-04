package com.baeldung.shallowdeepcopy;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenObjectIsDeepCopied_andCopyIsModified_thenOriginalShouldNotModify() {

        Article original = new Article("Java Shallow Copy vs Deep Copy Explained", new Tag("java"));
        Article deepCopy = new Article(original.getTitle(), new Tag(original.getTag().getName()));

        deepCopy.getTag().setName("python");

        assertNotEquals("python", original.getTag().getName());
    }
}
