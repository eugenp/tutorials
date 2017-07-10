package com.baeldung.services;

import static org.junit.Assert.*;
import org.junit.Test;

public class UppercaseTextServiceTest {
    @Test
    public void processText_LowercasedText_UppercasedText() {
        TextService uppercaseTextService = new UppercaseTextService();
        assertEquals("TEST", uppercaseTextService.processText("test"));
    }
}