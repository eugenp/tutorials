package com.baeldung.string;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ConcatTest {
    
    @Test
    public void whenCallConcat_thenCorrect() {
        assertEquals("elephant", "elep".concat("hant"));
    }
}
