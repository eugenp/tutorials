package com.baeldung.junit4;

import com.baeldung.junit4.categories.Annotations;
import com.baeldung.junit4.categories.JUnit4UnitTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(value = { Annotations.class, JUnit4UnitTest.class })
public class AnnotationTestExampleUnitTest {
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
