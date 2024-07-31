package com.baeldung.systemin;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static com.baeldung.systemin.Application.NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class SystemRulesUnitTest {

    @Rule
    public final TextFromStandardInputStream systemIn = emptyStandardInputStream();

    @Test
    public void givenName_whenReadWithSystemRules_thenReturnCorrectResult() {
        systemIn.provideLines("Baeldung");
        assertEquals(NAME.concat("Baeldung"), Application.readName());
    }
}

