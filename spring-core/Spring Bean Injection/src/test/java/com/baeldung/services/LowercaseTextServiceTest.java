package com.baeldung.services;

import org.junit.Test;
import static org.junit.Assert.*;

public class LowercaseTextServiceTest {
    @Test
    public void processText_UppercasedText_LowercasedText() {
        TextService lowercaseTextService = new LowercaseTextService();
        assertEquals("test", lowercaseTextService.processText("TEST"));
    }
}