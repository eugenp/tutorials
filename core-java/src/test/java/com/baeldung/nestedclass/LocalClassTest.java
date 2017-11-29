package com.baeldung.nestedclass;

import org.junit.Test;

public class LocalClassTest {

    @Test
    public void whenTestingLocalClass_thenCorrect() {
        NewEnclosing newEnclosing = new NewEnclosing();
        newEnclosing.run();
    }
}
