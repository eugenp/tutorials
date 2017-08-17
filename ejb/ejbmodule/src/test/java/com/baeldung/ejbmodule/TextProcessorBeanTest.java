package com.baeldung.ejbmodule;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TextProcessorBeanTest {
    @Test
    public void givenInputString_whenComparedToStringParsedByBean_thenSuccessful() {
        TextProcessorBean textProcessor = new TextProcessorBean();
        assertEquals("TEST", textProcessor.processText("test"));
    }
}