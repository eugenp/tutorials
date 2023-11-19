package com.baeldung.argsVsvarargs;

import static com.baeldung.argsVsvarargs.StringArrayAndVarargs.capitalizeNames;
import static com.baeldung.argsVsvarargs.StringArrayAndVarargs.firstLetterOfWords;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringArrayAndVarargsUnitTest {

    @Test
    void whenCheckingArgumentClassName_thenNameShouldBeStringArray() {
        String[] names = {"john", "ade", "kofi", "imo"};
        assertNotNull(names);
        assertEquals("java.lang.String[]", names.getClass().getTypeName());
        capitalizeNames(names);
    }

    @Test
    void whenCheckingReturnedObjectClass_thenClassShouldBeStringArray() {
        assertEquals(String[].class, firstLetterOfWords("football", "basketball", "volleyball").getClass());
        assertEquals(3, firstLetterOfWords("football", "basketball", "volleyball").length);
    }
}