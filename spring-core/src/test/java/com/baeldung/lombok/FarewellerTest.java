package com.baeldung.lombok;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FarewellerTest {

    private final static String TRANSLATED = "TRANSLATED";

    @Test
    public void sayByeWithTranslatedMessage() {
        Translator translator = mock(Translator.class);
        when(translator.translate("bye")).thenReturn(TRANSLATED);
        Fareweller fareweller = new Fareweller(translator);
        assertEquals(TRANSLATED, fareweller.farewell());
    }
}