package com.baeldung.migration.junit4;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.baeldung.migration.junit4.categories.Annotations;
import com.baeldung.migration.junit4.categories.JUnit4Tests;

@Category(value = { Annotations.class, JUnit4Tests.class })
public class AnnotationTestExampleTest {
    @Test(expected = Exception.class)
    public void shouldRaiseAnException() throws Exception {
        throw new Exception("This is my expected exception");
    }

    @Test(timeout = 1)
    @Ignore
    public void shouldFailBecauseTimeout() throws InterruptedException {
        Thread.sleep(10);
    }

}
